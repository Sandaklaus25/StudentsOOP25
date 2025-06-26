package commands;

import interfaces.Command;
import interfaces.CommandLineInterface;
import models.FileManager;

import java.util.Arrays;
import java.util.EnumMap;
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
    private final EnumMap<CommandsEnum, Command> commands;
    /**
     * Initializes the controller with all available commands.
     */
    public Controller() {
        commands = new EnumMap<>(CommandsEnum.class);
        commands.put(CommandsEnum.ENROLL, new Enroll());
        commands.put(CommandsEnum.ADVANCE, new Advance());
        commands.put(CommandsEnum.CHANGE, new Change());
        commands.put(CommandsEnum.GRADUATE, new Graduate());
        commands.put(CommandsEnum.INTERRUPT, new Interrupt());
        commands.put(CommandsEnum.RESUME, new Resume());
        commands.put(CommandsEnum.PRINT, new Print());
        commands.put(CommandsEnum.PRINTALL, new PrintAll());
        commands.put(CommandsEnum.ENROLLIN, new EnrollToDiscipline());
        commands.put(CommandsEnum.ADDGRADE, new AddGrade());
        commands.put(CommandsEnum.PROTOCOL, new Protocol());
        commands.put(CommandsEnum.REPORT, new Report());
        commands.put(CommandsEnum.OPEN, new Load());
        commands.put(CommandsEnum.CLOSE, new Close());
        commands.put(CommandsEnum.SAVE, new Save());
        commands.put(CommandsEnum.SAVEAS, new SaveAs());
        commands.put(CommandsEnum.HELP, new Help());
        commands.put(CommandsEnum.EXIT, new Exit());
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
    public boolean processUserInput(String input) {
        Command command;
        String[] inputTokens = input.trim().split("\\s+", 10);
        inputTokens[0] = inputTokens[0].toLowerCase();

        boolean commandCheck = !FileManager.getInstance().isLoaded()
                && !(inputTokens[0].equals(CommandsEnum.OPEN.getCommandName())
                || inputTokens[0].equals(CommandsEnum.HELP.getCommandName())
                || inputTokens[0].equals(CommandsEnum.EXIT.getCommandName()));

        if(commandCheck)
        {
            System.out.println(" Please input \""+CommandsEnum.OPEN.getCommandName()+" filename.txt\" to load existing file and continue!\n");
            return false;
        }

        command = getCommandByString(inputTokens);
        if(command == null) {throw new NullPointerException();}
        else {
            try {
                return (command.execute(inputTokens));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
    }

    /**
     * Retrieves the appropriate command based on the input tokens.
     * The command is retrieved through CommandsEnum model.
     * @see CommandsEnum
     *
     * @param input Array containing the parsed input tokens
     *              input[0] - command name
     * @return Command object corresponding to the requested command, or null if not found
     */
      private Command getCommandByString(String[] input) {
          CommandsEnum commandEnum = CommandsEnum.fromString(input[0]);
          if (commandEnum != null && commands.containsKey(commandEnum)) {
              return commands.get(commandEnum);
          }
          return null;
    }
}
