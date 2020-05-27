package Commands;

import Enums.CmdMod;

public class HelpCmd extends Command {
    public final static String description = "выводит помощь по командам";
    public final static String cmdName = "/help";

    public HelpCmd(){
        super(CmdMod.VARIATE);
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Show help";
    }
}
