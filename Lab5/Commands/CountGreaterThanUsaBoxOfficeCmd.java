package Commands;

import com.company.Command;
import com.company.Main;
import com.company.Receiver;

public class CountGreaterThanUsaBoxOfficeCmd implements Command {
    public final static String description = "выводит количество элементов, значение поля usaBoxOffice которых больше заданного";
    public final static String cmdName = "/count_greater_than_usa_box_office";
    private float amount;

    public CountGreaterThanUsaBoxOfficeCmd(float amount) {
        this.amount = amount;
    }

    @Override
    public void execute() {
        new Receiver(Main.moviesLib).countGreaterByUsaBoxOffice(amount);
    }

    @Override
    public String toString() {
        return "Count greater than USA box office";
    }
}
