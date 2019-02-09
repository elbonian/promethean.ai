package ai.promethian.DataModel;

import ai.promethean.DataModel.BooleanProperty;
import ai.promethean.DataModel.NumericalProperty;
import ai.promethean.DataModel.Perturbation;
import ai.promethean.DataModel.Resource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PertubationTest {
    private Perturbation p= new Perturbation(100);
    Resource r= new Resource("Time",1000.0);
    NumericalProperty np= new NumericalProperty("Altitude", 1000.0);
    BooleanProperty bp= new BooleanProperty("DoorOpen", false);

    @Test
    void checkGetResource(){
        p.addResource(r);
        assertTrue(p.getResource("Time").getValue()==1000.0);
    }

    @Test
    void checkGetNullResource(){
        assertTrue(p.getResource("Time")==null);
    }

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
