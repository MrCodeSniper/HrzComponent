package com.mujirenben.android.mine.mvp.model;

import android.app.Application;

import com.google.gson.JsonObject;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.common.util.DateTimeUtil;
import com.mujirenben.android.mine.mvp.contract.OrderContract;
import com.mujirenben.android.mine.mvp.model.bean.OrderBean;
import com.mujirenben.android.mine.mvp.model.bean.OrderDetail;
import com.mujirenben.android.mine.mvp.model.bean.OrderInfo;
import com.mujirenben.android.mine.mvp.model.bean.SelfRunOrderDetail;
import com.mujirenben.android.mine.mvp.model.service.OrderService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;

@ActivityScope
public class OrderModel extends BaseModel implements OrderContract.Model{

	@Inject
	Application mApplication;

	@Inject
	public OrderModel(IRepositoryManager repositoryManager) {
		super(repositoryManager);
	}

	@Override
	public Observable<OrderInfo> getOrderListByUserId(String userId) {
		return mRepositoryManager.obtainRetrofitService(OrderService.class).getOrderList(userId);
	}

	@Override
	public Observable<OrderBean> getOrdersByType(RequestBody body) {
		return mRepositoryManager.obtainRetrofitService(OrderService.class).getOrderByType(body);
	}

	@Override
	public Observable<OrderDetail> getOrderDetail(String orderId) {
		return mRepositoryManager.obtainRetrofitService(OrderService.class).getOrderDetailById(orderId);
	}

	@Override
	public Observable<SelfRunOrderDetail> getSelfRunOrderDetail(String walletOrderCode) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("walletOrderCode", walletOrderCode);
		HashMap<String, String> paramMap = HttpParamUtil.getCommonSignParamMap(mApplication, hashMap);

		return mRepositoryManager.obtainRetrofitService(OrderService.class).getSelfRunOrderDetail(paramMap);
	}
}
