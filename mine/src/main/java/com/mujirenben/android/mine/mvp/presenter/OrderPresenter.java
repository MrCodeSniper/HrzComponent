package com.mujirenben.android.mine.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.mujirenben.android.common.base.interal.AppManager;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.mvp.BasePresenter;
import com.mujirenben.android.common.util.throwable.HandlerCallback;
import com.mujirenben.android.common.util.throwable.ThrowableUtil;
import com.mujirenben.android.mine.mvp.contract.OrderContract;
import com.mujirenben.android.mine.mvp.model.bean.MineConstant;
import com.mujirenben.android.mine.mvp.model.bean.OrderBean;
import com.mujirenben.android.mine.mvp.model.bean.OrderDetail;
import com.mujirenben.android.mine.mvp.model.bean.OrderInfo;
import com.mujirenben.android.mine.mvp.model.bean.SelfRunOrderDetail;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class OrderPresenter extends BasePresenter<OrderContract.Model, OrderContract.View> {

	@Inject
	RxErrorHandler mErrorHandler;
	@Inject
	Application mApplication;
	@Inject
	ImageLoader mImageLoader;
	@Inject
	AppManager mAppManager;

	@Inject
	public OrderPresenter(OrderContract.Model model, OrderContract.View rootView) {
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

	public void getOrderListByUserId(String userId){
		mModel.getOrderListByUserId(userId)
				.subscribeOn(Schedulers.io())
				.doOnSubscribe(disposable -> addDispose(disposable))
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new ErrorHandleSubscriber<OrderInfo>(mErrorHandler) {
					@Override
					public void onNext(OrderInfo orderInfo) {
						if(null==orderInfo||null==orderInfo.getData()||orderInfo.getData().size()==0){
							mRootView.getOrderListByUserIdFail();
						}else {
							mRootView.showOrderList(orderInfo);
						}
					}
				});
	}

	public void getOrderDetailById(String userId){
		mRootView.showLoading();
		mModel.getOrderDetail(userId)
				.subscribeOn(Schedulers.io())
				.doOnSubscribe(disposable -> addDispose(disposable))
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new ErrorHandleSubscriber<OrderDetail>(mErrorHandler) {
					@Override
					public void onNext(OrderDetail orderInfo) {
						if(orderInfo==null||orderInfo.getData()==null){
							mRootView.showOrderDetailEmpty();
						}else {
							mRootView.showOrderDetails(orderInfo);
						}
					}
				});
	}


	public void getSelfRunOrderDetailById(String walletOrderCode) {
		mRootView.showLoading();
		mModel.getSelfRunOrderDetail(walletOrderCode)
				.subscribeOn(Schedulers.io())
				.doOnSubscribe(disposable -> addDispose(disposable))
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new ErrorHandleSubscriber<SelfRunOrderDetail>(mErrorHandler) {
					@Override
					public void onNext(SelfRunOrderDetail orderInfo) {
						if(orderInfo==null||orderInfo.getData()==null){
							mRootView.onSelfRunOrderDetailFailed(new NullPointerException("数据为空"));
						}else {
							if (orderInfo.getData().getIsVirtual() == MineConstant.SHOW_ADDRESS){
                                mRootView.showAddress(orderInfo);
                            }else{
							    mRootView.hideAddress();
                            }
                            mRootView.showConsumeAccount(orderInfo);
							mRootView.onSelfRunOrderDetailLoaded(orderInfo);
						}
					}
				});
	}


    /**
     * 获取订单列表
     * @param map
     */
	public void getOrdersByType(Map map,boolean isLoadMore){
		if(!isLoadMore){
			mRootView.showLoading();
		}
		String postJson = new Gson().toJson(map);
//		Logger.e("JSON:"+postJson);
		RequestBody body  = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),postJson);
		mModel.getOrdersByType(body)
				.subscribeOn(Schedulers.io())
				.doOnSubscribe(disposable -> {
					addDispose(disposable);
				})
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new ErrorHandleSubscriber<OrderBean>(mErrorHandler) {
					@Override
					public void onNext(OrderBean orderBean) {
                        String errorMsg = "未知错误";
					    if(orderBean != null){
					        if(orderBean.isSuccess()){
					        	if(orderBean.getData()==null||orderBean.getData().size()==0){
					        	    if(isLoadMore){
										mRootView.requestMoreDataEmpty();
									}else {
										mRootView.requestInitDataEmpty();
									}
					        		return;
								}
                                List<OrderBean.DataBean> orderList = orderBean.getData();
                                mRootView.showOrders(orderList);
                            }else {
					            String msg = orderBean.getMessage();
					            if(!TextUtils.isEmpty(msg)&&msg!=null){
					                errorMsg = msg;
                                }
					            mRootView.showRequestError(errorMsg);
                            }
                            return;
                        }
                        mRootView.showRequestError(errorMsg);
					}

					@Override
					public void onError(Throwable t) {
						super.onError(t);
                        ThrowableUtil.handleThrowable(t, new HandlerCallback() {
							@Override
							public void dataException(String msg) {
								mRootView.showRequestError(msg);
							}

							@Override
							public void networkException(String msg) {
								mRootView.showRequestError(msg);
							}
						});

						mRootView.hideLoading();
					}

					@Override
					public void onComplete() {
						super.onComplete();
						mRootView.hideLoading();
					}
				});


	}
}
