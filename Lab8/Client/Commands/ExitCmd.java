package Commands;

import Enums.CmdMod;

public class ExitCmd extends AbsCommand {
    public final static String description = "завершает программу и сохраняет коллекцию";
    public final static String cmdName = "/exit";

    public ExitCmd(){
        super(CmdMod.PUBLIC);
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Exit";
    }
}