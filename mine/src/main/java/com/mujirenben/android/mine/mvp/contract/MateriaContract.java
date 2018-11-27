package com.mujirenben.android.mine.mvp.contract;

import android.app.Activity;

import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.mine.mvp.model.bean.MateriaCircleBean;
import com.mujirenben.android.mine.mvp.model.bean.MateriaFriendsBean;

import io.reactivex.Observable;

public interface MateriaContract {

    interface View extends IView {

        Activity getActivity();

        void showFriendsMateriaSuccess(MateriaFriendsBean data);
        void showCirclesMateriaSuccess(MateriaCircleBean data);

        void showFriendsMateriaFail();
        void showCirclesMateriaFail();

    }

    interface Model extends IModel {

        Observable<MateriaFriendsBean> getMateriaDataFromFriends(int lastIdQueried, boolean update);
        Observable<MateriaCircleBean> getMateriaDataFromCircle(int lastIdQueried, boolean update);
    }
}
