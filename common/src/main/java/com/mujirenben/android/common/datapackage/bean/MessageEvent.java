package com.mujirenben.android.common.datapackage.bean;

/**
 * Created by mac on 2018/5/14.
 */

public class MessageEvent{
    private String message;
    public  MessageEvent(String message){
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


