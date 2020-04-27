package Exceptions;

public class ScriptParseException extends Exception {
    private String message;
    private int line;

    public ScriptParseException(String message, int line) {
        this.message = message;
        this.line = line + 1;
    }

    public String getMessage() {
        return "Строка " + this.line + ": " + this.message;
    }
}