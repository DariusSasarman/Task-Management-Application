package business;

import entities.Employee;
import entities.Task;

import java.util.*;

public class Utilities {

    private TasksManagement taskManager;

    public Utilities()
    {
        this.taskManager = new TasksManagement();
    }

    /// Required Methods
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

    /// Persistence method - called when closing
    public void save()
    {
        taskManager.securePersistence();
    }

    /// Crud Entity Methods
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

    /// Assigned Task Methods

    public void assignTasks(Employee target, List<Task> assignments) {
        for(Task t : assignments)
        {
            taskManager.assignTaskToEmployee(target.getIdEmployee(),t);
        }
    }

    public List<Task> getEmployeeAssignedTaskList(Employee target) {
        return taskManager.getAssignedTasks(target);
    }

    public String getAssignedTaskListToString(Employee e) {
        StringBuilder ret = new StringBuilder();
        ret.append('[');
        List<Task> tasks = getEmployeeAssignedTaskList(e);
        if(tasks.isEmpty())
        {
            ret.append("none]");
            return ret.toString();
        }
        for(Task t : tasks)
        {
            ret.append(t.toString());
            if(!t.equals(tasks.getLast()))
            {
                ret.append(", ");
            }
        }
        ret.append(']');
        return ret.toString();
    }

    public List<Task> getAllTasksAssignedList()
    {
        ArrayList<Task> ret = new ArrayList<>();
        for(Employee e : taskManager.getEmployeeList())
        {
            ret.addAll(taskManager.getAssignedTasks(e));
        }
        return ret;
    }
}
