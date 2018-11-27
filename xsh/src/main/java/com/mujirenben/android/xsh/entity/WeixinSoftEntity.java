package com.mujirenben.android.xsh.entity;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/8/15.Best Wishes to You!  []~(~▽~)~* Cheers!


public class WeixinSoftEntity {


    /**
     * code : 00000
     * message : null
     * success : true
     * data : http://xdz.hdyl.net.cn/abutment?pid=1000701
     */

    private String code;
    private Object message;
    private boolean success;
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
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
