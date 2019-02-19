package ai.promethean;
import ai.promethean.Parser.*;

import java.io.IOException;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        System.out.println("Hello World");
        //Input File here:
        Parser p= new Parser("C:\\Users\\Taylor\\Desktop\\promethean.ai\\JSON input\\InputFiles\\Sample_Json_new.json", true);
        ArrayList<Object> objects = p.parse();
        for(Object o: objects){
           System.out.println(o);
        }

    }
}
