package ai.promethean.DataModel;

/**
 * Used to keep track of SystemState changes due to Perturbations. A PerturbationInstance will include a previous SystemState which the system was in before a Perturbation took place, and the Perturbation that caused the state change
 */
public class PerturbationInstance {
    private SystemState previousSystemState;
    private Perturbation perturbation;

    /**
     * Instantiates a new Perturbation instance
     *
     * @param _prevSystemState The previous SystemState
     * @param _perturbation    The Perturbation
     */
    public PerturbationInstance(SystemState _prevSystemState, Perturbation _perturbation){
        setPerturbation(_perturbation);
        setPreviousSystemState(_prevSystemState);
    }

    /**
     * Sets Perturbation
     *
     * @param perturbation The Perturbation
     */
    public void setPerturbation(Perturbation perturbation) {
        this.perturbation = perturbation;
    }

    /**
     * Sets previous SystemState
     *
     * @param previousSystemState The previous SystemState
     */
    public void setPreviousSystemState(SystemState previousSystemState) {
        this.previousSystemState = previousSystemState;
    }

    /**
     * Gets the Perturbation
     *
     * @return The Perturbation
     */
    public Perturbation getPerturbation() {
        return perturbation;
    }

    /**
     * Gets previous SystemState
     *
     * @return The previous SystemState
     */
    public SystemState getPreviousSystemState() {
        return previousSystemState;
    }

    @Override
    public String toString() {
        return "Perturbation Instance: \n Previous State: " + previousSystemState
                + "\n Perturbation: " + perturbation;
    }

    /**
     * Apply perturbation to the SystemState
     *
     * @return The SystemState after applying the Perturbation to the previous state
     */
    public SystemState applyPerturbation(){
        //TODO When ready: this function can be implemented to create a new state by applying perturbation to prev state
        return new SystemState(-1);
    }
}
