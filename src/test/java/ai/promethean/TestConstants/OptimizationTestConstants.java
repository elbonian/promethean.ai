package ai.promethean.TestConstants;

import ai.promethean.DataModel.Optimization;
import ai.promethean.DataModel.StaticOptimizations;

public class OptimizationTestConstants {

    public static Optimization getTestOptimization1() {
        return new Optimization("NumProperty1", "min", 0.7);
    }

    public static Optimization getTestOptimization2() {
        return new Optimization("NumProperty2", "min", 0.4);
    }

    public static Optimization getTestOptimization3() {return new Optimization("duration", "min", 0.5); }


    public static StaticOptimizations getAllTestOptimizations() {
        StaticOptimizations staticOptimizations = new StaticOptimizations();
        staticOptimizations.addOptimization(getTestOptimization1());
        staticOptimizations.addOptimization(getTestOptimization2());
        staticOptimizations.addOptimization(getTestOptimization3());
        return staticOptimizations;
    }
}
