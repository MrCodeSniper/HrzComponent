package com.mujirenben.android.mine.login.bean;

public class LogoutResultBean {
    /**
     * code : 00000
     * message : null
     * success : true
     * data : null
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

    @Override
    public String toString() {
        return "LogoutResultBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data='" + data + '\'' +
                '}';
    }
}
