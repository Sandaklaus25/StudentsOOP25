package Models;

import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Objects;

public class Specialty {
    private final String name;
    private final Map<Discipline, List<Byte>> disciplineCourses = new HashMap<>();

    public Specialty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<Discipline, List<Byte>> getDisciplineCourses() {
        return new HashMap<>(disciplineCourses);
    }


    public void addDiscipline(Discipline discipline, List<Byte> courses) {
        disciplineCourses.put(discipline, new ArrayList<>(courses));
    }

    private boolean removeDiscipline(Discipline discipline) {
       return disciplineCourses.remove(discipline) != null;
    }

    private void addCourseToDiscipline(Discipline discipline, Byte course) {
        disciplineCourses.computeIfAbsent(discipline, k -> new ArrayList<>());
        List<Byte> courses = disciplineCourses.get(discipline);
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "name='" + name + '\'' +
                ", disciplineCourses=" + disciplineCourses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specialty specialty = (Specialty) o;
        return Objects.equals(name, specialty.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    // </editor-fold>
}
