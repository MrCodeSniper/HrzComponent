package com.mujirenben.android.common.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.android.common.R;
import com.mujirenben.android.common.widget.dialog.RxDialog;

public class DialogUtils {

    private static final int DEFAULT_DURATION_TIME = 30000 ;
    private Toast mToast;
    private TextView mTextView;
    private ImageView mloadingView;
    private Animation scaleAnimation;
    private Context mContext;
    private LinearLayout customView;

    public DialogUtils(Context context, int layoutId, String msg) {
        mContext = context;
        //自定义布局
        customView = (LinearLayout) LayoutInflater.from(mContext).inflate(layoutId, null);
        //自定义toast文本
        mTextView =  customView.findViewById(R.id.custom_toast_msg);
        mloadingView = customView.findViewById(R.id.custom_toast_loading_iv);
        mTextView.setVisibility(View.GONE);
        if(!TextUtils.isEmpty(msg)){
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText(msg);
        }
        if (mToast == null) {
            mToast = new Toast(context);
           // mToast.getLayoutParams().gravity = Gravity.CENTER;
        }
    }

    /**
     * 自定义居中显示toast
     */
    public void show() {
        if (mloadingView != null) {
            scaleAnimation = AnimationUtils.loadAnimation(mContext, R.anim.dialog_rotate_anim);
            // 步骤2:创建 动画对象 并传入设置的动画效果xml文件
            LinearInterpolator lin = new LinearInterpolator();
            scaleAnimation.setInterpolator(lin);
            mloadingView.startAnimation(scaleAnimation);
        }
        //设置toast居中显示
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setView(customView);
        mToast.show();
    }
    /**
     *
     * 隐藏toast
     */
    public void hide() {
        if (scaleAnimation != null) {
            scaleAnimation.cancel();
        }
        if (mToast != null) {
            mToast.cancel();
        }
    }
}