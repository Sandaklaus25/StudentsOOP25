package Commands;

import Commands.Interfaces.Command;
import Models.FileManager;
import Models.StudentsManager;
import Exceptions.InsufficientArgumentsException;
/**
 * The Help command displays information about all commands.
 * <p>
 * When executed, this command provides a comprehensive help menu that lists all
 * commands with their syntax and a brief description of their functionality.
 * </p>
 * <p>
 * Command format: help
 * </p>
 */
public class Help implements Command {
    @Override
    public String execute(String[] t, StudentsManager sm, FileManager fm) throws InsufficientArgumentsException {
        return """
            \t\t===== ПОМОЩНО МЕНЮ =====
            open       <file>                             - Отваря и зарежда файл с име <file> без която програмата не работи. Ако не съществува се създава нов!
            close                                         - Затваря текущия файл и изчиства цялата информация и не може да изпълнява команди освен "open"
            save                                          - Записва направените промени в текущо отворения файл
            saveas     "<path> <file>"                    - Запазва информацията като нов файл с име <file> и път <path> указани в кавички!
            help                                          - Показва това помощно меню
            enroll     <fn> <program> <group> <name>      - Записване на студент с име <name> в 1 курс на специалност <program> в група <group> и с факултетен номер <fn>
            advance    <fn>                               - Записва студент с факултетен номер <fn> в следващ курс. Позвелно е да премине в следващ курс стига и да няма неиздържани предмети от миналият курс
            change     <fn> <option> <value>              - <option> е едно "program","group","year". Прехвърля студент с факултетен номер <fn> в нова специалност(program), група(group) или курс(year)
            graduate   <fn>                               - Отбелязва студента с факултетен номер <fn> като завършил ако е положил успешно всички записани предмети
            interrupt  <fn>                               - Маркира студента с факултетен номер <fn> като прекъснал
            resume     <fn>                               - Възстановява студентските права на студента с факултетен номер <fn>
            print      <fn>                               - Извежда справка за студента с факултетен номер <fn>
            printall   <program> <year>                   - Извежда справка за всички студенти в дадена специалност <program> и курс <year>
            enrollIn   <fn> <course>                      - Записва студент с факултетен номер <fn> в дисциплина <course>
            addgrade   <fn> <course> <grade>              - Добавя оценка <grade> по дисциплина <course> на студента с факултетен номер <fn>
            protocol   <course>                           - Извежда протоколи за всички студенти, записани в дадена дисциплина <course>
            report     <fn>                               - Извежда академична справка за студент с факултетен номер <fn>
            exit                                          - Изход от приложението
            """;
    }
}
