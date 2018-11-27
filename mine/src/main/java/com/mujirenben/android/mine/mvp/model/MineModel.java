package com.mujirenben.android.mine.mvp.model;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mujirenben.android.common.base.greendao.City;
import com.mujirenben.android.common.base.greendao.CityDao;
import com.mujirenben.android.common.base.greendao.DaoSession;
import com.mujirenben.android.common.base.greendao.Province;
import com.mujirenben.android.common.base.greendao.UserBean;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.datapackage.resource.DataBaseHelper;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.common.util.DateTimeUtil;
import com.mujirenben.android.mine.mvp.contract.MineContract;
import com.mujirenben.android.mine.mvp.model.bean.UserInfo;
import com.mujirenben.android.mine.mvp.model.bean.UserRewardInfo;
import com.mujirenben.android.mine.mvp.model.service.MineService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.RequestBody;


@FragmentScope
public class MineModel extends BaseModel implements MineContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MineModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<UserInfo> getUserInfo(Map<String,String> map) {
//        return Observable.create(new ObservableOnSubscribe<UserBean>() {
//            @Override
//            public void subscribe(ObservableEmitter<UserBean> emitter) throws Exception {
//                DaoSession daoSession = mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_MAIN);
//                Query<UserBean> notesQuery = daoSession.getUserBeanDao().queryBuilder().build();
//                UserBean userBean = notesQuery.unique();
//                if(userBean==null){
//                    emitter.onNext(new UserBean());
//                }else {
//                    emitter.onNext(userBean);
//                }
//
//            }
//        });
        return mRepositoryManager.obtainRetrofitService(MineService.class).getUserInfo(map);
    }

    @Override
    public Observable<Long> saveUserInfo(final UserBean userBean) {
        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                // get the Rx variant of the note DAO
                DaoSession daoSession = mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_MAIN);
                long itemId = daoSession.getUserBeanDao().insertOrReplace(userBean);
                emitter.onNext(itemId);
            }
        });
    }

    @Override
    public Observable<Boolean> deleteAllUserInfo() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                // get the Rx variant of the note DAO
                DaoSession daoSession =mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_MAIN);
                daoSession.getUserBeanDao().deleteAll();
                emitter.onNext(true);
            }
        });
    }


    @Override
    public Observable<List<Province>> getAllProvince() {
        return Observable.create(new ObservableOnSubscribe<List<Province>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Province>> emitter) throws Exception {
                List<Province> cityList = mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_CITY).getProvinceDao().queryBuilder().build().list();
                emitter.onNext(cityList);
            }
        });
    }

    @Override
    public Observable<List<City>> getAllCityByProvince(Long proid) {
        return Observable.create(new ObservableOnSubscribe<List<City>>() {
            @Override
            public void subscribe(ObservableEmitter<List<City>> emitter) throws Exception {
                List<City> cityList = mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_CITY).getCityDao().queryBuilder().where(CityDao.Properties.ProID.eq(proid)).list();
                emitter.onNext(cityList);
            }
        });
    }

    @Override
    public Observable<UserRewardInfo> getUserRewardInfo(Context context) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("time", DateTimeUtil.formatDate(new Date()));
        HashMap<String, String> paramMap = HttpParamUtil.getCommonSignParamMap(context, hashMap);

        JsonObject jsonObj = new JsonObject();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            jsonObj.addProperty(entry.getKey(), entry.getValue());
        }

        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObj.toString());

        return  mRepositoryManager.obtainRetrofitService(MineService.class).getUserRewardInfo(body);
    }

    @Override
    public Observable<InviteCodeBean> getInviteCodeInfo() {
        RetrofitUrlManager.getInstance().putDomain("api_server", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        HashMap<String, String> paramMap = HttpParamUtil.getCommonSignParamMap(mApplication, null);
        return mRepositoryManager.obtainRetrofitService(MineService.class).getInviteCodeInfo(paramMap);
    }
}