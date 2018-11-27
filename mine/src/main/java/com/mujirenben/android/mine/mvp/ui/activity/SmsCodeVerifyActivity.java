package com.mujirenben.android.mine.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jkb.vcedittext.VerificationCodeEditText;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.LogUtil;
import com.mujirenben.android.common.util.sensorHelper.SensorHelper;
import com.mujirenben.android.common.widget.DialogUtils;
import com.mujirenben.android.common.widget.LoadingButton;
import com.mujirenben.android.mine.di.component.DaggerSmsVerifyCodeComponent;
import com.mujirenben.android.mine.di.module.SmsVerifyCodeModule;
import com.mujirenben.android.mine.login.LoginUtil;
import com.mujirenben.android.mine.login.bean.LoginResultBean;
import com.mujirenben.android.mine.login.bean.PhoneVerifyCodeBean;
import com.mujirenben.android.mine.mvp.contract.SmsCodeVerifyContract;
import com.mujirenben.android.mine.mvp.model.bean.MineConstant;
import com.mujirenben.android.mine.mvp.presenter.SmsCodeVerifyPresenter;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

import static com.mujirenben.android.mine.mvp.ui.activity.LoginHrzActivity.loginSourceKey;

@Route(path = ARouterPaths.SMS_CODE_VERTIFY)
public class SmsCodeVerifyActivity extends BaseActivity<SmsCodeVerifyPresenter> implements SmsCodeVerifyContract.View {

