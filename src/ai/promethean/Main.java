package ai.promethean;
import ai.promethean.DataModel.*;
public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Hello World");
        BooleanProperty np= new BooleanProperty("doorOpen",  true);
        System.out.println(np.toString());
    }
}
