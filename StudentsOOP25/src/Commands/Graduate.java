package Commands;

import Commands.Interfaces.Command;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;
/**
 * The Graduate command marks a student as having completed their studies.
 * <p>
 * This command updates a student's status to "hasGraduated" if they have successfully
 * completed all required courses and meet graduation criteria.
 * </p>
 * <p>
 * Command format: graduate <fn>
 * </p>
 */
public class Graduate implements Command {
    @Override
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        return sm.graduate(t[1]);
    }
}
