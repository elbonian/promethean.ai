package ai.promethian.DataModel;
import ai.promethean.DataModel.Optimization;
import ai.promethean.DataModel.StaticOptimizations;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OptimizationTest {
    private Optimization o= new Optimization("Time", true, 10);
    private Optimization o1= new Optimization("Time", true, 5);
    private Optimization o2= new Optimization("Time", false,4);
    private Optimization o3= new Optimization("Time1", true,7);
    private StaticOptimizations oList= new StaticOptimizations();

    @Test
    void checkName(){assertTrue(o.getName()=="Time");}
    @Test
    void checkMin(){assertTrue(o.getIsMin());}
    @Test
    void checkEqualDiff(){assertTrue(o.equals(o1));}
    @Test
    void checkNotEqualsName(){assertFalse(o.equals(o3));}
    @Test
    void checkNotEqualsBool(){assertFalse(o.equals(o2));}

    @Test
    void checkunSorted(){
        oList.addOptimization(o);
        oList.addOptimization(o1);
        assertTrue(oList.getOptimizations().get(0).getPriority()==10);
    }

    @Test
    void checkSorted(){
        oList.addOptimization(o);
        oList.addOptimization(o1);
        oList.sortOptimizations();
        assertTrue(oList.getOptimizations().get(0).getPriority()==5);
    }

}
