package com.mujirenben.android.common.event;

/**
 * @author: panrongfu
 * @date: 2018/8/25 19:54
 * @describe:
 */

public class KeFuInitEvent {

    public boolean success;

    public KeFuInitEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
