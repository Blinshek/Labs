package Commands;

import Enums.CmdMod;

public class RemoveGreaterKeyCmd extends AbsCommand {
    public final static String description = "удаляет элементы коллекции, чей ключ больше заданного";
    public final static String cmdName = "Remove greater key";
    private int k;

    public RemoveGreaterKeyCmd() {
        super(CmdMod.PRIVATE);
    }


    public RemoveGreaterKeyCmd(int k) {
        super(CmdMod.PRIVATE);
        this.k = k;
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Remove greater by key";
    }
}