package com.mujirenben.android.mine.mvp.model.bean;

/**
 * @author: panrongfu
 * @date: 2018/8/18 16:55
 * @describe:
 */

public class VerifyCodeBean {

    /**
     * code : 00000
     * message :
     * success : true
     * data : 3786
     */

    private String code;
    private String message;
    private boolean success;
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
