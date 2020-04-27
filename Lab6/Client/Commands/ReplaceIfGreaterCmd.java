package Commands;

import com.company.Command;
import com.company.Movie;

public class ReplaceIfGreaterCmd implements Command {
    public final static String description = "заменяет значение по ключу, если новое значение больше старого";
    public final static String cmdName = "/replace_if_greater";
    private Movie movie;
    private int k;

    public ReplaceIfGreaterCmd(int k, Movie movie) {
        this.k = k;
        this.movie = movie;
    }

    public int getK() {
        return k;
    }

    @Override
    public void execute() {
    }

    @Override
    public String toString() {
        return "Replace by key if greater";
    }
}