package ai.promethian.DataModel;

import ai.promethean.DataModel.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SystemStateTest {
    SystemState s= new SystemState(1);
    Resource r= new Resource("Time",1000.0);
    NumericalProperty np= new NumericalProperty("Altitude", 1000.0);
    BooleanProperty bp= new BooleanProperty("DoorOpen", false);
    SystemState goal= new SystemState(2);
    Task t= new Task(22, 1000);

    @Test
    void checkUID(){assertTrue(s.getUID()==1);}

    @Test
    void checkTimeExists(){assertTrue(s.getTime()==0);}

    @Test
    void checkResource(){assertTrue(s.getResources().size()==0);}

    @Test
    void checkAddResource(){
        s.addResource(r);
        assertTrue(s.getResources().size()==1);
    }

    @Test
    void checkResourceName(){
        s.addResource(r);
        assertTrue(s.getResources().get(0).getName()=="Time");
    }

    @Test
    void checkEquals(){
        s.addResource(r);
        goal.addResource(r);
        assertTrue(s.equals(goal));
    }

    @Test
    void checkNotEquals(){
        s.addResource(r);
        assertFalse(s.equals(goal));
    }

    @Test
    void checkContainsGoal(){
        s.addResource(r);
        s.addProperty(np);
        s.addProperty(bp);
        assertTrue(s.containsGoalState(goal));
    }

    @Test
    void checkContainsGoal1(){
        s.addResource(r);
        s.addProperty(np);
        s.addProperty(bp);
        goal.addResource(r);
        assertTrue(s.containsGoalState(goal));
    }

    @Test
    void checkContainsGoal2(){
        s.addResource(r);
        s.addProperty(np);
        s.addProperty(bp);
        goal.addResource(r);
        goal.addProperty(np);
        assertTrue(s.containsGoalState(goal));
    }

    @Test
    void checkContainsGoal3(){
        s.addResource(r);
        s.addProperty(np);
        s.addProperty(bp);
        goal.addResource(r);
        goal.addProperty(np);
        goal.addProperty(bp);
        assertTrue(s.containsGoalState(goal));
    }

    @Test
    void checkNotContainsGoal(){
        s.addResource(r);
        s.addProperty(np);
        goal.addResource(r);
        goal.addProperty(np);
        goal.addProperty(bp);
        assertFalse(s.containsGoalState(goal));
    }

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
    void checkGetResource(){
        s.addResource(r);
        assertTrue(s.getResource("Time").getValue()==1000.0);
    }

    @Test
    void checkGetNullResource(){
        assertTrue(s.getResource("Time")==null);
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
