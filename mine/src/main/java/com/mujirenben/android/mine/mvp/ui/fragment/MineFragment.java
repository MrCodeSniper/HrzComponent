package com.mujirenben.android.mine.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gcssloop.widget.RCImageView;
import com.hrz.poplayer.HrzLayerView;
import com.hrz.poplayer.config.LayerConfig;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.arounter.HrzRouter;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.base.BaseFragment;
import com.mujirenben.android.common.base.greendao.City;
import com.mujirenben.android.common.base.greendao.Province;
import com.mujirenben.android.common.base.greendao.UserBean;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.event.KeFuLoginEvent;
import com.mujirenben.android.common.event.LoginStatusEvent;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.PermissionUtil;
import com.mujirenben.android.common.util.PlatformUtils;
import com.mujirenben.android.common.util.rxtools.RxCaptcha;
import com.mujirenben.android.common.widget.HrzHeadersView;
import com.mujirenben.android.common.widget.StatusBarView;
import com.mujirenben.android.mine.di.component.DaggerMineComponent;
import com.mujirenben.android.mine.di.module.MineModule;
import com.mujirenben.android.mine.login.LoginUtil;
import com.mujirenben.android.mine.mvp.contract.MineContract;
import com.mujirenben.android.mine.mvp.model.InviteCodeBean;
import com.mujirenben.android.mine.mvp.model.bean.FuncOption;
import com.mujirenben.android.mine.mvp.model.bean.MineConstant;
import com.mujirenben.android.mine.mvp.model.bean.UserInfo;
import com.mujirenben.android.mine.mvp.model.bean.UserRewardInfo;
import com.mujirenben.android.mine.mvp.presenter.MinePresenter;
import com.mujirenben.android.mine.mvp.ui.activity.UserInfoActivity;
import com.mujirenben.android.mine.mvp.ui.adapter.MineViewAdapter;
import com.mujirenben.android.mine.mvp.ui.adapter.OptionAdapter;
import com.mujirenben.android.mine.mvp.ui.constant.MineConstants;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

import static com.hrz.poplayer.HrzLayerView.STATE_DIALOG;
import static com.hrz.poplayer.HrzLayerView.STATE_WEBVIEW;
import static com.mujirenben.android.common.util.rxtools.RxCaptcha.TYPE.CHARS;

import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;
/**
 * 登录前 不需要预加载 如果有登录需要预加载 收益数据
 */

public class MineFragment extends BaseFragment<MinePresenter> implements View.OnClickListener,PtrHandler,MineContract.View,BaseQuickAdapter.OnItemClickListener {

    private TextView mineMainSettingTv;
    private RelativeLayout flUserInfo;
    private RCImageView ivUserIcon;
    private TextView tvUserRank;
    private TextView tvUserName;
    private ImageView ivArrowRight;
    private ImageView ivUserQrcode;
    private RelativeLayout rlVipLevelUp;
    private RelativeLayout rlHrzAcademy;
    private RelativeLayout rlReward;
    private LinearLayout llHideOrShow;
    private ImageView ivEyes;
    private TextView tvHideOrShowReward;
    private TextView tvAccumulateRewardAmount;
    private TextView tvRewardIntoAccountToday;
    private TextView tvWithdrawGoodsAmount;
    private RelativeLayout rlPredictRewardAmount;
    private TextView tvPredictRewardAmount;
    private RelativeLayout rlWithdraw;
    private TextView tvWithdrawAmount;
    private LinearLayout llAllDeals;
    private LinearLayout llDealSuccess;
    private LinearLayout llDealClose;
    private LinearLayout llDealInvalid;
    private RecyclerView rvOtherOptions;
    private Unbinder unbinder;
    private int[] option_resource_id={R.drawable.invite_code_icon,R.drawable.mine_activity_icon,R.drawable.mine_fragment_icon_docking,R.drawable.icon_ruchang,R.drawable.mine_common_qa,R.drawable.mine_contact_kefu,R.drawable.mine_contact_us};
    private String[] option_tag;

    private OptionAdapter adapter;

    private RecyclerView rv_mine_userinfo;
    private HrzHeadersView hrzHeadersView;
    private View headerLayout;
    private HrzLayerView hrzLayerView;
    private HrzLayerView dialog;
    private StatusBarView mStatusBarView;

