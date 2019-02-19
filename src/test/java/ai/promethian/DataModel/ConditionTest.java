package ai.promethian.DataModel;

import ai.promethean.DataModel.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




public class ConditionTest {
    private NumericalCondition c = new NumericalCondition("Altitude", ">", 100.0);
    private NumericalCondition c1 = new NumericalCondition("Fuel", "<",3000.0);
    private NumericalCondition c2 = new NumericalCondition("Fuel", ">=", 300.0);

    private BooleanCondition bc= new BooleanCondition("DoorOpen", "==", false);
    private StringCondition sc= new StringCondition("WindSpeed", "==", "High");

    @Test
    void checkGreater() { assertTrue(c.evaluate(150.0));}
    @Test
    void checkGreaterFalse() { assertFalse(c.evaluate(50.0));}
    @Test
    void checkLess() { assertTrue(c1.evaluate(500.0));}
    @Test
    void checkGreaterEq() { assertTrue(c2.evaluate(500.0));}
    @Test
    void checkEQ() {assertTrue(c2.evaluate(300.0));}
    @Test
    void checkNQ() {assertFalse(c2.evaluate(200.0));}
    @Test
    void checkDiffObject(){assertFalse(c2.evaluate(false));}
    @Test
    void checkExceptionNumerical(){
        assertThrows(IllegalArgumentException.class, () -> {
            new NumericalCondition("DoorClosed", "!<=", 100.1);
        });
    }
    @Test
    void checkNameBool(){
        assertTrue(bc.getName()=="DoorOpen");
    }
    @Test
    void checkOperatorBool(){
        assertTrue(bc.getOperator()=="==");
    }
    @Test
    void checkBoolValue(){
        assertTrue(bc.getValue()==false);
    }
    @Test
    void checkEqualsBool(){
        assertTrue(bc.evaluate(false));
    }
    @Test
    void checkNonEqualsBool(){
        assertFalse(bc.evaluate(true));
    }
    @Test
    void checkNonEqualsObjectBool(){
        assertFalse(bc.evaluate(0));
    }
    @Test
    void checkExceptionBool(){
        assertThrows(IllegalArgumentException.class, () -> {
            BooleanCondition bc1= new BooleanCondition("DoorClosed", ">", false);
        });

    }
    @Test
    void checkNameString(){
        assertTrue(sc.getName()=="WindSpeed");
    }
  @Test
   void checkOperatorString(){
        assertTrue(sc.getOperator()=="==");
    }
    @Test
    void checkStringValue(){
        assertTrue(sc.getValue()=="High");
    }

    @Test
    void checkEqualsString(){
        assertTrue(sc.evaluate("High"));
    }
    @Test
    void checkNonEqualsString(){
        assertFalse(sc.evaluate("Low"));
    }
    @Test
    void checkNonEqualsObjectString(){
        assertFalse(bc.evaluate(true));
    }
    @Test
    void checkExceptionString(){
        assertThrows(IllegalArgumentException.class, () -> {
            StringCondition sc1= new StringCondition("DoorClosed", ">", "High");
        });
    }
}
