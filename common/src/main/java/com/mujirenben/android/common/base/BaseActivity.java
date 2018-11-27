
package com.mujirenben.android.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.mujirenben.android.common.base.delegate.IActivity;
import com.mujirenben.android.common.base.interal.lifecycle.ActivityLifecycleable;
import com.mujirenben.android.common.datapackage.cache.Cache;
import com.mujirenben.android.common.datapackage.cache.CacheType;
import com.mujirenben.android.common.event.NetWorkChangeEvent;
import com.mujirenben.android.common.mvp.IPresenter;
import com.mujirenben.android.common.receiver.INetEvent;
import com.mujirenben.android.common.statistics.StatisticProxy;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.NetWorkUtils;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

import static com.mujirenben.android.common.util.ThirdViewUtil.convertAutoView;


/**
 * ================================================
 * 因为 Java 只能单继承,所以如果要用到需要继承特定 {@link
 * } 的三方库,那你就需要自己自定义 {@link Activity}
 * 继承于这个特定的 {@link Activity},然后再按照 {@link BaseActivity} 的格式,将代码复制过去,记住一定要实现{@link IActivity}
 * ================================================
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity
        implements IActivity, ActivityLifecycleable ,INetEvent,Handler.Callback {




    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;
    private Unbinder mUnbinder;
    protected ImmersionBar mImmersionBar;
    public static INetEvent mINetEvent;

    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return mCache;
    }

    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = convertAutoView(name, context, attrs);
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化网络状态的监听
        mINetEvent=this;
        try {
            int layoutResID = initView(savedInstanceState);
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                setContentView(layoutResID);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initData(savedInstanceState);

        StatisticProxy.onPageStart(getApplicationContext(), this.getClass().getSimpleName());
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;
        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    //    Glide.with(getApplicationContext()).pauseRequests();
        StatisticProxy.onPageEnd(getApplicationContext(), this.getClass().getSimpleName());
    }

    /**
     * 是否使用eventBus,默认为使用(false)，如果子类使用eventbus， 务必提供最少一个@Subscribe方法，不然运行时会crash
     *
     * @return
     */
    @Override
    public boolean useEventBus() {
        return false;
    }

    /**
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册{@link android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,那你再在这个Activity中绑定继承于 {@link BaseFragment} 的Fragment将不起任何作用
     *
     * @return
     */
    @Override
    public boolean useFragment() {
        return true;
    }

    public void invokeActivityMethod(String type){
        com.orhanobut.logger.Logger.e("invokeActivityMethod");
    }

//
//    /****************************神策采集信息**************************************/
//    @Override
//    public String getScreenUrl() {
//        return getClass().getPackage().getName();
//    }
//
//    @Override
//    public JSONObject getTrackProperties() throws JSONException {
//        JSONObject jsonObject = new JSONObject();
////        jsonObject.put("orderId", "888888");
////        jsonObject.put("manufacturer", "sensorsdata");
//        return jsonObject;
//    }


    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onNetChange(int netWorkState) {
        switch (netWorkState) {
            case  NetWorkUtils.NETWORK_NONE:
                Logger.e("没有网络");
                EventBus.getDefault().post(new NetWorkChangeEvent(NetWorkUtils.NETWORK_NONE));
                break;
            case  NetWorkUtils.NETWORK_MOBILE:
                Logger.e("移动网络");
                EventBus.getDefault().post(new NetWorkChangeEvent(NetWorkUtils.NETWORK_MOBILE));
                break;
            case  NetWorkUtils.NETWORK_WIFI:
                Logger.e("wifi网络");
                EventBus.getDefault().post(new NetWorkChangeEvent(NetWorkUtils.NETWORK_WIFI));
                break;
        }
    }
}
