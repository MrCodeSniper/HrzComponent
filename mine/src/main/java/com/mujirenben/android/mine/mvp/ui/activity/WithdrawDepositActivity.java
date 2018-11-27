package com.mujirenben.android.mine.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.gcssloop.widget.RCImageView;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.arounter.HrzRouter;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.util.rxtools.RxTextTool;
import com.mujirenben.android.common.widget.DialogUtils;
import com.mujirenben.android.common.widget.dialog.RxDialog;
import com.mujirenben.android.mine.di.component.DaggerWithdrawComponent;
import com.mujirenben.android.mine.di.module.WithdrawModule;
import com.mujirenben.android.mine.mvp.contract.WithDrawContract;
import com.mujirenben.android.mine.mvp.model.bean.AccountInfoBean;
import com.mujirenben.android.mine.mvp.model.bean.MineConstant;
import com.mujirenben.android.mine.mvp.model.bean.VerifyCodeBean;
import com.mujirenben.android.mine.mvp.presenter.WithdrawPresenter;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;


@Route(path = ARouterPaths.WITHDRAW_DEPOSIT)
public class WithdrawDepositActivity extends BaseActivity<WithdrawPresenter> implements WithDrawContract.View {

    private ImageView tvBack;
    private TextView tvTitlebarRight;
    private RelativeLayout llExplantion;
    private TextView tvMarquee;
    private ImageView ivCloseEllipsizeNotice;
    private RelativeLayout withdrawUserInfoRl;
    private RCImageView withdrawTypeIconIv;
    private TextView withdrawUserAccountTv;
    private TextView withdrawUserNameTv;
    private TextView tv;
    private EditText etPaycount;
    private TextView tvWithdrawAll;
    private TextView withdrawNoticeTv;
    private TextView tvWithdrawOperation;
    private TextView withdrawDesTv;
    /**账户姓名布局*/
    private View phoneNameLl;
    /**填写信息提示*/
    private View withdrawInputDesTv;
    /**可提现金额*/
    private double canWithdraw;
    /**支付方式*/
    private int payWay;
    private Disposable disposable;
    private  AccountInfoBean.DataBean tempAccountInfo;
    private final int WITHDRAW_DETAIL_CODE = 103;
    /**倒计时*/
    private int countDownSec = 60;
    private Disposable verifyDisposable;
    /**提现手机号*/
    private String withdrawAccount;
    /**提现姓名*/
    private String withdrawName;
    /**身份证正面照*/
    private String withdrawFrontImgURL;
    /**身份证反面照*/
    private String withdrawOppositeImgURL;
    private DialogUtils dialog;
    /**
     * 直推人
     * 0：表示没有直推人
     * 非0：表示有直推人
     */
    private int relId = 0;
    private RxDialog inviteDialog;
    private RxDialog verifyDialog;
    private String finalPayCount;
    private String withdrawAmount;
    /**是否已经实名校验，0没校验，1已校验*/
    private int identityValidate;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWithdrawComponent.builder()
                .appComponent(appComponent)
                .withdrawModule(new WithdrawModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
//        StatusBarUtil.setStatusBarWhite(this);
        if (SpUtil.getIsMIUI(this)){
            StatusBarUtil.getStatusBarHelper(this,R.color.white).into(this);
        }else {
            StatusBarUtil.setStatusBarWhite(this);
        }
        return R.layout.mine_withdraw_deposit_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initViewUI();
        initClickEvent();
        getAccountData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        if (disposable != null){
            disposable.dispose();
        }
        if(verifyDisposable != null){
            verifyDisposable.dispose();
        }
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initViewUI() {
        String canWithdrawStr = LoginDataManager.getsInstance(this).getCanWithdrawCash();
        if(!TextUtils.isEmpty(canWithdrawStr)){
            canWithdraw = Double.parseDouble(canWithdrawStr);
        }
        dialog = new DialogUtils(this,R.layout.common_loading_toast,"");
        tvBack = findViewById(R.id.tv_back);
        phoneNameLl = findViewById(R.id.withdraw_phone_name_ll);
        withdrawInputDesTv = findViewById(R.id.withdraw_input_des_tv);
        tvTitlebarRight = findViewById(R.id.tv_titlebar_right);
        llExplantion =  findViewById(R.id.ll_explantion);
        tvMarquee =  findViewById(R.id.tv_marquee);
        withdrawUserInfoRl = findViewById(R.id.withdraw_user_info_rl);
        withdrawTypeIconIv = findViewById(R.id.withdraw_type_icon_iv);
        withdrawUserAccountTv = findViewById(R.id.withdraw_user_account);
        withdrawUserNameTv = findViewById(R.id.withdraw_user_name);
        withdrawDesTv = findViewById(R.id.withdraw_des_tv);
        etPaycount = findViewById(R.id.et_paycount);
        tvWithdrawAll = findViewById(R.id.tv_withdraw_all);
        withdrawNoticeTv = findViewById(R.id.withdraw_notice_tv);
        tvWithdrawOperation = findViewById(R.id.tv_withdraw_operation);

        // 响应点击事件的话必须设置以下属性
        withdrawNoticeTv.setMovementMethod(LinkMovementMethod.getInstance());
        RxTextTool.getBuilder("1.单笔限额为800元；\n", this)
                .append("2.手动转账到")
                .append("支付宝账号")
                .setForegroundColor(this.getResources().getColor(R.color.main_color))
                .append("时务必核对支付宝账号与姓名，点击查看")
                .append("如何提交正确的支付宝教程\n")
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        HrzRouter.getsInstance(WithdrawDepositActivity.this)
                                .navigation(Consts.getHrzRouterUrl(Consts.HRZ_WITHDRAW_ALIPAY_URL,"支付宝提现教程"));
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(getResources().getColor(R.color.main_color));
                    }
                })
                .setForegroundColor(this.getResources().getColor(R.color.main_color))
                .setUnderline()
                .append("3.使用微信提现到微信钱包处查看提现金额，点击查看")
                .append("微信钱包零钱教程\n")
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        HrzRouter.getsInstance(WithdrawDepositActivity.this)
                                .navigation(Consts.getHrzRouterUrl(Consts.HRZ_WITHDRAW_WX_URL,"微信提现教程"));
                    }
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(getResources().getColor(R.color.main_color));
                    }
                })
                .setForegroundColor(this.getResources().getColor(R.color.main_color))
                .setUnderline()
                .append("4.关于手续费与更多信息详见")
                .append("提现说明")
                .setUnderline()
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        HrzRouter.getsInstance(WithdrawDepositActivity.this)
                                .navigation(Consts.getHrzRouterUrl(Consts.HRZ_WITHDRAW_DES_URL,"提现说明"));
                    }
                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(getResources().getColor(R.color.main_color));
                    }
                })
                .setForegroundColor(this.getResources().getColor(R.color.main_color))
                .into(withdrawNoticeTv);
        tvMarquee.setSelected(true);
    }

    /**
     * 点击监听事件
     */
    private void initClickEvent() {
        tvBack.setOnClickListener(v -> finish());
        tvTitlebarRight.setOnClickListener(v ->
                ARouter.getInstance().build(ARouterPaths.WITHDRAW_RECORD).navigation(this)
        );
        llExplantion.setOnClickListener(v -> llExplantion.setVisibility(View.GONE));
        tvWithdrawAll.setOnClickListener(v -> {
            etPaycount.setText(canWithdraw+"");
            etPaycount.setSelection(etPaycount.getText().length());
        });
        withdrawUserInfoRl.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(MineConstant.ACCOUNT_INFO_KEY, tempAccountInfo);
            ARouter.getInstance()
                    .build(ARouterPaths.WITHDRAW_DETAIL)
                    .withBundle(MineConstant.ACCOUNT_INFO_KEY, bundle)
                    .navigation(this,WITHDRAW_DETAIL_CODE);
        });
        tvWithdrawOperation.setOnClickListener(v -> {
            if(TextUtils.isEmpty(withdrawName) || TextUtils.isEmpty(withdrawAccount)){
                ArmsUtils.makeText(WithdrawDepositActivity.this,"请填写完整提现信息");
                return;
            }
            if(TextUtils.isEmpty(etPaycount.getText().toString())){
                ArmsUtils.makeText(WithdrawDepositActivity.this,"提现金额不能为空");
                return;
            }
            String amount = etPaycount.getText().toString();
            Double fAmount = Double.parseDouble(amount);
            if (fAmount == 0){
                ArmsUtils.makeText(this,"提现金额不能为零");
                tvWithdrawOperation.setBackgroundColor(Color.parseColor("#FFB1B1B1"));
                return;
            }
            if(fAmount > canWithdraw){
                ArmsUtils.makeText(this,"输入金额超过可提现余额");
                tvWithdrawOperation.setBackgroundColor(Color.parseColor("#FFB1B1B1"));
                return;
            }
            if(fAmount < 10 ){
                ArmsUtils.makeText(this,"提现金额需大于10元");
                tvWithdrawOperation.setBackgroundColor(Color.parseColor("#FFB1B1B1"));
                return;
            }
            if(relId == 0){
                //没有直推人，则走绑定页面
                showInviteQrCodeDialog();
                return;
            }

            if(relId != 0 && identityValidate == 1){
                //有推荐人，而且已经实名认证通过
                showVerifyCodeDialog();
            }else if (relId !=0 && identityValidate == 0){
                //有推荐人，而且没有实名认证通过
                ArmsUtils.makeText(this,"实名认证通过之后才能体现哦~");
            }
        });
        etPaycount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //这部分是处理如果输入框内小数点后有俩位，那么舍弃最后一位赋值，光标移动到最后
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") >2) {
                        etPaycount.setText(s.toString().subSequence(0, s.toString().indexOf(".") +3));
                        etPaycount.setSelection(s.toString().trim().length()-1
                        );
                    }
                }
                //这部分是处理如果用户输入以.开头，在前面加上0
                if (s.toString().trim().substring(0).equals(".")) {
                    etPaycount.setText("0"+s);
                    etPaycount.setSelection(2);
                }
                //这里处理用户 多次输入.的处理 比如输入 1..6的形式，是不可以的
                if (s.toString().startsWith("0")&& s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        etPaycount.setText(s.subSequence(0, 1));
                        etPaycount.setSelection(1);
                        return;
                    }
                }
                String payCount = etPaycount.getText().toString();
                if(!TextUtils.isEmpty(payCount)&& payCount.startsWith("-")){
                    ArmsUtils.makeText(WithdrawDepositActivity.this,"提现金额不能低于10元");
                    tvWithdrawOperation.setBackgroundColor(Color.parseColor("#FFB1B1B1"));
                    return;
                }
                if(!TextUtils.isEmpty(payCount)){
                    dealWithDecimals(payCount);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                etPaycount.setSelection(etPaycount.getText().length());
            }
        });
    }

    /**
     * 小数处理
     * @param s
     */
    private void dealWithDecimals(String s) {
        if(s.toCharArray().length > 0){
            Double payCount = Double.parseDouble(s.toString());
            //大于可提现金额
            if(payCount > canWithdraw){
                ArmsUtils.makeText(this,"输入金额超过可提现余额");
                tvWithdrawOperation.setBackgroundColor(Color.parseColor("#FFB1B1B1"));
            }else if(payCount < 10){
                ArmsUtils.makeText(this,"提现金额不能低于10元");
                tvWithdrawOperation.setBackgroundColor(Color.parseColor("#FFB1B1B1"));
            } else{
                tvWithdrawOperation.setBackground(getResources().getDrawable(R.drawable.gradient_main_color));
            }
            if(payCount > 800){
                etPaycount.setText(800+"");
                ArmsUtils.makeText(this,"单笔提现不能超过800元哦~");
                payCount = 800d;
            }
            String deductCount = payCount*0.06+"";
            String[] payArray = deductCount.split(Pattern.quote("."));
            //小数部分
            String decimalPart = payArray[1];
            //获取整数部分
            int integerPart = Integer.parseInt(payArray[0]);
            String newDecimalPart="";
            if(decimalPart.toCharArray().length == 0){
                newDecimalPart = decimalPart+"00";
            }else if(decimalPart.toCharArray().length == 1){
                newDecimalPart = decimalPart+"0";
            }else if(decimalPart.toCharArray().length == 2) {
                newDecimalPart = decimalPart;
            }else {
                String oneDecimalStr = decimalPart.substring(0,1);
                String secondDecimalStr = decimalPart.substring(1,2);
                String thirdDecimalStr = decimalPart.substring(2,3);
                int oneDecimal = Integer.parseInt(oneDecimalStr);
                int secondDecimal = Integer.parseInt(secondDecimalStr);
                int thirdDecimal = Integer.parseInt(thirdDecimalStr);
                if(thirdDecimal != 0){
                    secondDecimal++;
                    if(secondDecimal >= 10){
                        secondDecimal = 0;
                        oneDecimal++;
                        if(oneDecimal >= 10){
                            oneDecimal = 0;
                            integerPart++;
                        }
                    }
                    newDecimalPart = oneDecimal+""+    secondDecimal+"";
                }
            }
            finalPayCount = integerPart+"."+newDecimalPart;
            withdrawDesTv.setText("额外扣除￥"+finalPayCount+"税费（费率6%）");
        }else {
            String deductText = getResources().getString(R.string.mine_withdraw_deposit_hint);
            withdrawDesTv.setText(deductText);
        }
    }

    /**
     * 申请提现
     * @param verifyCode
     */
    private void applyWithdraw(String verifyCode) {
        //支付宝支付
        payWay = 0;
        String outTradeCode = HttpParamUtil.getNonceStr();
        HashMap<String, String> map = new HashMap<>();
        map.put("amount", withdrawAmount);
        map.put("way", payWay + "");
        map.put("validateCode", verifyCode);
        map.put("outTradeCode", outTradeCode);
        map.put("refId",relId+"");
        RetrofitUrlManager.getInstance().putDomain("mock_app_withdraw", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        HashMap<String, String> withdrawMap = HttpParamUtil.getCommonSignParamMap(this, map);
        mPresenter.applyWithdraw(withdrawMap);
    }

    /**
     * 获取账号信息
     */
    private void getAccountData() {

        RetrofitUrlManager.getInstance().putDomain("mock_app_withdraw_info", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        HashMap<String, String> map = HttpParamUtil.getCommonSignParamMap(this, null);
        if (mPresenter != null) {
            mPresenter.getWithdrawInfo(map);
        }
    }

    /**
     * 判断账号信息是否为空
     * 1）不为空的情况，显示支付的类型icon（支付宝，微信）、账号、姓名
     * 2）为空的情况，显示：请填写提现账号信息
     * @param accountInfoBean
     */
    @Override
    public void showAccountInfo(AccountInfoBean.DataBean accountInfoBean) {
        if (accountInfoBean != null){
            tempAccountInfo = accountInfoBean;
            relId = tempAccountInfo.getRelId();
            identityValidate = accountInfoBean.getIdentityValidate();
            phoneNameLl.setVisibility(View.VISIBLE);
            withdrawInputDesTv.setVisibility(View.GONE);
            withdrawTypeIconIv.setVisibility(View.VISIBLE);
            withdrawAccount = accountInfoBean.getAlipayAcount();
            withdrawName = accountInfoBean.getRealName();
            withdrawUserAccountTv.setText(withdrawAccount);
            withdrawUserNameTv.setText(withdrawName);
            if(TextUtils.isEmpty(withdrawAccount) || TextUtils.isEmpty(withdrawName)){
                withdrawTypeIconIv.setVisibility(View.GONE);
                phoneNameLl.setVisibility(View.GONE);
                withdrawInputDesTv.setVisibility(View.VISIBLE);
            }
        }else {
            withdrawTypeIconIv.setVisibility(View.GONE);
            phoneNameLl.setVisibility(View.GONE);
            withdrawInputDesTv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 账户信息出错
     * @param errorMsg
     */
    @Override
    public void showAccountInfoError(String errorMsg) {
        tempAccountInfo = new AccountInfoBean.DataBean();
        ArmsUtils.makeText(this,errorMsg);
    }

    /**
     * 提现申请成功
     */
    @Override
    public void withdrawOnSuccess() {
        verifyDialog.dismiss();
        String currentCashStr = LoginDataManager.getsInstance(this).getCanWithdrawCash();
        if(!TextUtils.isEmpty(currentCashStr)){
            Double currentCash = Double.parseDouble(currentCashStr);
            Double withdrawCash = Double.parseDouble(withdrawAmount);
            //剩余的额度 = 当前额度 - 提现的额度
            Double remainCash = currentCash - withdrawCash;
            LoginDataManager.getsInstance(this).setCanWithdrawCash(remainCash+"");
        }

        ARouter.getInstance()
                .build(ARouterPaths.WITHDRAW_SUCCESS_ACTIVITY)
                .withString(MineConstant.WITHDRAW_AMOUNT_KEY,withdrawAmount)
                .withString(MineConstant.TAX_TOTAL_KEY,finalPayCount)
                .withString(MineConstant.WITHDRAW_ACCOUNT_KEY,"支付宝账号:"+withdrawAccount)
                .navigation(this);
    }

    /**
     * 提现申请失败
     * @param errorMsg
     */
    @Override
    public void withdrawOnFail(String errorMsg) {
        ArmsUtils.makeText(this, errorMsg);
    }

    /**
     * 锁粉成功
     * @param msg
     */
    @Override
    public void lockFanOnSuccess(String msg) {
        ArmsUtils.makeText(this,msg);
        //赋值推荐人id,非零表示已经有推荐人（已经跟服务端对过，不要纠结hard code问题）
        relId = 10086;
        inviteDialog.dismiss();
    }

    /**
     * 锁粉失败
     * @param msg
     */
    @Override
    public void lockFanOnFail(String msg) {
        ArmsUtils.makeText(this,msg);
    }

    /**
     * 发送验证码成功
     * @param verifyCodeBean
     */
    @Override
    public void verifyCodeOnSuccess(VerifyCodeBean verifyCodeBean) {
        ArmsUtils.makeText(this,"短信验证发送成功");
    }

    /**
     * 发送验证码失败
     * @param msg
     */
    @Override
    public void verifyCodeOnFail(String msg) {
        ArmsUtils.makeText(this,msg);
    }

    /**
     * 显示进度
     */
    @Override
    public void showLoading() {
        if(dialog != null){
            dialog.show();
        }
    }

    /**
     * 隐藏进度
     */
    @Override
    public void hideLoading() {
        if(dialog != null){
            dialog.hide();
        }
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

    /**
     * 弹出邀请人二维码dialog
     */
    public void showInviteQrCodeDialog(){
        View dialogView = LayoutInflater.from(this).inflate(R.layout.invite_qrcode_dialog_layout,null);
        TextView noticeTv = dialogView.findViewById(R.id.invite_dialog_notice_tv);
        EditText inviteCodeEt= dialogView.findViewById(R.id.invite_dialog_code_et);
        ImageView inviteQrCodeIv =  dialogView.findViewById(R.id.invite_dialog_qrcode_iv);
        TextView okayTv =  dialogView.findViewById(R.id.invite_dialog_okay_tv);
        TextView scanTv = dialogView.findViewById(R.id.withdraw_scan_title);
        inviteDialog = new RxDialog(this);
        inviteDialog.setContentView(dialogView);
        inviteDialog.show();
        okayTv.setEnabled(false);
        RxTextTool.getBuilder("保存二维码，扫一扫图片",this)
                .setUnderline()
                .into(scanTv);
        RxTextTool.getBuilder("1.保存下方二维码图片\n",this)
                .append("2.打开微信，通过微信扫一扫图片\n")
                .append("3.识别图中的二维码添加好友，将会获取【提现验证码】，同时可获取价值")
                .append("100元的皇冠升级抵扣券")
                .setForegroundColor(Color.parseColor("#FFED4143"))
                .into(noticeTv);
        Glide.with(this).load(Consts.HRZ_INVITE_QR_CODE_URL).into(inviteQrCodeIv);
        okayTv.setOnClickListener(v -> {
            String inviteCode = inviteCodeEt.getText().toString();
            applyLockFan(inviteCode);
        });
        inviteCodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s) && s.length() > 0){
                    okayTv.setEnabled(true);
                    okayTv.setBackground(getResources().getDrawable(R.drawable.invite_commit_btn_shape));
                }else {
                    okayTv.setEnabled(false);
                    okayTv.setBackground(getResources().getDrawable(R.drawable.deep_gray_round_shape_bg));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 手机验证码dialog
     */
    public void showVerifyCodeDialog(){
        View verifyDialogView = LayoutInflater.from(this).inflate(R.layout.withdraw_verify_code_layout,null);
        EditText phoneEt =  verifyDialogView.findViewById(R.id.withdraw_verify_dialog_phone_et);
        ImageView etClearIv =  verifyDialogView.findViewById(R.id.withdraw_verify_dialog_et_clear_iv);
        EditText verifyCodeEt =  verifyDialogView.findViewById(R.id.withdraw_verify_dialog_verify_et);
        TextView countDownTV =  verifyDialogView.findViewById(R.id.withdraw_verify_dialog_count_down_tv);
        TextView inviteDialogOkayTv =  verifyDialogView.findViewById(R.id.withdraw_verify_dialog_okay_tv);
        verifyDialog = new RxDialog(this);
        verifyDialog.setContentView(verifyDialogView);
        verifyDialog.show();

        countDownTV.setEnabled(true);
        countDownTV.setTextColor(Color.WHITE);
        countDownTV.setBackground(getResources().getDrawable(R.drawable.yellow_gradient_round_shape_bg));
        inviteDialogOkayTv.setEnabled(false);
        //etClearIv.setOnClickListener(v -> phoneEt.setText(""));
        String phone = LoginDataManager.getsInstance(this).getPhone();
        phoneEt.setText(phone);
        phoneEt.setFocusable(false);
        etClearIv.setVisibility(View.GONE);
        countDownTV.setOnClickListener(v ->{
            getVerifyCode();
            countDownVerify(countDownTV);
        });

        inviteDialogOkayTv.setOnClickListener(v -> {
            String verifyCode = verifyCodeEt.getText().toString();
            applyWithdraw(verifyCode);
        });
        verifyCodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    inviteDialogOkayTv.setEnabled(true);
                    inviteDialogOkayTv.setBackground(getResources().getDrawable(R.drawable.invite_commit_btn_shape));
                }else {
                    inviteDialogOkayTv.setEnabled(false);
                    inviteDialogOkayTv.setBackground(getResources().getDrawable(R.drawable.deep_gray_round_shape_bg));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 绑定邀请人
     * @param inviteCode
     */
    private void applyLockFan(String inviteCode){
        RetrofitUrlManager.getInstance().putDomain("mock_app_lock_fan", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("inviteCode",inviteCode);
        HashMap<String, String> map = HttpParamUtil.getCommonSignParamMap(this, hashMap);
        if (mPresenter != null) {
            mPresenter.applyLockFan(map);
        }
    }

    /**
     * 获取手机验证码
     */
    private void getVerifyCode() {
        if(mPresenter != null){
            withdrawAmount = etPaycount.getText().toString();
            RetrofitUrlManager.getInstance().putDomain("mock_app_send_verify", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
            HashMap<String,String> map = new HashMap<>();
            map.put("money",withdrawAmount);
            HashMap<String,String> hashMap = HttpParamUtil.getCommonSignParamMap(this,map);
            mPresenter.sendPhoneVerify(hashMap);
        }
    }

    /**
     * 倒计时
     * @param countDownTV
     */
    private void countDownVerify(TextView countDownTV) {
       countDownSec = 60;
       verifyDisposable =  Flowable.interval(1000, TimeUnit.MILLISECONDS)
                .onBackpressureDrop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    countDownSec -- ;
                    if(countDownSec == 0){
                        countDownTV.setEnabled(true);
                        countDownTV.setText("重发验证码");
                        countDownTV.setBackground(getResources().getDrawable(R.drawable.yellow_gradient_round_shape_bg));
                        verifyDisposable.dispose();
                    }else {
                        countDownTV.setEnabled(false);
                        countDownTV.setText(countDownSec+"s");
                        countDownTV.setBackground(getResources().getDrawable(R.drawable.deep_gray_round_shape_bg));
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == WITHDRAW_DETAIL_CODE){
            Log.e("onActivityResult","WITHDRAW_DETAIL_CODE: "+WITHDRAW_DETAIL_CODE);
            if(data != null){
                tempAccountInfo = data.getParcelableExtra(MineConstant.ACCOUNT_INFO_KEY);
                if(tempAccountInfo != null){
                    showAccountInfo(tempAccountInfo);
                }
            }
        }
    }
}
