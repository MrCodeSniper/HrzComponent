package com.mujirenben.android.common.datapackage.bean;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/7/14.Best Wishes to You!  []~(~▽~)~* Cheers!


public class BaseMockEntity {

    private String code;
    private String message;
    private String success;

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

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success='" + success + '\'' +
                '}';
    }
}
