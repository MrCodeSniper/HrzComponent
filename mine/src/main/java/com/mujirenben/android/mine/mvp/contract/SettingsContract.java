package com.mujirenben.android.mine.mvp.contract;

import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.mine.login.bean.LogoutResultBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public interface SettingsContract {

    interface View extends IView {
        /**
         * 缓存清除完后的回调
         * @param cacheSizeAfterCleared 缓存清除操作完成后的缓存大小
         */
        void onLocalCacheCleared(float cacheSizeAfterCleared);

        void onCacheSizeComputed(float size);

        void onCacheCleared();

        void deleteUserInfoSuccess();
        void deleteUserInfoFail();

        void logOutSuccess();
        void logOutFail(String msg);
    }

    interface Model extends IModel {
        boolean saveConfig(String spKey, boolean spValue);
        boolean isConfigOn(String spKey, boolean defValue);

        String getAppVersion();

        void clearLocalCache();
        Observable<Float> getLocalCacheSize();

        Observable<Boolean> deleteAllUserInfo();

        Observable<LogoutResultBean> logOut(RequestBody body);
    }
}
