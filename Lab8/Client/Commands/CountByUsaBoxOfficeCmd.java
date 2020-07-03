package Commands;

import Enums.CmdMod;

public class CountByUsaBoxOfficeCmd extends AbsCommand {
    public final static String description = "выводит количество элементов, значение поля usaBoxOffice которых равно заданному";
    public final static String cmdName = "Count by USA box office";
    private float amount;

    public CountByUsaBoxOfficeCmd() {
        super(CmdMod.PUBLIC);
    }

    public CountByUsaBoxOfficeCmd(float amount) {
        super(CmdMod.PUBLIC);
        this.amount = amount;
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Count by USA box office";
    }
}
