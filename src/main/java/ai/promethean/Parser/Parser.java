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
    //ArrayLists are mutable objects so we don't need a setter
    private ArrayList<Object> parsedObjects = new ArrayList<Object>();
    private TaskDictionary taskDictionary = new TaskDictionary();
    private StaticOptimizations optimizationList =  new StaticOptimizations();
    private ArrayList<Object> perturbationList =  new ArrayList<Object>();

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
    }

    public void setJson(String _json){
        this.json=_json;
    }

    public ArrayList<Object> parse() {
        JsonElement jsonTree = parser.parse(json);
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonArray optimizations= jsonObject.get("optimizations").getAsJsonArray();
            if(optimizations.isJsonArray()) {
                for (JsonElement op : optimizations) {
                    JsonObject optimization = op.getAsJsonObject();
                    if (optimization.get("name").getAsJsonPrimitive().isString()) {
                        String name = optimization.get("name").getAsString();
                        if (optimization.get("priority").getAsJsonPrimitive().isNumber()) {
                            int priority = optimization.get("priority").getAsInt();
                            if (optimization.get("type").getAsString().toLowerCase().contains("min")) {
                                Optimization o = new Optimization(name, true, priority);
                                optimizationList.addOptimization(o);
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
            }
            else{
                throw new IllegalArgumentException("JSON optimizations must be an array");
            }

            JsonObject initialState= jsonObject.get("initial_state").getAsJsonObject();
            SystemState systemState=new SystemState();
            if (initialState.get("properties") != null) {
                JsonArray properties = initialState.get("properties").getAsJsonArray();
                for (JsonElement elem : properties) {
                    JsonObject property = elem.getAsJsonObject();
                    if (property.get("name").getAsJsonPrimitive().isString()){
                        String name = property.get("name").getAsString();
                        boolean isDelta;
                        if (property.get("type") ==null || property.get("type").getAsString().toLowerCase().contains("assign")) {
                            isDelta=false;
                        }
                        else if (property.get("type").getAsString().toLowerCase().contains("delta")) {
                            isDelta=true;
                        }
                        else {
                            throw new IllegalArgumentException("Invalid property type (must be an assignment or delta)");
                        }
                        JsonPrimitive value = property.get("value").getAsJsonPrimitive();
                        if (value.getAsJsonPrimitive().isBoolean()) {
                            systemState.addProperty(name, value.getAsBoolean(), isDelta);
                        } else if (value.getAsJsonPrimitive().isNumber()) {
                            systemState.addProperty(name, value.getAsDouble(),isDelta);
                        } else if (value.getAsJsonPrimitive().isString()) {
                            systemState.addProperty(name, value.getAsString(),isDelta);
                        } else {
                            throw new IllegalArgumentException("Invalid property type");
                        }
                    }
                    else {
                        throw new IllegalArgumentException("Invalid State Property name type");
                    }
                }
            }
            parsedObjects.add(systemState);
            JsonObject gs= jsonObject.get("goal_state").getAsJsonObject();
            GoalState goalState= new GoalState();
            if (gs.get("requirements") != null) {
                JsonArray requirements = gs.get("requirements").getAsJsonArray();
                for (JsonElement elem : requirements) {
                    JsonObject requirement = elem.getAsJsonObject();
                    if (requirement.get("name").getAsJsonPrimitive().isString() && requirement.get("operator").getAsJsonPrimitive().isString() ) {
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
                    }
                    else {
                        throw new IllegalArgumentException("Invalid Task requirement name/operator type");
                    }
                }
            }
            parsedObjects.add(goalState);

            JsonArray tasks= jsonObject.get("tasks").getAsJsonArray();
            if(tasks.isJsonArray()) {
                for (JsonElement taski : tasks) {
                    JsonObject t = taski.getAsJsonObject();
                    if (t.get("duration").getAsJsonPrimitive().isNumber()){
                        int duration = t.get("duration").getAsInt();
                        Task task = new Task(duration);
                        if (t.get("requirements") != null) {
                            JsonArray requirements = t.get("requirements").getAsJsonArray();
                            for (JsonElement elem : requirements) {
                                JsonObject requirement = elem.getAsJsonObject();
                                if (requirement.get("name").getAsJsonPrimitive().isString() && requirement.get("operator").getAsJsonPrimitive().isString() ) {
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
                                }
                                else {
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
                                    boolean isDelta;
                                    if (property.get("type") ==null || property.get("type").getAsString().toLowerCase().contains("assign")) {
                                        isDelta=false;
                                    }
                                    else if (property.get("type").getAsString().toLowerCase().contains("delta")) {
                                        isDelta=true;
                                    }
                                    else {
                                        throw new IllegalArgumentException("Invalid property type (must be an assignment or delta)");
                                    }
                                    JsonPrimitive value = property.get("value").getAsJsonPrimitive();
                                    if (value.getAsJsonPrimitive().isBoolean()) {
                                        task.addProperty(name, value.getAsBoolean(), isDelta);
                                    } else if (value.getAsJsonPrimitive().isNumber()) {
                                        task.addProperty(name, value.getAsDouble(),isDelta);
                                    } else if (value.getAsJsonPrimitive().isString()) {
                                        task.addProperty(name, value.getAsString(),isDelta);
                                    } else {
                                        throw new IllegalArgumentException("Invalid property type");
                                    }
                                }
                                else {
                                    throw new IllegalArgumentException("Invalid Task Property name type");
                                }
                            }
                        }
                        taskDictionary.addTask(task);
                    }
                    else{
                        throw new IllegalArgumentException("JSON Object Task Duration invalid type");
                    }
                }
            }

            JsonArray perturbations= jsonObject.get("perturbations").getAsJsonArray();
            if(perturbations.isJsonArray()) {
                for (JsonElement pert : perturbations) {
                    JsonObject p = pert.getAsJsonObject();
                    Perturbation perturbation;
                    if (p.get("time") == null) {
                        perturbation = new Perturbation();
                    } else if(p.get("time").getAsJsonPrimitive().isNumber()){
                        int time = p.get("time").getAsInt();
                        perturbation = new Perturbation(time);
                    }
                    else{
                        throw new IllegalArgumentException("JSON Object Perturbation Time invalid type");
                    }

                    if (p.get("property_impacts") != null) {
                        JsonArray properties = p.get("property_impacts").getAsJsonArray();
                        for (JsonElement elem : properties) {
                            JsonObject property = elem.getAsJsonObject();
                            if (property.get("name").getAsJsonPrimitive().isString()) {
                                String name = property.get("name").getAsString();
                                boolean isDelta;
                                if (property.get("type") ==null || property.get("type").getAsString().toLowerCase().contains("assign")) {
                                    isDelta=false;
                                }
                                else if (property.get("type").getAsString().toLowerCase().contains("delta")) {
                                    isDelta=true;
                                }
                                else {
                                    throw new IllegalArgumentException("Invalid property type (must be an assignment or delta)");
                                }
                                JsonPrimitive value = property.get("value").getAsJsonPrimitive();
                                if (value.getAsJsonPrimitive().isBoolean()) {
                                    perturbation.addProperty(name, value.getAsBoolean(), isDelta);
                                } else if (value.getAsJsonPrimitive().isNumber()) {
                                    perturbation.addProperty(name, value.getAsDouble(),isDelta);
                                } else if (value.getAsJsonPrimitive().isString()) {
                                    perturbation.addProperty(name, value.getAsString(),isDelta);
                                } else {
                                    throw new IllegalArgumentException("Invalid property type");
                                }
                            }
                            else {
                                throw new IllegalArgumentException("Invalid Perturbation Property Name type");
                            }
                        }
                    }
                    perturbationList.add(perturbation);
                }
            }
        }
        optimizationList.sortOptimizations();
        parsedObjects.add(taskDictionary);
        parsedObjects.add(optimizationList);
        parsedObjects.add(perturbationList);
        return parsedObjects;
    }
}
