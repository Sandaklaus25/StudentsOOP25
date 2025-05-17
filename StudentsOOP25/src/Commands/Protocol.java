package Commands;

import Commands.Interfaces.Command;
import Models.Discipline;
import Models.DisciplineManager;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;

public class Protocol implements Command {
    @Override
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        Discipline discipline;
        try
        {
            discipline = DisciplineManager.getDisciplineByString(t[1]);
        }catch (ClassNotFoundException e)
        {
            return e.getMessage();
        }
        return sm.protocol(discipline);
    }
}
