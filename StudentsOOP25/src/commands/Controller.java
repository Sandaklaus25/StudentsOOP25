package commands;

import commands.interfaces.Command;
import commands.interfaces.CommandLineInterface;
import models.FileManager;

import java.util.HashMap;
/**
 * Main controller class that orchestrates command execution and system operations.
 * <p>
 * The Controller serves as the central command dispatcher for the student management system.
 * It maintains a registry of all available commands, handles input parsing, validates
 * system state, and coordinates between commands and the file management system.
 * </p>
 * <p>
 * This class implements the Command Pattern as the invoker, managing the relationship
 * between user input and command execution while enforcing system-wide constraints
 * such as requiring a loaded file for most operations.
 * </p>
 *
 * @see CommandLineInterface
 * @see Command
 * @see FileManager
 *
 * <p><b>Available Commands:</b></p>
 * <ul>
 *   <li>{@code enroll} - Register new students</li>
 *   <li>{@code advance} - Advance students to next year</li>
 *   <li>{@code change} - Modify student attributes</li>
 *   <li>{@code graduate} - Mark students as graduated</li>
 *   <li>{@code interrupt} - Mark students as interrupted</li>
 *   <li>{@code resume} - Resume interrupted students</li>
 *   <li>{@code print} - Display student information</li>
 *   <li>{@code printall} - Display all students in program/year</li>
 *   <li>{@code enrollin} - Enroll student in discipline</li>
 *   <li>{@code addgrade} - Add grades to students</li>
 *   <li>{@code protocol} - Generate discipline protocols</li>
 *   <li>{@code report} - Generate academic reports</li>
 *   <li>{@code open} - Load data file</li>
 *   <li>{@code close} - Close current file</li>
 *   <li>{@code save} - Save changes to current file</li>
 *   <li>{@code saveas} - Save to new file</li>
 *   <li>{@code help} - Display help menu</li>
 *   <li>{@code exit} - Terminate application</li>
 * </ul>
 *
 * <p><b>System State Requirements:</b></p>
 * <ul>
 *   <li>Most commands require a file to be loaded first</li>
 *   <li>Only "open", "help", and "exit" work without loaded file</li>
 *   <li>Commands are case-insensitive</li>
 *   <li>Input is tokenized with whitespace separation</li>
 * </ul>
 */
public class Controller implements CommandLineInterface {
    private final HashMap<String, Command> commands;
    private final FileManager fm;
    /**
     * Initializes the controller with all available commands.
     */
    public Controller() {
        commands = new HashMap<>();
        fm = new FileManager();
        commands.put("enroll", new Enroll());
        commands.put("advance", new Advance());
        commands.put("change", new Change());
        commands.put("graduate", new Graduate());
        commands.put("interrupt", new Interrupt());
        commands.put("resume", new Resume());
        commands.put("print", new Print());
        commands.put("printall", new PrintAll());
        commands.put("enrollin", new EnrollToDiscipline());
        commands.put("addgrade", new AddGrade());
        commands.put("protocol", new Protocol());
        commands.put("report", new Report());
        commands.put("open", new Load());
        commands.put("close", new Close());
        commands.put("save", new Save());
        commands.put("saveas", new SaveAs());
        commands.put("help", new Help());
        commands.put("exit", new Exit());
    }
    /**
     * Processes the input command and executes the corresponding command.
     * Validates that a file is loaded before executing most commands.
     * <p>
     * Input is tokenized with whitespace separation, limited to 10 tokens maximum.
     * Command names are converted to lowercase for case-insensitive matching.
     * </p>
     *
     * @param input User input command string to be parsed and executed
     * @return true if command executes successfully, false if command fails,
     *         stops executing, or any exception occurs during execution
     * @throws NullPointerException if the command is not recognized/found in the command registry
     */
    @Override
    public boolean open(String input) {
        Command command;
        String[] inputTokens = input.trim().split("\\s+", 10);
        inputTokens[0] = inputTokens[0].toLowerCase();

        boolean commandCheck = !fm.isLoaded() && !(inputTokens[0].equals("open") || inputTokens[0].equals("help") || inputTokens[0].equals("exit"));

        if(commandCheck)
        {
            System.out.println(" Please input \"open filename.txt\" to load a file and continue!\n");
            return false;
        }

        command = getCommand(inputTokens);
        if(command == null) {throw new NullPointerException();}
        else {
            try {
                return (command.execute(inputTokens, fm));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
    }
    /**
     * Retrieves the appropriate command based on the input tokens.
     *
     * @param input Array containing the parsed input tokens
     *              input[0] - command name
     * @return Command object corresponding to the requested command, or null if not found
     */
      private Command getCommand(String[] input) {
        if (commands.containsKey(input[0]))
            return commands.get(input[0]);
        return null;
    }
}
