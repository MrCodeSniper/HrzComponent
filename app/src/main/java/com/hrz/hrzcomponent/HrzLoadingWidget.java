package com.hrz.hrzcomponent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;


import com.mujirenben.liangchenbufu.R;


/**
 * 通用loading视图 - 包含一个布局 一个动画 一个图标 资源
 * 使用时 跟随接口状态即可
 */
public class HrzLoadingWidget extends Dialog {

    private  int DEFAULT_DURATION_TIME = 3000;
    private Animation mRotateAnimation;
    private ImageView mRotateView;
    private Activity mRefActivity;


    public HrzLoadingWidget(@NonNull Context context) {
        super(context,R.style.NoBackGroundDialog);
        if(context instanceof Activity){
            mRefActivity= (Activity) context;
        }
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.common_loading_layout, null);
        mRotateView=view.findViewById(R.id.common_iv_loading);
        setContentView(view);
        if (mRotateView != null) {
            mRotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.common_rotate_anim);
            // 步骤2:创建 动画对象 并传入设置的动画效果xml文件
            LinearInterpolator linearInterpolator = new LinearInterpolator();
            mRotateAnimation.setInterpolator(linearInterpolator);
            mRotateView.startAnimation(mRotateAnimation);
        }
    }

    @Override
    public void show() {
        if(mRefActivity!=null && mRefActivity.isFinishing()){
            return;
        }
        super.show();
    }

    @Override
    public void cancel() {
        super.cancel();
        if (mRotateAnimation != null) {
            mRotateAnimation.cancel();
        }
        mRefActivity=null;
    }

}
