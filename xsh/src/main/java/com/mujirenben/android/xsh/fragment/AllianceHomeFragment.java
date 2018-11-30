package com.mujirenben.android.xsh.fragment;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.R2;
import com.mujirenben.android.xsh.activity.AllianceShopDetailActivity;
import com.mujirenben.android.xsh.activity.DockingActivity;
import com.mujirenben.android.xsh.adapter.ShopAdapter;
import com.mujirenben.android.xsh.constant.Constants;
import com.mujirenben.android.xsh.entity.AddressBean;
import com.mujirenben.android.xsh.entity.AllianceBanner;
import com.mujirenben.android.xsh.entity.AllianceHomeShopBean;
import com.mujirenben.android.xsh.entity.AllianceHomeShopsEntity;
import com.mujirenben.android.xsh.entity.CityBean;
import com.mujirenben.android.xsh.entity.DistanceEntity;
import com.mujirenben.android.xsh.entity.IndustryBean;
import com.mujirenben.android.xsh.service.AllianceService;
import com.mujirenben.android.xsh.widget.AllianceCityPicker;
import com.mujirenben.android.xsh.widget.IndustryPicker;
import com.mujirenben.android.xsh.widget.SpaceItemDecoration;
import com.mujirenben.android.xsh.widget.xrecyclerview.ProgressStyle;
import com.mujirenben.android.xsh.widget.xrecyclerview.XRecyclerView;
import com.mujirenben.android.common.amap.AMapLocationHelper;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.base.BaseFragment;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.AppInfoUtils;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.EmptyUtils;
import com.mujirenben.android.common.util.LogUtils;
import com.mujirenben.android.common.util.ToastUtils;
import com.mujirenben.android.common.widget.DialogUtils;
import com.mujirenben.android.common.widget.StatusBarView;
import com.mujirenben.android.common.widget.indicatorLayout.IndicatorLayout;
import com.mylhyl.zxing.scanner.ScannerOptions;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import static com.mujirenben.android.common.base.BaseApplication.isLocation;
import static com.mujirenben.android.xsh.constant.Constants.BASE_ALLIANCE_HOST;


public class AllianceHomeFragment extends BaseFragment {

    public static  boolean isOpenLocation = false;
    @BindView(R2.id.tv_titlebar)
    TextView tvTitlebar;
    @BindView(R2.id.rl_titlebar)
    RelativeLayout rlTitlebar;

    BGABanner bannerAllianceHome;

    private static final int PAGE_SIZE = 10;

    ImageView ivSelect;

    XRecyclerView rvShopNearby;
    LinearLayout ll_discount;
    TextView tvCityName;
    LinearLayout ll_position_address;

    private AllianceCityPicker cityPicker;
    private IndustryPicker industryPicker;

    private LinearLayout ll_industry_all;
    private ShopAdapter shopAdapter;
    private ShopAdapter recommend_shopAdapter;

    private List<AllianceHomeShopBean.DataBean> list;
    private AMapLocationHelper aMapLocationHelper;


    private String selectQu = "新城区";
    private String shengfen = "陕西省";
    private String chengshi = "西安市";

    private String firstIndustry = "全部";
    private String SecondIndustry = "全部分类";


    private View headerview;
    private int page = 1;
    private DialogUtils loadingDialog;


    //保存的状态
    private String nowCity = "西安市";
    private int discount = 0;
    private Integer mIndustryId = null;
    private ImageView iv_back;
    private TextView tv_industry;
    private RelativeLayout rl_nomore;
    private ArrayList<IndustryBean.DataBean.SonsBean> sonsBeanArrayList;
    private LinearLayout ll_positon_near;
    private TextView tv_near;
    private double mlatitude;  //经度
    private double mlongitude;  //纬度
    private int postion = 10;         //距离位置索引

    private int mindistance = 0;   //最近距离
    private int maxdistance = 1000000000;   //最远距离
    private boolean IsFirst;  //切换全部的标识。

    private PopupWindow			sharepop;

