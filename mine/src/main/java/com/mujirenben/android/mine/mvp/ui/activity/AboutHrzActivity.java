package com.mujirenben.android.mine.mvp.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.dagger.component.AppComponent;

import com.mujirenben.android.common.util.AppInfoUtils;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.LogUtil;
import com.mujirenben.android.common.widget.DownloadProgressDialog;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import com.mujirenben.android.mine.di.component.DaggerAboutHrzComponent;
import com.mujirenben.android.mine.di.module.AboutHrzModule;
import com.mujirenben.android.mine.mvp.contract.AboutHrzContract;
import com.mujirenben.android.mine.mvp.model.bean.AppUpgradeInfo;
import com.mujirenben.android.mine.mvp.presenter.AboutHrzPresenter;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

@Route(path = ARouterPaths.ABOUT_HRZ_ACTIVITY)
public class AboutHrzActivity extends BaseActivity<AboutHrzPresenter> implements AboutHrzContract.View, DownloadProgressDialog.ClickListener {

    private static final String LOG_TAG = "AboutHrzActivity";

    private Handler mHandler = new Handler();
    private DownloadProgressDialog mDownloadDialog;

    @BindView(R2.id.layout_title_bar)
    RelativeLayout mTileBar;

    @BindView(R2.id.iv_app_qr_code)
    ImageView mAppQrCode;

    @BindView(R2.id.tv_current_version)
    TextView mCurrentAppVersion;

    @BindView(R2.id.layout_share_qr_code)
    RelativeLayout mSharedQrCodeRl;

    @BindView(R2.id.layout_check_upgrade)
    RelativeLayout mCheckUpgradeRl;

    @BindView(R2.id.layout_special_explain)
    RelativeLayout mSpecialExplainRl;

    @BindView(R2.id.layout_platform_service_explain)
    RelativeLayout mPlatformServiceExplainRl;

    @BindView(R2.id.layout_private_policy)
    RelativeLayout mPrivatePolicyRl;

    @BindView(R2.id.layout_hotline)
    RelativeLayout mHotlineRl;

    @BindView(R2.id.iv_official_account_qr_code)
    ImageView mOfficialAccountQrCode;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAboutHrzComponent.builder()
                .aboutHrzModule(new AboutHrzModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        initImmersionBar();
        return R.layout.mine_activity_about_hrz;
    }

    @Override
    protected void onDestroy() {
        mPresenter.cancelDownloadApk();
        super.onDestroy();
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

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        RetrofitUrlManager.getInstance().putDomain("mock_app_upgrade", "http://www.wanandroid.com");

        setupTitleBar();
        setupAppQrCode();
        setupCurrentAppVersion();
        setupSharedQrCode();
        setupCheckUpgrade();
        setupSpecialExplain();
        setupPlatformServiceExplain();
        setupPrivatePolicy();
        setupHotline();
        setupOfficialAccountQrCode();
    }

