package ai.promethean;
import ai.promethean.DataModel.*;
import ai.promethean.Parser.Parser;
import ai.promethean.Parser.ValidateSchema;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;

import java.io.IOException;
import ai.promethean.Parser.ValidationUtils;
import java.io.File;


public class Main {

    public static void main(String[] args) throws IOException, ProcessingException {
	// write your code here
        //System.out.println("Hello World");
        //Input File here:
        //Parser p= new Parser("C:\\Users\\Taylor\\Desktop\\promethean.ai\\JSON input\\InputFiles\\States.json", true);
        //p.parse();

        //Parser p= new Parser("C:\\Users\\Sam\\Dropbox\\CSCI4308_Senior_Projects\\promethean.ai\\JSON input\\InputFiles\\States.json", true);
        //p.parse();

        String input = "C:\\Users\\Sam\\Dropbox\\CSCI4308_Senior_Projects\\promethean.ai\\JSON input\\InputFiles\\Perturbations1.json";
        String schema = "C:\\Users\\Sam\\Dropbox\\CSCI4308_Senior_Projects\\promethean.ai\\JSON input\\Schemas\\Perturbation_schema.json";
        ValidateSchema v = new ValidateSchema(input, schema);
        v.validate();

    }
}
