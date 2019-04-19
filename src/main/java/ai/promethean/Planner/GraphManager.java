package ai.promethean.Planner;

import ai.promethean.DataModel.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * The type Graph manager.
 */
public class GraphManager {

    private SystemState initState;
    private GoalState goalState;
    private TaskDictionary taskDict;
    private StaticOptimizations optimizations;
    private PriorityQueue<StateTemplate> frontier = new PriorityQueue<StateTemplate>(1, new FrontierComparator());
    private long stopTime;
    private double originalHeuristicDist;

    /**
     * Instantiates a new Graph manager.
     *
     * @param initState     the init state
     * @param goalState     the goal state
     * @param taskDict      the task dict
     * @param optimizations the optimizations
     */
    public GraphManager(SystemState initState, GoalState goalState, TaskDictionary taskDict, StaticOptimizations optimizations) {
        this.initState = initState;
        this.goalState = goalState;
        this.taskDict = taskDict;
        this.optimizations = optimizations;
        this.originalHeuristicDist = Heuristic.h_value(initState, goalState, new Task(0, "Blank Task"));
    }

    public boolean frontierIsEmpty() {
        return frontier.peek() == null;
    }

    /**
     * Returns list of tasks that can be executed at a given state
     *
     * @param state the state in question
     * @return the list of tasks
     */
    public List<Task> validTasks(SystemState state) {
        List<Task> validTasks = new ArrayList<>();

        for (String name : taskDict.getKeys()) {
            boolean valid = true;
            Task task = taskDict.getTask(name);

            for (Condition condition : task.getRequirements()) {
                if (!condition.evaluate(state.getProperty(condition.getName()).getValue())) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                validTasks.add(task);
            }
        }

        return validTasks;
    }

    /**
     * Create a new system state from a previous state and a task to apply
     *
     * @param previousState the previous state
     * @param task          the task to apply
     * @param g_value       the g value of the new state
     * @return the system state
     */
    public SystemState createState(SystemState previousState, Task task, Double g_value, double h_value) {
        PropertyMap prev_properties = previousState.getPropertyMap();

        SystemState nextState = new SystemState(previousState.getTime() + task.getDuration(), h_value);
        nextState.setgValue(g_value);
        nextState.setPreviousState(previousState);
        nextState.setPreviousTask(task);

        for (String propertyName : prev_properties.getKeys()) {
            Property previousProperty = previousState.getProperty(propertyName);
            Property impact = task.getProperty(propertyName);
            if (impact != null) {
                Property newProperty = previousProperty.applyImpact(impact);
                nextState.addProperty(newProperty);
            } else {
                nextState.addProperty(previousProperty);
            }
        }

        return nextState;
    }

    /**
     * Creates a StateTemplate given a previous state and a potential task to be executed
     *
     * @param state the state in question
     * @param tasks a list of tasks that could be executed at that state
     * @return a list of StateTemplates
     */
    private List<StateTemplate> templateGeneration(SystemState state, List<Task> tasks) {
        List<StateTemplate> templates = new ArrayList<>();

        for (Task task : tasks) {
            Double g_value = TaskWeight.calculateTaskWeight(task, optimizations, originalHeuristicDist) + state.getgValue();
            double h_value = Heuristic.h_value(state,goalState,task);
            double f_value = g_value + h_value;
            templates.add(new StateTemplate(state, task, f_value, g_value, h_value));
        }

        return templates;
    }

    /**
     * Add a stateTemplate for every valid task to the frontier.
     *
     * @param state the state in question
     */
    public void addNeighborsToFrontier(SystemState state) {
        frontier.addAll(templateGeneration(state, validTasks(state)));
    }

    /**
     *
     * @param currentState the state in question
     * @return true if the previous state has a lower heuristic value than the given state
     */
    public boolean checkCLF(SystemState currentState) {
        if (currentState.getPreviousState() == null) {
            return true;
        } else if (currentState.gethValue() < currentState.getPreviousState().gethValue()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieve the best stateTemplate from the frontier and return a State
     *
     * @return the best next state
     */
    public SystemState poll() {
        StateTemplate template = frontier.poll();
        return createState(template.getPreviousState(), template.getTask(), template.getG(), template.getH());
    }

    public void setStopTime(long time) { stopTime = time; }

    public long getStopTime() { return stopTime; }
}
