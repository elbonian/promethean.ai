package ai.promethean;
import ai.promethean.Parser.*;

import java.io.IOException;
import java.util.ArrayList;
import java.time.*;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        System.out.println("Hello World");
        Clock baseclock = Clock.systemDefaultZone();
        Instant instant = baseclock.instant();
        System.out.println(instant);
    }
}
