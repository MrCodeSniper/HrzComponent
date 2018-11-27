package com.mujirenben.android.mine.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.widget.DialogUtils;
import com.mujirenben.android.mine.di.component.DaggerWithdrawDetailComponent;
import com.mujirenben.android.mine.di.module.WithdrawDetailModule;
import com.mujirenben.android.mine.mvp.contract.WithdrawDetailContract;
import com.mujirenben.android.mine.mvp.model.bean.AccountInfoBean;
import com.mujirenben.android.mine.mvp.model.bean.MineConstant;
import com.mujirenben.android.mine.mvp.presenter.WithdrawDetailPresenter;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

import static com.mujirenben.android.common.util.Preconditions.checkNotNull;

@Route(path = ARouterPaths.WITHDRAW_DETAIL)
public class WithdrawDetailActivity extends BaseActivity<WithdrawDetailPresenter> implements WithdrawDetailContract.View {

    @BindView(R2.id.tv_back)
    ImageView tvBack;
    @BindView(R2.id.tv_titlebar)
    TextView tvTitlebar;
    @BindView(R2.id.common_titlebar_right_iv)
    ImageView commonTitlebarRightIv;
    @BindView(R2.id.common_titlebar_right_tv)
    TextView titlebarRightTv;
    @BindView(R2.id.rl_titlebar)
    RelativeLayout rlTitlebar;
    @BindView(R2.id.withdraw_alipay_tv)
    TextView withdrawAlipayTv;
    @BindView(R2.id.withdraw_alipay_ll)
    LinearLayout withdrawAlipayLl;
    @BindView(R2.id.iv_award)
    ImageView ivAward;
    @BindView(R2.id.withdraw_wxpay_tv)
    TextView withdrawWxpayTv;
    @BindView(R2.id.withdraw_wxpay_ll)
    LinearLayout withdrawWxpayLl;
    @BindView(R2.id.tv_alipay_account)
    TextView tvAlipayAccount;
    @BindView(R2.id.et_alipay_account)
    EditText etAlipayAccount;
    @BindView(R2.id.tv_alipay_account_name)
    TextView tvAlipayAccountName;
    @BindView(R2.id.et_alipay_realname)
    EditText etAlipayRealname;
    @BindView(R2.id.et_idcard_number)
    EditText etIdcardNumber;
    @BindView(R2.id.withdraw_save_tv)
    TextView withdrawSaveTv;
    private int idCardType;
    private AccountInfoBean.DataBean accountInfo;
    private String idNumber;
    private Disposable disposable;
    private static final int PHOTO_REQUEST_GALLERY = 2;
    private DialogUtils dialog;
    private boolean isIdentify = false;
    private int HAD_IDENDTIFY = 1;
    private int NO_IDENDTIFY = 0;
    private int identityType = 0;
    private final int WITHDRAW_DETAIL_REQUEST_CODE = 103;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWithdrawDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .withdrawDetailModule(new WithdrawDetailModule(this))
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
        return R.layout.activity_withdraw_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initViewData();
        showAccountInfo();
    }

    private void initViewData() {
        String title = getResources().getString(R.string.withdraw_detail_title_text);
        tvTitlebar.setText(title);
        dialog = new DialogUtils(this, R.layout.common_loading_toast, "");
        etAlipayRealname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    String chineseregEx = "^[\\u4e00-\\u9fa5]*$";
                    String realName = etAlipayRealname.getText().toString();
                    String newRealName = realName;
                    if(realName.contains("·")){
                        newRealName = realName.replace("·","");
                    }

                    if (!newRealName.matches(chineseregEx)) {
                        ArmsUtils.makeText(WithdrawDetailActivity.this, "非法的用户名，请重新输入");
                        etAlipayRealname.setText("");
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        judgeEditTextNotEmpty();
    }

    /**
     * 账户信息
     */
    private void showAccountInfo() {
        Bundle bundle = getIntent().getBundleExtra(MineConstant.ACCOUNT_INFO_KEY);
        accountInfo = bundle.getParcelable(MineConstant.ACCOUNT_INFO_KEY);
        if (accountInfo != null) {
            String alipayAccount = accountInfo.getAlipayAcount();
            String realName = accountInfo.getRealName();
            String idNumber = accountInfo.getIdNumber();
            etAlipayAccount.setText(alipayAccount);
            etAlipayRealname.setText(realName);
            etIdcardNumber.setText(idNumber+"");
            identityType = accountInfo.getIdentityValidate();
        }

        if (identityType == NO_IDENDTIFY) {
            //未认证
            titlebarRightTv.setVisibility(View.INVISIBLE);
            etIdcardNumber.setFocusable(true);
            etIdcardNumber.setFocusableInTouchMode(true);
            etAlipayAccount.setFocusable(true);
            etAlipayAccount.setFocusableInTouchMode(true);
            etAlipayRealname.setFocusable(true);
            etAlipayRealname.setFocusableInTouchMode(true);
        } else if (identityType == HAD_IDENDTIFY) {
            //已认证
            etAlipayAccount.setFocusable(false);
            etAlipayRealname.setFocusable(false);
            etIdcardNumber.setFocusable(false);
            withdrawSaveTv.setVisibility(View.GONE);
            titlebarRightTv.setVisibility(View.VISIBLE);
            titlebarRightTv.setText("编辑");
            titlebarRightTv.setTextColor(getResources().getColor(R.color.text_color));
        }
    }

    /**
     * 判断输入的信息如果有空的，保存按键则显示灰色
     */
    private void judgeEditTextNotEmpty() {
        disposable = Flowable.interval(100, TimeUnit.MILLISECONDS)
                .onBackpressureDrop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (etIdcardNumber!= null&&etAlipayRealname != null && etAlipayAccount != null) {
                        String realName = etAlipayRealname.getText().toString();
                        String alipayAccount = etAlipayAccount.getText().toString();
                        String idNumber = etIdcardNumber.getText().toString();
                        if (!TextUtils.isEmpty(realName) &&
                                !TextUtils.isEmpty(alipayAccount)&& !TextUtils.isEmpty(idNumber)) {
                            withdrawSaveTv.setSelected(true);
                            withdrawSaveTv.setEnabled(true);
                        } else {
                            withdrawSaveTv.setSelected(false);
                            withdrawSaveTv.setEnabled(false);
                        }
                    }
                });
    }

    @Override
    public void showLoading() {
        if (dialog != null) {
            dialog.show();
        }
    }

    @Override
    public void hideLoading() {
        withdrawSaveTv.setEnabled(true);
        if (dialog != null) {
            dialog.hide();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        ArmsUtils.makeText(this, message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    protected void onStop() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onStop();
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void uploadIdPicOnSuccess(String uploadPicURL) {
    }

    @Override
    public void uploadIdPicOnFail(String errorMsg) {
        ArmsUtils.makeText(this, errorMsg);
    }

    /**
     * 更新账户信息成功
     */
    @Override
    public void updateAccountOnSuccess() {
        isIdentify = true;
        ArmsUtils.makeText(this, "更新账户信息成功");
        titlebarRightTv.setVisibility(View.VISIBLE);
        titlebarRightTv.setText("编辑");
        titlebarRightTv.setTextColor(getResources().getColor(R.color.text_color));
        withdrawSaveTv.setVisibility(View.INVISIBLE);
        //保存信息
        String alipayAccount = etAlipayAccount.getText().toString();
        accountInfo.setAlipayAcount(alipayAccount);
        String realName = etAlipayRealname.getText().toString();
        String idNumber = etIdcardNumber.getText().toString();
        accountInfo.setIdNumber(idNumber);
        accountInfo.setRealName(realName);
        //设置为1已校验
        accountInfo.setIdentityValidate(1);
    }

    /**
     * 更新账户信息失败
     * @param errorMsg
     */
    @Override
    public void updateAccountOnFail(String errorMsg) {
        ArmsUtils.makeText(this, errorMsg);
    }

    @OnClick({R2.id.tv_back, R2.id.common_titlebar_right_tv,R2.id.withdraw_save_tv})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tv_back) {
            Intent intent = new Intent();
            intent.putExtra(MineConstant.ACCOUNT_INFO_KEY, accountInfo);
            setResult(WITHDRAW_DETAIL_REQUEST_CODE, intent);
            finish();
        } else if (id == R.id.common_titlebar_right_tv) {
            String text = titlebarRightTv.getText().toString();
            switch (text){
                case "编辑":
                    etIdcardNumber.setFocusable(true);
                    etIdcardNumber.setFocusableInTouchMode(true);
                    etAlipayAccount.setFocusable(true);
                    etAlipayAccount.setFocusableInTouchMode(true);
                    etAlipayRealname.setFocusable(true);
                    etAlipayRealname.setFocusableInTouchMode(true);
                    titlebarRightTv.setText("取消");
                    titlebarRightTv.setTextColor(getResources().getColor(R.color.text_color));
                    withdrawSaveTv.setVisibility(View.VISIBLE);
                    break;
                case "取消":
                    titlebarRightTv.setText("编辑");
                    etIdcardNumber.setFocusable(false);
                    etAlipayAccount.setFocusable(false);
                    etAlipayRealname.setFocusable(false);
                    titlebarRightTv.setTextColor(getResources().getColor(R.color.text_color));
                    withdrawSaveTv.setVisibility(View.INVISIBLE);
                    break;
            }

        }  else if (id == R.id.withdraw_save_tv) {
            updateAccountInfo();
        }
        hideInputMethod();
    }

    /**
     * 更新账户信息
     */
    private void updateAccountInfo() {
        withdrawSaveTv.setEnabled(false);
        String realName = etAlipayRealname.getText().toString();
        String alipayAccount = etAlipayAccount.getText().toString();
        String idNumber = etIdcardNumber.getText().toString();
        HashMap<String, String> map = new HashMap<>();
        map.put("realName", realName);
        map.put("alipayAcount", alipayAccount);
        map.put("idNumber", idNumber);
        RetrofitUrlManager.getInstance().putDomain("mock_app_update_withdraw_info", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        HashMap<String, String> updateMap = HttpParamUtil.getCommonSignParamMap(this, map);
        mPresenter.updateWithDrawInfo(updateMap);
    }

    private void hideInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etIdcardNumber.getWindowToken(), 0); //强制隐藏键盘
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(MineConstant.ACCOUNT_INFO_KEY, accountInfo);
        setResult(WITHDRAW_DETAIL_REQUEST_CODE, intent);
        finish();

    }
}
