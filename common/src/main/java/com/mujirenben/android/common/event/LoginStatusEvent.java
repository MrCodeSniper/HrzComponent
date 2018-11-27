package com.mujirenben.android.common.event;


/**
 * @author: panrongfu
 * @date: 2018/7/17 20:47
 * @describe:
 */

public class LoginStatusEvent {
    public static final int LOGIN_REQUEST_CODE_KOULING_BUY = 1000;
    public static final int LOGIN_REQUEST_CODE_KOULING_SHARE = 1000;

    private boolean isLogin;
    private int requestCode = -1;

    public LoginStatusEvent(boolean isLogin) {
        this(isLogin, -1);
    }

    public LoginStatusEvent(boolean isLogin, int requestCode) {
        this.isLogin = isLogin;
        this.requestCode = requestCode;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public int getRequestCode() {
        return requestCode;
    }
}