    private static int INCOME_STATE= MineConstants.INCOME_SUCCESS;

    private String accumulateReward= MineConstants.NO_INCOME_DATA;
    private String todayIntoAccount=MineConstants.NO_INCOME_DATA;
    private String returnAmount=MineConstants.NO_INCOME_DATA;
    private String predictReward=MineConstants.NO_INCOME_DATA;
    private String withdrawAmount=MineConstants.NO_INCOME_DATA;

    private static boolean isLoadFirst=false;

    private String code;

    @Inject
    RxPermissions rxPermissions;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mineModule(new MineModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return prepareView(inflater,container);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        rv_mine_userinfo.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        MineViewAdapter mineViewAdapter=new MineViewAdapter(R.layout.mine_option_item_view);
        mineViewAdapter.addHeaderView(headerLayout);
        rv_mine_userinfo.setAdapter(mineViewAdapter);

        assignViews(headerLayout);
        initOptionView();
        initOnClickEvent();

        if(LoginDataManager.getsInstance(getActivity()).isLogin()){
            showLoginUi();
        }else {
            showNoLoginUi();
        }

        hrzHeadersView.setPtrHandler(this);
    }

    private View prepareView(LayoutInflater inflater,ViewGroup container){
        View view=inflater.inflate(R.layout.mine_userinfo_fragment, container, false);
        headerLayout=LayoutInflater.from(getActivity()).inflate(R.layout.mine_fragment_view,null);
        rv_mine_userinfo=view.findViewById(R.id.rv_mine_userinfo);
        hrzHeadersView=view.findViewById(R.id.hrz_header_view);
        unbinder=ButterKnife.bind(view);
        return view;
    }

    private void assignViews(View view) {
        mStatusBarView = view.findViewById(R.id.status_bar_view);
        mineMainSettingTv = view.findViewById(R.id.mine_main_setting_tv);
        flUserInfo = view.findViewById(R.id.fl_user_info);
        ivUserIcon = view.findViewById(R.id.iv_user_icon);
        tvUserRank = view.findViewById(R.id.tv_user_rank);
        tvUserName = view.findViewById(R.id.tv_user_name);
        ivArrowRight = view.findViewById(R.id.iv_arrow_right);
        ivUserQrcode = view.findViewById(R.id.iv_user_qrcode);
        rlVipLevelUp = view.findViewById(R.id.rl_vip_level_up);
        rlHrzAcademy = view.findViewById(R.id.rl_hrz_academy);
        rlReward = view.findViewById(R.id.rl_reward);
        llHideOrShow = view.findViewById(R.id.ll_hide_or_show);
        ivEyes = view.findViewById(R.id.iv_eyes);
        tvHideOrShowReward = view.findViewById(R.id.tv_hide_or_show_reward);
        tvAccumulateRewardAmount = view.findViewById(R.id.tv_accumulate_reward_amount);
        tvRewardIntoAccountToday = view.findViewById(R.id.tv_reward_into_account_today);
        tvWithdrawGoodsAmount = view.findViewById(R.id.tv_withdraw_goods_amount);
        rlPredictRewardAmount = view.findViewById(R.id.rl_predict_reward_amount);
        tvPredictRewardAmount = view.findViewById(R.id.tv_predict_reward_amount);
        rlWithdraw = view.findViewById(R.id.rl_withdraw);
        tvWithdrawAmount = view.findViewById(R.id.tv_withdraw_amount);
        llAllDeals = view.findViewById(R.id.ll_all_deals);
        llDealSuccess = view.findViewById(R.id.ll_deal_success);
        llDealClose = view.findViewById(R.id.ll_deal_close);
        llDealInvalid = view.findViewById(R.id.ll_deal_invalid);
        rvOtherOptions = view.findViewById(R.id.rv_other_options);
    }


    private void initOptionView(){
        option_tag=getResources().getStringArray(R.array.functionOptions);
        List<FuncOption> options=new ArrayList<>();
        for(int i=0;i<option_resource_id.length;i++){
            FuncOption option=new FuncOption(option_tag[i],option_resource_id[i]);
            options.add(option);
        }
        rvOtherOptions.setLayoutManager(new GridLayoutManager(getActivity(),4));
        adapter=new OptionAdapter(R.layout.mine_option_item_view,options);
        rvOtherOptions.setAdapter(adapter);
    }

