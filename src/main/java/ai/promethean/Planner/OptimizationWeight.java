package ai.promethean.Planner;

import ai.promethean.DataModel.NumericalProperty;
import ai.promethean.DataModel.Optimization;
import ai.promethean.DataModel.Property;

import java.util.*;

public class OptimizationWeight {

    public static Double weightedPropertyValue(Optimization optimization, Double propertyValue, int listLength) {
        if(optimization.getIsMin()) {
            return Math.pow(propertyValue,2.5+(listLength-optimization.getPriority()));
        } else {
            return Math.pow(2.0+propertyValue,-1*(2.5+(listLength-optimization.getPriority())));
        }
    }
}
