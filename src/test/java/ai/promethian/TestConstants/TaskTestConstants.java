package ai.promethian.TestConstants;

import ai.promethean.DataModel.Condition;
import ai.promethean.DataModel.Property;
import ai.promethean.DataModel.Task;
import ai.promethean.DataModel.TaskDictionary;

public class TaskTestConstants {

    public static Task getFullTask1() {
        Task returnTask = new Task(1,10);
        for (Property property: PropertyTestConstants.getAllTestProperties()) {
            returnTask.addProperty(property);
        }
        for (Condition condition: ConditionTestConstants.getAllTestConditions()) {
            returnTask.addRequirement(condition);
        }
        return returnTask;
    }

    public static Task getFullTask2() {
        Task returnTask = new Task(1,5);
        for (Property property: PropertyTestConstants.getAllNumericProperties()) {
            returnTask.addProperty(property);
        }
        for (Condition condition: ConditionTestConstants.getAllTestConditions()) {
            returnTask.addRequirement(condition);
        }
        return returnTask;
    }

    public static Task getFullTask3() {
        Task returnTask = new Task(1,8);
        for (Property property: PropertyTestConstants.getAllBooleanProperties()) {
            returnTask.addProperty(property);
        }
        for (Condition condition: ConditionTestConstants.getAllTestConditions()) {
            returnTask.addRequirement(condition);
        }
        return returnTask;
    }

    public static Task getFullTask4() {
        Task returnTask = new Task(1,8);
        for (Property property: PropertyTestConstants.getAllStringProperties()) {
            returnTask.addProperty(property);
        }
        for (Condition condition: ConditionTestConstants.getAllTestConditions()) {
            returnTask.addRequirement(condition);
        }
        return returnTask;
    }

    public static TaskDictionary getTaskDictionary() {
        TaskDictionary taskDictionary = new TaskDictionary();
        taskDictionary.addTask(getFullTask1());
        taskDictionary.addTask(getFullTask2());
        taskDictionary.addTask(getFullTask3());
        taskDictionary.addTask(getFullTask4());
        return taskDictionary;
    }

    public static Task getImpactsOnlyTask1() {
        Task returnTask = new Task(1,1);
        for (Property property: PropertyTestConstants.getAllTestProperties()) {
            returnTask.addProperty(property);
        }
        return returnTask;
    }


    public static Task getConditionsOnlyTask() {
        Task returnTask = new Task(1,10);
        for (Condition condition: ConditionTestConstants.getAllTestConditions()) {
            returnTask.addRequirement(condition);
        }
        return returnTask;
    }
}
