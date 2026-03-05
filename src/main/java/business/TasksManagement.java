package business;

import dao.AssignmentDao;
import dao.EntityDao;
import entities.Employee;
import entities.Task;

import java.util.List;

class TasksManagement {

    private AssignmentDao assignmentStorage;
    private EntityDao entityStorage;

    public TasksManagement(EntityDao EntityStorage, AssignmentDao AssignmentStorage)
    {
        this.assignmentStorage = AssignmentStorage;
        this.entityStorage = EntityStorage;
    }

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

    public List<Employee> getEmployeeList() {
        return entityStorage.getEmployeeList();
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

    public void securePersistence() {
        assignmentStorage.securePersistence();
        entityStorage.securePersistence();
    }

    public void addEmployee(Employee added) {
        entityStorage.addEmployee(added);
    }

    public void removeEmployee(Employee target) {
        entityStorage.removeEmployee(target);
    }

    public void addTask(Task added) {entityStorage.addTask(added);}

    public List<Task> getTaskList() {
        return entityStorage.getTaskList();
    }

    public void removeTask(Task added) {
        entityStorage.removeTask(added);
    }
}
