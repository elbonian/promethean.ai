package ai.promethean.ExecutingAgent;

import ai.promethean.DataModel.*;
import ai.promethean.Logger.Logger;

import java.util.Comparator;
import java.util.List;

public class PerturbationInjector extends ClockObserver {

    private List<Perturbation> perturbations;

    public PerturbationInjector(List<Perturbation> _perturbations) {
        perturbations = _perturbations;

        // Sort w.r.t time
        perturbations.sort(Comparator.comparing(Perturbation::getTime));
    }

    /**
     * Check if any perturbations need to be applied at the current time
     * If so, update the state stack
     * @param _time The current time
     * @return
     */
    @Override
    public boolean update(int _time) {
        // While loop in case there are multiple perturbations at the same time
        while (!perturbations.isEmpty() && perturbations.get(0).getTime() <= _time) {
            Perturbation perturbation = perturbations.remove(0);
            if (perturbation.getProperties().containsProperty("time")
                    && perturbation.getProperty("time") instanceof NumericalProperty) {
                NumericalProperty timeProperty = (NumericalProperty) perturbation.getProperty("time");

                int lag = timeProperty.getValue().intValue();
                Clock.addLag(lag);
            }
            updateState(perturbation, _time);
        }
        return true;
    }

    /**
     * Update the latest state with a perturbation
     * @param perturbation
     * @param time
     */
    private void updateState(Perturbation perturbation, int time) {
        SystemState previousState = ClockObserver.peekLastState();
        SystemState currentState = perturbation.applyPerturbation(previousState);
        currentState.setTime(time);

        Logger.writeLog("Perturbation: \n" + perturbation, "PerturbationInjector");


        ClockObserver.addState(currentState);
    }

}
