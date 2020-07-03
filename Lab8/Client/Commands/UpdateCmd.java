package Commands;

import Enums.CmdMod;
import com.company.Movie;

import java.util.ArrayList;

public class UpdateCmd extends AbsCommand {
    public final static String description = "обновляет значение элемента коллекции, id которого равен заданному";
    public final static String cmdName = "Update";
    private ArrayList<Movie> updatedMovies;

    public UpdateCmd(ArrayList<Movie> updatedMovies) {
        super(CmdMod.PRIVATE);
        this.updatedMovies = updatedMovies;
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Update element by id";
    }
}