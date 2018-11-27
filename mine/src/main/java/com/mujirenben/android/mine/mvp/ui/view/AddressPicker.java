package com.mujirenben.android.mine.mvp.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


import com.mujirenben.android.common.base.adapter.DefaultAdapter;
import com.mujirenben.android.common.base.greendao.City;
import com.mujirenben.android.common.base.greendao.Province;
import com.mujirenben.android.common.base.greendao.ShipAddress;
import com.mujirenben.android.common.base.greendao.Zone;
import com.mujirenben.android.common.datapackage.bean.DbTag;
import com.mujirenben.android.common.widget.tablayout.CommonTabLayout;
import com.mujirenben.android.common.widget.tablayout.listener.CustomTabEntity;
import com.mujirenben.android.common.widget.tablayout.listener.OnTabSelectListener;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;
import com.mujirenben.android.mine.mvp.ui.adapter.AddressAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chenhong.
 */

public class AddressPicker extends Dialog{

    private List<Province> provinceList=new ArrayList<>();
    private List<City> cityList=new ArrayList<>();
    private List<Zone> zoneList=new ArrayList<>();
    private  ArrayList<CustomTabEntity> entities;

    private static final String PROVINCE_TAG = "省级城市";
    private static final String CITY_TAG = "市级城市";
    private static final String ZONE_TAG = "县/区级城市";

    private ShipAddress mShipAddress;//为空
    private PickerState mState;

    private DbTag tag;//视图内容的item entity
    private List<DbTag> tags;//视图内容的list
    private AddressAdapter mAddressAdapter;//视图内容的adapter

    private String titleInfo;
    private Context mContext;
    private int addressLevel=3;
    private int selectTabPosition=0;


    public AddressPicker(@NonNull Context context,int level) {
        super(context);
        this.mContext=context;
        addressLevel=level;
        mState=new PickerState();
        mState.setId(1);
        mState.setType(EnumType.SAVE);
        mShipAddress=new ShipAddress();
    }

    public AddressPicker(@NonNull Context context,int level,ShipAddress existAddress) {
        super(context);
        this.mContext=context;
        addressLevel=level;
        mShipAddress=existAddress;
        mState=new PickerState();
        mState.setId(0);
        mState.setType(EnumType.EDIT);
    }

    public AddressPicker(@NonNull Context context, int themeResId,int level) {
        super(context, themeResId);
        this.mContext=context;
        addressLevel=level;
    }

    protected AddressPicker(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener,int level) {
        super(context, cancelable, cancelListener);
        this.mContext=context;
        addressLevel=level;
    }


