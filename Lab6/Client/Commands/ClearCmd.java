package Commands;

import com.company.Command;

public class ClearCmd implements Command {

    public final static String description = "очищает коллекцию";
    public final static String cmdName = "/clear";

    public ClearCmd() {
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Clear collection";
    }
}