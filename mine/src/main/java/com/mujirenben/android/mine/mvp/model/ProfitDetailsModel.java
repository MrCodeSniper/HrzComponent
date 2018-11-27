package com.mujirenben.android.mine.mvp.model;

import android.content.Context;

import com.google.gson.JsonObject;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.mine.mvp.contract.ProfitDetailsContract;
import com.mujirenben.android.mine.mvp.model.bean.ProfitListDetailsBean;
import com.mujirenben.android.mine.mvp.model.bean.ProfitMainDetailsBean;
import com.mujirenben.android.mine.mvp.model.bean.ProfitMonthDetailsBean;
import com.mujirenben.android.mine.mvp.model.bean.ProfitNoticeBean;
import com.mujirenben.android.mine.mvp.model.service.ProfitDetailsService;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.RequestBody;

public class ProfitDetailsModel extends BaseModel implements ProfitDetailsContract.Model {
    @Inject
    public ProfitDetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    public Observable<ProfitMainDetailsBean> getProfitMainDetails(HashMap<String, String> paramMap) {
        JsonObject jsonObj = new JsonObject();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            jsonObj.addProperty(entry.getKey(), entry.getValue());
        }

        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObj.toString());

        return Observable.just(mRepositoryManager.obtainRetrofitService(ProfitDetailsService.class).getProfitMainDetails(body))
                .flatMap(listObservable -> listObservable);
    }

    public Observable<ProfitNoticeBean> getProfitNotice() {
        return Observable.just(mRepositoryManager.obtainRetrofitService(ProfitDetailsService.class).getProfitNotice())
                .flatMap(listObservable -> listObservable);
    }

    public Observable<ProfitMonthDetailsBean> getProfitMonthDetails(HashMap<String, String> paramMap) {
        JsonObject jsonObj = new JsonObject();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            jsonObj.addProperty(entry.getKey(), entry.getValue());
        }

        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObj.toString());

        return Observable.just(mRepositoryManager.obtainRetrofitService(ProfitDetailsService.class).getProfitMonthDetails(body))
                .flatMap(listObservable -> listObservable);
    }

    public Observable<ProfitListDetailsBean> getProfitListByType(Context context, int type, int pageNumber, int pageSize) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("type", type + "");
        hashMap.put("page", pageNumber + "");
        hashMap.put("pageSize", pageSize + "");
        HashMap<String, String> paramMap = HttpParamUtil.getCommonSignParamMap(context, hashMap);

        JsonObject jsonObj = new JsonObject();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            jsonObj.addProperty(entry.getKey(), entry.getValue());
        }

        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObj.toString());

        return Observable.just(mRepositoryManager.obtainRetrofitService(ProfitDetailsService.class).getProfitListByType(body))
                .flatMap(listObservable -> listObservable);
    }
}