    private ImageView iv_close;
    private CommonTabLayout tabLayout;
    private TextView tvAboveTitle;
    private RecyclerView rv_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mine_dialog_select_address_view);
        setCanceledOnTouchOutside(false);//按空白处不能取消动画
        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    public void setTitleInfo(String titleInfo) {
        this.titleInfo = titleInfo;
    }

    private void initData() {
        DbTag dbTag = new DbTag();
        dbTag.setTag_name("广东省");
        dbTag.setTarget_id(19L);
        mAddressAdapter = new AddressAdapter(Arrays.asList(dbTag));
        rv_address.setAdapter(mAddressAdapter);

        ///请求数据库数据 不应该将请求数据的方法写在dialog中 向外界获取


    }

    private void initTab(){
        entities=new ArrayList<>();

        if(addressLevel>=1){
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
        }

        if(addressLevel>=2){
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
        }


        if(addressLevel>=3){
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
        }



        tabLayout.setTabData(entities);
        for(int i=0;i<addressLevel;i++){
            tabLayout.getTitleView(i).setVisibility(View.GONE);
        }
        tabLayout.getTitleView(0).setVisibility(View.VISIBLE);


        if(mState.type==EnumType.EDIT){

            if(mShipAddress.getAreaId1()!=null&&!mShipAddress.getAreaId1().equals("")){
                tabLayout.getTitleView(0).setVisibility(View.VISIBLE);
                tabLayout.getTitleView(0).setText(mShipAddress.getAreaId1());
            }


            if(mShipAddress.getAreaId2()!=null&&!mShipAddress.getAreaId2().equals("")){
                tabLayout.getTitleView(1).setVisibility(View.VISIBLE);
                tabLayout.getTitleView(1).setText(mShipAddress.getAreaId2());
            }

            if(mShipAddress.getAreaId3()!=null&&!mShipAddress.getAreaId3().equals("")){
                tabLayout.getTitleView(2).setVisibility(View.VISIBLE);
                tabLayout.getTitleView(2).setText(mShipAddress.getAreaId3());
            }
        }

    }


    private void initView() {
         iv_close = this.findViewById(R.id.iv_closedialog);
         //利用第三方的tab
         tabLayout = this.findViewById(R.id.stl_select_address);
         tvAboveTitle=this.findViewById(R.id.tv_above_title);
         tvAboveTitle.setText(titleInfo==null?"地址选择":titleInfo);
         rv_address=this.findViewById(R.id.rv_address);
         rv_address.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
         initTab();
    }

    private void initEvent() {

        iv_close.setOnClickListener((View v) -> this.cancel());
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                selectTabPosition=position;

                if(position==0){
                    Logger.e(selectTabPosition+"xx"+provinceList.toString());
                    mAddressAdapter.setmInfos(transformProvinceToTag(provinceList));
                }else if(position==1){
                    //

                    for(Province province:provinceList){
                        if(TextUtils.equals(province.getProName(),mShipAddress.getAreaId1())){
                            onContentItemClickListener.onTabCitySelect(province.getProSort());
                        }
                    }

                    mAddressAdapter.setmInfos(transformCityToTag(cityList));
                }
                mAddressAdapter.notifyDataSetChanged();
            }


            @Override
            public void onTabReselect(int position) {

            }
        });

        mAddressAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                //设置
                DbTag tag=(DbTag) data;
                //tag.setSelected(true);
                Logger.e(selectTabPosition+"ffsafa");
                if(selectTabPosition==0){
                    selectTabPosition=1;
                    mShipAddress.setAreaId1(tag.getTag_name());
                    tabLayout.getTitleView(0).setText(mShipAddress.getAreaId1());
                    tabLayout.getTitleView(1).setVisibility(View.VISIBLE);
                    //显示市级地区 由暴露给用户的接口提供
                    onContentItemClickListener.onLevelOneClick(tag.getTarget_id());
                    tabLayout.setCurrentTab(1);

                }else if(selectTabPosition==1){
                    selectTabPosition=2;
                    mShipAddress.setAreaId2(tag.getTag_name());
                    if(addressLevel>=3) tabLayout.getTitleView(2).setVisibility(View.VISIBLE);
                    onContentItemClickListener.onLevelTwoClick(tag.getTarget_id());

                }else if(selectTabPosition==2){
                    mShipAddress.setAreaId3(tag.getTag_name());
                }

            }


        });

    }





    private List<DbTag> transformProvinceToTag(List<Province> items){
        List<DbTag> tags=new ArrayList<>();
        for (Province item : items) {


            DbTag dbTag = new DbTag();
            //可能加上判断
            if(mShipAddress!=null&&mShipAddress.getAreaId1()!=null){
                if(item.getProName().equals(mShipAddress.getAreaId1())){
                    dbTag.setSelected(true);
                }else {
                    dbTag.setSelected(false);
                }
            }

            dbTag.setTarget_id(item.getProSort());
            dbTag.setTag_name(item.getProName());
            dbTag.setBelong(PROVINCE_TAG);
            tags.add(dbTag);
        }

        return tags;
    }

    private List<DbTag> transformCityToTag(List<City> items){
        List<DbTag> tags=new ArrayList<>();
        for (City item : items) {


            DbTag dbTag = new DbTag();
            //可能加上判断
            if(mShipAddress!=null&&mShipAddress.getAreaId2()!=null){
                if(item.getCityName().equals(mShipAddress.getAreaId2())){
                    dbTag.setSelected(true);
                }else {
                    dbTag.setSelected(false);
                }
            }

            dbTag.setTarget_id(item.getCitySort());
            dbTag.setTag_name(item.getCityName());
            dbTag.setBelong(CITY_TAG);
            tags.add(dbTag);
        }

        return tags;
    }



    public void setProvinceTags(List<Province> tags,boolean refreshView) {
        this.provinceList=tags;
        if(!refreshView){
            return;
        }
        mAddressAdapter.setmInfos(transformProvinceToTag(tags));
        mAddressAdapter.notifyDataSetChanged();
    }



    public void setCityTags(List<City> tags,boolean refreshView) {
        this.cityList=tags;
        if(!refreshView){
            return;
        }
        mAddressAdapter.setmInfos(transformCityToTag(tags));
        mAddressAdapter.notifyDataSetChanged();
    }

    public void setZoneTags(List<Zone> tags) {
        this.zoneList=tags;
    }

    private void showContent(){



    }


    public ShipAddress getmShipAddress() {
        return mShipAddress;
    }

    public enum EnumType {
        EDIT(1),SAVE(2);

         int value;

        EnumType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }



    public static class PickerState{

        private int id;
        private EnumType type;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public EnumType getType() {
            return type;
        }

        public void setType(EnumType type) {
            this.type = type;
        }
    }


    public interface OnContentItemClickListener{
        public void onLevelOneClick(Long ProId);
        public void onLevelTwoClick(Long cityId);
        public void onLevelThreeClick();
        public void onTabCitySelect(Long proId);
    }

   private OnContentItemClickListener onContentItemClickListener;

    public void setOnContentItemClickListener(OnContentItemClickListener onContentItemClickListener) {
        this.onContentItemClickListener = onContentItemClickListener;
    }
}
