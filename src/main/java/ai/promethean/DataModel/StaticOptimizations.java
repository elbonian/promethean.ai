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

    public void sortOptimizations() {
        Collections.sort(Optimizations, new SortbyOptimization());
    }

    public int size() { return Optimizations.size(); }

    public Optimization getOptimization(String name) {
        for (Optimization optimization: Optimizations) {
            if (optimization.getName() == name) {
                return optimization;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String printOut= "Static Optimizations: \n";
        for(Optimization o: Optimizations){
            printOut=printOut+ o + "\n";
        }
        return printOut;
    }
}