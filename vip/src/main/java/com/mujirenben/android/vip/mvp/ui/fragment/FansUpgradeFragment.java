package com.mujirenben.android.vip.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseFragment;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.widget.dialog.MessageOnlyDialog;
import com.mujirenben.android.vip.R;
import com.mujirenben.android.vip.R2;
import com.mujirenben.android.vip.di.component.DaggerFansAndCrownUpgradeComponent;
import com.mujirenben.android.vip.di.module.FansAndCrownUpgradeModule;
import com.mujirenben.android.vip.mvp.contract.FansAndCrownUpgradeContract;
import com.mujirenben.android.vip.mvp.model.bean.PrivilegeBean;
import com.mujirenben.android.vip.mvp.model.bean.VipUpgradeInfo;
import com.mujirenben.android.vip.mvp.presenter.FansAndCrownUpgradePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FansUpgradeFragment extends BaseFragment<FansAndCrownUpgradePresenter>
        implements FansAndCrownUpgradeContract.View{

    private LinearLayout mPrivilegesLL;

    private List<PrivilegeBean> mPrivileges = new ArrayList<>();

    @BindView(R2.id.tv_upgrade_title)
    TextView mUpgradeTitleTV;

    @BindView(R2.id.tv_current)
    TextView mCurrentTV;

    @BindView(R2.id.tv_total)
    TextView mTotalTV;

    @BindView(R2.id.tv_tip)
    TextView mTipTV;

    @BindView(R2.id.tv_gain_more)
    TextView mGainMoreTV;

    @BindView(R2.id.pb_progress)
    ProgressBar mProgressPB;

    public FansUpgradeFragment() {
        // Required empty public constructor
    }

    public static FansUpgradeFragment newInstance(String param1, String param2) {
        FansUpgradeFragment fragment = new FansUpgradeFragment();
        return fragment;
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFansAndCrownUpgradeComponent.builder()
                .appComponent(appComponent)
                .fansAndCrownUpgradeModule(new FansAndCrownUpgradeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fans_upgrade, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mPresenter.loadVipUpgradeInfo();

        mPrivileges.add(new PrivilegeBean("个人消费",
                "自己买商品获取商品佣金的50%。",
                "自己有效购买一个佣金为100的商品，可以获得50元分佣，比粉丝多得10元。",
                R.drawable.icon_fans_upgrade_privilege_1));
        mPrivileges.add(new PrivilegeBean("分享消费赚",
                "分享商品给其他人购买获取商品佣金的50%。",
                "分享商品给其他人购买获取商品佣金的比例。案例：分享一个佣金为100的商品，朋友去电商平台（淘宝/京东）有效购买，你可以获得50元分佣，比粉丝多得10元。",
                R.drawable.icon_fans_upgrade_privilege_2));
        mPrivileges.add(new PrivilegeBean("直属粉丝消费分佣",
                "拿直推粉丝/皇冠购买商品分佣的比例20%。",
                "你的所有粉丝/皇冠在红人装有效购买了一个佣金为100的商品，你可以获得20元分佣。",
                R.drawable.icon_fans_upgrade_privilege_3));
        mPrivileges.add(new PrivilegeBean("粉丝升级提成",
                "拿直推粉丝升级皇冠付费礼包提成70元。",
                "你的每一个一级粉丝升级皇冠你均获得70元。",
                R.drawable.icon_fans_upgrade_privilege_4));
        mPrivileges.add(new PrivilegeBean("线下商家利润",
                "对接店拿所有用户消费分佣的比例10%",
                "你对接一个线下商铺接入红人装线下联盟，所有红人装用户在商铺消费你均获得10%的分佣。",
                R.drawable.icon_crown_upgrade_privilege_5));

        mPrivilegesLL = getView().findViewById(R.id.ll_privileges);

        getView().findViewById(R.id.tv_invite).setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPaths.VIP_QR_CODE_ACTIVITY)
                        .navigation(getActivity());
            }
        });

        getView().findViewById(R.id.tv_upgrade_1).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouter.getInstance().build(ARouterPaths.UPGRADE_BAG_ACTIVITY).navigation();
                    }
                });

        getView().findViewById(R.id.tv_upgrade_2).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ARouter.getInstance().build(ARouterPaths.UPGRADE_BAG_ACTIVITY).navigation();
                    }
                });

        addPrivileges();
    }

    private void addPrivileges() {
        for (PrivilegeBean privilege : mPrivileges) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.include_privilege_item, null);
            TextView titleTV = view.findViewById(R.id.tv_title);
            TextView subtitleTV = view.findViewById(R.id.tv_sub_title);
            ImageView iconIV = view.findViewById(R.id.iv_icon);

            titleTV.setText(privilege.title);
            subtitleTV.setText(privilege.subtitle);
            iconIV.setImageResource(privilege.iconId);

            view.findViewById(R.id.ll_demo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageOnlyDialog dialog = new MessageOnlyDialog(getActivity());
                    dialog.setMessage(privilege.demo);
                    dialog.show();
                }
            });

            mPrivilegesLL.addView(view);
        }
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
                || info.getData().getLv() != 0
                || info.getData().getLv0() == null) {
            return;
        }

        VipUpgradeInfo.FansUpgradeInfo fansUpgradeInfo = info.getData().getLv0();
        int current = fansUpgradeInfo.getDone();
        int total = fansUpgradeInfo.getRequired();

        mProgressPB.setMax(total);
        mProgressPB.setProgress(current);
        mUpgradeTitleTV.setText(String.format("累积邀请%d个完成首次购物的粉丝", total));
        mCurrentTV.setText(current + "");
        mTotalTV.setText("/" + total);
        mTipTV.setText(String.format("已培养首购粉丝%d个，距离免费升级还差%d个", current, Math.max(total - current, 0)));
        mGainMoreTV.setText(String.format("免费升级为皇冠, 多赚%.2f元", fansUpgradeInfo.getMoreProfit()));
    }
}
