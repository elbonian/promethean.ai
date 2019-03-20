package ai.promethean.DataModel;
import java.util.*;

/**
 * The type Static optimizations.
 */
public class StaticOptimizations {
    private List<Optimization> Optimizations = new ArrayList<Optimization>();

    /**
     * Add optimization.
     *
     * @param o the o
     */
    public void addOptimization(Optimization o) {
        Optimizations.add(o);
    }

    /**
     * Gets optimizations.
     *
     * @return the optimizations
     */
    public List<Optimization> getOptimizations() {
        return Optimizations;
    }

    /**
     * Sort optimizations.
     */
    public void sortOptimizations() {
        Collections.sort(Optimizations, new SortbyOptimization());
    }

    /**
     * Size int.
     *
     * @return the int
     */
    public int size() { return Optimizations.size(); }

    /**
     * Gets optimization.
     *
     * @param name the name
     * @return the optimization
     */
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