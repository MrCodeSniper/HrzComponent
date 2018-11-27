package com.mujirenben.android.common.util.alipayHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.mujirenben.android.common.entity.AuthResult;
import com.mujirenben.android.common.entity.PayResult;
import com.mujirenben.android.common.event.AlipayEvent;
import com.mujirenben.android.common.event.VipLevelChangedEvent;
import com.mujirenben.android.common.util.ArmsUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * @author: panrongfu
 * @date: 2018/8/17 19:23
 * @describe: 阿里支付帮助类
 */

public class AlipayHelper {

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private Context context;
    /**
     * app支付请求参数字符串，主要包含商户的订单信息，key=value形式，以&连接。
     */
    private String orderInfo;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    //对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ArmsUtils.makeText(context, "支付成功");
                        AlipayEvent alipayEvent = new AlipayEvent();
                        alipayEvent.setResultInfo(resultInfo);
                        alipayEvent.setResultStatus(resultStatus);
                        EventBus.getDefault().post(alipayEvent);

                        // 通知刷新
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new VipLevelChangedEvent());
                                           }
                        }, 1 * 1000);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        AlipayEvent alipayEvent = new AlipayEvent();
//                        alipayEvent.setResultInfo(resultInfo);
//                        alipayEvent.setResultStatus(resultStatus);
//                        EventBus.getDefault().post(alipayEvent);
                        Log.e(">>>>",resultInfo+"");
                        ArmsUtils.makeText(context, "支付失败");
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        ArmsUtils.makeText(context,"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()));
                    } else {
                        // 其他状态值则为授权失败
                        ArmsUtils.makeText(context,"授权失败" + String.format("authCode:%s", authResult.getAuthCode()));
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 唤起支付宝支付
     */
    public void callAliPay(){

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) context);
                /**
                 * 1、app支付请求参数字符串，主要包含商户的订单信息，key=value形式，以&连接。
                 *
                 * 2、用户在商户app内部点击付款，是否需要一个loading做为在钱包唤起之前的过渡，
                 *   这个值设置为true，将会在调用pay接口的时候直接唤起一个loading，
                 *   直到唤起H5支付页面或者唤起外部的钱包付款页面loading才消失。
                 *  （建议将该值设置为true，优化点击付款到支付唤起支付页面的过渡过程。）
                 */
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    public AlipayHelper(Builder builder) {
        this.orderInfo = builder.orderInfo;
        this.context = builder.context;
    }

    public static Builder getBuilder(Context context){
        return new Builder(context);
    }

    public static class  Builder{
        private Context context;
        private String orderInfo;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setOrderInfo(String orderInfo) {
            this.orderInfo = orderInfo;
            return this;
        }

        public AlipayHelper build(){
            return new AlipayHelper(this);
        }
    }
}
