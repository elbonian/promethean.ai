package ai.promethean.GraphManagement;

import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class GraphManager {

    private PriorityQueue<StateTemplate> frontier;

    public GraphManager() {

    }

    private static ArrayList<Task> validTasks(SystemState state){
        // TODO: Find all tasks that can be executed from state (is TaskDictionary global?)
        return null;
    }

    private static ArrayList<StateTemplate> templateGeneration(SystemState state, ArrayList<Task> tasks){
        // TODO: Create templates for every task
        return null;
    }

    public static void addNeighbors(SystemState state) {
        ArrayList<Task> tasks = validTasks(state);
        ArrayList<StateTemplate> templates = templateGeneration(state, tasks);
        // TODO: Enqueue to frontier
    }

    private static SystemState createState(SystemState previousState, Task task) {
        // TODO: Build and return a new SystemState object
        return null;
    }

    public static SystemState poll() {
        // TODO: Dequeue best template from frontier or Null if frontier is empty
        // TODO: return createState(template.previousState, template.task)
        return null;
    }

}
