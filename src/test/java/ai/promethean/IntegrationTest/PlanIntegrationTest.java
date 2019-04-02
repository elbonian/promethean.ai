package ai.promethean.IntegrationTest;

import ai.promethean.API.API;
import ai.promethean.Planner.Plan;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PlanIntegrationTest {
    API api = new API();

    @Test
    void generatePlanNoError() {
        api.executePlan("JSON_input/InputFiles/Test.json", true);
    }

    @Test
    void generatePlanHasPlan() {
        Map<String, Object> objects= api.parseInput("JSON_input/InputFiles/Test.json", true);
        Plan plan = api.generatePlanFromParsedObjects(objects);
        assertNotNull(plan);
    }


}

