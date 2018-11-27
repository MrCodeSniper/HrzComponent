package com.mujirenben.android.common.misc;

import java.util.List;

public class KoulingBean {

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
        return "KoulingBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    public class DataBean {
        private long id;
        private String platformPid;
        private String name;
        private float orgPrice;
        private float coupon;
        private float commission;
        private int saleVolume;
        private List<String> imgs;
        private List<MobileDesc>  mobileDesc;
        private List<Property> properties;
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

        public int getSaleVolume() {
            return saleVolume;
        }

        public void setSaleVolume(int saleVolume) {
            this.saleVolume = saleVolume;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }

        public List<MobileDesc> getMobileDesc() {
            return mobileDesc;
        }

        public void setMobileDesc(List<MobileDesc> mobileDesc) {
            this.mobileDesc = mobileDesc;
        }

        public List<Property> getProperties() {
            return properties;
        }

        public void setProperties(List<Property> properties) {
            this.properties = properties;
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
            return "DataBean{" +
                    "id=" + id +
                    ", platformPid='" + platformPid + '\'' +
                    ", name='" + name + '\'' +
                    ", orgPrice=" + orgPrice +
                    ", coupon=" + coupon +
                    ", commission=" + commission +
                    ", saleVolume=" + saleVolume +
                    ", imgs=" + imgs +
                    ", mobileDesc=" + mobileDesc +
                    ", properties=" + properties +
                    ", platform=" + platform +
                    ", idType=" + idType +
                    '}';
        }
    }

    public class Property {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Property{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public class MobileDesc {
        private String content;
        private String label;
        private int sort;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        @Override
        public String toString() {
            return "MobileDesc{" +
                    "content='" + content + '\'' +
                    ", label='" + label + '\'' +
                    ", sort=" + sort +
                    '}';
        }
    }
}
