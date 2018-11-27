package com.mujirenben.android.home.mvp.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class WaterfallBean implements MultiItemEntity {

    public static final int TYPE_NONE = 0;
    public static final int TYPE_PRODUCT = 1;
    public static final int TYPE_SHOP = 2;
    public static final int TYPE_ADVERTISE = 3;

    private HomeModuleBean.DataBean.Product product;
    private HomeModuleBean.DataBean.Shop shop;
    private HomeModuleBean.DataBean.Advertise advertise;

    public HomeModuleBean.DataBean.Product getProduct() {
        return product;
    }

    public void setProduct(HomeModuleBean.DataBean.Product product) {
        this.product = product;
    }

    public HomeModuleBean.DataBean.Shop getShop() {
        return shop;
    }

    public void setShop(HomeModuleBean.DataBean.Shop shop) {
        this.shop = shop;
    }

    public HomeModuleBean.DataBean.Advertise getAdvertise() {
        return advertise;
    }

    public void setAdvertise(HomeModuleBean.DataBean.Advertise advertise) {
        this.advertise = advertise;
    }

    @Override
    public String toString() {
        return "WaterfallBean{" +
                "product=" + product +
                ", shop=" + shop +
                ", advertise=" + advertise +
                '}';
    }

    @Override
    public int getItemType() {
        if (product != null) return TYPE_PRODUCT;
        if (shop != null) return TYPE_SHOP;
        if (advertise != null) return TYPE_ADVERTISE;
        return TYPE_NONE;
    }
}
