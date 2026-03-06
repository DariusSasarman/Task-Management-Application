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

    /// Overridden because these objects are stored
    /// in hashmaps, and this solves persistence issues
    ///
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee e)) return false;
        return idEmployee == e.idEmployee;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idEmployee);
    }
}
