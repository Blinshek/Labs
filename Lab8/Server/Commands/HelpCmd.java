package Commands;

import Enums.CmdMod;
import com.company.Receiver;

public class HelpCmd extends AbsCommand {
    public final static String description = "выводит помощь по командам";
    public final static String cmdName = "/help";

    public HelpCmd(){
        super(CmdMod.VARIATE);
    }

    @Override
    public void execute() {
        Receiver.help(getOwner());
    }

    @Override
    public String toString() {
        return "Show help";
    }
}
