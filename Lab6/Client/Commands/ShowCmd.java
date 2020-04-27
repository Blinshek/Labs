package Commands;

import com.company.Command;

public class ShowCmd implements Command {
    public final static String description = "выводит все элементы коллекции";
    public final static String cmdName = "/show";

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Show collection";
    }
}
