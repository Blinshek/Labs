package Commands;

import com.company.Command;
import com.company.Main;
import com.company.Receiver;

public class MinByUsaBoxOfficeCmd implements Command {
    public final static String description = "выводит фильм с наименьшими кассовыми сборами в США";
    public final static String cmdName = "/min_by_usa_box_office";

    @Override
    public void execute() {
        Receiver.minByUsaBoxOffice();
    }

    @Override
    public String toString() {
        return "Print with min USA box office";
    }
}
