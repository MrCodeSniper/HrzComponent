
package com.mujirenben.android.home.dagger.module;

import com.mujirenben.android.common.dagger.scope.ActivityScope;
import com.mujirenben.android.home.mvp.contract.QrCodeContract;
import com.mujirenben.android.home.mvp.model.QrCodeModel;
import com.mujirenben.android.home.mvp.model.entity.GoodsEntity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * 展示 Module 的用法
 * ================================================
 */
@Module
public class QrCodeModule {
    private QrCodeContract.View view;

    /**
     * 构建 UserModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
     *
     * @param view
     */
    public QrCodeModule(QrCodeContract.View view) {
        this.view = view;
    }



    @ActivityScope
    @Provides
    QrCodeContract.View provideQrCodeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    QrCodeContract.Model provideQrCodeModel(QrCodeModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }

    @ActivityScope
    @Provides
    GoodsEntity providQrCodeEntity() {
        return new GoodsEntity();
    }


}
