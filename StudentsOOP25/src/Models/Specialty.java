package Models;

import java.util.*;

public class Specialty {
    private String name;
    private HashMap<Discipline, List<Byte>> disciplineCourses = new HashMap<>();

    protected Specialty(String name) {
        this.name = name;

    }

    // <editor-fold desc="Getters and Setters">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Discipline, List<Byte>> getDisciplineCourses() {
        return disciplineCourses;
    }

    public void setDisciplineCourses(HashMap<Discipline, List<Byte>> disciplineCourses) {
        this.disciplineCourses = disciplineCourses;
    }


    // </editor-fold>

    // <editor-fold desc="User Actions">
    public void AddDiscipline(Discipline discipline, List<Byte> courses) {
        disciplineCourses.put(discipline, courses);
    }

    public void RemoveDiscipline(Discipline discipline) {
        disciplineCourses.remove(discipline);
    }

    public void AddCourseToDiscipline(Discipline discipline, Byte course) {

        disciplineCourses.putIfAbsent(discipline, new ArrayList<>());

        List<Byte> courses = disciplineCourses.get(discipline);

        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public void RemoveCourseFromDiscipline(Discipline discipline, byte course) {
        List<Byte> courses = disciplineCourses.get(discipline);

        if (courses != null) {
            courses.remove((Byte) course);

            if (courses.isEmpty()) //Delete discipline if it becomes empty
            {
                disciplineCourses.remove(discipline);
            }
        }
    }

    @Override
    public String toString() {
        return "Objects.Specialty{" +
                "name='" + name + '\'' +
                ", disciplineCourses=" + disciplineCourses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specialty specialty = (Specialty) o;
        return Objects.equals(name, specialty.name) && Objects.equals(disciplineCourses, specialty.disciplineCourses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, disciplineCourses);
    }
// </editor-fold>
}
