package ai.promethean.Planner;

import java.util.*;
import ai.promethean.DataModel.SystemState;
import ai.promethean.GraphManagement.GraphManager;

public class AStar {

   public static void AStar(SystemState startState, SystemState endState) {
       boolean found = false;
       while(!found /*|| ceiling check*/) {
           SystemState currentState = GraphManager.poll();
           if (currentState.equals(endState)) {
               found = true;
           }
           GraphManager.addNeighbors(currentState);
       }
   }
}
