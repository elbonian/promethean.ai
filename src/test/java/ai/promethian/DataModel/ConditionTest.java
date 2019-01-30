package ai.promethian.DataModel;

import ai.promethean.DataModel.Condition;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ConditionTest {
    private Condition c1 = new Condition(1, 1, "==");
    private Condition c2 = new Condition(2, 1, ">");
    private Condition c3 = new Condition(1, 2, "<");
    private Condition c4 = new Condition(1, 8, "!=");
    private Condition c5 = new Condition(2, 1, ">=");
    private Condition c6 = new Condition(1, 2, "<=");

    @Test
    void checkEquals() { assertTrue(c1.evaluate(1, 1, "=="));}

    @Test
    void checkGreater() { assertTrue(c2.evaluate(2, 1, ">"));}

    @Test
    void checkLeast() { assertTrue(c3.evaluate(1, 2, "<"));}

    @Test
    void checkNotEquals() { assertTrue(c4.evaluate(1, 8, "!="));}

    @Test
    void checkGreaterEq() { assertTrue(c5.evaluate(2, 1, ">="));}

    @Test
    void checkLessEq() { assertTrue(c6.evaluate(1, 2, "<="));}
}
