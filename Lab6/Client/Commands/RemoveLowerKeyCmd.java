package Commands;

import com.company.Command;

public class RemoveLowerKeyCmd implements Command {
    public final static String description = "удаляет элементы коллекции, чей ключ меньше заданного";
    public final static String cmdName = "/remove_lower_key";
    private int k;

    public RemoveLowerKeyCmd(int k) {
        this.k = k;
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Remove lower by key";
    }
}