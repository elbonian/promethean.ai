package ai.promethean.PlannerTest;

import ai.promethean.DataModel.*;
import ai.promethean.Planner.GraphManager;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GraphManagerTest {
    private SystemState startingState;
    private GoalState goal;
    private StaticOptimizations so;
    private Task simpleTask;

    public GraphManagerTest() {
        this.startingState = new SystemState();
        startingState.addProperty(new BooleanProperty("doorClosed", true));
        startingState.addProperty(new BooleanProperty("alwaysFalse", false));

        this.so = new StaticOptimizations();
        this.goal = new GoalState();


        simpleTask= new Task(10, "openDoor");
        Condition c = new BooleanCondition("doorClosed", "==", true);
        Property prop = new BooleanProperty("doorClosed", false);
        simpleTask.addProperty(prop);
        simpleTask.addRequirement(c);
    }

    @Test
    void checkEmptyFrontier() {
        GraphManager gm = new GraphManager(startingState, goal, new TaskDictionary(), so);
        assertTrue(gm.frontierIsEmpty());
    }

    @Test
    void checkValidTasks() {
        TaskDictionary td = new TaskDictionary();
        GraphManager gm = new GraphManager(startingState, goal, td, so);

        assertTrue(gm.validTasks(startingState).isEmpty());

        td.addTask(simpleTask);

        assertEquals(1, gm.validTasks(startingState).size());

        Task task2 = new Task(10, "neverDo");
        task2.addRequirement(new BooleanCondition("alwaysFalse", "==", true));
        td.addTask(task2);

        List<Task> validTasks = gm.validTasks(startingState);

        assertEquals(1, validTasks.size());

        Task onlyValidTask = validTasks.get(0);
        assertEquals(onlyValidTask.getDuration(), simpleTask.getDuration());
        assertTrue(onlyValidTask.getName().equals(simpleTask.getName()));
    }

    @Test
    void checkAddNeighbors() {
        TaskDictionary td = new TaskDictionary();
        td.addTask(simpleTask);

        GraphManager gm = new GraphManager(startingState, goal, td, so);

        assertTrue(gm.frontierIsEmpty());

        gm.addNeighborsToFrontier(startingState);
        assertFalse(gm.frontierIsEmpty());
    }

    @Test
    void checkCreateState() {
        GraphManager gm = new GraphManager(startingState, goal, new TaskDictionary(), so);
        SystemState generatedState = gm.createState(startingState, simpleTask, 10.0, 1.0);
        assertFalse((Boolean) generatedState.getProperty("doorClosed").getValue());

        List<Property> generatedProperties = generatedState.getProperties();

        for (Property property: generatedProperties) {
            String propertyName = property.getName();
            if (!propertyName.equals("doorClosed")) {
                assertEquals(property.getValue(), startingState.getProperty(propertyName).getValue());
            }
        }
    }
}
