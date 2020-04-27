package Commands;

import com.company.Command;

public class HistoryCmd implements Command {
    public final static String description = "выводит историю выполненных команд";
    public final static String cmdName = "/history";

    @Override
    public void execute() { }

    @Override
    public String toString() {
        return "Show command history";
    }
}