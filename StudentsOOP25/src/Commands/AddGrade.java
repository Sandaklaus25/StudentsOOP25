package Commands;

import Commands.Interfaces.Command;
import Exceptions.InsufficientArgumentsException;
import Models.*;
/**
 * Command implementation for adding a grade to a student for a specific discipline.
 * Format: addgrade <facultyNumber> <discipline> <grade>
 */
public class AddGrade implements Command {
    /**
     * Adds a grade to a student for a specific discipline.
     *
     * @param t Array containing command tokens where:
     *          t[0] - command name
     *          t[1] - faculty number
     *          t[2] - discipline name
     *          t[3] - numeric grade
     * @param sm Reference to the StudentsManager
     * @param fm Reference to the FileManager
     * @return Result message of the operation
     * @throws InsufficientArgumentsException When incorrect number of arguments is provided
     */
    @Override
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=4)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        int grade;
        try {
            grade = Integer.parseInt(t[3]);
        }
        catch (NumberFormatException e) {
            throw new NumberFormatException("Невалиден вход за оценка! Оценката трябва да е число между 2 и 6!");
        }

        String facultyNumber = t[1];
        Discipline discipline;
        try {
            discipline = DisciplineManager.getDisciplineByName(t[2]);
        }catch (ClassNotFoundException e)
        {return e.getMessage();}
        return sm.addGrade(facultyNumber,discipline,grade);
    }
}
