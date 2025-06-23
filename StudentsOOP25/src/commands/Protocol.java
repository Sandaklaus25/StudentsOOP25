package commands;

import commands.interfaces.Command;
import models.Discipline;
import models.DisciplineManager;
import models.FileManager;
import models.StudentsManager;
import exceptions.InsufficientArgumentsException;
/**
 * The Protocol command uses one-row version of print per student for all students enrolled in a specific discipline.
 * <p>
 * This command produces a setUpProtocols listing all students registered
 * for a particular course or in my case Discipline.
 * </p>
 * <p>
 * Command format: setUpProtocols <course>;
 * </p>
 */
public class Protocol implements Command {
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        Discipline discipline;
        try
        {
            discipline = DisciplineManager.getDisciplineByName(t[1]);
        }catch (ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        return StudentsManager.getInstance().protocol(discipline);
    }
}
