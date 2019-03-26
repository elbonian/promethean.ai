package ai.promethean.ExecutingAgentTest;

import ai.promethean.DataModel.Perturbation;
import ai.promethean.DataModel.SystemState;
import ai.promethean.ExecutingAgent.ClockObserver;
import ai.promethean.ExecutingAgent.PerturbationInjector;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PerturbationInjectorTest {
    @Test
    void testUpdate() {
        String propName = "number";
        SystemState start = new SystemState(0);
        start.addProperty(propName, 0.0);

        SystemState goal = new SystemState(2);
        goal.addProperty(propName, 1.0);

        ClockObserver.addState(start);

        List<Perturbation> perturbationList = new ArrayList<>();

        Perturbation p = new Perturbation(1);
        p.addProperty(propName, 1.0, "delta");
        perturbationList.add(p);

        PerturbationInjector pi1 = new PerturbationInjector(new ArrayList<>());
        assertTrue(pi1.update(1));

        assertEquals(0.0, ClockObserver.peekLastState().getProperty(propName).getValue());

        PerturbationInjector pi2 = new PerturbationInjector(perturbationList);
        pi2.update(2);

        assertEquals(1.0, ClockObserver.peekLastState().getProperty(propName).getValue());
    }
}
