package ai.promethean.ExecutingAgent;
import java.util.*;

public class Clock {
    private int stepSize;
    private int currentTime;
    private static int lag = 0;
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
            currentTime += stepSize;
            stopFlag = notifyObservers();
        }
    }

    public boolean notifyObservers(){
        boolean result = true;

        for (ClockObserver o: observers) {
            int time = currentTime;
            result = o.update(time) && result;
        }
        return result;
    }

    /**
     * Reset task lag to 0
     */
    public static void resetLag() {
        lag = 0;
    }

    /**
     * Add task lag to the clock
     * @param _lag int number of clock ticks of lag
     */
    public static void addLag(int _lag) {
        lag += _lag;
    }

    /**
     * Get the current amount of task lag added to the clock
     * @return int total number of clock ticks of lag
     */
    public static int getLag() {
        return lag;
    }
}
