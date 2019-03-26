package ai.promethean.DataModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanPropertyTest {

    private BooleanProperty bp = new BooleanProperty("test", true);
    private BooleanProperty bp1 = new BooleanProperty("test", true);
    private BooleanProperty bp2 = new BooleanProperty("test1", true);
    private BooleanProperty bp3 = new BooleanProperty("test", false);
    // Checking that imports are working with this file structure
    @Test
    void checkEquals() {
        assertTrue(bp.equals(bp));
    }
    @Test
    void checkBool(){assertTrue(bp.getValue());}
    @Test
    void checkName(){assertTrue(bp.getName()=="test");}
    @Test
    void checkEqualDiff(){assertTrue(bp.equals(bp1));}
    @Test
    void checkNotEqualsName(){assertFalse(bp.equals(bp2));}
    @Test
    void checkNotEqualsBool(){assertFalse(bp.equals(bp3));}

    @Test
    void checkDelta(){assertNull(bp.getType());}
    @Test
    void checkChangeDelta(){
        BooleanProperty bp5= new BooleanProperty("doorClosed", false, "delta");
        assertTrue(bp5.getType().equals("delta"));
    }
    @Test
    void checkExceptionImpacts(){
        assertThrows(IllegalArgumentException.class, () -> {
            bp2.applyImpact(bp);
        });
    }

    @Test
    void checkApplyImpacts(){
        BooleanProperty bp6= new BooleanProperty("test", false,"delta");

        assertTrue(bp6.applyImpact(bp).getValue());
    }
}
