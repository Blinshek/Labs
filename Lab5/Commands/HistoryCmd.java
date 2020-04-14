package Commands;

import com.company.Command;
import com.company.Main;
import com.company.Receiver;

public class HistoryCmd implements Command {
    public final static String description = "выводит историю выполненных команд";
    public final static String cmdName = "/history";

    public HistoryCmd() {}

    @Override
    public void execute() {
        new Receiver(Main.moviesLib).showCommandHistory();
    }

    @Override
    public String toString() {
        return "Show command history";
    }
}