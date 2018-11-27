package com.mujirenben.android.xsh.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/7/8 0008.
 */

public class ShareQrResult {

    private int status;
    private String reason;
    private Data data;

    public class Data{


        private String generate;
        private String avatar;
        private String text;

        private String label;

        public String getGenerate() {
            return generate;
        }

        public void setGenerate(String generate) {
            this.generate = generate;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        private List<String> urllist;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<String> getUrllist() {
            return urllist;
        }

        public void setUrllist(List<String> urllist) {
            this.urllist = urllist;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
