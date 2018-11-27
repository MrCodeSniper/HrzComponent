package com.mujirenben.android.mine.mvp.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.dagger.component.AppComponent;

import com.mujirenben.android.common.datapackage.resource.SharePreferenceKey;
import com.mujirenben.android.common.widget.dialog.NormalDialog;
import com.mujirenben.android.common.widget.dialog.RxDialog;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;
import com.mujirenben.android.mine.di.component.DaggerNotificationSettingsComponent;
import com.mujirenben.android.mine.di.module.NotificationSettingsModule;
import com.mujirenben.android.mine.mvp.contract.NotificationSettingsContract;
import com.mujirenben.android.mine.mvp.presenter.NotificationSettingsPresenter;
import com.mujirenben.android.mine.mvp.ui.view.SwitchButton;

import butterknife.BindView;

@Route(path = ARouterPaths.NOTIFICATION_ACTIVITY)
public class NotificationSettingsActivity extends BaseActivity<NotificationSettingsPresenter>
        implements NotificationSettingsContract.View, SwitchButton.OnStateChangedListener{

    @BindView(R2.id.layout_title_bar)
    RelativeLayout mTileBar;

    @BindView(R2.id.rl_receive_new_notification)
    RelativeLayout mReceiveNewNotificationRl;

    @BindView(R2.id.layout_banner_settings)
    RelativeLayout mBannerSettingsRl;

    @BindView(R2.id.layout_ring_settings)
    RelativeLayout mRingSettingsRl;

    @BindView(R2.id.layout_vibrate_settings)
    RelativeLayout mVibrateSettingsRl;

    private SwitchButton mReceiveNewNotificationSb;
    private SwitchButton mBannerSettingsSb;
    private SwitchButton mRingSettingSb;
    private SwitchButton mVibrateSettingSb;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerNotificationSettingsComponent.builder()
                .notificationSettingsModule(new NotificationSettingsModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        initImmersionBar();
        return R.layout.mine_activity_notification_settings;
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
        setupNewNotificationSettings();
        setupBannerSettings();
        setupRingSettings();
        setupVibrateSettings();

        initSwitchButtons();
    }

    private void setupTitleBar() {
        TextView title = mTileBar.findViewById(R.id.tv_title);
        title.setText(R.string.notification_settings_title);

        ImageButton mLeftAction = mTileBar.findViewById(R.id.ib_left_action);
        mLeftAction.setVisibility(View.VISIBLE);
        mLeftAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationSettingsActivity.this.finish();
            }
        });
        mTileBar.findViewById(R.id.ib_right_action).setVisibility(View.GONE);
    }

    private void setupNewNotificationSettings() {
        TextView mainDescription = mReceiveNewNotificationRl.findViewById(R.id.tv_main_description);
        mainDescription.setText(R.string.notification_settings_receive_new_notification);

        TextView subDescription = mReceiveNewNotificationRl.findViewById(R.id.tv_sub_description);
        subDescription.setText(R.string.notification_settings_receive_new_notification_sub);

        mReceiveNewNotificationSb = mReceiveNewNotificationRl.findViewById(R.id.switch_action);
        mReceiveNewNotificationSb.setOnStateChangedListener(this);
        mReceiveNewNotificationSb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mReceiveNewNotificationSb.isChecked()) {
                    // 打开
                    mReceiveNewNotificationSb.setChecked(true);
                    return;
                }

                NormalDialog dialog = new NormalDialog(NotificationSettingsActivity.this);
                dialog.setMessage("关闭系统通知你将无法接收到商品优惠、价格变动的提醒等相关信息。");
                dialog.setLeftAction("确认", (view) -> {
                    mReceiveNewNotificationSb.setChecked(false);
                    dialog.dismiss();
                });
                dialog.setRightAction("取消", (view) -> dialog.dismiss());
                dialog.show();
            }
        });
    }

    private void setupBannerSettings() {
        TextView mainDescription = mBannerSettingsRl.findViewById(R.id.tv_main_description);
        mainDescription.setText(R.string.notification_settings_banner);

        mBannerSettingsSb = mBannerSettingsRl.findViewById(R.id.switch_action);
        mBannerSettingsSb.setOnStateChangedListener(this);
    }

    private void setupRingSettings() {
        TextView mainDescription = mRingSettingsRl.findViewById(R.id.tv_main_description);
        mainDescription.setText(R.string.notification_settings_ring);

        mRingSettingSb = mRingSettingsRl.findViewById(R.id.switch_action);
        mRingSettingSb.setOnStateChangedListener(this);
    }

    private void setupVibrateSettings() {
        TextView mainDescription = mVibrateSettingsRl.findViewById(R.id.tv_main_description);
        mainDescription.setText(R.string.notification_settings_vibrate);

        mVibrateSettingSb = mVibrateSettingsRl.findViewById(R.id.switch_action);
        mVibrateSettingSb.setOnStateChangedListener(this);
    }

    private void initSwitchButtons() {
        mReceiveNewNotificationSb.setChecked(mPresenter.isConfigOn(
                SharePreferenceKey.NOTIFICATION_SETTINGS_RECEIVE_NEW_NOTIFICATION, true));
        mBannerSettingsSb.setChecked(mPresenter.isConfigOn(
                SharePreferenceKey.NOTIFICATION_SETTINGS_BANNER, true));
        mRingSettingSb.setChecked(mPresenter.isConfigOn(
                SharePreferenceKey.NOTIFICATION_SETTINGS_RING, true));
        mVibrateSettingSb.setChecked(mPresenter.isConfigOn(
                SharePreferenceKey.NOTIFICATION_SETTINGS_VIBRATE, true));
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
    public void onStateChanged(SwitchButton witch, boolean checked) {
        final String spKey;
        if (witch == mReceiveNewNotificationSb) {
            spKey = SharePreferenceKey.NOTIFICATION_SETTINGS_RECEIVE_NEW_NOTIFICATION;
        } else if (witch == mBannerSettingsSb) {
            spKey = SharePreferenceKey.NOTIFICATION_SETTINGS_BANNER;
        } else if (witch == mRingSettingSb) {
            spKey = SharePreferenceKey.NOTIFICATION_SETTINGS_RING;
        } else if (witch == mVibrateSettingSb) {
            spKey = SharePreferenceKey.NOTIFICATION_SETTINGS_VIBRATE;
        } else {
            spKey = null;
        }

        if (spKey != null) {
            mPresenter.saveConfiguration(spKey, checked);
        }
    }
}
