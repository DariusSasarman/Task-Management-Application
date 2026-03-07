package entities;

import java.util.ArrayList;
import java.util.List;

public final class ComplexTask extends Task{
    private List<Task> subtasks;

    public ComplexTask(int idTask)
    {
        super(idTask);
        this.subtasks = new ArrayList<>();
    }

    public ComplexTask(int idTask, String statusTask)
    {
        super(idTask,statusTask);
        this.subtasks = new ArrayList<>();
    }

    /// List modifier methods
    public void addTask(Task added)
    {
        if(added == this) return;
        if(subtasks.contains(added))return;
        if (added instanceof ComplexTask)
        {
            if(((ComplexTask) added).containsTask(this))return;
        }
        subtasks.add(added);
    }

    public void removeTask(Task target)
    {
        subtasks.remove(target);
        List<ComplexTask> emptyComplexTasks = new ArrayList<>();
        for(Task t : subtasks)
        {
            if(t instanceof ComplexTask)
            {
                ((ComplexTask) t).removeTask(target);
                if(((ComplexTask)t).getSubtasks().isEmpty())
                {
                    emptyComplexTasks.add(((ComplexTask)t));
                }
            }
        }
        subtasks.removeAll(emptyComplexTasks);
    }

    /// List getter method
    public List<Task> getSubtasks()
    {
        return this.subtasks;
    }

    /// This checks down the tree if the complex Task contains the target task
    public boolean containsTask(Task target) {
        for (Task t : subtasks) {
            if (t == target) return true;
            if (t instanceof ComplexTask)
            {
                if(((ComplexTask) t).containsTask(target))return true;
            }
        }
        return false;
    }


    @Override
    public void setStatusTask(String statusTask)
    {
        super.setStatusTask(statusTask);
        for(Task t : getSubtasks())
        {
            /// Completion Status is inherited down
            /// the task tree
            t.setStatusTask(statusTask);
        }
    }

    @Override
    public int estimateDuration() {
        int sum = 0;
        for (Task t : subtasks)
        {
            sum += t.estimateDuration();
        }
        return sum;
    }

    @Override
    public String toString()
    {
        StringBuilder ret = new StringBuilder();
        ret.append(this.getIdTask());
        ret.append("[ ");
        for(Task t : subtasks)
        {
            ret.append(t.toString());
            if(!t.equals(subtasks.getLast()))
            {
                ret.append(", ");
            }
        }
        ret.append(" ]");
        return ret.toString();
    }
}
