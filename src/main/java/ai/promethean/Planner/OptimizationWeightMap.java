package ai.promethean.Planner;

import ai.promethean.DataModel.Optimization;
import java.util.*;

public class OptimizationWeightMap {
    private Map<String, Double> optimizationWeightMap = new HashMap<>();

    public OptimizationWeightMap(ArrayList<Optimization> optimizations) {
        for (int i =0 ; i<optimizations.size(); i++) {
            optimizationWeightMap.put(optimizations.get(i).getName(),1.0 + (optimizations.size()-i)/optimizations.size());
        }
    }

    public Map<String, Double> getOptimizationWeightMap () {
        return this.optimizationWeightMap;
    }
}
