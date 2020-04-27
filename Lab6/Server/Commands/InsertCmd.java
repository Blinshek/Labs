package Commands;

import com.company.Command;
import com.company.Movie;
import com.company.Receiver;

import java.io.Serializable;

public class InsertCmd implements Command, Serializable {
    public final static String description = "добавляет новый элемент с заданным ключом";
    public final static String cmdName = "/insert";
    private int k;
    private Movie movie;

    public InsertCmd(int k, Movie movie) {
        this.k = k;
        this.movie = movie;
    }

    public int getK() {
        return k;
    }

    @Override
    public void execute() {
        Receiver.insertKey(k, movie);
    }

    @Override
    public String toString() {
        return "Insert new element by key";
    }
}