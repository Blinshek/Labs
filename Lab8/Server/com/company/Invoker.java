package com.company;

import Commands.AbsCommand;

public class Invoker {
    private AbsCommand absCommand;

    public void setAbsCommand(AbsCommand absCommand) {
        this.absCommand = absCommand;
    }

    public void executeCommand() {
        this.absCommand.execute();
    }
}