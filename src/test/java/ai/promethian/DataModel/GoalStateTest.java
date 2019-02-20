package ai.promethian.DataModel;
import ai.promethean.DataModel.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GoalStateTest {
    GoalState gs= new GoalState();
    NumericalProperty np= new NumericalProperty("Altitude", 1000.0);
    BooleanProperty bp= new BooleanProperty("DoorOpen", false);
    SystemState s= new SystemState(1);
    NumericalCondition nc= new NumericalCondition("Altitude", ">", 900.0);
    BooleanCondition bc= new BooleanCondition("DoorOpen", "==", false);

    @Test
    void checkRequirements(){assertTrue(gs.getRequirements()!=null);}

    @Test
    void checkRequirementName(){
        gs.addRequirement(nc);
        assertTrue(gs.getRequirement("Altitude").getName().equals("Altitude"));
    }

    @Test
    void checkRequirementValue(){
        gs.addRequirement(nc);
        assertTrue(gs.getRequirement("Altitude").getValue().equals(900.0));
    }

    @Test
    void checkRequirementNull(){
        assertTrue(gs.getRequirement("Altitude")==null);
    }

    @Test
    void checkGoalEmptyState(){
        gs.addRequirement(nc);
        assertFalse(gs.meetsGoal(s));
    }

    @Test
    void checkGoalTrue(){
        gs.addRequirement(nc);
        s.addProperty(np);
        assertTrue(gs.meetsGoal(s));
    }

    @Test
    void checkGoalTrue1(){
        gs.addRequirement(nc);
        s.addProperty(np);
        s.addProperty(bp);
        assertTrue(gs.meetsGoal(s));
    }

    @Test
    void checkGoalTrue2(){
        s.addProperty(np);
        assertTrue(gs.meetsGoal(s));
    }
    @Test
    void checkGoalTrue3(){
        gs.addRequirement(nc);
        gs.addRequirement(bc);
        s.addProperty(bp);
        s.addProperty(np);
        assertTrue(gs.meetsGoal(s));
    }

    @Test
    void checkGoalFalse(){
        gs.addRequirement(nc);
        assertFalse(gs.meetsGoal(s));
    }

    @Test
    void checkGoalFalse1(){
        gs.addRequirement(nc);
        s.addProperty(bp);
        assertFalse(gs.meetsGoal(s));
    }

    @Test
    void checkGoalFalse2(){
        gs.addRequirement(bc);
        gs.addRequirement(nc);
        s.addProperty(bp);
        assertFalse(gs.meetsGoal(s));
    }

    @Test
    void checkGoalFalse3(){
        gs.addRequirement("Altitude", 50.0, "<");
        s.addProperty(np);
        assertFalse(gs.meetsGoal(s));
    }

    @Test
    void checkGoalFalse4(){
        gs.addRequirement(bc);
        gs.addRequirement("Altitude", 50.0, "<");
        s.addProperty(np);
        s.addProperty(bp);
        assertFalse(gs.meetsGoal(s));
    }

    @Test
    void checkGoalFalse5(){
        gs.addRequirement(bc);
        s.addProperty("DoorOpen", true);
        assertFalse(gs.meetsGoal(s));
    }
}
