package com.mujirenben.android.xsh.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.callback.DialogCallBack;
import com.mujirenben.android.xsh.constant.Constants;
import com.mujirenben.android.xsh.utils.jcvideolib.HomeListener;
import com.mujirenben.android.xsh.utils.jcvideolib.JCMediaManager;
import com.mujirenben.android.xsh.utils.jcvideolib.JCVideoPlayer;
import com.mujirenben.android.xsh.utils.jcvideolib.JCVideoPlayerStandard;
import com.mujirenben.android.xsh.widget.ODialog;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.NetWorkUtils;
import com.mujirenben.android.common.util.StatusBarUtil;

import retrofit2.http.Path;


/**
 * Created by Administrator on 2017/10/30 0030.
 */
@Route(path = ARouterPaths.ALLIANCE_VIDEO)
public class NewVideoActivity extends BaseActivity implements View.OnClickListener {

    private String playUrl;
    private boolean isHome;
    private JCVideoPlayerStandard jcVideoPlayerStandard;
    private BroadCast broadCast;
    private HomeListener mHomeWatcher;
    private ImageView iv_back;
    private TextView iv_bottom_share;
    private String title;
    private String url;
    private String text;
    private String thumb;
    private boolean isHaveShare;



    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setStatusBarTransparent(this);
        return R.layout.hrz_activity_newvideo_play;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        IntentFilter intentFilter = new IntentFilter();
        broadCast = new BroadCast();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        this.registerReceiver(broadCast, intentFilter);
        registerHomeListener();

        initParams(getIntent().getBundleExtra(Consts.HRZ_ROUTER_BUNDLE));
        inintView();
        inintData();
    }

    private void initParams(Bundle bundle){
        playUrl = bundle.getString(Constants.IntentConstant.INTENT_ID);
        title = bundle.getString(Constants.IntentConstant.TITLE);
        url =bundle.getString(Constants.IntentConstant.LINK_URL);
        text = bundle.getString(Constants.IntentConstant.SEARCH_TXT);
        thumb =bundle.getString(Constants.IntentConstant.ISWXIMG);
        isHaveShare =bundle.getBoolean("IsHaveShare", false);
    }



    private void registerHomeListener() {
        mHomeWatcher = new HomeListener(this);
        mHomeWatcher.setOnHomePressedListener(new HomeListener.OnHomePressedListener() {

            @Override
            public void onHomePressed() {
                //TODO 进行点击Home键的处理
                if (JCMediaManager.instance().mediaPlayer != null && JCMediaManager.instance().mediaPlayer.isPlaying()) {
                    if (jcVideoPlayerStandard.mCurrentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {
                        jcVideoPlayerStandard.stop();
                        isHome = true;
                    }
                }
            }

            @Override
            public void onHomeLongPressed() {
                //TODO 进行长按Home键的处理
            }
        });
        mHomeWatcher.startWatch();
    }
    private void stopVideo() {
        if (JCMediaManager.instance().mediaPlayer != null && JCMediaManager.instance().mediaPlayer.isPlaying()) {
            if (jcVideoPlayerStandard.mCurrentState == JCVideoPlayer.CURRENT_STATE_PLAYING || jcVideoPlayerStandard.mCurrentState == JCVideoPlayer.CURRENT_STATE_PREPAREING) {
                jcVideoPlayerStandard.stop();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopVideo();
        AlibcTradeSDK.destory();
        JCVideoPlayer.releaseAllVideos();
        mHomeWatcher.stopWatch();
        this.unregisterReceiver(broadCast);
    }

    private void inintData() {

        jcVideoPlayerStandard.setUp(playUrl,"");
        if (!NetWorkUtils.isWifi(this)) {

            if ( LoginDataManager.getsInstance(this).isWifiPlay()) {
                LoginDataManager.getsInstance(this).setIsWifyPlay(false);
                showWifiSelectDialog(this, new DialogCallBack() {
                    @Override
                    public void onCallBack() {
                        LoginDataManager.getsInstance(NewVideoActivity.this).setIsWifyPlay(true);
                        jcVideoPlayerStandard.prepareVideo();
                    }
                });
            } else {
                if (!LoginDataManager.getsInstance(NewVideoActivity.this).isWifiPlay()) {
                    jcVideoPlayerStandard.prepareVideo();
                }
            }

        } else {
            jcVideoPlayerStandard.prepareVideo();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏
            iv_back.setVisibility(View.VISIBLE);
            iv_bottom_share.setVisibility(View.VISIBLE);

        } else {
            //横屏
            iv_back.setVisibility(View.GONE);
            iv_bottom_share.setVisibility(View.GONE);
        }
    }

    private void inintView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_bottom_share = (TextView) findViewById(R.id.iv_bottom_share);
        if (isHaveShare){
            iv_bottom_share.setOnClickListener(this);
            iv_bottom_share.setVisibility(View.VISIBLE);
        }
        else
        {
            iv_bottom_share.setVisibility(View.GONE);
        }


        jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.custom_videoplayer_standard);
        jcVideoPlayerStandard.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (isHome) {
            isHome = false;
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.iv_back){
            finish();
        }
    }



    private class BroadCast extends BroadcastReceiver {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {// 开屏
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                // 锁屏
                if (JCMediaManager.instance().mediaPlayer != null && JCMediaManager.instance().mediaPlayer.isPlaying()) {
                    if (jcVideoPlayerStandard.mCurrentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {
                        jcVideoPlayerStandard.stop();
                    }

                }
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                // 解锁
                if (!isHome) {
                    if (JCMediaManager.instance().mediaPlayer != null && !JCMediaManager.instance().mediaPlayer.isPlaying()) {
                        if (jcVideoPlayerStandard.mCurrentState == JCVideoPlayer.CURRENT_STATE_PAUSE) {
                            jcVideoPlayerStandard.start();

                        }
                    }
                }

            }
        }
    }


    /**
     * Wifi下选择
     * @param context
     * @param callback
     * @return
     */
    public  Dialog showWifiSelectDialog(Context context, DialogCallBack callback){
        String title = context.getString(R.string.wifi_title);
        String content = context.getString(R.string.wifi_content);
        String sureStr = context.getString(R.string.dialog_ensure);
        String cancelStr = context.getString(R.string.dialog_cancel);
        return ODialog.showLCDialog(context, getDialogW(context), true, true, title, content, sureStr, cancelStr, -1, -1, callback, null);
    }


    /**
     * 获取dialog宽度
     */
    public static int getDialogW(Context aty) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = aty.getResources().getDisplayMetrics();
        int w = dm.widthPixels - 100;
        // int w = aty.getWindowManager().getDefaultDisplay().getWidth() - 100;
        return w;
    }
}
