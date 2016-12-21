package com.enemy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * Created by admin on 2016-09-13.
 */
public class Circle extends View {

    public static final int SPEED_VERY_SLOW = 1;
    public static final int SPEED_SLOW = 2;
    public static final int SPEED_FAST = 3;
    public static final int SPEED_VERY_FAST = 4;

    private final int DEFAULT_SPEED = Circle.SPEED_SLOW;
    private final int DEFAULT_RADIUS = 15;
    private final int DEFAULT_DURATION = 30000;

    public boolean isFirst = true;
    public int x;
    public int y;
    public int color;
    public int speed;
    public int radius;
    public int duration;

    private Paint paint;
    //private final RectF rect;

    public Circle(Context context) {
        super(context);

        final int strokeWidth = 40;

        paint = new Paint();
        paint.setAntiAlias(true);
        //paint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(strokeWidth);
        //Circle color

        Random random = new Random();
        int rand = random.nextInt(0xFF);

        rand += 0xFFFF00;
        paint.setColor(rand << 8);

        this.x = 0;
        this.y = 0;
        this.speed = this.DEFAULT_SPEED;
        this.radius = this.DEFAULT_RADIUS;
        this.duration = this.DEFAULT_DURATION;
    }

    public Circle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        final int strokeWidth = 40;

        paint = new Paint();
        paint.setAntiAlias(true);
        //paint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(strokeWidth);
        //Circle color
        paint.setColor(Color.RED);

        this.x = 0;
        this.y = 0;
        this.speed = this.DEFAULT_SPEED;
        this.radius = this.DEFAULT_RADIUS;
        this.duration = this.DEFAULT_DURATION;
    }

    public Circle(Context context, Point userPoint) {
        super(context);

        final int strokeWidth = 40;

        paint = new Paint();
        paint.setAntiAlias(true);
        //paint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(strokeWidth);
        //Circle color
        paint.setColor(Color.RED);

        this.x = userPoint.x;
        this.y = userPoint.y;
        this.speed = this.DEFAULT_SPEED;
        this.radius = this.DEFAULT_RADIUS;
        this.duration = this.DEFAULT_DURATION;
        //size 200x200 example
        //rect = new RectF(userPoint.x, userPoint.y, userPoint.x + 30, userPoint.y + 30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(this.radius, this.radius, this.radius, this.paint);

        //canvas.drawArc(rect, 90, angle, false, paint);
    }

    public void write(Canvas canvas)
    {
        Log.i("****", "Draw Circle X " + x + ", Y " + y);
        canvas.drawCircle(this.x--, this.y--, this.radius * 2, this.paint);
    }

    public int getSpeed() {
        return this.speed;
    }
}
