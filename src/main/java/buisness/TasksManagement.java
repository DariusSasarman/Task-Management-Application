package buisness;

import dao.dao;
import entities.Employee;
import entities.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TasksManagement {

    private Map<Employee, List<Task>> map;
    private dao rep;
    public TasksManagement()
    {
        this.map = new HashMap<>();
        this.rep = dao.loadInformation();
    }

    public void assignTaskToEmployee(int idEmployee, Task added)
    {
        Employee target = rep.getEmployee(idEmployee);
        if(target == null) throw new RuntimeException("Target Employee does not exist.");
        if(rep.getTask(added.getIdTask()) == null) rep.addTask(added);
        if(!map.containsKey(target))
        {
            map.put(target,new ArrayList<>());
        }
        map.get(target).add(added);
    }

    public int calculateEmployeeWorkDuration(int idEmployee)
    {
        Employee target = rep.getEmployee(idEmployee);
        if(target == null) throw new RuntimeException("Target Employee does not exist.");

        List<Task> list = map.get(target);
        int returnedSum = 0;
        for(Task t : list)
        {
            if(t.getStatusTask().equals("Completed"))
            {
                returnedSum += t.estimateDuration();
            }
        }
        return returnedSum;
    }

    public void saveData()
    {
        rep.securePersistence();
    }
}
