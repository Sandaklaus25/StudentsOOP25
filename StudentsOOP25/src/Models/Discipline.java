package Models;

import java.util.Objects;

public class Discipline {
    private final String name;
    private final boolean isMandatory;

    public Discipline(String name, boolean mandatory) {
        this.name = name;
        this.isMandatory = mandatory;
    }

    public String getName() {
        return name;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return isMandatory == that.isMandatory && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isMandatory);
    }

    @Override
    public String toString() {
        return "Дисциплина{" +
                "Име='" + name + '\'' +
                ", избирателна=" + !isMandatory +
                '}';
    }
}
