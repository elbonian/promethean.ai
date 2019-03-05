package ai.promethean.ExecutingAgent;

import ai.promethean.DataModel.SystemState;

import java.util.List;

public abstract class ClockObserver {

    protected static List<SystemState> stateList;

    /**
     * Called by Clock to update the listener's time
     * The listener should implement some logic here to check if a task / perturbation should update the state
     *
     * @param _time The current time
     */
    public abstract boolean update(int _time);

    /**
     * Set the list of SystemStates that observers will add to
     * @param states List of SystemStates
     */
    public static void setStateList(List<SystemState> states) {
        stateList = states;
    }

    /**
     * Add a SystemState to the static list of SystemStates
     * @param state
     */
    public static void addState(SystemState state) {
        stateList.add(state);
    }
}
