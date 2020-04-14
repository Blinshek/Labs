package Commands;

import com.company.Command;
import com.company.Main;
import com.company.Receiver;

public class RemoveGreaterKeyCmd implements Command {
    public final static String description = "удаляет элементы коллекции, чей ключ больше заданного";
    public final static String cmdName = "/remove_greater_key";
    private int k;

    public RemoveGreaterKeyCmd(int k) {
        this.k = k;
    }

    @Override
    public void execute() {
        new Receiver(Main.moviesLib).removeGreaterKey(k);
    }

    @Override
    public String toString() {
        return "Remove greater by key";
    }
}