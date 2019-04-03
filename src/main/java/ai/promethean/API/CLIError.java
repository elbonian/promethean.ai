package ai.promethean.API;

/**
 * This class allows for error throwing from the CLI component of the System.
 *
 */

public class CLIError extends RuntimeException {
    public CLIError(String message) {
        super(message);
    }
}