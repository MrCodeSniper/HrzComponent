package com.mujirenben.android.mine.mvp.contract;


import android.app.Activity;

import com.mujirenben.android.common.base.greendao.City;
import com.mujirenben.android.common.base.greendao.Province;
import com.mujirenben.android.common.base.greendao.ShipAddress;
import com.mujirenben.android.common.base.greendao.UserBean;
import com.mujirenben.android.common.base.greendao.Zone;
import com.mujirenben.android.common.entity.AddressBean;
import com.mujirenben.android.common.entity.EntityData;
import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public interface UserInfoContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Activity getActivity();
        void saveShipAddressSuccess(Long addressId);
        void saveShipAddressFail();
        void getShipAddressEmpty();
        void getShipAddressSuccess(List<AddressBean.DataBean> list);

        void getAllCitySuccess(List<City> cities);
        void getAllDbFail();
        void getAllZoneSuccess(List<Zone> zones);

        void getAllProviceSuccess(List<Province> provinces);

        void deleteAllShipAddress();//删除公用失败view

        void setAllNotDefaultSuccess(Boolean flag);

        void setDefaultConsition(Boolean flag);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<List<ShipAddress>> getUserShipAddressByUserId(int userid);
        Observable<Long> SaveUserShipAddress(ShipAddress addressInfo);
        Observable<Boolean> deleteAllShipAddresss(int userid);
        Observable<List<City>> getAllCityByProvince(Long proId);
        Observable<List<Province>> getAllProvince();
        Observable<List<Zone>> getAllZoneByCity(Long cityId);
        Observable<Boolean> deleteShipAddressById(Long addressId);
        Observable<Boolean> setAllNotDefault();
        Observable<Boolean> setDefaultById(Long addressId);

        Observable<Boolean> SaveTestData(UserBean userBean);
        Observable<AddressBean> getAddressListByUser(RequestBody body);
        Observable<EntityData> saveUserShipAddress(RequestBody body);
        Observable<EntityData> setDefaultShipAddress(RequestBody body);
        Observable<EntityData> deleteShipAddressById(RequestBody body);
    }
}
