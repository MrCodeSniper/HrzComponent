package com.mujirenben.android.vip.mvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mujirenben.android.common.base.BaseFragment;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.vip.R;
import com.mujirenben.android.vip.R2;
import com.mujirenben.android.vip.di.component.DaggerShopKeeperUpgradeComponent;
import com.mujirenben.android.vip.di.module.ShopKeeperUpgradeModule;
import com.mujirenben.android.vip.mvp.contract.ShopKeeperUpgradeContract;
import com.mujirenben.android.vip.mvp.model.bean.VipUpgradeInfo;
import com.mujirenben.android.vip.mvp.presenter.ShopKeeperUpgradePresenter;

import butterknife.BindView;

public class ShopKeeperUpgradeFragment extends BaseFragment<ShopKeeperUpgradePresenter>
        implements ShopKeeperUpgradeContract.View {

    @BindView(R2.id.tv_current)
    TextView mCurrentTV;

    @BindView(R2.id.tv_total)
    TextView mTotalTV;

    @BindView(R2.id.tv_tip)
    TextView mTipTV;

    @BindView(R2.id.pb_progress)
    ProgressBar mProgressPB;

    @BindView(R2.id.tv_next_judge)
    TextView mNextJudgeTV;

    @BindView(R2.id.tv_shopkeeper_level)
    TextView mShopkeeperLevelTV;

    public ShopKeeperUpgradeFragment() {
        // Required empty public constructor
    }

    public static ShopKeeperUpgradeFragment newInstance(String param1, String param2) {
        ShopKeeperUpgradeFragment fragment = new ShopKeeperUpgradeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerShopKeeperUpgradeComponent.builder()
                .appComponent(appComponent)
                .shopKeeperUpgradeModule(new ShopKeeperUpgradeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop_keeper_upgrade, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.loadVipUpgradeInfo();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onVipUpgradeInfoLoaded(VipUpgradeInfo info) {
        if (info == null
                || !info.isSuccess()
                || info.getData() == null
                || info.getData().getLv() != 2
                || info.getData().getLv2() == null) {
            return;
        }

        VipUpgradeInfo.ShopkeeperUpgradeInfo shopkeeperUpgradeInfo = info.getData().getLv2();
        int current = shopkeeperUpgradeInfo.getDone();
        int total = 10;
        String lastLv = "五";
        switch (shopkeeperUpgradeInfo.getLastLv()) {
            case 0:
                total = shopkeeperUpgradeInfo.getLv1Required();
                lastLv = "五";
                break;
            case 1:
                total = shopkeeperUpgradeInfo.getLv2Required();
                lastLv = "十";
                break;
            case 2:
                total = shopkeeperUpgradeInfo.getLv3Required();
                lastLv = "十五";
                break;
        }

        mProgressPB.setMax(total);
        mProgressPB.setProgress(current);

        mShopkeeperLevelTV.setText(String.format("当前身份: %s星店主", lastLv));
        mCurrentTV.setText(current + "");
        mTotalTV.setText("/" + total);
        mTipTV.setText(String.format("本月已培养一代店主%d个，距离下一等级还差%d个", current, total - current));
        mNextJudgeTV.setText(String.format("距离下次升级评定仅剩%d天", shopkeeperUpgradeInfo.getRemainDays()));
    }
}
