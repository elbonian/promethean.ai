package ai.promethean;
import ai.promethean.Parser.*;
import ai.promethean.API.*;
import java.io.IOException;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        System.out.println("Hello World");
        //API api = new API("run", "C:\\Users\\Sam\\Dropbox\\CSCI4308_Senior_Projects\\promethean.ai\\JSON input\\InputFiles\\Example_Json_2.json");
        //api.processCommand();
        API api = new API("error", "it's not working help");
        api.processCommand();
    }
}
