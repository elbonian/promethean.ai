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
        Clock clock = new Clock(30, 3);
        clock.runClock();
    }
}
