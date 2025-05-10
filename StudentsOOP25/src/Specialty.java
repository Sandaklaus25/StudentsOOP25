import java.util.*;

public class Specialty {
    private String name;
    private HashMap<Discipline, List<Byte>> disciplineCourses = new HashMap<>();
    public static List<Specialty> specialties = new ArrayList<>();

    public Specialty(String name) {
        this.name = name;
        specialties.add(this);
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
    public static Specialty getSpecialtyByString(String name) {
        for (Specialty specialty : specialties) {
            if (specialty.getName().equalsIgnoreCase(name)) {
                return specialty;
            }
        }
        return null;
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
    // </editor-fold>

    @Override
    public String toString() {
        return "Specialty{" +
                "name='" + name + '\'' +
                ", disciplineCourses=" + disciplineCourses +
                '}';
    }
}