    private Intent intent;
    private Unbinder unbinder;
    private AllianceService allianceService;
    private List<String> banner_list = new ArrayList<>();
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    StatusBarView mStatusBarView;
    IndicatorLayout indicatorLayout;
    private RelativeLayout rl_parent;

    private List<AllianceHomeShopsEntity.ShopBean> shopBeanList = new ArrayList<>();

    private View recommend_footview;
    private TextView tv_recommend;
    private RecyclerView rv_shop_recommend;

    private String version;

    private boolean isHiddened  = false;
    AllianceHomeShopsEntity testEntity = new AllianceHomeShopsEntity();
    private RxPermissions mRxPermissions;
    /**不是刷新*/
    private boolean unRefresh = true;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        loadingDialog = new DialogUtils(getActivity(), R.layout.common_loading_toast, "");
        View view = inflater.inflate(R.layout.fragment_alliance_homes, container, false);
        //初始化全部行业
        sonsBeanArrayList = new ArrayList<>();
        IndustryBean.DataBean.SonsBean sonsBean = new IndustryBean.DataBean.SonsBean();
        sonsBean.setId(-1);
        sonsBean.setName("全部分类");
        sonsBeanArrayList.add(sonsBean);
        mStatusBarView = view.findViewById(R.id.status_bar_view);
        mStatusBarView.setBackgroundColor(getResources().getColor(R.color.white));
        mStatusBarView.adjustStatusBarColor(getActivity());
        headerview = inflater.inflate(R.layout.recycle_view_home_header, null, false);
        prepareView(view, headerview);
        unbinder = ButterKnife.bind(this, view);
        version = AppInfoUtils.getVersionName(getActivity());
        return view;
    }

    private void prepareView(View rootview, View headerview) {
        tv_near = (TextView) headerview.findViewById(R.id.tv_near);
        ll_positon_near = (LinearLayout) headerview.findViewById(R.id.ll_positon_near);
        ll_positon_near.setOnClickListener((View v) -> seletNearShop());
        iv_back = (ImageView) rootview.findViewById(R.id.iv_back);
        rl_nomore = (RelativeLayout) headerview.findViewById(R.id.rl_nomore);
        rvShopNearby = (XRecyclerView) rootview.findViewById(R.id.rv_shop_nearby);
        ll_industry_all = (LinearLayout) headerview.findViewById(R.id.ll_industry_all);
        bannerAllianceHome = (BGABanner) headerview.findViewById(R.id.banner_alliance_home);
        ll_discount = (LinearLayout) headerview.findViewById(R.id.ll_discount);
        tvCityName = (TextView) headerview.findViewById(R.id.alliance_home_city_tv);
        ll_position_address = (LinearLayout) headerview.findViewById(R.id.ll_position_address);
        ivSelect = (ImageView) headerview.findViewById(R.id.iv_select);
        ivSelect.setSelected(false);
        tv_industry = (TextView) headerview.findViewById(R.id.tv_industry);
        indicatorLayout = headerview.findViewById(R.id.indicatorLayout);
        indicatorLayout.removePointView();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mStatusBarView.adjustStatusBarColor(getActivity());
        prepareInit();
        rvShopNearby.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        shopAdapter = new ShopAdapter(R.layout.recycleview_item_home_shop);
        shopAdapter.removeAllHeaderView();
        shopAdapter.removeAllFooterView();
        shopAdapter.addHeaderView(headerview);

        recommend_footview = View.inflate(getActivity(), R.layout.fragment_alliance_foot, null);
        tv_recommend = (TextView) recommend_footview.findViewById(R.id.tv_recommend);
        rl_parent = (RelativeLayout) recommend_footview.findViewById(R.id.rl_parent);

        rv_shop_recommend = (RecyclerView) recommend_footview.findViewById(R.id.rv_shop_recommend);

        shopAdapter.addFooterView(recommend_footview);
        shopAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rvShopNearby.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        rvShopNearby.addItemDecoration(new SpaceItemDecoration(0));
        rvShopNearby.setAdapter(shopAdapter);

        rv_shop_recommend.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recommend_shopAdapter = new ShopAdapter(R.layout.recycleview_item_home_shop);
        rv_shop_recommend.setFocusable(false);
        rv_shop_recommend.addItemDecoration(new SpaceItemDecoration(0));
        rv_shop_recommend.setAdapter(recommend_shopAdapter);

        aMapLocationHelper = new AMapLocationHelper(getActivity());

        prepareListener();
        request_banner_data();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void prepareInit() {
        okHttpClient = new OkHttpClient.Builder()
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


    @Override
    public void onResume() {
        super.onResume();
//        if(!isLocation){
//            startLocation();
//        }
    }

    /**
     * 开始定位
     */
    public void startLocation(){
        if(isOPen()){
            aMapLocationHelper.init((province, city, latitude, longitude) -> {
                isLocation = true;
                if (!TextUtils.isEmpty(city)) {
                    shengfen = province;
                    if (city.equals("北京市") || city.equals("天津市") || city.equals("重庆市") || city.equals("上海市")) {
                        chengshi = "市辖区";
                    } else {
                        chengshi = city;
                    }
                    mlatitude = latitude;
                    mlongitude = longitude;
                    nowCity = city;
                    tvCityName.setText(city);
                    request_first_page(0);// 找到定位才请求
                } else {
                    mlatitude = 0;
                    mlongitude = 0;
                    nowCity = "北京市";
                    tvCityName.setText("北京市");
//                            showNoData("请设置定位");
                    dismissDialog();
                    request_first_page(0);// 默认请求西安
                }

                if (EmptyUtils.isNotEmpty(mlatitude)) {
                    LoginDataManager.getsInstance(getActivity()).setUserLatitude(mlatitude + "");
                } else {
                    LoginDataManager.getsInstance(getActivity()).setUserLatitude("");
                }
                if (EmptyUtils.isNotEmpty(mlongitude)) {
                    LoginDataManager.getsInstance(getActivity()).setUserLongtitude(mlongitude + "");
                } else {
                    LoginDataManager.getsInstance(getActivity()).setUserLongtitude("");
                }
            }, 100);
        }else {
            ArmsUtils.makeText(getActivity(),"请打开gps获取更精确定位");
        }
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @return true 表示开启
     */
    public boolean isOPen() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        isOpenLocation = false;
        return false;
    }

    private void prepareListener() {

        iv_back.setOnClickListener((View v) -> {
            BaseApplication.isGoNextActivity = false;
            getActivity().finish();
        });
        ll_position_address.setOnClickListener((View v) -> requestOpenCities());
        ll_industry_all.setOnClickListener((View v) -> request_industry());


        rvShopNearby.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadingDialog.show();
                request_banner_data();
                unRefresh = false;
                request_first_page(discount);
            }

            @Override
            public void onLoadMore() {
                rl_parent.setVisibility(View.VISIBLE);
                unRefresh = false;
                if (page < Integer.MAX_VALUE) {
                    ++page;
                    request_more_page();
                }
            }
        });

        recommend_shopAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position) -> {
            if (!LoginDataManager.getsInstance(getActivity()).isLogin()) {
                ARouter.getInstance()
                        .build(ARouterPaths.LOGIN_MAIN_MINE)
                        .withString(Consts.LOGIN_SOURCE_KEY,"店铺详情")
                        .navigation(getActivity());
                return;
            }

            AllianceHomeShopsEntity.ShopBean bean = (AllianceHomeShopsEntity.ShopBean) recommend_shopAdapter.getData()
                    .get(position);
            AllianceShopDetailActivity.startSelf(getActivity(), bean.getId());
        });


        shopAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position) -> {
            if (!LoginDataManager.getsInstance(getActivity()).isLogin()) {
                ARouter.getInstance()
                        .build(ARouterPaths.LOGIN_MAIN_MINE)
                        .withString(Consts.LOGIN_SOURCE_KEY,"店铺详情")
                        .navigation(getActivity());
                return;
            }
            AllianceHomeShopsEntity.ShopBean bean = (AllianceHomeShopsEntity.ShopBean) shopAdapter.getData().get(position - 1);
            AllianceShopDetailActivity.startSelf(getActivity(), bean.getId());
        });


        ll_discount.setOnClickListener((View v) -> {
            if (ivSelect.isSelected()) {
                discount = 0;
                ivSelect.setSelected(false);
                loadingDialog.show();
                page = 1;
                request_first_page(0);
            } else {
                discount = 1;
                ivSelect.setSelected(true);
                loadingDialog.show();
                page = 1;
                request_first_page(1);
            }
        });
    }

    private void seletNearShop() {

        List<DistanceEntity> distanceList = new ArrayList();
        distanceList.add(new DistanceEntity("全部"));
        distanceList.add(new DistanceEntity("<0.5km"));
        distanceList.add(new DistanceEntity("0.5km-1km"));
        distanceList.add(new DistanceEntity("1km-2km"));
        distanceList.add(new DistanceEntity("2km-3km"));
        distanceList.add(new DistanceEntity("3km-4km"));
        distanceList.add(new DistanceEntity(">4km"));
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(getActivity(),new OptionPickerCallback(distanceList)).setBackgroundId(0xa0000000).build();
        pvOptions.setPicker(distanceList);
        pvOptions.show();
    }

    /**
     * 请求全部行业列表
     */
    private void request_industry() {

        Call<IndustryBean> call_industry = allianceService.getAllianceOpenIndustry();
        call_industry.enqueue(new Callback<IndustryBean>() {
            @Override
            public void onResponse(Call<IndustryBean> call, Response<IndustryBean> response) {
                Log.e(Constants.TAG, response.body().toString());
                if (response.body() != null) {
                    Log.e(Constants.TAG, response.body().toString());
                    IndustryBean industryBean = response.body();
                    if (industryBean.getCode().equals(Constants.ALLIANCE_REQUEST_SUCCESS)) {
                        Log.e(Constants.TAG, response.body().toString());

                        IndustryBean.DataBean all_industry = new IndustryBean.DataBean();
                        all_industry.setId(-1);
                        all_industry.setName("全部");
                        all_industry.setSons(sonsBeanArrayList);
                        industryBean.getData().add(0, all_industry);

                        for (int i = 1; i < industryBean.getData().size(); i++) {
                            IndustryBean.DataBean dataBean = industryBean.getData().get(i);
                            IndustryBean.DataBean.SonsBean sonsBean = new IndustryBean.DataBean.SonsBean();
                            sonsBean.setName("全部" + dataBean.getName());
                            sonsBean.setId(dataBean.getId());
                            dataBean.getSons().add(0, sonsBean);
                        }


                        initIndustryPicker(industryBean);


                        if (!industryPicker.isShow()) {
                            industryPicker.show();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<IndustryBean> call, Throwable t) {
                ArmsUtils.makeText(getActivity(),getResources().getString(R.string.network_error));
            }
        });


    }

    private void initIndustryPicker(IndustryBean industryBean) {
        industryPicker = new IndustryPicker.Builder(getActivity()).textSize(20)
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .province(firstIndustry)
                .city(SecondIndustry)
                .setAllDatas(industryBean)
                .district(selectQu)
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(false)
                .cityCyclic(false)
                .onlyShowProvinceAndCity(true)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .build();

        industryPicker.setOnCityItemClickListener(new IndustryPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onChoose(Integer industryId, String industryName, String fatherProvince) {
                Log.e(Constants.TAG, industryId + "xx");

                if (industryId == -1) {
                    mIndustryId = null;
                    firstIndustry = "全部";
                    SecondIndustry = "全部分类";
                    tv_industry.setText("全部分类");
                    page = 1;
                    request_first_page(discount);
                } else {
                    firstIndustry = fatherProvince;
                    SecondIndustry = industryName;
                    mIndustryId = industryId;
                    tv_industry.setText(industryName);
                    page = 1;
                    request_first_page(discount);
                }


            }
        });
    }

    /**
     * 请求全部开放城市
     */
    private void requestOpenCities() {
        Call<AddressBean> call_open_cities = allianceService.getOpenCityes();
        call_open_cities.enqueue(new Callback<AddressBean>() {
            @Override
            public void onResponse(Call<AddressBean> call, Response<AddressBean> response) {
                if (response.body() != null) {
                    AddressBean industryBean = response.body();
                    if (industryBean.getCode().equals(Constants.ALLIANCE_REQUEST_SUCCESS)) {
                        initCityPicker(industryBean);
                        if (!cityPicker.isShow()) {
                            cityPicker.show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AddressBean> call, Throwable t) {
                ArmsUtils.makeText(getActivity(),getResources().getString(R.string.network_error));
            }
        });


    }

    private void initCityPicker(AddressBean industryBean) {
        cityPicker = new AllianceCityPicker.Builder(getActivity()).textSize(20)
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .province(shengfen)
                .city(chengshi)
                .district(selectQu)
                .setAllDatas(industryBean)
                .onlyShowProvinceAndCity(true)
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(false)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .build();

        cityPicker.setOnCityItemClickListener(new AllianceCityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //保存选中状态
                shengfen = citySelected[0];
                chengshi = citySelected[1];

                nowCity = citySelected[1];

                if (citySelected[0].contains("市")) {
                    tvCityName.setText(citySelected[0]);
                    nowCity = citySelected[0];

                } else {
                    tvCityName.setText(citySelected[1]);
                }

                page = 1;
                request_first_page(discount);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onChoose(Integer industryId, String industryName) {

            }
        });
    }

    private void request_banner_data() {
        banner_list.clear();
        Call<AllianceBanner> call_banner = allianceService.getAllianceBanner(3, 377+"");
        call_banner.enqueue(new Callback<AllianceBanner>() {
            @Override
            public void onResponse(Call<AllianceBanner> call, Response<AllianceBanner> response) {
                if (response.body() != null) {
                    AllianceBanner allianceHomeShopBean = response.body();
                    if (Constants.ALLIANCE_REQUEST_SUCCESS.equals(allianceHomeShopBean.getCode())) {
                        Log.e("yyy", response.body().toString());
                        if(allianceHomeShopBean == null )return;
                        for (AllianceBanner.DataBean banner_item : allianceHomeShopBean.getData()) {
                            banner_list.add(banner_item.getImg());
                        }
                        bannerAllianceHome.setData(R.layout.banner_item, banner_list, null);
                        bannerAllianceHome.setAdapter((banner, itemView, url, position) -> {
                            ImageView simpleDraweeView = itemView.findViewById(R.id.sdv_item_fresco_content);
                            Glide.with(getActivity()).load(url).into(simpleDraweeView);
                        });

                        bannerAllianceHome.setDelegate((banner, itemView, model, position) -> {
                            if (allianceHomeShopBean.getData().get(position).getUrl().contains("dock")) {
                                if (!LoginDataManager.getsInstance(getActivity()).isLogin()) {
                                    ArmsUtils.makeText(getActivity(), "登录后才能进行操作哦~");
                                    ARouter.getInstance()
                                            .build(ARouterPaths.LOGIN_MAIN_MINE)
                                            .withString(Consts.LOGIN_SOURCE_KEY,"成为对接人")
                                            .navigation(getActivity());
                                } else {
                                    DockingActivity.startSelf(getActivity());
                                }
                            }
                        });
                        //当banner只有一个时，不轮播
                        if(banner_list.size() == 1){
                            bannerAllianceHome.setAutoPlayAble(false);
                            indicatorLayout.setVisibility(View.GONE);
                        }else {
                            bannerAllianceHome.setAutoPlayAble(true);
                            indicatorLayout.removePointView();
                            indicatorLayout.setItemSize(banner_list.size());
                            //设置第一张图片为选中状态
                            indicatorLayout.setCurrentItemPosition(0);
                        }

                        bannerAllianceHome.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                indicatorLayout.setCurrentItemPosition(position);
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
                        loadingDialog.hide();
                    } else {
                        Log.e("zzz", response.body().toString());
                    }

                }
            }

            @Override
            public void onFailure(Call<AllianceBanner> call, Throwable t) {
                ArmsUtils.makeText(getActivity(),getResources().getString(R.string.network_error));
                loadingDialog.hide();
            }


        });

    }

    private void request_first_page(int isCoupon) {
        Log.e("request_first_page","--------");
        shopBeanList.clear();
        recommend_shopAdapter.setNewData(shopBeanList);
        switch (postion) {
            case 1:
                mindistance = 0;
                maxdistance = 500;
                break;
            case 2:
                mindistance = 500;
                maxdistance = 1000;
                break;
            case 3:
                mindistance = 1000;
                maxdistance = 2000;
                break;
            case 4:
                mindistance = 2000;
                maxdistance = 3000;
                break;
            case 5:
                mindistance = 3000;
                maxdistance = 4000;
                break;
            case 6:
                mindistance = 4000;
                maxdistance = 1000000000;
                break;
            case 0:
                mindistance = 0;
                maxdistance = 1000000000;
                break;

        }

        if (loadingDialog != null && unRefresh) {
            loadingDialog.show();
        }

        Call<AllianceHomeShopsEntity> call = allianceService.getAllianceHomeShops(version, page, PAGE_SIZE, mIndustryId,
                nowCity, isCoupon, mlatitude, mlongitude, mindistance, maxdistance, 1);

        //
        call.enqueue(new Callback<AllianceHomeShopsEntity>() {
            @Override
            public void onResponse(Call<AllianceHomeShopsEntity> call, Response<AllianceHomeShopsEntity> response) {
                if (response.body() != null) {
                    rl_nomore.setVisibility(View.GONE);
                    testEntity =response.body();
                    AllianceHomeShopsEntity allianceHomeShopBean = response.body();
                    if (Constants.ALLIANCE_REQUEST_SUCCESS.equals(allianceHomeShopBean.getCode())) {
                        rl_parent.setVisibility(View.VISIBLE);
                        if (allianceHomeShopBean.getData().getAccordList().size() == 0) {
                            // showNoData("很抱歉，找不到商家数据");
                            rl_nomore.setVisibility(View.VISIBLE);
                            shopAdapter.setNewData(null);
                            recommend_shopAdapter.setNewData(null);
                            shopBeanList.addAll(allianceHomeShopBean.getData().getOtherList());
                            recommend_shopAdapter.setNewData(shopBeanList);
                        } else {
                            rvShopNearby.setVisibility(View.VISIBLE);
                            shopBeanList.addAll(allianceHomeShopBean.getData().getOtherList());
                            shopAdapter.setNewData(allianceHomeShopBean.getData().getAccordList());
                            if (allianceHomeShopBean.getData().getAccordList().size() >= 10) {

                            } else {

                                recommend_shopAdapter.setNewData(shopBeanList);
                            }

                        }

                        if (rvShopNearby != null) {
                            rvShopNearby.refreshComplete();
                        }

                    }
                } else {
                    rl_nomore.setVisibility(View.VISIBLE);
                    shopAdapter.setNewData(null);
                }
                dismissDialog();
            }

            @Override
            public void onFailure(Call<AllianceHomeShopsEntity> call, Throwable t) {
                if (rvShopNearby != null) {
                    rvShopNearby.refreshComplete();
                }
                dismissDialog();
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        if (aMapLocationHelper != null) {
            aMapLocationHelper.onDestroy();
        }
    }

    public void dismissDialog() {
        unRefresh = true;
        loadingDialog.hide();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void request_more_page() {

        switch (postion) {
            case 1:
                mindistance = 0;
                maxdistance = 500;
                break;
            case 2:
                mindistance = 500;
                maxdistance = 1000;
                break;
            case 3:
                mindistance = 1000;
                maxdistance = 2000;
                break;
            case 4:
                mindistance = 2000;
                maxdistance = 3000;
                break;
            case 5:
                mindistance = 3000;
                maxdistance = 4000;
                break;
            case 6:
                mindistance = 4000;
                maxdistance = 1000000000;
                break;
            case 0:
                mindistance = 0;
                maxdistance = 1000000000;
                break;

        }
        Call<AllianceHomeShopsEntity> call = allianceService.getAllianceHomeShops(version, page, PAGE_SIZE, mIndustryId,
                nowCity, discount, mlatitude, mlongitude, mindistance, maxdistance, 1);

        call.enqueue(new Callback<AllianceHomeShopsEntity>() {
            @Override
            public void onResponse(Call<AllianceHomeShopsEntity> call, Response<AllianceHomeShopsEntity> response) {

                if (response.body() != null) {
                    AllianceHomeShopsEntity allianceHomeShopBean = response.body();

                    if (Constants.ALLIANCE_REQUEST_SUCCESS.equals(allianceHomeShopBean.getCode())) {
                        if (allianceHomeShopBean.getData().getAccordList().size() == 0) {
                            // showNoData("无更多数据");
                            // page--;
                            rl_parent.setVisibility(View.VISIBLE);
                            recommend_shopAdapter.setNewData(null);
                            shopBeanList.addAll(allianceHomeShopBean.getData().getOtherList());

                            recommend_shopAdapter.setNewData(shopBeanList);
                        } else {
                            shopAdapter.addData(allianceHomeShopBean.getData().getAccordList());
                            shopAdapter.notifyDataSetChanged();
                            shopBeanList.addAll(allianceHomeShopBean.getData().getOtherList());
                            if (allianceHomeShopBean.getData().getAccordList().size() >= 10) {

                            } else {

                                recommend_shopAdapter.setNewData(shopBeanList);
                            }

                        }
                        if (rvShopNearby != null) {
                            rvShopNearby.loadMoreComplete();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AllianceHomeShopsEntity> call, Throwable t) {
//                showToast(R.string.network_error, Toast.LENGTH_SHORT);
                // page--;
                if (rvShopNearby != null) {
                    rvShopNearby.refreshComplete();
                }
                if (loadingDialog != null) {
                    loadingDialog.hide();
                }
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHiddened = hidden;
        if(!hidden){
            mStatusBarView.setBackgroundColor(0x00000000);
            mStatusBarView.adjustStatusBarColor(getActivity());
            if(mRxPermissions == null){
                mRxPermissions = new RxPermissions(getActivity());
            }
            mRxPermissions.requestEach(Manifest.permission.ACCESS_COARSE_LOCATION)
                    .subscribe(permission -> {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            if (!isLocation){
                                //已经成功定位过，则不需要重新定位
                                startLocation();
                            }
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            mlatitude = 0;
                            mlongitude = 0;
                            nowCity = "北京市";
                            tvCityName.setText("北京市");
                            request_first_page(0);// 默认请求西安
                            ArmsUtils.makeText(getActivity(),"未授权权限，定位功能不能使用");
                        } else {
                            mlatitude = 0;
                            mlongitude = 0;
                            nowCity = "北京市";
                            tvCityName.setText("北京市");
                            request_first_page(0);// 默认请求西安
                            // 用户拒绝了该权限，并且选中『不再询问』
                            ArmsUtils.makeText(getActivity(),"未授权权限，定位功能不能使用");
                        }
                    });
        }
    }


    class OptionPickerCallback implements OptionsPickerView.OnOptionsSelectListener {

        private List<DistanceEntity> mDistanceList;


        public OptionPickerCallback(List<DistanceEntity> distanceList) {
            this.mDistanceList=distanceList;
        }

        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            //返回的分别是三个级别的选中位置
            postion = options1;
            String tx = mDistanceList.get(options1).getPickerViewText();
            switch (postion) {
                case 1:
                    tv_near.setText(tx + "");
                    break;
                case 2:
                    tv_near.setText("0.5km-1km");
                    break;
                case 3:
                    tv_near.setText("1km-2km");
                    break;
                case 4:
                    tv_near.setText("2km-3km");
                    break;
                case 5:
                    tv_near.setText("3km-4km");
                    break;
                case 6:
                    tv_near.setText(">4km");
                    break;
                case 0:
                    tv_near.setText("全部");
                    break;

            }
            page = 1;

            tv_near.setText(mDistanceList.get(postion).getDistance());

            request_first_page(discount);
        }
    }
}
