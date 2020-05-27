package Commands;

import Enums.CmdMod;

public class ExecuteScriptCmd extends Command {
    public final static String description = "выполняет скрипт";
    public final static String cmdName = "/execute_script";
    private String filename;

    public ExecuteScriptCmd(String filename) {
        super(CmdMod.PUBLIC);
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
