package Models;

import java.util.ArrayList;
import java.util.List;

public class DisciplineManager {
    private static final DisciplineManager instance = new DisciplineManager();
    private  final List<Discipline> disciplines = new ArrayList<>();

    private DisciplineManager() {}

    public static DisciplineManager getInstance() {
        return instance;
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void clear() {
        disciplines.clear();
    }

    public void create(String name,boolean mandatory) {
        Discipline discipline = new Discipline(name, mandatory);
        disciplines.add(discipline);
        System.out.println("Дисциплината " + discipline.getName() +" е успешно запазена.");
    }

    public void remove(String name) {
        disciplines.removeIf(discipline -> discipline.getName().equals(name));
    }

    public static Discipline getDisciplineByString(String name) {
        for (Discipline discipline : instance.disciplines) {
            if (discipline.getName().equalsIgnoreCase(name)) {
                return discipline;
            }
        }
        System.out.println("Възникна грешка при намирането на дисциплина!");
        return null;
    }
}
