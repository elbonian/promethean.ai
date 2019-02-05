package ai.promethean.Parser;
import ai.promethean.DataModel.BooleanProperty;
import ai.promethean.DataModel.Optimization;
import ai.promethean.DataModel.SystemState;
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
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();

            String name = jsonObject.get("name").getAsString();

            Boolean isMin = jsonObject.get("isMinimum").getAsBoolean();

           System.out.println("name: "+ name);
            System.out.println("isMinimum: "+ isMin);
            Optimization o= new Optimization(name, isMin);
            System.out.println(o);
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
