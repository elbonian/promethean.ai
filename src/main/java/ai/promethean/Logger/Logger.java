package ai.promethean.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class Logger {
    /**
     * logFlag will be set to True if verbose is enabled
     */
    public static boolean logFlag = false;

    /**
     *Generate a date-time stamp to be the file name of the log
     */
    public static String logFileName= ("Logs/" + new Date()+".txt").replace(" ", "").replace(":", "");


    public static String getLogFileName(){
        return logFileName;
    }

    public static boolean isLogFlag() {
        return logFlag;
    }

    /**
     * @param msg Contents to be written to log
     * @param component Component which message is coming from
     */

    public static void writeLog(String msg, String component){
        //Generate a new date timestamp for the specific log entry
        Date writeTime = new Date();
        FileWriter fw = null;
        try {
            fw = new FileWriter(logFileName, true);
            fw.write("LOG:\n" + writeTime + "\n" + component +  "\n" + msg + "\n");
            fw.write("------------------------------\n");
            fw.close();
        }catch(IOException e){
            System.out.println("Logging Failed");
        }
        finally{
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    // This is unrecoverable. Just report it and move on
                    e.printStackTrace();
                }
            }
        }
    }
}
