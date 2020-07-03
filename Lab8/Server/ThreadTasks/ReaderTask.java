package ThreadTasks;

import Commands.AbsCommand;

import com.company.Client;
import com.company.Main;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ReaderTask implements Runnable {
    private final ByteBuffer lengthByteBuffer = ByteBuffer.wrap(new byte[4]);
    private ByteBuffer dataByteBuffer = null;
    private boolean readLength = true;

    private SocketChannel clientChan;
    private Client client;

    public ReaderTask(Client client) {
        this.client = client;
        this.clientChan = client.getClientChan();
    }

    @Override
    public void run() {
        while (true) {
            if (clientChan.isConnected()) {
                try {
                    recv(clientChan);
                    AbsCommand inpAbsCommand = recv(clientChan);
                    ExecuteTask.addTask(client, inpAbsCommand);
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                    Main.clients.remove(client);
                    break;
                }
            }
            Thread.yield();
        }
    }

    private AbsCommand recv(SocketChannel clientChan) throws IOException, ClassNotFoundException {
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
                final AbsCommand ret = (AbsCommand) ois.readObject();
                ret.setOwner(client);
                // clean up
                dataByteBuffer = null;
                readLength = true;
                System.out.println("Получена команда " + ret + "\n от " + client);
                return ret;
            }
        }
        return null;
    }
}