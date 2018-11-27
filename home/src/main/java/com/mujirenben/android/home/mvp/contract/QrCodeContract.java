package com.mujirenben.android.home.mvp.contract;

import android.app.Activity;

import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.home.mvp.model.entity.GoodsEntity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;

/**
 * Created by mac on 2018/5/9.
 */

public interface QrCodeContract {

    interface View extends IView {
        void loadSuccess(GoodsEntity homeIndex);//进来加载
        Activity getActivity();
        void noNetDialog();
        void requestOutTime();
        //申请相机权限
        RxPermissions getRxPermissions();
    }


    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model<T> extends IModel {
        Observable<GoodsEntity> getGoodsInfo(String packages, String qrcode, String city, String appkey);
    }
}
