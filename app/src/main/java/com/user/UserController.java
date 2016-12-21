package com.user;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;

public class UserController extends user implements UserInformation {

    private static UserController instance = null;

    public static UserController getInstance() {
        if(UserController.instance == null)
            return null;

        return UserController.instance;
    }

    public static UserController getInstance(Context context) {
        if(UserController.instance == null)
            UserController.instance = new UserController(context);

        return UserController.instance;
    }

    public static UserController getInstance(Context context, AttributeSet attributeSet) {
        if(UserController.instance == null)
            UserController.instance = new UserController(context, attributeSet);

        return UserController.instance;
    }

    private UserController(Context context) {
        super(context);
        if(this.mPoint == null) {
            this.mPoint = new Point();
        }

        this.mState = userState.USER_STATE_IDEL;
    }

    public UserController(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if(this.mPoint == null) {
            this.mPoint = new Point();
        }

        this.mState = userState.USER_STATE_IDEL;
    }

    @Override
    public void setPoint(Point point) {
        if(this.mPoint != null)
            this.mPoint = point;
    }

    @Override
    public Point getPoint() {
        return this.mPoint;
    }
}
