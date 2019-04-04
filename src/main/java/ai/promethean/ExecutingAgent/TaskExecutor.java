package ai.promethean.ExecutingAgent;

import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;
import ai.promethean.Planner.Plan;
import ai.promethean.Planner.PlanBlock;
import ai.promethean.Logger.*;

import java.util.List;

public class TaskExecutor extends ClockObserver {
    private Plan plan;
    private int initTime;
    private boolean planCompleted;
    private String className = "TaskExecutor";

    public TaskExecutor(Plan _plan) {
        plan = _plan;
        initTime = _plan.getInitialState().getTime();
        planCompleted=false;
    }

    public boolean isPlanCompleted() {
        return planCompleted;
    }

    /**
     * Notify TaskExecutor of the current time
     * This will apply a task and add a new state to shared state list if the task has finished
     * @param _time The current time
     * @return returns true if the TaskExecutor is out of tasks or a generated state is invalid
     */
    @Override
    public boolean update(int _time) {
        List<PlanBlock> planBlocks = plan.getPlanBlockList();
        if (planBlocks.isEmpty()) {
           this. planCompleted=true;
            return false;
        }

        int taskDuration = planBlocks.get(0).getTask().getDuration();


        if (_time == initTime + taskDuration + Clock.getLag()) {
            PlanBlock currentBlock = planBlocks.remove(0);
            Clock.resetLag();

            boolean appliedBlock = applyBlock(currentBlock, _time);
            if (appliedBlock) {
                initTime = _time;
            }
            return appliedBlock;
        }

        return true;
    }

    /**
     * Generate a new state from the current PlanBlock
     * Also compare the new state to the state that the plan predicted
     * @param block PlanBlock whose task should be applied to the current state
     * @param time Current time
     * @return true if the generated state matches the block's state or false otherwise
     */
    private boolean applyBlock(PlanBlock block, int time) {
        // Generate a new SystemState based on the last element of the list of SystemStates
        // Add that new State to the end of the state list
        // Check that the new state equals the plan's predicted state
        SystemState previousState = ClockObserver.peekLastState();
        Task task = block.getTask();

        SystemState currentState= task.applyTask(previousState);
        currentState.setTime(time);
        ClockObserver.addState(currentState);

        if (Logger.isLogFlag()) {
            Logger.writeLog("Task Applied: \n" + block.getTask(), this.className);
            Logger.writeLog("Current State: \n" + currentState, this.className);
            Logger.writeLog("Task Execution End Time: \n" + time, this.className);
        }
        return currentState.equals(block.getState());
    }
}