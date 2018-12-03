package com.mujirenben.android.common.misc;

import android.app.Activity;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.bumptech.glide.Glide;

import com.ch.android.common.R;

import com.kepler.jd.Listener.OpenAppAction;

import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.arounter.HrzRouter;
import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.event.LoginStatusEvent;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.service.GoodTaobaoLinkResult;
import com.mujirenben.android.common.service.GoodsService;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.AppInfoUtils;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.CommonUtils;
import com.mujirenben.android.common.util.GoodsUtil;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.rxtools.RxDataTool;
import com.mujirenben.android.common.util.rxtools.RxTextTool;
import com.mujirenben.android.common.util.sensorHelper.SensorHelper;
import com.mujirenben.android.common.util.wxHelper.ShareDialogHelper;
import com.mujirenben.android.common.util.wxHelper.WeiXinHelper;
import com.mujirenben.android.common.util.wxHelper.listener.OnClickLinkListener;
import com.mujirenben.android.common.util.wxHelper.listener.OnClickSessionListener;
import com.mujirenben.android.common.util.wxHelper.listener.OnClickTimeLineListener;
import com.mujirenben.android.common.widget.DialogUtils;
import com.mujirenben.android.common.widget.JdPopDialog;
import com.mujirenben.android.common.widget.dialog.LinkPlatformPopDialog;
import com.mujirenben.android.common.widget.dialog.RxDialog;
import com.mujirenben.android.common.widget.dialog.dialogpopmanager.DialogUIUtils;
import com.mujirenben.android.common.widget.dialog.dialogpopmanager.bean.BuildBean;
import com.mujirenben.android.common.widget.dialog.dialogpopmanager.utils.DialogManager;
import com.mujirenben.android.thirdsdk.JdSdk.JdSdkRouter;
import com.mujirenben.android.thirdsdk.alibabaSDK.AlibabaSDK;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

