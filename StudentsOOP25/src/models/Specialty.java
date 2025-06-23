package models;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Objects;
/**
 * Represents an academic program/specialty with a name and a disciplines
 * along with their courses where they can be studied.
 */
public class Specialty {
    private final String name;
    private final Map<Discipline, List<Byte>> disciplineCourses = new HashMap<>();
    /**
     * Constructs a Specialty with the given name.
     *
     * @param name the name of the specialty
     */
    public Specialty(String name) {
        this.name = name;
    }
    /**
     * Returns the name of this specialty.
     *
     * @return the specialty name
     */
    public String getName() {
        return name;
    }
    /**
     * Returns a copy of the map of disciplines and their associated courses.
     *
     * @return a new Map mapping Discipline to list of course numbers (as Bytes)
     */
    public Map<Discipline, List<Byte>> getDisciplineCourses() {
        return new HashMap<>(disciplineCourses);
    }
    /**
     * Adds a discipline with the associated list of courses to this specialty.
     * If the discipline already exists, it will be replaced.
     *
     * @param discipline the Discipline to add
     * @param courses the list of courses associated with the discipline
     */
    public void addDiscipline(Discipline discipline, List<Byte> courses) {
        disciplineCourses.put(discipline, new ArrayList<>(courses));
    }
    /**
     * Removes the specified discipline from this specialty.
     *
     * @param discipline the Discipline to remove
     * @return true if the discipline was removed, false if it was not found
     */
    private boolean removeDiscipline(Discipline discipline) {
       return disciplineCourses.remove(discipline) != null;
    }
    /**
     * Adds a course number to the list of courses for the specified discipline.
     * If the discipline is not present, it will be added with a new list.
     * If the course already exists for that discipline, it will not be added again.
     *
     * @param discipline the Discipline to which the course will be added
     * @param course the course number to add
     */
    private void addCourseToDiscipline(Discipline discipline, Byte course) {
        disciplineCourses.computeIfAbsent(discipline, k -> new ArrayList<>());
        List<Byte> courses = disciplineCourses.get(discipline);
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }
    /**
     * Returns a string representation of the Specialty.
     *
     * @return a string describing the specialty, including name and disciplines with courses
     */
    @Override
    public String toString() {
        return "Specialty{" +
                "name='" + name + '\'' +
                ", disciplineCourses=" + disciplineCourses +
                '}';
    }
    /**
     * Checks equality between this Specialty and another object.
     * Two Specialties are equal if they have the same name.
     *
     * @param o the object to compare to
     * @return true if the other object is a Specialty with the same name, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specialty specialty = (Specialty) o;
        return Objects.equals(name, specialty.name);
    }
    /**
     * Returns the hash code for this Specialty, based on its name.
     *
     * @return the hash code of the specialty
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    // </editor-fold>
}
