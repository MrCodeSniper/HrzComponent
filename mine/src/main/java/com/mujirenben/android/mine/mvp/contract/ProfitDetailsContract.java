package com.mujirenben.android.mine.mvp.contract;

import android.content.Context;

import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.mine.mvp.model.bean.ProfitListDetailsBean;
import com.mujirenben.android.mine.mvp.model.bean.ProfitMainDetailsBean;
import com.mujirenben.android.mine.mvp.model.bean.ProfitMonthDetailsBean;
import com.mujirenben.android.mine.mvp.model.bean.ProfitNoticeBean;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Query;

public interface ProfitDetailsContract {
    interface View extends IView {
        void bindMainProfitData(ProfitMainDetailsBean profitMainDetailsBean);
        void bindProfitNoticeData(ProfitNoticeBean profitNoticeBean);
        void bindProfitMonthData(ProfitMonthDetailsBean profitMonthDetailsBean);
        void onLoadDataError(String msg);
        void bindProfitListData(ProfitListDetailsBean profitListDetailsBean);
        void onLoadMoreSuccess();
        void onLoadMoreFailure();
        void onLoadNetworkError(String msg);
        void onLoadListDataError(String msg);
        void onLoadListNetworkError(String msg);
    }

    interface Model extends IModel {
        Observable<ProfitMainDetailsBean> getProfitMainDetails(HashMap<String, String> paramMap);
        Observable<ProfitMonthDetailsBean> getProfitMonthDetails(HashMap<String, String> paramMap);
        Observable<ProfitNoticeBean> getProfitNotice();
        Observable<ProfitListDetailsBean> getProfitListByType(Context context, int type, int pageNumber, int pageSize);
    }
}
