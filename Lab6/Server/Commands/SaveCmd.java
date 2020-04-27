package Commands;

import com.company.Command;
import com.company.MovieMap;
import com.company.Receiver;

import javax.xml.bind.JAXBException;

public class SaveCmd implements Command {
    public final static String description = "сохраняет коллекцию в файл";
    public final static String cmdName = "/save";

    public SaveCmd() {

    }

    @Override
    public void execute() {
        try {
            Receiver.saveCollection();
        } catch (JAXBException e) {
            System.out.println("Ошибка при сохранении коллекции");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Save collection";
    }
}