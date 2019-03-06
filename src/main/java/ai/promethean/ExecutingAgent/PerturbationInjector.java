package ai.promethean.ExecutingAgent;

import ai.promethean.DataModel.*;

import java.util.Comparator;
import java.util.List;

public class PerturbationInjector extends ClockObserver {

    private List<Perturbation> perturbations;

    public PerturbationInjector(List<Perturbation> _perturbations) {
        perturbations = _perturbations;

        // Sort w.r.t time
        perturbations.sort(Comparator.comparing(Perturbation::getTime));
    }

    @Override
    public boolean update(int _time) {
        // While loop in case there are multiple perturbations at the same time
        while (perturbations.get(0).getTime() == _time) {
            Perturbation perturbation = perturbations.remove(0);
            updateState(perturbation, _time);
        }
        return true;
    }

    private void updateState(Perturbation perturbation, int time) {
        SystemState previousState = stateList.get(stateList.size() - 1);
        PropertyMap prev_properties = previousState.getPropertyMap();

        SystemState currentState = new SystemState(time);

        for (String propertyName : prev_properties.getKeys()) {
            Property previousProperty = previousState.getProperty(propertyName);
            Property impact = perturbation.getProperty(propertyName);
            if (impact != null) {
                Property newProperty = previousProperty.applyImpact(impact);
                currentState.addProperty(newProperty);
            } else {
                currentState.addProperty(previousProperty);
            }
        }

        ClockObserver.addState(currentState);

    }
}
