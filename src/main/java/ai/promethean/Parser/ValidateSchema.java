package ai.promethean.Parser;

import java.io.IOException;
import java.io.File;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;



public class ValidateSchema {
    private String json_path;
    private String schema_path;


    public ValidateSchema(){
        setJson("");
        setSchema("");
    }

    public ValidateSchema(String _json_path, String _schema_path){
        setSchema(_schema_path);
        setJson(_json_path);
    }

    public void setJson(String _json_path){ this.json_path=_json_path;  }
    public void setSchema(String _schema_path){ this.schema_path =_schema_path;   }


    public void validate() throws IOException, ProcessingException {
        File jsonFile = new File(this.json_path);
        File schemaFile = new File(this.schema_path);

        if (ValidationUtils.isJsonValid(schemaFile, jsonFile)){
            System.out.println("Valid!");
        }else{
            System.out.println("NOT valid!");
        }

//        try (InputStream inputStream = getClass().getResourceAsStream("/path/to/your/schema.json")) {
//            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));
//            Schema schema = SchemaLoader.load(rawSchema);
//            schema.validate(new JSONObject("{\"hello\" : \"world\"}")); // throws a ValidationException if this object is invalid
//        }
//        JSONObject rawSchema = new JSONObject(new StringReader(this.schema));
//        Schema schema = SchemaLoader.load(rawSchema);
//        schema.validate(new JSONObject("{\"hello\" : \"world\"}"));
    }

}




