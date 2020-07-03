package com.company;

import Commands.AbsCommand;

import java.util.ArrayList;

public final class CommandHistory {
    private static AbsCommand[] history = new AbsCommand[15];
    private static int commandCnt = 0;

    public static void add(AbsCommand newAbsCommand) {
        if (commandCnt < history.length)
            commandCnt++;

        if (commandCnt == history.length && history.length - 1 >= 0)
            System.arraycopy(history, 1, history, 0, history.length - 1);
        history[commandCnt - 1] = newAbsCommand;
    }

    public static ArrayList<String> getHistory() {
        ArrayList<String> output = new ArrayList<>();
        if(commandCnt > 0) {
            output.add(Localizator.historyList.get());
            for (int i = 0; i < commandCnt; i++) {
                output.add("\t" + (i + 1) + ") " + history[i].toString());
            }
        } else
            output.add(Localizator.historyIsEmpty.get());
        return output;
    }
}