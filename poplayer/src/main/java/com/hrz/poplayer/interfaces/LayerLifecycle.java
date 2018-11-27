package com.hrz.poplayer.interfaces;

/**
 * Created by mac on 2018/7/10.
 */

//生命周期管理
public interface LayerLifecycle {

    void onCreate();
    void onShow();
    void onDismiss();
    void onRecycle();

}
