package com.mujirenben.android.common.datapackage.resource;

import android.content.Context;

import com.google.gson.Gson;
import com.mujirenben.android.common.util.FillUtil;

import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by admin on 2017/3/10.
 *  简单封装资源管理 准备中
 */
@Singleton
public class ResourceHelper {


    private Context context;

    @Inject
    public ResourceHelper(Context context) {
        this.context = context;
    }


    //主页资源加载
    public<S> Disposable getData(DisposableObserver<S> consumer , final Class<S> clazz , final String fillName) {
        return Observable.create(new ObservableOnSubscribe<S>() {
            @Override
            public void subscribe(ObservableEmitter<S> e) throws Exception {
                InputStream is = context.getAssets().open(fillName);
                String text = FillUtil.readTextFromFile(is);
                Gson gson = new Gson();
                e.onNext(gson.fromJson(text, clazz));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(consumer);
    }














}
