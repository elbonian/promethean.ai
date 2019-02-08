package ai.promethean.Planner;

import ai.promethean.DataModel.Optimization;
import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.TaskDictionary;

import java.util.ArrayList;

public class AStar extends Algorithm {
    private GraphManager graph;
    private Double ceiling;

    public AStar(SystemState goalState) {
        this.graph = new GraphManager(goalState);
    }

    public SystemState run(SystemState initialState,
                           SystemState goalState,
                           TaskDictionary tasks,
                           ArrayList<Optimization> optimizations) {

       boolean found = false;
       if (initialState.equals(goalState)) { found = true; }

       graph.addNeighbors(initialState);

       while(!found /*|| ceiling check*/) {
           SystemState currentState = graph.poll();
           if (currentState.equals(goalState)) { found = true; }
           graph.addNeighbors(currentState);
       }
       // TODO: Return runtimeGoalState
       return null;
    }

}
