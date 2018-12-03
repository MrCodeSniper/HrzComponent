package com.mujirenben.android.vip.mvp.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.google.zxing.WriterException;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.QrCodeUtils;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.util.rxtools.RxTextTool;
import com.mujirenben.android.vip.R;
import com.mujirenben.android.vip.R2;
import com.mujirenben.android.vip.di.component.DaggerVipQrCodeComponent;
import com.mujirenben.android.vip.di.module.VipQrCodeModule;
import com.mujirenben.android.vip.mvp.contract.VipQrCodeContract;
import com.mujirenben.android.vip.mvp.model.bean.InvitationCodeBean;
import com.mujirenben.android.vip.mvp.presenter.VipQrCodePresenter;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ARouterPaths.VIP_QR_CODE_ACTIVITY)
public class VipQrCodeActivity extends BaseActivity<VipQrCodePresenter>
        implements VipQrCodeContract.View {

    @BindView(R2.id.iv_head_portrait)
    ImageView mHeadPortraitIV;

    @BindView(R2.id.iv_qr_code)
    ImageView mQrCodeIV;

    @BindView(R2.id.tv_nick_name)
    TextView mNickNameTV;

    @BindView(R2.id.tv_invitation_code)
    TextView mInvitationCodeTV;

    @BindView(R2.id.btn_share)
    TextView mShareBTN;

    @BindView(R2.id.btn_copy_invitation_code)
    TextView mCopyInvitationCodeBTN;

    @BindView(R2.id.tv_title)
    TextView mTitleTV;
    private String mInviteCode;
    private String mQrCodeUrl;
    private String mSource;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerVipQrCodeComponent.builder()
                .appComponent(appComponent)
                .vipQrCodeModule(new VipQrCodeModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setQrCodeIVSize();
        mTitleTV.setText(R.string.qr_code_activity_title);
        mSource = getIntent().getStringExtra("source");
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
//        StatusBarUtil.setStatusBarWhite(this);
        if (SpUtil.getIsMIUI(this)){
            StatusBarUtil.getStatusBarHelper(this,R.color.white).into(this);
        }else {
            StatusBarUtil.setStatusBarWhite(this);
        }
        return R.layout.activity_vip_qrcode;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.loadUserInfo();

        LoginDataManager ldm = LoginDataManager.getsInstance(this);
        String name = ldm.getDisplayName();
        String portraitUrl = ldm.getAvatarUrl();
        mQrCodeIV.post(new Runnable() {
            @Override
            public void run() {
                String qrCodeUrl = ldm.getUserQrCodeUrl();
                if (!TextUtils.isEmpty(qrCodeUrl)) {
                    Bitmap qrCodeBmp = null;
                    try {
                        qrCodeBmp = QrCodeUtils.createCode(qrCodeUrl, BitmapFactory.decodeResource(getResources(), R.drawable.qr_code_logo));
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                    mQrCodeIV.setImageBitmap(qrCodeBmp);
                }
            }
        });

        mNickNameTV.setText(name + "邀您一起");
        if (!TextUtils.isEmpty(portraitUrl)) {
            Glide.with(this).load(portraitUrl).into(mHeadPortraitIV);
        }
    }

    @Override
    public void onUserInfoLoaded(InvitationCodeBean userInfo) {
        if (userInfo == null || !userInfo.isSuccess()) {
            return;
        }
        mInvitationCodeTV.setText("我的邀请码：" + userInfo.getData().getInviteCode());

        mInviteCode = userInfo.getData().getInviteCode();
        mQrCodeUrl = userInfo.getData().getInviteUrl();

        LoginDataManager ldm = LoginDataManager.getsInstance(this);
        String newUrl = userInfo.getData().getInviteUrl();
        if (!newUrl.equals(ldm.getUserQrCodeUrl())) {
            Bitmap qrCodeBmp = null;
            try {
                qrCodeBmp = QrCodeUtils.createCode(newUrl, BitmapFactory.decodeResource(getResources(), R.drawable.qr_code_logo));
            } catch (WriterException e) {
                e.printStackTrace();
            }
            mQrCodeIV.setImageBitmap(qrCodeBmp);

            ldm.updateUserQrCodeUrlIfNeeded(newUrl);
        }
    }

    @Override
    public void onTextCopied(String text) {
        ArmsUtils.makeText(this, "复制成功");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick(R2.id.btn_share)
    public void onShareButtonClicked(View view) {
        ARouter.getInstance()
                .build(ARouterPaths.VIP_SHARE_ACTIVITY)
                .withString("invite_code", mInviteCode)
                .withString("qr_code_url", mQrCodeUrl)
                .withString("source", mSource)
                .navigation();
    }

    @OnClick(R2.id.btn_copy_invitation_code)
    public void onCopyInvitationCodeButtonClicked(View view) {
        CharSequence text = mInvitationCodeTV.getText();
        if (text != null) {
            mPresenter.copyTextToClipboard(mInviteCode);
        }
    }

    @OnClick(R2.id.ib_left_action)
    public void onBackClick() {
        finish();
    }

    private void setQrCodeIVSize() {
        int screenW = getResources().getDisplayMetrics().widthPixels;
        int qrCodeIVMargin = getResources().getDimensionPixelSize(
                R.dimen.qr_code_image_view_margin_horizontal);
        int qrCodeIVSize = screenW - 2 * qrCodeIVMargin;
        ViewGroup.LayoutParams params = mQrCodeIV.getLayoutParams();
        params.width = params.height = qrCodeIVSize;
        mQrCodeIV.setLayoutParams(params);
    }
}
