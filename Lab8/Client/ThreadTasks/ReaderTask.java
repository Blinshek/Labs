package ThreadTasks;

import com.company.Main;
import com.company.Movie;
import javafx.application.Platform;
import javafx.collections.FXCollections;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

public class ReaderTask implements Runnable {
    private final ByteBuffer lengthByteBuffer = ByteBuffer.wrap(new byte[4]);
    private ByteBuffer dataByteBuffer = null;
    private boolean readLength = true;
    private Socket clientSocket;
    private static ArrayList<String> output = new ArrayList<>();

    public ReaderTask(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        while (true) {
            if (clientSocket.isConnected()) {
                try {
                    recv();
                    ArrayList<?> ret = recv();
                    if (ret.get(0) instanceof String) {
                        output.clear();
                        output.addAll((ArrayList<String>) ret);
                    } else if (ret.get(0) instanceof Movie) {
                        Platform.runLater(() -> {
                            Main.mainFormController.setMoviesList(FXCollections.observableArrayList((ArrayList<Movie>) ret));
                        });
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                    break;
                }
            }
            try {
                Thread.sleep(97);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.yield();
        }
    }

    private ArrayList<?> recv() throws IOException, ClassNotFoundException {
        ReadableByteChannel clientChan = Channels.newChannel(clientSocket.getInputStream());
        if (readLength) {
            clientChan.read(lengthByteBuffer);
            if (lengthByteBuffer.remaining() == 0) {
                readLength = false;
                dataByteBuffer = ByteBuffer.allocate(lengthByteBuffer.getInt(0));
                lengthByteBuffer.clear();
            }
        } else {
            clientChan.read(dataByteBuffer);
            if (dataByteBuffer.remaining() == 0) {
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(dataByteBuffer.array()));
                ArrayList<?> ret = (ArrayList<?>) ois.readObject();
                dataByteBuffer = null;
                readLength = true;
                return ret;
            }
        }
        return null;
    }

    public static ArrayList<String> getOutput() {
        int cnt = 0;
        while (output.isEmpty() && cnt < 30) {
            try {
                Thread.sleep(100);
                cnt++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ArrayList<String> out = new ArrayList<>();
        if (output.isEmpty()) {
            out.add("no");
            out.add("Превышено время ожидания сервера");
        } else {
            out.add("yes");
            out.addAll(output);
        }
        output.clear();
        return out;
    }
}