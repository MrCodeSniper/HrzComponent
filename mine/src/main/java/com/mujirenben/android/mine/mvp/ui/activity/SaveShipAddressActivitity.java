package com.mujirenben.android.mine.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.base.greendao.City;
import com.mujirenben.android.common.base.greendao.Province;
import com.mujirenben.android.common.base.greendao.Zone;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.datapackage.bean.DbTag;
import com.mujirenben.android.common.datapackage.bean.DefaultShippingAddress;
import com.mujirenben.android.common.datapackage.bean.MessageEventGlobal;
import com.mujirenben.android.common.datapackage.bean.SelfRunGoodsBean;
import com.mujirenben.android.common.entity.AddressBean;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.widget.LoadingDialog;
import com.mujirenben.android.common.widget.tablayout.CommonTabLayout;
import com.mujirenben.android.common.widget.tablayout.listener.CustomTabEntity;
import com.mujirenben.android.common.widget.tablayout.listener.OnTabSelectListener;
import com.mujirenben.android.mine.di.component.DaggerUserInfoComponent;
import com.mujirenben.android.mine.di.module.UserInfoModule;
import com.mujirenben.android.mine.mvp.contract.UserInfoContract;
import com.mujirenben.android.mine.mvp.presenter.UserInfoPresenter;
import com.mujirenben.android.mine.mvp.ui.adapter.AddressAdapter;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.RequestBody;

////写的丑 待修改
@Route(path = ARouterPaths.USER_INFOS)
public class SaveShipAddressActivitity extends BaseActivity<UserInfoPresenter> implements UserInfoContract.View {

    @BindView(R2.id.tv_back)
    ImageView tvBack;
    @BindView(R2.id.tv_titlebar)
    TextView tvTitlebar;
    @BindView(R2.id.tv_choose_location)
    TextView tvChooseLocation;
    @BindView(R2.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R2.id.iv_default_address_select)
    ImageView ivDefaultAddressSelect;
    @BindView(R2.id.btn_submmit)
    Button btnSubmmit;
    @BindView(R2.id.tv_location_info)
    TextView tv_location_info;
    @BindView(R2.id.et_name)
    EditText etName;
    @BindView(R2.id.et_phone)
    EditText etPhone;
    @BindView(R2.id.et_address_detail_info)
    EditText etAddressDetailInfo;
    private CommonTabLayout tabLayout;
    private AddressAdapter addressAdapter;
    private static final String PROVINCE_TAG = "省级城市";
    private static final String CITY_TAG = "市级城市";
    private static final String ZONE_TAG = "县/区级城市";
    private boolean isEdit=false;
    private boolean isNeedRequireThird=false;
    private List<Province> provinceList=new ArrayList<>();
    private List<City> cityList=new ArrayList<>();
    private List<Zone> zoneList=new ArrayList<>();
    private RelativeLayout rl_choose_area;
    private AddressBean.DataBean mShipAddress;
    private ArrayList<CustomTabEntity> entities;
    private ArrayList<DbTag> strlist;
    private Dialog dialog;
    boolean is_defalut = false;
    private AddressBean.DataBean bundledata;
    private Disposable disposable;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserInfoComponent
                .builder()
                .appComponent(appComponent)
                .userInfoModule(new UserInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
//        StatusBarUtil.setStatusBarWhite(this);
        if (SpUtil.getIsMIUI(this)){
            StatusBarUtil.getStatusBarHelper(this,R.color.white).into(this);
        }else {
            StatusBarUtil.setStatusBarWhite(this);
        }
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.mine_activity_ship_address;
    }

