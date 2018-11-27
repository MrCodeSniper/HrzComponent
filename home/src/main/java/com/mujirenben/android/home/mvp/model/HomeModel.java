package com.mujirenben.android.home.mvp.model;

import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.common.datapackage.bean.SearchHotWords;
import com.mujirenben.android.common.datapackage.http.api.HomeDataService;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.home.mvp.contract.HomeContract;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by mac on 2018/5/5.
 */
@FragmentScope
public class HomeModel extends BaseModel implements HomeContract.Model {

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<SearchHotWords> getSearchHotWords(RequestBody body) {
        return mRepositoryManager.obtainRetrofitService(HomeDataService.class).getSearchHotWords(body);
    }
}
