package commands;

import commands.interfaces.Command;
import exceptions.InsufficientArgumentsException;
import models.*;
/**
 * Command implementation for adding a grade to a student for a specific discipline.
 * Format: addgrade <facultyNumber> <discipline> <grade>
 */
public class AddGrade implements Command {
    /**
     * Adds a grade to a student for a specific discipline.
     *
     * @param t  Array containing command tokens where:
     *           t[0] - command name
     *           t[1] - faculty number
     *           t[2] - discipline name
     *           t[3] - numeric grade
     * @param fm Reference to the FileManager
     * @return Result message of the operation
     * @throws InsufficientArgumentsException When incorrect number of arguments is provided
     */
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=4)
            throw new InsufficientArgumentsException("Invalid number of arguments!");
        int grade;
        try {
            grade = Integer.parseInt(t[3]);
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid input for grade! Must be an integer between 2 and 6 inclusive!");
            return false;
        }

        String facultyNumber = t[1];
        Discipline discipline;
        try {
            discipline = DisciplineManager.getDisciplineByName(t[2]);
        }catch (ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        return StudentsManager.getInstance().addGrade(facultyNumber,discipline,grade);
    }
}
