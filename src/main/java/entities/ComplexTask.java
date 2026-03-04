package entities;

import java.util.ArrayList;
import java.util.List;

public class ComplexTask extends Task{
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
        subtasks.add(added);
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
    public int estimateDuration() {
        int sum = 0;
        for (Task t : subtasks)
        {
            sum += t.estimateDuration();
        }
        return sum;
    }
}
