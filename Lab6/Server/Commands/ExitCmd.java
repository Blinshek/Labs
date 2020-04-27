package Commands;

import com.company.Command;
import com.company.Main;
import com.company.Receiver;

public class ExitCmd implements Command {
    public final static String description = "завершает программу и сохраняет коллекцию";
    public final static String cmdName = "/exit";

    @Override
    public void execute() {
        Receiver.exit();
    }

    @Override
    public String toString() {
        return "Exit";
    }
}