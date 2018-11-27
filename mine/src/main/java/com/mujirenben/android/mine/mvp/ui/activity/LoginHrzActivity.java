package com.mujirenben.android.mine.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.arounter.HrzRouter;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.datapackage.bean.MessageEventGlobal;
import com.mujirenben.android.common.event.WeiXinEvent;
import com.mujirenben.android.common.http.HttpParamName;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.LogUtil;
import com.mujirenben.android.common.util.rxtools.RxTextTool;
import com.mujirenben.android.common.util.sensorHelper.SensorHelper;
import com.mujirenben.android.common.util.wxHelper.WeiXinHelper;
import com.mujirenben.android.mine.di.component.DaggerLoginComponent;
import com.mujirenben.android.mine.di.module.LoginModule;
import com.mujirenben.android.mine.login.LoginUtil;
import com.mujirenben.android.mine.login.bean.LoginResultBean;
import com.mujirenben.android.mine.login.bean.MatchPhoneBean;
import com.mujirenben.android.mine.login.bean.TempUserInfo;
import com.mujirenben.android.mine.login.bean.WeixinAccessTokenResult;
import com.mujirenben.android.mine.mvp.contract.LoginContract;
import com.mujirenben.android.mine.mvp.model.bean.MineConstant;
import com.mujirenben.android.mine.mvp.presenter.LoginPresenter;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

import static com.mujirenben.android.common.constants.Consts.PHONE_NUMBER_REG;
import static com.mujirenben.android.common.constants.Consts.USE_GRAY_TEST;


@Route(path = ARouterPaths.LOGIN_MAIN_MINE)
public class LoginHrzActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener, LoginContract.View {//右侧滑进左侧画出 渐变透明度

    private FrameLayout rlLoginBg;
    private RelativeLayout wxLoginLayout;
    private TextView titleView;
    private ImageView ivClose;
    private RelativeLayout phoneLayout;
    private LinearLayout llInput;
    private EditText etMobile;
    private TextView llLawDesc;
    private LinearLayout llAgreementLogin;
    private TextView tvAgreement;
    private TextView ivConfirmPhoneLogin;
    private LinearLayout llLoginWayWexin;
    private ImageView ivWxLogin;
    private LinearLayout phoneHadBindLayout;
    private RelativeLayout rlTitlebarBg;
    private ImageView tvBack;
    private TextView tvTitlebar;
    private TextView hadBindPhoneTv;
    private TextView hadBindOtherWxLoginTv;
    private TextView hadBindThisPhoneLoginTv;
    private String phone_num_saved;
    private LoginDataManager loginDataManager;

    public static TempUserInfo tempUserInfo;
    public static String loginSourceKey;

