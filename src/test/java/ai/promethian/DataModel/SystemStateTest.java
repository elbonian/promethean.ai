package ai.promethian.DataModel;

import ai.promethean.DataModel.BooleanProperty;
import ai.promethean.DataModel.NumericalProperty;
import ai.promethean.DataModel.Resource;
import ai.promethean.DataModel.SystemState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SystemStateTest {
    SystemState s= new SystemState(1);
    Resource r= new Resource("Time",1000.0);
    NumericalProperty np= new NumericalProperty("Altitude", 1000.0);
    BooleanProperty bp= new BooleanProperty("DoorOpen", false);
    SystemState goal= new SystemState(2);

    @Test
    void checkUID(){assertTrue(s.getUID()==1);}

    @Test
    void checkTimeExists(){assertTrue(s.getTimeStamp()!=null);}

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




}
