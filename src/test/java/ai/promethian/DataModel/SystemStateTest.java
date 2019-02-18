package ai.promethian.DataModel;

import ai.promethean.DataModel.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SystemStateTest {
    SystemState s= new SystemState();
    SystemState s1= new SystemState();
    NumericalProperty np= new NumericalProperty("Altitude", 1000.0);
    BooleanProperty bp= new BooleanProperty("DoorOpen", false);
    SystemState goal= new SystemState();
    Task t= new Task( 1000);



    @Test
    void checkTimeExists(){assertTrue(s.getTime()==0);}


    @Test
    void checkNullPrevSystemState(){
        assertTrue(s.getPreviousState()==null);
    }

    @Test
    void checkNullPrevTask(){
        assertTrue(s.getPreviousTask()==null);
    }

    @Test

    void checkValidPrevSystemState(){
        goal.setPreviousState(s);
        assertTrue(goal.getPreviousState().getTime()==0);
    }

    @Test
    void checkValidPrevTask(){
        s.setPreviousTask(t);
        assertTrue(s.getPreviousTask().getDuration()==1000);
    }

    @Test
    void checkBaseGValue(){
        assertTrue(s.getgValue()==0.0);
    }



    @Test
    void checkGetProperty(){
        s.addProperty(bp);
        assertTrue(s.getProperty("DoorOpen").getValue().equals(false));
    }

    @Test
    void checkGetProperty1(){
        s.addProperty(np);
        assertTrue(s.getProperty("Altitude").getValue().equals(1000.0));
    }

    @Test
    void checkGetPropertyNull(){
        assertTrue(s.getProperty("Altitude")==null);
    }

    

}
