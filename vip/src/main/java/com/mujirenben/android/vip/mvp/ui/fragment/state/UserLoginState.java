package com.mujirenben.android.vip.mvp.ui.fragment.state;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.gcssloop.widget.RCImageView;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.arounter.HrzRouter;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.widget.progress.CircleProgress;
import com.mujirenben.android.common.widget.switcher.AdvTextSwitcher;
import com.mujirenben.android.common.widget.switcher.Switcher;
import com.mujirenben.android.vip.R;
import com.mujirenben.android.vip.mvp.model.bean.FanHeaderInfo;
import com.mujirenben.android.vip.mvp.model.bean.NoticeBean;
import com.mujirenben.android.vip.mvp.model.bean.VipBannerBean;

import java.util.List;

/**
 * @author: panrongfu
 * @date: 2018/7/17 9:50
 * @describe: 用户已登录状态
 */
public class UserLoginState implements UserState {

    private ImageView vipHeaderLoginBg;
    private RCImageView vipHeaderLoginAvatarIv;
    private TextView vipHeaderLoginLevelTv;
    private TextView vipHeaderLoginNameTv;
    private TextView vipHeaderLoginNormalFanNumTv;
    private TextView vipHeaderLoginNormalFanTextTv;
    private LinearLayout vipHeaderLoginNormalFanLl;
    private TextView vipHeaderLoginCrownFanNumTv;
    private TextView vipHeaderLoginCrownFanTextTv;
    private LinearLayout vipHeaderLoginCrownFanLl;
    private TextView vipHeaderLoginShopFanNumTv;
    private TextView vipHeaderLoginShopFanTextTv;
    private LinearLayout vipHeaderLoginShopFanLl;
    private AdvTextSwitcher vipHeaderLoginSwitcher;
    private LinearLayout vipHeaderLoginIncomeLl;
    private FrameLayout vipHeaderLoginIncomeBorderFl;
    private TextView vipHeaderLoginNextMakeIncomeTextTv;
    private TextView vipHeaderLoginNextMakeIncomeNumTv;
    private TextView vipHeaderLoginMakeIncomeTextTv;
    private TextView vipHeaderLoginMakeIncomeNumTv;
    private TextView vipHeaderLoginGotoUpdateTv;
    private FrameLayout vipHeaderLoginLl;
    private ImageView vipHeaderLoginQrcode;
    private ImageView vipHeaderLoginLevelOneItemIv;
    private TextView vipHeaderLoginLevelOneItemTv;
    private ImageView vipHeaderLoginLevelTwoItemIv;
    private TextView vipHeaderLoginLevelTwoItemTv;
    private ImageView vipHeaderLoginLevelThreeItemIv;
    private TextView vipHeaderLoginLevelThreeItemTv;
    private RCImageView vipHeaderLoginSchoolAvatarIv;
    private TextView vipHeaderLoginSchoolWelcomeTv;
    private TextView vipHeaderLoginSchoolClassTv;
    private TextView vipHeaderLoginSchoolTrophyTv;
    private TextView vipHeaderLoginSchoolNeedTv;
    private CircleProgress vipHeaderLoginSchoolCircleProgress;
    private View vipHeaderShopExpiredRl;
    private View vipHeaderExpiredNoticeCloseIv;
    private ImageView vipHeaderLoginUpdateLayoutIv;
    //拉新活动
    private View vipHeaderPullNewActivityIv;
    /**
     * 会员已登录页面
     */
    private View vipLoginLayout;
    private Context mContext;
    private Switcher switcher;
    private static final int FAN_LEVEL = 0;
    private static final int CROWN_LEVEL = 1;
    private static final int SHOP_LEVEL = 2;
    private int upgradeType = 0;

    public UserLoginState(Context context, View vipLoginLayout) {
        this.mContext = context;
        this.vipLoginLayout = vipLoginLayout;
        initViewUI();
    }