public class KoulingHelper implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "KoulingHelper";

    private static final String KOULING_DOMAIN = "kouling";

    private static final String RESULT_CODE_OK = "00000";

    private static final int KOULING_TYPE_TAOBAO = 0;
    private static final int KOULING_TYPE_JD = 1;
    private static final int KOULING_TYPE_UNKOWN = 2;

    private boolean mShouldParseKouling = false;

    private static KoulingHelper sInstance;

    private Context mContext;
    private ClipboardManager mCm;
    private IRepositoryManager mRepoMgr;
    private BuildBean buildBean;

    private Toast mLoadingToast;
    private RxDialog mKoulingResultDialog;
    private RxDialog mKoulingErrorDialog;
    private static boolean isShowKoulingDialog = true;

    public interface Callback {
        void onKoulingGenerated(String kouling, String shareUrl);

        default void onKoulingFailed() {
        }
    }


    public Handler mHandler = new Handler();

    public static KoulingHelper get(Context context) {
        if (sInstance == null || sInstance.mContext==null) {
            synchronized (KoulingHelper.class) {
                if (sInstance == null || sInstance.mContext==null) {
                    sInstance = new KoulingHelper(context);
                }
            }
        }
        return sInstance;
    }

    private KoulingHelper(Context context) {
        mContext = context.getApplicationContext();
        mCm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        mRepoMgr = BaseApplication.getApplication().getAppComponent().repositoryManager();
    }

    public void init() {
        BaseApplication.getApplication().registerActivityLifecycleCallbacks(this);
        EventBus.getDefault().register(this);
    }

    public void destroy() {
        BaseApplication.getApplication().unregisterActivityLifecycleCallbacks(this);
        EventBus.getDefault().unregister(this);
    }

    private HashMap<String, String> getGenerationKoulingParams(long productId, String platformId, int idType, int platform) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("productId", productId + "");
        params.put("platformId", platformId + "");
        params.put("idType", idType + "");
        params.put("platform", platform + "");

        HashMap<String, String> resultMap = HttpParamUtil.getCommonSignParamMap(mContext, params);

        return resultMap;
    }

    private Map<String, String> getParseKoulingParams(String tpwdCode) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tpwdCode", tpwdCode);

        Map<String, String> resultMap = HttpParamUtil.getCommonSignParamMap(mContext, params);

        return resultMap;
    }

    public void generateKouling(long productId, String platformId, int idType, int platform, Callback callback) {
        Log.i(TAG, "generateKouling >> productId=" + productId + " platformId=" + platformId + " idType=" + idType + " platform=" + platform);
        RetrofitUrlManager.getInstance().putDomain(KOULING_DOMAIN, Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        Observable<KoulingGenerateResult> result =
                mRepoMgr.obtainRetrofitService(KoulingService.class)
                        .generateKouling(getGenerationKoulingParams(productId, platformId, idType, platform));
        result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KoulingGenerateResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(KoulingGenerateResult koulingGenerateResult) {
                        Log.i(TAG, "onNext >> koulingGenerateResult=" + koulingGenerateResult);
                        if (callback == null) {
                            return;
                        }
                        if (koulingGenerateResult.isSuccess()) {
                            callback.onKoulingGenerated(
                                    koulingGenerateResult.getData().getTpwdCode(),
                                    koulingGenerateResult.getData().getShareUrl());
                        } else {
                            ArmsUtils.makeText(mContext, "商品信息获取失败，我们会尽快修复\n" +
                                    "错误商品ID:" + productId);
                            callback.onKoulingFailed();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError >> e=" + e.getMessage());
                        if (callback == null) {
                            return;
                        }
                        callback.onKoulingFailed();
                    }

                    @Override
                    public void onComplete() {
                        // Do nothing.
                    }
                });
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    private String mText;
    private boolean mIsActive;

    @Override
    public void onActivityResumed(Activity activity) {
        Log.i(TAG, "onActivityResumed >> activity=" + activity);


        if(!SpUtil.getBoolenValue(null, "showed_guide_interface", mContext)){
            return;
        }

        if (!mCm.hasPrimaryClip()) return;

        Log.d("show" , "回到前台"+ String.valueOf(!isShowKoulingDialog));
        if (!isShowKoulingDialog){
            return;
        }

        if (!mIsActive) {
            mIsActive = true;
            Log.i(TAG, "后台切换到前台");

            ClipData data = mCm.getPrimaryClip();

            if (data == null || data.getItemCount() <= 0) return;

            ClipData.Item item = data.getItemAt(0);
            String text = String.valueOf(item.getText());
            if (!TextUtils.isEmpty(text)) {
                mText = text;
                classifyText(text, activity);
            }

            Log.i(TAG, "onActivityResumed >> text=" + text);
        } else if (mShouldParseKouling) {
            mShouldParseKouling = false;
            classifyText(mText, activity);
        }
    }

    private void classifyText(String text, Activity activity) {
        if (mayBeTaoKouling(text) && isShowKoulingDialog) {
            loadKoulingInfo(KOULING_TYPE_TAOBAO, text, activity);
        } else if (mayBeJingKouling(text)) {
            loadKoulingInfo(KOULING_TYPE_JD, text, activity);
        } else {
            loadKoulingInfo(KOULING_TYPE_UNKOWN, text, activity);
        }
    }


    @Override
    public void onActivityPaused(Activity activity) {

        if(buildBean!=null){
            buildBean.dissmiss();
        }

        if (mKoulingResultDialog != null) {
            mKoulingResultDialog.dismiss();
        }
        if (mKoulingErrorDialog != null) {
            mKoulingErrorDialog.dismiss();
        }
        if (linkPlatformPopDialog != null) {
            linkPlatformPopDialog.dismiss();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (!AppInfoUtils.isAppOnForeground(activity)) {
            //app 进入后台
            mIsActive = false;//记录当前已经进入后台
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    private boolean mayBeTaoKouling(String text) {
        return Pattern.matches(".*(￥[^￥]+￥|€[^€]+€).*|.*https?(://.*tb\\..*/[^\\?\\s]+|://.*taobao\\..*/[^\\?\\s]+.*|://.*tmall\\..*/[^\\?\\s]+|://.*zmnxbc\\..*/[^?\\s]+)", text);
    }

    private boolean mayBeJingKouling(String text) {
        return Pattern.matches(".*jd.*/([0-9]+)\\.html.*", text);
    }

    private void loadKoulingInfo(int koulingType, String kouling, Activity activity) {

        if (koulingType != KOULING_TYPE_UNKOWN) {
            showLoadingDialog(activity);
        }

        RetrofitUrlManager.getInstance().putDomain(KOULING_DOMAIN, Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        Observable<SimplifiedKoulingBean> info = mRepoMgr.obtainRetrofitService(KoulingService.class)
                .loadSimplifiedKoulingInfo(getParseKoulingParams(kouling));
        info.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimplifiedKoulingBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SimplifiedKoulingBean koulingBean) {
                        Log.i(TAG, "onNext >> koulingBean=" + koulingBean);

                        if (koulingBean.isSuccess()) {
                            showResultDialog(koulingBean, activity);
                        } else {
                            if (koulingType != KOULING_TYPE_UNKOWN ) {
                                showErrorDialog(activity);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError >> e=" + e.getMessage());
                        if (koulingType != KOULING_TYPE_UNKOWN) {
                            showErrorDialog(activity);
                        }
                    }

                    @Override
                    public void onComplete() {
                        // Do nothing.
                    }
                });
    }

    private void showLoadingDialog(Activity activity) {
        mLoadingToast = generateToast("商品获取中...");
        mLoadingToast.show();
    }


    private void showResultDialog(SimplifiedKoulingBean koulingBean, Activity activity) {
        if (mLoadingToast != null) {
            mLoadingToast.cancel();
            mLoadingToast = null;
        }

        SimplifiedKoulingBean.Data data = koulingBean.getData();

        int koulingType = data.getPlatform() == 2 ? KOULING_TYPE_TAOBAO : KOULING_TYPE_JD;

        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_taokouling_result_show, null);
        ImageView goodsImageIV = dialogView.findViewById(R.id.iv_goods_image);
        TextView platformTV = dialogView.findViewById(R.id.tv_platform);
        TextView goodsDescTV = dialogView.findViewById(R.id.tv_goods_description);
        TextView couponsTV = dialogView.findViewById(R.id.tv_coupons);
        TextView prePriceTV = dialogView.findViewById(R.id.tv_pre_price);
        TextView postPriceTV = dialogView.findViewById(R.id.tv_post_price);
        TextView shareToProfitTV = dialogView.findViewById(R.id.tv_share_to_profit);
        TextView buyTV = dialogView.findViewById(R.id.tv_buy);
        ImageView closeIV = dialogView.findViewById(R.id.iv_close);

        closeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buildBean != null && buildBean.dialog != null) {
                    buildBean.dialog.dismiss();
                    clearTaoKouLingInClipBoard();
                }

                writeSensorData("关闭");
            }
        });

        buyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginDataManager ldm = LoginDataManager.getsInstance(mContext);
                if (ldm.isLogin()) {
                    if (koulingBean.getData().getIdType() == 2) { // 第三方商品，直接跳转
                        requestTaobaoLink(activity, koulingBean);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString(Consts.PLATFORM_ID_INTENT_STR, data.getPlatform() + "");
                        bundle.putString(Consts.GOODS_ID_INTENT_STR, data.getId() + "");
                        ARouter.getInstance().build(ARouterPaths.GOODS_DETAIL)
                                .withBundle(Consts.HRZ_ROUTER_BUNDLE, bundle)
                                .navigation(activity);
                    }
                    if (buildBean != null && buildBean.dialog != null) {
                        buildBean.dialog.dismiss();
                        clearTaoKouLingInClipBoard();
                    }
                    mText = null;
                } else {
                    if (koulingBean.getData().getIdType() == 2) {
                        ARouter.getInstance()
                                .build(ARouterPaths.LOGIN_MAIN_MINE)
                                .withString(Consts.LOGIN_SOURCE_KEY, "淘口令")
                                .withInt("request_code", LoginStatusEvent.LOGIN_REQUEST_CODE_KOULING_BUY)
                                .navigation(mContext);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString(Consts.PLATFORM_ID_INTENT_STR, data.getPlatform() + "");
                        bundle.putString(Consts.GOODS_ID_INTENT_STR, data.getId() + "");
                        ARouter.getInstance().build(ARouterPaths.GOODS_DETAIL)
                                .withBundle(Consts.HRZ_ROUTER_BUNDLE, bundle)
                                .navigation(activity);
                    }
                }

                writeSensorData("购买");
            }
        });

        //分享按钮
        shareToProfitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeSensorData("分享");


                LoginDataManager ldm = LoginDataManager.getsInstance(mContext);
                if (ldm.isLogin()) {
                    generateKouling(koulingBean, new Callback() {
                        @Override
                        public void onKoulingGenerated(String kouling, String shareUrl) {
                            ArrayList<String> imgs = new ArrayList<>();
                            imgs.add(data.getImg());
                            showShareDialog(
                                    activity,
                                    shareUrl,
                                    koulingType,
                                    kouling,
                                    data.getCommission(),
                                    GoodsUtil.getPlatformById(data.getPlatform()),
                                    data.getName(),
                                    data.getOrgPrice(),
                                    data.getOrgPrice() - data.getCoupon(),
                                    data.getCoupon(),
                                    imgs);
                        }
                    });

                    if (buildBean != null && buildBean.dialog != null) {
                        buildBean.dialog.dismiss();
                    }

                    mText = null;

                } else {
                    ARouter.getInstance()
                            .build(ARouterPaths.LOGIN_MAIN_MINE)
                            .withString(Consts.LOGIN_SOURCE_KEY, "淘口令")
                            .withInt("request_code", LoginStatusEvent.LOGIN_REQUEST_CODE_KOULING_SHARE)
                            .navigation(mContext);
                }
            }
        });



        Glide.with(activity).load(data.getImg()).into(goodsImageIV);
        if(activity!=null){
            Glide.with(activity).load(data.getImg()).into(goodsImageIV);
        }

        platformTV.setText(GoodsUtil.getPlatformById(data.getPlatform()));
        goodsDescTV.setText(data.getName());
        couponsTV.setText("券 " + CommonUtils.takeOutLastZero(data.getCoupon()));
        shareToProfitTV.setText("分享奖励￥" + CommonUtils.takeOutLastZero(data.getCommission()));

        if (Float.compare(data.getCoupon(), 0.0f) == 0) {
            couponsTV.setVisibility(View.GONE);
            prePriceTV.setVisibility(View.GONE);
        }

        RxTextTool.getBuilder("", mContext)
                .append("¥ ")
                .setForegroundColor(mContext.getResources().getColor(R.color.main_color))
                .append(CommonUtils.takeOutLastZero(Float.valueOf(RxDataTool.format2Decimals("" + (data.getOrgPrice() - data.getCoupon())))))
                .setForegroundColor(mContext.getResources().getColor(R.color.main_color))
                .setBold()
                .setProportion(2f)
                .into(postPriceTV);

        RxTextTool.getBuilder("", mContext)
                .append("¥" + CommonUtils.takeOutLastZero(Float.valueOf(RxDataTool.format2Decimals("" + data.getOrgPrice()))))
                .setStrikethrough()
                .into(prePriceTV);


        com.orhanobut.logger.Logger.e("淘口令弹窗");
        buildBean = DialogUIUtils.showTaoKouLingDialog(activity, dialogView);
        if (buildBean.dialog != null){
//            buildBean.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialog) {
//
//                }
//            });
            buildBean.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    clearTaoKouLingInClipBoard();
                    com.orhanobut.logger.Logger.e("淘口令弹窗dismiss");
                }
            });
        }

        buildBean.setmLevel(5);
        DialogManager.getInstance().pushToQueue(buildBean);
    }

    private void clearTaoKouLingInClipBoard(){
        if (mCm != null){
            ClipData clipData = ClipData.newPlainText("", "");
            mCm.setPrimaryClip(clipData);
        }
    }


    private void generateKouling(SimplifiedKoulingBean data1, final Callback callback) {

        final DialogUtils loadingDialog = new DialogUtils(mContext, R.layout.common_loading_toast, "");
        loadingDialog.show();

        KoulingHelper koulingHelper = KoulingHelper.get(mContext);
        koulingHelper.generateKouling(
                data1.getData().getId(),
                data1.getData().getPlatformPid(),
                data1.getData().getIdType(),
                data1.getData().getPlatform(),
                new Callback() {
                    @Override
                    public void onKoulingGenerated(String kouling, String shareUrl) {
                        loadingDialog.hide();

                        if (callback != null) {
                            callback.onKoulingGenerated(kouling, shareUrl);
                        }
                    }

                    @Override
                    public void onKoulingFailed() {
                        loadingDialog.hide();
                    }
                });
    }


    private void showErrorDialog(Activity activity) {
        if (mLoadingToast != null) {
            mLoadingToast.cancel();
            mLoadingToast = null;
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                buildBean=DialogUIUtils.showKoulingErrorDialog(activity,null);
                buildBean.setmLevel(5);
                DialogManager.getInstance().pushToQueue(buildBean);
            }
        });
    }




    public void showShareDialog(Activity activity1,
                                String shareUrl,
                                int platform,
                                String kouling,
                                float commission,
                                String label,
                                String goodsName,
                                float prePrice,
                                float afterPrice,
                                float coupons,
                                ArrayList<String> imageUrls) {


        if(imageUrls.size()==0){
            ArmsUtils.makeText(mContext,"分享链接为空");
            return;
        }

        ShareDialogHelper.getBuilder(activity1)
                .setDialogTitle("标题")
                .setDialogContent("内容")
                .setOnLinkListener(new OnClickLinkListener() {
                    @Override
                    public void onClick() {
                        ClipData data1 = ClipData.newPlainText(null, kouling);
                        if (mCm != null) {
                            mCm.setPrimaryClip(data1);
                        }

                        ArmsUtils.makeText(mContext, "复制成功");
                    }
                })
                .setOnSessionListener(new OnClickSessionListener() {
                                          @Override
                                          public void onClick() {
                                              if (platform == Const.Platform.JD) {
                                                  WeiXinHelper.getBuilder(mContext)
                                                          .setWebPageUrl(kouling)
                                                          .setTitle(goodsName)
                                                          .setWebPageThumbPath(imageUrls.get(0))
                                                          .setDescription(activity1.getString(R.string.jd_share_text))
                                                          .build()
                                                          .shareWebPageTo(WeiXinHelper.ShareToType.SESSION);
                                              } else {
                                                  ARouter.getInstance().build(ARouterPaths.GOODS_SHARE)
                                                          .withInt("type", 0)
                                                          .withBoolean("is_taokouling", true)
                                                          .withBoolean("joint_share_style", true)
                                                          .withString("taokouling", kouling)
                                                          .withStringArrayList("image_urls", imageUrls)
                                                          .withFloat("commission", commission)
                                                          .withString("label", label)
                                                          .withString("goods_name", goodsName)
                                                          .withFloat("pre_price", prePrice)
                                                          .withFloat("after_price", afterPrice)
                                                          .withFloat("coupons", coupons)
                                                          .withString("qr_code_url", shareUrl)
                                                          .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                                          .navigation(mContext);
                                              }
                                          }
                                      }
                )
                .setOnTimeLineListener(new OnClickTimeLineListener() {
                    @Override
                    public void onClick() {
                        if (platform == Const.Platform.JD || platform==Const.Platform.MGJ || platform == Const.Platform.VIP) {
                            WeiXinHelper.getBuilder(mContext)
                                    .setWebPageUrl(kouling)
                                    .setTitle(goodsName)
                                    .setWebPageThumbPath(imageUrls.get(0))
                                    .setDescription(activity1.getString(R.string.jd_share_text))
                                    .build()
                                    .shareWebPageTo(WeiXinHelper.ShareToType.TIMELINE);
                        } else {
                            ARouter.getInstance().build(ARouterPaths.GOODS_SHARE)
                                    .withInt("type", 1)
                                    .withBoolean("is_taokouling", true)
                                    .withBoolean("joint_share_style", true)
                                    .withString("taokouling", kouling)
                                    .withStringArrayList("image_urls", imageUrls)
                                    .withFloat("commission", commission)
                                    .withString("label", label)
                                    .withString("goods_name", goodsName)
                                    .withFloat("pre_price", prePrice)
                                    .withFloat("after_price", afterPrice)
                                    .withFloat("coupons", coupons)
                                    .withString("qr_code_url", shareUrl)
                                    .withFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    .navigation(mContext);
                        }
                    }

    }
                )
                .build()
                .showDialog(false);

    }

    private Toast generateToast(String msg) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.toast_kouling, null);
        ((TextView)rootView.findViewById(R.id.tv_message)).setText(msg);

        Toast toast = new Toast(mContext);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(rootView);
        toast.setGravity(Gravity.CENTER, 0, 0);

        return toast;
    }

    private void requestTaobaoLink(Activity activity1, SimplifiedKoulingBean koulingBean1) {
        HashMap<String, String> linkParamMap = new HashMap<>();
        linkParamMap.put("productId", koulingBean1.getData().getId() + "");
        linkParamMap.put("platformId", koulingBean1.getData().getPlatformPid());
        linkParamMap.put("platform", koulingBean1.getData().getPlatform() + "");
        linkParamMap.put("idType", koulingBean1.getData().getIdType() + "");

        RetrofitUrlManager.getInstance().putDomain("server_goods_detail", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        HashMap<String, String> paramsMap = HttpParamUtil.getCommonSignParamMap(mContext, linkParamMap);
        Observable.just(mRepoMgr.obtainRetrofitService(GoodsService.class).getTaobaoLink(paramsMap))
                .flatMap(new Function<Observable<GoodTaobaoLinkResult>, ObservableSource<GoodTaobaoLinkResult>>() {
                    @Override
                    public ObservableSource<GoodTaobaoLinkResult> apply(Observable<GoodTaobaoLinkResult> goodTaobaoLinkResultObservable) throws Exception {
                        return goodTaobaoLinkResultObservable;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GoodTaobaoLinkResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GoodTaobaoLinkResult goodTaobaoLinkResult) {
                        jumpToThirdPartyApp(activity1, goodTaobaoLinkResult, koulingBean1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ArmsUtils.makeText(mContext, "转链失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void jumpToThirdPartyApp(Activity activity1, GoodTaobaoLinkResult goodTaobaoLinkResult, SimplifiedKoulingBean koulingBean1) {
        if (Const.Platform.TAOBAO == koulingBean1.getData().getPlatform()) {
            showDialog(activity1, koulingBean1.getData().getCommission() + "", koulingBean1.getData().getCoupon() + "", "淘宝");
            if (goodTaobaoLinkResult.getData() == null || goodTaobaoLinkResult.getData().getClickUrl() == null)
                return;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    AlibabaSDK.routeUrl(
                            activity1, goodTaobaoLinkResult.getData().getClickUrl(), new AlibcTradeCallback() {
                                @Override
                                public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {

                                }

                                @Override
                                public void onFailure(int i, String s) {

                                }
                            });
                }
            },1000);
        } else if (Const.Platform.JD == koulingBean1.getData().getPlatform()) {

            //京东弹窗
            if (LoginDataManager.getsInstance(activity1).getUserReadJdAggrement()) {
                JdPopDialog jdPopDialog = new JdPopDialog(activity1, R.style.Dialog_Fullscreen);
                jdPopDialog.setJdDialogInterface(new JdPopDialog.JdDialogInterface() {
                    @Override
                    public void cancel() {
                        jdPopDialog.cancel();
                        LoginDataManager.getsInstance(activity1).setUserReadJdAggrement(false);
                    }

                    @Override
                    public void read() {
                        HrzRouter.getsInstance(mContext).navigation(Consts.HRZ_JD_READ_AGGREEMENT);
                        jdPopDialog.cancel();
                        LoginDataManager.getsInstance(activity1).setUserReadJdAggrement(false);
                    }
                });
                jdPopDialog.show();
            } else {
                showDialog(activity1, koulingBean1.getData().getCommission() + "", koulingBean1.getData().getCoupon() + "", "京东");
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        JdSdkRouter.openUrlToJD(
                                goodTaobaoLinkResult.getData().getClickUrl(), activity1, new OpenAppAction() {
                                    @Override
                                    public void onStatus(int i) {
                                        Log.e("xxx",i+"xxx");
                                    }
                                });
                    }
                }, 1500);
            }
        }
    }

    private LinkPlatformPopDialog linkPlatformPopDialog;

    private void showDialog(Activity activity1, String shareEarn, String coupon, String platform) {
        linkPlatformPopDialog = new LinkPlatformPopDialog(
                activity1,
                R.style.Dialog_Fullscreen);
        linkPlatformPopDialog.setEarnAmount(TextUtils.isEmpty(shareEarn) ? "0.00" : shareEarn);
        linkPlatformPopDialog.setSaveAmount(TextUtils.isEmpty(coupon) ? "0.00" : coupon);
        linkPlatformPopDialog.setPlatForm(platform);
        linkPlatformPopDialog.show();
    }

    // 关闭、分享、购买
    private void writeSensorData(String action) {
        HashMap<String,Object> sensorMap = new HashMap<>();
        sensorMap.put("click", action);
        SensorHelper.uploadTrack("command", sensorMap);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(LoginStatusEvent event) {
        if (event.isLogin()) {
            if (event.getRequestCode() == LoginStatusEvent.LOGIN_REQUEST_CODE_KOULING_BUY
                    || event.getRequestCode() == LoginStatusEvent.LOGIN_REQUEST_CODE_KOULING_SHARE) {
                Log.i("jan3", "code = " + event.getRequestCode());
                mShouldParseKouling = true;
            }
        }
    }

    /**
     * 设置是否在当前页面显示淘口令
     * @param
     */
    public static void setIsShowTaoKoulingDialog(boolean isShow){
        isShowKoulingDialog = isShow;
        Log.d("show",String.valueOf(isShow));
    }
}
