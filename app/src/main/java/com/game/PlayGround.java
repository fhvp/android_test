package com.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.user.NormalUser;

/**
 * Created by admin on 2016-10-07.
 */

public class PlayGround extends View {

    public static int margin = 40;
    public static Rect playGroundRect = new Rect();

    public Paint playGroundPaint = null;

    public PlayGround(Context context) {
        super(context);

        this.playGroundPaint = new Paint();
    }

    public PlayGround(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        this.playGroundPaint = new Paint();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.playGroundPaint.setAntiAlias(true);
        //paint.setStyle(Paint.Style.STROKE);
        this.playGroundPaint.setStyle(Paint.Style.FILL);
        //Circle color
        this.playGroundPaint.setColor(Color.LTGRAY);
        canvas.drawRect(this.playGroundRect, this.playGroundPaint);

        //this.playGroundPaint.setColor(Color.DKGRAY);
        //canvas.drawRect(NormalUser.userPlayGroundRect, this.playGroundPaint);
    }

    public void changePlayGroundSize(int width, int height) {
        playGroundRect.set( this.margin, this.margin * 3, width - ( this.margin ), height - ( this.margin * 3 ) );

        Log.d("P/G Rect", "left " + playGroundRect.left + ", right " + playGroundRect.right + ", top " + playGroundRect.top + ", bottom " + playGroundRect.bottom);
        //invalidate();
    }

}
