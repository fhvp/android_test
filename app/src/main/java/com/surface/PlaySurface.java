package com.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.time.TimerThread;
import com.enemy.Circle;


/**
 * Created by admin on 2016-09-13.
 */
public class PlaySurface extends SurfaceView implements SurfaceHolder.Callback {

    private static PlaySurface instance = null;
    private static Context context;
    private static SurfaceHolder sHolder;
    private static TimerThread pThread;

    private PlaySurface(Context context){
        super(context);
        this.context = context;

        init();

        setFocusable(true);
    }

    public static PlaySurface getInstance()
    {
        if(PlaySurface.instance != null)
            return PlaySurface.instance;

        return null;
    }

    public static PlaySurface getInstance(Context context)
    {
        if(PlaySurface.instance == null)
            PlaySurface.instance = new PlaySurface(context);
        return PlaySurface.instance;
    }

    private void init()
    {
        this.instance = this;
        this.sHolder = getHolder();
        sHolder.addCallback(this);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        Log.d("******", "onDraw");
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        Log.d("******", "Click");
        int keyAction = event.getAction();
        Point userPoint = new Point( (int)event.getX(), (int)event.getY());

        Log.d("******", "Pointer X " + userPoint.x + " Y " + userPoint.y);

        switch(keyAction)
        {
            case MotionEvent.ACTION_DOWN:

                Circle mCircle = new Circle(this.context, userPoint);

                break;
            default:
                break;
        }

        //getHandler().post(pThread);


//        CircleAnimeation circleAnimeation = new CircleAnimeation(mCircle, userPoint);
//
//        Log.d("******", "Set User Point " + userPoint.x + ", " + userPoint.y);
//        circleAnimeation.setDuration(1000);
//        Log.d("******", "Set");
//        mCircle.startAnimation(circleAnimeation);
//        Log.d("******", "start");
        return true;
    }

    public static int i = 0x00;

    public void paintInitialize()
    {
        Paint paint = new Paint();
        Canvas screen = null;
        screen = sHolder.lockCanvas(null);

        screen.drawColor(Color.WHITE);
        Log.d("******", "Draw Color : White" + i++);


        paint.setColor(Color.BLUE);
        screen.drawCircle(100, 100, 2, paint);

        sHolder.unlockCanvasAndPost(screen);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("******", "Create");

        this.paintInitialize();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        Log.d("******", "Destroyed");
    }

}
