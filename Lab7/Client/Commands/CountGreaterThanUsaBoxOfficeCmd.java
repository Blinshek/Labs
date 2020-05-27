package Commands;

import Enums.CmdMod;

public class CountGreaterThanUsaBoxOfficeCmd extends Command {
    public final static String description = "выводит количество элементов, значение поля usaBoxOffice которых больше заданного";
    public final static String cmdName = "/count_greater_than_usa_box_office";
    private float amount;

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
