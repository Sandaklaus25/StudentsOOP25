package commands;

import commands.interfaces.Command;
import models.FileManager;
import models.StudentsManager;
import exceptions.InsufficientArgumentsException;
/**
 * Command implementation for advancing a student ignoring disciplines to the next year.
 * Format: advance <facultyNumber>
 */
public class Advance implements Command {
    /**
     * Advances a student to the next academic year.
     *
     * @param t  Array containing command tokens where:
     *           t[0] - command name
     *           t[1] - faculty number
     * @param fm Reference to the FileManager
     * @return Result message of the operation
     * @throws InsufficientArgumentsException When incorrect number of arguments is provided
     */
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        return StudentsManager.getInstance().advance(t[1]);
    }
}