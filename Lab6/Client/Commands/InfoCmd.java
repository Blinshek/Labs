package Commands;

import com.company.Command;

public class InfoCmd implements Command {
    public final static String description = "выводит информацию о коллекции";
    public final static String cmdName = "/info";

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Show info about collection";
    }
}