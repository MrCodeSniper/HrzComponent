package com.mujirenben.android.xsh.utils.jcvideolib;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.mujirenben.android.xsh.entity.ShangXinVideoBean;
import com.mujirenben.android.common.user.LoginDataManager;

import java.lang.reflect.Constructor;

/**
 * <p>全屏的activity</p>
 * <p>fullscreen activity</p>
 *
 *
 */
public class JCFullScreenActivity extends Activity  {

    static void startActivityFromNormal(Context context, int state, String url, Class videoPlayClass, int type, Object... obj) {
        CURRENT_STATE = state;
        DIRECT_FULLSCREEN = false;
        URL = url;
        VIDEO_PLAYER_CLASS = videoPlayClass;
        OBJECTS = obj;
        mContext=context;
        TYPE=type;
        Intent intent = new Intent(context, JCFullScreenActivity.class);
        context.startActivity(intent);
    }

    /**
     * <p>直接进入全屏播放</p>
     * <p>Full screen play video derictly</p>
     *
     * @param context        context
     * @param url            video mUrl
     * @param videoPlayClass your videoplayer extends JCAbstraceVideoPlayer
     * @param obj            custom param
     */
    public static void startActivity(Context context, String url, Class videoPlayClass, Object... obj) {
        CURRENT_STATE = JCVideoPlayer.CURRENT_STATE_NORMAL;
        URL = url;
        DIRECT_FULLSCREEN = true;
        mContext=context;
        VIDEO_PLAYER_CLASS = videoPlayClass;
        OBJECTS = obj;
        Intent intent = new Intent(context, JCFullScreenActivity.class);
        context.startActivity(intent);
    }

    private JCVideoPlayer mJcVideoPlayer;
    /**
     * 刚启动全屏时的播放状态
     */
    static int CURRENT_STATE = -1;
    public static String URL;
    private static int TYPE;
    static boolean DIRECT_FULLSCREEN = false;//this is should be in videoplayer
    static Class VIDEO_PLAYER_CLASS;
    static Object[] OBJECTS;
    BroadCast broadCast;
    private HomeListener mHomeWatcher;
    private boolean isHome;

    private String orderId;
    private static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View decor = this.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        try {
            Constructor<JCVideoPlayerStandard> constructor = VIDEO_PLAYER_CLASS.getConstructor(Context.class);
            mJcVideoPlayer = constructor.newInstance(this);
            setContentView(mJcVideoPlayer);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mJcVideoPlayer.mIfCurrentIsFullscreen = true;
        mJcVideoPlayer.mIfFullscreenIsDirectly = DIRECT_FULLSCREEN;
        mJcVideoPlayer.setUp(URL, OBJECTS);
        mJcVideoPlayer.setStateAndUi(CURRENT_STATE);
        mJcVideoPlayer.addTextureView();




        if(!LoginDataManager.getsInstance(this).IsFirstFull()&&TYPE==1){
            LoginDataManager.getsInstance(this).setIsFirstFull(true);
            mJcVideoPlayer.iv_full.setVisibility(View.VISIBLE);
        }
        if (mJcVideoPlayer.mIfFullscreenIsDirectly) {
            mJcVideoPlayer.startButton.performClick();
        } else {
            JCVideoPlayer.IF_RELEASE_WHEN_ON_PAUSE = true;
            JCMediaManager.instance().setListener(mJcVideoPlayer);
            if (CURRENT_STATE == JCVideoPlayer.CURRENT_STATE_PAUSE) {
                mJcVideoPlayer.position=(int)JCMediaManager.instance().mediaPlayer.getCurrentPosition();
                JCMediaManager.instance().mediaPlayer.seekTo(JCMediaManager.instance().mediaPlayer.getCurrentPosition());
//                mJcVideoPlayer.setPro();
            }
//
        }

        IntentFilter intentFilter = new IntentFilter();
        broadCast = new BroadCast();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        this.registerReceiver(broadCast, intentFilter);
        registerHomeListener();
    }

    private void registerHomeListener() {
        mHomeWatcher = new HomeListener(this);
        mHomeWatcher.setOnHomePressedListener(new HomeListener.OnHomePressedListener() {

            @Override
            public void onHomePressed() {
                //TODO 进行点击Home键的处理
                if(mJcVideoPlayer!=null) {
                    mJcVideoPlayer.stop();
                    isHome=true;
                }
            }

            @Override
            public void onHomeLongPressed() {
                //TODO 进行长按Home键的处理
            }
        });
        mHomeWatcher.startWatch();
    }

