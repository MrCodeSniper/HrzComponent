package com.mujirenben.android.mine.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hrz.poplayer.HrzLayerView;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.resource.SharePreferenceKey;
import com.mujirenben.android.common.event.KeFuLoginEvent;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.widget.DialogUtils;


import com.mujirenben.android.mine.di.component.DaggerSettingsComponent;
import com.mujirenben.android.mine.di.module.SettingsModule;
import com.mujirenben.android.mine.login.LoginUtil;
import com.mujirenben.android.mine.mvp.contract.SettingsContract;
import com.mujirenben.android.mine.mvp.presenter.SettingsPresenter;
import com.mujirenben.android.mine.mvp.ui.view.SwitchButton;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.hrz.poplayer.HrzLayerView.STATE_DIALOG;


@Route(path = ARouterPaths.SETTINGS_ACTIVITY)
public class SettingsActivity extends BaseActivity<SettingsPresenter>
        implements SettingsContract.View, SwitchButton.OnStateChangedListener,Handler.Callback {

    @BindView(R2.id.layout_title_bar)
    RelativeLayout mTileBar;

    @BindView(R2.id.layout_notification_settings)
    RelativeLayout mNotificationSettingsRl;

    @BindView(R2.id.layout_download_img_manually)
    RelativeLayout mDownloadImgSettings;

    @BindView(R2.id.layout_auto_play_video)
    RelativeLayout mAutoPlayVideoSettings;

    @BindView(R2.id.layout_clear_cache)
    RelativeLayout mClearCacheSettings;

    @BindView(R2.id.layout_about_hrz)
    RelativeLayout mAboutHrzSettings;

    @BindView(R2.id.layout_switch_addr)
    RelativeLayout mSwitchAddrRL;

    @BindView(R2.id.btn_logout)
    Button mLogoutBtn;

    private SwitchButton mAutoDownloadImgSb;
    private SwitchButton mAutoPlayVideoSb;
    private DialogType type = DialogType.CACHE;
    private DialogUtils dialogUtils;

    private enum DialogType {
        CACHE,LOGOUT
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingsComponent.builder()
                .appComponent(appComponent)
                .settingsModule(new SettingsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        initImmersionBar();
        return R.layout.mine_activity_settings;
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
        setupTitleBar();
        setupNotificationSettings();
        setupDownloadSettings();
        setupAutoPlayVideoSettings();
        setupClearCacheSettings();
        setupAboutHrzSettings();
        setupLogoutBtn();
        // TODO: 2018/9/19 上线需要注释掉这段代码
        /**********上线需要注释掉这段代码 开始*******/
        setupSwitchAddrSettings();
        /**********上线需要注释掉这段代码 结束*******/
        initSwitchButtons();
    }

    private void initSwitchButtons() {
        mAutoDownloadImgSb.setChecked(mPresenter.isConfigOn(
                SharePreferenceKey.SETTINGS_AUTO_DOWNLOAD_IMG_WITHOUT_WIFI, false));
        mAutoPlayVideoSb.setChecked(mPresenter.isConfigOn(
                SharePreferenceKey.SETTINGS_AUTO_PLAY_VIDEO_WITH_WIFI, true));
    }

    private void setupTitleBar() {
         dialogUtils = new DialogUtils(this,R.layout.common_loading_toast,"");
        TextView title = mTileBar.findViewById(R.id.tv_title);
        title.setText(R.string.settings_title_settings);

        ImageButton mLeftAction = mTileBar.findViewById(R.id.ib_left_action);
        mLeftAction.setVisibility(View.VISIBLE);
        mLeftAction.setOnClickListener(v -> SettingsActivity.this.finish());
        mTileBar.findViewById(R.id.ib_right_action).setVisibility(View.GONE);
    }

    private void setupNotificationSettings() {
        TextView mainDescription = mNotificationSettingsRl.findViewById(R.id.tv_main_description);
        mainDescription.setText(R.string.settings_notification_settings);

        TextView subDescription = mNotificationSettingsRl.findViewById(R.id.tv_sub_description);
        subDescription.setVisibility(View.GONE);

        mNotificationSettingsRl.setOnClickListener(v -> {
            ARouter.getInstance().build(ARouterPaths.NOTIFICATION_ACTIVITY).navigation();
//                HrzResourceManagerImpl.getDefault(getApplicationContext());
        });
    }

    private void setupDownloadSettings() {
        TextView mainDescription = mDownloadImgSettings.findViewById(R.id.tv_main_description);
        mainDescription.setText(R.string.settings_manually_download_img_without_wifi);

        mAutoDownloadImgSb = mDownloadImgSettings.findViewById(R.id.switch_action);
        mAutoDownloadImgSb.setOnStateChangedListener(this);
    }

    private void setupAutoPlayVideoSettings() {
        TextView mainDescription = mAutoPlayVideoSettings.findViewById(R.id.tv_main_description);
        mainDescription.setText(R.string.settings_auto_play_video_with_wifi);

        mAutoPlayVideoSb = mAutoPlayVideoSettings.findViewById(R.id.switch_action);
        mAutoPlayVideoSb.setOnStateChangedListener(this);
    }

    private void setupClearCacheSettings() {
        mPresenter.getLocalCacheSize();

        TextView mainDescription = mClearCacheSettings.findViewById(R.id.tv_main_description);
        mainDescription.setText(R.string.settings_clear_local_cache);

        mClearCacheSettings.setOnClickListener(v -> {
            String msg = "确定要清除缓存吗？";
            showDialog(DialogType.CACHE,msg);
        });
    }

    private void setupAboutHrzSettings() {
        TextView mainDescription = mAboutHrzSettings.findViewById(R.id.tv_main_description);
        mainDescription.setText(R.string.settings_about_hrz);

        TextView subDescription = mAboutHrzSettings.findViewById(R.id.tv_sub_description);
        subDescription.setText(getResources().getString(R.string.settings_version_prefix) + mPresenter.getAppVersion());

//        mAboutHrzSettings.setOnClickListener(v -> ARouter.getInstance().build(ARouterPaths.ABOUT_HRZ_ACTIVITY).navigation());
    }

    private void setupSwitchAddrSettings() {
        TextView mainDescription = mSwitchAddrRL.findViewById(R.id.tv_main_description);
        mainDescription.setText("测试服务器");
        SwitchButton sb = mSwitchAddrRL.findViewById(R.id.switch_action);
        boolean  isTest = SpUtil.getBoolenValue(null,"isTest",this);
        String server =  SpUtil.getStringValue(null,"server",this);
        if(isTest){
            mainDescription.setText(server);
            sb.setChecked(false);
        }else {
            sb.setChecked(true);
            mainDescription.setText(server);
        }
        sb.setOnStateChangedListener(new SwitchButton.OnStateChangedListener() {
            @Override
            public void onStateChanged(SwitchButton which, boolean checked) {
                if (checked) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("server","apix2.hongrenzhuang.com");
                    map.put("isTest",false);
                    SpUtil.save(null,map,SettingsActivity.this);
                    Consts.OFFICIAL_SERVER = "apix2.hongrenzhuang.com";
                    mainDescription.setText( Consts.OFFICIAL_SERVER);
                } else {
                    //mainDescription.setText("测试服务器");
                    Map<String,Object> map = new HashMap<>();
                    map.put("server",Consts.TEST_SERVER);
                    map.put("isTest",true);
                    SpUtil.save(null,map,SettingsActivity.this);
                    Consts.OFFICIAL_SERVER = "192.168.0.4:9000";
                    mainDescription.setText( Consts.OFFICIAL_SERVER);
                }
            }
        });

        if (sb.isChecked()) {
            Consts.OFFICIAL_SERVER = "apix2.hongrenzhuang.com";
        } else {
            Consts.OFFICIAL_SERVER = "192.168.0.4:9010";
        }
    }

    private void setupLogoutBtn() {
        LoginDataManager loginDataManager = LoginDataManager.getsInstance(getApplicationContext());
        if(loginDataManager.isLogin()){
            mLogoutBtn.setVisibility(View.VISIBLE);
        }else {
            mLogoutBtn.setVisibility(View.GONE);
        }

        mLogoutBtn.setOnClickListener(v -> {
            String msg = "确定要退出当前账号吗？";
            showDialog(DialogType.LOGOUT,msg);
        });
    }

    @Override
    public void onLocalCacheCleared(float cacheSizeAfterCleared) {

    }

    @Override
    public void showLoading() {
       dialogUtils.show();
    }

    @Override
    public void hideLoading() {
        dialogUtils.hide();
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
    public void onStateChanged(SwitchButton which, boolean checked) {
        String spKey = null;
        if (which == mAutoDownloadImgSb) {
            spKey = SharePreferenceKey.SETTINGS_AUTO_DOWNLOAD_IMG_WITHOUT_WIFI;
        } else if (which == mAutoPlayVideoSb) {
            spKey = SharePreferenceKey.SETTINGS_AUTO_PLAY_VIDEO_WITH_WIFI;
        }

        if (spKey != null) {
            mPresenter.saveConfig(spKey, checked);
        }
    }

    @Override
    public void onCacheSizeComputed(float size) {
        TextView subDescription = mClearCacheSettings.findViewById(R.id.tv_sub_description);
        subDescription.setText(size + " MB");
    }

    @Override
    public void onCacheCleared() {
        mPresenter.getLocalCacheSize();
    }

    /**
     * 弹出对话框
     * @param type
     * @param msg
     */
    private void showDialog(DialogType type, String msg){

        HrzLayerView dialog = new HrzLayerView(this, STATE_DIALOG);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_normal, null);
        TextView contentTv = view.findViewById(R.id.tv_message);
        TextView cancelTv = view.findViewById(R.id.tv_right_action);
        TextView confirmTv = view.findViewById(R.id.tv_left_action);
        contentTv.setText(msg);
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) contentTv.getLayoutParams();
        params.gravity = Gravity.CENTER;
        contentTv.setTextColor(Color.parseColor("#B2B2B2"));
        confirmTv.setText("确定");
        cancelTv.setText("取消");
        cancelTv.setOnClickListener(v -> dialog.dismiss());
        confirmTv.setOnClickListener(v ->{
            switch (type){
                case CACHE:
                    mPresenter.clearLocalCache();
                    break;
                case LOGOUT:
                    showLoading();
                    mPresenter.logout(SettingsActivity.this);
                    break;
            }

            dialog.dismiss();
        });
        dialog.setDialogView(view);
        dialog.initLayerView(null);
        dialog.show();

    }

    @Override
    public void logOutSuccess() {
        mPresenter.clearSqlite();
    }

    @Override
    public void logOutFail(String msg) {
        ArmsUtils.makeText(this,msg);
        hideLoading();
    }

    @Override
    public void deleteUserInfoSuccess() {
        hideLoading();
        LoginDataManager loginDataManager = LoginDataManager.getsInstance(getApplicationContext());
        loginDataManager.logout();
        EventBus.getDefault().post(new KeFuLoginEvent(false));
        LoginUtil.postLogoutEvent(getApplicationContext());
        //客服退出登录
        SettingsActivity.this.finish();
    }

    @Override
    public void deleteUserInfoFail() {
        hideLoading();
    }

    public static class MyHandler extends Handler {

        WeakReference<SettingsActivity> weakReference;

        public MyHandler(SettingsActivity mainActivity) {
            weakReference = new WeakReference<>(mainActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null == weakReference || null == weakReference.get()) {
                return;
            }
            weakReference.get().handleMessage(msg);

        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
    MyHandler mHandler = new MyHandler(this);
}
