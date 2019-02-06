package ai.promethean.Parser;


import ai.promethean.DataModel.*;
import com.google.gson.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class Parser {
    private JsonParser parser = new JsonParser();
    private String json;


    public Parser(){
        setJson("");
    }

    public Parser(String _json, Boolean isFile){
        if(!isFile) {
            setJson(_json);
        }
        else{
            try
            {
                String content = new String(Files.readAllBytes(Paths.get(_json)), StandardCharsets.ISO_8859_1);
               setJson(content);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        System.out.println(json);
    }


    public void setJson(String _json){
        this.json=_json;
    }

    public void parse() {
        JsonElement jsonTree = parser.parse(json);
        JsonArray jsonArray = jsonTree.getAsJsonArray();
        if (jsonTree.isJsonArray()) {
            for(JsonElement j: jsonArray) {
                JsonObject jsonObject= j.getAsJsonObject();
                String title = jsonObject.get("title").getAsString();
                
                if (title.equalsIgnoreCase("Optimization")) {
                    String name = jsonObject.get("name").getAsString();
                    Boolean isMin = jsonObject.get("isMinimum").getAsBoolean();

                    Optimization o = new Optimization(name, isMin);
                    System.out.println(o);
                } else if (title.equalsIgnoreCase("State")) {
                    int UID = jsonObject.get("UID").getAsInt();
                    long time = jsonObject.get("time").getAsLong();
                    SystemState systemState = new SystemState(UID, time);
                    System.out.println(systemState);
                    //TODO resources and properties

                } else if (title.equalsIgnoreCase("Task")) {
                    int UID = jsonObject.get("UID").getAsInt();
                    int duration = jsonObject.get("duration").getAsInt();
                    //TODO requirements, resources and properties
                } else if (title.equalsIgnoreCase("Perturbation")) {
                    long time = jsonObject.get("time").getAsLong();
                    //TODO resources and properties
                } else {
                    throw new IllegalArgumentException("JSON Object title does not exist");
                }
            }
//
//            if (f2.isJsonObject()) {
//                JsonObject f2Obj = f2.getAsJsonObject();
//
//                JsonElement f3 = f2Obj.get("f3");
//                System.out.println("f3: "+ f3);
//            }

        }
    }
}
