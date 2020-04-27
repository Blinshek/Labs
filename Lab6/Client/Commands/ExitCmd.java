package Commands;

import com.company.Command;

public class ExitCmd implements Command {
    public final static String description = "завершает программу без сохранения коллекции";
    public final static String cmdName = "/exit";

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Exit";
    }
}