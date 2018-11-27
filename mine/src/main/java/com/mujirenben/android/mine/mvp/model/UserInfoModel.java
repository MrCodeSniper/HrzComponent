package com.mujirenben.android.mine.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.mujirenben.android.common.base.greendao.City;
import com.mujirenben.android.common.base.greendao.CityDao;
import com.mujirenben.android.common.base.greendao.Province;
import com.mujirenben.android.common.base.greendao.ShipAddress;
import com.mujirenben.android.common.base.greendao.ShipAddressDao;
import com.mujirenben.android.common.base.greendao.UserBean;
import com.mujirenben.android.common.base.greendao.Zone;
import com.mujirenben.android.common.base.greendao.ZoneDao;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.datapackage.resource.DataBaseHelper;
import com.mujirenben.android.common.entity.AddressBean;
import com.mujirenben.android.common.entity.EntityData;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.mine.mvp.contract.UserInfoContract;
import com.mujirenben.android.mine.mvp.model.service.AddressService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.RequestBody;


@ActivityScope
public class UserInfoModel extends BaseModel implements UserInfoContract.Model {

    @Inject
    Gson mGson;
    @Inject
    Application mApplication;


    @Inject
    public UserInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }



    @Override
    public Observable<List<ShipAddress>> getUserShipAddressByUserId(int userid) {
        return Observable.create(emitter -> {
            List<ShipAddress> addressList =mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_MAIN).getShipAddressDao().queryBuilder().list();
            emitter.onNext(addressList);
        });
    }

    @Override
    public Observable<Long> SaveUserShipAddress(ShipAddress address) {
        return Observable.create(emitter -> {
            long itemId = mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_MAIN).getShipAddressDao().insertOrReplace(address);
            emitter.onNext(itemId);
        });
    }

    @Override
    public Observable<Boolean> deleteAllShipAddresss(int userid) {
        return Observable.create(emitter -> {
            mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_MAIN).getShipAddressDao().deleteAll();
            emitter.onNext(true);
        });
    }

    @Override
    public Observable<Boolean> deleteShipAddressById(Long addressId) {
        return Observable.create(emitter -> {
            mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_MAIN).getShipAddressDao().deleteByKey(addressId);
            emitter.onNext(true);
        });
    }

    @Override
    public Observable<List<City>> getAllCityByProvince(Long proid) {
        return Observable.create(emitter -> {
            List<City> cityList = mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_CITY).getCityDao().queryBuilder().where(CityDao.Properties.ProID.eq(proid)).list();
            emitter.onNext(cityList);
        });
    }

    @Override
    public Observable<List<Province>> getAllProvince() {
        return Observable.create(emitter -> {
            List<Province> cityList = mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_CITY).getProvinceDao().queryBuilder().build().list();
            emitter.onNext(cityList);
        });
    }

    @Override
    public Observable<List<Zone>> getAllZoneByCity(Long cityId) {
        return Observable.create(emitter -> {
            List<Zone> zoneList = mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_CITY).getZoneDao().queryBuilder().where(ZoneDao.Properties.CityID.eq(cityId)).list();
            emitter.onNext(zoneList);
        });
    }



    @Override
    public Observable<Boolean> setAllNotDefault() {
        return Observable.create(emitter -> {
            ShipAddress address = mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_MAIN).getShipAddressDao().queryBuilder().where(ShipAddressDao.Properties.IsDefault.eq(1L)).build().unique();
            if(address!=null){
                address.setIsDefault(0L);
                mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_MAIN).getShipAddressDao().update(address);
                emitter.onNext(true);
            }
            emitter.onNext(true);
        });
    }

    @Override
    public Observable<Boolean> setDefaultById(Long addressId) {
        return Observable.create(emitter -> {
            ShipAddress address = mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_MAIN).getShipAddressDao().queryBuilder().where(ShipAddressDao.Properties.AddressId.eq(addressId)).build().unique();
            if(address!=null){
                address.setIsDefault(1L);
                mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_MAIN).getShipAddressDao().update(address);
                emitter.onNext(true);
            }
            emitter.onNext(false);
        });
    }










    @Override
    public Observable<Boolean> SaveTestData(UserBean userBean) {
        return Observable.create(emitter -> {
            mRepositoryManager.obtainDBService(DataBaseHelper.class).getDaoSessionByType(Const.DB_TYPE_MAIN).getUserBeanDao().save(userBean);
            emitter.onNext(true);
        });
    }

    @Override
    public Observable<AddressBean> getAddressListByUser(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(AddressService.class).getUserAddressList(body);
    }

    @Override
    public Observable<EntityData> saveUserShipAddress(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(AddressService.class).saveUserShipAddress(body);
    }

    @Override
    public Observable<EntityData> setDefaultShipAddress(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(AddressService.class).setDefaultShipAddressById(body);
    }

    @Override
    public Observable<EntityData> deleteShipAddressById(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(AddressService.class).deleteAddressById(body);
    }


}