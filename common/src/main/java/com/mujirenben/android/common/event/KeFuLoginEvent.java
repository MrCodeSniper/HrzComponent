package com.mujirenben.android.common.event;

/**
 * @author: panrongfu
 * @date: 2018/7/30 11:24
 * @describe:
 */

public class KeFuLoginEvent {

    boolean keFuLogin;

    public KeFuLoginEvent(boolean keFuLogin) {
        this.keFuLogin = keFuLogin;
    }

    public boolean getkeFuLogin() {
        return keFuLogin;
    }
}
