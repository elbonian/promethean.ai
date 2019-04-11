package ai.promethean.Logger;

import java.io.*;
import java.util.Date;


public class Logger {
    /**
     * logFlag will be set to True if verbose is enabled
     */
    public static boolean logFlag = false;
    public static boolean printFlag = false;

    /**
     *Generate a date-time stamp to be the file name of the log
     */
    public static String directoryName="Logs";
    public static String logFileName= (new Date()+".txt").replace(" ", "").replace(":", "");


    public static String getLogFileName(){
        return logFileName;
    }

    public static boolean isPrintFlag(){ return printFlag;}

    public static boolean isLogFlag() {
        return logFlag;
    }


    /**
     * @param msg Contents to be written to log
     * @param component Component which message is coming from
     */

    public static void writeLog(String msg, String component) {
        //Generate a new date timestamp for the specific log entry
        if (isLogFlag()) {
            Date writeTime = new Date();
            FileWriter fw = null;
            try {
                File directory = new File(directoryName);
                if (!directory.exists()) {
                    directory.mkdir();
                }
                fw = new FileWriter(directoryName + "/" + logFileName, true);
                String writeString = "LOG:\n" + writeTime + "\n" + component + "\n" + msg + "\n";
                fw.write(writeString);
                fw.write("------------------------------\n");
                fw.close();
                if (isPrintFlag()) {
                    System.out.println(writeString);
                    System.out.println("------------------------------\n");
                }
            } catch (IOException e) {
                System.out.println("Logging Failed" + e);
            } finally {
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

    /**
     * @param e Exception to be written to log
     * @param component Component of the system the exception is from
     */
    public static void logError(Exception e, String component) {
        String errorLog = "ERROR:\n";

        if (e.getMessage() != null) {
            errorLog += e.getMessage() + "\n";
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        e.printStackTrace(pw);

        errorLog += sw.toString();

        Logger.writeLog(errorLog, component);

        try {
            sw.close();
        } catch (IOException ioe){
            // This should never happen since StringWriter.close() doesn't do anything
            ioe.printStackTrace();
        }
        pw.close();
    }
}
