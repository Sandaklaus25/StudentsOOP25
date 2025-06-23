package commands;

import commands.interfaces.Command;
import models.FileManager;
import models.StudentsManager;
import exceptions.InsufficientArgumentsException;
/**
 * Command implementation for generating an academic report for a student.
 * Format: report <facultyNumber>
 */
public class Report implements Command {
    /**
     * Generates an academic report for a student with the given faculty number.
     * The report includes grades and academic status information.
     *
     * @param t  Array containing command tokens where:
     *           t[0] - command name
     *           t[1] - faculty number
     * @param fm Reference to the FileManager
     * @return Academic report for the specified student
     * @throws InsufficientArgumentsException When incorrect number of arguments is provided
     */
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        return StudentsManager.getInstance().report(t[1]);
    }
}
