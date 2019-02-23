package ai.promethean.Planner;

import ai.promethean.DataModel.*;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class GraphManager {

    private PriorityQueue<StateTemplate> frontier = new PriorityQueue<StateTemplate>(1, new FrontierComparator());
    private static SystemState initState;
    private static GoalState goalState;
    private static TaskDictionary taskDict;
    private static StaticOptimizations optimizations;

    public GraphManager(SystemState initState, GoalState goalState, TaskDictionary taskDict, StaticOptimizations optimizations) {
        this.initState = initState;
        this.goalState = goalState;
        this.taskDict = taskDict;
        this.optimizations = optimizations;
    }

    public boolean frontierIsEmpty(){
        if (frontier.peek() == null) {
            return true;
        }
        return false;
    }

    private static ArrayList<Task> validTasks(SystemState state){
        // For keeping track of valid tasks
        ArrayList<Task> validTasks = new ArrayList<>();
        for (String name: taskDict.getKeys()) {
            boolean possible_task = true;
            Task current_task = taskDict.getTask(name);
            ArrayList<Condition> requirements = current_task.getRequirements();
            // I love IntelliJ
            for (Condition condition : requirements) {
                if (!condition.evaluate(state.getProperty(condition.getName()).getValue())) {
                    possible_task = false;
                    break;
                }
            }
            // If no conditions are unsatisfied, then add this task to the valid_tasks array
            if (possible_task) {
                validTasks.add(current_task);
            }
        }
        return validTasks;
    }

    private ArrayList<StateTemplate> templateGeneration(SystemState state, ArrayList<Task> tasks){
        Double g_value;
        Double f_value;
        ArrayList<StateTemplate> templates = new ArrayList<>();

        for (Task task : tasks) {
            g_value = Heuristic.g_value(state, task, optimizations);
            f_value = Heuristic.f_value(state, this.goalState, g_value);

            templates.add(new StateTemplate(state, task, f_value, g_value));
        }
        return templates;
    }

    // Add stateTemplates of all potential neighbors to the frontier
    public void addNeighborsToFrontier(SystemState state) {
        ArrayList<StateTemplate> templates = templateGeneration(state, validTasks(state));
        frontier.addAll(templates);
    }

    private static SystemState createState(SystemState previousState, Task task) {
        ArrayList<Property> impacts = task.getProperty_impacts();
        PropertyMap affectedProperties = previousState.getPropertyMap();

        SystemState nextState = new SystemState(previousState.getTime() + task.getDuration());
        nextState.setgValue(previousState.getgValue() + TaskWeight.calculateTaskWeight(task, optimizations));
        nextState.setPreviousState(previousState);
        nextState.setPreviousTask(task);


        for (Property property: impacts) {
            String propertyName = property.getName();

            Property oldProperty = affectedProperties.getProperty(propertyName);
            nextState.getPropertyMap().addProperty(property.applyPropertyImpactOnto(oldProperty));
        }

        for (String propertyName: affectedProperties.getKeys()) {
            if (! nextState.getPropertyMap().containsProperty(propertyName)) {
                nextState.getPropertyMap().addProperty(affectedProperties.getProperty(propertyName));
            }
        }

        return nextState;
    }

    // Get best neighbor from frontier and convert to SystemState
    public SystemState poll() {
        StateTemplate template = frontier.poll();
        return createState(template.getPreviousState(), template.getTask());
    }
}
