package ai.promethian.DataModel;

import ai.promethean.DataModel.BooleanProperty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BooleanPropertyTest {

    private BooleanProperty bp = new BooleanProperty("test", true);
    // Checking that imports are working with this file structure
    @Test
    void checkEquals() {
        assertTrue(bp.equals(bp));
    }
}
