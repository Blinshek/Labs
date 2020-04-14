package Commands;

import com.company.Command;
import com.company.Main;
import com.company.Receiver;

public class InfoCmd implements Command {
    public final static String description = "выводит информацию о коллекции";
    public final static String cmdName = "/info";

    public InfoCmd() {
    }

    @Override
    public void execute() {
        new Receiver(Main.moviesLib).info();
    }

    @Override
    public String toString() {
        return "Show info about collection";
    }
}