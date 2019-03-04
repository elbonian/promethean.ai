package ai.promethean.DataModel;
import java.util.Comparator;

/**
 * The type Sortby optimization.
 */
public class SortbyOptimization implements Comparator<Optimization>{
    public int compare(Optimization a, Optimization b){
        return a.getPriority()-(b.getPriority());
    }
}
