package com.mujirenben.android.common.pay;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.ch.android.common.R;
import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.alipayHelper.AlipayHelper;
import com.mujirenben.android.common.widget.CustomPopupWindow;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PayHelper {

    private static final String TAG = "PayHelper";

    private Context mContext;
    private static PayHelper sInstance;

    IRepositoryManager mRepositoryManager;
    RxErrorHandler mErrorHandler;

    private PayHelper(Context context) {
        mContext = context.getApplicationContext();
        mRepositoryManager = BaseApplication.getApplication().getAppComponent().repositoryManager();
        mErrorHandler = BaseApplication.getApplication().getAppComponent().rxErrorHandler();
//        if (Consts.USE_SAND_BOX_ALIPAY) {
//            EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
//        }
    }

    public static PayHelper getDefault(Context context) {
        if (sInstance == null) {
            synchronized (PayHelper.class) {
                if (sInstance == null) {
                    sInstance = new PayHelper(context);
                }
            }
        }
        return sInstance;
    }

    public interface GenerateOrderCallback {
        void onGenerateCompleted(OrderResultBean data);
        void onGenerateFailed(Throwable error);
    }

    public void generatorOrder(Activity hostActivity, GenerateOrderCallback callback, HashMap<String, String> params) {
        RetrofitUrlManager.getInstance().putDomain("goodOrder", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        HashMap<String,String> orderMap = HttpParamUtil.getCommonSignParamMap(mContext, params);

        String postJson = new Gson().toJson(orderMap);
        RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postJson);
        mRepositoryManager.obtainRetrofitService(CommitOrderService.class).goodOrder(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<OrderResultBean>(mErrorHandler) {
                    @Override
                    public void onNext(OrderResultBean orderResultBean) {

                        Log.i(TAG, "generatorOrder >> orderResultBean = " + orderResultBean);

                        if (callback != null) {
                            if (orderResultBean == null || orderResultBean.getData() == null) {
                                callback.onGenerateFailed(new NullPointerException());
                            } else {
                                callback.onGenerateCompleted(orderResultBean);
                            }
                        }

                        // 弹框选择支付方式
                        showPopupWindow(hostActivity, orderResultBean);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i(TAG, "generatorOrder >> error = " + t);
                        super.onError(t);
                        if (callback != null) {
                            callback.onGenerateFailed(t);
                        }
                    }
                });
    }

    /**
     * 获取支付宝支付参数orderInfo
     * @param map
     * @return
     */
    public void alipayReq(Activity hostActivity, HashMap<String,String> map){
        mRepositoryManager.obtainRetrofitService(CommitOrderService.class).alipayReq(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<PayRequestBean>(mErrorHandler) {
                    @Override
                    public void onNext(PayRequestBean requestBean) {
                        Log.i(TAG, "alipayReq >> requestBean = " + requestBean);
                        if (requestBean != null && requestBean.getData() != null) {
                            AlipayHelper.getBuilder(hostActivity)
                                    .setOrderInfo(requestBean.getData().getSdkStr())
                                    .build()
                                    .callAliPay();
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i(TAG, "alipayReq >> error = " + t);
                        super.onError(t);
                    }
                });
    }

    /**
     * 从底部弹出popwindow
     */
    public void showPopupWindow(Activity hostActivity, OrderResultBean orderResultBean) {
        if(hostActivity == null || hostActivity.isFinishing())return;
        View popupView = LayoutInflater.from(hostActivity).inflate(R.layout.pay_type_pop_layout, null);
        CustomPopupWindow.builder(hostActivity)
                .contentView(popupView)
                .isFocus(true)
                .customListener((contentView, popupWindow) -> initClickEvent(hostActivity, contentView, popupWindow, orderResultBean))
//                .animationStyle(R.style.AnimBottom)
                .isOutsideTouch(true)
                .build()
                .show();
    }
    /**
     * 弹窗点击事件
     * @param contentView
     * @param popupWindow
     */
    private void initClickEvent(Activity hostActivity, View contentView, CustomPopupWindow popupWindow, OrderResultBean orderResultBean) {
        ImageView payPopCloseIv = contentView.findViewById(R.id.pay_pop_close_iv);
        ImageView  payAlipayCheckIv =  contentView.findViewById(R.id.pay_alipay_check_iv);
        ImageView  payWxCheckIv = contentView.findViewById(R.id.pay_wx_check_iv);
        TextView payConfirmTv = contentView.findViewById(R.id.pay_confirm_tv);

        payPopCloseIv.setOnClickListener(v -> {
            popupWindow.dismiss();
        });
        payWxCheckIv.setOnClickListener(v->{
            ArmsUtils.makeText(mContext,"暂不支持微信支付");
        });

        payConfirmTv.setText("￥" + orderResultBean.getData().getAmount() + " 确认付款");
        payConfirmTv.setOnClickListener(v -> {
            alipayReq(hostActivity, orderResultBean.getData().getOrderCode());
            popupWindow.dismiss();
        });
    }

    /**
     * 获取支付宝支付参数orderInfo
     * @param orderCode
     */
    private void alipayReq(Activity hostActivity, String orderCode) {
        HashMap<String,String> map = new HashMap<>();
        map.put("walletOrderCode",orderCode);
        map.put("payway","1");
        RetrofitUrlManager.getInstance().putDomain("alipayReq", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        HashMap<String,String> alipayReqMap = HttpParamUtil.getCommonSignParamMap(mContext, map);
        alipayReq(hostActivity, alipayReqMap);
    }

}
