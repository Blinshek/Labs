package Commands;

import Enums.CmdMod;

public class HistoryCmd extends Command {
    public final static String description = "выводит историю выполненных команд";
    public final static String cmdName = "/history";

    public HistoryCmd(){
        super(CmdMod.PUBLIC);
    }

    @Override
    public void execute() { }

    @Override
    public String toString() {
        return "Show command history";
    }
}