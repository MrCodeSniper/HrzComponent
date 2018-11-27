package com.mujirenben.android.common.misc;

public class KoulingGenerateResult {

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

    @Override
    public String toString() {
        return "KoulingGenerateResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    public class DataBean {
        private String tpwdCode;
        private String shareUrl;

        public String getTpwdCode() {
            return tpwdCode;
        }

        public void setTpwdCode(String tpwdCode) {
            this.tpwdCode = tpwdCode;
        }

        public String getShareUrl() {
            return shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "tpwdCode='" + tpwdCode + '\'' +
                    ", shareUrl='" + shareUrl + '\'' +
                    '}';
        }
    }
}
