package com.mujirenben.android.mine.mvp.model.bean;

import com.mujirenben.android.common.user.UserInfoBean;

/**
 * Created by Administrator on 2018\8\23 0023.
 */

public class UserInfo {


    /**
     * code : 00000
     * message : null
     * success : true
     * data : {"id":1000084,"sex":1,"phone":"13631406762","phoneValidate":1,"email":"","emailValidate":0,"nikeName":"你别较真这条数据真假性","unionId":"","openId":"oB7rst5i_CjmINheUYmG6A-Iowhh","avatarUrl":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKb3DkMqyx9lFTGnyZmbmmYQJQCjtLHDlPhv8XcTHdUG0ZFHicpLeSNlAu9hrqXFria3XoDgdnjD3RQ/132","status":0,"lv":1,"star":0,"expiredLv":1,"expiredDay":0}
     */

    private String code;
    private String message;
    private boolean success;
    private UserInfoBean data;

    @Override
    public String toString() {
        return "UserInfo{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

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

    public UserInfoBean getData() {
        return data;
    }

    public void setData(UserInfoBean data) {
        this.data = data;
    }

}
