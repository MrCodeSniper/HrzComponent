package com.mujirenben.android.xsh.entity;

import java.util.List;

public class AllianceHomeShops extends BaseEntity {


    private List<DataBean> data;

    @Override
    public String toString() {
        return "AllianceHomeShops{" +
                "data=" + data +
                '}';
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {


        private int id;
        private Object merchantUserId;
        private Object referrerUserid;
        private String storeName;
        private Object telephone;
        private Object userName;
        private Object card;
        private Object frontIdentity;
        private Object reverseIdentity;
        private Object inhandIdentity;
        private Object documentation;
        private String storefrontThumb;
        private Object storeinsideThumb;
        private Object commission;
        private String phone;
        private Object province;
        private Object city;
        private Object area;
        private String address;
        private Object bankName;
        private Object bankNum;
        private Object tradeid;
        private Object addtime;
        private Object edittime;
        private Object comment;
        private Object isDelete;
        private Object licenseTitle;
        private Object registrNum;
        private Object lawPerson;
        private Object abode;
        private Object indateEnd;
        private Object indateStart;
        private Object businessLicense;
        private Object status;
        private Object cardSex;
        private Object cardBirth;
        private Object cardNationality;
        private Object cardAddress;
        private Object cardIssue;
        private Object cardStartDate;
        private Object cardEndDate;
        private Object isSign;
        private Object referrerCommission;
        private Object isActivation;
        private Object isSignUnionpay;
        private Object activationComment;
        private Object contractId;
        private Object contractUrl;
        private Object qrcodeId;
        private Object unionpayMerid;
        private Object referrerStatus;
        private Object videoImg;
        private Object videoUrl;
        private Object timeTab;
        private Object perCapita;
        private Object contractImg;
        private Object merchantType;
        private Object bankType;
        private Object bankProvince;
        private Object bankCity;
        private Object autographImg;
        private Object longitude;
        private Object latitude;
        private Object contractSignImg;
        private String industry;
        private Object region;
        private Object storefrontImgs;
        private Object environmentImgs;
        private Object serviceTabs;
        private Object businessStatus;
        private String couponInfo;
        private int distance;
        private List<CmsMerchantTimesBean> cmsMerchantTimes;


        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getMerchantUserId() {
            return merchantUserId;
        }

        public void setMerchantUserId(Object merchantUserId) {
            this.merchantUserId = merchantUserId;
        }

        public Object getReferrerUserid() {
            return referrerUserid;
        }

        public void setReferrerUserid(Object referrerUserid) {
            this.referrerUserid = referrerUserid;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public Object getTelephone() {
            return telephone;
        }

        public void setTelephone(Object telephone) {
            this.telephone = telephone;
        }

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
            this.userName = userName;
        }

        public Object getCard() {
            return card;
        }

        public void setCard(Object card) {
            this.card = card;
        }

        public Object getFrontIdentity() {
            return frontIdentity;
        }

        public void setFrontIdentity(Object frontIdentity) {
            this.frontIdentity = frontIdentity;
        }

        public Object getReverseIdentity() {
            return reverseIdentity;
        }

        public void setReverseIdentity(Object reverseIdentity) {
            this.reverseIdentity = reverseIdentity;
        }

        public Object getInhandIdentity() {
            return inhandIdentity;
        }

        public void setInhandIdentity(Object inhandIdentity) {
            this.inhandIdentity = inhandIdentity;
        }

        public Object getDocumentation() {
            return documentation;
        }

        public void setDocumentation(Object documentation) {
            this.documentation = documentation;
        }

        public String getStorefrontThumb() {
            return storefrontThumb;
        }

        public void setStorefrontThumb(String storefrontThumb) {
            this.storefrontThumb = storefrontThumb;
        }

        public Object getStoreinsideThumb() {
            return storeinsideThumb;
        }

        public void setStoreinsideThumb(Object storeinsideThumb) {
            this.storeinsideThumb = storeinsideThumb;
        }

        public Object getCommission() {
            return commission;
        }

        public void setCommission(Object commission) {
            this.commission = commission;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getProvince() {
            return province;
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public Object getArea() {
            return area;
        }

        public void setArea(Object area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Object getBankName() {
            return bankName;
        }

        public void setBankName(Object bankName) {
            this.bankName = bankName;
        }

        public Object getBankNum() {
            return bankNum;
        }

        public void setBankNum(Object bankNum) {
            this.bankNum = bankNum;
        }

        public Object getTradeid() {
            return tradeid;
        }

        public void setTradeid(Object tradeid) {
            this.tradeid = tradeid;
        }

        public Object getAddtime() {
            return addtime;
        }

        public void setAddtime(Object addtime) {
            this.addtime = addtime;
        }

        public Object getEdittime() {
            return edittime;
        }

        public void setEdittime(Object edittime) {
            this.edittime = edittime;
        }

        public Object getComment() {
            return comment;
        }

        public void setComment(Object comment) {
            this.comment = comment;
        }

        public Object getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(Object isDelete) {
            this.isDelete = isDelete;
        }

        public Object getLicenseTitle() {
            return licenseTitle;
        }

        public void setLicenseTitle(Object licenseTitle) {
            this.licenseTitle = licenseTitle;
        }

        public Object getRegistrNum() {
            return registrNum;
        }

        public void setRegistrNum(Object registrNum) {
            this.registrNum = registrNum;
        }

        public Object getLawPerson() {
            return lawPerson;
        }

        public void setLawPerson(Object lawPerson) {
            this.lawPerson = lawPerson;
        }

        public Object getAbode() {
            return abode;
        }

        public void setAbode(Object abode) {
            this.abode = abode;
        }

        public Object getIndateEnd() {
            return indateEnd;
        }

        public void setIndateEnd(Object indateEnd) {
            this.indateEnd = indateEnd;
        }

        public Object getIndateStart() {
            return indateStart;
        }

        public void setIndateStart(Object indateStart) {
            this.indateStart = indateStart;
        }

        public Object getBusinessLicense() {
            return businessLicense;
        }

        public void setBusinessLicense(Object businessLicense) {
            this.businessLicense = businessLicense;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getCardSex() {
            return cardSex;
        }

        public void setCardSex(Object cardSex) {
            this.cardSex = cardSex;
        }

        public Object getCardBirth() {
            return cardBirth;
        }

        public void setCardBirth(Object cardBirth) {
            this.cardBirth = cardBirth;
        }

        public Object getCardNationality() {
            return cardNationality;
        }

        public void setCardNationality(Object cardNationality) {
            this.cardNationality = cardNationality;
        }

        public Object getCardAddress() {
            return cardAddress;
        }

        public void setCardAddress(Object cardAddress) {
            this.cardAddress = cardAddress;
        }

        public Object getCardIssue() {
            return cardIssue;
        }

        public void setCardIssue(Object cardIssue) {
            this.cardIssue = cardIssue;
        }

        public Object getCardStartDate() {
            return cardStartDate;
        }

        public void setCardStartDate(Object cardStartDate) {
            this.cardStartDate = cardStartDate;
        }

        public Object getCardEndDate() {
            return cardEndDate;
        }

        public void setCardEndDate(Object cardEndDate) {
            this.cardEndDate = cardEndDate;
        }

        public Object getIsSign() {
            return isSign;
        }

        public void setIsSign(Object isSign) {
            this.isSign = isSign;
        }

        public Object getReferrerCommission() {
            return referrerCommission;
        }

        public void setReferrerCommission(Object referrerCommission) {
            this.referrerCommission = referrerCommission;
        }

        public Object getIsActivation() {
            return isActivation;
        }

        public void setIsActivation(Object isActivation) {
            this.isActivation = isActivation;
        }

        public Object getIsSignUnionpay() {
            return isSignUnionpay;
        }

        public void setIsSignUnionpay(Object isSignUnionpay) {
            this.isSignUnionpay = isSignUnionpay;
        }

        public Object getActivationComment() {
            return activationComment;
        }

        public void setActivationComment(Object activationComment) {
            this.activationComment = activationComment;
        }

        public Object getContractId() {
            return contractId;
        }

        public void setContractId(Object contractId) {
            this.contractId = contractId;
        }

        public Object getContractUrl() {
            return contractUrl;
        }

        public void setContractUrl(Object contractUrl) {
            this.contractUrl = contractUrl;
        }

        public Object getQrcodeId() {
            return qrcodeId;
        }

        public void setQrcodeId(Object qrcodeId) {
            this.qrcodeId = qrcodeId;
        }

        public Object getUnionpayMerid() {
            return unionpayMerid;
        }

        public void setUnionpayMerid(Object unionpayMerid) {
            this.unionpayMerid = unionpayMerid;
        }

        public Object getReferrerStatus() {
            return referrerStatus;
        }

        public void setReferrerStatus(Object referrerStatus) {
            this.referrerStatus = referrerStatus;
        }

        public Object getVideoImg() {
            return videoImg;
        }

        public void setVideoImg(Object videoImg) {
            this.videoImg = videoImg;
        }

        public Object getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(Object videoUrl) {
            this.videoUrl = videoUrl;
        }

        public Object getTimeTab() {
            return timeTab;
        }

        public void setTimeTab(Object timeTab) {
            this.timeTab = timeTab;
        }

        public Object getPerCapita() {
            return perCapita;
        }

        public void setPerCapita(Object perCapita) {
            this.perCapita = perCapita;
        }

        public Object getContractImg() {
            return contractImg;
        }

        public void setContractImg(Object contractImg) {
            this.contractImg = contractImg;
        }

        public Object getMerchantType() {
            return merchantType;
        }

        public void setMerchantType(Object merchantType) {
            this.merchantType = merchantType;
        }

        public Object getBankType() {
            return bankType;
        }

        public void setBankType(Object bankType) {
            this.bankType = bankType;
        }

        public Object getBankProvince() {
            return bankProvince;
        }

        public void setBankProvince(Object bankProvince) {
            this.bankProvince = bankProvince;
        }

        public Object getBankCity() {
            return bankCity;
        }

        public void setBankCity(Object bankCity) {
            this.bankCity = bankCity;
        }

        public Object getAutographImg() {
            return autographImg;
        }

        public void setAutographImg(Object autographImg) {
            this.autographImg = autographImg;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Object getContractSignImg() {
            return contractSignImg;
        }

        public void setContractSignImg(Object contractSignImg) {
            this.contractSignImg = contractSignImg;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public Object getRegion() {
            return region;
        }

        public void setRegion(Object region) {
            this.region = region;
        }

        public Object getStorefrontImgs() {
            return storefrontImgs;
        }

        public void setStorefrontImgs(Object storefrontImgs) {
            this.storefrontImgs = storefrontImgs;
        }

        public Object getEnvironmentImgs() {
            return environmentImgs;
        }

        public void setEnvironmentImgs(Object environmentImgs) {
            this.environmentImgs = environmentImgs;
        }

        public Object getServiceTabs() {
            return serviceTabs;
        }

        public void setServiceTabs(Object serviceTabs) {
            this.serviceTabs = serviceTabs;
        }

        public Object getBusinessStatus() {
            return businessStatus;
        }

        public void setBusinessStatus(Object businessStatus) {
            this.businessStatus = businessStatus;
        }

        public String getCouponInfo() {
            return couponInfo;
        }

        public void setCouponInfo(String couponInfo) {
            this.couponInfo = couponInfo;
        }

        public List<CmsMerchantTimesBean> getCmsMerchantTimes() {
            return cmsMerchantTimes;
        }

        public void setCmsMerchantTimes(List<CmsMerchantTimesBean> cmsMerchantTimes) {
            this.cmsMerchantTimes = cmsMerchantTimes;
        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", merchantUserId=" + merchantUserId +
                    ", referrerUserid=" + referrerUserid +
                    ", cmsMerchantTimes=" + cmsMerchantTimes +
                    '}';
        }
    }


    public static class CmsMerchantTimesBean {
        /**
         * id : 1
         * merchantId : 241
         * startTime : 01:04:31
         * endTime : 20:06:33
         */

        private int id;
        private int merchantId;
        private String startTime;
        private String endTime;


        @Override
        public String toString() {
            return "CmsMerchantTimesBean{" +
                    "id=" + id +
                    ", merchantId=" + merchantId +
                    ", startTime='" + startTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(int merchantId) {
            this.merchantId = merchantId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }



}