    private void initOnClickEvent(){
        llHideOrShow.setOnClickListener(this);
        mineMainSettingTv.setOnClickListener(this);
        rlWithdraw.setOnClickListener(v ->ARouter.getInstance().build(ARouterPaths.WITHDRAW_DEPOSIT).navigation(getActivity()));
        rlPredictRewardAmount.setOnClickListener(v -> ARouter.getInstance().build(ARouterPaths.PROFIT_DETAILS).navigation());
    }

    private void gotoMineActivity(){
        ARouter.getInstance()
                .build(ARouterPaths.LOGIN_MAIN_MINE)
                .withString(Consts.LOGIN_SOURCE_KEY,"我的")
                .withTransition(R.anim.push_bottom_in_set, R.anim.push_bottom_out_set)
                .navigation(getActivity());
    }

    private void  gotoUserInfoActivity(){
        UserInfoActivity.gotoActivity(getActivity());
    }

    @Override
    public void showUserRewardInfo(UserRewardInfo userRewardInfo) {
        INCOME_STATE=MineConstants.INCOME_SUCCESS;
        rlReward.setVisibility(View.VISIBLE);
        accumulateReward=userRewardInfo.getData().getTotalIncome();
        predictReward = userRewardInfo.getData().getPredictIncome();
        todayIntoAccount=userRewardInfo.getData().getTodayIncome();
        returnAmount=userRewardInfo.getData().getTodayReturns();
        withdrawAmount=userRewardInfo.getData().getCash();
        LoginDataManager.getsInstance(getActivity()).setCanWithdrawCash(withdrawAmount);
        showAmountUi();
        hrzHeadersView.refreshComplete();
    }

    @Override
    public void getUserRewardError() {
        INCOME_STATE=MineConstants.INCOME_FAIL;
        hrzHeadersView.refreshComplete();
        showAmountUi();
    }

    //显示收益模块
    private void showAmountUi(){
            if (LoginDataManager.getsInstance(getActivity()).getIsShowUserIncome()) {
                setUserLoginView();
                showReward(INCOME_STATE);
            } else {
               hideReward();
            }
    }


    private void showReward(int state){
        tvHideOrShowReward.setText("隐藏");
        ivEyes.setSelected(false);
        llHideOrShow.setSelected(false);
        if(state==MineConstants.INCOME_SUCCESS){
            tvAccumulateRewardAmount.setText(accumulateReward);
            tvPredictRewardAmount.setText(predictReward);
            String todayStr = todayIntoAccount;
            if (!todayStr.contains("今日入账:")) {
                todayStr = "今日入账:" + todayIntoAccount;
            }
            tvRewardIntoAccountToday.setText(todayStr);
            String returnStr = returnAmount;
            if (!returnStr.contains("退货金额:")) {
                returnStr = "退货金额:" + returnAmount;
            }
            tvWithdrawGoodsAmount.setText(returnStr);
            String withdrawStr = withdrawAmount;
            if (!withdrawStr.contains("可提现: ")) {
                withdrawStr = "可提现: " + withdrawAmount;
            }
            tvWithdrawAmount.setText(withdrawStr);
        }else if(state==MineConstants.INCOME_FAIL){
            tvAccumulateRewardAmount.setText(MineConstants.NO_INCOME_DATA);
            tvPredictRewardAmount.setText(MineConstants.NO_INCOME_DATA);
            tvRewardIntoAccountToday.setText("今日入账:" + MineConstants.NO_INCOME_DATA);
            tvWithdrawGoodsAmount.setText("退货金额:" + MineConstants.NO_INCOME_DATA);
            tvWithdrawAmount.setText("可提现: " + MineConstants.NO_INCOME_DATA);
        }
    }

    private void hideReward(){
        tvHideOrShowReward.setText("显示");
        ivEyes.setSelected(true);
        llHideOrShow.setSelected(true);
        tvAccumulateRewardAmount.setText(MineConstants.REWARD_HIDE_STR);
        tvPredictRewardAmount.setText(MineConstants.REWARD_HIDE_STR);
        tvRewardIntoAccountToday.setText("今日入账:"+MineConstants.REWARD_HIDE_STR);
        tvWithdrawGoodsAmount.setText("退货金额:"+MineConstants.REWARD_HIDE_STR);
        tvWithdrawAmount.setText("可提现: "+MineConstants.REWARD_HIDE_STR);
    }

