package Commands;

import Enums.CmdMod;

public class InfoCmd extends Command {
    public final static String description = "выводит информацию о коллекции";
    public final static String cmdName = "/info";

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