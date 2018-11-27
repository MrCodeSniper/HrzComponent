package com.mujirenben.android.vip.mvp.ui.fragment.state;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.gcssloop.widget.RCImageView;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.arounter.HrzRouter;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.DateTimeUtil;
import com.mujirenben.android.common.util.rxtools.RxTextTool;
import com.mujirenben.android.common.widget.progress.CircleProgress;
import com.mujirenben.android.common.widget.switcher.AdvTextSwitcher;
import com.mujirenben.android.common.widget.switcher.Switcher;

import com.mujirenben.android.vip.R;
import com.mujirenben.android.vip.mvp.model.bean.FanHeaderInfo;
import com.mujirenben.android.vip.mvp.model.bean.NoticeBean;
import com.mujirenben.android.vip.mvp.model.bean.VipBannerBean;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * @author: panrongfu
 * @date: 2018/8/13 19:06
 * @describe: 店主状态
 */

public class ShopLoginState implements UserState{


    private RCImageView vipHeaderLoginAvatarIv;
    private TextView vipHeaderLoginNameTv;
    private ImageView vipHeaderLoginQrcode;
    private TextView vipHeaderLoginLevelTv;
    private LinearLayout vipHeaderLoginNormalFanLl;
    private TextView vipHeaderLoginNormalFanNumTv;
    private TextView vipHeaderLoginNormalFanTextTv;
    private LinearLayout vipHeaderLoginCrownFanLl;
    private TextView vipHeaderLoginCrownFanNumTv;
    private TextView vipHeaderLoginCrownFanTextTv;
    private LinearLayout vipHeaderLoginShopFanLl;
    private TextView vipHeaderLoginShopFanNumTv;
    private TextView vipHeaderLoginShopFanTextTv;
    private AdvTextSwitcher vipHeaderLoginSwitcher;
    private ImageView vipHeaderLoginUpdateLayoutIv;
    private TextView vipHeaderLoginShopInvalidTv;
    private TextView vipHeaderLoginShopExclusiveTv;
    private TextView vipHeaderLoginShopRetrievalTv;
    private CircleProgress vipHeaderLoginSchoolCircleProgress;
    private Switcher switcher;
    private View vipShopLoginLayout;
    private Context mContext;
    private View vipHeaderPullNewActivityIv;

    public ShopLoginState(Context context, View vipLoginLayout) {
        this.mContext = context;
        this.vipShopLoginLayout = vipLoginLayout;
        initViewUI();
    }

    private void initViewUI() {
        switcher = new Switcher();
        vipHeaderLoginAvatarIv = vipShopLoginLayout.findViewById(R.id.vip_header_login_avatar_iv);
        vipHeaderLoginNameTv = vipShopLoginLayout.findViewById(R.id.vip_header_login_name_tv);
        vipHeaderLoginQrcode =  vipShopLoginLayout.findViewById(R.id.vip_header_login_qrcode);
        vipHeaderLoginLevelTv = vipShopLoginLayout.findViewById(R.id.vip_header_login_level_tv);
        vipHeaderLoginNormalFanLl =  vipShopLoginLayout.findViewById(R.id.vip_header_login_normal_fan_ll);
        vipHeaderLoginNormalFanNumTv = vipShopLoginLayout.findViewById(R.id.vip_header_login_normal_fan_num_tv);
        vipHeaderLoginNormalFanTextTv =  vipShopLoginLayout.findViewById(R.id.vip_header_login_normal_fan_text_tv);
        vipHeaderLoginCrownFanLl = vipShopLoginLayout.findViewById(R.id.vip_header_login_crown_fan_ll);
        vipHeaderLoginCrownFanNumTv =  vipShopLoginLayout.findViewById(R.id.vip_header_login_crown_fan_num_tv);
        vipHeaderLoginCrownFanTextTv =  vipShopLoginLayout.findViewById(R.id.vip_header_login_crown_fan_text_tv);
        vipHeaderLoginShopFanLl =  vipShopLoginLayout.findViewById(R.id.vip_header_login_shop_fan_ll);
        vipHeaderLoginShopFanNumTv =  vipShopLoginLayout.findViewById(R.id.vip_header_login_shop_fan_num_tv);
        vipHeaderLoginShopFanTextTv =  vipShopLoginLayout.findViewById(R.id.vip_header_login_shop_fan_text_tv);
        vipHeaderLoginSwitcher =  vipShopLoginLayout.findViewById(R.id.vip_header_login_switcher);
        vipHeaderLoginUpdateLayoutIv =  vipShopLoginLayout.findViewById(R.id.vip_header_login_update_layout_iv);
        vipHeaderLoginShopInvalidTv =  vipShopLoginLayout.findViewById(R.id.vip_header_login_shop_invalid_tv);
        vipHeaderLoginShopExclusiveTv =  vipShopLoginLayout.findViewById(R.id.vip_header_login_shop_exclusive_tv);
        vipHeaderLoginShopRetrievalTv =  vipShopLoginLayout.findViewById(R.id.vip_header_login_shop_retrieval_tv);
        vipHeaderLoginSchoolCircleProgress = vipShopLoginLayout.findViewById(R.id.vip_header_login_school_circleProgress);

        vipHeaderPullNewActivityIv = vipShopLoginLayout.findViewById(R.id.vip_header_pull_new_activity_iv);
    }

    @Override
    public void showUniqueInfoLayout(FanHeaderInfo fanHeaderInfo) {
        initFanLayout(fanHeaderInfo);
        initClickEvent();
        setUserInfo();
        initSchoolLayout(fanHeaderInfo);
    }

