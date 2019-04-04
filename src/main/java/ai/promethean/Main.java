package ai.promethean;

import ai.promethean.API.*;
import ai.promethean.Logger.Logger;


public class Main {

    public static void main(String[] args) {
        API api = new API();
        Logger.logFlag = true;
        Logger.printFlag = true;
        api.executePlan("JSON_input/InputFiles/PerturbationTest.json", true, "JSON_output/Plans/","JSON_output/SystemStates/");
    }
}
