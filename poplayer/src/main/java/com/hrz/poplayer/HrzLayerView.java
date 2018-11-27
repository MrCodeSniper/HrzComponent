package com.hrz.poplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.hrz.poplayer.generator.OrderMaker;
import com.hrz.poplayer.impl.PushManagerImpl;
import com.hrz.poplayer.impl.TimerManagerImpl;
import com.hrz.poplayer.interfaces.LayerLifecycle;
import com.hrz.poplayer.interfaces.WebViewConfig;
import com.hrz.poplayer.log.LogInvoker;
import com.hrz.poplayer.log.Logs;
import com.hrz.poplayer.proxy.LayerLifeCycleProxy;
import com.hrz.poplayer.strategy.ILayerStrategy;
import com.hrz.poplayer.strategy.LayerStrategyChooser;
import com.hrz.poplayer.strategy.NativeLayerStrategyImpl;
import com.hrz.poplayer.strategy.WebViewLayerStrategyImpl;


/**
 * Created by mac on 2018/7/10.
 */

public class HrzLayerView extends View {

    static final String TAG="HrzLayerView";

    private boolean isShow=false;

    public static int STATE_WEBVIEW=1;
    public static int STATE_DIALOG=0;
    public int state=-1;

    private ILayerStrategy iLayerStrategy;

    private Context mContext;

    private LayerLifecycle proxy;

    public HrzLayerView(Context context, int state) {
        super(context);
        mContext=context;
        this.state=state;
    }

    public HrzLayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }

    public HrzLayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
    }


    public void initLayerView(WebViewConfig webViewConfig) {
        //日志类初始化
        Logs logs=new Logs();
        // 初始化时 会实例化功能接口的实现
        OrderMaker orderMaker=new OrderMaker(logs);

        HrzLayer.Builder builder=HrzLayer.builder()
                .setPushManagerImpl((PushManagerImpl) orderMaker.getPushManagerImpl())
                .setTimerManagerImpl((TimerManagerImpl) orderMaker.getTimerManagerImpl())
                .setWebViewConfigImpl((WebViewConfig) orderMaker.getWebConfigManagerImpl());

       // HrzLayer hrzLayer = HrzLayer.getInstance(builder);

        HrzLayer hrzLayer= builder.build();

        if(state==STATE_DIALOG){
            if(dialogView!=null){
                iLayerStrategy=new NativeLayerStrategyImpl(dialogView);
            }
        }else if(state==STATE_WEBVIEW){
            iLayerStrategy=new WebViewLayerStrategyImpl(webViewConfig,loadScheme);
        }else {
            iLayerStrategy=null;
            return;
        }

        hrzLayer.setLayerStrategyChooser(new LayerStrategyChooser(iLayerStrategy,mContext));
        proxy= (LayerLifecycle) new LayerLifeCycleProxy(hrzLayer).getProxyInstance();

        //日志管理类实例化
        LogInvoker logInvoker=new LogInvoker();
        logInvoker.addOrder(orderMaker.getPushManagerImpl());
        logInvoker.addOrder(orderMaker.getTimerManagerImpl());
        logInvoker.executeAllOrder();
        logInvoker.getEnvironment().getOrder().toString();

    }


    ///////////////////////////// HrzLayerView的显示状态 /////////////////////////////////////////

    public void show(){
        proxy.onShow();
        isShow=true;
    }

    public void dismiss(){
        proxy.onDismiss();
        isShow=false;
    }

    /**
     * 设置弹窗是否可以取消，这里只针对 STATE_DIALOG 状态
     * @param flag
     */
    public void setLayerCanCancel(boolean flag){
        if(iLayerStrategy != null){
            iLayerStrategy.setLayerCanCancel(flag);
        }
    }


    public boolean isShow() {
        return isShow;
    }


    ///////////////////////////// 用户设置DialogView /////////////////////////////////////////

    private View dialogView;

    public void setDialogView(View dialogView) {
        this.dialogView = dialogView;
    }

    public View getDialogView() {
        return dialogView;
    }

    private String loadScheme="";

    public void setLoadScheme(String loadScheme) {
        this.loadScheme = loadScheme;
    }




}
