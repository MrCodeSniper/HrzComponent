package com.mujirenben.android.home.mvp.contract;

import android.app.Activity;
import com.mujirenben.android.common.datapackage.bean.SearchHotWords;
import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import io.reactivex.Observable;
import okhttp3.RequestBody;

public interface HomeContract {

    interface View extends IView{
        Activity getActivity();
        void getSearchHotWordsSuccess(SearchHotWords searchHotWords);
        void getSearchHotWordsFail();
    }


    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model<T> extends IModel{
       Observable<SearchHotWords> getSearchHotWords(RequestBody body);
    }
}
