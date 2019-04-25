package ai.promethean.Planner;

import ai.promethean.DataModel.GoalState;
import ai.promethean.DataModel.StaticOptimizations;
import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.TaskDictionary;

import static oracle.jrockit.jfr.events.Bits.longValue;

/**
 * The type A star.
 */
public class AStar implements Algorithm {

    public AStar() {}

    /**
     * High-Level A-Star Algorithm
     * @return RuntimeGoalState, or null if no path is valid
     */
    public SystemState run(SystemState initState, GoalState goalState, TaskDictionary tasks, StaticOptimizations optimizations, double minutesAllowed, boolean activateCLF) {
        GraphManager graph = new GraphManager(initState, goalState, tasks, optimizations);
        // stop time is the current time plus the conversion from minutes allowed to milliseconds allowed
        graph.setStopTime( System.currentTimeMillis() + longValue(minutesAllowed*60*1000 ));
        if (goalState.meetsGoal(initState)) {
            return initState;
        }

        graph.addNeighborsToFrontier(initState);
        boolean clfActive = activateCLF;
        while (!graph.frontierIsEmpty()) {
            SystemState currentState = graph.poll();
            if ( goalState.meetsGoal(currentState) || (!clfActive && System.currentTimeMillis() > graph.getStopTime()) ) {
                return currentState;
            }
            graph.addNeighborsToFrontier(currentState);
            if (clfActive && !graph.checkCLF(currentState)) {
                clfActive = false;
                graph.setStopTime( System.currentTimeMillis() + longValue(minutesAllowed*60*1000 ));
            }
            if (clfActive) {
                clfActive = graph.checkCLF(currentState);
            }
        }

        return null;
    }

}
