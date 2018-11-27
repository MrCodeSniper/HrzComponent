package com.mujirenben.android.common.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ch.android.common.R;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.widget.widgetcallback.OnKouLingCloseDialog;

/**
 * Created by Administrator on 2018\9\7 0007.
 */

public class ExpirationDialog extends RxDialog {

    private Context mContext;

    public OnKouLingCloseDialog kouLingCloseDialog;

    private int days=0;


    public void setKouLingCloseDialog(OnKouLingCloseDialog kouLingCloseDialog) {
        this.kouLingCloseDialog = kouLingCloseDialog;
    }

    public ExpirationDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public ExpirationDialog(@NonNull Context context, int themeResId,int days) {
        super(context, themeResId);
        this.days=days;
        this.mContext = context;
        initView();
    }

    public ExpirationDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        initView();
    }

    private void initView(){

        boolean atOnceExpiring = days > 0;

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_shopkeeper_expiration, null);
        TextView titleTV = dialogView.findViewById(R.id.tv_title);
        TextView messageTV = dialogView.findViewById(R.id.tv_message);
        TextView sureTV = dialogView.findViewById(R.id.tv_sure);
        TextView cancelTV = dialogView.findViewById(R.id.tv_cancel);

        titleTV.setText(atOnceExpiring ? "店主身份即将到期" : "店主身份已到期");
        messageTV.setText(atOnceExpiring ?
                String.format("您的店主身份还有%d天将到期，到期后将无法享受店主分佣特权并降级为皇冠，请点击立即续费。", days) :
                "您的店主身份已到期，点击立即恢复身份。");
        sureTV.setText(atOnceExpiring ? "续费店主" : "成为店主");

        sureTV.setOnClickListener((view) -> {
            ARouter.getInstance().build(ARouterPaths.SHOP_KEEPER_RENEW_ACTIVITY).navigation(mContext);
            selfDismiss();
        });

        cancelTV.setOnClickListener((view) -> selfDismiss());

       requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
       setCancelable(false);
       setContentView(dialogView);
       setFullScreen();
    }


    private void selfDismiss(){
        if(kouLingCloseDialog!=null){
            kouLingCloseDialog.hide();
        }
    }



}
