package ai.promethean.Parser;

import ai.promethean.API.ParserError;
import ai.promethean.DataModel.*;
import com.google.gson.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Parser {
    private JsonParser parser = new JsonParser();
    private String json;
    private ArrayList<Object> parsedObjects = new ArrayList<Object>();
    private TaskDictionary taskDictionary = new TaskDictionary();
    private StaticOptimizations optimizationList =  new StaticOptimizations();
    private ArrayList<Object> perturbationList =  new ArrayList<Object>();

    /* Parser set JSON with input
     * @param   _json    Either the json string or the file path for a json file
     * @param   isFile   Denotes whether _json is a json string or file path
     */

    public void setJson(String _json, Boolean isFile){
        if(!isFile) {
            json=_json;
        }
        else{
            try
            {
                String content = new String(Files.readAllBytes(Paths.get(_json)), StandardCharsets.ISO_8859_1);
                json= content;
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
    public ArrayList<Object> parse(String _json, Boolean isFile) {
        setJson(_json,isFile);
        JsonElement jsonTree = parser.parse(json);
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();

            //Section that parses json into optimization objects
            //Check null because optimizations is an optional field
            if (jsonObject.get("optimizations") != null) {
                JsonElement optimizationArray = jsonObject.get("optimizations");

                //Iterate through every optimization json object and create objects
                if (optimizationArray.isJsonArray()) {
                    JsonArray optimizations= optimizationArray.getAsJsonArray();
                    for (JsonElement op : optimizations) {
                        JsonObject optimization = op.getAsJsonObject();
                        if (!(optimization.get("name")==null || !optimization.get("name").getAsJsonPrimitive().isString())) {
                            String name = optimization.get("name").getAsString();
                            if (!(optimization.get("priority")==null ||!optimization.get("priority").getAsJsonPrimitive().isNumber())) {
                                int priority = optimization.get("priority").getAsInt();

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
            if(jsonObject.get("initial_state")==null){
                throw new ParserError("Missing initial_state JSON object");
            }
            JsonObject initialState = jsonObject.get("initial_state").getAsJsonObject();
            SystemState systemState = new SystemState();
            if (initialState.get("properties") != null) {
                JsonArray properties = initialState.get("properties").getAsJsonArray();
                for (JsonElement elem : properties) {
                    JsonObject property = elem.getAsJsonObject();
                    if (!(property.get("name")==null || !property.get("name").getAsJsonPrimitive().isString()) ){
                        String name = property.get("name").getAsString();
                        if(property.get("value")==null){
                            throw new ParserError("Initial State Property value must be non-null");
                        }
                        JsonPrimitive value = property.get("value").getAsJsonPrimitive();
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
            if(jsonObject.get("goal_state")==null){
                throw new ParserError("Missing goal_state JSON object");
            }
            JsonObject gs = jsonObject.get("goal_state").getAsJsonObject();
            GoalState goalState = new GoalState();
            if (gs.get("requirements") != null) {
                JsonArray requirements = gs.get("requirements").getAsJsonArray();
                for (JsonElement elem : requirements) {
                    JsonObject requirement = elem.getAsJsonObject();
                    if (!(requirement.get("name")==null || requirement.get("operator")==null|| !requirement.get("name").getAsJsonPrimitive().isString() || !requirement.get("operator").getAsJsonPrimitive().isString())) {
                        String name = requirement.get("name").getAsString();
                        String operator = requirement.get("operator").getAsString();
                        if(requirement.get("value")==null){
                            throw new ParserError("Goal State requirement value must be non-null");
                        }
                        JsonPrimitive value = requirement.get("value").getAsJsonPrimitive();
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
            if(jsonObject.get("tasks")==null){
                throw new ParserError("Missing tasks JSON object");
            }
            JsonElement taskList = jsonObject.get("tasks");

            //Iterate through every task json object and create java objects
            if (taskList.isJsonArray()) {
                JsonArray tasks= taskList.getAsJsonArray();
                for (JsonElement taski : tasks) {
                    JsonObject t = taski.getAsJsonObject();
                    if(t.get("duration")==null){
                        throw new ParserError("Task duration must be non-null");
                    }
                    if (t.get("duration").getAsJsonPrimitive().isNumber()) {
                        int duration = t.get("duration").getAsInt();
                        Task task= new Task(duration);
                        if(t.get("name")!=null)
                        {
                           String name= t.get("name").getAsString();
                           task.setName(name);
                        }
                        if (t.get("requirements") != null) {
                            JsonArray requirements = t.get("requirements").getAsJsonArray();
                            for (JsonElement elem : requirements) {
                                JsonObject requirement = elem.getAsJsonObject();
                                if (!(requirement.get("name")==null || requirement.get("operator")==null|| !requirement.get("name").getAsJsonPrimitive().isString() || !requirement.get("operator").getAsJsonPrimitive().isString())) {
                                    String name = requirement.get("name").getAsString();
                                    String operator = requirement.get("operator").getAsString();
                                    if(requirement.get("value")==null){
                                        throw new ParserError("Task requirement value must be non-null");
                                    }
                                    JsonPrimitive value = requirement.get("value").getAsJsonPrimitive();
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
                                if (!(property.get("name")==null || !property.get("name").getAsJsonPrimitive().isString()) ){
                                    String name = property.get("name").getAsString();
                                    String type;

                                    //Captures any property type containing characters "assign" or "delta" (Case insensitive)
                                    if (property.get("type") == null || property.get("type").getAsString().toLowerCase().contains("assign")) {
                                        type = "assignment";
                                    } else if (property.get("type").getAsString().toLowerCase().contains("delta")) {
                                        type = "delta";
                                    } else {
                                        throw new ParserError("Invalid property type (must be an assignment or delta)");
                                    }
                                    if(property.get("value")==null){
                                        throw new ParserError("Task property impact value must be non-null");
                                    }
                                    JsonPrimitive value = property.get("value").getAsJsonPrimitive();
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
            if(jsonObject.get("perturbations")!=null){
                JsonElement perturbationArray = jsonObject.get("perturbations");

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
                        if(p.get("name")!=null)
                        {
                            String name= p.get("name").getAsString();
                            perturbation.setName(name);
                        }

                        if (p.get("property_impacts") != null) {
                            JsonArray properties = p.get("property_impacts").getAsJsonArray();
                            for (JsonElement elem : properties) {
                                JsonObject property = elem.getAsJsonObject();
                                if (!(property.get("name")==null || !property.get("name").getAsJsonPrimitive().isString()) ){
                                    String name = property.get("name").getAsString();
                                    String type;
                                    
                                    //Captures any property type containing characters "assign" or "delta" (Case insensitive)
                                    if (property.get("type") == null || property.get("type").getAsString().toLowerCase().contains("assign")) {
                                        type = "assignment";
                                    } else if (property.get("type").getAsString().toLowerCase().contains("delta")) {
                                        type = "delta";
                                    } else {
                                        throw new ParserError("Invalid property type (must be an assignment or delta)");
                                    }
                                    if(property.get("value")==null){
                                        throw new ParserError("Perturbation property impact value must be non-null");
                                    }
                                    JsonPrimitive value = property.get("value").getAsJsonPrimitive();
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
