package Commands;

import Enums.CmdMod;
import com.company.Receiver;

import java.sql.SQLException;

public class RemoveLowerKeyCmd extends Command {
    public final static String description = "удаляет элементы коллекции, чей ключ меньше заданного";
    public final static String cmdName = "/remove_lower_key";
    private int k;

    public RemoveLowerKeyCmd(int k) {
        super(CmdMod.PRIVATE);
        this.k = k;
    }

    @Override
    public void execute() {
        try {
            Receiver.removeLowerId(k, getOwner());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Remove lower by key";
    }
}