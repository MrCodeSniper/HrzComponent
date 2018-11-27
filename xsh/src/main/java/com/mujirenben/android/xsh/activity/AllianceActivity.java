package com.mujirenben.android.xsh.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;


import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.entity.TabEntity;
import com.mujirenben.android.xsh.fragment.AllianceHomeFragment;
import com.mujirenben.android.xsh.fragment.AllianceMineFragment;
import com.mujirenben.android.xsh.service.AllianceService;
import com.mujirenben.android.xsh.widget.NoScrollViewPager;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.PhonePlatformUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.widget.tablayout.CommonTabLayout;
import com.mujirenben.android.common.widget.tablayout.listener.CustomTabEntity;
import com.mujirenben.android.common.widget.tablayout.listener.OnTabSelectListener;
import com.mujirenben.android.xsh.entity.MediaEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mujirenben.android.xsh.constant.Constants.BASE_ALLIANCE_HOST;


@Route(path = ARouterPaths.ALLIANCE_ACTIVITY)
public class AllianceActivity extends BaseActivity {


    NoScrollViewPager vpContent;

    CommonTabLayout tlBottomTab;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "我的"};

    private int[] mIconUnselectIds = {
            R.drawable.icon_home_normal,
            R.drawable.icon_individual};
    private int[] mIconSelectIds = {
            R.drawable.icon_home,
            R.drawable.icon_individual_normal,};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    private Retrofit retrofit;
    private AllianceService allianceService;
    private OkHttpClient okHttpClient;

    public static void startSelf(Context context) {
        context.startActivity(new Intent(context, AllianceActivity.class));
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
//        StatusBarUtil.setStatusBarWhite(this);
        if (PhonePlatformUtil.isMIUI()){
            StatusBarUtil.getStatusBarHelper(this,R.color.white).into(this);
        }else {
            StatusBarUtil.setStatusBarWhite(this);
        }

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_alliance;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initView();
        initData();

    }




    public void initView() {
        vpContent = (NoScrollViewPager) findViewById(R.id.vp_content);
        vpContent.setScroll(false);
        tlBottomTab = (CommonTabLayout) findViewById(R.id.tab);
    }

    public void initData() {
        mFragments.add(new AllianceHomeFragment());
        mFragments.add(new AllianceMineFragment());

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        vpContent.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        initTab();
        prepareInit();
        getmediaRegister(); //媒体用户注册
    }


    private void initTab() {
        tlBottomTab.setTabData(mTabEntities);
        tlBottomTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpContent.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tlBottomTab.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    ///////////////////////////权限相关//////////////////////////////////////


    private void initPermission() {
        Log.e("hrz", "进入请求权限");
        // 要申请的权限 数组 可以同时申请多个权限
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (Build.VERSION.SDK_INT >= 23) {
            //如果超过6.0才需要动态权限，否则不需要动态权限
            //如果同时申请多个权限，可以for循环遍历
            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
            int check2 = ContextCompat.checkSelfPermission(this, permissions[1]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (check != PackageManager.PERMISSION_GRANTED || check2 != PackageManager.PERMISSION_GRANTED) {

                Log.e("hrz", "请求权限");
                //手动去请求用户打开权限(可以在数组中添加多个权限) 1 为请求码 一般设置为final静态变量
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                Log.e("hrz", "同意了");
                //写入你需要权限才能使用的方法
                initData();
            }
        } else {
            //写入你需要权限才能使用的方法
            initData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //回调，判断用户到底点击是还是否。
        //如果同时申请多个权限，可以for循环遍历
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //写入你需要权限才能使用的方法
            initData();

        } else {
            // 没有获取 到权限，从新请求，或者关闭app
            ArmsUtils.makeText(this, "需要获得定位权限");
        }
    }

    private int GPS_REQUEST_CODE = 10;


    /**
     * 检测GPS是否打开
     *
     * @return
     */
    private boolean checkGPSIsOpen() {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isOpen;
    }

    /**
     * 跳转GPS设置
     */
    private void openGPSSettings() {
        if (checkGPSIsOpen()) {
            //initLocation(); //自己写的定位方法
            initData();
        } else {
            //没有打开则弹出对话框
            new AlertDialog.Builder(this)
                    .setTitle(R.string.notifyTitle)
                    .setMessage(R.string.gpsNotifyMsg)
                    // 拒绝, 退出应用
                    .setNegativeButton(R.string.cancel,
                            (dialog, which) -> finish())
                    .setPositiveButton(R.string.setting,
                            (dialog, which) -> {
                                //跳转GPS设置界面
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(intent, GPS_REQUEST_CODE);
                            })
                    .setCancelable(false)
                    .show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            //做需要做的事情，比如再次检测是否打开GPS了 或者定位
            openGPSSettings();
        }
    }
    ///////////////////////////权限相关//////////////////////////////////////

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }



    private void prepareInit() {


        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(setRequestHeader())         //添加头拦截器
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_ALLIANCE_HOST)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .build();

        allianceService = retrofit.create(AllianceService.class);
    }

    /**
     * 设置请求头拦截器
     * @return
     */
    private Interceptor setRequestHeader(){
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        final Matcher m = p.matcher(BaseApplication.VER_CODE);
        Log.i(Consts.CHENHOGN_TAG,"retrofitSingle\t"+m.replaceAll("").trim());
        Interceptor interceptor= null;
        try {
            interceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("token", LoginDataManager.getsInstance(AllianceActivity.this).getAccountSecToken())
                            .header("uuid", BaseApplication.UUID)
                            .header("version",m.replaceAll("").trim())
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            };
        } catch (Exception e) {

        }
        return interceptor;
    }

    private void getmediaRegister() {
        Map<String,Object> map=new HashMap<>();
        map.put("avatarUrl",LoginDataManager.getsInstance(this).getAvatarUrl());
        map.put("wxNickName",LoginDataManager.getsInstance(this).getNickName());
        map.put("unionId",  LoginDataManager.getsInstance(this).getWeixinUnionId());
        map.put("mediaId", LoginDataManager.getsInstance(this).getUserId());
        map.put("mediaUserId",LoginDataManager.getsInstance(this).getWeixinUnionId());
        Gson gson=new Gson();
        String strEntity = gson.toJson(map);
        com.orhanobut.logger.Logger.e(strEntity);

        RequestBody requestBody =RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),strEntity);
        String userid=  LoginDataManager.getsInstance(this).getUserId()+"";
        Call<MediaEntity> call = allianceService.getMediaEntity(
                requestBody
        );
        call.enqueue(new Callback<MediaEntity>() {
            @Override
            public void onResponse(Call<MediaEntity> call, Response<MediaEntity> response) {
                if (!TextUtils.isEmpty(response.body().getData())) {
                    LoginDataManager.getsInstance(AllianceActivity.this).setMediaIdForAlliance(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<MediaEntity> call, Throwable t) {
                com.orhanobut.logger.Logger.e(t.getMessage()+"注册媒体Id失败");
            }
        });
    }


}
