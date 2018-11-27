package com.mujirenben.android.home.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.mujirenben.android.common.base.greendao.DaoSession;
import com.mujirenben.android.common.base.greendao.UserBean;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.datapackage.resource.DataBaseHelper;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.home.mvp.contract.MyLocalUserContract;

import org.greenrobot.greendao.query.Query;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


@ActivityScope
public class MyLocalUserModel extends BaseModel implements MyLocalUserContract.Model {

    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MyLocalUserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<UserBean> getUserInfo() {
        return Observable.create(new ObservableOnSubscribe<UserBean>() {
            @Override
            public void subscribe(ObservableEmitter<UserBean> emitter) throws Exception {
                DaoSession daoSession = mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_MAIN);
                Query<UserBean> notesQuery = daoSession.getUserBeanDao().queryBuilder().build();
                UserBean userBean = notesQuery.unique();
                emitter.onNext(userBean);
            }
        });
    }




}