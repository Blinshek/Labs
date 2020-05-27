package Commands;

import Enums.CmdMod;

public class ShowCmd extends Command {
    public final static String description = "выводит все элементы коллекции";
    public final static String cmdName = "/show";

    public ShowCmd(){
        super(CmdMod.PUBLIC);
    }
    @Override
    public void execute() { }

    @Override
    public String toString() {
        return "Show collection";
    }
}