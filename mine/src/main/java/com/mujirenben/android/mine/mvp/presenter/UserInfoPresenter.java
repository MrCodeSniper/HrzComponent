package com.mujirenben.android.mine.mvp.presenter;

import android.app.Application;

import com.mujirenben.android.common.base.greendao.City;
import com.mujirenben.android.common.base.greendao.Province;
import com.mujirenben.android.common.base.greendao.ShipAddress;
import com.mujirenben.android.common.base.greendao.UserBean;
import com.mujirenben.android.common.base.greendao.Zone;
import com.mujirenben.android.common.base.interal.AppManager;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.entity.EntityData;
import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.mine.mvp.contract.UserInfoContract;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.RequestBody;


@ActivityScope
public class UserInfoPresenter extends BasePresenter<UserInfoContract.Model, UserInfoContract.View> {


    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public UserInfoPresenter(UserInfoContract.Model model, UserInfoContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
        unDispose();
    }

    public void setDefaultAddressById(Long addressById){
        mModel.setDefaultById(addressById)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                    @Override
                    public void onNext(Boolean flag) {
                        mRootView.setDefaultConsition(flag);
                    }
                });
    }


    public void setDefaultAddress(RequestBody body){

        mModel.setDefaultShipAddress(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EntityData>() {
                    @Override
                    public void accept(EntityData addressBean) throws Exception {
                        Logger.e(addressBean.toString());
                        if(addressBean==null){
                            mRootView.setDefaultConsition(false);
                            return;
                        }
                        if(addressBean.isSuccess()){
                            mRootView.setDefaultConsition(true);
                        }
                    }
                });

    }



    public void setDefaultOpposite(){
        mModel.setAllNotDefault()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                    @Override
                    public void onNext(Boolean flag) {
                        mRootView.setAllNotDefaultSuccess(flag);
                    }
                });
    }


    public void deleteSpecificalShipAddressById(Long addressId){
        mModel.deleteShipAddressById(addressId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                    @Override
                    public void onNext(Boolean flag) {
                        mRootView.deleteAllShipAddress();
                    }
                });
    }




    public void deleteAllShipAddress(int userId){
        mModel.deleteAllShipAddresss(userId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                    @Override
                    public void onNext(Boolean flag) {
                        mRootView.deleteAllShipAddress();
                    }
                });
    }



    public void getAddressByUserIdServer(RequestBody body){
        mModel.getAddressListByUser(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> addDispose(disposable)).observeOn(AndroidSchedulers.mainThread())
                .subscribe(addressBean -> {
                    Logger.e(addressBean.toString());
                    if(addressBean==null||addressBean.getData()==null||addressBean.getData().size()<1){
                        mRootView.getShipAddressEmpty();
                    }else {
                        mRootView.getShipAddressSuccess(addressBean.getData());
                    }
                });
    }


    public void getAddressByUserId(int userId){

        mModel.getUserShipAddressByUserId(userId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<List<ShipAddress>>(mErrorHandler) {
                    @Override
                    public void onNext(List<ShipAddress> list) {

                    }
                });

    }


    public void testRM(UserBean userBean){
        mModel.SaveTestData(userBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                    @Override
                    public void onNext(Boolean list) {

                    }
                });

    }

    public void deleteAddressById(RequestBody body){

        mModel.deleteShipAddressById(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EntityData>() {
                    @Override
                    public void accept(EntityData baseEntity) throws Exception {
                        Logger.e(baseEntity.toString());
                        if(baseEntity!=null&&baseEntity.isSuccess()){
                            mRootView.setDefaultConsition(true);
                        }else {
                            mRootView.setDefaultConsition(false);
                        }
                    }
                });

    }



    public void saveShipAddressServer(RequestBody body){

        mModel.saveUserShipAddress(body)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EntityData>() {
                    @Override
                    public void accept(EntityData baseEntity) throws Exception {
                        Logger.e(baseEntity.toString());
                        if(baseEntity!=null&&baseEntity.isSuccess()){
                            mRootView.saveShipAddressSuccess(0L);
                        }else {
                            mRootView.saveShipAddressFail();
                        }
                    }
                });

    }


    public void saveShipData(ShipAddress address){

        mModel.SaveUserShipAddress(address)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<Long>(mErrorHandler) {
                    @Override
                    public void onNext(Long itemId) {
                        if(itemId==null||itemId<0){
                            mRootView.saveShipAddressFail();
                            return;
                        }
                        mRootView.saveShipAddressSuccess(itemId);
                    }
                });



    }


    public void getAllCity(Long proId){
        mModel.getAllCityByProvince(proId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<List<City>>(mErrorHandler) {
                    @Override
                    public void onNext(List<City> cities) {
                        if(cities==null||cities.size()==0){
                            mRootView.getAllDbFail();
                            return;
                        }
                        mRootView.getAllCitySuccess(cities);
                    }
                });
    }




    public void getAllProvince(){
        mModel.getAllProvince()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<List<Province>>(mErrorHandler) {
                    @Override
                    public void onNext(List<Province> provinces) {
                        if(provinces==null||provinces.size()==0){
                            mRootView.getAllDbFail();
                            return;
                        }
                        mRootView.getAllProviceSuccess(provinces);
                    }
                });
    }


    public void getZoneByCity(Long cityid){
        mModel.getAllZoneByCity(cityid)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        addDispose(disposable);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<List<Zone>>(mErrorHandler) {
                    @Override
                    public void onNext(List<Zone> cities) {
                        if(cities==null||cities.size()==0){
                            mRootView.getAllDbFail();
                            return;
                        }
                        mRootView.getAllZoneSuccess(cities);
                    }
                });
    }












}
