package ai.promethean;
import ai.promethean.DataModel.GoalState;
import ai.promethean.DataModel.StaticOptimizations;
import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.TaskDictionary;
import ai.promethean.Parser.*;
import ai.promethean.Planner.*;
import ai.promethean.ExecutingAgent.*;

import ai.promethean.API.*;
import java.io.IOException;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        API api = new API();

        api.executePlan("JSON_input/InputFiles/test.json", true);
    }
}
