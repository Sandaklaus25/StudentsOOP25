package Commands;

import Commands.Interfaces.Command;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;
/**
 * Command implementation for generating an academic report for a student.
 * Format: report <facultyNumber>
 */
public class Report implements Command {
    /**
     * Generates an academic report for a student with the given faculty number.
     * The report includes grades and academic status information.
     *
     * @param t Array containing command tokens where:
     *          t[0] - command name
     *          t[1] - faculty number
     * @param sm Reference to the StudentsManager
     * @param fm Reference to the FileManager
     * @return Academic report for the specified student
     * @throws InsufficientArgumentsException When incorrect number of arguments is provided
     */
    @Override
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        return sm.report(t[1]);
    }
}
