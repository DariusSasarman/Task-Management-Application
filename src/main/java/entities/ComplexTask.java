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

    public void removeTask(Task target)
    {
        subtasks.remove(target);
    }

    public List<Task> getSubtasks()
    {
        return this.subtasks;
    }

    @Override
    public void setStatusTask(String statusTask)
    {
        super.setStatusTask(statusTask);
        for(Task t : getSubtasks())
        {
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