    private boolean mFromBought; // 从购买页面跳转过来的
    private SelfRunGoodsBean mData;
    private long mSkuId;
    private int mQuantity;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // 确认购买时没有默认地址时，跳转过来，之后还要回到下单页面
        mFromBought = getIntent().getBooleanExtra("from_bought", false);
        mData = (SelfRunGoodsBean)getIntent().getSerializableExtra("self_run_goods_info");
        mSkuId = getIntent().getLongExtra("sku_id", -1);
        mQuantity = getIntent().getIntExtra("quantity", 1);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        if(bundle!=null){
            isEdit=true;
            bundledata= (AddressBean.DataBean) bundle.getSerializable("shipaddress");
            mShipAddress=bundledata;//传来这个包不是保存而是编辑 要修改表中数据
        }else {
            isEdit=false;
        }

        rl_choose_area=findViewById(R.id.rl_choose_area);
        tvTitlebar.setText("收货地址");
        DbTag dbTag = new DbTag();
        dbTag.setTag_name("广东省");
        dbTag.setTarget_id(19L);
        addressAdapter = new AddressAdapter(Arrays.asList(dbTag));


        if(mShipAddress==null){
            mShipAddress = new AddressBean.DataBean();//可能为bundle对象
        }



        tvBack.setOnClickListener((View v) -> finish());
        btnSubmmit.getBackground().setAlpha(133);
        rl_choose_area.setOnClickListener((View v) -> showSelectAddressDialog());
        btnSubmmit.setOnClickListener((View v)-> {
            String phoneStr = etPhone.getText().toString();
            if(!TextUtils.isEmpty(phoneStr)){
//                Pattern pattern = Pattern.compile(PHONE_NUMBER_REG);
//                Matcher matcher = pattern.matcher(phoneStr);
//                boolean match = Pattern.matches(PHONE_NUMBER_REG, phoneStr);
//                if(match){
                    if(phoneStr.length()<11){
                        ArmsUtils.makeText(getApplicationContext(),"手机号码格式错误");
                    }else {
                        LoadingDialog.getInstance(getActivity()).show();
                        submitSaveShipAddress();
                    }

                }else {
                    ArmsUtils.makeText(getApplicationContext(),"手机号码格式错误");
                }
        });
        ivDefaultAddressSelect.setOnClickListener((View v) -> selectDefault());
        strlist = new ArrayList<>();


        if(bundledata!=null){
            etName.setText(bundledata.getUserName());
            etPhone.setText(bundledata.getPhone());
            etAddressDetailInfo.setText(bundledata.getDetailInfo());
            ivDefaultAddressSelect.setSelected(bundledata.getFlag()==1?true:false);
            tv_location_info.setText(bundledata.getProvinceName() + bundledata.getCityName() + bundledata.getCountyName());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        judgeEditTextNotEmpty();
    }

