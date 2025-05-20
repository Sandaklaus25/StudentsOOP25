package Models;

import java.util.ArrayList;
import java.util.List;

public class DisciplineManager {
    private static final DisciplineManager instance = new DisciplineManager();
    private final List<Discipline> disciplines = new ArrayList<>();

    private DisciplineManager() {}

    public static DisciplineManager getInstance() {
        return instance;
    }

    public List<Discipline> getDisciplines() {
        return new ArrayList<>(disciplines);
    }

    public String create(String name, boolean mandatory) {
        Discipline discipline = new Discipline(name, mandatory);
        disciplines.add(discipline);
        return ("Дисциплината " + discipline.getName() + " е успешно добавена.");
    }

    public static Discipline getDisciplineByName(String name) throws ClassNotFoundException {
        for (Discipline discipline : instance.disciplines) {
            if (discipline.getName().equalsIgnoreCase(name)) {
                    return discipline;
            }
        }
        throw new ClassNotFoundException("Възникна грешка при намирането на дисциплина!");
    }

    public void clear() {
        disciplines.clear();
    }
}