    /**
     * 初始化UI
     */
    private void initViewUI() {
        switcher = new Switcher();
        vipLoginLayout.setVisibility(View.VISIBLE);
        vipHeaderShopExpiredRl = vipLoginLayout.findViewById(R.id.vip_header_shop_expired_rl);
        vipHeaderExpiredNoticeCloseIv = vipLoginLayout.findViewById(R.id.vip_header_expired_notice_close_iv);
        vipHeaderLoginBg = vipLoginLayout.findViewById(R.id.vip_header_login_bg);
        vipHeaderLoginAvatarIv = vipLoginLayout.findViewById(R.id.vip_header_login_avatar_iv);
        vipHeaderLoginLevelTv = vipLoginLayout.findViewById(R.id.vip_header_login_level_tv);
        vipHeaderLoginNameTv = vipLoginLayout.findViewById(R.id.vip_header_login_name_tv);

        //粉丝栏
        vipHeaderLoginNormalFanNumTv = vipLoginLayout.findViewById(R.id.vip_header_login_normal_fan_num_tv);
        vipHeaderLoginNormalFanTextTv = vipLoginLayout.findViewById(R.id.vip_header_login_normal_fan_text_tv);
        vipHeaderLoginNormalFanLl = vipLoginLayout.findViewById(R.id.vip_header_login_normal_fan_ll);
        vipHeaderLoginCrownFanNumTv = vipLoginLayout.findViewById(R.id.vip_header_login_crown_fan_num_tv);
        vipHeaderLoginCrownFanTextTv = vipLoginLayout.findViewById(R.id.vip_header_login_crown_fan_text_tv);
        vipHeaderLoginCrownFanLl = vipLoginLayout.findViewById(R.id.vip_header_login_crown_fan_ll);
        vipHeaderLoginShopFanNumTv = vipLoginLayout.findViewById(R.id.vip_header_login_shop_fan_num_tv);
        vipHeaderLoginShopFanTextTv = vipLoginLayout.findViewById(R.id.vip_header_login_shop_fan_text_tv);
        vipHeaderLoginShopFanLl = vipLoginLayout.findViewById(R.id.vip_header_login_shop_fan_ll);

        //粉丝级别
        vipHeaderLoginLevelOneItemIv = vipLoginLayout.findViewById(R.id.vip_header_login_level_one_item_iv);
        vipHeaderLoginLevelOneItemTv = vipLoginLayout.findViewById(R.id.vip_header_login_level_one_item_tv);
        vipHeaderLoginLevelTwoItemIv = vipLoginLayout.findViewById(R.id.vip_header_login_level_two_item_iv);
        vipHeaderLoginLevelTwoItemTv = vipLoginLayout.findViewById(R.id.vip_header_login_level_two_item_tv);
        vipHeaderLoginLevelThreeItemIv = vipLoginLayout.findViewById(R.id.vip_header_login_level_three_item_iv);
        vipHeaderLoginLevelThreeItemTv = vipLoginLayout.findViewById(R.id.vip_header_login_level_three_item_tv);

        //收益栏
        vipHeaderLoginIncomeLl = vipLoginLayout.findViewById(R.id.vip_header_login_income_ll);
        vipHeaderLoginIncomeBorderFl = vipLoginLayout.findViewById(R.id.vip_header_login_income_border_fl);
        vipHeaderLoginMakeIncomeTextTv = vipLoginLayout.findViewById(R.id.vip_header_login_make_income_text_tv);
        vipHeaderLoginMakeIncomeNumTv = vipLoginLayout.findViewById(R.id.vip_header_login_make_income_num_tv);
        vipHeaderLoginNextMakeIncomeTextTv = vipLoginLayout.findViewById(R.id.vip_header_login_next_make_income_text_tv);
        vipHeaderLoginNextMakeIncomeNumTv = vipLoginLayout.findViewById(R.id.vip_header_login_next_make_income_num_tv);

        vipHeaderLoginSwitcher = vipLoginLayout.findViewById(R.id.vip_header_login_switcher);
        vipHeaderLoginGotoUpdateTv = vipLoginLayout.findViewById(R.id.vip_header_login_goto_update_tv);
        vipHeaderLoginQrcode = vipLoginLayout.findViewById(R.id.vip_header_login_qrcode);
        vipHeaderLoginUpdateLayoutIv = vipLoginLayout.findViewById(R.id.vip_header_login_update_layout_iv);

        vipHeaderLoginSchoolAvatarIv = vipLoginLayout.findViewById(R.id.vip_header_login_school_avatar_iv);
        vipHeaderLoginSchoolWelcomeTv = vipLoginLayout.findViewById(R.id.vip_header_login_school_welcome_tv);
        vipHeaderLoginSchoolClassTv = vipLoginLayout.findViewById(R.id.vip_header_login_school_class_tv);
        vipHeaderLoginSchoolTrophyTv = vipLoginLayout.findViewById(R.id.vip_header_login_school_trophy_tv);
        vipHeaderLoginSchoolNeedTv = vipLoginLayout.findViewById(R.id.vip_header_login_school_need_tv);
        vipHeaderLoginSchoolCircleProgress = vipLoginLayout.findViewById(R.id.vip_header_login_school_circleProgress);

        vipHeaderPullNewActivityIv = vipLoginLayout.findViewById(R.id.vip_header_pull_new_activity_iv);

    }

