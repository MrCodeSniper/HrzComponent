package com.mujirenben.android.mine.di.component;

import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.mine.di.module.MineModule;
import com.mujirenben.android.mine.di.module.OrderModule;
import com.mujirenben.android.mine.mvp.model.OrderModel;
import com.mujirenben.android.mine.mvp.ui.activity.OrderDetailsActivity;
import com.mujirenben.android.mine.mvp.ui.activity.OrderListActivity;
import com.mujirenben.android.mine.mvp.ui.activity.SaveShipAddressActivitity;
import com.mujirenben.android.mine.mvp.ui.activity.SelfRunOrderDetailsActivity;

import dagger.Component;

@ActivityScope
@Component(modules = OrderModule.class, dependencies = AppComponent.class)
public interface OrderComponent {
	void inject(OrderListActivity activity);
	void inject(OrderDetailsActivity activity);
	void inject(SelfRunOrderDetailsActivity activity);
}
