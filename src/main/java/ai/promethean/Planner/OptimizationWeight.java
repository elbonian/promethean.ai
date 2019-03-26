package ai.promethean.Planner;

import ai.promethean.DataModel.Optimization;

public class OptimizationWeight {

    private OptimizationWeight() {}

    public static Double weightedPropertyValue00(Optimization optimization, Double propertyValue, int listLength) {
        if (optimization.getType() == "min") {
            return Math.pow(propertyValue, 2.5 + (listLength - optimization.getPriority()));
        }

        return Math.pow(2.0 + propertyValue, -1 * (2.5 + (listLength - optimization.getPriority())));
    }

    public static Double weightedPropertyValue(Optimization optimization, Double propertyValue, int listLength) {
        if (optimization.getType().equals("min")) {
            return propertyValue; //* 1.0+optimization.getPriority();
        } else {
            return propertyValue; ///1.0+optimization.getPriority();
        }
    }
}
