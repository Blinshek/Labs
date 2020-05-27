package Commands;

import Enums.CmdMod;
import com.company.Movie;

public class InsertCmd extends Command {
    public final static String description = "добавляет новый элемент";
    public final static String cmdName = "/insert";
    private Movie movie;

    public InsertCmd(Movie movie) {
        super(CmdMod.PRIVATE);
        this.movie = movie;
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Insert new element";
    }
}