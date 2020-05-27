package Exceptions;

public class CmdValidationException extends Exception{
    private String message;

    public CmdValidationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "Ошибка: " + this.message;
    }
}