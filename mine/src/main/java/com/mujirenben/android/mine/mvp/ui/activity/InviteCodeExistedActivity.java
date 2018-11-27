package com.mujirenben.android.mine.mvp.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.gcssloop.widget.RCImageView;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ARouterPaths.INVITE_CODE_EXISTED_ACTIVITY)
public class InviteCodeExistedActivity extends BaseActivity {

    @BindView(R2.id.tv_title)
    TextView mTitleTV;

    @BindView(R2.id.tv_invite_code)
    TextView mInviteCodeTV;

    @BindView(R2.id.tv_name)
    TextView mNameTV;

    @BindView(R2.id.iv_icon)
    RCImageView mIconIV;

    @BindView(R2.id.rl_referral_info)
    RelativeLayout mReferralInfoRL;

    private String mNickName;
    private String mAvatarUrl;
    private String mInviteCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtil.setStatusBarWhite(this);
        if (SpUtil.getIsMIUI(this)){
            StatusBarUtil.getStatusBarHelper(this,R.color.white).into(this);
        }else {
            StatusBarUtil.setStatusBarWhite(this);
        }
    }

    @OnClick(R2.id.ib_left_action)
    public void onBackClick(View view) {
        finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_invite_code_existed;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTitleTV.setText("填写邀请码");

        mInviteCode = getIntent().getStringExtra("inviteCode");
        mNickName = getIntent().getStringExtra("nikeName");
        mAvatarUrl = getIntent().getStringExtra("avatarUrl");

        mInviteCodeTV.setText("邀请码: " + mInviteCode);

        if (TextUtils.isEmpty(mNickName) || TextUtils.isEmpty(mAvatarUrl)) {
            mReferralInfoRL.setVisibility(View.GONE);
        } else {
            mReferralInfoRL.setVisibility(View.VISIBLE);
            mNameTV.setText(mNickName);
            Glide.with(this).load(mAvatarUrl).into(mIconIV);
        }
    }
}
