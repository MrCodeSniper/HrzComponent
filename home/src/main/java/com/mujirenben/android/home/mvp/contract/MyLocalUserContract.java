package com.mujirenben.android.home.mvp.contract;


import android.app.Activity;

import com.mujirenben.android.common.base.greendao.UserBean;
import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;

import io.reactivex.Observable;

public interface MyLocalUserContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Activity getActivity();
        void showUserInfo(UserBean userBean);
        void noUserInfoDB();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<UserBean> getUserInfo();
    }
}
