package Commands;

import Enums.CmdMod;
import com.company.Client;

import java.io.Serializable;

public abstract class Command implements Serializable {
    private static final long serialVersionUID = 2L;
    public final CmdMod modifier;
    private Client owner;

    protected Command(CmdMod modifier){
        this.modifier = modifier;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public Client getOwner() {
        return owner;
    }

    abstract public void execute();
}