package com.mujirenben.android.mine.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
import com.mujirenben.android.common.datapackage.bean.MessageEventGlobal;
import com.mujirenben.android.common.entity.AddressBean;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.NetWorkUtils;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.mine.di.component.DaggerUserInfoComponent;
import com.mujirenben.android.mine.di.module.UserInfoModule;
import com.mujirenben.android.mine.mvp.contract.UserInfoContract;
import com.mujirenben.android.mine.mvp.presenter.UserInfoPresenter;
import com.mujirenben.android.mine.mvp.ui.adapter.UserAddressAdapter;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.RequestBody;

import static com.mujirenben.android.common.datapackage.bean.Const.CHOOSE_SHIP_ADDRESS_RESULT_CODE;

/**
 * Created by mac on 2018/5/31.
 */

@Route(path = ARouterPaths.ADDRESS_INFOS)
public class ShipAddressActivity extends BaseActivity<UserInfoPresenter> implements UserInfoContract.View  {
    @BindView(R2.id.tv_back)
    ImageView tvBack;
    @BindView(R2.id.tv_titlebar)
    TextView tvTitlebar;
    @BindView(R2.id.rl_address_info)
    RecyclerView rlAddressInfo;
    @BindView(R2.id.tv_add_address)
    TextView tvAddAddress;
    private UserAddressAdapter addressAdapter;
    private List<AddressBean.DataBean> shipAddresses;
    private static final String DELETE_DEFAULT="DELETE_DEFAULT";
    private static final String SET_DEFAULT="SET_DEFAULT";
    private String tag;
    private boolean isChooseAddress=false;




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
        return R.layout.mine_activity_address_info;
    }
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if(getIntent()!=null){
            isChooseAddress=getIntent().getBooleanExtra(Const.CHOOSE_ADDRESS_STATE,false);
        }
        shipAddresses=new ArrayList<>();
        tvBack.setOnClickListener((View v)->finish());
        tvTitlebar.setText("收货地址");
        tvAddAddress.setOnClickListener((View v)-> ARouter.getInstance().build(ARouterPaths.USER_INFOS).navigation(this));
        rlAddressInfo.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        addressAdapter=new UserAddressAdapter(R.layout.mine_user_addressinfo_holder);
        addressAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if(view.getId()==R.id.iv_default_address_select){
                tag=SET_DEFAULT;
                AddressBean.DataBean addressBean=addressAdapter.getData().get(position);
                if(!view.isSelected()){
                    setDefaultAddress(addressBean.getId()+"");
                }

            }else if(view.getId()==R.id.tv_delete_user_ship){
                tag=DELETE_DEFAULT;
                AddressBean.DataBean addressBean=addressAdapter.getData().get(position);
                deleteAddressById(addressBean.getId()+"");
            }else if (view.getId()==R.id.ll_ship_above||view.getId()==R.id.rl_click_area_one||view.getId()==R.id.tv_ship_address_detail){

                Logger.e(isChooseAddress+"**");
                if(isChooseAddress){
                    Logger.e("xxyyyyy");
                    AddressBean.DataBean addressBean=addressAdapter.getData().get(position);
                    Intent intent=new Intent();
                    intent.putExtra(Const.CHOOSE_SHIP_ADDRESS,addressBean);
                    setResult(CHOOSE_SHIP_ADDRESS_RESULT_CODE,intent);
                    finish();
                }


            }


        });
        rlAddressInfo.setAdapter(addressAdapter);
        if(NetWorkUtils.checkNetWork(this)){
            getUserAddressInfo();
        }
    }

    private void deleteAddressById(String id){
        HashMap<String,String> map= new HashMap<String, String>();
        map.put("accountSecToken", LoginDataManager.getsInstance(this).getAccountSecToken());
        map.put("id",id);
        HashMap<String,String> signed_map= HttpParamUtil.getCommonSignParamMap(this,map);
        RequestBody requestBody =RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(signed_map));
        RetrofitUrlManager.getInstance().putDomain("user_address", Consts.PROTOCROL+Consts.OFFICIAL_SERVER);
        mPresenter.deleteAddressById(requestBody);
    }

    private void setDefaultAddress(String id){
        HashMap<String,String> map= new HashMap<String, String>();
        map.put("accountSecToken", LoginDataManager.getsInstance(this).getAccountSecToken());
        map.put("flag","1");
        map.put("id",id);
        HashMap<String,String> signed_map= HttpParamUtil.getCommonSignParamMap(this,map);
        RequestBody requestBody =RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(signed_map));
        RetrofitUrlManager.getInstance().putDomain("user_address", Consts.PROTOCROL+Consts.OFFICIAL_SERVER);
        mPresenter.setDefaultAddress(requestBody);
    }


    private void getUserAddressInfo(){
        HashMap<String,String> map= new HashMap<String, String>();
        map.put("accountSecToken", LoginDataManager.getsInstance(this).getAccountSecToken());
        HashMap<String,String> signed_map= HttpParamUtil.getCommonSignParamMap(this,map);
        RequestBody requestBody =RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),new Gson().toJson(signed_map));
        RetrofitUrlManager.getInstance().putDomain("user_address", Consts.PROTOCROL+Consts.OFFICIAL_SERVER);
        try {
            mPresenter.getAddressByUserIdServer(requestBody);
        }catch (Exception e){
            Logger.e(e.getMessage());
        }
    }




    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void saveShipAddressSuccess(Long addressId) {

    }

    @Override
    public void saveShipAddressFail() {

    }

    @Override
    public void getShipAddressEmpty() {
        rlAddressInfo.setVisibility(View.GONE);
        Logger.e("收货地址为空");
    }



    @Override
    public void getShipAddressSuccess(List<AddressBean.DataBean> list) {
        Logger.e(list.toString());
        rlAddressInfo.setVisibility(View.VISIBLE);
        if(addressAdapter!=null){
            shipAddresses=list;
            addressAdapter.setNewData(shipAddresses);
        }

    }

    @Override
    public void getAllCitySuccess(List<City> cities) {

    }

    @Override
    public void getAllDbFail() {

    }

    @Override
    public void getAllZoneSuccess(List<Zone> zones) {

    }

    @Override
    public void getAllProviceSuccess(List<Province> provinces) {

    }

    @Override
    public void deleteAllShipAddress() {
        //一般只删除1个
        Logger.e("删除了1个");
        EventBus.getDefault().post(new MessageEventGlobal(Const.DELETE_SHIP_ADDRESS_SUCCESS));
    }

    @Override
    public void setAllNotDefaultSuccess(Boolean flag) {

    }

    @Override
    public void setDefaultConsition(Boolean flag) {
        Logger.e("setDefaultConsition");

        if(flag){
            Logger.e("设置为默认");
            if(tag==DELETE_DEFAULT){
                ArmsUtils.makeText(this,"删除地址成功");
            }else if(tag==SET_DEFAULT){
                ArmsUtils.makeText(this,"设置默认地址成功");
            }
            EventBus.getDefault().post(new MessageEventGlobal(Const.SAVE_SHIP_ADDRESS_SUCCESS));
        }else {
            if(tag==DELETE_DEFAULT){
                ArmsUtils.makeText(this,"删除地址失败");
            }else if(tag==SET_DEFAULT){
                ArmsUtils.makeText(this,"设置默认地址失败");
            }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEventGlobal messageEvent) {
        if(messageEvent.getMessage().equals(Const.SAVE_SHIP_ADDRESS_SUCCESS)){
            getUserAddressInfo();
        }else if(messageEvent.getMessage().equals(Const.DELETE_SHIP_ADDRESS_SUCCESS)){
            getUserAddressInfo();
        }
    }

    @Override
    public boolean useEventBus() {
        return true;
    }
}
