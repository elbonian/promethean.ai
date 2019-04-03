package ai.promethean.Logger;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Logger {
    /**
     * logFlag will be set to True if verbose is enabled
     */
    public static boolean logFlag = false;

    /**
     *Generate a date-time stamp to be the file name of the log
     */
    public static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
    public static Date date = new Date();
    public static String logFileName = (sdf.format(date)+".txt");

    public static String getLogFileName(){
        return logFileName;
    }

    public static boolean isLogFlag() {
        return logFlag;
    }

}
