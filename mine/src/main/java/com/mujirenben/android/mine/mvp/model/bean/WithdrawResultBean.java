package com.mujirenben.android.mine.mvp.model.bean;

/**
 * @author: panrongfu
 * @date: 2018/8/6 15:56
 * @describe:
 */

public class WithdrawResultBean {

    /**
     * code : 00000
     * message :
     * success : true
     */

    private String code;
    private String message;
    private boolean success;

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
}
