package Commands;

import Enums.CmdMod;
import com.company.Receiver;

import java.sql.SQLException;

public class RemoveGreaterKeyCmd extends AbsCommand {
    public final static String description = "удаляет элементы коллекции, чей ключ больше заданного";
    public final static String cmdName = "/remove_greater_key";
    private int k;

    public RemoveGreaterKeyCmd() {
        super(CmdMod.PRIVATE);
    }

    public RemoveGreaterKeyCmd(int k) {
        super(CmdMod.PRIVATE);
        this.k = k;
    }

    @Override
    public void execute() {
        try {
            Receiver.removeGreaterId(k, getOwner());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Remove greater by key";
    }
}