    private ImageView tvBack;
    private TextView tvTitlebar;
    private TextView tvAlreadySent;
    private VerificationCodeEditText etVerify;
    private TextView tvResent;
    private LoadingButton btnLoginSms;
    private TextView tvResentVoice;
    private LinearLayout llBg;
    private TextView tvSendCode;
    private Timer countdownTimer;
    private DialogUtils toastUtil;
    private int sec_count = 59;
    private String phonenum;
    private String titleText;
    private boolean phoneHadBeenBind = false;
    /**
     * 从哪个页面跳转过来的，
     * 1、登录页面
     * 2、修改绑定手机页面
     */
    private String from;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                if (countdownTimer != null) {
                    countdownTimer.cancel();
                    countdownTimer = null;
                }
                sec_count = 59;
                tvSendCode.setText("获取验证码");
                tvSendCode.setEnabled(true);
            } else {
                tvSendCode.setText(msg.what + "s后重新发送");
                tvSendCode.setEnabled(false);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvBack = findViewById(R.id.tv_back);
        tvTitlebar = findViewById(R.id.tv_titlebar);
        tvAlreadySent = findViewById(R.id.tv_already_sent);
        etVerify = findViewById(R.id.et_verify);
        tvResent = findViewById(R.id.tv_resent);
        btnLoginSms = findViewById(R.id.btn_login_sms);
        btnLoginSms.setUnActivateState("确认");
        btnLoginSms.setEnabled(true);
        tvResentVoice = findViewById(R.id.tv_resent_voice);
        llBg = findViewById(R.id.ll_sms_verify_bg);
//      RxTextTool.getBuilder("收不到？", this)
//             .append("获取语音验证码").setForegroundColor(this.getResources().getColor(R.color.argeement_color))
//             .into(tvResentVoice);
        toastUtil = new DialogUtils(this, R.layout.common_loading_toast, "");
        initClickEvent();
        prepareView();
        //已经被绑定过的手机，需要重新发验证
        if(phoneHadBeenBind || (!TextUtils.isEmpty(from)&&from.equals(MineConstant.BIND_PAGE_ACTIVITY))){
            startCountdown(tvResent);
        }
        llBg.getBackground().setAlpha(234);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSmsVerifyCodeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .smsVerifyCodeModule(new SmsVerifyCodeModule(this))
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
        return R.layout.main_activity_sms_verify;
    }

    /**
     * 初始化UI
     */
    private void prepareView() {
        phonenum = getIntent().getStringExtra(MineConstant.PHONE_KEY);
        titleText = getIntent().getStringExtra(MineConstant.TITLE_KEY);
        from = getIntent().getStringExtra(MineConstant.FROM_KEY);
        phoneHadBeenBind = getIntent().getBooleanExtra(MineConstant.PHONE_HAD_BEEN_BIND,false);
        tvTitlebar.setText(titleText);
        if (phonenum != null && !TextUtils.isEmpty(phonenum)) {
            tvAlreadySent.setText("输入(+86)" + phonenum + "收到的4位验证码");
            phonenum = phonenum.trim().replaceAll("\\s*", "");
        }
    }

    /**
     * 点击事件
     */
    private void initClickEvent() {
      tvBack.setOnClickListener(v -> backToLastActivity());
      btnLoginSms.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(from) && from.equals(MineConstant.LOGIN_PAGE_ACTIVITY)){
                btnLoginSms.startLoading("正在登录");
            }else if(!TextUtils.isEmpty(from) && from.equals(MineConstant.BIND_PAGE_ACTIVITY)){
                btnLoginSms.startLoading("正在验证");
            }
            String verifyCode = etVerify.getText().toString();
            RetrofitUrlManager.getInstance().putDomain("wx_login_hrz", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
            if(phoneHadBeenBind){
                //如果手机已经被绑定过，且使用该手机进行登录，则不需要传token
                mPresenter.loginWithPhone(phonenum, verifyCode,false);
            }else {
                //如果手机没有被绑定过，且使用该手机进行登录，则需要传token
                mPresenter.loginWithPhone(phonenum, verifyCode,true);
            }
        });
      tvResent.setOnClickListener(v -> startCountdown(tvResent));
      etVerify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length() == 4) {
//                    btnLoginSms.setActivateState("确认");
//                    btnLoginSms.setEnabled(true);
//                } else {
//                    btnLoginSms.setUnActivateState("确认");
//                    btnLoginSms.setEnabled(false);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 4) {
                    btnLoginSms.setActivateState("确认");
                    btnLoginSms.setEnabled(true);
                } else {
                    btnLoginSms.setUnActivateState("确认");
                    btnLoginSms.setEnabled(false);
                }
            }
        });
    }

    /**
     * 返回上一个页面
     */
    private void backToLastActivity() {
        if(!TextUtils.isEmpty(from) && from.equals(MineConstant.LOGIN_PAGE_ACTIVITY)){
            ARouter.getInstance().build(ARouterPaths.LOGIN_MAIN_MINE)
                    .withString("user_phone", phonenum)
                    .withTransition(R.anim.slide_out_right, R.anim.slide_to_right)//跳转到的界面动画，自己退场动画
                    .navigation(this);
        }else if(!TextUtils.isEmpty(from) && from.equals(MineConstant.BIND_PAGE_ACTIVITY)){
            ARouter.getInstance().build(ARouterPaths.BIND_PHONE_ACTIVITY)
                    .withString("user_phone", phonenum)
                    .withTransition(R.anim.slide_out_right, R.anim.slide_to_right)//跳转到的界面动画，自己退场动画
                    .navigation(this);
        }
        finish();
    }

    /**
     * 验证码倒计时
     * @param textView
     */
    public void startCountdown(TextView textView) {
        tvSendCode = textView;
        if (countdownTimer == null) {
            countdownTimer = new Timer();
            countdownTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = sec_count--;
                    handler.sendMessage(msg);
                }
            }, 0, 1000);
            RetrofitUrlManager.getInstance().putDomain("wx_login_hrz", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
            mPresenter.getSmsVerifyCode(phonenum);
            // LoginHelper.getInstance(this).getPhoneVerifyCode(this,phonenum);
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    /**
     * 手机验证返回成功
     * @param
     */
    @Override
    public void onPhoneVerifyCodeReturn(PhoneVerifyCodeBean pb) {
        ArmsUtils.makeText(this,"手机验证码已发送，请注意查收");
    }

    /**
     * 手机验证返回失败
     * @param
     * @param msg
     */
    @Override
    public void onPhoneVerifyCodeReturnFail(String msg) {
        ArmsUtils.makeText(getApplicationContext(),"无法获取手机验证码");
    }

    /**
     * 手机号码登录成功
     * @param ld
     */
    @Override
    public void onLoginSuccess(LoginResultBean ld) {
        if (ld == null || !ld.getCode().equals(Consts.REQUEST_SUCCESS)) {
            ArmsUtils.makeText(SmsCodeVerifyActivity.this,"登录失败");
            LoginDataManager.getsInstance(getApplicationContext()).logout();
            return;
        }
        if(ld.getCode().equals("91023")){
            ArmsUtils.makeText(this,ld.getMessage());
            return;
        }
        LogUtil.d("login", " onLoginSuccess  loginResultBean: " + ld.toString());
        LoginDataManager loginDataManager = LoginDataManager.getsInstance(getApplicationContext());
        loginDataManager.setAccountSecToken(ld.getData().getAccountSecToken());
        loginDataManager.setAccountSecKey(ld.getData().getAccountSecKey());
        loginDataManager.saveWeixinUnionid(ld.getData().getInfo().getUnionId());
        loginDataManager.saveWeixinOpenId(ld.getData().getInfo().getOpenId());
        loginDataManager.setUserInfo(ld.getData().getInfo());
        //绑定个推账号 TODO
//        GeTuiSDKInitUtil.initGeTuiServer(this);
        //会员登录成功，改变vip页面状态
        LoginUtil.postLoginSuccessEvent(getApplicationContext());
        hideInputMethod();
        //登录成功之后神策打点
        SensorsDataAPI.sharedInstance().login(ld.getData().getInfo().getId()+"");
        //只有是从登录页面跳转过来的 才上传登录打点
        if(!TextUtils.isEmpty(from) && from.equals(MineConstant.LOGIN_PAGE_ACTIVITY)){
            Map<String,Object> loginSensorMap = new HashMap<>();
            loginSensorMap.put("source",loginSourceKey);
            loginSensorMap.put("result","success");
            loginSensorMap.put("type","手机");
            SensorHelper.uploadTrack("login",loginSensorMap);
        }
        setResult(MineConstant.SMS_CODE_VERIFY_REQUEST_CODE);
        ArmsUtils.makeText(this,"登录成功");
        finish();
    }

    /**
     * 手机号码登录失败
     * @param errorCode
     */
    @Override
    public void onLoginFail(String errorCode) {
        //只有是从登录页面跳转过来的 才上传登录打点
        if(!TextUtils.isEmpty(from) && from.equals(MineConstant.LOGIN_PAGE_ACTIVITY)){
            Map<String,Object> loginSensorMap = new HashMap<>();
            loginSensorMap.put("source",loginSourceKey);
            loginSensorMap.put("result","fail");
            loginSensorMap.put("type","手机");
            SensorHelper.uploadTrack("login",loginSensorMap);
        }
        ArmsUtils.makeText(SmsCodeVerifyActivity.this,errorCode);
        btnLoginSms.setActivateState("确认");
        btnLoginSms.setEnabled(true);
    }

    /**
     * 隐藏输入法
     */
    private void hideInputMethod() {
        if(getActivity() != null && !getActivity().isFinishing()){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etVerify.getWindowToken(), 0); //强制隐藏键盘
        }
    }

    @Override
    public void showLoading() {
        toastUtil.show();
    }

    @Override
    public void hideLoading() {
        toastUtil.hide();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backToLastActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginHrzActivity.tempUserInfo = null;
    }
}
