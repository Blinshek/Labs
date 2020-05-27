package Commands;

import Enums.CmdMod;
import com.company.Receiver;

import java.sql.SQLException;

public class ShowCmd extends Command {
    public final static String description = "выводит все элементы коллекции";
    public final static String cmdName = "/show";

    public ShowCmd(){
        super(CmdMod.PUBLIC);
    }
    @Override
    public void execute() {
        try {
            Receiver.showCollection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Show collection";
    }
}