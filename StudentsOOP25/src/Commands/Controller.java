package Commands;

import Commands.Interfaces.Command;
import Commands.Interfaces.CommandLineInterface;
import Models.FileManager;
import Models.StudentsManager;

import java.util.HashMap;
/**
 * Main controller class that handles command dispatching and execution.
 * Implements the CommandLineInterface and maintains the command registry.
 */
public class Controller implements CommandLineInterface {
    private final HashMap<String, Command> commands;
    private final StudentsManager students;
    private final FileManager fm;
    /**
     * Initializes the controller with all available commands.
     */
    public Controller() {
        students = StudentsManager.getInstance();
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
     *
     * @param input User input command string
     * @return Result message from the executed command
     */
    @Override
    public String open(String input) {

        Command command;
        String[] inputTokens = input.trim().split("\\s+", 10);
        inputTokens[0] = inputTokens[0].toLowerCase();

        boolean commandCheck = !fm.isLoaded() && !(inputTokens[0].equals("open") || inputTokens[0].equals("help") || inputTokens[0].equals("exit"));
        if(commandCheck)
        {
            return (" Моля изберете \"open filename.txt\" за да заредите файл и да продължите!\n");
        }

        command = getCommand(inputTokens);
        if(command == null) {throw new NullPointerException();}
        else {
            try {
                return (command.execute(inputTokens, students, fm));
            } catch (Exception e) {
                return (e.getMessage());
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
