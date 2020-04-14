package Commands;

import com.company.Command;
import com.company.Main;
import com.company.Movie;
import com.company.Receiver;

public class InsertCmd implements Command {
    public final static String description = "добавляет новый элемент с заданным ключом";
    public final static String cmdName = "/insert";
    private int k;
    private Movie movie;

    public InsertCmd(int k, Movie movie) {
        this.k = k;
        this.movie = movie;
    }

    @Override
    public void execute() {
        new Receiver(Main.moviesLib).insertKey(k, movie);
    }

    @Override
    public String toString() {
        return "Insert new element by key";
    }
}