    /**
     * 展示未登录会员独有的信息
     * 1)粉丝栏
     * 2)公告栏
     * 3)等级栏
     * 4)分享赚栏
     * 5)升级赚更多栏
     * @param fanHeaderInfo vip信息对象
     */
    @Override
    public void showUniqueInfoLayout(FanHeaderInfo fanHeaderInfo) {
        // TODO: 2018/7/17 已登录会员页面展示
        Log.e("showUniqueInfoLayout", "UserLoginState");
        vipLoginLayout.setVisibility(View.VISIBLE);
        initDiffRoleBackground(fanHeaderInfo);
        intLevelLayout(fanHeaderInfo);
        initFanLayout(fanHeaderInfo);
        intSchoolLayout(fanHeaderInfo);
        initClickEvent(fanHeaderInfo);
        setUserInfo();
        initInComeData(fanHeaderInfo);
        expiredLevel(fanHeaderInfo);
    }

    private void expiredLevel(FanHeaderInfo fanHeaderInfo) {
        if(fanHeaderInfo != null && fanHeaderInfo.getData() != null){
            int expiredLv = fanHeaderInfo.getData().getExpiredLv();
            int currrentLv = fanHeaderInfo.getData().getLv();
            long currentTime = System.currentTimeMillis()/1000;
            long expiredTime = fanHeaderInfo.getData().getExpiredDay();
            if(currentTime > expiredTime && expiredLv == SHOP_LEVEL){
                vipHeaderShopExpiredRl.setVisibility(View.VISIBLE);
                vipHeaderLoginGotoUpdateTv.setText("点击续费店主");
                vipHeaderLoginGotoUpdateTv.setTag(0);
                vipHeaderShopExpiredRl.setOnClickListener(v ->
                        ARouter.getInstance().build(ARouterPaths.SHOP_KEEPER_RENEW_ACTIVITY).navigation(mContext));
            }else {
                vipHeaderShopExpiredRl.setVisibility(View.GONE);
                vipHeaderLoginGotoUpdateTv.setTag(1);
            }
        }
    }

    private void initInComeData(FanHeaderInfo fanHeaderInfo) {
        FanHeaderInfo.DataBean fd = null;
        String incomePercent = "";
        if(fanHeaderInfo != null){
            fd = fanHeaderInfo.getData();
            incomePercent  = fd.getIncomePercent();
        }
        vipHeaderLoginMakeIncomeNumTv.setText(fd == null?"--":fd.getMyIncome()+"");
        vipHeaderLoginNextMakeIncomeNumTv.setText(fd == null?"--":fd.getNextIncome()+"");
        String orgIncomePercent = mContext.getResources().getString(R.string.vip_header_login_goto_update_text);
        String newIncomePercent;
        if(TextUtils.isEmpty(incomePercent)){
            newIncomePercent = String.format(orgIncomePercent, "--%");
        }else {
            newIncomePercent = String.format(orgIncomePercent, incomePercent+"%");
        }
        vipHeaderLoginGotoUpdateTv.setText(newIncomePercent);

    }

