package ai.promethean.DataModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NumericalPropertyTest {
    private NumericalProperty np = new NumericalProperty("test", 100.0, "delta");
    private NumericalProperty np1 = new NumericalProperty("test", 100.0);
    private NumericalProperty np2 = new NumericalProperty("test1", 100.0);
    private NumericalProperty np3 = new NumericalProperty("test", -50.0);

    @Test
    void checkEquals() {
        assertTrue(np.equals(np));
    }
    @Test
    void checkNum(){assertTrue(np.getValue()==100.0);}
    @Test
    void checkName(){assertTrue(np.getName()=="test");}
    @Test
    void checkEqualDiff(){assertTrue(np.equals(np1));}
    @Test
    void checkNotEqualsName(){assertFalse(np.equals(np2));}
    @Test
    void checkNotEqualsBool(){assertFalse(np.equals(np3));}

    @Test
    void checkExceptionImpacts(){
        assertThrows(IllegalArgumentException.class, () -> {
            np2.applyImpact(np);
        });
    }

    @Test
    void checkApplyImpacts(){
        NumericalProperty np6= new NumericalProperty("test", -20.0);
        assertTrue(np6.applyImpact(np).getValue().equals(80.0));
    }


    @Test
    void checkApplyImpactsDelta(){
        NumericalProperty np6= new NumericalProperty("test", -20.0);
        NumericalProperty np7= np6.applyImpact(np);
        assertTrue(np7.getValue().equals(80.0));

    }
}
