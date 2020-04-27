package Commands;

import com.company.Command;
import com.company.Main;
import com.company.Receiver;

public class ExecuteScriptCmd implements Command {
    public final static String description = "выполняет скрипт";
    public final static String cmdName = "/execute_script";
    private String filename;

    public ExecuteScriptCmd(String filename) {
        this.filename = filename;
    }

    @Override
    public void execute() {
        Receiver.executeScript(filename);
    }

    @Override
    public String toString() {
        return "Execute script";
    }
}
