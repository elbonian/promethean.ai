package ai.promethean.Planner;

import ai.promethean.DataModel.NumericalProperty;
import ai.promethean.DataModel.Property;
import ai.promethean.DataModel.StaticOptimizations;
import ai.promethean.DataModel.Task;

public class TaskWeight {

    private TaskWeight() {}

    public static Double calculateTaskWeight(Task task, StaticOptimizations optimizations) {
        Double squaredSum = 0.0;
        int optimizationsLength = optimizations.size();

        if (optimizations.getOptimization("Duration") != null) {
            squaredSum += OptimizationWeight.weightedPropertyValue(
                        optimizations.getOptimization("Duration"),
                        task.getDuration() + 0.0,
                        optimizationsLength);
        } else {
            squaredSum += task.getDuration();
        }

        for (Property property : task.getProperties()) {
            if (property instanceof NumericalProperty) {
                if (optimizations.getOptimization(property.getName()) != null) {
                    squaredSum += OptimizationWeight.weightedPropertyValue(
                                optimizations.getOptimization(property.getName()),
                                ((NumericalProperty) property).getValue(),
                                optimizationsLength);
                } else {
                    squaredSum += ((NumericalProperty) property).getValue();
                }
            } else {
                squaredSum += 1.0;
            }
        }

        return Math.sqrt(squaredSum);
    }
}
