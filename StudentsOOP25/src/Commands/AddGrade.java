package Commands;

import Commands.Interfaces.Command;
import Exceptions.InsufficientArgumentsException;
import Models.*;

public class AddGrade implements Command {
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
            discipline = DisciplineManager.getDisciplineByString(t[2]);
        }catch (ClassNotFoundException e)
        {return e.getMessage();}
        return sm.addGrade(facultyNumber,discipline,grade);
    }
}
