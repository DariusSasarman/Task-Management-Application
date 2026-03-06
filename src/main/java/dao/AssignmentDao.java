package dao;

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

    public void removeTask(Task added) {
        for(Employee e : map.keySet())
        {
            map.get(e).remove(added);
        }
    }
}
