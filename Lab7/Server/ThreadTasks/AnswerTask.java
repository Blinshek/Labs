package ThreadTasks;

import com.company.Client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnswerTask implements Runnable {
    public static LinkedHashMap<Client, ArrayList<String>> answers = new LinkedHashMap<>();
    @Override
    public void run() {
        while(true){
            if(!answers.isEmpty()) {
                for (Map.Entry<Client, ArrayList<String>> entry : answers.entrySet()) {
                    try {
                        answer(entry.getValue(), entry.getKey().getClientChan());
                    } catch (IOException e) {
                        e.printStackTrace();
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

    public static void answer(ArrayList<String> list, SocketChannel clientChan) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < 4; i++) baos.write(0);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(list);
        oos.close();
        final ByteBuffer wrap = ByteBuffer.wrap(baos.toByteArray());
        wrap.putInt(0, baos.size() - 4);
        clientChan.write(wrap);
        System.out.println("Ответили клиенту " + clientChan.getRemoteAddress());
    }

    public static void addAnswer(Client client, ArrayList<String> answer) {
        answers.put(client, answer);
    }
}
