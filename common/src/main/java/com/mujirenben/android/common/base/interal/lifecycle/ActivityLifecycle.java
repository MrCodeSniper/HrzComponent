
package com.mujirenben.android.common.base.interal.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;


import com.mujirenben.android.common.base.delegate.ActivityLifecycles;
import com.mujirenben.android.common.base.delegate.ActivityLifecyclesImpl;
import com.mujirenben.android.common.base.delegate.FragmentDelegate;
import com.mujirenben.android.common.base.delegate.IActivity;
import com.mujirenben.android.common.base.interal.AppManager;
import com.mujirenben.android.common.base.interal.ConfigModule;
import com.mujirenben.android.common.datapackage.cache.Cache;
import com.mujirenben.android.common.datapackage.cache.IntelligentCache;
import com.mujirenben.android.common.util.Preconditions;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;


/**
 * ================================================
 * {@link Application.ActivityLifecycleCallbacks} 默认实现类
 * 通过 {@link ActivityLifecycles} 管理 {@link Activity}
 * ================================================
 */
@Singleton
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;
    @Inject
    Cache<String, Object> mExtras;
    @Inject
    Lazy<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycle;
    @Inject
    Lazy<List<FragmentManager.FragmentLifecycleCallbacks>> mFragmentLifecycles;

    @Inject
    public ActivityLifecycle() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //如果 intent 包含了此字段,并且为 true 说明不加入到 list 进行统一管理
        boolean isNotAdd = false;
        if (activity.getIntent() != null)
            isNotAdd = activity.getIntent().getBooleanExtra(AppManager.IS_NOT_ADD_ACTIVITY_LIST, false);

        if (!isNotAdd)
            mAppManager.addActivity(activity);

        //配置ActivityDelegate
        if (activity instanceof IActivity) {
            ActivityLifecycles activityLifecycles = fetchActivityDelegate(activity);
            if (activityLifecycles == null) {
                Cache<String, Object> cache = getCacheFromActivity((IActivity) activity);
                activityLifecycles = new ActivityLifecyclesImpl(activity);
                //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
                //否则存储在 LRU 算法的存储空间中, 前提是 Activity 使用的是 IntelligentCache (框架默认使用)
                cache.put(IntelligentCache.KEY_KEEP + ActivityLifecycles.ACTIVITY_DELEGATE, activityLifecycles);
            }
            activityLifecycles.onCreate(savedInstanceState);
        }

        registerFragmentCallbacks(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ActivityLifecycles activityLifecycles = fetchActivityDelegate(activity);
        if (activityLifecycles != null) {
            activityLifecycles.onStart();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        mAppManager.setCurrentActivity(activity);

        ActivityLifecycles activityLifecycles = fetchActivityDelegate(activity);
        if (activityLifecycles != null) {
            activityLifecycles.onResume();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ActivityLifecycles activityLifecycles = fetchActivityDelegate(activity);
        if (activityLifecycles != null) {
            activityLifecycles.onPause();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (mAppManager.getCurrentActivity() == activity) {
            mAppManager.setCurrentActivity(null);
        }

        ActivityLifecycles activityLifecycles = fetchActivityDelegate(activity);
        if (activityLifecycles != null) {
            activityLifecycles.onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        ActivityLifecycles activityLifecycles = fetchActivityDelegate(activity);
        if (activityLifecycles != null) {
            activityLifecycles.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mAppManager.removeActivity(activity);

        ActivityLifecycles activityLifecycles = fetchActivityDelegate(activity);
        if (activityLifecycles != null) {
            activityLifecycles.onDestroy();
            getCacheFromActivity((IActivity) activity).clear();
        }
    }

    /**
     * 给每个 Activity 的所有 Fragment 设置监听其生命周期, Activity 可以通过 {@link IActivity#useFragment()}
     * 设置是否使用监听,如果这个 Activity 返回 false 的话,这个 Activity 下面的所有 Fragment 将不能使用 {@link FragmentDelegate}
     * 意味着 @link BaseFragment} 也不能使用
     *
     * @param activity
     */
    private void registerFragmentCallbacks(Activity activity) {

        boolean useFragment = activity instanceof IActivity ? ((IActivity) activity).useFragment() : true;
        if (activity instanceof FragmentActivity && useFragment) {

            //mFragmentLifecycle 为 Fragment 生命周期实现类, 用于框架内部对每个 Fragment 的必要操作, 如给每个 Fragment 配置 FragmentDelegate
            //注册框架内部已实现的 Fragment 生命周期逻辑
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycle.get(), true);

            if (mExtras.containsKey(IntelligentCache.KEY_KEEP + ConfigModule.class.getName())) {
                List<ConfigModule> modules = (List<ConfigModule>) mExtras.get(IntelligentCache.KEY_KEEP + ConfigModule.class.getName());
                for (ConfigModule module : modules) {
                    module.injectFragmentLifecycle(mApplication, mFragmentLifecycles.get());
                }
                mExtras.remove(IntelligentCache.KEY_KEEP + ConfigModule.class.getName());
            }

            //注册框架外部, 开发者扩展的 Fragment 生命周期逻辑
            for (FragmentManager.FragmentLifecycleCallbacks fragmentLifecycle : mFragmentLifecycles.get()) {
                ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycle, true);
            }
        }
    }

    private ActivityLifecycles fetchActivityDelegate(Activity activity) {
        ActivityLifecycles activityLifecycles = null;
        if (activity instanceof IActivity) {
            Cache<String, Object> cache = getCacheFromActivity((IActivity) activity);
            activityLifecycles = (ActivityLifecycles) cache.get(IntelligentCache.KEY_KEEP + ActivityLifecycles.ACTIVITY_DELEGATE);
        }
        return activityLifecycles;
    }

    @NonNull
    private Cache<String, Object> getCacheFromActivity(IActivity activity) {
        Cache<String, Object> cache = activity.provideCache();
        Preconditions.checkNotNull(cache, "%s cannot be null on Activity", Cache.class.getName());
        return cache;
    }

}