    public static int CALL_PHONE_REQUEST_CODE = 1;
    public static int WRITE_EXTERNAL_STORAGE = 2;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PHONE_REQUEST_CODE) {
            clickHotline(null);
        } else if (requestCode == WRITE_EXTERNAL_STORAGE) {
            checkUpgrade(null);
        }
    }

    @OnClick(R2.id.layout_hotline)
    public void clickHotline(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mPresenter.getHotline()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, CALL_PHONE_REQUEST_CODE);
            return;
        }
        startActivity(intent);
    }

    @OnClick(R2.id.layout_check_upgrade)
    public void checkUpgrade(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
            return;
        }

        mPresenter.checkUpgrade();
    }

    @OnClick(R2.id.layout_special_explain)
    public void routeToSpecialExplain(View view) {
        routeToWebView(R.string.about_hrz_settings_special_explain, "http://www.baidu.com");//TODO 修改url
    }

    @OnClick(R2.id.layout_platform_service_explain)
    public void routeToPlatformServiceExplain(View view) {
        routeToWebView(R.string.about_hrz_settings_platform_service_explain, "http://www.baidu.com");//TODO 修改url
    }

    @OnClick(R2.id.layout_private_policy)
    public void routeToPrivatePolicy(View view) {
        routeToWebView(R.string.about_hrz_settings_private_policy, "http://www.baidu.com");//TODO 修改url
    }

    @OnClick(R2.id.layout_share_qr_code)
    public void shareQrCode(View view) {

    }

    private void routeToWebView(int titleId, String uriStr) {
        ARouter.getInstance().build(ARouterPaths.SETTINGS_WEB_VIEW_ACTIVITY)
                .withString("title", getResources().getString(titleId))
                .withString("content_uri", uriStr)
                .navigation();
    }

    private void setupTitleBar() {
        TextView title = mTileBar.findViewById(R.id.tv_title);
        title.setText(R.string.settings_about_hrz);

        ImageButton mLeftAction = mTileBar.findViewById(R.id.ib_left_action);
        mLeftAction.setVisibility(View.VISIBLE);
        mLeftAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutHrzActivity.this.finish();
            }
        });
        mTileBar.findViewById(R.id.ib_right_action).setVisibility(View.GONE);
    }

    private void setupAppQrCode() {
        mAppQrCode.setImageBitmap(mPresenter.getAppQrCode());
    }

    private void setupCurrentAppVersion() {
        mCurrentAppVersion.setText(getResources().getString(R.string.about_hrz_settings_version_prefix)
                + mPresenter.getAppVersion());
    }

    private void setupSharedQrCode() {
        TextView mainDescription = mSharedQrCodeRl.findViewById(R.id.tv_main_description);
        mainDescription.setText(R.string.about_hrz_settings_share_app_qr_code);

        TextView subDescription = mSharedQrCodeRl.findViewById(R.id.tv_sub_description);
        subDescription.setVisibility(View.GONE);
    }

    private void setupCheckUpgrade() {
        TextView mainDescription = mCheckUpgradeRl.findViewById(R.id.tv_main_description);
        mainDescription.setText(R.string.about_hrz_settings_check_upgrade);

        TextView subDescription = mCheckUpgradeRl.findViewById(R.id.tv_sub_description);
        subDescription.setVisibility(View.GONE);
    }

    private void setupSpecialExplain() {
        TextView mainDescription = mSpecialExplainRl.findViewById(R.id.tv_main_description);
        mainDescription.setText(R.string.about_hrz_settings_special_explain);

        TextView subDescription = mSpecialExplainRl.findViewById(R.id.tv_sub_description);
        subDescription.setVisibility(View.GONE);
    }

    private void setupPlatformServiceExplain() {
        TextView mainDescription = mPlatformServiceExplainRl.findViewById(R.id.tv_main_description);
        mainDescription.setText(R.string.about_hrz_settings_platform_service_explain);

        TextView subDescription = mPlatformServiceExplainRl.findViewById(R.id.tv_sub_description);
        subDescription.setVisibility(View.GONE);
    }

    private void setupPrivatePolicy() {
        TextView mainDescription = mPrivatePolicyRl.findViewById(R.id.tv_main_description);
        mainDescription.setText(R.string.about_hrz_settings_private_policy);

        TextView subDescription = mPrivatePolicyRl.findViewById(R.id.tv_sub_description);
        subDescription.setVisibility(View.GONE);
    }

    private void setupHotline() {
        TextView mainDescription = mHotlineRl.findViewById(R.id.tv_main_description);
        mainDescription.setText(R.string.about_hrz_settings_hotline);

        TextView subDescription = mHotlineRl.findViewById(R.id.tv_sub_description);
        subDescription.setText(mPresenter.getHotline());
    }

    private void setupOfficialAccountQrCode() {
        mOfficialAccountQrCode.setImageBitmap(mPresenter.getOfficialAccountQrCode());
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
    public void onRetrieveAppUpgradeInfoCompleted(AppUpgradeInfo info) {
        LogUtil.i(LOG_TAG, info.toString());
        if (info.getVersionCode() > AppInfoUtils.getVersionCode(getApplicationContext())) {
            mPresenter.downloadApk(info.getDownloadUri());
        } else {
            ArmsUtils.makeText(this, getResources().getString(R.string.about_hrz_settings_already_up_to_data));
        }
    }

    @Override
    public void onDownloadStart() {
        LogUtil.i(LOG_TAG, "onDownloadStart");
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mDownloadDialog = new DownloadProgressDialog(AboutHrzActivity.this);
                mDownloadDialog.show();
                mDownloadDialog.setProgressMax(100);
                mDownloadDialog.setClickListener(AboutHrzActivity.this);
            }
        });
    }

    @Override
    public void onDownloadCompleted(String apkFilePath) {
        LogUtil.i(LOG_TAG, "onDownloadCompleted");
        dissmissDialog();
        installApk(apkFilePath);
    }

    @Override
    public void onDownloadFailed() {
        LogUtil.i(LOG_TAG, "onDownloadFailed");
        dissmissDialog();
    }

    @Override
    public void onDownloadCanceled() {
        LogUtil.i(LOG_TAG, "onDownloadCanceled");
        ArmsUtils.makeText(this, "已取消下载");
    }

    @Override
    public void onRetrieveAppUpgradeInfoEmpty() {

    }

    @Override
    public void onDownloadUpgrade(long downloadedSize, long totalSize) {
        LogUtil.d(LOG_TAG, "onDownloadUpgrade >> downloadedSize = " + downloadedSize + " totalSize = " + totalSize);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mDownloadDialog != null) {
                    mDownloadDialog.setProgress((int) (((float)downloadedSize / totalSize) *100));
                    mDownloadDialog.setDownloadState((downloadedSize / (1024 * 1024)) + "M/" + totalSize / (1024 * 1024) + "M");
                }
            }
        });
    }

    @Override
    public void onLeftButtonClicked() {
        mPresenter.cancelDownloadApk();
        dissmissDialog();
    }

    @Override
    public void onRightButtonClicked() {
        dissmissDialog();
    }

    private void dissmissDialog() {
        if (mDownloadDialog != null) {
            mDownloadDialog.dismiss();
            mDownloadDialog = null;
        }
    }

    private void installApk(String fileSavePath) {
        File file = new File(fileSavePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri data;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判断版本大于等于7.0
            data = FileProvider.getUriForFile(this, "com.mujirenben.liangchenbufu.fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);// 给目标应用一个临时授权
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        startActivity(intent);
    }
}
