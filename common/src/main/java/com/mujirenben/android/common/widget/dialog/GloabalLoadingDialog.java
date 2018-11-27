package com.mujirenben.android.common.widget.dialog;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/9/11.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ch.android.common.R;

public class GloabalLoadingDialog extends Dialog{

    private Animation rotateAnimation;
    private ImageView content_iv;
    private LinearInterpolator linearInterpolator;

    private Context mContext;
    public GloabalLoadingDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
        prepareVariable();
        initView();
    }

    private void prepareVariable() {
        rotateAnimation = AnimationUtils.loadAnimation(mContext, R.anim.dialog_rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnimation.setInterpolator(lin);
    }

    public GloabalLoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        prepareVariable();
        initView();
    }

    protected GloabalLoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        prepareVariable();
        initView();
    }


    private void initView(){

        View view = LayoutInflater.from(mContext).inflate(R.layout.common_loading_toast, null);
        content_iv = view.findViewById(R.id.custom_toast_loading_iv);
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) content_iv.getLayoutParams();
        params.gravity = Gravity.CENTER;

        setContentView(view);
    }

    public void showLoading() {
        if (content_iv != null) {
            content_iv.startAnimation(rotateAnimation);
        }
        this.show();
    }

    /**
     * 隐藏toast
     */
    public void hideLoading() {
        if (rotateAnimation != null) {
            rotateAnimation.cancel();
        }

        this.cancel();
    }
}
