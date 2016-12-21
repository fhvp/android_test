package com.time;

/**
 * Created by admin on 2016-09-13.
 */
public class TimerThread extends Thread {

    private static boolean timerState = false;

    public TimerThread() {
        this.timerState = true;
    }

    @Override
    public void run() {

    }

    public void setTimerState(boolean state) {
        this.timerState = state;
    }

    public boolean getTimerState() {
        return this.timerState;
    }
}
