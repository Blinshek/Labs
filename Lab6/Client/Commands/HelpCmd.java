package Commands;

import com.company.Command;

public class HelpCmd implements Command {
    public final static String description = "выводит помощь по командам";
    public final static String cmdName = "/help";

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Show help";
    }
}
