package com.mujirenben.android.xsh.entity;

/**
 * Created by Thinkpad on 2018/4/25.
 */

public class NewShopEntity {
    private String address;
    private String storeName;

    public NewShopEntity(String address, String storeName) {
        this.address = address;
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
