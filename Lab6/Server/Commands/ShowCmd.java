package Commands;

import com.company.Command;
import com.company.Receiver;

public class ShowCmd implements Command {
    public final static String description = "выводит все элементы коллекции";
    public final static String cmdName = "/show";

    @Override
    public void execute() {
        Receiver.showCollection();
    }

    @Override
    public String toString() {
        return "Show collection";
    }
}