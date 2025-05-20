package Commands;

import Commands.Interfaces.Command;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;
/**
 * Command implementation for advancing a student ignoring disciplines to the next year.
 * Format: advance <facultyNumber>
 */
public class Advance implements Command {
    /**
     * Advances a student to the next academic year.
     *
     * @param t Array containing command tokens where:
     *          t[0] - command name
     *          t[1] - faculty number
     * @param sm Reference to the StudentsManager
     * @param fm Reference to the FileManager
     * @return Result message of the operation
     * @throws InsufficientArgumentsException When incorrect number of arguments is provided
     */
    @Override
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        return sm.advance(t[1]);
    }
}