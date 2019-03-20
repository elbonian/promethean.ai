package ai.promethian.IntegrationTest;

import ai.promethean.API.API;
import ai.promethean.Planner.Plan;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlanIntegrationTest {
    API api = new API();

    @Test
    void generatePlanNoError() {
        api.generatePlan("JSON_input/InputFiles/test.json", true,"JSON_output/Plans/");
    }

    @Test
    void generatePlanHasPlan() {
        Plan plan = api.generatePlan("JSON_input/InputFiles/test.json", true,"JSON_output/Plans/");
        assertNotNull(plan);
    }


}

