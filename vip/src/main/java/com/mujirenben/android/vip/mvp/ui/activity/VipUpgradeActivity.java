package com.mujirenben.android.vip.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.vip.R;
import com.mujirenben.android.vip.mvp.ui.fragment.CrownUpgradeFragment;
import com.mujirenben.android.vip.mvp.ui.fragment.FansUpgradeFragment;
import com.mujirenben.android.vip.mvp.ui.fragment.ShopKeeperUpgradeFragment;

@Route(path = ARouterPaths.VIP_UPGRADE_ACTIVITY)
public class VipUpgradeActivity extends FragmentActivity {

    public static final String KEY_UPGRADE_TYPE = "upgrade_type";

    private static final int UPGRADE_TYPE_FANS = 0;
    private static final int UPGRADE_TYPE_CROWN = 1;
    private static final int UPGRADE_TYPE_SHOP_KEEPER = 2;

    private int mUpgradeType = UPGRADE_TYPE_FANS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        StatusBarUtil.setStatusBarWhite(this);
        if (SpUtil.getIsMIUI(this)){
            StatusBarUtil.getStatusBarHelper(this,R.color.white).into(this);
        }else {
            StatusBarUtil.setStatusBarWhite(this);
        }
        Intent intent = getIntent();
        mUpgradeType = intent.getIntExtra(KEY_UPGRADE_TYPE, UPGRADE_TYPE_SHOP_KEEPER);

        setContentView(R.layout.activity_vip_upgrade);

        String title = "会员升级";

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment content = null;
        switch (mUpgradeType) {
            case UPGRADE_TYPE_FANS:
                content = new FansUpgradeFragment();
                title = "升级皇冠";
                break;
            case UPGRADE_TYPE_CROWN:
                content = new CrownUpgradeFragment();
                title = "升级店主";
                break;
            case UPGRADE_TYPE_SHOP_KEEPER:
                content = new ShopKeeperUpgradeFragment();
                title = "店主升级";
                break;
        }
        transaction.add(R.id.fl_content, content);
        transaction.commit();

        ((TextView)findViewById(R.id.tv_title)).setText(title);
        findViewById(R.id.ib_left_action).setOnClickListener(v -> finish());
    }
}
