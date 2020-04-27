package Commands;

import com.company.Command;

public class CountByUsaBoxOfficeCmd implements Command {
    public final static String description = "выводит количество элементов, значение поля usaBoxOffice которых равно заданному";
    public final static String cmdName = "/count_by_usa_box_office";
    private float amount;

    public CountByUsaBoxOfficeCmd(float amount) {
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
