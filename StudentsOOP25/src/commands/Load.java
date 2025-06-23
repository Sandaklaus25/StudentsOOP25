package commands;

import commands.interfaces.Command;
import models.FileManager;
import exceptions.InsufficientArgumentsException;

import java.io.FileNotFoundException;

/**
 * Command implementation for opening and loading student data files.
 * <p>
 * The Load command (mapped to "open") initializes the system by loading student
 * data from a specified file. This command is essential for system operation as
 * most other commands require a loaded file to function. Unlike some systems,
 * this implementation requires the file to exist beforehand.
 * </p>
 *
 * @see Command
 * @see FileManager
 *
 * <p><b>Command Format:</b> {@code open <filename>}</p>
 * <p><b>Example:</b> {@code open students.txt}</p>
 *
 * <p><b>Parameters:</b></p>
 * <ul>
 *   <li>{@code filename} - Name of the file to open (must exist)</li>
 * </ul>
 *
 * <p><b>File Requirements:</b></p>
 * <ul>
 *   <li>File must exist in the file system</li>
 *   <li>File must be readable</li>
 *   <li>File can be empty (will initialize empty system)</li>
 *   <li>File should contain valid program data</li>
 * </ul>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>Exactly 2 arguments required (command + filename)</li>
 *   <li>File must exist (does not create new files)</li>
 *   <li>Only one file can be loaded at a time</li>
 * </ul>
 *
 * <p><b>Effects:</b></p>
 * <ul>
 *   <li>Loads student data into memory</li>
 *   <li>Enables all other system commands</li>
 *   <li>Sets the file as current working file</li>
 *   <li>Validates and parses file content</li>
 * </ul>
 *
 * <p><b>Note:</b> This command does NOT create new files if they don't exist,
 * unlike traditional file systems. An existing file is required.</p>
 */
public class Load implements Command {
    /**
     *
     * @param t  an array of arguments required for the command
     * @param fm the {@link FileManager} instance to perform file-related operations
     * @return true or false depending on command execution
     * @throws InsufficientArgumentsException when incorrect number of arguments provided
     */
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        if(t.length!=2)
            throw new InsufficientArgumentsException("Invalid number of arguments!");
        return fm.openFile(t[1]);
    }
}
