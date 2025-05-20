package Commands;

import Commands.Interfaces.Command;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;
/**
 * Command implementation for displaying information about a specific student.
 * Format: print <facultyNumber>
 */
public class Print implements Command {
    /**
     * Displays detailed information about a specific student.
     *
     * @param t Array containing command tokens where:
     *          t[0] - command name
     *          t[1] - faculty number
     * @param sm Reference to the StudentsManager
     * @param fm Reference to the FileManager
     * @return Formatted information about the student
     * @throws InsufficientArgumentsException When incorrect number of arguments is provided
     */
    @Override
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        return sm.print(t[1]);
    }
}
