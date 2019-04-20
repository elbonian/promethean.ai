package ai.promethean;

import ai.promethean.API.API;
import ai.promethean.CLI.CLI;
import ai.promethean.DataModel.*;
import ai.promethean.TestCaseGenerator.TestCaseGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        CLI.exec(args);
    }
}
