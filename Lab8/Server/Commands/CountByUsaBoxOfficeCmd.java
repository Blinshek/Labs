package Commands;

import Enums.CmdMod;
import com.company.Receiver;

public class CountByUsaBoxOfficeCmd extends AbsCommand {
    public final static String description = "выводит количество элементов, значение поля usaBoxOffice которых равно заданному";
    public final static String cmdName = "/count_by_usa_box_office";
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
        Receiver.countByUsaBoxOffice(amount);
    }

    @Override
    public String toString() {
        return "Count by USA box office";
    }
}
