package Commands;

import Commands.Interfaces.Command;
import Models.Discipline;
import Models.DisciplineManager;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;
/**
 * The EnrollToDiscipline command registers a student for a specific course/discipline.
 * <p>
 * This command allows a student with a given faculty number to be enrolled in a specific
 * academic discipline or course.
 * </p>
 * <p>
 * Command format: enrollin <fn> <course>
 * </p>
 */
public class EnrollToDiscipline implements Command {
    @Override
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=3)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");

        String facultyNumber = t[1];
        Discipline discipline;
        try
        {discipline = DisciplineManager.getDisciplineByName(t[2]);}
        catch (ClassNotFoundException e)
        {
            return e.getMessage();
        }
        return sm.enrollIn(facultyNumber, discipline);
    }
}