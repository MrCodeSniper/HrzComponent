/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mujirenben.android.common.base.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


import com.mujirenben.android.common.datapackage.bean.MessageEvent;
import com.mujirenben.android.common.util.ArmsUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * ================================================
 * {@link ActivityLifecycles} 默认实现类
 * ================================================
 */
public class ActivityLifecyclesImpl implements ActivityLifecycles {
    private Activity mActivity;
    private IActivity iActivity;

    public ActivityLifecyclesImpl(@NonNull Activity activity) {
        this.mActivity = activity;
        this.iActivity = (IActivity) activity;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //如果要使用 EventBus 请将此方法返回 true
        if (iActivity.useEventBus()){
            //注册到事件主线
            EventBus.getDefault().register(mActivity);

            EventBus.getDefault().
                    post(new MessageEvent("hello event"));

            Log.d("eventbus", "send msg");
        }

        //初始化fresco
//        Fresco.initialize(mActivity);
        iActivity.setupActivityComponent(ArmsUtils.obtainAppComponentFromContext(mActivity));
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void onDestroy() {
        //如果要使用 EventBus 请将此方法返回 true
        if (iActivity != null && iActivity.useEventBus())
            EventBus.getDefault().unregister(mActivity);
        this.iActivity = null;
        this.mActivity = null;
    }
}
