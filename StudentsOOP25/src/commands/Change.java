package commands;

import commands.interfaces.Command;
import models.FileManager;
import models.StudentsManager;
import exceptions.InsufficientArgumentsException;
/**
 * Command implementation for changing student attributes.
 * Format: change <facultyNumber> <option> <value>
 */
public class Change implements Command {
    /**
     * Changes a student's program, group, or year based on the provided option.
     *
     * @param t  Array containing command tokens where:
     *           t[0] - command name
     *           t[1] - faculty number
     *           t[2] - option to change (program, group, year)
     *           t[3] - value to use
     * @param fm Reference to the FileManager
     * @return Result message of the operation
     * @throws InsufficientArgumentsException When incorrect number of arguments is provided
     */
    @Override
        public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=4)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        String facultyNumber = t[1];
        String option = t[2];
        String value = t[3];

        return StudentsManager.getInstance().change(facultyNumber, option, value);
    }
}