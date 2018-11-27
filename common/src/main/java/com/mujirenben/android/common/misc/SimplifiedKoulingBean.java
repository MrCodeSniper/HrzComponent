package com.mujirenben.android.common.misc;

public class SimplifiedKoulingBean {

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
        return "SimplifiedKoulingBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    public class Data {

        private long id;
        private String platformPid;
        private String name;
        private float orgPrice;
        private float coupon;
        private float commission;
        private String img;
        private int platform;
        private int idType;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getPlatformPid() {
            return platformPid;
        }

        public void setPlatformPid(String platformPid) {
            this.platformPid = platformPid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getOrgPrice() {
            return orgPrice;
        }

        public void setOrgPrice(float orgPrice) {
            this.orgPrice = orgPrice;
        }

        public float getCoupon() {
            return coupon;
        }

        public void setCoupon(float coupon) {
            this.coupon = coupon;
        }

        public float getCommission() {
            return commission;
        }

        public void setCommission(float commission) {
            this.commission = commission;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getPlatform() {
            return platform;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
        }

        public int getIdType() {
            return idType;
        }

        public void setIdType(int idType) {
            this.idType = idType;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", platformPid='" + platformPid + '\'' +
                    ", name='" + name + '\'' +
                    ", orgPrice=" + orgPrice +
                    ", coupon=" + coupon +
                    ", commission=" + commission +
                    ", img='" + img + '\'' +
                    ", platform=" + platform +
                    ", idType=" + idType +
                    '}';
        }
    }
}
