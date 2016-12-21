package com.user;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import com.game.PlayGround;

/**
 * Created by admin on 2016-10-10.
 */

public class NormalUser extends  UserController {

    public static Rect userPlayGroundRect = new Rect();

    public NormalUser(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        NormalUser.getInstance(context, attributeSet);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.paintUser();

        //this.bringToFront();

//        this.userPlayGroundRect.set(PlayGround.playGroundRect.left + (this.icon_width / 2), PlayGround.playGroundRect.top + (this.icon_height / 2)
//                                , PlayGround.playGroundRect.right - (this.icon_width + (this.icon_width / 2)), PlayGround.playGroundRect.bottom - (this.icon_height));
        this.userPlayGroundRect.set(PlayGround.playGroundRect.left + (this.icon_width / 2), PlayGround.playGroundRect.top + (this.icon_height / 2)
                , PlayGround.playGroundRect.right - (this.icon_width / 2), PlayGround.playGroundRect.bottom - (this.icon_height / 2));

        Log.d("N/G Rect", "left " + userPlayGroundRect.left + ", right " + userPlayGroundRect.right + ", top " + userPlayGroundRect.top + ", bottom " + userPlayGroundRect.bottom);
    }

    public void paintUser() {
        this.setX( this.getInstance().mPoint.x - (this.icon_width) );
        this.setY( this.getInstance().mPoint.y - (this.icon_height) );
    }
}
