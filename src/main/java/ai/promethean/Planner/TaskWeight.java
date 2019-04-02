package ai.promethean.Planner;

import ai.promethean.DataModel.NumericalProperty;
import ai.promethean.DataModel.Property;
import ai.promethean.DataModel.StaticOptimizations;
import ai.promethean.DataModel.Task;

public class TaskWeight {

    private TaskWeight() {}

    public static Double calculateTaskWeight(Task task, StaticOptimizations optimizations) {
        Double taskWeight = 0.0;
        int optimizationsLength = optimizations.size();

        if (optimizations.getOptimization("Duration") != null) {
            taskWeight += OptimizationWeight.weightedPropertyValue(
                        optimizations.getOptimization("Duration"),
                        task.getDuration() + 0.0,
                        optimizationsLength);
        } else {
            taskWeight += task.getDuration();
        }
        Double numericalSum = 0.0;
        Integer numNumericalProperties = 0;
        Integer numNonNumericalProperties = 0;

        for (Property property : task.getProperties()) {
            if (property instanceof NumericalProperty) {
                if (optimizations.getOptimization(property.getName()) != null) {
                    taskWeight += Math.abs(OptimizationWeight.weightedPropertyValue(
                                optimizations.getOptimization(property.getName()),
                                Math.abs(((NumericalProperty) property).getValue()),
                                optimizationsLength));
                } else {
                    taskWeight += Math.abs(((NumericalProperty) property).getValue());
                }
                numericalSum += Math.abs(((NumericalProperty) property).getValue());
                numNumericalProperties += 1;
            } else {
                numNonNumericalProperties += 1;
                taskWeight +=1;
            }
        }
        Double numericalAverage = numericalSum/numNumericalProperties;
        taskWeight += numericalAverage * numNonNumericalProperties;

        return taskWeight;
    }
}
