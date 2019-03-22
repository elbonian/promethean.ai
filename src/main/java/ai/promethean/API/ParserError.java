package ai.promethean.API;

/**
 * This class allows for error throwing from the Parser component of the System.
 *
 */

public class ParserError extends RuntimeException {
    public ParserError(String message) {
        super(message);
    }
}
