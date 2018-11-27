package com.mujirenben.android.mine.di.module;

import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.mine.mvp.contract.OrderContract;
import com.mujirenben.android.mine.mvp.model.OrderModel;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderModule {
	private OrderContract.View view;

	/**
	 * 构建MineModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
	 *
	 * @param view
	 */
	public OrderModule(OrderContract.View view) {
		this.view = view;
	}

	@ActivityScope
	@Provides
	OrderContract.View provideOrderView() {
		return this.view;
	}

	@ActivityScope
	@Provides
	OrderContract.Model provideOrderModel(OrderModel model) {
		return model;
	}
}
