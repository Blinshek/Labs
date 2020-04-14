package com.company;

public class Invoker {
    private Command command;

    public Invoker(){}

    public void setCommand(Command command) {
        this.command = command;
    }
    public void executeCommand(){
        this.command.execute();
        CommandHistory.add(command);
    }
}