package com.mujirenben.android.home.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.home.R;


@Route(path = "/com/seckill")
public class SecKillActivity extends BaseActivity {
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.secklill_activity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}
