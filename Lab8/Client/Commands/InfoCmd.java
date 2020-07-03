package Commands;

import Enums.CmdMod;

public class InfoCmd extends AbsCommand {
    public final static String description = "выводит информацию о коллекции";
    public final static String cmdName = "Info";

    public InfoCmd(){
        super(CmdMod.PUBLIC);
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Show info about collection";
    }
}