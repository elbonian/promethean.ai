package ai.promethean.ExecutingAgent;

import ai.promethean.DataModel.SystemState;

import java.util.Stack;

public abstract class ClockObserver {

    protected static Stack<SystemState> stateStack = new Stack<>();

    /**
     * Called by Clock to update the listener's time
     * The listener should implement some logic here to check if a task / perturbation should update the state
     *
     * @param _time The current time
     */
    public abstract boolean update(int _time);

    /**
     * Add a SystemState to the static list of SystemStates
     * @param state
     */
    public static void addState(SystemState state) {
        stateStack.add(state);
    }

    /**
     * Peek at the last SystemState added without removing it
     * @return the last SystemState
     */
    public static SystemState peekLastState() {
        return stateStack.peek();
    }

    public static Stack<SystemState> getStateStack() {
        return stateStack;
    }
}
