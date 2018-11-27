package com.mujirenben.android.vip.di.module;

import com.mujirenben.android.vip.mvp.contract.VipQrCodeContract;
import com.mujirenben.android.vip.mvp.model.VipQrCodeModel;

import dagger.Module;
import dagger.Provides;

@Module
public class VipQrCodeModule {

    private VipQrCodeContract.View view;

    public VipQrCodeModule(VipQrCodeContract.View view) {
        this.view = view;
    }

    @Provides
    public VipQrCodeContract.View provideVipQrCodeView() {
        return view;
    }

    @Provides
    public VipQrCodeContract.Model provideVipQrCodeModel(VipQrCodeModel model) {
        return model;
    }
}
