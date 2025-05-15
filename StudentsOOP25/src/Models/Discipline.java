package Models;

import java.util.*;

public class Discipline {
    private String name;
    private boolean isMandatory;

    protected Discipline(String name, boolean mandatory) {
        this.name = name;
        this.isMandatory = mandatory;
    }

    // <editor-fold desc="Getters and Setters">
    public String getName() {
        return name;
    }

    public boolean getIsMandatory()
    {
        return isMandatory;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }
    // </editor-fold>

    // <editor-fold desc="User Actions">
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return isMandatory == that.isMandatory && Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        return "Дисциплина{" +
                "Име='" + name + '\'' +
                ", избирателна=" + !isMandatory +
                '}';
    }
    // </editor-fold>
}
