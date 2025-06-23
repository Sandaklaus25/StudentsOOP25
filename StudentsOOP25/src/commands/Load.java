package commands;

import commands.interfaces.Command;
import models.FileManager;
import exceptions.InsufficientArgumentsException;
/**
 * The Load command opens and loads a file containing student data.
 * <p>
 * This command attempts to open a specified file for data operations. If the file
 * doesn't exist, it does NOT create a new one but gives error.
 * Therefore an existing file must be present (it could still be empty).
 * This command is essential for initializing the system's data operations.
 * </p>
 * <p>
 * Command format: open <file>
 * </p>
 */
public class Load implements Command {
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Има грешка при въведения брой аргументи!");
        return fm.openFile(t[1]);
    }
}
