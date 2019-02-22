package ai.promethean.GraphManagement;

import ai.promethean.DataModel.*;
import ai.promethean.Planner.TaskWeight;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class GraphManager {

    private PriorityQueue<StateTemplate> frontier;
    private static SystemState initState;
    private static SystemState goalState;
    private static TaskDictionary taskDict;
    private static StaticOptimizations optimizations;

    public GraphManager() {

    }
    private static ArrayList<Task> validTasks(SystemState state, TaskDictionary taskDictionary){
        // For keeping track of valid tasks
        ArrayList<Task> valid_tasks = new ArrayList<>();
        for (int i = 0; i < taskDictionary.size(); i++) {
            boolean possible_task = false;
            Task current_task = taskDictionary.getTask(i);
            ArrayList<Condition> requirements = current_task.getRequirements();
            // I love IntelliJ
            for (Condition condition : requirements) {
                String name = condition.getName();
                Object state_value = state.getProperty(name);
                // ISSUE: condition.evaluate only does Doubles, extending props will break this
                // Extend after merging other changes to handle different inputs
                // Basically remove the (Double) cast right there, for now it won't compile without it
                if (condition.evaluate((Double) state_value)) {
                    possible_task = true;
                } else {
                    possible_task = false;
                }
            }
            // If all conditions are satisfied, then add this task to the valid_tasks array
            if (possible_task) {
                valid_tasks.add(current_task);
            }
        }
        return valid_tasks;
    }

    private static ArrayList<StateTemplate> templateGeneration(SystemState state, ArrayList<Task> tasks){
        // TODO: Create templates for every task
        return null;
    }

    public static void addNeighbors(SystemState state) {
        ArrayList<Task> tasks = validTasks(state, taskDict);
        ArrayList<StateTemplate> templates = templateGeneration(state, tasks);
        // TODO: Enqueue to frontier
    }

    private static SystemState createState(SystemState previousState, Task task) {
        ArrayList<Property> taskProperties = task.getProperty_impacts();
        PropertyMap affectedProperties = previousState.getPropertyMap();

        int previousTime = previousState.getTime();
        int nextTime = previousTime + task.getDuration();
        double gVal = previousState.getgValue() + TaskWeight.calculateTaskWeight(task, optimizations);

        SystemState nextState = new SystemState(nextTime);
        nextState.setgValue(gVal);
        nextState.setPreviousState(previousState);
        nextState.setPreviousTask(task);

        PropertyMap nextStateMap = nextState.getPropertyMap();

        for (Property property: taskProperties) {
            String propertyName = property.getName();

            Property oldProperty = affectedProperties.getProperty(propertyName);
            nextStateMap.addProperty(oldProperty.applyPropertyImpactOnto(property));
        }

        for (String propertyName: affectedProperties.getKeys()) {
            if (!nextStateMap.containsProperty(propertyName)) {
                nextStateMap.addProperty(affectedProperties.getProperty(propertyName));
            }
        }

        return nextState;
    }

    public static SystemState poll() {
        // TODO: Dequeue best template from frontier or Null if frontier is empty
        // TODO: return createState(template.previousState, template.task)
        return null;
    }
}
