package ai.promethean;
import ai.promethean.DataModel.GoalState;
import ai.promethean.DataModel.Optimization;
import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.Task;
import ai.promethean.TestCaseGenerator.PropertyDelta;
import ai.promethean.TestCaseGenerator.TestCaseGenerator;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class GenerateTestCase {
    public static void main(String args[]) {
        SystemState starting = new SystemState();
        double upperLimit = 100;
        Random r = new Random();
        List<String> propertyNames = new ArrayList<>();
        String propertyBase = "property";
        GoalState ending = new GoalState();
        int offset = 0; // because scoping
        int offsetCopy = offset;
        for (Integer i = 0; i < 5; i ++) {
            String propertyName = propertyBase + i.toString();
            starting.addProperty(propertyName, true);
            ending.addRequirement(propertyName, false, "==");
            offset ++;
        }
        offsetCopy = offset;
        for (Integer i = 0 + offset; i < 1 + offsetCopy; i++) {
            String propertyName = propertyBase + i.toString();
            starting.addProperty(propertyName, false);
            ending.addRequirement(propertyName, true, "==");
            offset++;
        }
        offsetCopy = offset;
        for (Integer i = 0 + offset; i < 5 + offsetCopy; i++) {
            String propertyName = propertyBase + i.toString();
            Double d1 = r.nextDouble() * upperLimit;
            starting.addProperty(propertyName, upperLimit);
            Double d2 = d1 + r.nextDouble() * upperLimit;
            ending.addRequirement(propertyName, d2, "<=");
            offset++;
        }
        starting.addProperty("Battery", 0.0);
        ending.addRequirement("Battery", 0.0, ">=");
        starting.addProperty("asdf", true);
        ending.addRequirement("asdf", false, "==");
        TestCaseGenerator generator = new TestCaseGenerator(starting, ending, 100, true);
        ArrayList<Optimization> optimizations = generator.generateOptimizations();
        ArrayList<Task> tasks = generator.generateTestCase();
        List<PropertyDelta> deltas = generator.getPropertyDeltas();
        List<Task> optimalPathTasks = generator.getCriticalPathTasks();
        try{
            generator.testCaseToJSON(tasks, optimizations, 0);
        } catch (IOException e){
            System.out.println("asdf");
        }
    }
}