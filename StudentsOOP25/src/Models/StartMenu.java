package Models;

import java.io.File;
import java.util.*;
import java.util.Scanner;

public class StartMenu {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        SpecialtyManager sp = SpecialtyManager.getInstance();
        StudentsManager sm = StudentsManager.getInstance();
        FileManager fm = new FileManager();
        System.out.println("\t\t(Използвай \"help\" за помощ)");
        while (true) {

            System.out.print(">> Изчаква се команда: ");
            input = scanner.nextLine();
            String[] inputTokens = input.split("\\s+", 5);    //removes all blank spaces

            inputTokens[0] = inputTokens[0].toLowerCase();
            boolean commandCheck = !fm.isLoaded() && !(inputTokens[0].equals("open") || inputTokens[0].equals("help") || inputTokens[0].equals("exit"));
            if(commandCheck || (inputTokens.length<2 && inputTokens[0].equals("open")))
            {
                System.out.println(" Моля изберете \"open filename.txt\" за да отворите файл и да продължите!\n");
                continue;
            }

            switch (inputTokens[0]) {
                case "open":
                    fm.load(inputTokens[1]);
                    break;
                case "close":
                    fm.close();
                    break;
                case "save":
                    fm.saveTo(fm.getLoadedFile().getName());
                    break;
                case "saveas":
                    fm.saveTo(inputTokens[1]);
                    break;
                case "help":
                    printHelpMenu();
                    break;
                case "enroll":
                    System.out.println(">> Записване на нов студент...");
                    break;
                case "advance":
                    System.out.println(">> Преминаване на студента в следващ курс...");
                    break;
                case "change":
                    System.out.println(">> Промяна на информация за студент...");
                    break;
                case "graduate":
                    System.out.println(">> Дипломиране на студент...");
                    break;
                case "interrupt":
                    System.out.println(">> Прекъсване на обучението на студент...");
                    break;
                case "resume":
                    System.out.println(">> Възобновяване на обучението на студент...");
                    break;
                case "print":
                    System.out.println(">> Извеждане на информация за студент...");
                    break;
                case "printall":
                    System.out.println(">> Извеждане на всички студенти...");
                    break;
                case "enrollin":
                    System.out.println(">> Записване на студент в дисциплина...");
                    break;
                case "addgrade":
                    System.out.println(">> Добавяне на оценка към студент...");
                    break;
                case "protocol":
                    System.out.println(">> Показване на протокол по дисциплина...");
                    break;
                case "report":
                    System.out.println(">> Генериране на справка за студент...");
                    break;
                case "exit":
                    System.out.println(">> Изход от приложението. Довиждане!");
                    return;
                default:
                    System.out.println(">> Невалидна команда. Опитайте отново.");
            }
            afterCommand(scanner);
        }
    }

    public static void printHelpMenu() {
        System.out.println("\n\t\t===== ПОМОЩНО МЕНЮ =====\n");
        System.out.println("open       <file>                             - Отваря и зарежда файл с име <file> без която програмата не работи. Ако не съществува се създава нов!");
        System.out.println("close                                         - Затваря текущия файл и изчиства цялата информация и не може да изпълнява команди освен \"open\"");
        System.out.println("save                                          - Записва направените промени в текущо отворения файл");
        System.out.println("saveas     \"<path> <file>\"                   - Запазва информацията като нов файл с име <file> и път <path> указани в кавички!");
        System.out.println("help                                          - Показва това помощно меню");
        System.out.println("enroll     <fn> <program> <group> <name>      - Записване на студент с име <name> в 1 курс на специалност <program> в група <group> и с факултетен номер <fn>");
        System.out.println("advance    <fn>                               - Записва студент с факултетен номер <fn> в следващ курс");
        System.out.println("change     <fn> <option> <value>              - <option> е едно \"program\",\"group\",\"year\". Прехвърля студент с факултетен номер <fn> в нова специалност(program), група(group) или курс(year)");
        System.out.println("graduate   <fn>                               - Отбелязва студента с факултетен номер <fn> като завършил ако е положил успешно всички записани предмети");
        System.out.println("interrupt  <fn>                               - Маркира студента с факултетен номер <fn> като прекъснал");
        System.out.println("resume     <fn>                               - Възстановява студентските права на студента с факултетен номер <fn>");
        System.out.println("print      <fn>                               - Извежда справка за студента с факултетен номер <fn>");
        System.out.println("printall   <program> <year>                   - Извежда справка за всички студенти в дадена специалност <program> и курс <year>");
        System.out.println("enrollIn   <fn> <course>                      - Записва студент с факултетен номер <fn> в дисциплина <course>");
        System.out.println("addgrade   <fn> <course> <grade>              - Добавя оценка <grade> по дисциплина <course> на студента с факултетен номер <fn>");
        System.out.println("protocol   <course>                           - Извежда протоколи за всички студенти, записани в дадена дисциплина <course>");
        System.out.println("report     <fn>                               - Извежда академична справка за студент с факултетен номер <fn>");
        System.out.println("exit                                          - Изход от приложението");
    }

    public static void afterCommand(Scanner scanner) {
        for (int i = 0; i < 3; ++i) System.out.println();
    }   //clears screen and breaks between commands
}
