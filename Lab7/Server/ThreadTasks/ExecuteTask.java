package ThreadTasks;

import Commands.Command;
import Exceptions.CmdValidationException;
import com.company.Client;
import com.company.CmdValidator;
import com.company.Invoker;
import com.company.Receiver;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

public class ExecuteTask extends RecursiveAction {
    public static LinkedHashMap<Client, ArrayList<Command>> requestMap = new LinkedHashMap<>();

    public static void addTask(Client owner, Command cmd) {
        if (owner != null && cmd != null) {
            if (requestMap.containsKey(owner))
                requestMap.get(owner).add(cmd);
            else {
                ArrayList<Command> req = new ArrayList<>();
                req.add(cmd);
                requestMap.put(owner, req);
            }
        }
    }

    @Override
    protected void compute() {
        while (true) {
            if (!requestMap.isEmpty()) {
                System.out.println("смотрим запросы");
                for (Map.Entry<Client, ArrayList<Command>> entry : requestMap.entrySet()) {
                    ArrayList<String> output = new ArrayList<>();
                    for (Command cmd : entry.getValue()) {
                        try {
                            //Command cmd = entry.getValue();
                            System.out.println("На проверке " + cmd + " от " + cmd.getOwner());
                            if (CmdValidator.validateCmd(cmd)) {
                                switch (cmd.modifier) {
                                    case VARIATE:
                                    case PUBLIC: {
                                        executeCmd(cmd);
                                        output = Receiver.getOutput();
                                        break;
                                    }
                                    case PRIVATE: {
                                        if (entry.getKey().isLogged()) {
                                            executeCmd(cmd);
                                            output = Receiver.getOutput();
                                        } else
                                            output.add("Ошибка: у вас нет доступа к данной команде");
                                        break;
                                    }
                                    case PUBLIC_ONLY: {
                                        if (!entry.getKey().isLogged()) {
                                            executeCmd(cmd);
                                            output = Receiver.getOutput();
                                        } else
                                            output.add("Ошибка: у вас нет доступа к данной команде");
                                        break;
                                    }
                                }
                            }
                        } catch (CmdValidationException e) {
                            output.add(e.getMessage());
                        }
                    }
                    AnswerTask.addAnswer(entry.getKey(), output);
                }
                //if (!requestMap.isEmpty())
                    requestMap.clear();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.yield();
        }
    }

    private void executeCmd(Command command) {
        Invoker invoker = new Invoker();
        invoker.setCommand(command);
        invoker.executeCommand();
        /*
        Runnable task = () -> {
            Invoker invoker = new Invoker();
            invoker.setCommand(command);
            invoker.executeCommand();
        };
        Thread thread = new Thread(task);
        thread.start();

         */
    }
}
