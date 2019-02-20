package ai.promethian.DataModel;

import ai.promethean.DataModel.BooleanProperty;
import ai.promethean.DataModel.StringCondition;
import ai.promethean.DataModel.StringProperty;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringPropertyTest {
    private StringProperty sp = new StringProperty("test", "High");
    private StringProperty sp1 = new StringProperty("test", "High");
    private StringProperty sp2 = new StringProperty("test1", "High");
    private StringProperty sp3 = new StringProperty("test", "Low");

    @Test
    void checkEquals() {
        assertTrue(sp.equals(sp));
    }
    @Test
    void checkString(){assertTrue(sp.getValue()=="High");}
    @Test
    void checkName(){assertTrue(sp.getName()=="test");}
    @Test
    void checkEqualDiff(){assertTrue(sp.equals(sp1));}
    @Test
    void checkNotEqualsName(){assertFalse(sp.equals(sp2));}
    @Test
    void checkNotEqualsValue(){assertFalse(sp.equals(sp3));}

    @Test
    void checkExceptionImpacts(){
        assertThrows(IllegalArgumentException.class, () -> {
            sp.applyPropertyImpactOnto(sp2);
        });
    }

   @Test
  void checkApplyImpacts(){
      StringProperty sp6= new StringProperty("test", "Medium",true);
      assertTrue(sp.applyPropertyImpactOnto(sp6).getValue().equals("High"));
 }
}
