package com.mujirenben.android.home.mvp.presenter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.common.util.NetWorkUtils;
import com.mujirenben.android.common.util.PermissionUtil;
import com.mujirenben.android.common.util.rxtools.RxLifecycleUtils;
import com.mujirenben.android.home.mvp.contract.QrCodeContract;
import com.mujirenben.android.home.mvp.model.entity.GoodsEntity;

import java.net.SocketTimeoutException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * 调用model的数据请求 并确定view执行位置
 * Created by mac on 2018/5/5.
 */
@ActivityScope
public class QrCodePresenter extends BasePresenter<QrCodeContract.Model, QrCodeContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    GoodsEntity goodsEntity;


    @Inject
    public QrCodePresenter(QrCodeContract.Model model, QrCodeContract.View rootView) {
        super(model, rootView);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        PermissionUtil.launchCamera(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("请求权限失败");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showMessage("需要去手机设置中打开");
            }
        }, mRootView.getRxPermissions(), mErrorHandler);
    }








    public void  requestGoodsInfo(String qrcode, String appkey, Context context){

        //请求接口时 判断网络


        if(!NetWorkUtils.isAvailable(context)){
            mRootView.noNetDialog();
            return;
        }




      mModel.getGoodsInfo("",qrcode,"1",appkey)
              .subscribeOn(Schedulers.io())
             // .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
              .doOnSubscribe(new Consumer<Disposable>() {
                  @Override
                  public void accept(Disposable disposable) throws Exception {
                        //刚接受注册 做一些ui操作
                      addDispose(disposable);
                  }
              })
              .observeOn(AndroidSchedulers.mainThread())
              .doFinally(new Action() {
                  @Override
                  public void run() throws Exception {
                      //回调操作做一些ui操作
                  }
              })
              .onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                  @Override
                  public ObservableSource apply(Throwable throwable) throws Exception {
                      if (throwable instanceof SocketTimeoutException) {
                          mRootView.requestOutTime();
                      }
                      return null;
                  }
              })
              .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
              .subscribe(new ErrorHandleSubscriber<GoodsEntity>(mErrorHandler) {
                  @Override
                  public void onNext(GoodsEntity o) {
                      if(o!=null){
                          mRootView.loadSuccess(o);
                      }
                  }
              });



    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.goodsEntity=null;
        this.mErrorHandler=null;
        unDispose();
    }
}
