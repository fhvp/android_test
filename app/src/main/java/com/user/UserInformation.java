package com.user;

import android.graphics.Point;

/**
 * Created by admin on 2016-10-05.
 */

public interface UserInformation {
    enum userState {
        USER_STATE_IDEL,
        USER_STATE_READY,
        USER_STATE_RUN,
        USER_STATE_DEAD,
        USER_STATE_ERROR
    }
}
