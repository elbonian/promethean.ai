package ai.promethian.TestConstants;

import ai.promethean.DataModel.Optimization;
import ai.promethean.DataModel.StaticOptimizations;

import java.util.ArrayList;

public class OptimizationTestConstants {

    public static Optimization getTestOptimization1() {
        return new Optimization("NumProperty1", true);
    }

    public static Optimization getTestOptimization2() {
        return new Optimization("NumProperty2", false);
    }

    public static StaticOptimizations getAllTestOptimizations() {
        StaticOptimizations staticOptimizations = new StaticOptimizations();
        staticOptimizations.addOptimization(getTestOptimization1());
        staticOptimizations.addOptimization(getTestOptimization2());
        return staticOptimizations;
    }
}
