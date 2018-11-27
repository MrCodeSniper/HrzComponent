package com.mujirenben.android.common.entity;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/8/16.Best Wishes to You!  []~(~▽~)~* Cheers!


public class EntityData {


    /**
     * code : 00000
     * msg : 成功
     * success : true
     * data : null
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

    @Override
    public String toString() {
        return "EntityData{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", data='" + data + '\'' +
                '}';
    }
}
