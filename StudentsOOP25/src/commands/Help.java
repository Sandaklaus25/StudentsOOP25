package commands;

import commands.interfaces.Command;
import models.FileManager;
import exceptions.InsufficientArgumentsException;
/**
 * Command implementation for displaying comprehensive system help and command reference.
 * <p>
 * The Help command provides users with a complete reference guide to all available
 * commands in the student management system. It displays command syntax, descriptions,
 * and usage examples in Bulgarian language to match the system's locale.
 * </p>
 *
 * @see Command
 *
 * <p><b>Command Format:</b> {@code help}</p>
 * <p><b>Example:</b> {@code help}</p>
 *
 * <p><b>Parameters:</b> None</p>
 *
 * <p><b>Features:</b></p>
 * <ul>
 *   <li>Always available, regardless of system state</li>
 *   <li>Displays all commands with syntax and descriptions</li>
 *   <li>Organized by functional categories</li>
 *   <li>Includes examples for complex commands</li>
 * </ul>
 *
 * <p><b>Command Categories Covered:</b></p>
 * <ul>
 *   <li>File Management: open, close, save, saveas</li>
 *   <li>Student Management: enroll, advance, change, graduate, interrupt, resume</li>
 *   <li>Information Display: print, printall, report, protocol</li>
 *   <li>Discipline Management: enrollin, addgrade (both stored in student)</li>
 *   <li>System Commands: help, exit</li>
 * </ul>
 *
 * <p><b>Return Value:</b> Always returns true indicating successful help display.</p>
 */
public class Help implements Command {
    /**
     *
     * @param t  an array of arguments required for the command
     * @param fm the {@link FileManager} instance to perform file-related operations
     * @return true if command finished
     * @throws InsufficientArgumentsException not used here
     */
    @Override
    public boolean execute(String[] t, FileManager fm) throws InsufficientArgumentsException {
        System.out.println("""
        \t\t===== HELP MENU =====
        open       <file>                             - Opens and loads a file named <file> which is required for the program to function. If it does not exist, a new file is created!
        close                                         - Closes the current file, clears all information, and disables all commands except "open"
        save                                          - Saves the changes made to the currently opened file
        saveas     "<path> <file>"                    - Saves the information as a new file with the name <file> and path <path> specified in quotes!
        help                                          - Displays this help menu
        enroll     <fn> <program> <group> <name...>   - Enrolls a student with name <name...> in the first year of the <program> in group <group> with faculty number <fn>
        advance    <fn>                               - Advances the student with faculty number <fn> to the next year, allowed only if there are no failed courses from the previous year
        change     <fn> <option> <value>              - <option> can be "program", "group", or "year". Transfers the student with faculty number <fn> to a new program, group, or year
        graduate   <fn>                               - Marks the student with faculty number <fn> as graduated if they have successfully passed all enrolled courses
        interrupt  <fn>                               - Marks the student with faculty number <fn> as interrupted
        resume     <fn>                               - Restores student rights for the student with faculty number <fn>
        print      <fn>                               - Prints student information for the student with faculty number <fn>
        printall   <program> <year>                   - Prints information for all students in the specified program and year
        enrollIn   <fn> <course>                      - Enrolls the student with faculty number <fn> in the course <course>
        addgrade   <fn> <course> <grade>              - Adds a grade <grade> for the course <course> to the student with faculty number <fn>
        protocol   <course>                           - Prints protocols for all students enrolled in the course <course>
        report     <fn>                               - Prints an academic report for the student with faculty number <fn>
        exit                                          - Exits the application
        """);
        return true;
    }
}
