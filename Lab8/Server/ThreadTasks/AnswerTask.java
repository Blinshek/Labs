package ThreadTasks;

import com.company.Client;
import com.company.Main;
import com.company.Movie;
import javafx.beans.InvalidationListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AnswerTask implements Runnable {
    public static ConcurrentHashMap<Client, ArrayList<ArrayList<?>>> answers = new ConcurrentHashMap<>();

    @Override
    public void run() {
        while (true) {
            if (!answers.isEmpty()) {
                for (Map.Entry<Client, ArrayList<ArrayList<?>>> entry : answers.entrySet()) {
                    for (ArrayList<?> list : entry.getValue()) {
                        try {
                            answer(list, entry.getKey().getClientChan());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                answers.clear();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.yield();
        }
    }

    public static void massSync(ArrayList<Movie> bd) {
        for (Client client : Main.clients) {
            addAnswer(client, bd);
        }
    }

    public static void addAnswer(Client client, ArrayList<?> answer) {
        if (answers.get(client) != null) {
            answers.get(client).add(answer);
        } else {
            ArrayList<ArrayList<?>> arrayList = new ArrayList<>();
            arrayList.add(answer);
            answers.put(client, arrayList);
        }
    }

    private void answer(ArrayList<?> arrayList, SocketChannel clientChan) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < 4; i++) baos.write(0);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(arrayList);
        oos.close();
        final ByteBuffer wrap = ByteBuffer.wrap(baos.toByteArray());
        wrap.putInt(0, baos.size() - 4);
        clientChan.write(wrap);
        System.out.println("Ответили клиенту " + clientChan.getRemoteAddress() + ", " + arrayList.get(0).getClass());
    }
}
