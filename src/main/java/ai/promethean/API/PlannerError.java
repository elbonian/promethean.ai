package ai.promethean.API;

/**
 * This class allows for error throwing from the Parser component of the System.
 *
 */

public class PlannerError extends RuntimeException {
    public PlannerError(String message) {
        super(message);
    }
}
