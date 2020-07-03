package Commands;

import Enums.CmdMod;
import com.company.Client;

public class LogoutCmd extends AbsCommand {
    public final static String description = "выйти из учётной записи";
    public final static String cmdName = "/logout";

    public LogoutCmd(Client user) {
        super(CmdMod.PRIVATE);
        setOwner(user);
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Logout";
    }
}
