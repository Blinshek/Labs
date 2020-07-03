package Commands;

import Enums.CmdMod;

public class HelpCmd extends AbsCommand {
    public final static String description = "выводит помощь по командам";
    public final static String cmdName = "Help";

    public HelpCmd(){
        super(CmdMod.PUBLIC);
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Show help";
    }
}