    @Override
    public void onHiddenChanged(boolean loginViewHidden, boolean unLoginViewHidden) {

    }

    /**
     * 点击事件处理
     */
    private void initClickEvent() {

        //点击二维码跳转
        vipHeaderLoginQrcode.setOnClickListener(view ->
                ARouter.getInstance()
                        .build(ARouterPaths.VIP_QR_CODE_ACTIVITY)
                        .withString("source", "会员")
                        .navigation(mContext));
        LoginDataManager loginDataManager = LoginDataManager.getsInstance(mContext);
        String expirationStr ="您的店主身份将于";
        long expirationTime = loginDataManager.getShopKeeperExpirationTimeMillis();
        long currentTime = System.currentTimeMillis();
        if(currentTime > expirationTime){
            expirationStr = "您的店主身份已于";
        }
        String dateTime = DateTimeUtil.parseTimestamp(expirationTime,DateTimeUtil.yyyyYearMMMonthddDay);
        vipHeaderLoginShopInvalidTv.setMovementMethod(LinkMovementMethod.getInstance());
        RxTextTool.getBuilder(expirationStr,mContext)
                .append(dateTime)
                .append("过期，")
                .append("点击续费")
                .setUnderline()
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        ARouter.getInstance().build(ARouterPaths.SHOP_KEEPER_RENEW_ACTIVITY).navigation(mContext);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(mContext.getResources().getColor(R.color.golden_deep_color));
                    }
                })
                .into(vipHeaderLoginShopInvalidTv);

        vipHeaderLoginShopRetrievalTv.setOnClickListener(v -> {
            ARouter.getInstance()
                    .build(ARouterPaths.STOCKING_ACTIVITY)
                    .navigation(mContext);
        });

        vipHeaderPullNewActivityIv.setOnClickListener(v -> {
            String clickURL = Consts.VIP_PULL_NEW_URL;
            Logger.e(clickURL);
            HrzRouter.getsInstance(mContext).navigation(clickURL);
        });

    }

    /**
     * 设置用户信息
     */
    public void setUserInfo() {
        LoginDataManager loginDataManager = LoginDataManager.getsInstance(mContext);
        String displayName = loginDataManager.getDisplayName();
        String avatarUrl = loginDataManager.getAvatarUrl();

        if (!TextUtils.isEmpty(displayName)) {
            vipHeaderLoginNameTv.setText(displayName);
        }

        if (!TextUtils.isEmpty(avatarUrl)) {
            Glide.with(mContext).load(avatarUrl).into(vipHeaderLoginAvatarIv);
        }

    }

    /**
     * 公告栏
     * @param noticeList
     */
    @Override
    public void intAnnounceLayout(List<NoticeBean.DataBean> noticeList) {
        String[] tips = new String[noticeList.size()];
        for(int i = 0; i<noticeList.size(); i++){
            tips[i] = noticeList.get(i).getNotice();
        }
        vipHeaderLoginSwitcher.clearTips();
        vipHeaderLoginSwitcher.setTpis(tips);
        switcher.pause();
        switcher.setAdvTsView(vipHeaderLoginSwitcher);
        //注意这里的单位是秒！秒！秒！秒！秒！
        switcher.setDuration(5);
        switcher.start();
        //公告栏字体颜色
        vipHeaderLoginSwitcher.setSwitcherTextColor(Color.parseColor("#FF333333"));
        vipHeaderLoginSwitcher.setOnTipClickListener(position -> {});
    }

    /**
     * 粉丝栏
     * @param fanHeaderInfo
     */
    private void initFanLayout(FanHeaderInfo fanHeaderInfo) {
        FanHeaderInfo.DataBean fd = null;
        if(fanHeaderInfo != null){
            fd = fanHeaderInfo.getData();
        }
        vipHeaderLoginNormalFanNumTv.setText(fd == null? "--":fd.getLv0Count()+"");
        vipHeaderLoginCrownFanNumTv.setText(fd == null? "--":fd.getLv1Count()+"");
        vipHeaderLoginShopFanNumTv.setText(fd == null? "--":fd.getLv2Count()+"");
        vipHeaderLoginNormalFanLl.setOnClickListener(v -> gotoFanActivity());
        vipHeaderLoginCrownFanLl.setOnClickListener(v -> gotoFanActivity());
        vipHeaderLoginShopFanLl.setOnClickListener(v -> gotoFanActivity());

        RxTextTool.getBuilder("您的专区商品剩余",mContext)
                  .append(fd == null ? "--":fd.getSurplusCount()+"")
                  .setProportion(1.2f)
                  .append("件")
                  .into(vipHeaderLoginShopExclusiveTv);
    }

    /**
     * 跳转到我的粉丝页面
     */
    private void gotoFanActivity() {
        ARouter.getInstance()
                .build(ARouterPaths.ACTIVITY_FAN)
                .navigation(mContext);
    }

    private void initSchoolLayout(FanHeaderInfo fanHeaderInfo) {
        int totalClass = 11;
        int currentClass  = 7;
        float everyClassOccupyAngle = 360/totalClass;
        float currentTotalOccupyAngle = currentClass*everyClassOccupyAngle;
        vipHeaderLoginSchoolCircleProgress.sweepAngle(currentTotalOccupyAngle);
        vipHeaderLoginSchoolCircleProgress.setProgressText(currentClass+"/"+totalClass);
    }

    @Override
    public void showBannerLayout(VipBannerBean bannerBean) {

    }
}
