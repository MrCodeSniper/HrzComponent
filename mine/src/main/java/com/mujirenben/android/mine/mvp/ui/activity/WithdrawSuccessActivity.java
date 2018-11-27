package com.mujirenben.android.mine.mvp.ui.activity;

/**
 * @author: panrongfu
 * @date: 2018/8/20 17:21
 * @describe:
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.mine.mvp.model.bean.MineConstant;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterPaths.WITHDRAW_SUCCESS_ACTIVITY)
public class WithdrawSuccessActivity extends BaseActivity {

    @BindView(R2.id.tv_back)
    ImageView tvBack;
    @BindView(R2.id.tv_titlebar)
    TextView tvTitlebar;
    @BindView(R2.id.common_titlebar_right_iv)
    ImageView commonTitlebarRightIv;
    @BindView(R2.id.common_titlebar_right_tv)
    TextView commonTitlebarRightTv;
    @BindView(R2.id.rl_titlebar)
    RelativeLayout rlTitlebar;
    @BindView(R2.id.withdraw_actual_income_tv)
    TextView withdrawActualIncomeTv;
    @BindView(R2.id.withdraw_tax_tv)
    TextView withdrawTaxTv;
    @BindView(R2.id.withdraw_account_tv)
    TextView withdrawAccountTv;
    @BindView(R2.id.withdraw_success_finish_tv)
    TextView withdrawSuccessFinishTv;

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
        return R.layout.mine_withdraw_success_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initViewUI();
    }

    private void initViewUI() {
        tvTitlebar.setText("收益提现");
        String withdrawAmount = getIntent().getStringExtra(MineConstant.WITHDRAW_AMOUNT_KEY);
        String totalTaxCount = getIntent().getStringExtra(MineConstant.TAX_TOTAL_KEY);
        Float actualIncome = 0.0f;
        if(!TextUtils.isEmpty(withdrawAmount) && !TextUtils.isEmpty(totalTaxCount)){
            actualIncome = Float.parseFloat(withdrawAmount)-Float.parseFloat(totalTaxCount);
        }
        String payAccount = getIntent().getStringExtra(MineConstant.WITHDRAW_ACCOUNT_KEY);
        withdrawActualIncomeTv.setText(actualIncome+"");
        withdrawTaxTv.setText("￥"+totalTaxCount);
        withdrawAccountTv.setText(payAccount);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R2.id.tv_back, R2.id.withdraw_success_finish_tv})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tv_back) {
            finish();
        } else if (i == R.id.withdraw_success_finish_tv) {
            finish();
        }
    }
}
