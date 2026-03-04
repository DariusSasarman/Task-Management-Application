package dao;

import entities.Employee;
import entities.Task;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class dao implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Map<Integer, Employee> employees;
    private Map<Integer, Task> tasks;

    private dao()
    {
        this.employees = new HashMap<>();
        this.tasks = new HashMap<>();
    }

    public Employee getEmployee(int idEmployee) {
        return employees.get(idEmployee);
    }

    public Task getTask(int idTask) {
        return tasks.get(idTask);
    }

    public void addTask(Task added) {
        tasks.put(added.getIdTask(),added);
    }

    public void addEmployee(Employee added)
    {
        employees.put(added.getIdEmployee(),added);
    }

    public void securePersistence()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.dat"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static dao loadInformation()
    {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.dat"))) {
            return (dao) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new dao();
        }
    }
}
