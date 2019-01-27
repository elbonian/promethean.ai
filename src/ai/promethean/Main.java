package ai.promethean;
import ai.promethean.DataModel.*;

import java.util.SplittableRandom;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Hello World");
        BooleanProperty np= new BooleanProperty("doorOpen",  true);
        System.out.println(np.toString());

        Resource r1= new Resource("Time",60.0);
        Resource r2= new Resource("Time", 70.0);
        System.out.println(r1.equals(r2));

        NumericalProperty np1= new NumericalProperty("doorOpen",1.0);
        BooleanProperty np2= new BooleanProperty("doorOpen",  false);
        System.out.println(np.equals(np2));
    }
}
