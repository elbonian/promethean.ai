package ai.promethean.ExecutingAgent;
import java.util.*;

public class Clock {
    private int stepSize;
    private static int currentTime;
    private boolean stopFlag;
    private List<ClockObserver> observers= new ArrayList<ClockObserver>();

    public Clock(){
        setStepSize(1);
        setCurrentTime(0);
        setStopFlag(false);
    }

    public Clock(int _stepSize){
        setStepSize(_stepSize);
        setCurrentTime(0);
        setStopFlag(false);
    }

    public void setStopFlag(boolean stopFlag) {
        this.stopFlag = stopFlag;
    }
    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }
    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }
    public void addObserver(ClockObserver observer) {
        observers.add(observer);
    }

    public int getStepSize() {
        return stepSize;
    }
    public int getCurrentTime() {
        return currentTime;
    }
    public boolean getStopFlag() {return stopFlag; }

    public void runClock(){
        while (!stopFlag){
            Clock.incrementTime(stepSize);
            stopFlag = notifyObservers();
        }
    }
    public boolean notifyObservers(){
        boolean result = true;

        for (ClockObserver o: observers) {
            int time = Clock.getTime();
            result = o.update(time) && result;
        }
        return result;
    }

    public static void incrementTime(int stepSize) {
        currentTime += stepSize;
    }

    public static int getTime() {
        return currentTime;
    }

    public static void setTime(int time) {
        currentTime = time;
    }

}
