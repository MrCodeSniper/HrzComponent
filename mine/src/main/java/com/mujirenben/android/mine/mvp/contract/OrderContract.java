package com.mujirenben.android.mine.mvp.contract;

import android.app.Activity;

import com.mujirenben.android.common.mvp.IModel;
import com.mujirenben.android.common.mvp.IView;
import com.mujirenben.android.mine.mvp.model.bean.OrderBean;
import com.mujirenben.android.mine.mvp.model.bean.OrderDetail;
import com.mujirenben.android.mine.mvp.model.bean.OrderInfo;
import com.mujirenben.android.mine.mvp.model.bean.SelfRunOrderDetail;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public interface OrderContract {
	interface View extends IView {
		Activity getActivity();
		void showOrderList(OrderInfo orderInfo);
		void showOrderDetails(OrderDetail orderItem);
		void showOrders(List<OrderBean.DataBean> orderBean);
		void showRequestError(String errorMsg);

		void requestInitDataEmpty();
		void requestMoreDataEmpty();

		void showOrderDetailEmpty();
		void getOrderListByUserIdFail();
		void getOrdersByType();
        default void showAddress(SelfRunOrderDetail detail){};
        default void hideAddress(){};

        //显示消费账户
        default void showConsumeAccount(SelfRunOrderDetail detail){};

		default void onSelfRunOrderDetailLoaded(SelfRunOrderDetail detail){};
		default void onSelfRunOrderDetailFailed(Throwable error){};
	}


	//Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
	interface Model extends IModel {
		Observable<OrderInfo> getOrderListByUserId(String userId);
		Observable<OrderBean> getOrdersByType(RequestBody body);
		Observable<OrderDetail> getOrderDetail(String orderId);
		default Observable<SelfRunOrderDetail> getSelfRunOrderDetail(String walletOrderCode) { return null;};
	}
}