    private void intSchoolLayout(FanHeaderInfo fanHeaderInfo) {
        int totalClass = 11;
        int currentClass  = 7;
        float everyClassOccupyAngle = 360/totalClass;
        float currentTotalOccupyAngle = currentClass*everyClassOccupyAngle;
        vipHeaderLoginSchoolCircleProgress.sweepAngle(currentTotalOccupyAngle);
        vipHeaderLoginSchoolCircleProgress.setProgressText(currentClass+"/"+totalClass);
    }

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
     * 设置不同角色的背景颜色
     *
     * @param fanHeaderInfo
     */
    @SuppressLint("ResourceAsColor")
    private void initDiffRoleBackground(FanHeaderInfo fanHeaderInfo) {
        FanHeaderInfo.DataBean fd = null;
        int roleType = 0;
        if(fanHeaderInfo != null && fanHeaderInfo.getData() != null){
            roleType = fanHeaderInfo.getData().getLv();
        }
        switch (roleType) {
            case 0:
                //粉丝栏
                int normalColor = Color.parseColor("#CCFFFFFF");
                vipHeaderLoginNormalFanNumTv.setTextColor(normalColor);
                vipHeaderLoginNormalFanTextTv.setTextColor(normalColor);
                vipHeaderLoginCrownFanNumTv.setTextColor(normalColor);
                vipHeaderLoginCrownFanTextTv.setTextColor(normalColor);
                vipHeaderLoginShopFanNumTv.setTextColor(normalColor);
                vipHeaderLoginShopFanTextTv.setTextColor(normalColor);
                //二维码
                vipHeaderLoginQrcode.setImageResource(R.drawable.vip_header_normal_qrcode);
                //整个头部背景
                vipHeaderLoginBg.setImageResource(R.drawable.vip_header_login_normal_bg);
                //公告栏字体颜色
                vipHeaderLoginSwitcher.setSwitcherTextColor(Color.parseColor("#9FA5BE"));
                //公告栏背景
                vipHeaderLoginSwitcher.setBackgroundResource(R.drawable.normal_fan_announce_bg);
                //收入栏背景
                vipHeaderLoginIncomeLl.setBackgroundResource(R.drawable.vip_header_normal_income_bg);
                //收入栏背景边框
                vipHeaderLoginIncomeBorderFl.setBackgroundResource(R.drawable.vip_header_normal_border_bg);
                //预计收益文字颜色设置为白色
                vipHeaderLoginNextMakeIncomeTextTv.setTextColor(Color.WHITE);
                //预计收益数字的颜色
                vipHeaderLoginNextMakeIncomeNumTv.setTextColor(Color.WHITE);
                break;
            //皇冠以上
            default:
                //粉丝栏
                int crownColor = Color.WHITE;
                vipHeaderLoginNormalFanNumTv.setTextColor(crownColor);
                vipHeaderLoginNormalFanTextTv.setTextColor(crownColor);
                vipHeaderLoginCrownFanNumTv.setTextColor(crownColor);
                vipHeaderLoginCrownFanTextTv.setTextColor(crownColor);
                vipHeaderLoginShopFanNumTv.setTextColor(crownColor);
                vipHeaderLoginShopFanTextTv.setTextColor(crownColor);
                //二维码
                vipHeaderLoginQrcode.setImageResource(R.drawable.vip_header_crown_qrcode);
                //整个头部背景
                vipHeaderLoginBg.setImageResource(R.drawable.vip_header_login_crown_bg);
                //公告栏字体颜色
                vipHeaderLoginSwitcher.setSwitcherTextColor(Color.parseColor("#FF333333"));
                //公告栏背景颜色
                vipHeaderLoginSwitcher.setBackgroundResource(R.drawable.crown_fan_announce_bg);
                //收入栏背景
                vipHeaderLoginIncomeLl.setBackgroundResource(R.drawable.vip_header_crown_income_bg);
                //收入栏背景边框
                vipHeaderLoginIncomeBorderFl.setBackgroundResource(R.drawable.vip_header_crown_border_bg);
                int goldenColor = mContext.getResources().getColor(R.color.golden_light_color);
                //预计收益文字颜色设置为白色
                vipHeaderLoginNextMakeIncomeTextTv.setTextColor(goldenColor);
                //预计收益数字的颜色
                vipHeaderLoginNextMakeIncomeNumTv.setTextColor(goldenColor);
                break;
        }
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
    }

    /**
     * 跳转到我的粉丝页面
     */
    private void gotoFanActivity() {
        ARouter.getInstance()
                .build(ARouterPaths.ACTIVITY_FAN)
                .navigation(mContext);
    }

    /**
     * 公告栏
     * @param noticeList
     */
    @Override
    public void intAnnounceLayout(List<NoticeBean.DataBean> noticeList) {
        if(noticeList != null && noticeList.size() > 0){
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
            vipHeaderLoginSwitcher.setOnTipClickListener(position -> {});
        }
    }

    @Override
    public void showBannerLayout(VipBannerBean bannerBean) {

    }