    public void AddCartPage(final ShangXinVideoBean.Goods goods){
//        BuyTimeUtil.SendShowTime(String.valueOf(goods.goodsid),"shangxin", String.valueOf(BaseApplication.getInstance().sxVideoId),this);
//        AlibcShowParams alibcShowParams=new AlibcShowParams(OpenType.Native,false);
//        AlibcAddCartPage alibcAddCartPage=new AlibcAddCartPage(String.valueOf(goods.open_iid));
////        AlibcTaokeParams alibcTaokeParams=new AlibcTaokeParams(Contant.TAOKE_PID,null,null);
//        AlibcTaokeParams alibcTaokeParams=new AlibcTaokeParams();
//        alibcTaokeParams.setPid(Contant.TAOKE_PID);
//        AlibcTrade.show(this, alibcAddCartPage, alibcShowParams, alibcTaokeParams, null, new AlibcTradeCallback() {
//
//            @Override
//            public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
//
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//
//            }
//        });
    }



    public void showTbpage(final ShangXinVideoBean.Goods goods){
//        AlibcDetailPage alibcDetailPage=new AlibcDetailPage(String.valueOf(goods.open_iid));
//        AlibcShowParams alibcShowParams=new AlibcShowParams(OpenType.H5,false);
////        AlibcTaokeParams alibcTaokeParams=new AlibcTaokeParams(Contant.TAOKE_PID,null,null);
//        AlibcTaokeParams alibcTaokeParams=new AlibcTaokeParams();
//        alibcTaokeParams.setPid(Contant.TAOKE_PID);
//        AlibcTrade.show(this, alibcDetailPage, alibcShowParams, alibcTaokeParams, null ,
//                new AlibcTradeCallback() {
//
//
//                    @Override
//                    public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
//                        orderId = alibcTradeResult.payResult.paySuccessOrders.get(0).toString();
//                        new addPlusAsy().execute(goods.goodsid);
//                        ToastUtils.getInstanc(JCFullScreenActivity.this).showToast("购买成功,请到已购列表查看进度");
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg) {
//                        //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
//                    }
//                });


//        TradeService tradeService= AlibabaSDK.getService(TradeService.class);
//
//
//        TaokeParams taokeParams = new TaokeParams(); //若非淘客taokeParams设置为null即可
//        taokeParams.pid = Contant.TAOKE_PID;
//
//        ItemDetailPage itemDetailPage = new ItemDetailPage(goods.open_iid, null);
//        tradeService.show(itemDetailPage, taokeParams, JCFullScreenActivity.this, null, new TradeProcessCallback(){
//            @Override
//            public void onFailure(int code, String msg) {
//
//            }
//
//            @Override
//            public void onPaySuccess(TradeResult tradeResult) {
//                orderId=tradeResult.paySuccessOrders.get(0).toString();
//                new addPlusAsy().execute(goods.goodsid);
//                toastUtil.show("购买成功,请到已购列表查看进度",Toast.LENGTH_SHORT);
//            }});

    }



//    @Override
//    public void proBuy(ShangXinVideoBean.Goods goods) {
//        if (!(boolean)SPUtil.get(JCFullScreenActivity.this, Contant.User.USER_ISLOGIN,false) ) {
//            Intent intent=new Intent();
//            intent.setClass(mContext,LoginActivity.class);
//            mContext.startActivity(intent);
//            mJcVideoPlayer.noLogin();
//
//            return;
//        }
//        BuyTimeUtil.SendShowTime(String.valueOf(goods.goodsid),"shangxin", String.valueOf(BaseApplication.getInstance().sxVideoId),this);
//        showTbpage(goods);
//        MobclickAgent.onEvent(JCFullScreenActivity.this,"Hrz_shangxinvideo_buy");
//    }
//
//    @Override
//    public void proCart(ShangXinVideoBean.Goods goods) {
//        if (!(boolean)SPUtil.get(JCFullScreenActivity.this, Contant.User.USER_ISLOGIN,false) ) {
//            Intent intent=new Intent();
//            intent.setClass(mContext,LoginActivity.class);
//            mContext.startActivity(intent);
//            mJcVideoPlayer.noLogin();
//            return;
//        }
//        MobclickAgent.onEvent(JCFullScreenActivity.this,"Hrz_shangxinvideo_car");
//  //      AddCartPage(goods);
//
//    }
//





    private class BroadCast extends BroadcastReceiver {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {// 开屏
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                // 锁屏
                mJcVideoPlayer.stop();
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                // 解锁
                if(!isHome){
                    if(mJcVideoPlayer!=null){
                        mJcVideoPlayer.start();
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isHome){
            isHome=false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // mJcVideoPlayer.stop();
        AlibcTradeSDK.destory();
        this.unregisterReceiver(broadCast);
        mHomeWatcher.stopWatch();
    }

    @Override
    public void onBackPressed() {

        //this.unregisterReceiver(broadCast);
        mJcVideoPlayer.backFullscreen();
        JCVideoPlayer.releaseAllVideos();

    }

    @Override
    protected void onPause() {
        super.onPause();
 //       JCVideoPlayer.releaseAllVideos();
//        finish();
    }
}
