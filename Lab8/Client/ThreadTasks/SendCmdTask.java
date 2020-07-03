package ThreadTasks;

import Commands.AbsCommand;
import com.company.CommandHistory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class SendCmdTask implements Runnable {
    private static ArrayList<AbsCommand> commands = new ArrayList<>();
    private Socket clientSocket;

    public SendCmdTask(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        while (true) {
            if (!commands.isEmpty()) {
                for (AbsCommand cmd : commands) {
                    try {
                        send(cmd);
                        CommandHistory.add(cmd);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                commands.clear();
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addCmd(AbsCommand command) {
        commands.add(command);
    }

    public void send(AbsCommand inpAbsCommand) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < 4; i++) baos.write(0);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(inpAbsCommand);
        oos.close();
        final ByteBuffer wrap = ByteBuffer.wrap(baos.toByteArray());
        wrap.putInt(0, baos.size() - 4);
        clientSocket.getOutputStream().write(wrap.array());
    }
}
