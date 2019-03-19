package ai.promethean;


import ai.promethean.API.*;

import java.io.IOException;


public class Main {

    public static void main(String[] args) {
        API api = new API();
        api.generatePlan("JSON_input/InputFiles/test.json", true,"JSON_output/Plans/");


    }
}
