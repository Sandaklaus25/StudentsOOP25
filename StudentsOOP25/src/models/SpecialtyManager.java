package models;

import java.util.ArrayList;
import java.util.List;
/**
 * A singleton class that manages a collection of academic program/specialty.
 * Provides methods to create, retrieve, and clear specialties.
 */
public class SpecialtyManager {
    private static final SpecialtyManager instance = new SpecialtyManager();
    private final List<Specialty> specialties = new ArrayList<>();
    /**
     * Private constructor to enforce singleton pattern.
     */
    private SpecialtyManager() {}
    /**
     * Returns the singleton instance of SpecialtyManager.
     *
     * @return the singleton SpecialtyManager instance
     */
    public static SpecialtyManager getInstance() {
        return instance;
    }
    /**
     * Returns a new list containing all specialties managed by this instance.
     *
     * @return a list of Specialty objects
     */
    public List<Specialty> getSpecialties() {
        return new ArrayList<>(specialties);
    }
    /**
     * Creates a new Specialty with the given name and adds it to the manager.
     * Prints a confirmation message on addition.
     *
     * @param name the name of the new specialty to create
     */
    public void create(String name) {
        Specialty specialty = new Specialty(name);
        specialties.add(specialty);
        System.out.println("Specialty " + specialty.getName() + " has been added.");
    }
    /**
     * Finds and returns a Specialty by its name, ignoring case.
     * If no specialty with the given name exists, prints a message and returns null.
     *
     * @param name the name of the specialty to search for
     * @return the matching Specialty object, or null if not found
     */
    public static Specialty getSpecialtyByName(String name){
        for (Specialty specialty : instance.specialties) {
            if (specialty.getName().equalsIgnoreCase(name)) {
                return specialty;
            }
        }
        System.out.println("Specialty not found!");
        return null;
    }
    /**
     * Clears all specialties from the manager.
     */
    public void clear() {
        specialties.clear();
    }
}
