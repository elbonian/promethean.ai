package ai.promethean.Parser;

import ai.promethean.API.API;
import ai.promethean.DataModel.*;
import com.google.gson.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JSONParser implements ParserInterface{
    //Constants specific to the JSON language
    private static final String OPTIMIZATION_FIELD= "optimizations";
    private static final String INITSTATE_FIELD= "initial_state";
    private static final String GOALSTATE_FIELD= "goal_state";
    private static final String TASK_FIELD= "tasks";
    private static final String PERTURBATION_FIELD= "perturbations";
    private static final String PROPERTY_IMPACTS_FIELD= "property_impacts";

    private static final String FIELD_NAME = "name";
    private static final String FIELD_OPERATOR = "operator";
    private static final String FIELD_PRIORITY= "priority";
    private static final String FIELD_VALUE = "value";
    private static final String FIELD_DURATION = "duration";
    private static final String FIELD_REQUIREMENTS = "requirements";
    private static final String DELTA = "delta";

    private JsonParser parser = new JsonParser();
    private String json;


    private Map<String, Object> parsedObjects = new HashMap<>();


    private TaskDictionary taskDictionary = new TaskDictionary();
    private StaticOptimizations optimizationList =  new StaticOptimizations();
    private List<Object> perturbationList =  new ArrayList<>();
    private API api= new API();

    /* Parser set JSON with input
     * @param   json    Either the json string or the file path for a json file
     * @param   isFile   Denotes whether _json is a json string or file path
     */

    public void setJson(String json, Boolean isFile){
        if(!isFile) {
            this.json=json;
        }
        else{
            try
            {
                String content = new String(Files.readAllBytes(Paths.get(json)), StandardCharsets.ISO_8859_1);
                this.json= content;
            }
            catch (IOException e)
            {

               api.throwParserError("Invalid File Path");
            }

        }

    }

    /* Parse function parses the class's json string into data model classes
    * @return parsedObject list which contains:
    * Initial SystemState Object (Required)
    * GoalState Object (Required)
    * TaskDictionary Object (Required)
    * OptimizationList Object (Optional)
    * List of Perturbation Objects (Optional)
    */

    public Map<String, Object> parse(String json, Boolean isFile) {
        setJson(json,isFile);
        JsonElement jsonTree = parser.parse(this.json);
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();

            //Section that parses json into optimization objects
            //Check null because optimizations is an optional field
            if (jsonObject.get(OPTIMIZATION_FIELD) != null) {
                JsonElement optimizationArray = jsonObject.get(OPTIMIZATION_FIELD);

                //Iterate through every optimization json object and create objects
                if (optimizationArray.isJsonArray()) {
                    JsonArray optimizations= optimizationArray.getAsJsonArray();
                    for (JsonElement op : optimizations) {
                        JsonObject optimization = op.getAsJsonObject();
                        if (!(optimization.get(FIELD_NAME)==null || !optimization.get(FIELD_NAME).getAsJsonPrimitive().isString())) {
                            String name = optimization.get(FIELD_NAME).getAsString();
                            if (!(optimization.get(FIELD_PRIORITY)==null ||!optimization.get(FIELD_PRIORITY).getAsJsonPrimitive().isNumber())) {
                                int priority = optimization.get(FIELD_PRIORITY).getAsInt();

                                //Captures any type containing min (Case Indifferent)
                                if(optimization.get("type")==null){
                                    api.throwParserError("JSON Optimization type must be non-null");
                                }
                                if (optimization.get("type").getAsString().toLowerCase().contains("min")) {
                                    Optimization o = new Optimization(name, "min", priority);
                                    optimizationList.addOptimization(o);

                                    //Captures any type containing max (Case insensitive)
                                } else if (optimization.get("type").getAsString().toLowerCase().contains("max")) {
                                    Optimization o = new Optimization(name, "max", priority);
                                    optimizationList.addOptimization(o);
                                } else {
                                    api.throwParserError("JSON Object Optimization type is invalid (must be a minimum or maximum)");
                                }
                            } else {
                                api.throwParserError("JSON Object Optimization Priority must be a number and non-null");
                            }
                        } else {
                            api.throwParserError("JSON Optimization name must be a string and non-null");
                        }
                    }
                } else {
                    api.throwParserError("JSON optimizations must be an array");
                }
                //Sort optimizations by priority
                //Add parsed optimization Java objects to return list
                optimizationList.sortOptimizations();
                parsedObjects.put("optimizations",optimizationList);
            }

            //Section that parses json into an initial SystemState object (Required field)
            if(jsonObject.get(INITSTATE_FIELD)==null){
                api.throwParserError("Missing initial_state JSON object");
            }
            JsonObject initialState = jsonObject.get(INITSTATE_FIELD).getAsJsonObject();
            SystemState systemState = new SystemState();
            if (initialState.get("properties") != null) {
                JsonArray properties = initialState.get("properties").getAsJsonArray();
                for (JsonElement elem : properties) {
                    JsonObject property = elem.getAsJsonObject();
                    if (!(property.get(FIELD_NAME)==null || !property.get(FIELD_NAME).getAsJsonPrimitive().isString()) ){
                        String name = property.get(FIELD_NAME).getAsString();
                        if(property.get(FIELD_VALUE)==null){
                            api.throwParserError("Initial State Property value must be non-null");
                        }
                        JsonPrimitive value = property.get(FIELD_VALUE).getAsJsonPrimitive();
                        if (value.getAsJsonPrimitive().isBoolean()) {
                            systemState.addProperty(name, value.getAsBoolean());
                        } else if (value.getAsJsonPrimitive().isNumber()) {
                            systemState.addProperty(name, value.getAsDouble());
                        } else if (value.getAsJsonPrimitive().isString()) {
                            systemState.addProperty(name, value.getAsString());
                        } else {
                            api.throwParserError("Invalid property type; must be a number, string, or bool");
                        }
                    } else {
                        api.throwParserError("Invalid State Property name type; must be a string and non-null");
                    }
                }
            }
            //Add parsed initial SystemState object to return list
            parsedObjects.put("initialState",systemState);

            //Section parses json into GoalState object (required field)
            if(jsonObject.get(GOALSTATE_FIELD)==null){
                api.throwParserError("Missing goal_state JSON object");
            }
            JsonObject gs = jsonObject.get(GOALSTATE_FIELD).getAsJsonObject();
            GoalState goalState = new GoalState();
            if (gs.get(FIELD_REQUIREMENTS) != null) {
                JsonArray requirements = gs.get(FIELD_REQUIREMENTS).getAsJsonArray();
                for (JsonElement elem : requirements) {
                    JsonObject requirement = elem.getAsJsonObject();
                    if (!(requirement.get(FIELD_NAME)==null || requirement.get(FIELD_OPERATOR)==null|| !requirement.get(FIELD_NAME).getAsJsonPrimitive().isString() || !requirement.get(FIELD_OPERATOR).getAsJsonPrimitive().isString())) {
                        String name = requirement.get(FIELD_NAME).getAsString();
                        String operator = requirement.get(FIELD_OPERATOR).getAsString();
                        if(requirement.get(FIELD_VALUE)==null){
                            api.throwParserError("Goal State requirement value must be non-null");
                        }
                        JsonPrimitive value = requirement.get(FIELD_VALUE).getAsJsonPrimitive();
                        if (value.getAsJsonPrimitive().isBoolean()) {
                            goalState.addRequirement(name, value.getAsBoolean(), operator);
                        } else if (value.getAsJsonPrimitive().isNumber()) {
                            goalState.addRequirement(name, value.getAsDouble(), operator);
                        } else if (value.getAsJsonPrimitive().isString()) {
                            goalState.addRequirement(name, value.getAsString(), operator);
                        } else {
                            api.throwParserError("Invalid Goal State requirement value type; must be a number, string or boolean");
                        }
                    } else {
                        api.throwParserError("Invalid Goal State requirement name/operator type; must be strings and non-null");
                    }
                }
            }
            //Add java GoalSate object to return list
            parsedObjects.put("goalState",goalState);

            //Section parses json objects into Task java objects (required field)
            if(jsonObject.get(TASK_FIELD)==null){
                api.throwParserError("Missing tasks JSON object");
            }
            JsonElement taskList = jsonObject.get(TASK_FIELD);

            //Iterate through every task json object and create java objects
            if (taskList.isJsonArray()) {
                JsonArray tasks= taskList.getAsJsonArray();
                for (JsonElement taski : tasks) {
                    JsonObject t = taski.getAsJsonObject();
                    if(t.get(FIELD_DURATION)==null){
                        api.throwParserError("Task duration must be non-null");
                    }
                    if (t.get(FIELD_DURATION).getAsJsonPrimitive().isNumber()) {
                        int duration = t.get(FIELD_DURATION).getAsInt();
                        Task task= new Task(duration);
                        if(t.get(FIELD_NAME)!=null)
                        {
                           String name= t.get(FIELD_NAME).getAsString();
                           task.setName(name);
                        }
                        if (t.get(FIELD_REQUIREMENTS) != null) {
                            JsonArray requirements = t.get(FIELD_REQUIREMENTS).getAsJsonArray();
                            for (JsonElement elem : requirements) {
                                JsonObject requirement = elem.getAsJsonObject();
                                if (!(requirement.get(FIELD_NAME)==null || requirement.get(FIELD_OPERATOR)==null|| !requirement.get(FIELD_NAME).getAsJsonPrimitive().isString() || !requirement.get(FIELD_OPERATOR).getAsJsonPrimitive().isString())) {
                                    String name = requirement.get(FIELD_NAME).getAsString();
                                    String operator = requirement.get(FIELD_OPERATOR).getAsString();
                                    if(requirement.get(FIELD_VALUE)==null){
                                        api.throwParserError("Task requirement value must be non-null");
                                    }
                                    JsonPrimitive value = requirement.get(FIELD_VALUE).getAsJsonPrimitive();
                                    if (value.getAsJsonPrimitive().isBoolean()) {
                                        task.addRequirement(name, value.getAsBoolean(), operator);
                                    } else if (value.getAsJsonPrimitive().isNumber()) {
                                        task.addRequirement(name, value.getAsDouble(), operator);
                                    } else if (value.getAsJsonPrimitive().isString()) {
                                        task.addRequirement(name, value.getAsString(), operator);
                                    } else {
                                        api.throwParserError("Invalid Task requirement value type; must be a string, number, or boolean");
                                    }
                                } else {
                                    api.throwParserError("Invalid Task requirement name/operator type; must be a string and non-null");
                                }

                            }
                        }

                        if (t.get(PROPERTY_IMPACTS_FIELD) != null) {
                            JsonArray properties = t.get(PROPERTY_IMPACTS_FIELD).getAsJsonArray();
                            for (JsonElement elem : properties) {
                                JsonObject property = elem.getAsJsonObject();
                                if (!(property.get(FIELD_NAME)==null || !property.get(FIELD_NAME).getAsJsonPrimitive().isString()) ){
                                    String name = property.get(FIELD_NAME).getAsString();
                                    String type="";

                                    //Captures any property type containing characters "assign" or DELTA (Case insensitive)
                                    if (property.get("type") == null || property.get("type").getAsString().toLowerCase().contains("assign")) {
                                        type = "assignment";
                                    } else if (property.get("type").getAsString().toLowerCase().contains(DELTA)) {
                                        type = DELTA;
                                    } else {
                                        api.throwParserError("Invalid property type (must be an assignment or delta)");
                                    }
                                    if(property.get(FIELD_VALUE)==null){
                                        api.throwParserError("Task property impact value must be non-null");
                                    }
                                    JsonPrimitive value = property.get(FIELD_VALUE).getAsJsonPrimitive();
                                    if (value.getAsJsonPrimitive().isBoolean()) {
                                        task.addProperty(name, value.getAsBoolean(), type);
                                    } else if (value.getAsJsonPrimitive().isNumber()) {
                                        task.addProperty(name, value.getAsDouble(), type);
                                    } else if (value.getAsJsonPrimitive().isString()) {
                                        task.addProperty(name, value.getAsString(), type);
                                    } else {
                                        api.throwParserError("Invalid property type; must be a string, number, or boolean");
                                    }
                                } else {
                                    api.throwParserError("Invalid Task Property name type; must be a string and non-null");
                                }
                            }
                        }
                        //Add each task object to TaskDictionary object
                        taskDictionary.addTask(task);
                    } else {
                        api.throwParserError("JSON Object Task Duration invalid type; must be an integer");
                    }
                }
                //Add final TaskDictionary to return list
                parsedObjects.put("tasks",taskDictionary);
            }
            else{
                api.throwParserError("JSON tasks must be a JSON array");
            }

            //Section parses json objects into Perturbation java objects
            //Check for null because perturbations is an optional value
            if(jsonObject.get(PERTURBATION_FIELD)!=null){
                JsonElement perturbationArray = jsonObject.get(PERTURBATION_FIELD);

                //Iterate through every json perturbation and create java objects
                if (perturbationArray.isJsonArray()) {
                    JsonArray perturbations= perturbationArray.getAsJsonArray();
                    for (JsonElement pert : perturbations) {
                        JsonObject p = pert.getAsJsonObject();
                        if(p.get("time")==null){
                            api.throwParserError("Perturbation time must be non-null");
                        }
                        int time = p.get("time").getAsInt();
                        Perturbation perturbation = new Perturbation(time);
                        if(p.get(FIELD_NAME)!=null)
                        {
                            String name= p.get(FIELD_NAME).getAsString();
                            perturbation.setName(name);
                        }

                        if (p.get(PROPERTY_IMPACTS_FIELD) != null) {
                            JsonArray properties = p.get(PROPERTY_IMPACTS_FIELD).getAsJsonArray();
                            for (JsonElement elem : properties) {
                                JsonObject property = elem.getAsJsonObject();
                                if (!(property.get(FIELD_NAME)==null || !property.get(FIELD_NAME).getAsJsonPrimitive().isString()) ){
                                    String name = property.get(FIELD_NAME).getAsString();
                                    String type="";
                                    
                                    //Captures any property type containing characters "assign" or DELTA (Case insensitive)
                                    if (property.get("type") == null || property.get("type").getAsString().toLowerCase().contains("assign")) {
                                        type = "assignment";
                                    } else if (property.get("type").getAsString().toLowerCase().contains(DELTA)) {
                                        type = DELTA;
                                    } else {
                                        api.throwParserError("Invalid property type (must be an assignment or delta)");
                                    }
                                    if(property.get(FIELD_VALUE)==null){
                                        api.throwParserError("Perturbation property impact value must be non-null");
                                    }
                                    JsonPrimitive value = property.get(FIELD_VALUE).getAsJsonPrimitive();
                                    if (value.getAsJsonPrimitive().isBoolean()) {
                                        perturbation.addProperty(name, value.getAsBoolean(), type);
                                    } else if (value.getAsJsonPrimitive().isNumber()) {
                                        perturbation.addProperty(name, value.getAsDouble(), type);
                                    } else if (value.getAsJsonPrimitive().isString()) {
                                        perturbation.addProperty(name, value.getAsString(), type);
                                    } else {
                                        api.throwParserError("Invalid property type; must be a string, number, or boolean");
                                    }
                                } else {
                                    api.throwParserError("Invalid Perturbation Property Name type; must be a string and non-null");
                                }
                            }
                        }
                        perturbationList.add(perturbation);
                    }
                    //Add final perturbation list to return list
                    parsedObjects.put("perturbations",perturbationList);
                }
                else{
                    api.throwParserError("JSON perturbation must be a JSON array");
                }
            }
        }
        return parsedObjects;
    }

}
