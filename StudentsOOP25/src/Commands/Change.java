package Commands;

import Commands.Interfaces.Command;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;

public class Change implements Command {
    @Override
        public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=4)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        String facultyNumber = t[1];
        String option = t[2];
        String value = t[3];

        return sm.change(facultyNumber, option, value);
    }
}