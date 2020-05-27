package Commands;

import Enums.CmdMod;
import com.company.Receiver;

public class InfoCmd extends Command {
    public final static String description = "выводит информацию о коллекции";
    public final static String cmdName = "/info";

    public InfoCmd(){
        super(CmdMod.PUBLIC);
    }

    @Override
    public void execute() {
        Receiver.info();
    }

    @Override
    public String toString() {
        return "Show info about collection";
    }
}