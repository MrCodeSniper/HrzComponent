package com.mujirenben.android.home.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.baichuan.trade.common.utils.JSONUtils;
import com.google.gson.Gson;
import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.datapackage.resource.ResourceHelper;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.AppInfoUtils;
import com.mujirenben.android.home.mvp.model.entity.HomeModuleBean;
import com.mujirenben.android.home.mvp.model.remote.HomeModuleService;

import java.io.Serializable;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.RequestBody;

public class ResourceLoadService extends Service {

    private static final String TAG = "ResourceLoadService";

    private IRepositoryManager mRepoMgr;
    private HomeModuleService mRemoteService;

    private HomeModuleBean mBannerData;
    private HomeModuleBean mIconListData;
    private HomeModuleBean mToufuData;
    private HomeModuleBean mRewardData;
    private HomeModuleBean mWaterfallData;

    private Callback mCallback;

    private SharedPreferences mSp;
    private static final String KEY_BANNER_DATA = "banner_data";
    private static final String KEY_ICON_LIST_DATA = "icon_list_data";
    private static final String KEY_TOUFU_DATA = "toufu_data";
    private static final String KEY_REWARD_DATA = "reward_data";
    private static final String KEY_WATERFALL_DATA = "waterfall_data";

    private boolean mHasLoadedInitially = false;

    public interface Callback extends Serializable {
        int MODULE_BANNER = 0;
        int MODULE_ICON_LIST = 1;
        int MODULE_TOUFU = 2;
        int MODULE_REWARD = 3;
        int MODULE_WATERFALL = 4;

        void onHomeModuleDataLoaded(int module, HomeModuleBean data);
        void onMoreWaterfallDataLoaded(HomeModuleBean data);
    }

    public class CallbackBinder extends Binder {
        public void setCallback(Callback callback) {
            Log.i(TAG, "setCallback >> callback=" + callback);
            mCallback = callback;
            notifyListener();
        }

