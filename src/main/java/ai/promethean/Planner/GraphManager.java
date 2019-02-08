package ai.promethean.Planner;

import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class GraphManager {

    // Ordered based on f value
    private PriorityQueue<StateTemplate> frontier = new PriorityQueue<StateTemplate>(1, new FrontierComparator());
    private SystemState goalState;

    public GraphManager(SystemState goalState) {
        this.goalState = goalState;
    }

    // Return all tasks that can be executed from a given SystemState
    private ArrayList<Task> validTasks(SystemState state){
        // TODO: Find all tasks that can be executed from state (is TaskDictionary global?)
        return null;
    }

    // Create stateTemplate given previous state and valid task list
    private ArrayList<StateTemplate> templateGeneration(SystemState state, ArrayList<Task> tasks){
        Double g_value;
        Double f_value;
        ArrayList<StateTemplate> templates = null;

        for (Task task : tasks) {
            // g_value = Heuristic.g_value(state, task);
            // f_value = Heuristic.f_value(state, this.goalState, g_value);
            g_value = 0.0;
            f_value = 0.0;

            StateTemplate template = new StateTemplate(state, task, f_value, g_value);
            templates.add(template);
        }
        return templates;
    }

    // Add stateTemplates of all potential neighbors to the frontier
    public void addNeighbors(SystemState state) {
        ArrayList<Task> tasks = validTasks(state);
        ArrayList<StateTemplate> templates = templateGeneration(state, tasks);
        templates.forEach((template) -> frontier.add(template));
    }

    // Given a previousState, task, return next SystemState
    private SystemState createState(SystemState previousState, Task task) {
        // TODO: Build and return a new SystemState object
        return null;
    }

    // Get best neighbor from frontier and convert to SystemState
    public SystemState poll() {
        StateTemplate template = frontier.poll();
        return createState(template.getPreviousState(), template.getTask());
    }

}
