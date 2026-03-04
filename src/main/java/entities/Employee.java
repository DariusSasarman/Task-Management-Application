package entities;

import java.io.Serial;
import java.io.Serializable;

public final class Employee implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int idEmployee;
    private final String name;

    public Employee(int idEmployee, String name)
    {
        this.idEmployee = idEmployee;
        this.name = name;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public String getName() {
        return name;
    }
}