    private int mRequestCode = -1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        return R.layout.mine_activity_login_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        loginDataManager = LoginDataManager.getsInstance(getApplicationContext());
        if(getIntent() != null){
            mRequestCode = getIntent().getIntExtra("request_code", -1);
            loginSourceKey = getIntent().getStringExtra(Consts.LOGIN_SOURCE_KEY);
            phone_num_saved = getIntent().getStringExtra("user_phone");
          if (TextUtils.isEmpty(loginSourceKey)){
              loginSourceKey = "我的";
          }
        }
        assignViews();
        initViewData();
    }

    private void assignViews() {
        rlLoginBg = (FrameLayout) findViewById(R.id.rl_login_bg);
        wxLoginLayout = (RelativeLayout) findViewById(R.id.wx_login_layout);
        titleView = (TextView) findViewById(R.id.login_bind_phone_title);
        ivClose = (ImageView) findViewById(R.id.iv_close);
        phoneLayout = (RelativeLayout) findViewById(R.id.ll_login_way_phone);
        llInput = (LinearLayout) findViewById(R.id.ll_input);
        etMobile = (EditText) findViewById(R.id.et_mobile);
        llLawDesc = (TextView) findViewById(R.id.ll_law_desc);
        llAgreementLogin = (LinearLayout) findViewById(R.id.ll_agreement_login);
        tvAgreement = (TextView) findViewById(R.id.tv_login_agreement);
        ivConfirmPhoneLogin = (TextView) findViewById(R.id.iv_confirm_phone_login);
        llLoginWayWexin = (LinearLayout) findViewById(R.id.ll_login_way_weixin);
        ivWxLogin = (ImageView) findViewById(R.id.iv_wx_login);
        phoneHadBindLayout = (LinearLayout) findViewById(R.id.phone_had_bind_layout);
        rlTitlebarBg = (RelativeLayout) findViewById(R.id.rl_titlebar_bg);
        tvBack = (ImageView) findViewById(R.id.tv_back);
        tvTitlebar = (TextView) findViewById(R.id.tv_titlebar);
        hadBindPhoneTv = (TextView) findViewById(R.id.had_bind_phone_tv);
        hadBindOtherWxLoginTv = (TextView) findViewById(R.id.had_bind_other_wx_login_tv);
        hadBindThisPhoneLoginTv = (TextView) findViewById(R.id.had_bind_this_phone_login_tv);
    }
    /**
     * 初始化页面数据
     */
    private void initViewData() {
        if (USE_GRAY_TEST){
            ivClose.setVisibility(View.GONE);
        }else {
            ivClose.setVisibility(View.VISIBLE);
        }
        wxLoginLayout.setVisibility(View.VISIBLE);
        phoneHadBindLayout.setVisibility(View.GONE);
        if(!TextUtils.isEmpty(phone_num_saved)){
            etMobile.setText(phone_num_saved);
            setViewState(etMobile.getText().toString());
        }
        llLoginWayWexin.setOnClickListener(LoginHrzActivity.this);
        // 响应点击事件的话必须设置以下属性
        tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        RxTextTool.getBuilder("继续表示您同意", this)
                .append("红人装用户协议")
                .setForegroundColor(getResources().getColor(R.color.argeement_color))
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        HrzRouter.getsInstance(LoginHrzActivity.this).navigation(Consts.HRZ_USER_AGREE_URL);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(getResources().getColor(R.color.argeement_color));
                    }
                })
                .into(tvAgreement);
        rlLoginBg.getBackground().setAlpha(234);
        prepareListener();
    }

    /**
     * 监听事件
     */
    private void prepareListener() {
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setViewState(s);

                if (count != 0) {
                    int length = s.length() + 1;
                    if (length % 4 == 0 && length < 5) {
                        etMobile.setText(s + " ");
                        etMobile.setSelection(length);
                    } else if (length % 9 == 0 && length < 14 && length > 5) {
                        etMobile.setText(s + " ");
                        etMobile.setSelection(length);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ivConfirmPhoneLogin.setOnClickListener((View v) -> {
            String phoneNumber = etMobile.getText().toString();
            if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 11) {
                ArmsUtils.makeText(LoginHrzActivity.this,"请输入11位的手机号码");
                return;
            }
            boolean isPhoneNumber = isPhoneNumber(phoneNumber.trim().replaceAll("\\s*", ""));
            if (!isPhoneNumber) {
                ArmsUtils.makeText(LoginHrzActivity.this,"请输入正确的手机号码");
                return;
            }
            matchPhone();
        });
        ivClose.setOnClickListener( v ->{
            LoginDataManager.getsInstance(this).logout();
            finish();
        });
    }

    /**
     * 匹配手机号
     */
    private void matchPhone() {
        String phone = etMobile.getText().toString();
        String phoneNumber = phone.trim().replaceAll("\\s*", "");
        HashMap<String, String> paramMap = HttpParamUtil.getCommonParamMap(this);
        paramMap.put(HttpParamName.PARAM_ACCOUNT_SEC_TOKEN,tempUserInfo.getAccountSecToken());
        paramMap.put("phone",phoneNumber);
        paramMap.put(HttpParamName.PARAM_SIGN, HttpParamUtil.getSignParamStr(paramMap,tempUserInfo.getAccountSecKey()));
        RetrofitUrlManager.getInstance().putDomain("match_phone", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        mPresenter.matchPhone(paramMap);
    }

    public static boolean isPhoneNumber(String mobiles) {
        boolean match = Pattern.matches(PHONE_NUMBER_REG, mobiles);
        return match;
    }

    private void setViewState(CharSequence text) {
        if (text.length() >= 1) {
            llAgreementLogin.setVisibility(View.VISIBLE);
        } else {
            llAgreementLogin.setVisibility(View.GONE);
        }
    }

    public void showSoftInputFromWindow() {
        etMobile.setFocusable(true);
        etMobile.setFocusableInTouchMode(true);
        etMobile.requestFocus();

        InputMethodManager inputManager = (InputMethodManager)etMobile.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(etMobile, 0);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEventGlobal messageEvent) {
        if (messageEvent.getMessage().equals(Const.SAVE_USER_DATA_SUCCESS)) {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(WeiXinEvent weiXinEvent) {
        if (weiXinEvent.getErrCode() == BaseResp.ErrCode.ERR_OK) {
            String code = weiXinEvent.getCode();
            loginDataManager.saveWeixinCode(code);

            LogUtil.d("login", "Event code: " + code);
            RetrofitUrlManager.getInstance().putDomain("wx_login", "https://api.weixin.qq.com");
            mPresenter.getWeixinAccessToken(code);

        } else {
            ArmsUtils.makeText(LoginHrzActivity.this,getResources().getString(R.string.login_weixin_error));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        rlLoginBg.setVisibility(View.VISIBLE);
        if(tempUserInfo == null){
            tempUserInfo = new TempUserInfo();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == llLoginWayWexin) {
            WeiXinHelper.getBuilder(getApplication()).build().wxLogin();
        }
    }

    @Override
    public Activity getActivity() {
        return null;
    }

    /**
     * 获取微信AccessToken成功
     * @param weixinAccessTokenResult
     */
    @Override
    public void onAccessTokeReturn(WeixinAccessTokenResult weixinAccessTokenResult) {
        if (weixinAccessTokenResult == null || weixinAccessTokenResult.getErrcode() > 0) {
            ArmsUtils.makeText(LoginHrzActivity.this,"获取asseceToken失败");
            return;
        }
        LogUtil.d("login", "onAccessTokeReturn: " + weixinAccessTokenResult.toString());
        String accessToken = weixinAccessTokenResult.getAccess_token();
        String openId = weixinAccessTokenResult.getOpenid();
        String unionid = weixinAccessTokenResult.getUnionid();
        //把accessToken保存在内存中
        tempUserInfo.setWxAccessToken(accessToken);
        //把openId保存在内存中
        tempUserInfo.setWxOpenId(openId);
        //把unionid保存在内存中
        tempUserInfo.setWxUnionid(unionid);
//        loginDataManager.saveWeixinAccessToken(accessToken);
//        loginDataManager.saveWeixinOpenId(openId);
//        loginDataManager.saveWeixinUnionid(weixinAccessTokenResult.getUnionid());
        RetrofitUrlManager.getInstance().putDomain("wx_login_hrz", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        mPresenter.LoginWithWeixin(accessToken, openId);
    }

    /**
     * 获取微信AccessToken失败
     * @param msg
     */
    @Override
    public void getWeixinAccessTokenFail(String msg) {
        ArmsUtils.makeText(this,msg);
        HashMap<String,Object> loginSensorMap = new HashMap<>();
        loginSensorMap.put("source",loginSourceKey);
        loginSensorMap.put("result","cancel");
        loginSensorMap.put("type","微信");
        SensorHelper.uploadTrack("login",loginSensorMap);
    }

    /**
     * 微信登录成功回调
     * @param loginResultBean
     */
    @Override
    public void onLoginSuccess(LoginResultBean loginResultBean) {

        if (loginResultBean == null && !loginResultBean.getCode().equals(Consts.REQUEST_SUCCESS)) {
            ArmsUtils.makeText(LoginHrzActivity.this,"登录失败！");
            return;
        }
        LoginResultBean.DataBean ld;
        if(loginResultBean.getCode().equals("91023")){
            ArmsUtils.makeText(this,loginResultBean.getMessage());
            return;
        }
        if (loginResultBean != null && loginResultBean.getData() != null) {
            ld = loginResultBean.getData();
            //把accountSecToken保存在内存中
            tempUserInfo.setAccountSecToken(ld.getAccountSecToken());
            //把accountSecKey保存在内存中
            tempUserInfo.setAccountSecKey(ld.getAccountSecKey());
            //把用户信息保存在内存中
            tempUserInfo.setUserInfoBean(ld.getInfo());
            //把phoneValidate保存在内存中
            tempUserInfo.setPhoneValidate(ld.getInfo().getPhoneValidate());

            //如果已经绑定了手机号，则直接跳转到我的页面,且把用户信息保存在本地
            if(!TextUtils.isEmpty(ld.getAccountSecToken())
                    && !TextUtils.isEmpty(ld.getAccountSecKey())&& ld.getInfo().getPhoneValidate() == 1){
                LoginDataManager loginDataManager = LoginDataManager.getsInstance(getApplicationContext());
                loginDataManager.setAccountSecToken(ld.getAccountSecToken());
                loginDataManager.setAccountSecKey(ld.getAccountSecKey());
                loginDataManager.setUserInfo(ld.getInfo());
                loginDataManager.saveWeixinOpenId(ld.getInfo().getOpenId());
                loginDataManager.saveWeixinUnionid(ld.getInfo().getUnionId());
                LoginUtil.postLoginSuccessEventWithRequestCode(getApplicationContext(), mRequestCode);

                //登录成功之后神策打点
                SensorsDataAPI.sharedInstance().login(ld.getInfo().getId()+"");
                Map<String,Object> loginSensorMap = new HashMap<>();
                loginSensorMap.put("source",loginSourceKey);
                loginSensorMap.put("result","success");
                loginSensorMap.put("type","微信");
                SensorHelper.uploadTrack("login",loginSensorMap);
                ArmsUtils.makeText(LoginHrzActivity.this,"登录成功");
                //绑定个推账号 TODO
//                GeTuiSDKInitUtil.initGeTuiServer(this);
                finish();

            }else if (!TextUtils.isEmpty(ld.getAccountSecToken()) && !TextUtils.isEmpty(ld.getAccountSecKey())){
                // 微信登录成功，进行下一步的手机号码绑定
                llLoginWayWexin.setVisibility(View.GONE);
                phoneLayout.setVisibility(View.VISIBLE);
                titleView.setText(R.string.mine_login_bind_phone_title);
                showSoftInputFromWindow();
            }
        } else {
            ArmsUtils.makeText(this, "登录异常,请联系管理员");
        }
    }

    /**
     * 微信登录失败回调
     * @param msg
     */
    @Override
    public void loginWithWeixinFail(String msg) {
        ArmsUtils.makeText(this,"登录失败");
        HashMap<String,Object> loginSensorMap = new HashMap<>();
        loginSensorMap.put("source",loginSourceKey);
        loginSensorMap.put("result","fail");
        loginSensorMap.put("type","微信");
        SensorHelper.uploadTrack("login",loginSensorMap);
    }

    /**
     * 匹配手机号成功回调
     * @param matchPhoneBean
     */
    @Override
    public void matchPhoneOnSuccess(MatchPhoneBean matchPhoneBean) {

        if(matchPhoneBean.getData() !=null && matchPhoneBean.getData().isMatch()){
            //没有绑定则走绑定流程
            ARouter.getInstance().build(ARouterPaths.SMS_CODE_VERTIFY)
                    .withString(MineConstant.TITLE_KEY,"绑定手机")
                    .withBoolean(MineConstant.PHONE_HAD_BEEN_BIND,false)
                    .withString(MineConstant.FROM_KEY,MineConstant.LOGIN_PAGE_ACTIVITY)
                    .withString(MineConstant.PHONE_KEY, etMobile.getText().toString())
                    .withTransition(R.anim.slide_in_right, R.anim.slide_out_left)//跳转到的界面动画，自己退场动画
                    .navigation(this);
            finish();
        }else {
            //如果该手机已经，绑定过微信，提示是否使用该手机登录
            showPhoneHadBindLayout();
        }
    }


    /**
     * 匹配手机号失败回调
     * @param msg
     */
    @Override
    public void matchPhoneOnFail(String msg) {
        ArmsUtils.makeText(this,msg);
    }

    /**
     * 显示已绑定页面
     */
    private void showPhoneHadBindLayout() {
        wxLoginLayout.setVisibility(View.GONE);
        phoneHadBindLayout.setVisibility(View.VISIBLE);
        hadBindPhoneTv.setText("+86 "+etMobile.getText().toString());
        hadBindThisPhoneLoginTv.setOnClickListener(v -> {
            ARouter.getInstance().build(ARouterPaths.SMS_CODE_VERTIFY)
                    .withString(MineConstant.TITLE_KEY,"绑定手机")
                    .withBoolean(MineConstant.PHONE_HAD_BEEN_BIND,true)
                    .withString(MineConstant.FROM_KEY,MineConstant.LOGIN_PAGE_ACTIVITY)
                    .withString(MineConstant.PHONE_KEY, etMobile.getText().toString())
                    .withTransition(R.anim.slide_in_right, R.anim.slide_out_left)//跳转到的界面动画，自己退场动画
                    .navigation(this);
            finish();
        });
        tvBack.setOnClickListener(v -> {
            wxLoginLayout.setVisibility(View.VISIBLE);
            phoneHadBindLayout.setVisibility(View.GONE);
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onBackPressed() {
        if (USE_GRAY_TEST){
           // super.onBackPressed();
        }else {
            super.onBackPressed();
            //把暂存的用户信息清空
            tempUserInfo = null;
        }
    }

}
