package Commands;

import Enums.CmdMod;
import com.company.Client;
import com.company.Receiver;

import java.sql.SQLException;

public class RegistrationCmd extends AbsCommand {
    public final static String description = "Зарегистрировать новую учётную запись";
    public final static String cmdName = "/register";
    private Client newUser;


    //newUser содержит пароль и соль
    public RegistrationCmd(Client owner, Client newUser) {
        super(CmdMod.PUBLIC_ONLY);
        setOwner(owner);
        this.newUser = newUser;
    }

    @Override
    public void execute() {
        try {
            Receiver.registration(getOwner(), newUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Client getNewUser() {
        return newUser;
    }

    @Override
    public String toString() {
        return "Registration";
    }
}