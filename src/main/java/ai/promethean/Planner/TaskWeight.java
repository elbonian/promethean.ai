package ai.promethean.Planner;

import ai.promethean.DataModel.*;

public class TaskWeight {

    private TaskWeight() {}

    /**
     *
     * @param task the current task
     * @param optimizations any optimizations given to the planner
     * @return a Task edge weight based on the property impacts of the task and optimizations of those properties
     */
    public static Double calculateTaskWeight(Task task, StaticOptimizations optimizations, double baseHeuristicDist) {
        double taskWeight = 0.0;
        int numNumericalProperties = 0;
        int numNonNumericalProperties = 0;

        Optimization timeOp = optimizations.getOptimization("Duration");
        if (timeOp != null) {
            taskWeight += task.getDuration() * timeOp.getWeight();
            numNumericalProperties += 1;
        } else if (optimizations.size() == 0) {
            taskWeight += task.getDuration();
            numNumericalProperties += 1;
        }

        for (Property property : task.getProperties()) {
            if (property instanceof NumericalProperty) {
                Optimization propertyOptimization = optimizations.getOptimization(property.getName());
                if (propertyOptimization != null) {
                    if (propertyOptimization.getType().equals("max") && (((NumericalProperty) property).getValue() > 0) ||
                            propertyOptimization.getType().equals("min") && (((NumericalProperty) property).getValue() < 0)) {
                        taskWeight += (-1*Math.abs( ((NumericalProperty) property).getValue() )) * propertyOptimization.getWeight();
                        numNumericalProperties += 1;
                    } else {
                        taskWeight += Math.abs( ((NumericalProperty) property).getValue() ) * propertyOptimization.getWeight();
                        numNumericalProperties += 1;
                    }
                }
            } else {
                numNonNumericalProperties += 1;
            }
        }

        Double totalAverage = taskWeight/(numNumericalProperties + numNonNumericalProperties);
        taskWeight += totalAverage * numNonNumericalProperties;

        // This ensures our Heuristic remains admissible
        taskWeight += baseHeuristicDist;

        return taskWeight;
    }
}
