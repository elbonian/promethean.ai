package ai.promethean.Output;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ai.promethean.API.API;
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
            //For now, create a unique file name with the current date
            //Might include file prefix later as an argument
            String fileName= filePath + "Plan-" + new Date()+".json";
            fileName = fileName.replace(" ", "");
            fileName = fileName.replace(":", "");

            mapper.writeValue(new File(fileName), o);
        }catch(IOException io){
            API api= new API();
            api.throwOutputError("Invalid Output File Path");
        }
    }
}

