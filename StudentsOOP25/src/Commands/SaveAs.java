package Commands;

import Commands.Interfaces.Command;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;

public class SaveAs implements Command {
    @Override
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");

        return fm.writeFile(t[1]);
    }
}
