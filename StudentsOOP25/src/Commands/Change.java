package Commands;

import Commands.Interfaces.Command;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;
/**
 * Command implementation for changing student attributes.
 * Format: change <facultyNumber> <option> <value>
 */
public class Change implements Command {
    /**
     * Changes a student's program, group, or year based on the provided option.
     *
     * @param t Array containing command tokens where:
     *          t[0] - command name
     *          t[1] - faculty number
     *          t[2] - option to change (program, group, year)
     *          t[3] - value to use
     * @param sm Reference to the StudentsManager
     * @param fm Reference to the FileManager
     * @return Result message of the operation
     * @throws InsufficientArgumentsException When incorrect number of arguments is provided
     */
    @Override
        public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=4)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        String facultyNumber = t[1];
        String option = t[2];
        String value = t[3];

        return sm.change(facultyNumber, option, value);
    }
}