package com.mujirenben.android.mine.mvp.model.bean;

/**
 * @author: panrongfu
 * @date: 2018/8/15 21:42
 * @describe:
 */

public class IdCardUploadBean {

    /**
     * code : 10000
     * msg : 成功
     * success : true
     * data : http://imgcdn.tlgn365.com/2018-07-25/78a509b5-62ad-4f6c-a143-2e149b6f5ed4.png
     */

    private String code;
    private String msg;
    private boolean success;
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
