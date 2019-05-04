package ai.promethean.IntegrationTest;

import ai.promethean.API.API;
import ai.promethean.Planner.Plan;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PlanIntegrationTest {

    @Test
    void generatePlanNoError() {
        API.executePlan("JSON_input/InputFiles/Test.json", true, 5, true);
    }

    @Test
    void generatePlanHasPlan() {
        Map<String, Object> objects= API.parseInput("JSON_input/InputFiles/Test.json", true);
        Plan plan = API.generatePlanFromParsedObjects(objects, 5, true);
        assertNotNull(plan);
    }


}

