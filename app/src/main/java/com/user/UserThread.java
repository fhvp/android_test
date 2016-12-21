package com.user;

import android.graphics.Point;

/**
 * Created by admin on 2016-10-05.
 */

public class UserThread extends Thread {

    private static UserThread instance = null;
    private static int state;

    private static final int THREAD_STATE_IDEL = 0;
    private static final int THREAD_STATE_RUN = 1;

    private UserController userController = UserController.getInstance();

    private UserThread()
    {
        this.state = THREAD_STATE_RUN;
    }

    public static UserThread getInstance()
    {
        if(UserThread.instance == null)
            UserThread.instance = new UserThread();
        return UserThread.instance;
    }

    public static void destoryInstance()
    {
        UserThread.instance = null;
        UserThread.state = THREAD_STATE_IDEL;
    }

    @Override
    public void run()
    {
        int timer = 0;
        Point userPoint = new Point();
        while(this.state == THREAD_STATE_RUN)
        {
            //GameModeActivity.getInstance().setTimer(timer++);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
