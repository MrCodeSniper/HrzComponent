package com.mujirenben.android.mine.mvp.model.bean;


import com.mujirenben.android.common.widget.tablayout.listener.CustomTabEntity;

/**
 * @author: panrongfu
 * @date: 2018/7/19 21:24
 * @describe:
 */

public class MineTabEntity implements CustomTabEntity {

    String title;

    public MineTabEntity(String title) {
        this.title = title;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return 0;
    }

    @Override
    public int getTabUnselectedIcon() {
        return 0;
    }
}
