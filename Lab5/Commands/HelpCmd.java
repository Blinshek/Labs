package Commands;

import com.company.Command;
import com.company.Main;
import com.company.Receiver;

public class HelpCmd implements Command {
    public final static String description = "выводит помощь по командам";
    public final static String cmdName = "/help";
    public HelpCmd(){}

    @Override
    public void execute() {
        new Receiver(Main.moviesLib).help();
    }

    @Override
    public String toString() {
        return "Show help";
    }
}
