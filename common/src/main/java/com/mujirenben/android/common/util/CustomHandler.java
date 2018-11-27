package com.mujirenben.android.common.util;

import android.os.Handler;
import android.os.Message;

import com.mujirenben.android.common.base.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * Created by mac on 2018/9/23.
 */

public class CustomHandler extends Handler{


    WeakReference<BaseActivity> weakReference;

    public CustomHandler(BaseActivity activity) {
        weakReference = new WeakReference<BaseActivity>(activity);
    }


    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (null == weakReference || null == weakReference.get()) {
            return;
        }
        ((BaseActivity)(weakReference.get())).handleMessage(msg);
    }


}
