package ai.promethean.Planner;

import ai.promethean.DataModel.GoalState;
import ai.promethean.DataModel.StaticOptimizations;
import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.TaskDictionary;

/**
 * The type A star.
 */
public class AStar implements Algorithm {
    private Double ceiling;

    public AStar() {}

    /**
     * High-Level A-Star Algorithm
     * @return RuntimeGoalState, or null if no path is valid
     */
    public SystemState run(SystemState initState, GoalState goalState, TaskDictionary tasks, StaticOptimizations optimizations) {
        GraphManager graph = new GraphManager(initState, goalState, tasks, optimizations);
        if (goalState.meetsGoal(initState)) {
            return initState;
        }

        graph.addNeighborsToFrontier(initState);
        boolean clfActive = true;
        while (!(graph.frontierIsEmpty() || !clfActive)) {
            SystemState currentState = graph.poll();
            if (goalState.meetsGoal(currentState)) {
                return currentState;
            }
            graph.addNeighborsToFrontier(currentState);
            clfActive = graph.checkCLF(currentState);
        }

        return null;
    }

    /**
     * High-Level A-Star Algorithm
     * @return RuntimeGoalState, or null if no path is valid
     */
    public SystemState run(SystemState initState, GoalState goalState, TaskDictionary tasks, StaticOptimizations optimizations, int minutesAllowed) {
        GraphManager graph = new GraphManager(initState, goalState, tasks, optimizations);
        initState.setMilliTime(System.currentTimeMillis());
        // stop time is the current time plus the conversion from minutes allowed to milliseconds allowed
        long stopTime = initState.getTime() + minutesAllowed*60*1000;
        if (goalState.meetsGoal(initState)) {
            return initState;
        }

        graph.addNeighborsToFrontier(initState);
        boolean clfActive = true;
        while (!(graph.frontierIsEmpty() || !clfActive)) {
            SystemState currentState = graph.poll();
            if (goalState.meetsGoal(currentState) || currentState.getMilliTime() > stopTime) {
                return currentState;
            }
            graph.addNeighborsToFrontier(currentState);
            clfActive = graph.checkCLF(currentState);
        }

        return null;
    }

}
