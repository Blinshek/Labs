package Commands;

import Enums.CmdMod;
import com.company.Receiver;

import java.sql.SQLException;

public class ClearCmd extends AbsCommand {
    public final static String description = "очищает коллекцию";
    public final static String cmdName = "Clear";

    public ClearCmd() {
        super(CmdMod.PRIVATE);
    }

    @Override
    public void execute() {
        try {
            Receiver.clearCollection(getOwner());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Clear collection";
    }
}