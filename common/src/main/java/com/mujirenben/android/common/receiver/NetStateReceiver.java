package com.mujirenben.android.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.util.NetWorkUtils;

/**
 * Created by Administrator on 2018\9\19 0019.
 */

public class NetStateReceiver extends BroadcastReceiver {

    private INetEvent mINetEvent = BaseActivity.mINetEvent;

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            //容错机制
            if (mINetEvent != null) {
                mINetEvent.onNetChange(NetWorkUtils.getNetWorkState(context));
            }
        }
    }
}

