package Models;

import java.util.ArrayList;
import java.util.List;

public class SpecialtyManager {
    private static final SpecialtyManager instance = new SpecialtyManager();
    private final List<Specialty> specialties = new ArrayList<>();

    private SpecialtyManager() {}

    public static SpecialtyManager getInstance() {
        return instance;
    }

    public List<Specialty> getSpecialties() {
        return new ArrayList<>(specialties);
    }

    public void create(String name) {
        Specialty specialty = new Specialty(name);
        specialties.add(specialty);
        System.out.println("Специалност " + specialty.getName() + " е успешно добавена.");
    }

    public boolean remove(String name) {
        return specialties.removeIf(specialty -> specialty.getName().equals(name));
    }

    public static Specialty getSpecialtyByName(String name) throws ClassNotFoundException {
        for (Specialty specialty : instance.specialties) {
            if (specialty.getName().equalsIgnoreCase(name)) {
                return specialty;
            }
        }
        throw new ClassNotFoundException("Специалността не принадлежи в списъка!");
    }

    public void clear() {
        specialties.clear();
    }
}