        public void requestLoadMoreWaterfallData(int page, int callbackProdNum) {
            Log.i(TAG, "requestLoadMoreWaterfallData >> page=" + page + ", callbackProdNum=" + callbackProdNum);
            RetrofitUrlManager.getInstance().putDomain("normal_server", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
            requestLoadMoreWaterfallDataInternal(page, callbackProdNum);
        }

        public void refresh() {
            refreshInternal();
        }
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "===onCreate===");
        try {
            mRepoMgr = BaseApplication.getApplication().getAppComponent().repositoryManager();
        } catch (Exception e) {
            // 初始化AppDelegate
            ((BaseApplication)getApplication()).init();

            mRepoMgr = BaseApplication.getApplication().getAppComponent().repositoryManager();
        }
        mRemoteService = mRepoMgr.obtainRetrofitService(HomeModuleService.class);
        mSp = getSharedPreferences("home_data", Context.MODE_PRIVATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mHasLoadedInitially) {
            refreshInternal();
            mHasLoadedInitially = true;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void refreshInternal() {
        RetrofitUrlManager.getInstance().putDomain("normal_server", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        loadBannerData();
        loadIconListData();
        loadToufuData();
//        loadRewardData();
        loadWaterfallData();
    }

    private void notifyListener() {
        if (mCallback == null) return;

        if (mBannerData != null) {
            mCallback.onHomeModuleDataLoaded(Callback.MODULE_BANNER, mBannerData);
            mBannerData = null;
        }
        if (mIconListData != null) {
            mCallback.onHomeModuleDataLoaded(Callback.MODULE_ICON_LIST, mIconListData);
            mIconListData = null;
        }
        if (mToufuData != null) {
            mCallback.onHomeModuleDataLoaded(Callback.MODULE_TOUFU, mToufuData);
            mToufuData = null;
        }
        if (mRewardData != null) {
            mCallback.onHomeModuleDataLoaded(Callback.MODULE_REWARD, mRewardData);
            mRewardData = null;
        }
        if (mWaterfallData != null) {
            mCallback.onHomeModuleDataLoaded(Callback.MODULE_WATERFALL, mWaterfallData);
            mWaterfallData = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if ("hrz.intent.action.HOME_FRAGMENT".equals(intent.getAction())) {
            if (!mHasLoadedInitially) {
                refreshInternal();
                mHasLoadedInitially = true;
            }
            return new CallbackBinder();
        }
        return null;
    }

    private void requestLoadMoreWaterfallDataInternal(int page, int callbackProdNum) {
//        JsonObject jsonObj = new JsonObject();
//        jsonObj.addProperty("platform", 1);
//        jsonObj.addProperty("page", page);
//        jsonObj.addProperty("callbackProdNum", callbackProdNum);

        HashMap<String, String> map = new HashMap<>();
        map.put("platform", "1");
        map.put("page", page + "");
        map.put("callbackProdNum", callbackProdNum + "");
        map=HttpParamUtil.getResultMap(this,map);




        String requestBody= JSONUtils.toJson(map);

        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"), requestBody);

        Observable<HomeModuleBean> result = mRemoteService.loadWaterfallData(body);
        result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeModuleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeModuleBean homeModuleBean) {
                        Log.i(TAG, "requestLoadMoreWaterfallDataInternal >> data=" + homeModuleBean.getCode());
                        if (mCallback != null) {
                            mCallback.onMoreWaterfallDataLoaded(homeModuleBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "requestLoadMoreWaterfallDataInternal >> error=" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadBannerData() {
//        JsonObject jsonObj = new JsonObject();
//        jsonObj.addProperty("platform", 1);
//        jsonObj.addProperty("version", AppInfoUtils.getVersionName(this));
//        jsonObj.addProperty("type", "banner");

        HashMap<String, String> map = new HashMap<>();
        map.put("platform", "1");
        map.put("version", AppInfoUtils.getVersionName(this) + "");
        map.put("type", "banner");

        map= HttpParamUtil.getCommonSignParamMap(this,map);

        String requestBody= JSONUtils.toJson(map);

        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"), requestBody);

        Observable<HomeModuleBean> result = mRemoteService.loadBannerData(body);
        result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeModuleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeModuleBean homeModuleBean) {
                        Log.i(TAG, "loadBannerData >> data=" + homeModuleBean.getCode());
                        mBannerData = homeModuleBean;
                        writeDataToLocal(KEY_BANNER_DATA, homeModuleBean);
                        notifyListener();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "loadBannerData >> error=" + e.getMessage());
                        mBannerData = readDataFromLocal(KEY_BANNER_DATA);
                        if (mBannerData != null) {
                            notifyListener();
                        } else {
                            readDataInBuild(Callback.MODULE_BANNER);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadIconListData() {

//        JsonObject jsonObj = new JsonObject();
//        jsonObj.addProperty("platform", 1);
//        jsonObj.addProperty("version", AppInfoUtils.getVersionName(this));
//        jsonObj.addProperty("type", "entrance");

        HashMap<String, String> map = new HashMap<>();
        map.put("platform", "1");
        map.put("version", AppInfoUtils.getVersionName(this) + "");
        map.put("type", "entrance");

        map= HttpParamUtil.getCommonSignParamMap(this,map);

        String requestBody= JSONUtils.toJson(map);

        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"), requestBody);

        Observable<HomeModuleBean> result = mRemoteService.loadIconListData(body);
        result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeModuleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeModuleBean homeModuleBean) {
                        Log.i(TAG, "loadIconListData >> data=" + homeModuleBean.getCode());
                        mIconListData = homeModuleBean;
                        writeDataToLocal(KEY_ICON_LIST_DATA, homeModuleBean);
                        notifyListener();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "loadIconListData >> error=" + e.getMessage());
                        mIconListData = readDataFromLocal(KEY_ICON_LIST_DATA);
                        if (mIconListData != null) {
                            notifyListener();
                        } else {
                            readDataInBuild(Callback.MODULE_ICON_LIST);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadToufuData() {

        int vipGrade = 0; // 未注册
        LoginDataManager ldm = LoginDataManager.getsInstance(this);
        if (ldm.isLogin()) {
            vipGrade = ldm.getLevel() + 1;
        }

//        JsonObject jsonObj = new JsonObject();
//        jsonObj.addProperty("platform", 1);
//        jsonObj.addProperty("version", AppInfoUtils.getVersionName(this));
//        jsonObj.addProperty("vipGrade", vipGrade);

        HashMap<String, String> map = new HashMap<>();
        map.put("platform", "1");
        map.put("version", AppInfoUtils.getVersionName(this) + "");
        map.put("vipGrade", vipGrade + "");

        map= HttpParamUtil.getCommonSignParamMap(this,map);

        String requestBody= JSONUtils.toJson(map);

        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"), requestBody);

        Observable<HomeModuleBean> result = mRemoteService.loadToufuData(body);
        result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeModuleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeModuleBean homeModuleBean) {
                        Log.i(TAG, "loadToufuData >> data=" + homeModuleBean);
                        mToufuData = homeModuleBean;
                        writeDataToLocal(KEY_TOUFU_DATA, homeModuleBean);
                        notifyListener();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "loadToufuData >> error=" + e.getMessage());
                        mToufuData = readDataFromLocal(KEY_TOUFU_DATA);
                        if (mToufuData != null) {
                            notifyListener();
                        } else {
                            readDataInBuild(Callback.MODULE_TOUFU);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadRewardData() {

//        JsonObject jsonObj = new JsonObject();
//        jsonObj.addProperty("platform", 1);
//        jsonObj.addProperty("version", AppInfoUtils.getVersionName(this));
//        jsonObj.addProperty("vip", 0);

        HashMap<String, String> map = new HashMap<>();
        map.put("platform", "1");
        map.put("version", AppInfoUtils.getVersionName(this) + "");
        map.put("vip",  "0");

        map= HttpParamUtil.getCommonSignParamMap(this,map);

        String requestBody= JSONUtils.toJson(map);

        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"), requestBody);

        Observable<HomeModuleBean> result = mRemoteService.loadRewardData(body);
        result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeModuleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeModuleBean homeModuleBean) {
                        Log.i(TAG, "loadRewardData >> data=" + homeModuleBean.getCode());
                        mRewardData = homeModuleBean;
                        writeDataToLocal(KEY_REWARD_DATA, homeModuleBean);
                        notifyListener();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "loadRewardData >> error=" + e.getMessage());
                        mRewardData = readDataFromLocal(KEY_REWARD_DATA);
                        if (mRewardData != null) {
                            notifyListener();
                        } else {
                            readDataInBuild(Callback.MODULE_REWARD);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadWaterfallData() {

        HashMap<String,String> map=new HashMap<>();
        map.put("platform","1");
        map.put("page",1+"");
        map.put("callbackProdNum",0+"");
        map=HttpParamUtil.getResultMap(this,map);


        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"), JSONUtils.toJson(map));

        Observable<HomeModuleBean> result = mRemoteService.loadWaterfallData(body);
        result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeModuleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeModuleBean homeModuleBean) {
                        Log.i(TAG, "loadWaterfallData >> data=" + homeModuleBean.getCode());
                        mWaterfallData = homeModuleBean;
                        writeDataToLocal(KEY_WATERFALL_DATA, homeModuleBean);
                        notifyListener();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "loadWaterfallData >> error=" + e.getMessage());
                        mWaterfallData = readDataFromLocal(KEY_WATERFALL_DATA);
                        if (mWaterfallData != null) {
                            notifyListener();
                        } else {
                            readDataInBuild(Callback.MODULE_WATERFALL);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void writeDataToLocal(String key, HomeModuleBean data) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putString(key, new Gson().toJson(data));
        editor.apply();
    }

    private HomeModuleBean readDataFromLocal(String key) {
        try {
            String data = mSp.getString(key, "");
            if (!TextUtils.isEmpty(data)) {
                return new Gson().fromJson(data, HomeModuleBean.class);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private void readDataInBuild(int type) {
        String fileName = null;
        switch (type) {
            case Callback.MODULE_BANNER:
                fileName = "new_banner.txt";
                break;

            case Callback.MODULE_ICON_LIST:
                fileName = "new_icon_list.txt";
                break;

            case Callback.MODULE_TOUFU:
                fileName = "new_toufu.txt";
                break;

            case Callback.MODULE_WATERFALL:
                fileName = "new_waterfall.txt";
                break;
        }

        if (fileName == null) {
            return;
        }

        mRepoMgr.obtainTypeService(ResourceHelper.class).getData(new DisposableObserver<HomeModuleBean>() {
            @Override
            public void onNext(HomeModuleBean homeModuleBean) {
                switch (type) {
                    case Callback.MODULE_BANNER:
                        mBannerData = homeModuleBean;
                        break;

                    case Callback.MODULE_ICON_LIST:
                        mIconListData = homeModuleBean;
                        break;

                    case Callback.MODULE_TOUFU:
                        mToufuData = homeModuleBean;
                        break;

                    case Callback.MODULE_WATERFALL:
                        mWaterfallData = homeModuleBean;
                        break;
                }

                notifyListener();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, HomeModuleBean.class, fileName);
    }
}
