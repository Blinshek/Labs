package Commands;

import Enums.CmdMod;
import com.company.Receiver;

public class CountByUsaBoxOfficeCmd extends Command {
    public final static String description = "выводит количество элементов, значение поля usaBoxOffice которых равно заданному";
    public final static String cmdName = "/count_by_usa_box_office";
    private float amount;

    public CountByUsaBoxOfficeCmd(float amount) {
        super(CmdMod.PUBLIC_ONLY);
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
