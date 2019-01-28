package ai.promethean.DataModel;

public class PerturbationInstance {
    private SystemState previousSystemState;
    private Perturbation perturbation;

    public PerturbationInstance(SystemState _prevSystemState, Perturbation _perturbation){
        setPerturbation(_perturbation);
        setPreviousSystemState(_prevSystemState);
    }

    public void setPerturbation(Perturbation perturbation) {
        this.perturbation = perturbation;
    }

    public void setPreviousSystemState(SystemState previousSystemState) {
        this.previousSystemState = previousSystemState;
    }

    public Perturbation getPerturbation() {
        return perturbation;
    }

    public SystemState getPreviousSystemState() {
        return previousSystemState;
    }

    @Override
    public String toString() {
        return "Perturbation Instance: \n Previous State: " + previousSystemState
                + "\n Perturbation: " + perturbation;
    }

    public SystemState applyPerturbation(){
        //TODO When ready: this function can be implemented to create a new state by applying perturbation to prev state
        return new SystemState(-1);
    }
}
