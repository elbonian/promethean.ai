package ai.promethean.API;

public class OutputError extends RuntimeException {
    public OutputError(String message) {
        super(message);
    }
}
