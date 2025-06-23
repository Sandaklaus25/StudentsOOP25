package models;

import java.util.ArrayList;
import java.util.List;
/**
 * A singleton class that manages a collection of academic disciplines.
 * Provides methods to create, retrieve, and clear disciplines.
 */
public class DisciplineManager {
    /** The singleton instance of {@code DisciplineManager}. */
    private static final DisciplineManager instance = new DisciplineManager();
    /** The list of disciplines managed by this class. */
    private final List<Discipline> disciplines = new ArrayList<>();
    /**
     * Private constructor to enforce singleton pattern.
     */
    private DisciplineManager() {}
    /**
     * Returns the singleton instance of {@code DisciplineManager}.
     *
     * @return the singleton instance
     */
    public static DisciplineManager getInstance() {
        return instance;
    }
    /**
     * Returns a copy of the list of all disciplines.
     *
     * @return a list of disciplines
     */
    public List<Discipline> getDisciplines() {
        return new ArrayList<>(disciplines);
    }
    /**
     * Creates a new discipline with the given name and mandatory status,
     * and adds it to the list.
     *
     * @param name      the name of the discipline
     * @param mandatory {@code true} if the discipline is mandatory, {@code false} if elective
     */
    public void create(String name, boolean mandatory) {
        Discipline discipline = new Discipline(name, mandatory);
        disciplines.add(discipline);
        System.out.println("Discipline " + discipline.getName() + " has been added.");
    }
    /**
     * Retrieves a discipline by its name, ignoring case sensitivity.
     *
     * @param name the name of the discipline to search for
     * @return the matching {@code Discipline}, or {@code null} if not found
     */
    public static Discipline getDisciplineByName(String name){
        for (Discipline discipline : instance.disciplines) {
            if (discipline.getName().equalsIgnoreCase(name)) {
                    return discipline;
            }
        }
        System.out.println("Discipline not found!");
        return null;
    }
    /**
     * Removes all disciplines from the manager.
     */
    public void clear() {
        disciplines.clear();
    }
}