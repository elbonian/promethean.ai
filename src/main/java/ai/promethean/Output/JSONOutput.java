package ai.promethean.Output;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.*;

public class JSONOutput implements Output {

    /**
     * This function takes in a Java object and transcribes it into a JSON file
     * @param o: Java object to write to file
     * @param filePath: The desired location of the output file
     */
    public void writeToFile(Object o, String filePath){
        ObjectMapper mapper= new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File(filePath), o);
        }catch(IOException io){
            System.out.println("error");
        }
    }
}

