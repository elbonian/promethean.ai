package ai.promethean.Planner;

import ai.promethean.DataModel.Optimization;

public class OptimizationWeight {

    private OptimizationWeight() {}

    /**
     *
     * @param optimization contains the information on how we will optimize
     * @param propertyValue The particular value we will optimize on
     * @param listLength listLength gives a reference for the optimization priority
     * @return a property value that has been weighted given an optimization
     */
    public static Double weightedPropertyValue(Optimization optimization, Double propertyValue, int listLength) {
        //TODO: Proper Optimization weighting function
        return propertyValue;
    }
}
