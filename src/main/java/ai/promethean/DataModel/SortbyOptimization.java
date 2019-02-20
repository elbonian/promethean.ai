package ai.promethean.DataModel;
import java.util.Comparator;

public class SortbyOptimization implements Comparator<Optimization>{
    public int compare(Optimization a, Optimization b){
        return a.getPriority()-(b.getPriority());
    }
}
