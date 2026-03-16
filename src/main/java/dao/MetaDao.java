package dao;

import entities.Employee;
import entities.Task;

import java.io.*;
import java.util.List;

public class MetaDao implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private AssignmentDao assignmentStorage;
    private EntityDao entityStorage;

    /// Persistence Methods
    public MetaDao()
    {
        this.assignmentStorage = new AssignmentDao();
        this.entityStorage = new EntityDao();
    }

    public void securePersistence()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Data.dat"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static MetaDao loadInformation()
    {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Data.dat"))) {
            return (MetaDao) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new MetaDao();
        }
    }

    public Employee getEmployee(int idEmployee) {
        return entityStorage.getEmployee(idEmployee);
    }

    public Task getTask(int idTask) {
        return entityStorage.getTask(idTask);
    }

    public void addEmployee(Employee added) {
        entityStorage.addEmployee(added);
    }

    public void addTask(Task added) {
        entityStorage.addTask(added);
    }

    public void addTaskToEmployeeList(Employee target, Task added) {
        assignmentStorage.addTaskToEmployeeList(target,added);
    }

    public List<Task> getEmployeeTaskList(Employee target) {
        return assignmentStorage.getTaskList(target);
    }


    public List<Employee> getEmployeeList() {
        return entityStorage.getEmployeeList();
    }

    public void removeEmployee(Employee target) {
        assignmentStorage.removeEmployee(target);
        entityStorage.removeEmployee(target);
    }

    public List<Task> getTaskList() {
        return entityStorage.getTaskList();
    }

    public void removeTask(Task added) {
        entityStorage.removeTask(added);
        assignmentStorage.removeTask(added);
    }
}
