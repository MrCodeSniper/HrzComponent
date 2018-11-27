package com.mujirenben.android.common.event;

/**
 * @author: panrongfu
 * @date: 2018/9/4 12:21
 * @describe:
 */

public class TokenOutTimeEvent {
    boolean outTime;

    public TokenOutTimeEvent(boolean outTime) {
        this.outTime = outTime;
    }

    public boolean isOutTime() {
        return outTime;
    }

    public void setOutTime(boolean outTime) {
        this.outTime = outTime;
    }
}
