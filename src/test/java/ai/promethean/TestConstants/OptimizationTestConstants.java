package ai.promethean.TestConstants;

import ai.promethean.DataModel.Optimization;
import ai.promethean.DataModel.StaticOptimizations;

public class OptimizationTestConstants {

    public static Optimization getTestOptimization1() {
        return new Optimization("NumProperty1", "min", 1);
    }

    public static Optimization getTestOptimization2() {
        return new Optimization("NumProperty2", "max", 1);
    }

    public static StaticOptimizations getAllTestOptimizations() {
        StaticOptimizations staticOptimizations = new StaticOptimizations();
        staticOptimizations.addOptimization(getTestOptimization1());
        staticOptimizations.addOptimization(getTestOptimization2());
        return staticOptimizations;
    }
}
