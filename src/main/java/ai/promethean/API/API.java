package ai.promethean.API;
import ai.promethean.Parser.*;
import ai.promethean.API.*;
import java.util.ArrayList;
import ai.promethean.DataModel.*;
import ai.promethean.Planner.*;
import ai.promethean.GraphManagement.*;

/*
    This API is meant to be as simple as possible and expandable as possible.
    If you want to add a new functionality simply add a function to the the API class.
    PlannerError is a custom error throwing class. When throwing a PlannerError make sure to include
    a detailed error message.
    Example, if we want to add a function to create resource objects, simply create that function and then
    call it with the resource object that keeps track of the examples

 */

public class API {
    public API(){

    }

    public void throwError(String err_msg){
        throw new PlannerError(err_msg);
    }

    public void runSimulation(String inputFile){
        /*
        Example execution of graph from Hayden's Graph-Planner Branch. This is just an example of what it
        might look like to call runSimulation, we will be continually updating this
         */
        /*Parser p = new Parser(inputFile, true);
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
            System.out.println("\n");*/
    }

    public void generatePlan(String inputFile){
        //TODO
        //These currently serve as examples and only call the parser, need to link up with entire system
        Parser p = new Parser(inputFile, true);
        ArrayList<Object> parsedObjects = p.parse();
        System.out.println(parsedObjects);
    }

}