    /**
     * 等级栏
     * 根据当前用户的等级动态改变布局
     * @param fanHeaderInfo
     */
    private void intLevelLayout(FanHeaderInfo fanHeaderInfo) {
        int roleType = 0;
        if(fanHeaderInfo != null && fanHeaderInfo.getData() != null){
            roleType = fanHeaderInfo.getData().getLv();
        }
        int bigHeight = ArmsUtils.dip2px(mContext, 77);
        int bigWidth = ArmsUtils.dip2px(mContext, 49);
        int normalHeight = ArmsUtils.dip2px(mContext, 45);
        int normalWidth = ArmsUtils.dip2px(mContext, 29);

        vipHeaderLoginLevelOneItemIv.setImageResource(R.drawable.icon_fensi);
        vipHeaderLoginLevelOneItemIv.getLayoutParams().height = normalHeight;
        vipHeaderLoginLevelOneItemIv.getLayoutParams().width = normalWidth;
        vipHeaderLoginLevelOneItemTv.setText("粉丝");

        vipHeaderLoginLevelTwoItemIv.setImageResource(R.drawable.icon_huangguan);
        vipHeaderLoginLevelTwoItemIv.getLayoutParams().height = normalHeight;
        vipHeaderLoginLevelTwoItemIv.getLayoutParams().width = normalWidth;
        vipHeaderLoginLevelTwoItemTv.setText("皇冠");

        vipHeaderLoginLevelThreeItemIv.setImageResource(R.drawable.icon_shop_star);
        vipHeaderLoginLevelThreeItemIv.getLayoutParams().height = normalHeight;
        vipHeaderLoginLevelThreeItemIv.getLayoutParams().width = normalWidth;
        vipHeaderLoginLevelThreeItemTv.setText("店主");

        switch (roleType) {
            case FAN_LEVEL:
                vipHeaderLoginLevelTv.setText("粉丝");
                vipHeaderLoginLevelOneItemIv.getLayoutParams().height = bigHeight;
                vipHeaderLoginLevelOneItemIv.getLayoutParams().width = bigWidth;
                vipHeaderLoginLevelOneItemIv.setImageResource(R.drawable.icon_fensi);
                vipHeaderLoginLevelOneItemTv.setText("");
                vipHeaderLoginUpdateLayoutIv.setImageResource(R.drawable.make_income_fan_bg);
                break;
            case CROWN_LEVEL:
                vipHeaderLoginLevelTv.setText("皇冠");
                vipHeaderLoginLevelTwoItemIv.setImageResource(R.drawable.icon_huangguan);
                vipHeaderLoginLevelTwoItemIv.getLayoutParams().height = bigHeight;
                vipHeaderLoginLevelTwoItemIv.getLayoutParams().width = bigWidth;
                vipHeaderLoginLevelTwoItemTv.setText("");
                vipHeaderLoginUpdateLayoutIv.setImageResource(R.drawable.make_incom_crown_bg);
                break;
        }
    }

    /**
     * 点击事件处理
     * @param fanHeaderInfo
     */
    private void initClickEvent(FanHeaderInfo fanHeaderInfo) {
        if(fanHeaderInfo != null && fanHeaderInfo.getData() != null){
            upgradeType = fanHeaderInfo.getData().getLv();
        }
        vipHeaderExpiredNoticeCloseIv.setOnClickListener(v ->
                vipHeaderShopExpiredRl.setVisibility(View.GONE));
        
        //点击二维码跳转
        vipHeaderLoginQrcode.setOnClickListener(view -> 
                ARouter.getInstance()
                        .build(ARouterPaths.VIP_QR_CODE_ACTIVITY)
                        .withString("source", "会员")
                        .navigation(mContext));
        vipHeaderLoginGotoUpdateTv.setOnClickListener(v ->{
            if(fanHeaderInfo != null && fanHeaderInfo.getData() != null){
                int expiredLv = fanHeaderInfo.getData().getExpiredLv();
                judgeGoToActivity(expiredLv);
            }
        });

        vipHeaderLoginUpdateLayoutIv.setOnClickListener(v ->{
                if(fanHeaderInfo != null && fanHeaderInfo.getData() != null){
                    int expiredLv = fanHeaderInfo.getData().getExpiredLv();
                    judgeGoToActivity(expiredLv);
                }
            });
        //
        vipHeaderPullNewActivityIv.setOnClickListener(v -> {
            //String clickURL = Consts.VIP_PULL_NEW_URL+LoginDataManager.getsInstance(mContext).getWeixinUnionId();
            HrzRouter.getsInstance(mContext).navigation(Consts.VIP_PULL_NEW_URL);
        });
    }

    /**
     * 判断是走店主权益 还是升级页面
     * 1、如果过期的级别是店主，则需要跳转到店主权益
     * 2、如果过期的级别不是店主，则跳转到对应级别的升级页面
     * @param expiredLv
     */
    private void judgeGoToActivity(int expiredLv){

        if(expiredLv == SHOP_LEVEL){
            ARouter.getInstance()
                    .build(ARouterPaths.SHOP_KEEPER_RENEW_ACTIVITY)
                    .navigation(mContext);
        }else {
            ARouter.getInstance()
                    .build(ARouterPaths.VIP_UPGRADE_ACTIVITY)
                    .withInt("upgrade_type", upgradeType)
                    .navigation(mContext);
        }
    }

    @Override
    public void onHiddenChanged(boolean loginViewHidden, boolean unLoginViewHidden) {
        if (loginViewHidden) {
            if (switcher != null) {
                switcher.pause();
            }
        }
    }
}
