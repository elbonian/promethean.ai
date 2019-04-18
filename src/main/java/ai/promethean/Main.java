package ai.promethean;

import ai.promethean.API.*;



public class Main {

    public static void main(String[] args) {
        API api = new API();
        api.executePlan("JSON_input/TestCases/one_path.json", true, "JSON_output/Plans/","JSON_output/SystemStates/");
    }
}
