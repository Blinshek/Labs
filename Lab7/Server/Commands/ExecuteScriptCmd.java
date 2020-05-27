package Commands;

import Enums.CmdMod;
import com.company.Receiver;

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
        Receiver.executeScript(filename, getOwner());
    }

    @Override
    public String toString() {
        return "Execute script";
    }
}
