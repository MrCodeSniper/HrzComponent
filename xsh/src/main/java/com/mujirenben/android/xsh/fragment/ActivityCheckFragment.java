package com.mujirenben.android.xsh.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bigkoo.pickerview.OptionsPickerView;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.adapter.NewShopListAdapter;
import com.mujirenben.android.xsh.constant.Constants;
import com.mujirenben.android.xsh.entity.CityEntity;
import com.mujirenben.android.xsh.entity.CmsIndustryPos;
import com.mujirenben.android.xsh.entity.IndustryEntity;
import com.mujirenben.android.xsh.entity.NewShopEntity;
import com.mujirenben.android.xsh.entity.PersonIndexTop;
import com.mujirenben.android.xsh.entity.ProvEntity;
import com.mujirenben.android.xsh.widget.xrecyclerview.XRecyclerView;
import com.mujirenben.android.common.base.BaseFragment;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.widget.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Cyj on 2018/8/2.
 */
public class ActivityCheckFragment extends BaseFragment implements View.OnClickListener {


    private View view;
    private static ActivityCheckFragment activityCheckFragment;
    private PersonIndexTop personIndexTop;
    private ImageView iv_imgicon;
    private LinearLayout ll_right;
    private TextView shop_area;
    private LinearLayout ll_shop_worker;
    private TextView shop_worker;
    private EditText tv_search;
    private TextView tv_title;
    private XRecyclerView xrecyclerview;
    private boolean isShop;
    //行业选择
    private ArrayList<IndustryEntity> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<CmsIndustryPos>> options2Items = new ArrayList<>();
    //省份选择
    private ArrayList<ProvEntity> provItems = new ArrayList<>();
    //城市選擇
    private ArrayList<CityEntity> cityItems = new ArrayList<>();
    private String industryid;

    private List<NewShopEntity> newShopEntityList = new ArrayList<>();
    private NewShopListAdapter shopListAdapter;
    private TextView tv_yongji;
    private String cityid;
    private RelativeLayout rl_yong;
    private String provinceStr = "";
    private String cityStr = "";

