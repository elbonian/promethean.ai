package ai.promethean.API;
import ai.promethean.Parser.*;
import java.util.ArrayList;
import ai.promethean.DataModel.*;
import ai.promethean.Planner.*;


/**
 * This API is meant to be as simple as possible and expandable as possible.
 * To add new functionality define your own function and the arguments it takes.
 * ParserError and PlannerError classes serve to throw descriptive errors from specific components of the system.
 * Example, if we want to add a function to create resource objects, simply create that function and then
 * call it with the resource object that keeps track of the examples
 */

public class API {
    public API(){

    }

    public void throwPlannerError(String err_msg){
        throw new PlannerError(err_msg);
    }

    public void throwParserError(String err_msg){
        throw new ParserError(err_msg);
    }

    public void generatePlan(String inputFile, Boolean isFile){
        Parser p = new Parser("JSON_input/InputFiles/test.json", isFile);
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

