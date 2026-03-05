package dao;

import entities.Employee;
import entities.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityDao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Map<Integer, Employee> employees;
    private Map<Integer, Task> tasks;

    private EntityDao()
    {
        this.employees = new HashMap<>();
        this.tasks = new HashMap<>();
    }

    public void securePersistence()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("EntityData.dat"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static EntityDao loadInformation()
    {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("EntityData.dat"))) {
            return (EntityDao) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new EntityDao();
        }
    }

    public Employee getEmployee(int idEmployee) {
        return employees.get(idEmployee);
    }

    public void addEmployee(Employee added)
    {
        if(employees.containsKey(added.getIdEmployee()))
        {
            throw new RuntimeException("Employee UUID Already exists");
        }
        employees.put(added.getIdEmployee(),added);
    }

    public List<Employee> getEmployeeList() { return (ArrayList<Employee>) employees.values();}

    public Task getTask(int idTask) {
        return tasks.get(idTask);
    }

    public void addTask(Task added) {
        tasks.put(added.getIdTask(),added);
    }

    public List<Task> getTaskList() { return (ArrayList<Task>) tasks.values();}
}
