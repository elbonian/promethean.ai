package ai.promethian.DataModel;

import ai.promethean.DataModel.Condition;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ConditionTest {
    private Condition c = new Condition("Altitude", 100, ">");
    private Condition c1 = new Condition("Fuel", 3000, "<");
    private Condition c2 = new Condition("Fuel", 300, ">=");

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
    
}
