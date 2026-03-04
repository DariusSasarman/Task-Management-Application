package business;

import dao.AssignmentDao;
import dao.EntityDao;
import entities.Employee;
import entities.Task;

import java.util.HashMap;
import java.util.List;

class TasksManagement {

    private AssignmentDao AssignmentStorage;
    private EntityDao EntityStorage;

    public TasksManagement(EntityDao EntityStorage, AssignmentDao AssignmentStorage)
    {
        this.AssignmentStorage = AssignmentStorage;
        this.EntityStorage = EntityStorage;
    }

    public void assignTaskToEmployee(int idEmployee, Task added)
    {
        Employee target = EntityStorage.getEmployee(idEmployee);
        if(EntityStorage.getTask(added.getIdTask()) == null) EntityStorage.addTask(added);
        AssignmentStorage.addTaskToEmployeeList(target,added);
    }

    public int calculateEmployeeWorkDuration(int idEmployee)
    {
        Employee target = EntityStorage.getEmployee(idEmployee);
        if(target == null) throw new RuntimeException("Target Employee does not exist.");
        List<Task> list = AssignmentStorage.getTaskList(target);
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

    public List<Employee> getEmployeeList() {
        return EntityStorage.getEmployeeList();
    }

    public Integer getCompletedCount(int idEmployee) {
        Integer count = 0;
        for(Task t : AssignmentStorage.getTaskList(EntityStorage.getEmployee(idEmployee)))
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
        for(Task t : AssignmentStorage.getTaskList(EntityStorage.getEmployee(idEmployee)))
        {
            if(t.getStatusTask().equals("Uncompleted"))
            {
                count++;
            }
        }
        return count;
    }

    public void securePersistence() {
        AssignmentStorage.securePersistence();
        EntityStorage.securePersistence();
    }
}
