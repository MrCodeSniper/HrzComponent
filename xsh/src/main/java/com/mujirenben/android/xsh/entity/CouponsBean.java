package com.mujirenben.android.xsh.entity;

import java.util.List;

public class CouponsBean {

    private String code;
    private List<WelfareBean.CouponsSubBean> coupons;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<WelfareBean.CouponsSubBean> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<WelfareBean.CouponsSubBean> coupons) {
        this.coupons = coupons;
    }
}
