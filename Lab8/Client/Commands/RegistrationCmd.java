package Commands;

import Enums.CmdMod;
import com.company.Client;

public class RegistrationCmd extends AbsCommand {
    public final static String description = "Зарегистрировать новую учётную запись";
    public final static String cmdName = "/register";
    private Client newUser;

    //юзер уже содержит пароль и соль
    public RegistrationCmd(Client owner, Client newUser) {
        super(CmdMod.PUBLIC_ONLY);
        setOwner(owner);
        this.newUser = newUser;
    }

    @Override
    public void execute() {
    }

    public Client getNewUser() {
        return newUser;
    }

    @Override
    public String toString() {
        return "Registration";
    }
}