package Commands;

import Enums.CmdMod;
import com.company.Client;

public class LoginCmd extends Command{
    public final static String description = "войти в учётную запись";
    public final static String cmdName = "/login";
    private Client newUser;

    //юзер уже содержит пароль и соль
    public LoginCmd(Client owner, Client newUser) {
        super(CmdMod.PUBLIC_ONLY);
        setOwner(owner);
        this.newUser = newUser;
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Login";
    }
}
