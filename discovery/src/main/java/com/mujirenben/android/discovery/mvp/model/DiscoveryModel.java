package com.mujirenben.android.discovery.mvp.model;

import android.app.Application;

import com.google.gson.JsonObject;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.discovery.mvp.contract.DiscoveryContract;
import com.mujirenben.android.discovery.mvp.model.entity.DiscoveryBean;
import com.mujirenben.android.discovery.mvp.model.service.DiscoveryService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.RequestBody;

public class DiscoveryModel extends BaseModel implements DiscoveryContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public DiscoveryModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<DiscoveryBean> loadRecommendData(int page, int pageSize) {

        HashMap<String, String> extraParams = new HashMap<>();
        extraParams.put("page", page + "");
        extraParams.put("pageSize", pageSize + "");
        extraParams.put("type", "bursting");

        Map<String, String> params = HttpParamUtil.getCommonSignParamMap(mApplication, extraParams);
        JsonObject jsonObj = new JsonObject();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            jsonObj.addProperty(entry.getKey(), entry.getValue());
        }

        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObj.toString());
        RetrofitUrlManager.getInstance().putDomain("normal_server", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        return mRepositoryManager.obtainRetrofitService(DiscoveryService.class)
                .loadDiscoveryBean(body);
    }

    @Override
    public Observable<DiscoveryBean> loadMaterialData(int page, int pageSize) {

        HashMap<String, String> extraParams = new HashMap<>();
        extraParams.put("page", page + "");
        extraParams.put("pageSize", pageSize + "");
        extraParams.put("type", "betir");

        Map<String, String> params = HttpParamUtil.getCommonSignParamMap(mApplication, extraParams);
        JsonObject jsonObj = new JsonObject();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            jsonObj.addProperty(entry.getKey(), entry.getValue());
        }

        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObj.toString());
        RetrofitUrlManager.getInstance().putDomain("normal_server", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        return mRepositoryManager.obtainRetrofitService(DiscoveryService.class)
                .loadDiscoveryBean(body);
    }

    @Override
    public Observable<DiscoveryBean> clickShare(long id) {
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("page", id);
        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObj.toString());
        RetrofitUrlManager.getInstance().putDomain("normal_server", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        return mRepositoryManager.obtainRetrofitService(DiscoveryService.class).clickShare(body);
    }

    @Override
    public void onDestroy() {

    }
}
