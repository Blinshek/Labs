package Commands;

import com.company.Command;
import com.company.Main;
import com.company.Receiver;

public class ClearCmd implements Command {

    public final static String description = "очищает коллекцию";
    public final static String cmdName = "/clear";

    public ClearCmd() {
    }

    @Override
    public void execute() {
        Receiver.clearCollection();
    }

    @Override
    public String toString() {
        return "Clear collection";
    }
}