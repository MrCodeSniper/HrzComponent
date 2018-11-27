package com.mujirenben.android.discovery.mvp.contract;

import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.discovery.mvp.model.entity.DiscoveryBean;

import io.reactivex.Observable;

public class DiscoveryContract {

    public interface View extends IView {
        void onRecommendDataLoaded(DiscoveryBean data);
        void onMaterialDataLoaded(DiscoveryBean data);

        void onMoreRecommendDataLoaded(DiscoveryBean data);
        void onMoreMaterialDataLoaded(DiscoveryBean data);

        void onLoadDataError(String errorMsg);
    }

    public interface Model extends IModel {
        Observable<DiscoveryBean> loadRecommendData(int page, int pageSize);
        Observable<DiscoveryBean> loadMaterialData(int page, int pageSize);
        Observable<DiscoveryBean> clickShare(long id);
    }
}
