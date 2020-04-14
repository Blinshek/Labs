package Commands;

import com.company.Command;
import com.company.Main;
import com.company.Receiver;

public class ShowCmd implements Command {
    public final static String description = "выводит все элементы коллекции";
    public final static String cmdName = "/show";

    public ShowCmd() {
    }

    @Override
    public void execute() {
        new Receiver(Main.moviesLib).showCollection();
    }

    @Override
    public String toString() {
        return "Show collection";
    }
}
