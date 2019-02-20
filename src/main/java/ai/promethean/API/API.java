package ai.promethean.API;
import ai.promethean.Parser.*;

import java.io.*;
import java.util.ArrayList;

public class API {

    private String command;
    private String information;

    public void setCommand(String command) {
        this.command = command;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public API(){
        setCommand("");
        setInformation("");
    }
    public API(String _command, String _information){
        // set all commands to lowercase for safety
        setCommand(_command.toLowerCase());
        setInformation(_information);
    }

    public void processCommand(){
            // run command
           if (this.command.equals("run")){
               Parser p = new Parser(this.information, true);
               ArrayList<Object> parsedObjects = p.parse();
               System.out.println(parsedObjects);
               //TODO MAKE THIS INSTANTIATE THE PARSER
           }

           else if (this.command.equals("error")){
               throw new java.lang.Error(this.information);
        }
    }


}
