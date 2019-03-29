package ai.promethean.DataModel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PertubationTest {
    private Perturbation p= new Perturbation(100);
    NumericalProperty np= new NumericalProperty("Altitude", 1000.0);
    BooleanProperty bp= new BooleanProperty("DoorOpen", false);



    @Test
    void checkGetProperty(){
        p.addProperty(bp);
        assertTrue(p.getProperty("DoorOpen").getValue().equals(false));
    }

    @Test
    void checkGetProperty1(){
        p.addProperty(np);
        assertTrue(p.getProperty("Altitude").getValue().equals(1000.0));
    }

    @Test
    void checkGetPropertyNull(){
        assertTrue(p.getProperty("Altitude")==null);
    }
}
