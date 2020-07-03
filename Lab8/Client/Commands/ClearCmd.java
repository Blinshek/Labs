package Commands;

import Enums.CmdMod;

public class ClearCmd extends AbsCommand {
    public final static String description = "очищает коллекцию";
    public final static String cmdName = "Clear";

    public ClearCmd() {
        super(CmdMod.PRIVATE);
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Clear collection";
    }
}