package Commands;

import com.company.Command;
import com.company.Main;
import com.company.Receiver;

public class HelpCmd implements Command {
    public final static String description = "выводит помощь по командам";
    public final static String cmdName = "/help";

    @Override
    public void execute() {
        Receiver.help();
    }

    @Override
    public String toString() {
        return "Show help";
    }
}
