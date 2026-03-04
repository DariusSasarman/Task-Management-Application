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

    public void securePersistence()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("AssignmentData.dat"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AssignmentDao loadInformation()
    {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("AssignmentData.dat"))) {
            return (AssignmentDao) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new AssignmentDao();
        }
    }

    public void addTaskToEmployeeList(Employee target, Task added)
    {
        if(target == null) throw new RuntimeException("Target Employee does not exist.");
        map.get(target).add(added);
        if(!map.containsKey(target))
        {
            map.put(target,new ArrayList<>());
        }
        map.get(target).add(added);
    }

    public List<Task> getTaskList( Employee target)
    {
        if(target == null) throw new RuntimeException("Target Employee does not exist.");
        return this.map.get(target);
    }
}
