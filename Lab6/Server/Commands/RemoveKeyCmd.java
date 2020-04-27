package Commands;

import com.company.Command;
import com.company.Main;
import com.company.Receiver;

public class RemoveKeyCmd implements Command {
    public final static String description = "удаляет элемент коллекции с заданным ключом";
    public final static String cmdName = "/remove_key";

    private int k;

    public RemoveKeyCmd(int k) {
        this.k = k;
    }

    public int getK() {
        return k;
    }

    @Override
    public void execute() {
        Receiver.removeByKey(k);
    }

    @Override
    public String toString() {
        return "Remove by key";
    }
}
