package ai.promethean.ExecutingAgent;

public class Clock {
    private int duration;
    private int stepSize;
    private int currentTime;

    public Clock(){
        setDuration(0);
        setStepSize(0);
        setCurrentTime(0);
    }

    public Clock(int _duration, int _stepSize){
        setDuration(_duration);
        setStepSize(_stepSize);
        setCurrentTime(0);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }
    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public int getDuration() {
        return duration;
    }
    public int getStepSize() {
        return stepSize;
    }
    public int getCurrentTime() {
        return currentTime;
    }

    public void runClock(){
        while (this.currentTime < this.duration){
            this.currentTime += this.stepSize;
            System.out.println(this.currentTime);
        }

    }

}
