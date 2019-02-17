package ai.promethian.DataModel;

import ai.promethean.DataModel.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SystemStateTest {
    SystemState s= new SystemState(1);
    NumericalProperty np= new NumericalProperty("Altitude", 1000.0);
    BooleanProperty bp= new BooleanProperty("DoorOpen", false);
    SystemState goal= new SystemState(2);
    Task t= new Task(22, 1000);

    @Test
    void checkUID(){assertTrue(s.getUID()==1);}

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
        assertTrue(goal.getPreviousState().getUID()==1);
    }

    @Test
    void checkValidPrevTask(){
        s.setPreviousTask(t);
        assertTrue(s.getPreviousTask().getUID()==22);
    }

    @Test
    void checkUndefinedGValue(){
        assertTrue(s.getgValue()==-1);
    }

    @Test
    void checkDefinedGValue(){
        SystemState s1= new SystemState(20,false);
        assertTrue(s1.getgValue()==0);
    }

    @Test
    void checkGoalGValue(){
        SystemState s1= new SystemState(21,true);
        assertTrue(s1.getgValue()==-1);
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
