package Commands;

import Enums.CmdMod;
import com.company.Movie;

public class InsertCmd extends AbsCommand {
    public final static String description = "добавляет новый элемент";
    public final static String cmdName = "Insert";
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
    }

    @Override
    public String toString() {
        return "Insert new element";
    }
}