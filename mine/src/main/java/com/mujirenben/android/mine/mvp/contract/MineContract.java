package com.mujirenben.android.mine.mvp.contract;


import android.app.Activity;
import android.content.Context;

import com.mujirenben.android.common.base.greendao.City;
import com.mujirenben.android.common.base.greendao.Province;
import com.mujirenben.android.common.base.greendao.UserBean;
import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.mine.login.bean.LoginResultBean;
import com.mujirenben.android.mine.mvp.model.InviteCodeBean;
import com.mujirenben.android.mine.mvp.model.bean.UserInfo;
import com.mujirenben.android.mine.mvp.model.bean.UserRewardInfo;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public interface MineContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Activity getActivity();
        void showUserInfo(UserBean userBean);
        void noUserInfoDB();
        void saveSucess(Long itemId);
        void saveFail(Long itemId);
        void requestPermissionSuccess(String permissionType);

        void getProvinceSuccess(List<Province> provinceList);
        void getCitiesSuccess(List<City> cityList);
        void getLocalDbFail();


        void showUserRewardInfo(UserRewardInfo userRewardInfo);
        void getUserRewardError();

        RxPermissions getRxPermissions();
        void showUserInfo(UserInfo loginResultBean);

        void onLoadInviteCodeInfoCompleted(InviteCodeBean info);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<UserInfo> getUserInfo(Map<String, String> map);
        Observable<Long> saveUserInfo(UserBean userBean);
        Observable<Boolean> deleteAllUserInfo();
        Observable<List<Province>> getAllProvince();
        Observable<List<City>> getAllCityByProvince(Long proid);
        Observable getUserRewardInfo(Context context);
        Observable<InviteCodeBean> getInviteCodeInfo();


    }
}
