package ai.promethian.DataModel;

import ai.promethean.DataModel.AbstractCondition;
import ai.promethean.DataModel.BooleanCondition;
import ai.promethean.DataModel.Condition;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;




public class ConditionTest {
    private Condition c = new Condition("Altitude", 100, ">");
    private Condition c1 = new Condition("Fuel", 3000, "<");
    private Condition c2 = new Condition("Fuel", 300, ">=");

    private BooleanCondition bc= new BooleanCondition("DoorOpen", "==", false);

    @Test
    void checkGreater() { assertTrue(c.evaluate(150));}
    @Test
    void checkGreaterFalse() { assertFalse(c.evaluate(50));}
    @Test
    void checkLess() { assertTrue(c1.evaluate(500));}
    @Test
    void checkGreaterEq() { assertTrue(c2.evaluate(500));}
    @Test
    void checkEQ() {assertTrue(c2.evaluate(300));}
    @Test
    void checkNQ() {assertFalse(c2.evaluate(200));}

    @Test
    void checkName(){
        assertTrue(bc.getName()=="DoorOpen");
    }

    @Test
    void checkOperator(){
        assertTrue(bc.getOperator()=="==");
    }

    @Test
    void checkBoolValue(){
        assertTrue(bc.getValue()==false);
    }

    @Test
    void checkEquals(){
        assertTrue(bc.evaluate(false));
    }

    @Test
    void checkNonEqualsBool(){
        assertFalse(bc.evaluate(true));
    }

    @Test
    void checkNonEqualsObject(){
        assertFalse(bc.evaluate(0));
    }


    @Test
    void checkException(){
        assertThrows(IllegalArgumentException.class, () -> {
            BooleanCondition bc1= new BooleanCondition("DoorClosed", ">", false);
        });

    }

}
