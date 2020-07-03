package Commands;

import Enums.CmdMod;
import com.company.Receiver;

public class CountGreaterThanUsaBoxOfficeCmd extends AbsCommand {
    public final static String description = "выводит количество элементов, значение поля usaBoxOffice которых больше заданного";
    public final static String cmdName = "/count_greater_than_usa_box_office";
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
        Receiver.countGreaterByUsaBoxOffice(amount);
    }

    @Override
    public String toString() {
        return "Count greater than USA box office";
    }
}
