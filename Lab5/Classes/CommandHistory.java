package com.company;

public final class CommandHistory {
    private static Command[] history = new Command[15];
    private static int commandCnt = 0;

    public static void add(Command newCommand) {
        if (commandCnt < history.length)
            commandCnt++;

        if (commandCnt == history.length && history.length - 1 >= 0)
            System.arraycopy(history, 1, history, 0, history.length - 1);
        history[commandCnt - 1] = newCommand;
    }

    public static void show() {
        if(commandCnt > 0) {
            System.out.println("История команд:");
            for (int i = 0; i < commandCnt; i++) {
                System.out.println("\t" + (i + 1) + ") " + history[i].toString());
            }
        }
    }
}