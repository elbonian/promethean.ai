package ai.promethean.Parser;

import ai.promethean.API.ParserError;
import ai.promethean.DataModel.*;
import com.google.gson.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Parser {
    //Constants specific to the JSON language
    private static final String OPTIMIZATION_FIELD= "optimizations";
    private static final String INITSTATE_FIELD= "initial_state";
    private static final String GOALSTATE_FIELD= "goal_state";
    private static final String TASK_FIELD= "tasks";
    private static final String PERTURBATION_FIELD= "perturbations";

    private static final String FIELD_NAME = "name";
    private static final String FIELD_OPERATOR = "operator";
    private static final String FIELD_PRIORITY= "priority";
    private static final String FIELD_VALUE = "value";
    private static final String FIELD_DURATION = "duration";

    private JsonParser parser = new JsonParser();
    private String json;
    private ArrayList<Object> parsedObjects = new ArrayList<Object>();
    private TaskDictionary taskDictionary = new TaskDictionary();
    private StaticOptimizations optimizationList =  new StaticOptimizations();
    private ArrayList<Object> perturbationList =  new ArrayList<Object>();

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
                e.printStackTrace();
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
    public List<Object> parse(String json, Boolean isFile) {
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
                                    throw new ParserError("JSON Optimization type must be non-null");
                                }
                                if (optimization.get("type").getAsString().toLowerCase().contains("min")) {
                                    Optimization o = new Optimization(name, "min", priority);
                                    optimizationList.addOptimization(o);

                                    //Captures any type containing max (Case insensitive)
                                } else if (optimization.get("type").getAsString().toLowerCase().contains("max")) {
                                    Optimization o = new Optimization(name, "max", priority);
                                    optimizationList.addOptimization(o);
                                } else {
                                    throw new ParserError("JSON Object Optimization type is invalid (must be a minimum or maximum)");
                                }
                            } else {
                                throw new ParserError("JSON Object Optimization Priority must be a number and non-null");
                            }
                        } else {
                            throw new ParserError("JSON Optimization name must be a string and non-null");
                        }
                    }
                } else {
                    throw new ParserError("JSON optimizations must be an array");
                }
                //Sort optimizations by priority
                //Add parsed optimization Java objects to return list
                optimizationList.sortOptimizations();
                parsedObjects.add(optimizationList);
            }

            //Section that parses json into an initial SystemState object (Required field)
            if(jsonObject.get(INITSTATE_FIELD)==null){
                throw new ParserError("Missing initial_state JSON object");
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
                            throw new ParserError("Initial State Property value must be non-null");
                        }
                        JsonPrimitive value = property.get(FIELD_VALUE).getAsJsonPrimitive();
                        if (value.getAsJsonPrimitive().isBoolean()) {
                            systemState.addProperty(name, value.getAsBoolean());
                        } else if (value.getAsJsonPrimitive().isNumber()) {
                            systemState.addProperty(name, value.getAsDouble());
                        } else if (value.getAsJsonPrimitive().isString()) {
                            systemState.addProperty(name, value.getAsString());
                        } else {
                            throw new ParserError("Invalid property type; must be a number, string, or bool");
                        }
                    } else {
                        throw new ParserError("Invalid State Property name type; must be a string and non-null");
                    }
                }
            }
            //Add parsed initial SystemState object to return list
            parsedObjects.add(systemState);

            //Section parses json into GoalState object (required field)
            if(jsonObject.get(GOALSTATE_FIELD)==null){
                throw new ParserError("Missing goal_state JSON object");
            }
            JsonObject gs = jsonObject.get(GOALSTATE_FIELD).getAsJsonObject();
            GoalState goalState = new GoalState();
            if (gs.get("requirements") != null) {
                JsonArray requirements = gs.get("requirements").getAsJsonArray();
                for (JsonElement elem : requirements) {
                    JsonObject requirement = elem.getAsJsonObject();
                    if (!(requirement.get(FIELD_NAME)==null || requirement.get(FIELD_OPERATOR)==null|| !requirement.get(FIELD_NAME).getAsJsonPrimitive().isString() || !requirement.get(FIELD_OPERATOR).getAsJsonPrimitive().isString())) {
                        String name = requirement.get(FIELD_NAME).getAsString();
                        String operator = requirement.get(FIELD_OPERATOR).getAsString();
                        if(requirement.get(FIELD_VALUE)==null){
                            throw new ParserError("Goal State requirement value must be non-null");
                        }
                        JsonPrimitive value = requirement.get(FIELD_VALUE).getAsJsonPrimitive();
                        if (value.getAsJsonPrimitive().isBoolean()) {
                            goalState.addRequirement(name, value.getAsBoolean(), operator);
                        } else if (value.getAsJsonPrimitive().isNumber()) {
                            goalState.addRequirement(name, value.getAsDouble(), operator);
                        } else if (value.getAsJsonPrimitive().isString()) {
                            goalState.addRequirement(name, value.getAsString(), operator);
                        } else {
                            throw new ParserError("Invalid Goal State requirement value type; must be a number, string or boolean");
                        }
                    } else {
                        throw new ParserError("Invalid Goal State requirement name/operator type; must be strings and non-null");
                    }
                }
            }
            //Add java GoalSate object to return list
            parsedObjects.add(goalState);

            //Section parses json objects into Task java objects (required field)
            if(jsonObject.get(TASK_FIELD)==null){
                throw new ParserError("Missing tasks JSON object");
            }
            JsonElement taskList = jsonObject.get(TASK_FIELD);

            //Iterate through every task json object and create java objects
            if (taskList.isJsonArray()) {
                JsonArray tasks= taskList.getAsJsonArray();
                for (JsonElement taski : tasks) {
                    JsonObject t = taski.getAsJsonObject();
                    if(t.get(FIELD_DURATION)==null){
                        throw new ParserError("Task duration must be non-null");
                    }
                    if (t.get(FIELD_DURATION).getAsJsonPrimitive().isNumber()) {
                        int duration = t.get(FIELD_DURATION).getAsInt();
                        Task task= new Task(duration);
                        if(t.get(FIELD_NAME)!=null)
                        {
                           String name= t.get(FIELD_NAME).getAsString();
                           task.setName(name);
                        }
                        if (t.get("requirements") != null) {
                            JsonArray requirements = t.get("requirements").getAsJsonArray();
                            for (JsonElement elem : requirements) {
                                JsonObject requirement = elem.getAsJsonObject();
                                if (!(requirement.get(FIELD_NAME)==null || requirement.get(FIELD_OPERATOR)==null|| !requirement.get(FIELD_NAME).getAsJsonPrimitive().isString() || !requirement.get(FIELD_OPERATOR).getAsJsonPrimitive().isString())) {
                                    String name = requirement.get(FIELD_NAME).getAsString();
                                    String operator = requirement.get(FIELD_OPERATOR).getAsString();
                                    if(requirement.get(FIELD_VALUE)==null){
                                        throw new ParserError("Task requirement value must be non-null");
                                    }
                                    JsonPrimitive value = requirement.get(FIELD_VALUE).getAsJsonPrimitive();
                                    if (value.getAsJsonPrimitive().isBoolean()) {
                                        task.addRequirement(name, value.getAsBoolean(), operator);
                                    } else if (value.getAsJsonPrimitive().isNumber()) {
                                        task.addRequirement(name, value.getAsDouble(), operator);
                                    } else if (value.getAsJsonPrimitive().isString()) {
                                        task.addRequirement(name, value.getAsString(), operator);
                                    } else {
                                        throw new ParserError("Invalid Task requirement value type; must be a string, number, or boolean");
                                    }
                                } else {
                                    throw new ParserError("Invalid Task requirement name/operator type; must be a string and non-null");
                                }

                            }
                        }

                        if (t.get("property_impacts") != null) {
                            JsonArray properties = t.get("property_impacts").getAsJsonArray();
                            for (JsonElement elem : properties) {
                                JsonObject property = elem.getAsJsonObject();
                                if (!(property.get(FIELD_NAME)==null || !property.get(FIELD_NAME).getAsJsonPrimitive().isString()) ){
                                    String name = property.get(FIELD_NAME).getAsString();
                                    String type;

                                    //Captures any property type containing characters "assign" or "delta" (Case insensitive)
                                    if (property.get("type") == null || property.get("type").getAsString().toLowerCase().contains("assign")) {
                                        type = "assignment";
                                    } else if (property.get("type").getAsString().toLowerCase().contains("delta")) {
                                        type = "delta";
                                    } else {
                                        throw new ParserError("Invalid property type (must be an assignment or delta)");
                                    }
                                    if(property.get(FIELD_VALUE)==null){
                                        throw new ParserError("Task property impact value must be non-null");
                                    }
                                    JsonPrimitive value = property.get(FIELD_VALUE).getAsJsonPrimitive();
                                    if (value.getAsJsonPrimitive().isBoolean()) {
                                        task.addProperty(name, value.getAsBoolean(), type);
                                    } else if (value.getAsJsonPrimitive().isNumber()) {
                                        task.addProperty(name, value.getAsDouble(), type);
                                    } else if (value.getAsJsonPrimitive().isString()) {
                                        task.addProperty(name, value.getAsString(), type);
                                    } else {
                                        throw new ParserError("Invalid property type; must be a string, number, or boolean");
                                    }
                                } else {
                                    throw new ParserError("Invalid Task Property name type; must be a string and non-null");
                                }
                            }
                        }
                        //Add each task object to TaskDictionary object
                        taskDictionary.addTask(task);
                    } else {
                        throw new ParserError("JSON Object Task Duration invalid type; must be an integer");
                    }
                }
                //Add final TaskDictionary to return list
                parsedObjects.add(taskDictionary);
            }
            else{
                throw new ParserError("JSON tasks must be a JSON array");
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
                            throw new ParserError("Perturbation time must be non-null");
                        }
                        int time = p.get("time").getAsInt();
                        Perturbation perturbation = new Perturbation(time);
                        if(p.get(FIELD_NAME)!=null)
                        {
                            String name= p.get(FIELD_NAME).getAsString();
                            perturbation.setName(name);
                        }

                        if (p.get("property_impacts") != null) {
                            JsonArray properties = p.get("property_impacts").getAsJsonArray();
                            for (JsonElement elem : properties) {
                                JsonObject property = elem.getAsJsonObject();
                                if (!(property.get(FIELD_NAME)==null || !property.get(FIELD_NAME).getAsJsonPrimitive().isString()) ){
                                    String name = property.get(FIELD_NAME).getAsString();
                                    String type;
                                    
                                    //Captures any property type containing characters "assign" or "delta" (Case insensitive)
                                    if (property.get("type") == null || property.get("type").getAsString().toLowerCase().contains("assign")) {
                                        type = "assignment";
                                    } else if (property.get("type").getAsString().toLowerCase().contains("delta")) {
                                        type = "delta";
                                    } else {
                                        throw new ParserError("Invalid property type (must be an assignment or delta)");
                                    }
                                    if(property.get(FIELD_VALUE)==null){
                                        throw new ParserError("Perturbation property impact value must be non-null");
                                    }
                                    JsonPrimitive value = property.get(FIELD_VALUE).getAsJsonPrimitive();
                                    if (value.getAsJsonPrimitive().isBoolean()) {
                                        perturbation.addProperty(name, value.getAsBoolean(), type);
                                    } else if (value.getAsJsonPrimitive().isNumber()) {
                                        perturbation.addProperty(name, value.getAsDouble(), type);
                                    } else if (value.getAsJsonPrimitive().isString()) {
                                        perturbation.addProperty(name, value.getAsString(), type);
                                    } else {
                                        throw new ParserError("Invalid property type; must be a string, number, or boolean");
                                    }
                                } else {
                                    throw new ParserError("Invalid Perturbation Property Name type; must be a string and non-null");
                                }
                            }
                        }
                        perturbationList.add(perturbation);
                    }
                    //Add final perturbation list to return list
                    parsedObjects.add(perturbationList);
                }
                else{
                    throw new ParserError("JSON perturbation must be a JSON array");
                }
            }
        }
        return parsedObjects;
    }

}
