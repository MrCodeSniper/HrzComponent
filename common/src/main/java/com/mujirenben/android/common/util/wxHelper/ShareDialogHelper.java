package com.mujirenben.android.common.util.wxHelper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.ch.android.common.R;
import com.mujirenben.android.common.util.wxHelper.listener.OnClickLinkListener;
import com.mujirenben.android.common.util.wxHelper.listener.OnClickSessionListener;
import com.mujirenben.android.common.util.wxHelper.listener.OnClickTimeLineListener;
import com.mujirenben.android.common.widget.dialog.RxDialog;

/**
 * @author 潘荣福
 * @date 2018/7/18
 */
public class ShareDialogHelper{
    /**上下文*/
    private Context mContext;
    /**对话框标题*/
    private String dialogTitle;
    /**对话狂内容*/
    private String dialogContent;
    /**复制链接回调*/
    private OnClickLinkListener linkListener;
    /**指定好友回调*/
    private OnClickSessionListener sessionListener;
    /**朋友回调*/
    private OnClickTimeLineListener timeLineListener;
    /**ShareDialogHelper对象*/
    public static Builder getBuilder(Context context){
        return new Builder(context);
    }
    private ShareDialogHelper(Builder builder) {
        this.mContext = builder.mContext;
        this.dialogTitle = builder.dialogTitle;
        this.dialogContent = builder.dialogContent;
        this.linkListener = builder.linkListener;
        this.sessionListener = builder.sessionListener;
        this.timeLineListener = builder.timeLineListener;
    }

    public static class Builder{
        /**上下文*/
        private Context mContext;
        /**对话框标题*/
        private String dialogTitle;
        /**对话狂内容*/
        private String dialogContent;
        /**复制链接回调*/
        private OnClickLinkListener linkListener;
        /**指定好友回调*/
        private OnClickSessionListener sessionListener;
        /**朋友回调*/
        private OnClickTimeLineListener timeLineListener;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setDialogTitle(String dialogTitle) {
            this.dialogTitle = dialogTitle;
            return this;
        }

        public Builder setDialogContent(String dialogContent) {
            this.dialogContent = dialogContent;
            return this;
        }

        public Builder setOnLinkListener(OnClickLinkListener linkListener) {
            this.linkListener = linkListener;
            return this;
        }

        public Builder setOnSessionListener(OnClickSessionListener sessionListener) {
            this.sessionListener = sessionListener;
            return this;
        }

        public Builder setOnTimeLineListener(OnClickTimeLineListener timeLineListener) {
            this.timeLineListener = timeLineListener;
            return this;
        }

        public  ShareDialogHelper build(){
            return new ShareDialogHelper(this);
        }
    }

    /**
     * 显示对话框
     */
    public void showDialog(boolean hideLink){

        if(mContext==null|| ((Activity)mContext).isFinishing()) return;

        RxDialog rxDialog = new RxDialog(mContext,R.style.ShareDialogTheme);
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.layout_share_free_get, null);
        (dialogView.findViewById(R.id.ll_title)).setVisibility(View.GONE);
        (dialogView.findViewById(R.id.tv_content)).setVisibility(View.GONE);
        dialogView.findViewById(R.id.iv_close).setOnClickListener((View view) -> rxDialog.cancel());
        if (hideLink){
            dialogView.findViewById(R.id.ll_link).setVisibility(View.GONE);
        }
        if (sessionListener != null) {
            dialogView.findViewById(R.id.iv_wx).setOnClickListener(v -> {
                if (sessionListener != null) {
                    sessionListener.onClick();
                }
                rxDialog.dismiss();
            });
        } else {
            dialogView.findViewById(R.id.ll_session).setVisibility(View.GONE);
        }

        if (timeLineListener != null) {
            dialogView.findViewById(R.id.iv_circle).setOnClickListener(v -> {
                if (timeLineListener != null) {
                    timeLineListener.onClick();
                }
                rxDialog.dismiss();
            });
        } else {
            dialogView.findViewById(R.id.ll_timeline).setVisibility(View.GONE);
        }

        if (linkListener != null) {
            dialogView.findViewById(R.id.iv_link).setOnClickListener(view -> {
                if (linkListener != null) {
                    linkListener.onClick();
                }
                rxDialog.dismiss();
            });
        } else {
            dialogView.findViewById(R.id.ll_link).setVisibility(View.GONE);
        }

        rxDialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE);
        rxDialog.setCancelable(false);
        rxDialog.setContentView(dialogView);
        rxDialog.setFullScreen();
        rxDialog.show();
    }
}
