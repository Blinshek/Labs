package Commands;

import Enums.CmdMod;
import com.company.Receiver;

import java.sql.SQLException;

public class RemoveKeyCmd extends AbsCommand {
    public final static String description = "удаляет элемент коллекции с заданным ключом";
    public final static String cmdName = "/remove_key";

    private int k;

    public RemoveKeyCmd() {
        super(CmdMod.PRIVATE);
    }

    public RemoveKeyCmd(int k) {
        super(CmdMod.PRIVATE);
        this.k = k;
    }

    public int getK() {
        return k;
    }

    @Override
    public void execute() {
        try {
            Receiver.removeById(k, getOwner());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Remove by key";
    }
}
