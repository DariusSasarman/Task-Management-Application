package business;

import dao.AssignmentDao;
import dao.EntityDao;
import entities.Employee;
import entities.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class TasksManagement  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private AssignmentDao assignmentStorage;
    private EntityDao entityStorage;

    public TasksManagement()
    {
        this.assignmentStorage = new AssignmentDao();
        this.entityStorage = new EntityDao();
    }

    /// Persistence Methods

    public void securePersistence()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Data.dat"))) {
            oos.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TasksManagement loadInformation()
    {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Data.dat"))) {
            return (TasksManagement) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new TasksManagement();
        }
    }

    /// Required Methods

    public void assignTaskToEmployee(int idEmployee, Task added)
    {
        Employee target = entityStorage.getEmployee(idEmployee);
        if(entityStorage.getTask(added.getIdTask()) == null) entityStorage.addTask(added);
        assignmentStorage.addTaskToEmployeeList(target,added);
    }

    public int calculateEmployeeWorkDuration(int idEmployee)
    {
        Employee target = entityStorage.getEmployee(idEmployee);
        if(target == null) throw new RuntimeException("Target Employee does not exist.");
        List<Task> list = assignmentStorage.getTaskList(target);
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


    public Integer getCompletedCount(int idEmployee) {
        Integer count = 0;
        for(Task t : assignmentStorage.getTaskList(entityStorage.getEmployee(idEmployee)))
        {
            if(t.getStatusTask().equals("Completed"))
            {
                count++;
            }
        }
        return count;
    }

    public Integer getUncompletedCount(int idEmployee) {
        Integer count = 0;
        for(Task t : assignmentStorage.getTaskList(entityStorage.getEmployee(idEmployee)))
        {
            if(t.getStatusTask().equals("Uncompleted"))
            {
                count++;
            }
        }
        return count;
    }

    /// Entity DAO methods

    public void addEmployee(Employee added) {
        entityStorage.addEmployee(added);
    }

    public List<Employee> getEmployeeList() {
        return entityStorage.getEmployeeList();
    }

    public void removeEmployee(Employee target) {
        assignmentStorage.removeEmployee(target);
        entityStorage.removeEmployee(target);
    }

    public void addTask(Task added) {entityStorage.addTask(added);}

    public List<Task> getTaskList() {
        return entityStorage.getTaskList();
    }

    public void removeTask(Task added) {
        entityStorage.removeTask(added);
        assignmentStorage.removeTask(added);
    }

    public List<Task> getAssignedTasks(Employee e) {
        return new ArrayList<>(assignmentStorage.getTaskList(e));
    }
}
