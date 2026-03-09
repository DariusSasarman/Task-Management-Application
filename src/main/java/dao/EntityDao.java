package dao;

import entities.ComplexTask;
import entities.Employee;
import entities.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class EntityDao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Map<Integer, Employee> employees;
    private Map<Integer, Task> tasks;

    public EntityDao()
    {
        this.employees = new HashMap<>();
        this.tasks = new HashMap<>();
    }


    /// Employee DAO methods
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

    public void removeEmployee(Employee target) {
        if(!employees.containsKey(target.getIdEmployee()))return;
        employees.remove(target.getIdEmployee());
    }

    public List<Employee> getEmployeeList() { return new ArrayList<>(employees.values());}

    /// Task DAO methods

    public Task getTask(int idTask) {
        return tasks.get(idTask);
    }

    public void addTask(Task added) {
        if(tasks.containsKey(added.getIdTask()))
        {
            throw new RuntimeException("Task UUID Already exists");
        }
        tasks.put(added.getIdTask(),added);
    }

    public List<Task> getTaskList() { return new ArrayList<>(tasks.values());}

    public void removeTask(Task target) {
        if(!tasks.containsKey(target.getIdTask())) return;
        tasks.remove(target.getIdTask());

        /// Complex tasks might still hold a reference
        /// To the simple task at hand
        /// Which would prevent the garbage collector
        /// To delete the instance completely

        List<ComplexTask> emptyComplexTasks = new ArrayList<>();

        for(Task t : tasks.values())
        {
            if(t instanceof ComplexTask)
            {
                ComplexTask complexTask = (ComplexTask) t;
                if (complexTask.containsTask(target))
                {
                    complexTask.removeTask(target);

                }
                /// This might delete the last task inside it
                /// An empty complex task isn't a task at all
                if(complexTask.getSubtasks().isEmpty())
                {
                    emptyComplexTasks.add(complexTask);
                }
            }
        }
        for(ComplexTask t : emptyComplexTasks)
        {
            tasks.remove(t.getIdTask());
        }

    }
}
