package Experiment;

import org.w3c.dom.ls.LSOutput;

public class TimeHandler {
    private static long startTime, stopTime;

    public static void start() {
        startTime = System.nanoTime();
    }

    public static String stop() {
        stopTime = System.nanoTime();
        return humanReading(stopTime - startTime);
    }

    public static String humanReading(long nanos) {
        //минуты
        long min = nanos / 60_000_000_000L;
        nanos = nanos % 60_000_000_000L;
        //секунды
        long sec = nanos / 1_000_000_000L;
        nanos = nanos % 1_000_000_000L;
        //миллисекунды
        long millis = nanos / 1_000_000L;
        nanos =  nanos % 1_000_000L;
        //микросекунды
        long micros = nanos / 1000L;
        nanos = nanos % 1000L;
        return (String.format("%02d:%02d.%03d.%03d.%03d", min, sec, millis, micros, nanos));
    }

    
}
