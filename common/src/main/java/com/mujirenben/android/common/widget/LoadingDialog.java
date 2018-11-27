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
import android.widget.TextView;
import android.widget.Toast;

import com.ch.android.common.R;

/**
 * Created by Administrator on 2018\8\14 0014.
 */

public class LoadingDialog {

    private Toast mToast;
    private TextView mTextView;
    private TimeCount timeCount;
    private ImageView mloadingView;
    private String msg="";
    private Handler mHandler = new Handler();
    private boolean canceled = true;
    private Animation scaleAnimation;
    private int layoutId;
    private Context context;
    private View customView;
    private  int DEFAULT_DURATION_TIME = 30000;

    private static LoadingDialog instance = null;


    public static synchronized LoadingDialog getInstance(Context context) {
        if (instance==null)
            instance=new LoadingDialog(context);
        return instance;
    }



    public LoadingDialog(Context cxt) {

        this.layoutId = layoutId;
        this.msg = msg;
        this.context = cxt.getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //自定义布局
        customView = inflater.inflate(R.layout.common_loading_toast, null);
        //自定义toast文本
        mTextView =  customView.findViewById(R.id.toast_msg);
        mloadingView = customView.findViewById(R.id.toast_loading_iv);
        if(!TextUtils.isEmpty(msg)){
            mTextView.setText(msg);
        }

        mloadingView = (ImageView) customView.findViewById(R.id.toast_loading_iv);
        if (mToast == null) {
            mToast = new Toast(context);
        }
    }


    /**
     * 自定义居中显示toast
     */
    public void show() {
        if (mloadingView != null) {
            scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.dialog_rotate_anim);
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
        Log.i("ToastUtil", "Toast show...");
    }

    /**
     * 自定义时长、居中显示toast
     *
     * @param duration
     */
    public void show(int duration) {
        timeCount = new TimeCount(duration, 1000);
        Log.i("ToastUtil", "Toast show...");
        if (canceled) {
            timeCount.start();
            canceled = false;
            showUntilCancel();
        }
    }

    /**
     * 隐藏toast
     */
    public void hide() {
        if (scaleAnimation != null) {
            scaleAnimation.cancel();
        }
        if (mToast != null) {
            mToast.cancel();
        }

        canceled = true;
        Log.i("ToastUtil", "Toast that customed duration hide...");
    }

    private void showUntilCancel() {
        if (canceled) { //如果已经取消显示，就直接return
            return;
        }
        mToast.show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("ToastUtil", "Toast showUntilCancel...");
                showUntilCancel();
            }
        }, Toast.LENGTH_LONG);
    }

    /**
     * 自定义计时器
     */
    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval); //millisInFuture总计时长，countDownInterval时间间隔(一般为1000ms)
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //  mTextView.setText(message + ": " + millisUntilFinished / 1000 + "s后消失");
        }

        @Override
        public void onFinish() {
            hide();
        }
    }














}
