package business;

import dao.AssignmentDao;
import dao.EntityDao;
import entities.ComplexTask;
import entities.Employee;
import entities.SimpleTask;
import entities.Task;

import java.util.*;

public class Utilities {

    private TasksManagement taskManager;

    public Utilities()
    {
        this.taskManager = new TasksManagement(EntityDao.loadInformation(),AssignmentDao.loadInformation());
    }

    public List<Employee> getRanking()
    {
        List<Employee> returned = taskManager.getEmployeeList();
        returned.removeIf((Employee e) -> taskManager.calculateEmployeeWorkDuration(e.getIdEmployee()) < 40);
        returned.sort(Comparator.comparing((Employee e) -> taskManager.calculateEmployeeWorkDuration(e.getIdEmployee())));
        return returned;
    }

    public Map<String, Map<String,Integer>> getStatus()
    {
        HashMap<String,Map<String,Integer>> returned = new HashMap<>();
        for(Employee e : taskManager.getEmployeeList())
        {
            returned.put(e.getName(),new HashMap<>());
            returned.get(e.getName()).put("Completed",taskManager.getCompletedCount(e.getIdEmployee()));
            returned.get(e.getName()).put("Uncompleted",taskManager.getUncompletedCount(e.getIdEmployee()));
        }
        return returned;
    }

    public void save()
    {
        taskManager.securePersistence();
    }

    public void addEmployee(Employee added) {
        taskManager.addEmployee(added);
    }

    public List<Employee> getEmployees() {
        return taskManager.getEmployeeList();
    }

    public void removeEmployee(Employee target) {
        taskManager.removeEmployee(target);
    }

    public void addTask(Task added) {taskManager.addTask(added);}

    public List<Task> getTasks() {
        return taskManager.getTaskList();
    }

    public void removeTask(Task added) {
        taskManager.removeTask(added);
    }
}
