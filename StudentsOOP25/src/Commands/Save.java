package Commands;

import Commands.Interfaces.Command;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;
/**
 * The Save command writes the current state of university data to the currently open file.
 * <p>
 * This command persists all changes made to university data records in the currently
 * active file without changing the file name or location.
 * </p>
 * <p>
 * Command format: save
 * </p>
        */
public class Save implements Command {
    @Override
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        return fm.writeFile(fm.getLoadedFile().getName());
    }
}
