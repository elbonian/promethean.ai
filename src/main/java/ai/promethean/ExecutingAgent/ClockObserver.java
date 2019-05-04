package ai.promethean.ExecutingAgent;

import ai.promethean.DataModel.SystemState;
import ai.promethean.Logger.Logger;

import java.util.EmptyStackException;
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
        try{
            return stateStack.peek();
        } catch (EmptyStackException e) {
            Logger.logError(e, ClockObserver.class.getSimpleName());
            throw e;
        }
    }

    public static Stack<SystemState> getStateStack() {
        return stateStack;
    }
}
