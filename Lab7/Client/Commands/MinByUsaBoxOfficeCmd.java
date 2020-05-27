package Commands;

import Enums.CmdMod;

public class MinByUsaBoxOfficeCmd extends Command {
    public final static String description = "выводит фильм с наименьшими кассовыми сборами в США";
    public final static String cmdName = "/min_by_usa_box_office";

    public MinByUsaBoxOfficeCmd(){
        super(CmdMod.PUBLIC);
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Print with min USA box office";
    }
}
