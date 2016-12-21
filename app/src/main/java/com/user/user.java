package com.user;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by admin on 2016-10-05.
 */

public class user extends ImageView {

    public Point mPoint = new Point();
    public UserInformation.userState mState = UserInformation.userState.USER_STATE_IDEL;
    public int icon_width = 0;
    public int icon_height = 0;

    private final int DEFAULT_ICON_WIDTH = 48; //72
    private final int DEFAULT_ICON_HEIGHT = 48;//72
    public String mImageSrc = new String();

    public user(Context context) {
        super(context);

        this.icon_width = DEFAULT_ICON_WIDTH;
        this.icon_height = DEFAULT_ICON_HEIGHT;
    }

    public user(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        this.icon_width = DEFAULT_ICON_WIDTH;
        this.icon_height = DEFAULT_ICON_HEIGHT;
    }

    public void setPoint(Point point) {
        this.mPoint = point;
    }

    public Point getPoint() {
        return this.mPoint;
    }
}
