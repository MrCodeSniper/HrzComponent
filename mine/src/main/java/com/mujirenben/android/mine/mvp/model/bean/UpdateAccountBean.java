package com.mujirenben.android.mine.mvp.model.bean;

/**
 * @author: panrongfu
 * @date: 2018/8/6 16:09
 * @describe: 保存提现信息
 */

public class UpdateAccountBean {


    /**
     * code : 00000
     * message :
     * success : true
     * data : {"result":"0","desc":"保存成功"}
     */

    private String code;
    private String message;
    private boolean success;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * result : 0
         * desc : 保存成功
         */

        private String result;
        private String desc;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
