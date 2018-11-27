package com.mujirenben.android.mine.mvp.model;

public class InviteCodeBean {

    private String code;
    private String message;
    private boolean success;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "InviteCodeBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    public class Data {

        private boolean hasRel;
        private String nikeName;
        private String avatarUrl;
        private String inviteCode;

        public boolean isHasRel() {
            return hasRel;
        }

        public void setHasRel(boolean hasRel) {
            this.hasRel = hasRel;
        }

        public String getNikeName() {
            return nikeName;
        }

        public void setNikeName(String nikeName) {
            this.nikeName = nikeName;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "hasRel=" + hasRel +
                    ", nikeName='" + nikeName + '\'' +
                    ", avatarUrl='" + avatarUrl + '\'' +
                    ", inviteCode='" + inviteCode + '\'' +
                    '}';
        }
    }
}
