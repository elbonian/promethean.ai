package ai.promethean.Planner;

import ai.promethean.DataModel.Optimization;
import java.util.*;

public class OptimizationWeightMap {
    private Map<String, Integer> optimizationWeightMap = new HashMap<>();

    public OptimizationWeightMap(ArrayList<Optimization> optimizations) {
        for (int i =0 ; i<optimizations.size(); i++) {
            optimizationWeightMap.put(optimizations.get(i).getName(),(optimizations.size()-i)*2);
        }
    }

    public Map<String, Integer> getOptimizationWeightMap () {
        return this.optimizationWeightMap;
    }
}
