package Commands;

import Enums.CmdMod;

public class CountGreaterThanUsaBoxOfficeCmd extends AbsCommand {
    public final static String description = "выводит количество элементов, значение поля usaBoxOffice которых больше заданного";
    public final static String cmdName = "Count greater than USA box office";
    private float amount;

    public CountGreaterThanUsaBoxOfficeCmd() {
        super(CmdMod.PUBLIC);
    }

    public CountGreaterThanUsaBoxOfficeCmd(float amount) {
        super(CmdMod.PUBLIC);
        this.amount = amount;
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Count greater than USA box office";
    }
}
