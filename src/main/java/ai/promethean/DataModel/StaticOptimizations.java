package ai.promethean.DataModel;
import java.util.*;

public class StaticOptimizations {
    private ArrayList<Optimization> Optimizations = new ArrayList<Optimization>();

    public void addOptimization(Optimization o) {
        Optimizations.add(o);
    }

    public ArrayList<Optimization> getOptimizations() {
        return Optimizations;
    }
}