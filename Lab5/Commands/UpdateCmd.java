package Commands;

import com.company.Command;
import com.company.Main;
import com.company.Movie;
import com.company.Receiver;

public class UpdateCmd implements Command {
    public final static String description = "обновляет значение элемента коллекции, id которого равен заданному";
    public final static String cmdName = "/update";
    private int id;
    private Movie movie;

    public UpdateCmd(int id, Movie movie) {
        this.id = id;
        this.movie = movie;
    }

    @Override
    public void execute() {
        new Receiver(Main.moviesLib).updateId(id, movie);
    }

    @Override
    public String toString() {
        return "Update element by id";
    }
}