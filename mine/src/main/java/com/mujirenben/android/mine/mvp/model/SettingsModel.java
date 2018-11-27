package com.mujirenben.android.mine.mvp.model;

import android.app.Application;
import android.os.Environment;

import com.ch.android.resource.Configuration;
import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.base.greendao.DaoSession;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.datapackage.resource.DataBaseHelper;
import com.mujirenben.android.common.datapackage.resource.SharePreferenceHelper;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.common.util.AppInfoUtils;
import com.mujirenben.android.common.util.LogUtil;
import com.mujirenben.android.mine.login.LoginService;
import com.mujirenben.android.mine.login.bean.LogoutResultBean;
import com.mujirenben.android.mine.mvp.contract.SettingsContract;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.RequestBody;

public class SettingsModel extends BaseModel implements SettingsContract.Model {

    private static final String TAG = "SettingsModel";

    public static final String SP_FILE_NAME = "settings";

    @Inject
    Application mApplication;

    private File mCacheFile;

    @Inject
    public SettingsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);

        AppComponent appComponent = BaseApplication.getApplication().getAppComponent();
        mCacheFile = appComponent.cacheFile();

        CACHE_PATHS = new String[] {
                Environment.getExternalStorageDirectory() + "/hongrenzhuang",
                mCacheFile.getAbsolutePath(),
                Configuration.getCacheRootDirectoryPath(mApplication)
        };
    }

    @Override
    public boolean saveConfig(String spKey, boolean spValue) {
        new SharePreferenceHelper(mApplication).setBoolean(spKey, SP_FILE_NAME, spValue);
        return true;
    }

    @Override
    public boolean isConfigOn(String spKey, boolean defValue) {
        return new SharePreferenceHelper(mApplication).getBooleanValue(SP_FILE_NAME, spKey, defValue);
    }

    @Override
    public String getAppVersion() {
        return AppInfoUtils.getVersionName(mApplication);
    }

    @Override
    public void clearLocalCache() {
        for (String path : CACHE_PATHS) {
            File cacheRootDir = new File(path);
            if (cacheRootDir.exists()) {
                try {
                    deleteContents(cacheRootDir);
                } catch (IOException e) {
                    LogUtil.i(TAG, "clearLocalCache() occurs an internal error.");
                    e.printStackTrace();
                }
            }
        }
    }

    String[] CACHE_PATHS;

    @Override
    public Observable<Float> getLocalCacheSize() {
        return Observable.create(new ObservableOnSubscribe<Float>() {
            @Override
            public void subscribe(ObservableEmitter<Float> emitter) throws Exception {
                float size = 0.0f;
                for (String path : CACHE_PATHS) {
                    File cacheRootDir = new File(path);
                    if (cacheRootDir.exists()) {
                        size += getFileTotalSize(cacheRootDir);
                    }
                }
                emitter.onNext(((int)(size * 100)) / 100.0f);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Observable<Boolean> deleteAllUserInfo() {
        return Observable.create(emitter -> {
            DaoSession daoSession =mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_MAIN);
            daoSession.getUserBeanDao().deleteAll();
            emitter.onNext(true);
        });
    }

    @Override
    public Observable<LogoutResultBean> logOut(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(LoginService.class).logOut(body);
    }

    public static void deleteContents(File dir) throws IOException {
        File[] files = dir.listFiles();
        if (files == null) {
            throw new IllegalArgumentException("not a directory: " + dir);
        }
        for (File file : files) {
            if (file.isDirectory()) {
                deleteContents(file);
            }
            if (!file.delete()) {
                throw new IOException("failed to delete file: " + file);
            }
        }
    }

    public static float getFileTotalSize(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            float size = 0;
            if(children.length>0){
                for (File f : children) {
                    size += getFileTotalSize(f);
                }
            }
            return size;
        } else {
            float preciseSize = (float)file.length() / 1024 / 1024;
            return (int)(preciseSize * 100) / 100.0f; // Unit: MB
        }
    }
}
