package com.mujirenben.android.vip.mvp.model.bean;

import android.support.annotation.Keep;

@Keep
public class PrivilegeBean {
    public String title;
    public String subtitle;
    public int iconId;
    public String demo;

    public PrivilegeBean(String title, String subtitle, String demo, int iconId) {
        this.title = title;
        this.subtitle = subtitle;
        this.iconId = iconId;
        this.demo = demo;
    }
}
