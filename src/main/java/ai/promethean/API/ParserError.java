package ai.promethean.API;

public class ParserError extends RuntimeException {
    public ParserError(String message) {
        super(message);
    }
}
