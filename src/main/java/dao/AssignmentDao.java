package dao;

import entities.ComplexTask;
import entities.Employee;
import entities.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssignmentDao implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Map<Employee, List<Task>> map;
    
    public AssignmentDao()
    {
        this.map = new HashMap<>();
    }

    public void addTaskToEmployeeList(Employee target, Task added)
    {
        if(target == null) throw new RuntimeException("Target employee does not exist.");
        if(added == null) throw new RuntimeException("Target task does not exist.");
        if(!map.containsKey(target))
        {
            map.put(target,new ArrayList<>());
        }
        map.get(target).add(added);
    }

    public List<Task> getTaskList( Employee target)
    {
        if(target == null) throw new RuntimeException("Target Employee does not exist.");
        if(!map.containsKey(target))return new ArrayList<>();
        return this.map.get(target);
    }

    public void removeTask(Task target) {
        for(Employee e : map.keySet())
        {
            List<Task> taskList = map.get(e);
            List<ComplexTask> emptyComplexTaskList = new ArrayList<>();
            taskList.remove(target);

            /// Complex task handling.
            for(Task t : taskList)
            {
                if(t instanceof ComplexTask)
                {
                    ComplexTask complexTask = (ComplexTask) t;
                    if (complexTask.containsTask(target))
                    {
                        complexTask.removeTask(target);
                    }
                    if(complexTask.getSubtasks().isEmpty())
                    {
                        emptyComplexTaskList.add(complexTask);
                    }
                }
            }
            taskList.removeAll(emptyComplexTaskList);
        }

    }
    public void removeEmployee(Employee target) {
        map.remove(target);
    }
}
