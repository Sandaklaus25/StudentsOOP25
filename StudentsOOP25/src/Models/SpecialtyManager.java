package Models;

import java.util.ArrayList;
import java.util.List;

public class SpecialtyManager {
    private static final SpecialtyManager instance = new SpecialtyManager();
    private  final List<Specialty> specialties = new ArrayList<>();

    public SpecialtyManager() {}

    public static SpecialtyManager getInstance() {
        return instance;
    }
    public List<Specialty> getSpecialties() {return specialties;}

    public void create(String name) {
        Specialty specialty = new Specialty(name);
        specialties.add(specialty);
        System.out.println("Специалност " + specialty.getName() +" е успешно запазена.");
    }

    public void remove(String name) {
        specialties.removeIf(specialty -> specialty.getName().equals(name));
    }


    public static Specialty getSpecialtyByString(String name) {
        for (Specialty specialty : instance.specialties) {
            if (specialty.getName().equalsIgnoreCase(name)) {
                return specialty;
            }
        }
        System.out.println("Възникна грешка при намирането на специалност!");
        return null;
    }

    public void clear() {
        specialties.clear();
    }
}
