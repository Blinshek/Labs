package Commands;

import com.company.Command;
import com.company.Main;
import com.company.Receiver;

public class ExitCmd implements Command {
    public final static String description = "завершает программу без сохранения коллекции";
    public final static String cmdName = "/exit";

    public ExitCmd() {
    }

    @Override
    public void execute() {
        new Receiver(Main.moviesLib).exit();
    }

    @Override
    public String toString() {
        return "Exit";
    }
}