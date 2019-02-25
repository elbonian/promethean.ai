package ai.promethian.DataModel;

import ai.promethean.DataModel.BooleanProperty;
import ai.promethean.DataModel.StringCondition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void checkDelta(){assertTrue(bp.getType().equals("assignment"));}

    @Test
    void checkChangeDelta(){
        BooleanProperty bp5= new BooleanProperty("doorClosed", false, "delta");
        assertTrue(bp5.getType().equals("delta"));
    }
    @Test
    void checkExceptionImpacts(){
        assertThrows(IllegalArgumentException.class, () -> {
            bp.applyPropertyImpactOnto(bp2);
        });
    }

    @Test
    void checkApplyImpacts(){
        BooleanProperty bp6= new BooleanProperty("test", false,"delta");

        assertTrue(bp.applyPropertyImpactOnto(bp6).getValue());
    }
}
