package Commands;

import Commands.Interfaces.Command;
import Models.Discipline;
import Models.DisciplineManager;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;

public class EnrollToDiscipline implements Command {
    @Override
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=3)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");

        String facultyNumber = t[1];
        Discipline discipline;
        try
        {discipline = DisciplineManager.getDisciplineByString(t[2]);}
        catch (ClassNotFoundException e)
        {
            return e.getMessage();
        }
        return sm.enrollIn(facultyNumber, discipline);
    }
}