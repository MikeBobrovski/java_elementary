package Util;

import java.time.format.DateTimeFormatter;

public final class Logger {
    int counter;
    private static Logger instance;

    private Logger() {
    }

    public void log(String str) {
        final String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss SSS");
        String dateStr = java.time.LocalTime.now().format(dtf);
        dateStr = dateStr.substring(0, dateStr.indexOf(' ')) + ":" + dateStr.substring(dateStr.indexOf(' ') + 1);
        System.out.printf("%d) %s [%s]: %s\n", ++counter, dateStr, methodName, str);
    } //log (String)

    public static Logger getLogger() {
        return instance = (instance == null) ? new Logger() : instance;
    }

} //class StdLogger
