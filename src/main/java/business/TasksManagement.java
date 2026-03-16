package business;

import dao.MetaDao;
import entities.Employee;
import entities.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class TasksManagement  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private MetaDao storage;

    public TasksManagement()
    {
        this.storage = MetaDao.loadInformation();
    }

    public void securePersistence()
    {
        storage.securePersistence();
    }
    /// Required Methods

    public void assignTaskToEmployee(int idEmployee, Task added)
    {
        Employee target = storage.getEmployee(idEmployee);
        if(storage.getTask(added.getIdTask()) == null) storage.addTask(added);
        storage.addTaskToEmployeeList(target,added);
    }

    public int calculateEmployeeWorkDuration(int idEmployee)
    {
        Employee target = storage.getEmployee(idEmployee);
        if(target == null) throw new RuntimeException("Target Employee does not exist.");
        List<Task> list = storage.getEmployeeTaskList(target);
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
        for(Task t : storage.getEmployeeTaskList(storage.getEmployee(idEmployee)))
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
        for(Task t : storage.getEmployeeTaskList(storage.getEmployee(idEmployee)))
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
        storage.addEmployee(added);
    }

    public List<Employee> getEmployeeList() {
        return storage.getEmployeeList();
    }

    public void removeEmployee(Employee target) {
        storage.removeEmployee(target);
    }

    public void addTask(Task added) {storage.addTask(added);}

    public List<Task> getTaskList() {
        return storage.getTaskList();
    }

    public void removeTask(Task target) {
        storage.removeTask(target);
    }

    public List<Task> getAssignedTasks(Employee e) {
        return new ArrayList<>(storage.getEmployeeTaskList(e));
    }
}
