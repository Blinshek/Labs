package Commands;

import Enums.CmdMod;

public class ExecuteScriptCmd extends AbsCommand {
    public final static String description = "выполняет скрипт";
    public final static String cmdName = "Execute script";
    private String filename;

    public ExecuteScriptCmd() {
        super(CmdMod.PRIVATE);
    }

    public ExecuteScriptCmd(String filename) {
        super(CmdMod.PRIVATE);
        this.filename = filename;
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Execute script";
    }
}
