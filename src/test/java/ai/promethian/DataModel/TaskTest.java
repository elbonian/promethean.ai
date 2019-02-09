package ai.promethian.DataModel;
import ai.promethean.DataModel.BooleanProperty;
import ai.promethean.DataModel.NumericalProperty;
import ai.promethean.DataModel.Resource;
import ai.promethean.DataModel.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TaskTest {
    private Task t1= new Task(1, 10);
    Resource r= new Resource("Time",1000.0);
    NumericalProperty np= new NumericalProperty("Altitude", 1000.0);
    BooleanProperty bp= new BooleanProperty("DoorOpen", false);

    @Test
    void checkEquals() { assertEquals(t1.getDuration(),(10));}

    @Test
    void checkGetResource(){
        t1.addResource(r);
        assertTrue(t1.getResource("Time").getValue()==1000.0);
    }

    @Test
    void checkGetNullResource(){
        assertTrue(t1.getResource("Time")==null);
    }

    @Test
    void checkGetProperty(){
        t1.addProperty(bp);
        assertTrue(t1.getProperty("DoorOpen").getValue().equals(false));
    }

    @Test
    void checkGetProperty1(){
        t1.addProperty(np);
        assertTrue(t1.getProperty("Altitude").getValue().equals(1000.0));
    }
}


