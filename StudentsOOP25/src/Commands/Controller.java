package Commands;

import Commands.Interfaces.Command;
import Commands.Interfaces.CommandLineInterface;
import Models.FileManager;
import Models.StudentsManager;

import java.util.HashMap;

public class Controller implements CommandLineInterface {
    private final HashMap<String, Command> commands;
    private final StudentsManager students;
    private final FileManager fm;

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
        if(command == null) {return null;}
        else {
            try {
                return (command.execute(inputTokens, students, fm));
            } catch (Exception e) {
                return (e.getMessage());
            }
        }
    }

      private Command getCommand(String[] input) {
        if (commands.containsKey(input[0].toLowerCase()))
            return commands.get(input[0]);
        return null;
    }
}
