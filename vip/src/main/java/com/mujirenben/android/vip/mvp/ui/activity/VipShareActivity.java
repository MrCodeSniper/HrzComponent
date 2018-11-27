package com.mujirenben.android.vip.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.BitmpUtils;
import com.mujirenben.android.common.util.LogUtil;
import com.mujirenben.android.common.util.QrCodeUtils;
import com.mujirenben.android.common.util.sensorHelper.SensorHelper;
import com.mujirenben.android.common.util.wxHelper.ShareDialogHelper;
import com.mujirenben.android.common.util.wxHelper.WeiXinHelper;
import com.mujirenben.android.vip.R;
import com.mujirenben.android.vip.R2;
import com.mujirenben.android.vip.di.component.DaggerVipShareComponent;
import com.mujirenben.android.vip.di.module.VipShareModule;
import com.mujirenben.android.vip.mvp.contract.VipShareContract;
import com.mujirenben.android.vip.mvp.presenter.VipSharePresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = ARouterPaths.VIP_SHARE_ACTIVITY)
public class VipShareActivity extends BaseActivity<VipSharePresenter>
        implements VipShareContract.View {

    private static final float MIN_PAGE_SCALE = 0.8f;

    @BindView(R2.id.btn_share)
    TextView mShareBTN;

    @BindView(R2.id.btn_save)
    TextView mSaveBTN;

    @BindView(R2.id.vp_styles)
    ViewPager mStylesVP;

    @BindView(R2.id.tv_title)
    TextView mTitleTV;

    @BindView(R2.id.ll_pager_indicator)
    LinearLayout mPagerIndicatorLL;

    @BindView(R2.id.fl_vp_wrapper)
    FrameLayout mVpWrapperFL;


    private int[] mPageImageIds = {
            R.drawable.poster1,
            R.drawable.poster2,
            R.drawable.poster3
    };

    private Bitmap mCheckedBg;
    private Bitmap mUnCheckedBg;

    private String mSavedImagePath;

    private String mNickName;
    private String mInviteCode;
    private String mQrCodeUrl;
    private Bitmap mQrCode;
    private String mPortraitUrl;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerVipShareComponent.builder()
                .appComponent(appComponent)
                .vipShareModule(new VipShareModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        int size = getResources().getDimensionPixelSize(R.dimen.page_dot_size);

        mCheckedBg = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(mCheckedBg);
        Paint checkedPaint = new Paint();
        checkedPaint.setColor(0xFFB2B2B2);
        canvas1.drawCircle(size/2, size/2, size/2, checkedPaint);

        mUnCheckedBg = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(mUnCheckedBg);
        Paint uncheckedPaint = new Paint();
        uncheckedPaint.setColor(0xFFCECECE);
        canvas2.drawCircle(size/2, size/2, size/2, uncheckedPaint);

        initImmersionBar();

        return R.layout.activity_vip_share;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTitleTV.setText("选择分享样式");

        Intent intent = getIntent();
        mInviteCode = intent.getStringExtra("invite_code");
        mQrCodeUrl = intent.getStringExtra("qr_code_url");

        if (TextUtils.isEmpty(mQrCodeUrl)) {
            LoginDataManager ldm = LoginDataManager.getsInstance(this);
            mQrCodeUrl = ldm.getUserQrCodeUrl();
        }


        LoginDataManager ldm = LoginDataManager.getsInstance(this);
        mNickName = ldm.getDisplayName();
        mPortraitUrl = ldm.getAvatarUrl();

        mVpWrapperFL.setOnTouchListener((v, event) -> mStylesVP.dispatchTouchEvent(event));

        mStylesVP.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                page.setScaleY(1f - ((float) ((1 - MIN_PAGE_SCALE) * Math.abs(position))));
                page.setScaleX(1f - ((float) ((1 - MIN_PAGE_SCALE) * Math.abs(position))));
            }
        });
        mStylesVP.setAdapter(new StylesViewPagerAdapter(getSupportFragmentManager(), mPageImageIds));
        mStylesVP.setOffscreenPageLimit(3);
        mStylesVP.setCurrentItem(1);
        mStylesVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                refreshDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < mPageImageIds.length; i++) {
            mPagerIndicatorLL.addView(generateDot());
        }

        refreshDots(1);
    }

    @Override
    public void onSavingSharingImageStarted() {

    }

    @Override
    public void onSavingSharingImageCompleted(String path, boolean toShare) {
        if (toShare) {
            mSavedImagePath = path;
            ShareDialogHelper.getBuilder(this)
                    .setDialogTitle("标题")
                    .setDialogContent("内容")
                    .setOnLinkListener(() -> {

                    })
                    .setOnSessionListener(() -> share(WeiXinHelper.ShareToType.SESSION))
                    .setOnTimeLineListener(() -> share(WeiXinHelper.ShareToType.TIMELINE))
                    .build()
                    .showDialog(true);
        } else {
            ArmsUtils.makeText(this, "保存成功");
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(path))));
        }
    }

    @Override
    public void onSavingSharingImageFailed() {
        ArmsUtils.makeText(this, "保存失败");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick(R2.id.btn_save)
    public void onSaveButtonClicked() {
        Log.i(TAG, "onSaveButtonClicked");
        Bitmap bmp = extractBitmapToShare();
        mPresenter.saveImage(bmp, false);
    }

    @OnClick(R2.id.btn_share)
    public void onShareButtonClicked() {
        Log.i(TAG, "onShareButtonClicked");
        Bitmap bmp = extractBitmapToShare();
        mPresenter.saveImage(bmp, true);

        LogUtil.i("jan", "source = " + getIntent().getStringExtra("source"));
        if (!TextUtils.isEmpty(getIntent().getStringExtra("source"))) {
            HashMap<String, Object> sensorMap = new HashMap<>();
            sensorMap.put("number", mStylesVP.getCurrentItem() + 1);
            sensorMap.put("source", getIntent().getStringExtra("source"));
            SensorHelper.uploadTrack("FindShare", sensorMap);
        }
    }

    @OnClick(R2.id.ib_left_action)
    public void onBackClick() {
        finish();
    }

    private void share(WeiXinHelper.ShareToType shareType) {
        WeiXinHelper.getBuilder(this)
                .setTitle("")
                .setPictureThumbPaths(new String[]{"file://" + mSavedImagePath})
                .setDescription("")
                .build()
                .sharePictureTo(shareType);
    }

    private Bitmap extractBitmapToShare() {
        Fragment fragment = ((StylesViewPagerAdapter)mStylesVP.getAdapter()).getItem(mStylesVP.getCurrentItem());
        if (fragment != null) {
            return BitmpUtils.extractBitmapFromView(fragment.getView());
        }

        return null;
    }

    private void refreshDots(int current) {
        for (int i = 0; i < mPagerIndicatorLL.getChildCount(); i++) {
            View child = mPagerIndicatorLL.getChildAt(i);
            if (i == current) {
                child.setBackground(new BitmapDrawable(getResources(), mCheckedBg));
            } else {
                child.setBackground(new BitmapDrawable(getResources(), mUnCheckedBg));
            }
        }
    }

    private View generateDot() {
        int size = getResources().getDimensionPixelSize(R.dimen.page_dot_size);
        int margin = getResources().getDimensionPixelSize(R.dimen.page_dot_margin_horizontal);

        View view = new View(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        params.leftMargin = params.rightMargin = margin;
        view.setBackground(new BitmapDrawable(getResources(), mCheckedBg));
        view.setLayoutParams(params);
        return view;
    }

    protected void initImmersionBar() {
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.main_color));
        }
    }

    public static class PageFragment extends Fragment {

        private int mPosterId;


        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater,
                                 @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            mPosterId = getArguments().getInt("poster_id");
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_share_page, null);
            view.setBackgroundResource(mPosterId);

            TextView nickNameTV = view.findViewById(R.id.tv_nick_name);
            TextView inviteCodeTV = view.findViewById(R.id.tv_invitation_code);
            ImageView qrCodeIV = view.findViewById(R.id.iv_qr_code);
            ImageView portraitIV = view.findViewById(R.id.iv_head_portrait);

            String nickName = getArguments().getString("nick_name");
            String qrCodeUrl = getArguments().getString("qr_code_url");
            String inviteCode = getArguments().getString("invite_code");
            String portraitUrl = getArguments().getString("portrait_url");

            nickNameTV.setText(nickName + "邀您一起");
            inviteCodeTV.setText("我的邀请码：" + inviteCode);

            Bitmap qrCode = QrCodeUtils.createQRImage(qrCodeUrl, 800, 800);
            qrCodeIV.setImageBitmap(qrCode);
            qrCode=null;

            if (!TextUtils.isEmpty(portraitUrl)) {
                Glide.with(portraitIV).load(portraitUrl).into(portraitIV);
            }

            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            view.setScaleX(MIN_PAGE_SCALE);
            view.setScaleY(MIN_PAGE_SCALE);
        }
    }

    private class StylesViewPagerAdapter extends FragmentStatePagerAdapter {

        private List<PageFragment> mFragments = new ArrayList<>();

        public StylesViewPagerAdapter(FragmentManager fm, int[] posterIds) {
            super(fm);
            for (int id : posterIds) {
                PageFragment fragment = new PageFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("poster_id", id);
                bundle.putString("nick_name", mNickName);
                bundle.putString("qr_code_url", mQrCodeUrl);
                bundle.putString("invite_code", mInviteCode);
                bundle.putString("portrait_url", mPortraitUrl);
                fragment.setArguments(bundle);
                mFragments.add(fragment);
            }
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position % 3);
        }
    }
}
