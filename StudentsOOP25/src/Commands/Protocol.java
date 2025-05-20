package Commands;

import Commands.Interfaces.Command;
import Models.Discipline;
import Models.DisciplineManager;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;
/**
 * The Protocol command uses one-row version of print per student for all students enrolled in a specific discipline.
 * <p>
 * This command produces a protocol listing all students registered
 * for a particular course or in my case Discipline.
 * </p>
 * <p>
 * Command format: protocol <course>;
 * </p>
 */
public class Protocol implements Command {
    @Override
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        Discipline discipline;
        try
        {
            discipline = DisciplineManager.getDisciplineByName(t[1]);
        }catch (ClassNotFoundException e)
        {
            return e.getMessage();
        }
        return sm.protocol(discipline);
    }
}
