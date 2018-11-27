package com.mujirenben.android.common.event;

public class WeiXinEvent {
    private int type;//1:登录 2.分享 3:微信支付
    private int errCode;//微信返回的错误码
    private String code;//登录成功才会有的code

    public final static int WEIXIN_TYPE_LOGIN = 1;
    public final static int WEIXIN_TYPE_SHARE = 2;
    public final static int WEIXIN_TYPE_PAY = 3;

    public WeiXinEvent() {
    }

    public WeiXinEvent(int type, int errCode, String code) {
        this.type = type;
        this.errCode = errCode;
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}

