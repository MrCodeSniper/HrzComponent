package com.mujirenben.android.common.datapackage.bean;

import java.io.Serializable;
import java.util.List;

public class SelfRunGoodsBean implements Serializable{

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
        return "SelfRunGoodsBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    public class Data implements Serializable {
        private long id;
        private String name;
        private String img;
        private int stock;
        private float orgPrice;
        private float coupon;
        private int saleVolume;
        private int type;
        private int platform;
        private List<String> imgs;
        private List<Sku> skus;
        private List<Attr> attrs;
        private Deliver deliver;
        private List<Desc> desc;
        private int minNum;

        public float getCoupon() {
            return coupon;
        }

        public void setCoupon(float coupon) {
            this.coupon = coupon;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
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

        public int getSaleVolume() {
            return saleVolume;
        }

        public void setSaleVolume(int saleVolume) {
            this.saleVolume = saleVolume;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPlatform() {
            return platform;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }

        public List<Sku> getSkus() {
            return skus;
        }

        public void setSkus(List<Sku> skus) {
            this.skus = skus;
        }

        public List<Attr> getAttrs() {
            return attrs;
        }

        public void setAttrs(List<Attr> attrs) {
            this.attrs = attrs;
        }

        public Deliver getDeliver() {
            return deliver;
        }

        public void setDeliver(Deliver deliver) {
            this.deliver = deliver;
        }

        public List<Desc> getDesc() {
            return desc;
        }

        public void setDesc(List<Desc> desc) {
            this.desc = desc;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getMinNum() {
            return minNum;
        }

        public void setMinNum(int minNum) {
            this.minNum = minNum;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", img='" + img + '\'' +
                    ", stock=" + stock +
                    ", orgPrice=" + orgPrice +
                    ", coupon=" + coupon +
                    ", saleVolume=" + saleVolume +
                    ", type=" + type +
                    ", platform=" + platform +
                    ", imgs=" + imgs +
                    ", skus=" + skus +
                    ", attrs=" + attrs +
                    ", deliver=" + deliver +
                    ", desc=" + desc +
                    ", minNum=" + minNum +
                    '}';
        }
    }

    public class Desc implements Serializable {

        private String content;
        private String label;
        private long sort;

        public long getSort() {
            return sort;
        }

        public void setSort(long sort) {
            this.sort = sort;
        }

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

        @Override
        public String toString() {
            return "Desc{" +
                    "content='" + content + '\'' +
                    ", label='" + label + '\'' +
                    '}';
        }
    }

    public class Deliver implements Serializable {

        private float postage;
        private int isNoFee;

        public float getPostage() {
            return postage;
        }

        public void setPostage(float postage) {
            this.postage = postage;
        }

        public int getIsNoFee() {
            return isNoFee;
        }

        public void setIsNoFee(int isNoFee) {
            this.isNoFee = isNoFee;
        }

        @Override
        public String toString() {
            return "Deliver{" +
                    "postage=" + postage +
                    ", isNoFee=" + isNoFee +
                    '}';
        }
    }

    public class Sku implements Serializable {
        private long skuId;
        private float price;
        private String attrsPath;
        private String img;
        private int stock;

        public long getSkuId() {
            return skuId;
        }

        public void setSkuId(long skuId) {
            this.skuId = skuId;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getAttrsPath() {
            return attrsPath;
        }

        public void setAttrsPath(String attrsPath) {
            this.attrsPath = attrsPath;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        @Override
        public String toString() {
            return "Sku{" +
                    "skuId=" + skuId +
                    ", price=" + price +
                    ", attrsPath='" + attrsPath + '\'' +
                    ", img='" + img + '\'' +
                    ", stock=" + stock +
                    '}';
        }
    }

    public class Attr implements Serializable {
        private long attrId;
        private String name;
        private List<Value> values;

        public long getAttrId() {
            return attrId;
        }

        public void setAttrId(long attrId) {
            this.attrId = attrId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Value> getValues() {
            return values;
        }

        public void setValues(List<Value> values) {
            this.values = values;
        }

        @Override
        public String toString() {
            return "Attr{" +
                    "attrId=" + attrId +
                    ", name='" + name + '\'' +
                    ", values=" + values +
                    '}';
        }
    }

    public class Value implements Serializable {
        private long valueId;
        private String name;
        private String img;

        public long getValueId() {
            return valueId;
        }

        public void setValueId(long valueId) {
            this.valueId = valueId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        @Override
        public String toString() {
            return "Value{" +
                    "valueId=" + valueId +
                    ", name='" + name + '\'' +
                    ", img='" + img + '\'' +
                    '}';
        }
    }

    public class PostWay implements Serializable {
        private long postWayId;
        private int firstNum;
        private float firstFee;
        private int secondNum;
        private float secondFee;
        private String postageWay;
        private int isNoFee;
        private String remark;
        private int isDefault;

        public long getPostWayId() {
            return postWayId;
        }

        public void setPostWayId(long postWayId) {
            this.postWayId = postWayId;
        }

        public int getFirstNum() {
            return firstNum;
        }

        public void setFirstNum(int firstNum) {
            this.firstNum = firstNum;
        }

        public float getFirstFee() {
            return firstFee;
        }

        public void setFirstFee(float firstFee) {
            this.firstFee = firstFee;
        }

        public int getSecondNum() {
            return secondNum;
        }

        public void setSecondNum(int secondNum) {
            this.secondNum = secondNum;
        }

        public float getSecondFee() {
            return secondFee;
        }

        public void setSecondFee(float secondFee) {
            this.secondFee = secondFee;
        }

        public String getPostageWay() {
            return postageWay;
        }

        public void setPostageWay(String postageWay) {
            this.postageWay = postageWay;
        }

        public int getIsNoFee() {
            return isNoFee;
        }

        public void setIsNoFee(int isNoFee) {
            this.isNoFee = isNoFee;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }

        @Override
        public String toString() {
            return "PostWay{" +
                    "postWayId=" + postWayId +
                    ", firstNum=" + firstNum +
                    ", firstFee=" + firstFee +
                    ", secondNum=" + secondNum +
                    ", secondFee=" + secondFee +
                    ", postageWay='" + postageWay + '\'' +
                    ", isNoFee=" + isNoFee +
                    ", remark='" + remark + '\'' +
                    ", isDefault=" + isDefault +
                    '}';
        }
    }
}
