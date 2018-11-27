package com.mujirenben.android.vip.mvp.model;

import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.google.gson.JsonObject;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.mvp.BaseModel;
import com.mujirenben.android.vip.mvp.contract.VipQrCodeContract;
import com.mujirenben.android.vip.mvp.model.bean.InvitationCodeBean;
import com.mujirenben.android.vip.mvp.model.service.VipService;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.RequestBody;

public class VipQrCodeModel extends BaseModel implements VipQrCodeContract.Model {

    @Inject
    Application mApplication;

    @Inject
    public VipQrCodeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<InvitationCodeBean> loadInvitaionCode() {
        RetrofitUrlManager.getInstance().putDomain("normal_server", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);

        Map<String, String> params = HttpParamUtil.getCommonSignParamMap(mApplication, null);

        JsonObject jsonObj = new JsonObject();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            jsonObj.addProperty(entry.getKey(), entry.getValue());
        }

        RequestBody body = RequestBody.create(
                okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObj.toString());


        return mRepositoryManager.obtainRetrofitService(VipService.class).loadInvitationCode(body);
    }

    @Override
    public void copyTextToClipboard(String text, Runnable taskAfterCopied) {
        ClipData data = ClipData.newPlainText(null, text);
        ClipboardManager cm =
                (ClipboardManager)mApplication.getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm != null) {
            cm.setPrimaryClip(data);

            if (taskAfterCopied != null) {
                taskAfterCopied.run();
            }
        }
    }
}
