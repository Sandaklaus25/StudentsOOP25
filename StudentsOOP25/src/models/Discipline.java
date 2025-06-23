package models;

import java.util.Objects;
/**
 * Represents an academic discipline with a name and a flag indicating
 * whether it is mandatory or elective.
 */
public class Discipline {
    private final String name;
    private final boolean isMandatory;
    /**
     * Constructs a new {@code Discipline} with the specified name and type.
     *
     * @param name       the name of the discipline
     * @param mandatory  {@code true} if the discipline is mandatory, {@code false} if elective
     */
    public Discipline(String name, boolean mandatory) {
        this.name = name;
        this.isMandatory = mandatory;
    }
    /**
     * Returns the name of the discipline.
     *
     * @return the discipline name
     */
    public String getName() {
        return name;
    }
    /**
     * Returns whether the discipline is mandatory.
     *
     * @return {@code true} if mandatory, {@code false} if elective
     */
    public boolean isMandatory() {
        return isMandatory;
    }
    /**
     * Checks equality based on name and mandatory status.
     *
     * @param o the object to compare
     * @return {@code true} if both disciplines have the same name and mandatory status
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return isMandatory == that.isMandatory && Objects.equals(name, that.name);
    }
    /**
     * Returns the hash code for this Discipline, based on its name.
     *
     * @return the hash code of the discipline
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, isMandatory);
    }
    /**
     * Returns a string representation of the Discipline.
     *
     * @return a string describing the discipline, including name and is it mandatory
     */
    @Override
    public String toString() {
        return "Дисциплина{" +
                "Име='" + name + '\'' +
                ", избирателна=" + !isMandatory +
                '}';
    }
}
