package Commands;

import Enums.CmdMod;
import com.company.Movie;
import com.company.Receiver;

import java.sql.SQLException;

public class InsertCmd extends AbsCommand {
    public final static String description = "добавляет новый элемент в коллекцию";
    public final static String cmdName = "/insert";
    private Movie movie;

    public InsertCmd() {
        super(CmdMod.PRIVATE);
    }

    public InsertCmd(Movie movie) {
        super(CmdMod.PRIVATE);
        this.movie = movie;
    }

    @Override
    public void execute() {
        try {
            Receiver.insert(movie, getOwner());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Insert new element";
    }
}