package com.mujirenben.android.xsh.activity;
//Thanks For Your Reviewing My Code 
//Please send your issues email to 15168264355@163.com when you find there are some bugs in My class 
//You Can add My wx 17620752830 and we can communicate each other about IT industry
//Code Programming By MrCodeSniper on 2018/8/22.Best Wishes to You!  []~(~▽~)~* Cheers!


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;

import java.util.List;

public class Amap2DActivity extends BaseActivity implements LocationSource, AMapLocationListener {

    private MapView mapView;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    private String storeAddress = null;
    private String storeCity = null;
    private String storeName;

    private TextView tv_title_bar;
    private ImageView iv_back;

    public static final String EXTRA_STORE_NAME = "store_name";
    public static final String EXTRA_STORE_ADDRESS = "store_address";
    public static final String EXTRA_STORE_CITY = "store_city";

    private final static LatLng SYDNEY = new LatLng(-33.86759, 151.2088);
    private final static LatLng BEIJING = new LatLng(39.8965, 116.4074);


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
//        StatusBarUtil.setStatusBarWhite(this);
        if (SpUtil.getIsMIUI(this)){
            StatusBarUtil.getStatusBarHelper(this,R.color.white).into(this);
        }else {
            StatusBarUtil.setStatusBarWhite(this);
        }
        Intent intent = getIntent();

        if (intent != null) {
            storeAddress = intent.getStringExtra(EXTRA_STORE_ADDRESS);
            storeCity = intent.getStringExtra(EXTRA_STORE_CITY);
            storeName = intent.getStringExtra(EXTRA_STORE_NAME);
        }
        return R.layout.amap_2d_map_activity_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        tv_title_bar=findViewById(R.id.tv_titlebar);
        tv_title_bar.setText(storeName);
        iv_back=findViewById(R.id.tv_back);
        iv_back.setOnClickListener(v -> finish());


        mapView = (MapView) findViewById(R.id.amap_2d_mapview);
        mapView.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(Amap2DActivity.this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//			mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // aMap.setMyLocationType()
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BEIJING, 14));
        aMap.invalidate();

//		storeAddress = "白云区平安小学";
//		storeCity = "广州市";
        GeocodeSearch geocodeSearch = new GeocodeSearch(getApplicationContext());
        GeocodeQuery geocodeQuery = new GeocodeQuery(storeAddress, storeCity);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                double lat = regeocodeResult.getRegeocodeAddress().getPois().get(0).getLatLonPoint().getLatitude();
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                if (i == 1000) {
                    List<GeocodeAddress> addressList =  geocodeResult.getGeocodeAddressList();
                    if (addressList != null && addressList.size() > 0) {
                        GeocodeAddress geocodeAddress = addressList.get(0);
                        LatLonPoint latLonPoint = geocodeAddress.getLatLonPoint();
                        if (latLonPoint != null) {
                            double lat = latLonPoint.getLatitude();
                            double lon = latLonPoint.getLongitude();

                            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 16));
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(new LatLng(lat, lon));
                            markerOptions.title(storeName);
                            markerOptions.visible(true);

                            ImageView imageView = new ImageView(Amap2DActivity.this);
                            imageView.setImageResource(R.drawable.location_marker);
                            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(imageView);
                            markerOptions.icon(bitmapDescriptor);
                            aMap.addMarker(markerOptions);
                        }
                    }
                }
            }
        });
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);

    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }


}