    /**
     * 判断输入的信息如果有空的，保存按键则显示灰色
     */
    private void judgeEditTextNotEmpty() {
        disposable = Flowable.interval(100, TimeUnit.MILLISECONDS)
                .onBackpressureDrop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {

                    if(etName!=null){
                        if(TextUtils.isEmpty(etName.getText().toString())
                                ||etPhone.getText().toString().length()!=11
                                ||TextUtils.isEmpty(tv_location_info.getText().toString())
                                ||TextUtils.isEmpty(etAddressDetailInfo.getText().toString())){

                            btnSubmmit.getBackground().setAlpha(133);
                            btnSubmmit.setEnabled(false);
                            return;
                        }
                        btnSubmmit.getBackground().setAlpha(255);
                        btnSubmmit.setEnabled(true);
                    }

                });
    }

    private void submitSaveShipAddress(){

        mShipAddress.setUserName(etName.getText().toString());
        mShipAddress.setPhone(etPhone.getText().toString());
        mShipAddress.setFlag(ivDefaultAddressSelect.isSelected()?1:0);
        mShipAddress.setDetailInfo(etAddressDetailInfo.getText().toString());


        if(mShipAddress!=null){
                    Logger.e(mShipAddress.toString());
                    HashMap<String,String> map= new HashMap<String, String>();
                    map.put("accountSecToken", LoginDataManager.getsInstance(this).getAccountSecToken());
                    map.put("phone",mShipAddress.getPhone());
                    map.put("provinceName",mShipAddress.getProvinceName());
                    map.put("cityName",mShipAddress.getCityName());
                    map.put("countyName",mShipAddress.getCountyName());
                    map.put("detailInfo",mShipAddress.getDetailInfo());
                    map.put("flag",mShipAddress.getFlag()+"");//TODO 识别不了默认
                    map.put("userName",mShipAddress.getUserName());
                    if(isEdit){
                        map.put("id",mShipAddress.getId()+"");
                    }

                    HashMap<String,String> signed_map= HttpParamUtil.getCommonSignParamMap(this,map);
                    Logger.e(new Gson().toJson(signed_map));
                    RequestBody requestBody =RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(signed_map));
                    RetrofitUrlManager.getInstance().putDomain("user_address", Consts.PROTOCROL+Consts.OFFICIAL_SERVER);
                    mPresenter.saveShipAddressServer(requestBody);
                }




    }

    void selectDefault() {
        ivDefaultAddressSelect.setSelected(!is_defalut);
        is_defalut = !is_defalut;
    }

    //写的丑 待修改
    void showSelectAddressDialog() {

        dialog.setContentView(R.layout.mine_dialog_select_address_view);
        ImageView iv_close = dialog.findViewById(R.id.iv_closedialog);
        iv_close.setOnClickListener((View v) -> dialog.cancel());
        tabLayout = dialog.findViewById(R.id.stl_select_address);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if(position==0){
                    strlist.clear();
                    for (Province item : provinceList) {
                        DbTag dbTag = new DbTag();
                        if(item.getProName().equals(mShipAddress.getProvinceName())){
                            dbTag.setSelected(true);
                        }else {
                            dbTag.setSelected(false);
                        }
                        dbTag.setTarget_id(item.getProSort());
                        dbTag.setTag_name(item.getProName());
                        dbTag.setBelong(PROVINCE_TAG);
                        strlist.add(dbTag);
                    }
                }else if(position==1){
                    strlist.clear();

                    if(isEdit){//编辑模式点击请求数据
                        for(Province item : provinceList){
                            if(item.getProName().equals(mShipAddress.getProvinceName())){
                                mPresenter.getAllCity(item.getProSort());
                                return;
                            }
                        }
                    }


                    for (City item : cityList) {
                        DbTag dbTag = new DbTag();
                        if(item.getCityName().equals(mShipAddress.getCityName())){
                            dbTag.setSelected(true);
                        }else {
                            dbTag.setSelected(false);
                        }
                        dbTag.setTarget_id(item.getCitySort());
                        dbTag.setTag_name(item.getCityName());
                        dbTag.setBelong(CITY_TAG);
                        strlist.add(dbTag);
                    }
                }else if(position==2){
                    strlist.clear();

                    if(isEdit){//编辑模式点击请求数据
                      //  mPresenter.getZoneByCity(item.getTarget_id());
                        //需要知道id todo
                        Logger.e(cityList.toString());
                        if(isEdit){//编辑模式点击请求数据
                            for(Province item : provinceList){
                                if(item.getProName().equals(mShipAddress.getProvinceName())){
                                    isNeedRequireThird=true;
                                    mPresenter.getAllCity(item.getProSort());
                                    return;
                                }
                            }
                        }
                        //如果没有点第二个tab 直接点第三个tab


                    }


                    for (Zone item : zoneList) {
                        DbTag dbTag = new DbTag();
                        if(item.getZoneName().equals(mShipAddress.getCountyName())){
                            dbTag.setSelected(true);
                        }else {
                            dbTag.setSelected(false);
                        }
                        dbTag.setTarget_id(item.getZoneID());
                        dbTag.setTag_name(item.getZoneName());
                        dbTag.setBelong(ZONE_TAG);
                        strlist.add(dbTag);
                    }
                }
                Logger.e(strlist.toString());
                addressAdapter.setmInfos(strlist);
                addressAdapter.notifyDataSetChanged();

            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        RecyclerView rv = dialog.findViewById(R.id.rv_address);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(addressAdapter);
        mPresenter.getAllProvince();


        entities = new ArrayList<>();
        entities.add(new CustomTabEntity() {
            @Override
            public String getTabTitle() {
                return PROVINCE_TAG;
            }

            @Override
            public int getTabSelectedIcon() {
                return 0;
            }

            @Override
            public int getTabUnselectedIcon() {
                return 0;
            }
        });
        entities.add(new CustomTabEntity() {
            @Override
            public String getTabTitle() {
                return CITY_TAG;
            }

            @Override
            public int getTabSelectedIcon() {
                return 0;
            }

            @Override
            public int getTabUnselectedIcon() {
                return 0;
            }
        });
        entities.add(new CustomTabEntity() {
            @Override
            public String getTabTitle() {
                return ZONE_TAG;
            }

            @Override
            public int getTabSelectedIcon() {
                return 0;
            }

            @Override
            public int getTabUnselectedIcon() {
                return 0;
            }
        });

        tabLayout.setTabData(entities);

        tabLayout.getTitleView(1).setVisibility(View.GONE);
        tabLayout.getTitleView(2).setVisibility(View.GONE);

        if(!TextUtils.isEmpty(mShipAddress.getProvinceName())){
            tabLayout.getTitleView(0).setText(mShipAddress.getProvinceName());
        }

        if(!TextUtils.isEmpty(mShipAddress.getCityName())){
            tabLayout.getTitleView(1).setVisibility(View.VISIBLE);
            tabLayout.getTitleView(1).setText(mShipAddress.getCityName());
        }

        if(!TextUtils.isEmpty(mShipAddress.getCountyName())){
            tabLayout.getTitleView(2).setVisibility(View.VISIBLE);
            tabLayout.getTitleView(2).setText(mShipAddress.getCountyName());
        }




        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 居中位置
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
//        window.setWindowAnimations(R.style.AnimBottom);  //添加动画
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void saveShipAddressSuccess(Long addressId) {
        Log.i("jan", "saveShipAddressSuccess = mShipAddress = " + mShipAddress);
        LoadingDialog.getInstance(this).hide();
        if (mFromBought) {
            DefaultShippingAddress
                    address = new DefaultShippingAddress();
            address.setData(address.new Data());
            DefaultShippingAddress.Data data = address.getData();
            data.setUserName(mShipAddress.getUserName());
            data.setPhone(mShipAddress.getPhone());
            data.setProvinceName(mShipAddress.getProvinceName());
            data.setCityName(mShipAddress.getCityName());
            data.setCountyName(mShipAddress.getCountyName());
            data.setDetailInfo(mShipAddress.getDetailInfo());


            Log.i("jan", "address = " + address);

            ARouter.getInstance()
                    .build(ARouterPaths.COMMIT_ORDER)
                    .withSerializable("self_run_goods_info", mData)
                    .withSerializable("default_shipping_address", address)
                    .withInt("quantity", mQuantity)
                    .withLong("sku_id", mSkuId)
                    .navigation(this);
        }

        ArmsUtils.makeText(this,"保存收货地址成功");
        finish();
        //发送一个eventbus消息通知获取收货地址的界面
        EventBus.getDefault().post(new MessageEventGlobal(Const.SAVE_SHIP_ADDRESS_SUCCESS));

    }

    @Override
    public void saveShipAddressFail() {
        ArmsUtils.makeText(this,"保存收货地址失败");
    }

    @Override
    public void getShipAddressEmpty() {}

    @Override
    public void getShipAddressSuccess(List<AddressBean.DataBean> list) {}

    @Override
    public void getAllCitySuccess(List<City> cities) {
        cityList.clear();
        cityList.addAll(cities);
//        Logger.e(cities.toString());
        strlist.clear();
        for (City item : cities) {
            DbTag dbTag = new DbTag();
            //可能加上判断
            if(item.getCityName().equals(mShipAddress.getCityName())){
                dbTag.setSelected(true);
            }else {
                dbTag.setSelected(false);
            }
            dbTag.setTarget_id(item.getCitySort());
            dbTag.setTag_name(item.getCityName());
            dbTag.setBelong(CITY_TAG);
            strlist.add(dbTag);
        }
        if(isNeedRequireThird){
            for(City item : cityList){
                if(item.getCityName().equals(mShipAddress.getCityName())){
                    mPresenter.getZoneByCity(item.getCitySort());
                    isNeedRequireThird=false;
                    return;
                }
            }
        }
        addressAdapter.setmInfos(strlist);
        addressAdapter.notifyDataSetChanged();
    }

    @Override
    public void getAllDbFail() {
        Logger.e("出錯了");
    }

    @Override
    public void getAllZoneSuccess(List<Zone> zones) {
        zoneList.clear();
        zoneList.addAll(zones);
        strlist.clear();
        for (Zone item : zones) {
            DbTag dbTag = new DbTag();
            if(item.getZoneName().equals(mShipAddress.getCountyName())){
                dbTag.setSelected(true);
            }else {
                dbTag.setSelected(false);
            }
            dbTag.setTarget_id(item.getZoneID());
            dbTag.setTag_name(item.getZoneName());
            dbTag.setBelong(ZONE_TAG);

            strlist.add(dbTag);
        }
        addressAdapter.setmInfos(strlist);
        addressAdapter.notifyDataSetChanged();
    }

    @Override
    public void getAllProviceSuccess(List<Province> provinces) {
        provinceList.clear();
        provinceList.addAll(provinces);

        strlist.clear();
        for (Province item : provinces) {
            DbTag dbTag = new DbTag();
            if(item.getProName().equals(mShipAddress.getProvinceName())){
                dbTag.setSelected(true);
            }else {
                dbTag.setSelected(false);
            }
            dbTag.setTarget_id(item.getProSort());
            dbTag.setTag_name(item.getProName());
            dbTag.setBelong(PROVINCE_TAG);


            if(TextUtils.equals(dbTag.getTag_name(),"台湾省")||TextUtils.equals(dbTag.getTag_name(),"澳门特别行政区")||TextUtils.equals(dbTag.getTag_name(),"香港特别行政区")){

            }else {
                strlist.add(dbTag);
            }



        }
        addressAdapter.setmInfos(strlist);
        addressAdapter.notifyDataSetChanged();
        addressAdapter.setOnItemClickListener((view, viewType, data, position) -> {
            DbTag item = (DbTag) data;
            switch (item.getBelong()) {
                case PROVINCE_TAG:
                        tabLayout.getTitleView(1).setVisibility(View.VISIBLE);
                        mShipAddress.setProvinceName(item.getTag_name());
                        tabLayout.setCurrentTab(1);
                        tabLayout.getTitleView(0).setText(item.getTag_name());
                        mPresenter.getAllCity(item.getTarget_id());
                    break;
                case CITY_TAG:
                    tabLayout.setCurrentTab(2);
                    tabLayout.getTitleView(2).setVisibility(View.VISIBLE);
                    mShipAddress.setCityName(item.getTag_name());
                    mPresenter.getZoneByCity(item.getTarget_id());//
                    tabLayout.getTitleView(1).setText(item.getTag_name());
                    break;
                case ZONE_TAG:
                    tabLayout.getTitleView(2).setText(item.getTag_name());
                    mShipAddress.setCountyName(item.getTag_name());
                    dialog.cancel();
                    tv_location_info.setText(mShipAddress.getProvinceName()+ mShipAddress.getCityName() + mShipAddress.getCountyName());
                    break;
            }

        });
    }

    @Override
    public void deleteAllShipAddress() {

    }

    @Override
    public void setAllNotDefaultSuccess(Boolean flag) {

    }

    @Override
    public void setDefaultConsition(Boolean flag) {
        if(!flag){
            ArmsUtils.makeText(this,"设置默认地址失败");
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if(disposable != null){
            disposable.dispose();
        }
        super.onStop();
    }
}
