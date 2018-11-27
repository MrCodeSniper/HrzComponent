package com.mujirenben.android.mine.mvp.ui.activity;

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
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.rxtools.RxTextTool;
import com.mujirenben.android.mine.mvp.model.bean.MineConstant;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import java.util.regex.Pattern;

import butterknife.BindView;

import static com.mujirenben.android.common.constants.Consts.PHONE_NUMBER_REG;

/**
 * @author: panrongfu
 * @date: 2018/8/22 12:58
 * @describe:
 */
@Route(path = ARouterPaths.BIND_PHONE_ACTIVITY)
public class BindPhoneActivity extends BaseActivity {

    @BindView(R2.id.login_bind_phone_title)
    TextView loginBindPhoneTitle;
    @BindView(R2.id.iv_close)
    ImageView ivClose;
    @BindView(R2.id.et_mobile)
    EditText etMobile;
    @BindView(R2.id.ll_input)
    LinearLayout llInput;
    @BindView(R2.id.ll_law_desc)
    TextView llLawDesc;
    @BindView(R2.id.tv_login_agreement)
    TextView tvLoginAgreement;
    @BindView(R2.id.iv_confirm_phone_login)
    TextView ivConfirmPhoneLogin;
    @BindView(R2.id.ll_agreement_login)
    LinearLayout llAgreementLogin;
    @BindView(R2.id.ll_login_way_phone)
    RelativeLayout llLoginWayPhone;
    @BindView(R2.id.rl_login_bg)
    RelativeLayout rlLoginBg;
    private String phone_num_saved;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        return R.layout.mine_bind_phone_acitivity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initViewUI();
        showSoftInputFromWindow();
    }

    private void initViewUI() {
        if (getIntent() != null && getIntent().getStringExtra("user_phone") != null) {
            phone_num_saved = getIntent().getStringExtra("user_phone");
            etMobile.setText(phone_num_saved);
        }

        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged 被执行----> s=" + s+"----start="+ start
                        + "----after="+after + "----count" +count);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 1) {
                    llAgreementLogin.setVisibility(View.VISIBLE);
                } else {
                    llAgreementLogin.setVisibility(View.GONE);
                    return;
                }
                com.orhanobut.logger.Logger.e(s.toString()+"***"+start+"***"+before+"***"+count);
                if (count != 0) {
                    int length = s.length() + 1;
                    if (length % 4 == 0 && length < 5) {
                        etMobile.setText(s + " ");
                        etMobile.setSelection(length);
                    } else if (length % 9 == 0 && length < 14 && length > 5) {
                        etMobile.setText(s + " ");
                        etMobile.setSelection(length);
                    }
                }else {//删除情况

                    if(s.length()==3||s.length()==8){
                        etMobile.setText(s.subSequence(0,s.length()-1));
                        etMobile.setSelection(s.length()-1);
                    }

                }




            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged 被执行---->" + s);
            }
        });
        ivConfirmPhoneLogin.setOnClickListener(v -> {
            String phoneNumber = etMobile.getText().toString();
            if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 11) {
                ArmsUtils.makeText(BindPhoneActivity.this,"请输入11位的手机号码");
                return;
            }
//            boolean isPhoneNumber = isPhoneNumber(phoneNumber.trim().replaceAll("\\s*", ""));
//            if (!isPhoneNumber) {
//                ArmsUtils.makeText(BindPhoneActivity.this,"请输入正确的手机号码");
//                return;
//            }

            ARouter.getInstance().build(ARouterPaths.SMS_CODE_VERTIFY)
                    //etMobile.getText().toString().trim().replaceAll("\\s*", "")
                    .withString(MineConstant.TITLE_KEY,"更改手机")
                    .withBoolean(MineConstant.PHONE_HAD_BEEN_BIND,false)
                    .withString(MineConstant.FROM_KEY,MineConstant.BIND_PAGE_ACTIVITY)
                    .withString(MineConstant.PHONE_KEY, etMobile.getText().toString())
                    .withTransition(R.anim.slide_in_right, R.anim.slide_out_left)//跳转到的界面动画，自己退场动画
                    .navigation(this,200);
            BindPhoneActivity.this.finish();
        });
        ivClose.setOnClickListener( v -> finish());
        // 响应点击事件的话必须设置以下属性
        tvLoginAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        RxTextTool.getBuilder("继续表示您同意", this)
                .append("红人装用户协议")
                .setUnderline()
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        HrzRouter.getsInstance(BindPhoneActivity.this).navigation(Consts.getHrzRouterUrl(Consts.HRZ_USER_AGREE_URL,"红人装用户协议"));
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(getResources().getColor(R.color.argeement_color));
                    }
                })
                .into(tvLoginAgreement);
    }

    public boolean isPhoneNumber(String mobiles) {
        boolean match = Pattern.matches(PHONE_NUMBER_REG, mobiles);
        return match;
    }



    public void showSoftInputFromWindow() {
        etMobile.setFocusable(true);
        etMobile.setFocusableInTouchMode(true);
        etMobile.requestFocus();

        InputMethodManager inputManager =
                (InputMethodManager)etMobile.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(etMobile, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MineConstant.SMS_CODE_VERIFY_REQUEST_CODE){
            BindPhoneActivity.this.finish();
        }
    }
}