    private void showLoginUi(){
        setUserLoginView();
        setUserLoginListener();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if(position==0){
            mPresenter.loadInviteCodeInfo();
        }else if(position==1){
            HrzRouter.getsInstance(getActivity()).navigation(Consts.getHrzRouterUrl(Consts.HRZ_MY_ACTIVITY_URL,"我的活动"));
        }else if(position==2){
            ARouter.getInstance().build(ARouterPaths.ALLIANCE_DOCKING).navigation(getActivity());
        }else if(position==3){
            HrzRouter.getsInstance(getActivity()).navigation(Consts.getHrzRouterUrl(Consts.HRZ_TI_YAN_GUAN_URL,"体验馆入场券"));
        }else if(position==4){
            HrzRouter.getsInstance(getActivity()).navigation(Consts.getHrzRouterUrl(Consts.HRZ_FAQ_URL,"常见问题"));
        }else if(position==5){
            EventBus.getDefault().post(new KeFuLoginEvent(true));
        }else if(position==6){
            ARouter.getInstance().build(ARouterPaths.CONTACT_US).navigation(getActivity());
        }
    }

    private void setUserLoginListener(){
        adapter.setOnItemClickListener(this);
        ivArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build(ARouterPaths.VIP_QR_CODE_ACTIVITY).withString("source", "我的")
                        .navigation(getActivity());
            }
        });
        flUserInfo.setOnClickListener(v -> gotoUserInfoActivity());
        tvUserName.setOnClickListener(v -> gotoUserInfoActivity());
        ivUserQrcode.setOnClickListener(v ->
                ARouter.getInstance()
                        .build(ARouterPaths.VIP_QR_CODE_ACTIVITY).withString("source", "我的")
                        .navigation(getActivity()));

        rlVipLevelUp.setOnClickListener(v -> {
            ((BaseActivity)getActivity()).invokeActivityMethod(Consts.SET_TABLAYOUT_INDEX);
            // HrzRouter.getsInstance(getContext()).navigation("http://192.168.1.232:8080/#/index");
        });

        rlHrzAcademy.setOnClickListener(v -> {
            HrzRouter.getsInstance(getActivity()).navigation(Consts.getHrzRouterUrl(Consts.HRZ_SCHOOL_URL,"商学院"));
            // HrzRouter.getsInstance(getContext()).navigation("http://192.168.1.39:8080/#/NewGet/NewGet");
        });

        llAllDeals.setOnClickListener(v -> ARouter.getInstance().build(ARouterPaths.ORDER_LIST)
                .withInt("select_order",MineConstants.ALL_ORDERS)
                .navigation(getActivity()));
        llDealSuccess.setOnClickListener(v -> ARouter.getInstance().build(ARouterPaths.ORDER_LIST)
                .withInt("select_order",MineConstants.ORDER_SUCCESS)
                .navigation(getActivity()));
        llDealClose.setOnClickListener(v -> ARouter.getInstance().build(ARouterPaths.ORDER_LIST)
                .withInt("select_order",MineConstants.ORDER_JIESUAN)
                .navigation(getActivity()));
        llDealInvalid.setOnClickListener(v -> ARouter.getInstance().build(ARouterPaths.ORDER_LIST)
                .withInt("select_order",MineConstants.ORDER_FAIL).navigation(getActivity()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(LoginStatusEvent messageEvent) {
        if(LoginDataManager.getsInstance(getActivity()).isLogin()){
            showLoginUi();
            requestRewardPart();
        }else{
            showNoLoginUi();
        }
    }

    private void showNoLoginUi(){
        rlReward.setVisibility(View.GONE);
        tvUserRank.setVisibility(View.GONE);
        tvUserName.setText(R.string.click_login_text);
        ivUserIcon.setImageResource(R.drawable.iv_user_default_icon);
        llAllDeals.setOnClickListener(v -> gotoMineActivity());
        flUserInfo.setOnClickListener(v -> gotoMineActivity());
        tvUserName.setOnClickListener(v -> gotoMineActivity());
        ivUserQrcode.setOnClickListener(v -> gotoMineActivity());
        ivArrowRight.setOnClickListener(v -> gotoMineActivity());
        adapter.setOnItemClickListener((adapter, view, position) -> gotoMineActivity());
        rlVipLevelUp.setOnClickListener(v -> gotoMineActivity());
        rlHrzAcademy.setOnClickListener(v -> gotoMineActivity());
        llDealSuccess.setOnClickListener(v -> gotoMineActivity());
        llDealClose.setOnClickListener(v -> gotoMineActivity());
        llDealInvalid.setOnClickListener(v -> gotoMineActivity());
    }


    @Override
    public void requestPermissionSuccess(String permissionType) {
       if(TextUtils.equals(permissionType,PermissionUtil.STORAGE)){
           ARouter.getInstance().build(ARouterPaths.SETTINGS_ACTIVITY).navigation(getActivity());
       }
    }

    @Override
    public void showUserInfo(UserInfo loginResultBean) {
        LoginDataManager.getsInstance(getActivity()).setUserInfo(loginResultBean.getData());
        setUserLoginView();
        LoginUtil.postLoginSuccessEvent(getActivity());
        isLoadFirst=true;
    }

    //TODO ????为什么把邀请码的请求放在我的里面？？？
    @Override
    public void onLoadInviteCodeInfoCompleted(InviteCodeBean info) {
        Log.i("jan", "info = " + info);
        if (info == null || info.getData() == null) {
            ARouter.getInstance().build(ARouterPaths.INVITE_CODE_WRITING_ACTIVITY).navigation(getActivity());
            return;
        }

        InviteCodeBean.Data data = info.getData();
        if (data.isHasRel()) {
            ARouter.getInstance().build(ARouterPaths.INVITE_CODE_EXISTED_ACTIVITY)
                    .withString("nikeName", data.getNikeName())
                    .withString("avatarUrl", data.getAvatarUrl())
                    .withString("inviteCode", data.getInviteCode())
                    .navigation(getActivity());
        } else {
            ARouter.getInstance().build(ARouterPaths.INVITE_CODE_WRITING_ACTIVITY).navigation(getActivity());
        }
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    /**
     * 显示用户数据
     */
    public void setUserLoginView() {

        rlReward.setVisibility(View.VISIBLE);
        tvUserRank.setVisibility(View.VISIBLE);

        if(LoginDataManager.getsInstance(getActivity()).getLevel() == MineConstants.FAN_LEVEL){
           tvUserRank.setText(getResources().getString(R.string.levelFans));
        }else if(LoginDataManager.getsInstance(getActivity()).getLevel() == MineConstants.CROWN_LEVEL) {
            tvUserRank.setText(getResources().getString(R.string.levelCrown));
        }else if (LoginDataManager.getsInstance(getActivity()).getLevel() == MineConstants.SHOP_LEVEL){
            tvUserRank.setText(getResources().getString(R.string.levelShopKepper));
        }else {
            tvUserRank.setText(getResources().getString(R.string.levelFans));
        }

        if (!TextUtils.isEmpty(LoginDataManager.getsInstance(getActivity()).getDisplayName())) {
            tvUserName.setText(LoginDataManager.getsInstance(getActivity()).getDisplayName());
        }else {
            tvUserName.setText("请设置您的昵称");
        }

        if (!TextUtils.isEmpty(LoginDataManager.getsInstance(getActivity()).getAvatarUrl())) {
            Glide.with(getContext()).load(LoginDataManager.getsInstance(getActivity()).getAvatarUrl()).into(ivUserIcon);
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.mine_main_setting_tv){
            if(PermissionUtil.checkStoragePermission(getActivity())){
                ARouter.getInstance().build(ARouterPaths.SETTINGS_ACTIVITY).navigation(getActivity());
            }else {
                mPresenter.requestStorage();
            }
        }else if(v.getId()==R.id.ll_hide_or_show){
            if(llHideOrShow.isSelected()){
                LoginDataManager.getsInstance(getActivity()).setIsShowUserIncome(true);
            }else {
                LoginDataManager.getsInstance(getActivity()).setIsShowUserIncome(false);
            }
            showAmountUi();
        }

    }


    @Override
    public void hideLoading() {
        if(hrzHeadersView!=null){
            hrzHeadersView.refreshComplete();
        }
    }

    @Override
    public void setData(@Nullable Object data) { }

    @Override
    public void showLoading() { }

    @Override
    public void saveSucess(Long itemId) { }

    @Override
    public void saveFail(Long itemId) {}

    @Override
    public void showMessage(@NonNull String message) {
        //出错 异常
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() { }

    @Override
    public void showUserInfo(UserBean userBean) { }

    @Override
    public void noUserInfoDB() { }

    @Override
    public void getProvinceSuccess(List<Province> provinceList) { }

    @Override
    public void getCitiesSuccess(List<City> cityList) { }

    @Override
    public void getLocalDbFail() {}

    @Override
    public RxPermissions getRxPermissions() {
        return rxPermissions;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if(!isLoadFirst){
                delayInit();
            }
            mStatusBarView.adjustStatusBarColor(getActivity());
        }else {
            hideLoading();
        }
    }

    //延迟加载收益
    private void delayInit() {
        if(LoginDataManager.getsInstance(getActivity()).isLogin()){
            requestRewardPart();
        }
    }


    //--------------------------------未上线功能------------------------------------------------

    /*
     * 显示红包雨view 目前功能未上线
     */
    private void showRedPocketView() {
        if (hrzLayerView == null) {
            hrzLayerView = new HrzLayerView(getActivity(), STATE_WEBVIEW);
            hrzLayerView.setLoadScheme(LayerConfig.redPocketScheme);
            hrzLayerView.initLayerView(new WebConfigImplByUser());
        }
        hrzLayerView.show();
    }


    private void hideLayerView() {
        if (hrzLayerView != null) {
            hrzLayerView.dismiss();
        }
    }

    /*
     * 显示验证码弹窗
     */
    private void showVertifyCodeDialog() {
        if (dialog == null) {
            dialog = new HrzLayerView(getActivity(), STATE_DIALOG);
            dialog.setDialogView(initVertifyCodeView());
            dialog.initLayerView(null);
        }
        dialog.show();
    }

    private View initVertifyCodeView(){
        View view = View.inflate(getActivity(), R.layout.mine_module_vertify_dialog_view, null);
        view.findViewById(R.id.tv_cancle).setOnClickListener(v -> dialog.dismiss());
        EditText et_vertify_code=view.findViewById(R.id.et_vertify_code);
        code=getCaptureCode(view.findViewById(R.id.view_capture));
        view.findViewById(R.id.tv_change).setOnClickListener(v -> {
            code=getCaptureCode(view.findViewById(R.id.view_capture));
        });
        view.findViewById(R.id.tv_sure).setOnClickListener(v -> {
            if(TextUtils.equals(et_vertify_code.getText().toString().toLowerCase(),code)){
                dialog.dismiss();
                et_vertify_code.setText("");
                ArmsUtils.makeText(getActivity(),"验证成功");
            }else {
                et_vertify_code.setText("");
                ArmsUtils.makeText(getActivity(),"验证失败,请重试");
            }
        });
        return view;
    }


    /**
     * 生成二维码
     * @param view
     * @return
     */
    private String getCaptureCode(ImageView view) {
        RxCaptcha.build()
                .backColor(0xB2B2B2)
                .codeLength(4)
                .fontSize(45)
                .lineNumber(0)
                .size(200, 100)
                .type(CHARS).into(view);
        return RxCaptcha.build().getCode();
    }



    //--------------------------下拉刷新回调------------------------------------------------------


    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        if(LoginDataManager.getsInstance(getActivity()).isLogin()){
            frame.postDelayed(() -> requestRewardPart(), 250);
        }else {
            if(hrzHeadersView!=null) hrzHeadersView.refreshComplete();
        }
    }


    private void requestRewardPart(){
        RetrofitUrlManager.getInstance().putDomain("api_server", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        if(mPresenter!=null)  mPresenter.getUserRewardInfo(getContext());
    }

    private void requestUserInfo(){
        RetrofitUrlManager.getInstance().putDomain("api_server", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        HashMap<String,String> params=new HashMap<>();
        params.put("accountSecToken",LoginDataManager.getsInstance(getActivity()).getAccountSecToken());
        if(mPresenter!=null) mPresenter.getUserInfoByToken(HttpParamUtil.getResultMap(getActivity(),params));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null){
            unbinder.unbind();
        }
    }

}
