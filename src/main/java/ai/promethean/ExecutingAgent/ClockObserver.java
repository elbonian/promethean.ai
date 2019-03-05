package ai.promethean.ExecutingAgent;

import ai.promethean.DataModel.SystemState;

abstract class ClockObserver {

    /**
     * Called by Clock to update the listener's time
     * The listener should implement some logic here to check if a task / perturbation should update the state
     *
     * @param _time The current time
     */
    public abstract Boolean update(int _time);

}
