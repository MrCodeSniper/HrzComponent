package com.mujirenben.android.vip.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.base.BaseFragment;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.bean.SelfRunGoodsBean;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.pay.OrderResultBean;
import com.mujirenben.android.common.pay.PayHelper;
import com.mujirenben.android.common.pay.SelfRunGoodsCommonService;
import com.mujirenben.android.common.widget.DialogUtils;
import com.mujirenben.android.common.widget.dialog.MessageOnlyDialog;
import com.mujirenben.android.vip.R;
import com.mujirenben.android.vip.R2;
import com.mujirenben.android.vip.di.component.DaggerFansAndCrownUpgradeComponent;
import com.mujirenben.android.vip.di.module.FansAndCrownUpgradeModule;
import com.mujirenben.android.vip.mvp.contract.FansAndCrownUpgradeContract;
import com.mujirenben.android.vip.mvp.model.PrivilegeBean;
import com.mujirenben.android.vip.mvp.model.bean.VipUpgradeInfo;
import com.mujirenben.android.vip.mvp.presenter.FansAndCrownUpgradePresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

public class CrownUpgradeFragment extends BaseFragment<FansAndCrownUpgradePresenter>
        implements FansAndCrownUpgradeContract.View {

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

    @BindView(R2.id.tv_last_upgrade_time)
    TextView mLastUpgradeTimeTV;

    private VipUpgradeInfo mData;
    private DialogUtils mLoadingDialog;


    public CrownUpgradeFragment() {
        // Required empty public constructor
    }

    public static CrownUpgradeFragment newInstance(String param1, String param2) {
        CrownUpgradeFragment fragment = new CrownUpgradeFragment();
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
        return inflater.inflate(R.layout.fragment_crown_upgrade, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mPresenter.loadVipUpgradeInfo();

        mPrivileges.add(new PrivilegeBean("个人消费",
                "自己买商品获取商品佣金的90%。",
                "自己有效购买一个佣金为100的商品，可以获得90元分佣，比皇冠多得40元。",
                R.drawable.icon_crown_upgrade_privilege_1));
        mPrivileges.add(new PrivilegeBean("分享消费赚",
                "分享商品给其他人购买获取商品佣金的90%。",
                "分享一个佣金为100的商品，朋友去电商平台（淘宝/京东）有效购买，你可以获得90元分佣，比皇冠多得40元。",
                R.drawable.icon_crown_upgrade_privilege_2));
        mPrivileges.add(new PrivilegeBean("直属粉丝消费分佣",
                "拿直推粉丝/皇冠购买商品分佣的比例20%。",
                "你的所有粉丝/皇冠在红人装有效购买了一个佣金为100的商品，你可以获得20元分佣。",
                R.drawable.icon_crown_upgrade_privilege_3));
        mPrivileges.add(new PrivilegeBean("专区商品销售提成",
                "拿直推粉丝升级皇冠付费礼包提成140元。拿非直推粉丝升级皇冠付费提成70元。",
                "每个直推级粉丝为升级皇冠获得140元，比皇冠多得70元。每个非直推粉丝为升级皇冠获得70元。",
                R.drawable.icon_crown_upgrade_privilege_4));
        mPrivileges.add(new PrivilegeBean("线下商家利润",
                "对接店拿所有用户消费分佣的比例15%。",
                "你对接一个线下商铺接入红人装线下联盟，所有红人装用户在商铺消费你均获得15%的分佣。",
                R.drawable.icon_crown_upgrade_privilege_5));
        mPrivilegesLL = getView().findViewById(R.id.ll_privileges);

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
    public void onVipUpgradeInfoLoaded(VipUpgradeInfo info) {
        if (info == null
                || !info.isSuccess()
                || info.getData() == null
                || info.getData().getLv() != 1
                || info.getData().getLv1() == null) {
            return;
        }

        mData = info;

        VipUpgradeInfo.CrownUpgradeInfo crownUpgradeInfo = info.getData().getLv1();
        int current = crownUpgradeInfo.getDone();
        int total = crownUpgradeInfo.getRequired();
        long lastUpgradeTime = crownUpgradeInfo.getLastUpTime() * 1000;

        mProgressPB.setMax(total);
        mProgressPB.setProgress(current);
        mUpgradeTitleTV.setText(String.format("累积培养%d个以上的皇冠才有升级资格", total));
        mCurrentTV.setText(current + "");
        mTotalTV.setText("/" + total);
        if (current >= total) {
            mTipTV.setText("恭喜获得升级店主资格");
        } else {
            mTipTV.setText(String.format("已培养皇冠%d个，距离升级还差%d个", current, total - current));
        }
        mGainMoreTV.setText(String.format("升级为店主, 多赚%.2f元", crownUpgradeInfo.getMoreProfit()));
        mLastUpgradeTimeTV.setText("上次升级时间:"+ new SimpleDateFormat("yyyy-MM-dd").format(new Date(lastUpgradeTime)));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick(R2.id.tv_buy)
    public void onBuyClick(View view) {

        if (mData != null) {
            int current = mData.getData().getLv1().getDone();
            int total = mData.getData().getLv1().getRequired();
            if (current >= total) {
                // 正在加载中
                if (mLoadingDialog != null) {
                    return;
                }

                mLoadingDialog = new DialogUtils(getActivity(), R.layout.common_loading_toast, "");
                mLoadingDialog.show();

                loadSelfRunGoodsInfo(2);
            } else {
                MessageOnlyDialog dialog = new MessageOnlyDialog(getActivity());
                dialog.setMessage(String.format("需要培养%d个皇冠才可以升级店主", total));
                dialog.show();
            }
        }
    }

    private void loadSelfRunGoodsInfo(int type) {
        loadSelfRunGoodsInfoFromNet(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<SelfRunGoodsBean>(BaseApplication.getApplication().getAppComponent().rxErrorHandler()) {
                    @Override
                    public void onNext(SelfRunGoodsBean mData) {
                        Log.i(TAG, "loadDefaultShippingAddress >> selfRunGoodsBean = " + mData);
                        if (mData != null && mData.getData() != null && mData.getData().getSkus() != null
                                && mData.getData().getSkus().size() > 0) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("skuId", "" + mData.getData().getSkus().get(0).getSkuId());
                            map.put("number", "1");
                            map.put("outTradeCode", System.currentTimeMillis() + "");
                            PayHelper.getDefault(getActivity()).generatorOrder(getActivity(), new PayHelper.GenerateOrderCallback() {
                                @Override
                                public void onGenerateCompleted(OrderResultBean data) {
                                    if (mLoadingDialog != null) {
                                        mLoadingDialog.hide();
                                        mLoadingDialog = null;
                                    }
                                }

                                @Override
                                public void onGenerateFailed(Throwable error) {
                                    if (mLoadingDialog != null) {
                                        mLoadingDialog.hide();
                                        mLoadingDialog = null;
                                    }
                                }
                            }, map);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (mLoadingDialog != null) {
                            mLoadingDialog.hide();
                            mLoadingDialog = null;
                        }
                    }
                });
    }

    private Observable<SelfRunGoodsBean> loadSelfRunGoodsInfoFromNet(int type) {
        RetrofitUrlManager.getInstance().putDomain("self_run_goods", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);

        HashMap<String, String> specParamMap = new HashMap<>();
        specParamMap.put("type", type + "");
        HashMap<String, String> params = HttpParamUtil.getCommonSignParamMap(BaseApplication.getApplication(), specParamMap);
        return BaseApplication.getApplication().getAppComponent().repositoryManager()
                .obtainRetrofitService(SelfRunGoodsCommonService.class)
                .loadSelfRunGoodsInfo(params);
    }
}
