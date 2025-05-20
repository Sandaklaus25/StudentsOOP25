package Commands;

import Commands.Interfaces.Command;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;
/**
 * The SaveAs command writes the current state of student data to a new file.
 * <p>
 * This command persists all current student records to a new file with the
 * specified name, allowing the user to create a backup or alternative version
 * of the data.
 * </p>
 * <p>
 * Command format: saveas <filename>.txt
 * </p>
 */
public class SaveAs implements Command {
    @Override
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");

        return fm.writeFile(t[1]);
    }
}
