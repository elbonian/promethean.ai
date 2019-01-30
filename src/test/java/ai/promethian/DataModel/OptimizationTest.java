package ai.promethian.DataModel;
import ai.promethean.DataModel.Optimization;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OptimizationTest {
    private Optimization o= new Optimization("Time", true);
    private Optimization o1= new Optimization("Time", true);
    private Optimization o2= new Optimization("Time", false);
    private Optimization o3= new Optimization("Time1", true);

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
}
