package com.mujirenben.android.mine.mvp.model.bean;

/**
 * @author: panrongfu
 * @date: 2018/8/17 10:51
 * @describe:
 */

public class LockFanBean {

    /**
     * code : 00000
     * message : 成功绑定邀请人
     * success : true
     * data : null
     */

    private String code;
    private String message;
    private boolean success;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
