package com.mujirenben.android.vip.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.bean.SelfRunGoodsBean;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.pay.OrderResultBean;
import com.mujirenben.android.common.pay.PayHelper;
import com.mujirenben.android.common.pay.SelfRunGoodsCommonService;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.widget.DialogUtils;
import com.mujirenben.android.common.widget.dialog.MessageOnlyDialog;
import com.mujirenben.android.vip.R;
import com.mujirenben.android.vip.R2;
import com.mujirenben.android.vip.mvp.model.bean.PrivilegeBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@Route(path = ARouterPaths.SHOP_KEEPER_RENEW_ACTIVITY)
public class ShopKeeperRenewActivity extends BaseActivity {

    private LinearLayout mPrivilegesLL;

    private List<PrivilegeBean> mPrivileges = new ArrayList<>();

    private long mExpirationTime;

    @BindView(R2.id.tv_next_expiration_date)
    TextView mNextExpirationDate;

    @BindView(R2.id.tv_expiration_date)
    TextView mExpirationDate;

    private DialogUtils mLoadingDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
//        StatusBarUtil.setStatusBarWhite(this);
        if (SpUtil.getIsMIUI(this)){
            StatusBarUtil.getStatusBarHelper(this,R.color.white).into(this);
        }else {
            StatusBarUtil.setStatusBarWhite(this);
        }
        return R.layout.activity_shop_keeper_renew;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        LoginDataManager ldm = LoginDataManager.getsInstance(this);
        mExpirationTime = getIntent().getLongExtra("shopkeeper_expiration", ldm.getShopKeeperExpirationTimeMillis());
        handleExpirationTime();

        ((TextView)findViewById(R.id.tv_title)).setText("店主权益");
        findViewById(R.id.ib_left_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.ll_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 正在加载中
                if (mLoadingDialog != null) {
                    return;
                }

                mLoadingDialog = new DialogUtils(ShopKeeperRenewActivity.this, R.layout.common_loading_toast, "");
                mLoadingDialog.show();

                loadSelfRunGoodsInfo(2);
            }
        });

        LoginDataManager loginDataManager = LoginDataManager.getsInstance(this);
        ImageView vipIconIV = findViewById(R.id.iv_vip_icon);
        String avatarUrl = loginDataManager.getAvatarUrl();
        if (!TextUtils.isEmpty(avatarUrl)) {
            Glide.with(this).load(avatarUrl).into(vipIconIV);
        }

        TextView currentLevelTV = findViewById(R.id.tv_current_level);
        currentLevelTV.setText("当前的身份: " + ldm.getLevelString());

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
        mPrivilegesLL = findViewById(R.id.ll_privileges);

        addPrivileges();

        /**
         * 在这还需要做更详细的对比
         * 1）如果当前的店主身份已过期，则显示的时间=当前的时间 + 一年时间
         * 2）如果当前的店主身份没有过去，则显示的时间 = 店主续费到期时间 + 一年时间
         */
        long currentTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        if(currentTime > mExpirationTime){
            calendar.setTime(new Date(currentTime));
        }else {
            calendar.setTime(new Date(mExpirationTime));
        }
        calendar.add(Calendar.YEAR,1);
        mNextExpirationDate.setText(String.format("本次续费后%d年%d月%d日到期",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE)));
    }

    private void handleExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(mExpirationTime));
        long currentTime = System.currentTimeMillis();
        String flag = "将";
        if(currentTime > mExpirationTime){
            flag = "已";
        }
        mExpirationDate.setText(String.format("您的店主身份%s于%d年%d月%d日过期",
                flag,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DATE)));
    }

    private void addPrivileges() {
        for (PrivilegeBean privilege : mPrivileges) {
            View view = LayoutInflater.from(this).inflate(R.layout.include_privilege_item, null);
            TextView titleTV = view.findViewById(R.id.tv_title);
            TextView subtitleTV = view.findViewById(R.id.tv_sub_title);
            ImageView iconIV = view.findViewById(R.id.iv_icon);

            titleTV.setText(privilege.title);
            subtitleTV.setText(privilege.subtitle);
            iconIV.setImageResource(privilege.iconId);

            view.findViewById(R.id.ll_demo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageOnlyDialog dialog = new MessageOnlyDialog(ShopKeeperRenewActivity.this);
                    dialog.setMessage(privilege.demo);
                    dialog.show();
                }
            });

            mPrivilegesLL.addView(view);
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
                            PayHelper.getDefault(ShopKeeperRenewActivity.this).generatorOrder(ShopKeeperRenewActivity.this, new PayHelper.GenerateOrderCallback() {
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
