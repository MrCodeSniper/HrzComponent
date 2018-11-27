package com.mujirenben.android.mine.mvp.contract;

import android.app.Activity;

import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.mine.mvp.model.bean.WithdrawListBean;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

public interface WithdrawRecordContract {

    interface View extends IView {
        Activity getActivity();
        void showWithdrawRecordSuccess(List<WithdrawListBean.DataBean> recordList);
        void showWithdrawRecordFail();
        void showDataErrorLayout(String msg);
    }

    interface Model extends IModel {
        Observable<WithdrawListBean> getWithdrawRecordData(HashMap<String, String> map);
    }
}
