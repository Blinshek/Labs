package Commands;

import com.company.Command;
import com.company.Main;
import com.company.Movie;
import com.company.Receiver;

public class ReplaceIfGreaterCmd implements Command {
    public final static String description = "заменяет значение по ключу, если новое значение больше старого";
    public final static String cmdName = "/replace_if_greater";
    private Movie movie;
    private int k;

    public ReplaceIfGreaterCmd(int k, Movie movie) {
        this.k = k;
        this.movie = movie;
    }

    @Override
    public void execute() {
        new Receiver(Main.moviesLib).replaceIfGreater(k, movie);
    }

    @Override
    public String toString() {
        return "Replace by key if greater";
    }
}