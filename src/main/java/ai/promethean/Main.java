package ai.promethean;
import ai.promethean.DataModel.GoalState;
import ai.promethean.DataModel.StaticOptimizations;
import ai.promethean.DataModel.SystemState;
import ai.promethean.DataModel.TaskDictionary;
import ai.promethean.Parser.*;
import ai.promethean.Planner.*;

import java.io.IOException;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws IOException {
        Parser p = new Parser("/Users/nix/workspace/git/promethean.ai/JSON_input/InputFiles/test.json", true);
        ArrayList<Object> objects = p.parse();
        Algorithm algo = new AStar((SystemState) objects.get(1),
                                    (GoalState) objects.get(2),
                                    (TaskDictionary) objects.get(3),
                                    (StaticOptimizations) objects.get(0));

        Planner planner = new Planner(algo);
        Plan plan = planner.plan();

        System.out.println("\nInitial State:\n======================");
        System.out.println(plan.getInitialState());
        System.out.println("\nRuntime Goal State:\n======================");
        System.out.println(plan.getGoalState());
        System.out.println("\nPlan:\n======================");
        ArrayList<PlanBlock> list = plan.getPlanBlockList();
        for (PlanBlock block: list) {
            System.out.println(block.getTask());
            System.out.println("\n");
            System.out.println(block.getState());
            System.out.println("\n");
        }

    }
}
