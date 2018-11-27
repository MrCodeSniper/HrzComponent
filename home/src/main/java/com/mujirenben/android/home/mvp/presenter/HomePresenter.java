package com.mujirenben.android.home.mvp.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.mujirenben.android.common.dagger.scope.FragmentScope;
import com.mujirenben.android.common.datapackage.bean.SearchHotWords;
import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.home.mvp.contract.HomeContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.RequestBody;

/**
 * 调用model的数据请求 并确定view执行位置
 * Created by mac on 2018/5/5.
 */
@FragmentScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {

    }


    public void requestHotWords(RequestBody body){
        mModel.getSearchHotWords(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<SearchHotWords>(mErrorHandler) {

                    @Override
                    public void onNext(SearchHotWords recommendBean) {
                        if(recommendBean!=null&&recommendBean.getData()!=null){
                            mRootView.getSearchHotWordsSuccess(recommendBean);
                        }else {
                            mRootView.getSearchHotWordsFail();
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.getSearchHotWordsFail();
                    }
                });
    }

}
