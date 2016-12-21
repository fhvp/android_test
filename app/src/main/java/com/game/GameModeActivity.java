package com.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enemy.Circle;
import com.enemy.CircleAnimeation;
import com.example.admin.testproj.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.user.NormalUser;
import com.user.UserController;
import com.user.UserThread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by admin on 2016-09-30.
 */

public class GameModeActivity extends Activity implements SurfaceHolder.Callback {

    public static final int MESSAGE_HANDLER_TIME = 0x01;
    public static final int MESSAGE_HANDLER_DATA = 0x02;

    private static int userGapX;
    private static int userGapY;
    private TextView timerText = null;
    private RelativeLayout enermyLayout = null;
    private static PlayTimer playTimer = null;
    private static int gameState = GameState.GATE_STATE_IDLE;

    private static GameModeActivity instance = null;

    private static Map<Circle, Integer> mCircleMap = new HashMap<Circle, Integer>();

    private int width = 0;
    private int height = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    public static GameModeActivity getInstance() {
        if (GameModeActivity.instance == null)
            GameModeActivity.instance = new GameModeActivity();
        return GameModeActivity.instance;
    }

    public GameModeActivity() {
        //GameModeActivity.instance = this;
        this.gameState = GameState.GATE_STATE_READY;

        if(GameModeActivity.instance == null)
            GameModeActivity.instance = this;

        Log.d("GameModeActivity", "instance = " + GameModeActivity.instance);
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            if (msg.what == 0)   //Local Thread Message
            {
                int type = Integer.parseInt(msg.getData().getString("type"));

                switch (type) {
                    case GameModeActivity.MESSAGE_HANDLER_TIME:
                        //Set Time
                        Log.d("Handler", "MESSAGE_HANDLER_TIME, " + msg.getData().getString("time"));
                        timerText.setText(msg.getData().getString("time"));
                        //Add Circle Enemy
                        addEnemy();
                        break;
                    case GameModeActivity.MESSAGE_HANDLER_DATA:
                        switch(Integer.parseInt(msg.getData().getString("data")))
                        {
                            case GameState.GATE_STATE_RUN:
                                break;
                            case GameState.GATE_STATE_POUSE:
                                break;
                            case GameState.GATE_STATE_GAMEOVER:
                                Log.d("Handler", "GAMEOVER");
                                break;
                        }
                        break;
                }
            } else    //Global Message
            {
                switch (msg.what) {
                    case GameState.GATE_STATE_RUN:
                        break;
                    case GameState.GATE_STATE_POUSE:
                        if (playTimer != null)
                            playTimer.interrupt();
                        break;
                    case GameState.GATE_STATE_GAMEOVER:
                        if (GameModeActivity.getInstance().gameState != GameState.GATE_STATE_GAMEOVER) {
                            Log.d("메시지", "가 왔어요!! " + playTimer);
                            GameModeActivity.getInstance().gameState = GameState.GATE_STATE_GAMEOVER;
                            GameModeActivity.getInstance().stopCircle();
                            GameModeActivity.getInstance().onResume();
                        }
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_view);

        Log.d("Activity", "onCreate");
        Intent intent = new Intent(this.getIntent());

        //Timer
        this.timerText = (TextView) findViewById(R.id.timer);
        this.playTimer = new PlayTimer();
        this.playTimer.start();

        //Window Size
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);

//        DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
//        size.set(displayMetrics.widthPixels, displayMetrics.heightPixels);

        //Log.d("Display Size", "width " + size.x + "height " + size.y);

        width = size.x;
        height = size.y;

        //PlayGround
        PlayGround playGround = ((PlayGround) findViewById(R.id.play_ground));
        playGround.changePlayGroundSize(width, height);

        //User//////////////////////////////////////////////////////////////////////
        NormalUser imageView = ((NormalUser) findViewById(R.id.user));
        //Log.i("..", "width = " + width + ", height = " + height);

        Point imageViewPoint = new Point(width / 2, height / 2);
        imageView.getInstance().setPoint(imageViewPoint);
        //Log.d("ImageViewPoint", "x " + imageViewPoint.x + ", y " + imageViewPoint.y);

        this.userGapX = 0;
        this.userGapY = 0;
        ////////////////////////////////////////////////////////////////////////////

        enermyLayout = (RelativeLayout) findViewById(R.id.enermy_layout);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("Activity", "onResume");
        switch (this.gameState) {
            case GameState.GATE_STATE_GAMEOVER:
                //Start Game Over Activity
                Intent intent = new Intent(this, GameOverActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();

        Log.d("Activity", "onStart");

        //Start Thread
        UserThread userThread = UserThread.getInstance();
        userThread.start();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    protected void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());

        Log.d("Activity", "onStop");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("Activity", "onPause");
    }

    int numOfCircle = 0;

    public boolean CheckGameState() {
        if (this.gameState == GameState.GATE_STATE_GAMEOVER) {
            Log.d("Stop", "Set onResume");
            this.onResume();
            return false;
        }

        return true;
    }

    public void addEnemy() {

        if( CheckGameState() == false)
            return;

        Random random = new Random();
        int xPoint = random.nextInt(this.width);
        int yPoint = random.nextInt(this.height);

        //Log.d("x " + xPoint, "y " + yPoint);

        //Check User Point
        Point userPoint = NormalUser.getInstance().getPoint();
        if (Math.abs(userPoint.x - xPoint) <= 100) {
            if (userPoint.x > width)
                xPoint += 100;
            else
                xPoint -= 100;
        }
        if (Math.abs(userPoint.y - yPoint) <= 100) {
            if (userPoint.y > height)
                yPoint += 100;
            else
                yPoint -= 100;
        }

        Circle newCircle = new Circle(this/*.getBaseContext()*/);
        newCircle.setId(0x8000 + numOfCircle);
        newCircle.setX(xPoint);
        newCircle.setY(yPoint);
        CircleAnimeation newCircleAnimeation = new CircleAnimeation(newCircle);
        newCircleAnimeation.setDuration(newCircle.duration);
        newCircle.startAnimation(newCircleAnimeation);

        enermyLayout.addView(newCircle);

        //Log.d("Chiled", "Count " + enermyLayout.getChildCount());
        numOfCircle++;

        //Add List
        this.mCircleMap.put(newCircle, 0);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("******", "Create");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("******", "Change");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("******", "Destoryed");
    }

    public boolean onTouchEvent(MotionEvent event) {
        int keyAction = event.getAction();

        if (this.gameState != GameState.GATE_STATE_RUN) {
            return true;
        }

        switch (keyAction) {
            case MotionEvent.ACTION_DOWN:
                if (this.userGapX == 0 && this.userGapY == 0) {
                    Point oldUserPoint = UserController.getInstance().getPoint();
                    this.userGapX = oldUserPoint.x - (int) event.getX();
                    this.userGapY = oldUserPoint.y - (int) event.getY();
                }
            case MotionEvent.ACTION_MOVE:
                int x = 0;
                int y = 0;
                NormalUser imageView = ((NormalUser) findViewById(R.id.user));

//                if((int) event.getX() < 5)
//                    this.userGapX
//                if((int) event.getX() > this.width)

                imageView.paintUser();

                //Set X
                x = (int) event.getX() + this.userGapX;
                if (NormalUser.userPlayGroundRect.left > x)
                    x = NormalUser.userPlayGroundRect.left;
                else if (NormalUser.userPlayGroundRect.right < x)
                    x = NormalUser.userPlayGroundRect.right;

                //Set Y
                y = (int) event.getY() + this.userGapY;
                if (NormalUser.userPlayGroundRect.top > y)
                    y = NormalUser.userPlayGroundRect.top;
                else if (NormalUser.userPlayGroundRect.bottom < y)
                    y = NormalUser.userPlayGroundRect.bottom;

                Point userPoint = new Point(x, y);
                //Log.d("x " + x, "y " + y);
                //Log.d("left " + NormalUser.userPlayGroundRect.left + ", right " + NormalUser.userPlayGroundRect.right, "top " + NormalUser.userPlayGroundRect.top + ", bottom " + NormalUser.userPlayGroundRect.bottom);

                imageView.getInstance().setPoint(userPoint);

                break;
            case MotionEvent.ACTION_UP:
                this.userGapX = 0;
                this.userGapY = 0;
            default:
                break;
        }
        return true;
    }

    public void setTimer(int time) {
        //timerText.setText("   " + String.valueOf(time));
    }

    public boolean stopCircle() {

        for (Map.Entry<Circle, Integer> element : this.mCircleMap.entrySet()) {
            Circle circle = (Circle) element.getKey();

            if (circle == null)
                return false;

            //Old Duration Value
            element.setValue((int) circle.getAnimation().getDuration());
            circle.clearAnimation();
        }

        Log.d("Stop", "All Animation");
        return true;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("GameMode Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    private class PlayTimer extends Thread {
        private boolean threadFlag = false;
        private int timer = 0;

        public PlayTimer() {
            GameModeActivity.getInstance().gameState = GameState.GATE_STATE_RUN;
            this.threadFlag = true;
        }

        @Override
        public void run() {
            int periodic = 0;

            while (this.threadFlag) {
                try {
                    switch(GameModeActivity.getInstance().gameState)
                    {
                        case GameState.GATE_STATE_POUSE:
                        {
                            Message message = new Message();
                            Bundle data = new Bundle();
                            //Add Local Thread Data
                            data.putString("type", String.valueOf(GameModeActivity.MESSAGE_HANDLER_DATA));
                            data.putString("data", String.valueOf(GameState.GATE_STATE_POUSE));
                            message.setData(data);
                            message.what = 0x00;

                            handler.sendMessage(message);
                        }
                            break;
                        case GameState.GATE_STATE_GAMEOVER:
                        {
//                            Message message = new Message();
//                            Bundle data = new Bundle();
//                            //Add Local Thread Data
//                            data.putString("type", String.valueOf(GameModeActivity.MESSAGE_HANDLER_DATA));
//                            data.putString("data", String.valueOf(GameState.GATE_STATE_GAMEOVER));
//                            message.setData(data);
//                            message.what = 0x00;
//
//                            Log.d("Thread", "GAMEOVER");
//
//                            handler.sendMessage(message);
                            this.threadFlag = false;
                        }
                            break;
                        case GameState.GATE_STATE_RUN:
                        {
                            Message message = new Message();
                            Bundle data = new Bundle();
                            //Add Local Thread Data
                            data.putString("type", String.valueOf(GameModeActivity.MESSAGE_HANDLER_TIME));
                            data.putString("time", String.valueOf(++timer));
                            message.setData(data);
                            message.what = 0x00;

                            Log.d("Thread", "Set Time, " + timer);

                            handler.sendMessage(message);
                        }
                            break;
                        default:
                            break;
                    }

                    sleep(1000);
                    //periodic = (periodic + 1) % 10;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}
