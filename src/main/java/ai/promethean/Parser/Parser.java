package ai.promethean.Parser;

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

    public Parser(){
        setJson("");
    }


    /* Parser constructor with input
     * @param   _json    Either the json string or the file path for a json file
     * @param   isFile   Denotes whether _json is a json string or file path
     */
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
    }

    public void setJson(String _json){
        this.json=_json;
    }

    /* Parse function parses the class's json string into data model classes
    * @return parsedObject list which contains:
    * Initial SystemState Object (Required)
    * GoalState Object (Required)
    * TaskDictionary Object (Required)
    * OptimizationList Object (Optional)
    * List of Perturbation Objects (Optional)
    */
    public ArrayList<Object> parse() {
        JsonElement jsonTree = parser.parse(json);
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();

            //Section that parses json into optimization objects
            //Check null because optimizations is an optional field
            if (jsonObject.get("optimizations") != null) {
                JsonArray optimizations = jsonObject.get("optimizations").getAsJsonArray();

                //Iterate through every optimization json object and create objects
                if (optimizations.isJsonArray()) {
                    for (JsonElement op : optimizations) {
                        JsonObject optimization = op.getAsJsonObject();
                        if (optimization.get("name").getAsJsonPrimitive().isString()) {
                            String name = optimization.get("name").getAsString();
                            if (optimization.get("priority").getAsJsonPrimitive().isNumber()) {
                                int priority = optimization.get("priority").getAsInt();

                                //Captures any type containing min (Case Indifferent)
                                if (optimization.get("type").getAsString().toLowerCase().contains("min")) {
                                    Optimization o = new Optimization(name, true, priority);
                                    optimizationList.addOptimization(o);

                                    //Captures any type containing max (Case insensitive)
                                } else if (optimization.get("type").getAsString().toLowerCase().contains("max")) {
                                    Optimization o = new Optimization(name, false, priority);
                                    optimizationList.addOptimization(o);
                                } else {
                                    throw new IllegalArgumentException("JSON Object Optimization type is invalid (must be a minimum or maximum)");
                                }
                            } else {
                                throw new IllegalArgumentException("JSON Object Optimization Priority must be a number");
                            }
                        } else {
                            throw new IllegalArgumentException("JSON Object name is invalid type");
                        }
                    }
                } else {
                    throw new IllegalArgumentException("JSON optimizations must be an array");
                }
                //Sort optimizations by priority
                //Add parsed optimization Java objects to return list
                optimizationList.sortOptimizations();
                parsedObjects.add(optimizationList);
            }

            //Section that parses json into an initial SystemState object (Required field)
            JsonObject initialState = jsonObject.get("initial_state").getAsJsonObject();
            SystemState systemState = new SystemState();
            if (initialState.get("properties") != null) {
                JsonArray properties = initialState.get("properties").getAsJsonArray();
                for (JsonElement elem : properties) {
                    JsonObject property = elem.getAsJsonObject();
                    if (property.get("name").getAsJsonPrimitive().isString()) {
                        String name = property.get("name").getAsString();
                        JsonPrimitive value = property.get("value").getAsJsonPrimitive();
                        if (value.getAsJsonPrimitive().isBoolean()) {
                            systemState.addProperty(name, value.getAsBoolean());
                        } else if (value.getAsJsonPrimitive().isNumber()) {
                            systemState.addProperty(name, value.getAsDouble());
                        } else if (value.getAsJsonPrimitive().isString()) {
                            systemState.addProperty(name, value.getAsString());
                        } else {
                            throw new IllegalArgumentException("Invalid property type");
                        }
                    } else {
                        throw new IllegalArgumentException("Invalid State Property name type");
                    }
                }
            }
            //Add parsed initial SystemState object to return list
            parsedObjects.add(systemState);

            //Section parses json into GoalState object (required field)
            JsonObject gs = jsonObject.get("goal_state").getAsJsonObject();
            GoalState goalState = new GoalState();
            if (gs.get("requirements") != null) {
                JsonArray requirements = gs.get("requirements").getAsJsonArray();
                for (JsonElement elem : requirements) {
                    JsonObject requirement = elem.getAsJsonObject();
                    if (requirement.get("name").getAsJsonPrimitive().isString() && requirement.get("operator").getAsJsonPrimitive().isString()) {
                        String name = requirement.get("name").getAsString();
                        String operator = requirement.get("operator").getAsString();
                        JsonPrimitive value = requirement.get("value").getAsJsonPrimitive();
                        if (value.getAsJsonPrimitive().isBoolean()) {
                            goalState.addRequirement(name, value.getAsBoolean(), operator);
                        } else if (value.getAsJsonPrimitive().isNumber()) {
                            goalState.addRequirement(name, value.getAsDouble(), operator);
                        } else if (value.getAsJsonPrimitive().isString()) {
                            goalState.addRequirement(name, value.getAsString(), operator);
                        } else {
                            throw new IllegalArgumentException("Invalid Task requirement value type");
                        }
                    } else {
                        throw new IllegalArgumentException("Invalid Task requirement name/operator type");
                    }
                }
            }
            //Add java GoalSate object to return list
            parsedObjects.add(goalState);

            //Section parses json objects into Task java objects (required field)
            JsonArray tasks = jsonObject.get("tasks").getAsJsonArray();

            //Iterate through every task json object and create java objects
            if (tasks.isJsonArray()) {
                for (JsonElement taski : tasks) {
                    JsonObject t = taski.getAsJsonObject();
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
                                if (requirement.get("name").getAsJsonPrimitive().isString() && requirement.get("operator").getAsJsonPrimitive().isString()) {
                                    String name = requirement.get("name").getAsString();
                                    String operator = requirement.get("operator").getAsString();
                                    JsonPrimitive value = requirement.get("value").getAsJsonPrimitive();
                                    if (value.getAsJsonPrimitive().isBoolean()) {
                                        task.addRequirement(name, value.getAsBoolean(), operator);
                                    } else if (value.getAsJsonPrimitive().isNumber()) {
                                        task.addRequirement(name, value.getAsDouble(), operator);
                                    } else if (value.getAsJsonPrimitive().isString()) {
                                        task.addRequirement(name, value.getAsString(), operator);
                                    } else {
                                        throw new IllegalArgumentException("Invalid Task requirement value type");
                                    }
                                } else {
                                    throw new IllegalArgumentException("Invalid Task requirement name/operator type");
                                }

                            }
                        }

                        if (t.get("property_impacts") != null) {
                            JsonArray properties = t.get("property_impacts").getAsJsonArray();
                            for (JsonElement elem : properties) {
                                JsonObject property = elem.getAsJsonObject();
                                if (property.get("name").getAsJsonPrimitive().isString()) {
                                    String name = property.get("name").getAsString();
                                    String type;

                                    //Captures any property type containing characters "assign" or "delta" (Case insensitive)
                                    if (property.get("type") == null || property.get("type").getAsString().toLowerCase().contains("assign")) {
                                        type = "assignment";
                                    } else if (property.get("type").getAsString().toLowerCase().contains("delta")) {
                                        type = "delta";
                                    } else {
                                        throw new IllegalArgumentException("Invalid property type (must be an assignment or delta)");
                                    }
                                    JsonPrimitive value = property.get("value").getAsJsonPrimitive();
                                    if (value.getAsJsonPrimitive().isBoolean()) {
                                        task.addProperty(name, value.getAsBoolean(), type);
                                    } else if (value.getAsJsonPrimitive().isNumber()) {
                                        task.addProperty(name, value.getAsDouble(), type);
                                    } else if (value.getAsJsonPrimitive().isString()) {
                                        task.addProperty(name, value.getAsString(), type);
                                    } else {
                                        throw new IllegalArgumentException("Invalid property type");
                                    }
                                } else {
                                    throw new IllegalArgumentException("Invalid Task Property name type");
                                }
                            }
                        }
                        //Add each task object to TaskDictionary object
                        taskDictionary.addTask(task);
                    } else {
                        throw new IllegalArgumentException("JSON Object Task Duration invalid type");
                    }
                }
                //Add final TaskDictionary to return list
                parsedObjects.add(taskDictionary);
            }

            //Section parses json objects into Perturbation java objects
            //Check for null because perturbations is an optional value
            if(jsonObject.get("perturbations")!=null){
                JsonArray perturbations = jsonObject.get("perturbations").getAsJsonArray();

                //Iterate through every json perturbation and create java objects
                if (perturbations.isJsonArray()) {
                    for (JsonElement pert : perturbations) {
                        JsonObject p = pert.getAsJsonObject();
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
                                if (property.get("name").getAsJsonPrimitive().isString()) {
                                    String name = property.get("name").getAsString();
                                    String type;
                                    
                                    //Captures any property type containing characters "assign" or "delta" (Case insensitive)
                                    if (property.get("type") == null || property.get("type").getAsString().toLowerCase().contains("assign")) {
                                        type = "assignment";
                                    } else if (property.get("type").getAsString().toLowerCase().contains("delta")) {
                                        type = "delta";
                                    } else {
                                        throw new IllegalArgumentException("Invalid property type (must be an assignment or delta)");
                                    }
                                    JsonPrimitive value = property.get("value").getAsJsonPrimitive();
                                    if (value.getAsJsonPrimitive().isBoolean()) {
                                        perturbation.addProperty(name, value.getAsBoolean(), type);
                                    } else if (value.getAsJsonPrimitive().isNumber()) {
                                        perturbation.addProperty(name, value.getAsDouble(), type);
                                    } else if (value.getAsJsonPrimitive().isString()) {
                                        perturbation.addProperty(name, value.getAsString(), type);
                                    } else {
                                        throw new IllegalArgumentException("Invalid property type");
                                    }
                                } else {
                                    throw new IllegalArgumentException("Invalid Perturbation Property Name type");
                                }
                            }
                        }
                        perturbationList.add(perturbation);
                    }
                    //Add final perturbation list to return list
                    parsedObjects.add(perturbationList);
                }
            }
        }
        return parsedObjects;
    }

}