    private OptionsPickerView pvLocation;
    private OptionsPickerView pvCity;
    private OptionsPickerView pvIndustry;


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    private void initFragmentView(View view){
        ll_right = (LinearLayout) view.findViewById(R.id.ll_right);
        LinearLayout ll_shop = (LinearLayout) view.findViewById(R.id.ll_shop);
        ll_shop.setOnClickListener(this);
        shop_area = (TextView) view.findViewById(R.id.shop_area);
        ll_shop_worker = (LinearLayout) view.findViewById(R.id.ll_shop_worker);
        ll_shop_worker.setOnClickListener(this);
        shop_worker = (TextView) view.findViewById(R.id.shop_worker);
        tv_search = (EditText) view.findViewById(R.id.tv_search);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        xrecyclerview = (XRecyclerView) view.findViewById(R.id.xrecyclerview);
        xrecyclerview = (XRecyclerView) view.findViewById(R.id.xrecyclerview);
    }


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hrz_fragment_activitycheck, container, false);

        initFragmentView(view);

        tv_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    newShopEntityList.clear();
                    shopListAdapter.notifyDataSetChanged();
                    if (tv_search.getText().toString().equals("")) {
                        ArmsUtils.makeText(getActivity(),"请输入商家名称");
                    } else {
                        SearchData();
                    }
                    return true;
                }
                return false;
            }
        });

        xrecyclerview.setPullRefreshEnabled(false);
        xrecyclerview.setLoadingMoreEnabled(false);
        LinearLayoutManager linearLayoutManager_VIKEW = new LinearLayoutManager(getActivity());
        linearLayoutManager_VIKEW.setOrientation(1);
        xrecyclerview.setLayoutManager(linearLayoutManager_VIKEW);
        return view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initData();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    public static ActivityCheckFragment getInstance() {
        if (activityCheckFragment == null) {
            activityCheckFragment = new ActivityCheckFragment();
        }
        return activityCheckFragment;
    }


    private void initData() {
        newShopEntityList.clear();

        shop_area.setText("");
        shop_worker.setText("");
        tv_search.setText("");
        shopListAdapter = new NewShopListAdapter(getActivity(), newShopEntityList);
        xrecyclerview.setAdapter(shopListAdapter);
    }


    private void SearchData() {
        newShopEntityList.clear();
        RequestParams request = new RequestParams("http://xdz.hdyl.net.cn/merchant/getByName.htm?storeName=" + tv_search.getText());
        x.http().get(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    int status = json.getInt("code");
                    String reason = json.getString("msg");
                    Log.i(Constants.TAG, "SearchData\t" + json.toString());
                    if (status == 10000) {
                        org.json.JSONArray jsonArray = json.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String storeName = jsonObject.getString("storeName");
                            String address = jsonObject.getString("address");


                            NewShopEntity newShopEntity = new NewShopEntity(address, storeName);
                            newShopEntityList.add(newShopEntity);
                        }
                        if (newShopEntityList.size() != 0) {

                            tv_title.setText("该区域已入驻商家");
                        } else {
                            tv_title.setText("该区域未入驻商家");
                        }
                        tv_title.setVisibility(View.VISIBLE);
                        shopListAdapter.refreshAdapter(newShopEntityList);
                    } else {
                        ArmsUtils.makeText(getActivity(),reason+"");
                    }
                    LoadingDialog.getInstance(getActivity()).hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    private void getProvince() {
        RequestParams request = new RequestParams("http://xdz.hdyl.net.cn/city/provinces.htm?");

        x.http().get(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject json = null;
                try {
                    json = new JSONObject(result.toString());
                    int status = json.getInt("code");
                    String reason = json.getString("msg");
                    org.json.JSONArray data = json.getJSONArray("data");
                    Log.i(Constants.TAG, "json\t" + json);
                    if (status == 10000) {
                        List<ProvEntity> industryEntityList = JSON.parseObject(data.toString(), new TypeReference<List<ProvEntity>>() {
                        });
                        provItems.clear();

                        Log.i(Constants.TAG, "industryEntityList" + industryEntityList.toString());
                        for (int i = 0; i < industryEntityList.size(); i++) {
                            provItems.add(industryEntityList.get(i));
                        }


                        setLocationPicker();
                       


                    } else {
                        ArmsUtils.makeText(getActivity(),reason+"");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    
    private void setLocationPicker(){
        if(pvLocation==null){
            pvLocation = new OptionsPickerView.Builder(getActivity(), new LocationPickerCallback()).build();
        }

        pvLocation.setPicker(provItems);

        if(!pvLocation.isShowing()){
            pvLocation.show();
        }
    }


    private void setCityPicker(){
        if(pvCity==null){
            pvCity = new OptionsPickerView.Builder(getActivity(), new CityPickerCallback()).build();
        }

        pvCity.setPicker(cityItems);
        if(!pvCity.isShowing()){
            pvCity.show();
        }
    }
    


    private void getCity(String provincesId) {
        RequestParams request = new RequestParams("http://xdz.hdyl.net.cn/city/citites.htm?provincesId=" + provincesId);
        x.http().get(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                JSONObject json = null;
                try {
                    json = new JSONObject(result);
                    int status = json.getInt("code");
                    String reason = json.getString("msg");
                    org.json.JSONArray data = json.getJSONArray("data");

                    if (status == 10000) {
                        List<CityEntity> industryEntityList = JSON.parseObject(data.toString(), new TypeReference<List<CityEntity>>() {
                        });
                        cityItems.clear();

                        Log.i(Constants.TAG, "industryEntityList" + industryEntityList.toString());
                        for (int i = 0; i < industryEntityList.size(); i++) {
                            cityItems.add(industryEntityList.get(i));
                        }
                        

                        setCityPicker();
                        
                    } else {
                        ArmsUtils.makeText(getActivity(),reason+"");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }


        });

    }


    private void getindustry() {
        RequestParams request = new RequestParams("http://xdz.hdyl.net.cn/industry.htm?");

        x.http().get(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject json = null;
                try {
                    json = new JSONObject(result);
                    int status = json.getInt("code");
                    String reason = json.getString("msg");
                    org.json.JSONArray data = json.getJSONArray("data");
                    if (status == 10000) {
                        List<IndustryEntity> industryEntityList = JSON.parseObject(data.toString(), new TypeReference<List<IndustryEntity>>() {
                        });
                        options1Items.clear();
                        options2Items.clear();

                        Log.i(Constants.TAG, "industryEntityList" + industryEntityList.toString());
                        for (int i = 0; i < industryEntityList.size(); i++) {

                            options1Items.add(industryEntityList.get(i));

                            ArrayList<CmsIndustryPos> cmsIndustryPosArrayList = new ArrayList<>();
                            cmsIndustryPosArrayList.addAll(industryEntityList.get(i).getCmsIndustryPos());
                            options2Items.add(cmsIndustryPosArrayList);

                        }

                       setInstryPicker();



                    } else {
                        ArmsUtils.makeText(getActivity(),reason+"");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void setInstryPicker() {
        if(pvIndustry==null){
            pvIndustry = new OptionsPickerView.Builder(getActivity(),new CheckOptionCallback()).build();
        }

        pvIndustry.setPicker(options1Items, options2Items);
        if(!pvIndustry.isShowing()){
            pvIndustry.show();
        }
    }

    private void getShopdata() {
        newShopEntityList.clear();
        shopListAdapter.notifyDataSetChanged();
        RequestParams request = new RequestParams( "http://xdz.hdyl.net.cn/merchant/findByTradeAndArea.htm?tradeid=" + industryid + "&province=" + provinceStr + "&city=" + cityStr );

        x.http().get(request, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject json = new JSONObject(result);
                    int status = json.getInt("code");
                    String reason = json.getString("msg");

                    Log.i(Constants.TAG, "getShopdata\t" + json.toString());
                    if (status == 10000) {
                        org.json.JSONArray jsonArray = json.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String storeName = jsonObject.getString("storeName");
                            String address = jsonObject.getString("address");


                            NewShopEntity newShopEntity = new NewShopEntity(address, storeName);
                            newShopEntityList.add(newShopEntity);
                        }
                        if (newShopEntityList.size() != 0) {
                           
                            tv_title.setText("该区域已入驻商家");
                        } else {
                            tv_title.setText("该区域未入驻商家");
                        }
                        tv_title.setVisibility(View.VISIBLE);
                        shopListAdapter.refreshAdapter(newShopEntityList);

                    } else {
                        ArmsUtils.makeText(getActivity(),reason+"");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_shop_worker) {
//            isShop = true;
            if (shop_area.getText().toString().equals("")) {
                showToast(getActivity(),"请先选择地区");
            } else {
                getindustry();
            }
        } else if (v.getId() == R.id.ll_shop) {
            getProvince();
        }
    }



    private static Toast mToast;

    /**
     * 解决Toast重复弹出 长时间不消失的问题
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message){
        if (mToast==null) {
            mToast = new Toast(context.getApplicationContext());
            View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.common_toast_layout,null);
            TextView msgTv = view.findViewById(R.id.common_toast_msg_tv);
            msgTv.setText(message+"");
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER,0,0);
            mToast.setView(view);
            mToast.show();
        }else {
            TextView msgTv = mToast.getView().findViewById(R.id.common_toast_msg_tv);
            msgTv.setText(message+"");
            mToast.show();
        }
    }


    class LocationPickerCallback implements OptionsPickerView.OnOptionsSelectListener {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            //返回的分别是三个级别的选中位置
            String tx = cityItems.get(options1).getPickerViewText();
            shop_area.append(tx + "");
            cityStr = tx;
            cityid = cityItems.get(options1).getCityid();
        }
    }


    class CityPickerCallback implements OptionsPickerView.OnOptionsSelectListener {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            //返回的分别是三个级别的选中位置
            String tx = cityItems.get(options1).getPickerViewText();
            shop_area.append(tx + "");
            cityStr = tx;
            cityid = cityItems.get(options1).getCityid();
        }
    }


    class CheckOptionCallback implements OptionsPickerView.OnOptionsSelectListener {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            //返回的分别是三个级别的选中位置
            String tx = options1Items.get(options1).getPickerViewText()
                    + options2Items.get(options1).get(options2).getName();
            shop_worker.setText(tx + "");
            industryid = options2Items.get(options1).get(options2).getId();
            getShopdata();
        }
    }

}
