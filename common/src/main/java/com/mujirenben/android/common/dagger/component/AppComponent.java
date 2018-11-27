package com.mujirenben.android.common.dagger.component;

import android.app.Application;

import com.google.gson.Gson;
import com.mujirenben.android.common.base.delegate.AppDelegate;
import com.mujirenben.android.common.base.greendao.DaoSession;
import com.mujirenben.android.common.base.interal.AppManager;
import com.mujirenben.android.common.dagger.module.ApplicationModule;
import com.mujirenben.android.common.dagger.module.ClientModule;
import com.mujirenben.android.common.dagger.module.GlobalConfigModule;
import com.mujirenben.android.common.datapackage.cache.Cache;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.datapackage.manager.IRepositoryManager;
import com.mujirenben.android.common.datapackage.resource.DataBaseHelper;

import java.io.File;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.OkHttpClient;


/**
 * Created by admin on 2017/3/10.
 */

@Singleton
@Component(modules = {ApplicationModule.class,GlobalConfigModule.class, ClientModule.class})
public interface AppComponent {

    Application application();

    //用于管理所有 activity
    AppManager appManager();

    //ResourceHelper resourceHelper();


    //用于管理网络请求层,以及数据缓存层
    IRepositoryManager repositoryManager();

    //RxJava 错误处理管理类
    RxErrorHandler rxErrorHandler();

    //用来存取一些整个App公用的数据,切勿大量存放大容量数据
    Cache<String, Object> extras();

    //用于创建框架所需缓存对象的工厂
    Cache.Factory cacheFactory();

    //图片管理器,用于加载图片的管理类,默认使用 Glide ,使用策略模式,可在运行时替换框架
    ImageLoader imageLoader();

    //gson
    Gson gson();

    OkHttpClient okHttpClient();


    //缓存文件根目录(RxCache 和 Glide 的缓存都已经作为子文件夹放在这个根目录下),应该将所有缓存都放到这个根目录下,便于管理和清理,可在 GlobalConfigModule 里配置
    File cacheFile();

    DaoSession getDaoSession();


    DataBaseHelper getDBHelper();

    void inject(AppDelegate delegate); //注入生命周期中


    //在定义Component的建造者
    @Component.Builder
    interface Builder {
        //@BindsInstance 允许Component初始化的时候设置一些对象
        @BindsInstance
        Builder application(Application application);//效果等同于module provideContext()
        Builder globalConfigModule(GlobalConfigModule globalConfigModule);//有全局配置的话可以装配
        AppComponent build();
    }




}
