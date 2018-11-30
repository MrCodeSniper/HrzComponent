package com.mujirenben.kefu.util;

import android.content.Context;
import android.text.TextUtils;

import com.hyphenate.chat.ChatClient;
import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.event.KeFuInitEvent;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.kefu.UIProvider;
import com.mujirenben.kefu.bean.KeFuLoginBean;
import com.mujirenben.kefu.service.KeFuService;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * @author: panrongfu
 * @date: 2018/7/25 9:50
 * @describe:
 */

public class KeFuSdkUtil {

    private static Context mContext;
    private static LoginDataManager loginDataManager;

    /**
     * 初始化环信客服SDK
     * @param context
     */
    public static void initKeFuSDK(Context context){
        mContext = context.getApplicationContext();

        loginDataManager = LoginDataManager.getsInstance(context);
        String keFuAppKey = loginDataManager.getKeFuAppKey();
        String keFuTenantId = loginDataManager.getKeFuTenantId();
        String password = loginDataManager.getKeFuPassword();
        if(TextUtils.isEmpty(keFuAppKey) || TextUtils.isEmpty(password)|| TextUtils.isEmpty(keFuTenantId)){
            getKeFuLoginInfo();
        }else {
            initChatClient(keFuAppKey,keFuTenantId);
        }
    }

    /**
     * 获取客服系统的账户信息
     */
    private static void getKeFuLoginInfo(){
        RetrofitUrlManager.getInstance().putDomain("kefu_login_info", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        HashMap<String,String> map = HttpParamUtil.getCommonSignParamMap(mContext,null);
        IRepositoryManager mRepositoryManager = BaseApplication.getApplication().getAppComponent().repositoryManager();
        RxErrorHandler mErrorHandler = BaseApplication.getApplication().getAppComponent().rxErrorHandler();
        mRepositoryManager.obtainRetrofitService(KeFuService.class).getKeFuLoginInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<KeFuLoginBean>(mErrorHandler) {
                    @Override
                    public void onNext(KeFuLoginBean keFuLoginBean) {
                        if (keFuLoginBean != null && keFuLoginBean.isSuccess() && keFuLoginBean.getData() != null ){
                            String keFuAppKey = keFuLoginBean.getData().getAppKey();
                            String keFuTenantId = keFuLoginBean.getData().getTenantId();
                            String keFuUserName = keFuLoginBean.getData().getUsername();
                            String keFuToken = keFuLoginBean.getData().getToken();
                            String password = keFuLoginBean.getData().getPassword();
                            String imNumber = keFuLoginBean.getData().getImNumber();
                            loginDataManager.setKeFuAppKey(keFuLoginBean.getData().getAppKey());
                            loginDataManager.setKeFuTenantId(keFuTenantId);
                            loginDataManager.setKeFuUsername(keFuUserName);
                            loginDataManager.setKeFuPassword(password);
                            loginDataManager.setKeFuToken(keFuToken);
                            loginDataManager.setImNumber(imNumber);
                            initChatClient(keFuAppKey, keFuTenantId);
                        }else {
                            EventBus.getDefault().post(new KeFuInitEvent(false));
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onError(Throwable t) {
                        EventBus.getDefault().post(new KeFuInitEvent(false));
                    }
                });
    }

    /**
     * 初始化ChatClient
     * @param keFuAppKey
     * @param keFuTenantId
     */
    private static void initChatClient(String keFuAppKey, String keFuTenantId){
        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey(keFuAppKey);//必填项，appkey获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“AppKey”
        options.setTenantId(keFuTenantId);//必填项，tenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”
        // Kefu SDK 初始化
        boolean isInitialized = ChatClient.getInstance().init(mContext, options);
        // Kefu EaseUI的初始化
        UIProvider.getInstance().init(mContext);
        //后面可以设置其他属性
        EventBus.getDefault().post(new KeFuInitEvent(true));
    }
}


