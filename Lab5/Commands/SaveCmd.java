package Commands;

import com.company.Command;
import com.company.Main;
import com.company.MoviesLib;
import com.company.Receiver;

public class SaveCmd implements Command {
    public final static String description = "сохраняет коллекцию в файл";
    public final static String cmdName = "/save";

    private MoviesLib collection;

    public SaveCmd(MoviesLib collection) {
        this.collection = collection;
    }

    @Override
    public void execute() {
        try {
            new Receiver(Main.moviesLib).saveCollection(collection);
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении коллекции");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Save collection";
    }
}