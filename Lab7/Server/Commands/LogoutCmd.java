package Commands;

import Enums.CmdMod;
import com.company.Client;
import com.company.Receiver;

import java.sql.SQLException;

public class LogoutCmd extends Command {
    public final static String description = "выйти из учётной записи";
    public final static String cmdName = "/logout";

    public LogoutCmd(Client user) {
        super(CmdMod.PRIVATE);
        setOwner(user);
    }

    @Override
    public void execute() {
        Receiver.logout(getOwner());
    }

    @Override
    public String toString() {
        return "Login/Registration";
    }
}