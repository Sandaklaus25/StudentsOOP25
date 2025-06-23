package commands;

import commands.interfaces.Command;
import models.Discipline;
import models.DisciplineManager;
import models.FileManager;
import models.StudentsManager;
import exceptions.InsufficientArgumentsException;
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
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=3)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");

        String facultyNumber = t[1];
        Discipline discipline;
        try
        {discipline = DisciplineManager.getDisciplineByName(t[2]);}
        catch (ClassNotFoundException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        return StudentsManager.getInstance().enrollIn(facultyNumber, discipline);
    }
}