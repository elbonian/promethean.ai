package ai.promethean.DataModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OptimizationTest {
    private Optimization o= new Optimization("Time", "min", 10);
    private Optimization o1= new Optimization("Time", "min", 5);
    private Optimization o2= new Optimization("Time", "max",4);
    private Optimization o3= new Optimization("Time1", "min",7);
    private StaticOptimizations oList= new StaticOptimizations();

    @Test
    void checkName(){assertTrue(o.getName()=="Time");}
    @Test
    void checkMin(){assertTrue(o.getType()=="min");}
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
        assertTrue(oList.getOptimizations().get(0).getWeight()==10);
    }

    @Test
    void checkSorted(){
        oList.addOptimization(o);
        oList.addOptimization(o1);
        oList.sortOptimizations();
        assertTrue(oList.getOptimizations().get(0).getWeight()==5);
    }

}
