package Exceptions;

public class CommandParseException extends Exception{
    private String message;

    public CommandParseException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "Ошибка: " + this.message;
    }
}