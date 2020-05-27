package Commands;

import Enums.CmdMod;
import com.company.Movie;

public class UpdateCmd extends Command {
    public final static String description = "обновляет значение элемента коллекции, id которого равен заданному";
    public final static String cmdName = "/update";
    private int id;
    private Movie movie;

    public UpdateCmd(int id, Movie movie) {
        super(CmdMod.PRIVATE);
        this.id = id;
        this.movie = movie;
    }

    public int getId() {
        return id;
    }

    @Override
    public void execute() { }

    @Override
    public String toString() {
        return "Update element by id";
    }
}