package com.enemy;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.game.GameModeActivity;
import com.game.GameState;
import com.user.UserController;

/**
 * Created by admin on 2016-09-27.
 */

public class CircleAnimeation extends Animation{

    private Circle circle = null;

    //private static List<CircleStruct> circleList = new ArrayList<CircleStruct>();

    public CircleAnimeation() {

    }

    public CircleAnimeation(Circle circle) {
        this.circle = circle;
    }

    public void addCircleAnimation(Circle circle) {
        this.circle = circle;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        Point userPoint = UserController.getInstance().getPoint();

        if (this.circle.getX() > userPoint.x)
            this.circle.setX(this.circle.getX() - circle.getSpeed());
        else
            this.circle.setX(this.circle.getX() + circle.getSpeed());

        if (this.circle.getY() > userPoint.y)
            this.circle.setY(this.circle.getY() - circle.getSpeed());
        else
            this.circle.setY(this.circle.getY() + circle.getSpeed());

        if( Math.abs(userPoint.x - this.circle.getX()) < UserController.getInstance().icon_width && Math.abs(userPoint.y - this.circle.getY()) < UserController.getInstance().icon_height ) {
            //Log.d("충돌", "충돌났어요");
            GameModeActivity.getInstance().handler.sendEmptyMessage(GameState.GATE_STATE_GAMEOVER);

//            Message message = new Message();
//            Bundle data = new Bundle();
//            data.putString("type", String.valueOf(GameModeActivity.MESSAGE_HANDLER_DATA));
//            data.putString("data", String.valueOf(GameState.GATE_STATE_GAMEOVER));
//            message.setData(data);
//            message.what = 0x00;
//
//            Log.d("Thread", "GAMEOVER");
//
//            GameModeActivity.getInstance().handler.sendMessage(message);
        }

        this.circle.requestLayout();
        //}
    }

}
