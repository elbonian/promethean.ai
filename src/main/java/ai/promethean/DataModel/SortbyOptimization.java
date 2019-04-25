package ai.promethean.DataModel;
import java.util.Comparator;

/**
 * The type Sortby optimization.
 */
public class SortbyOptimization implements Comparator<Optimization>{
    public int compare(Optimization a, Optimization b){
        if (a.getWeight() > b.getWeight()) {
            return 1;
        } else if (a.getWeight() < b.getWeight()) {
            return -1;
        } else {
            return 0;
        }
    }
}
