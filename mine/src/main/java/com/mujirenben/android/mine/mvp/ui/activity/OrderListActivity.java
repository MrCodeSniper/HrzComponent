package com.mujirenben.android.mine.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.base.adapter.loadmore.SimpleLoadMoreView;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.widget.DialogUtils;
import com.mujirenben.android.common.widget.dropdownmenu.DropDownMenu;
import com.mujirenben.android.common.widget.tablayout.CommonTabLayout;
import com.mujirenben.android.common.widget.tablayout.listener.CustomTabEntity;
import com.mujirenben.android.common.widget.tablayout.listener.OnTabSelectListener;
import com.mujirenben.android.mine.di.component.DaggerOrderComponent;
import com.mujirenben.android.mine.di.module.OrderModule;
import com.mujirenben.android.mine.mvp.contract.OrderContract;
import com.mujirenben.android.mine.mvp.model.bean.MineTabEntity;
import com.mujirenben.android.mine.mvp.model.bean.OrderBean;
import com.mujirenben.android.mine.mvp.model.bean.OrderDetail;
import com.mujirenben.android.mine.mvp.model.bean.OrderInfo;
import com.mujirenben.android.mine.mvp.presenter.OrderPresenter;
import com.mujirenben.android.mine.mvp.ui.adapter.OrderListAdapter;
import com.mujirenben.android.mine.mvp.ui.adapter.OrderPlatformSelectAdapter;
import com.mujirenben.android.mine.mvp.ui.constant.MineConstants;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;


@Route(path = ARouterPaths.ORDER_LIST)
public class OrderListActivity extends BaseActivity<OrderPresenter> implements OrderContract.View, View.OnClickListener,
        OrderListAdapter.OnItemClickListener, OnTabSelectListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R2.id.tv_back)
    ImageView backBtn;
    @BindView(R2.id.tv_titlebar)
    TextView titleView;
    @BindView(R2.id.mine_order_list_status_platforms_layout)
    View platformView;
    @BindView(R2.id.mine_order_list_status_activity_layout)
    View activityLayout;
    @BindView(R2.id.mine_order_list_recycler_view)
    RecyclerView orderListView;
    @BindView(R2.id.mine_order_tabLayout)
    CommonTabLayout mineOrderTabLayout;
    @BindView(R2.id.mine_order_tabLayout_plantform)
    CommonTabLayout mine_order_tabLayout_plantform;
    @BindView(R2.id.mine_order_list_platforms_arrow)
    ImageView mine_order_list_platforms_arrow;
    @BindView(R2.id.mine_order_list_platforms_tv)
    TextView mine_order_list_platforms_tv;
    @BindView(R2.id.vs_nodata_view)
    ViewStub viewStub;
    @BindView(R2.id.default_view_ll)
    LinearLayout defaultViewLl;
    @BindView(R2.id.default_icon_iv)
    ImageView defaultIconIv;
    @BindView(R2.id.default_title_tv)
    TextView defaultTitleTv;
    @BindView(R2.id.default_operate_tv)
    TextView defaultOperateTv;
    @BindView(R2.id.rv_pop_list)
    RecyclerView rvOrderPlatform;
    @BindView(R2.id.iv_turn_down)
    ImageView ivTurnDown;
    @BindView(R2.id.mine_order_dropDownMenu)
    DropDownMenu dropDownMenu;
    @BindView(R2.id.tv_all_category)
    TextView tvAllCategory;

    private String headers[] = {"platform"};
    //订单列表适配器
    private OrderListAdapter orderListAdapter;
    //下拉筛选适配器
    private RecycleItemAdapter recycleItemAdapter;
    private OrderInfo orderInfo;
    //是否加载更多
    private boolean isLoadMoreState = false;
    private int firstSelectedTag = 0;
    //是否动画正在加载
    private boolean isAnimationLoading = false;
    //选择的请求订单平台
    private String selectPlantform;
    //加载显示dialog
    private DialogUtils toastUtil;
    //请求订单的状态
    private int request_order_status = MineConstants.ALL_ORDERS;
    //目前加载的页数
    private int nowPage = 1;
    //标签列表
    private List<String> selectTags;
    //一页加载的数量
    public static final int pageCount = 10;
    //是否已经弹出了平台框
    private boolean isPopRecycleView = false;
    //选中的订单类型 all,success,jiesuan,fail
    private OrderType selectedOrderType = OrderType.ALL;
    //内置选择订单平台
    private String[] tabTexts;
    private String[] tabPlantforms;
    private int selectedPlantform;

    //订单平台类型TAB
    private ArrayList<CustomTabEntity> tabTextList = new ArrayList<>();
    private ArrayList<CustomTabEntity> tabPlantformsList = new ArrayList<>();
    //显示的订单列表
    List<OrderBean.DataBean> parentOrderList;
    //订单活动
    public static final int NO_ACTIVITY = -1;
    public static final int ONE_YUAN_ACTIVITY = 2;
    public static final int FILL_ACCOUNT = 3;

    private OrderPlatformSelectAdapter platformSelectAdapter;


    @Override
    public void onTabSelect(int position) {
        if (position == 0) {
            request_order_status = MineConstants.ALL_ORDERS;
            nowPage = 1;
            requestOrderData(nowPage, pageCount, request_order_status, false, selectedPlantform);
        } else if (position == 1) {
            request_order_status = MineConstants.ORDER_SUCCESS;
            nowPage = 1;
            requestOrderData(nowPage, pageCount, request_order_status, false, selectedPlantform);
        } else if (position == 2) {
            request_order_status = MineConstants.ORDER_JIESUAN;
            nowPage = 1;
            requestOrderData(nowPage, pageCount, request_order_status, false, selectedPlantform);
        } else if (position == 3) {
            request_order_status = MineConstants.ORDER_FAIL;
            nowPage = 1;
            requestOrderData(nowPage, pageCount, request_order_status, false, selectedPlantform);
        }
    }

    @Override
    public void onTabReselect(int position) {}

    /*
       1.自营商品跳转自营详情
       2.其他平台跳转订单详情
     */
    @Override
    public void onItemClick(OrderBean.DataBean orderItem) {
        if (selectedPlantform == Const.Platform.HRZ) {
            ARouter.getInstance().build(ARouterPaths.SELF_RUN_ORDER_DETAILS)
                    .withString("orderId", orderItem.getParentOrderId() + "")
                    .navigation(this);
        } else {
            ARouter.getInstance().build(ARouterPaths.ORDER_DETAILS)
                    .withString("orderId", orderItem.getParentOrderId() + "")
                    .navigation(this);
        }
    }

    @Override
    public void onItemShareClick(OrderBean.DataBean orderItem) {}

    @Override
    public void onLoadMoreRequested() {
        orderListView.post(() -> {
            nowPage += 1;
            requestOrderData(nowPage, pageCount, request_order_status, true, selectedPlantform);
        });
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderComponent
                .builder()
                .appComponent(appComponent)
                .orderModule(new OrderModule(this))
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
        prepareVariable();
        return R.layout.mine_order_list_layout;
    }

    private void prepareVariable() {
        parentOrderList = new ArrayList<>();
        selectTags = Arrays.asList(this.getResources().getStringArray(R.array.filterTabs));
    }

    private int getSelectedPlatformByPosition(int position) {
        selectedPlantform = position;
//        int pos = position;
//        if (position == 1) {
//            selectedPlantform = Const.Platform.TAOBAO;
//        } else if (position == 2) {
//            selectedPlantform = Const.Platform.JD;
//        } else if (position == 3) {
//            selectedPlantform = Const.Platform.VIP;
//        }else  if(position==4){
//            selectedPlantform = Const.Platform.MGJ;
//        } else if (position == 5) {
//            selectedPlantform = Const.Platform.HRZ;
//        } else if (position == 6) {
//            selectedPlantform = Const.Platform.OFFLINE;
//        }
        com.orhanobut.logger.Logger.d(selectedPlantform);
        return selectedPlantform;
    }

    private void initDoubleTabData(){
        tabTexts = this.getResources().getStringArray(R.array.tabTexts);
        tabPlantforms = this.getResources().getStringArray(R.array.tabPlantforms);

        for (String text : tabPlantforms) {
            MineTabEntity tabEntity = new MineTabEntity(text);
            tabPlantformsList.add(tabEntity);
        }

        for (String text : tabTexts) {
            MineTabEntity tabEntity = new MineTabEntity(text);
            tabTextList.add(tabEntity);
        }
        mineOrderTabLayout.setTabData(tabTextList);
        mine_order_tabLayout_plantform.setTabData(tabPlantformsList);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initDropDownMenu();
        initDoubleTabData();
        initListener();
        titleView.setText(R.string.mine_order_list_page_title);
        setViewClickListener();

        orderListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        orderListAdapter = new OrderListAdapter(R.layout.mine_order_list_item_layout);
        orderListAdapter.setOnLoadMoreListener(this);
        orderListAdapter.setEnableLoadMore(true);

        orderListView.setAdapter(orderListAdapter);
        orderListAdapter.setOnItemClickListener(this);
        orderListAdapter.setLoadMoreView(new SimpleLoadMoreView());

        firstSelectedTag = getIntent().getIntExtra("select_order", 0);
        mineOrderTabLayout.setCurrentTab(firstSelectedTag);
        mine_order_tabLayout_plantform.setCurrentTab(0);
        request_order_status = firstSelectedTag;
        requestOrderData(nowPage, pageCount, request_order_status, false, selectedPlantform);

    }



    private void initDropDownMenu() {
        platformSelectAdapter = new OrderPlatformSelectAdapter(getApplicationContext(), Arrays.asList(this.getResources().getStringArray(R.array.tabPlantforms)));
        View platformView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.order_platform_select_layout, null);
        GridView platformGrid = platformView.findViewById(R.id.search_platform_list_gridview);
        platformGrid.setAdapter(platformSelectAdapter);
        platformGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
        platformGrid.setOnItemClickListener((parent, view, position, id) -> {
            platformSelectAdapter.setCheckItem(position);
            dropDownMenu.switchMenu();
            ivTurnDown.setImageResource(R.drawable.common_icon_down);
            mine_order_tabLayout_plantform.setCurrentTab(position);
            nowPage = 1;
            requestOrderData(nowPage, pageCount, request_order_status, false, getSelectedPlatformByPosition(position));
        });


        TextView contentView = new TextView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setVisibility(View.GONE);

        List<View> popupViews = new ArrayList<>();
        popupViews.add(platformView);
        dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
        dropDownMenu.setOnDropMenuDismissListener(() -> {
            tvAllCategory.setVisibility(View.GONE);
            mine_order_tabLayout_plantform.setVisibility(View.VISIBLE);
        });
    }


    private void initListener(){
        mineOrderTabLayout.setOnTabSelectListener(this);
        mine_order_tabLayout_plantform.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if(platformSelectAdapter!=null)  platformSelectAdapter.setCheckItem(position);
                nowPage = 1;
                requestOrderData(nowPage, pageCount, request_order_status, false, getSelectedPlatformByPosition(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    private void requestOrderData(int page, int count, int orderStatus, boolean isLoadMore, int thirdpartId) {
        RetrofitUrlManager.getInstance().putDomain("order_findList", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("accountSecToken", LoginDataManager.getsInstance(OrderListActivity.this).getAccountSecToken());
        map.put("page", page + "");
        map.put("pageSize", count + "");
        map.put("activityId", -1 + "");
        map.put("orderStatus", orderStatus + "");
        map.put("thirdpartId", thirdpartId + "");

        isLoadMoreState = isLoadMore;
        mPresenter.getOrdersByType(HttpParamUtil.getResultMap(OrderListActivity.this, map), isLoadMore);
    }


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showOrderList(OrderInfo orderInfo) {}

    @Override
    public void showOrders(List<OrderBean.DataBean> orderBean) {
        if (isLoadMoreState && orderListAdapter != null) {
            if (orderBean == null || orderBean.size() == 0) {
                orderListAdapter.loadMoreEnd();
            } else {
                parentOrderList.addAll(orderBean);
                orderListAdapter.setNewData(parentOrderList);
                orderListAdapter.loadMoreComplete();
            }
        } else {
            hideNodataView();
            parentOrderList.clear();
            parentOrderList.addAll(orderBean);
            orderListAdapter.setNewData(parentOrderList);
        }
    }

    @Override
    public void showRequestError(String errorMsg) {
        orderListAdapter.setNewData(null);
        showNodataView(errorMsg);
    }

    @Override
    public void requestInitDataEmpty() {
        orderListAdapter.setNewData(null);
        showNodataView(Consts.ERROR_STR.DATA_EMPTY);
    }

    @Override
    public void requestMoreDataEmpty() {
        orderListAdapter.loadMoreEnd();
    }

    @Override
    public void showOrderDetailEmpty() { }

    @Override
    public void getOrderListByUserIdFail() { }

    @Override
    public void getOrdersByType() { }

    @Override
    public void showOrderDetails(OrderDetail orderItem) { }

    private void setViewClickListener() {
        backBtn.setOnClickListener(OrderListActivity.this);
        platformView.setOnClickListener(OrderListActivity.this);
        activityLayout.setOnClickListener(OrderListActivity.this);
        ivTurnDown.setOnClickListener(OrderListActivity.this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == backBtn.getId()) {
            finish();
        }else if (id==ivTurnDown.getId()){
            if(dropDownMenu.isShowing()){
                tvAllCategory.setVisibility(View.GONE);
                mine_order_tabLayout_plantform.setVisibility(View.VISIBLE);
                ivTurnDown.setImageResource(R.drawable.common_icon_down);

            }else {
                tvAllCategory.setVisibility(View.VISIBLE);
                mine_order_tabLayout_plantform.setVisibility(View.GONE);
                ivTurnDown.setImageResource(R.drawable.common_icon_up);
            }
            dropDownMenu.switchMenu();
        }
    }


    @Override
    public void showLoading() {
        if (toastUtil == null) {
            toastUtil = new DialogUtils(getActivity(), R.layout.common_loading_toast, "");
        }
        toastUtil.show();
    }

    @Override
    public void hideLoading() {
        if (toastUtil != null) {
            toastUtil.hide();
        }
    }

    private void showNodataView(String message) {
        if (message.equals(Consts.ERROR_STR.NETWORK_PROBLEM) || message.equals(Consts.ERROR_STR.NETWORK_UNENABLE)) {
            orderListView.setVisibility(View.GONE);

            defaultViewLl.setVisibility(View.VISIBLE);
            defaultTitleTv.setText(R.string.no_network_hint);
            defaultOperateTv.setVisibility(View.GONE);
            defaultIconIv.setImageResource(R.drawable.network_error_icon);
        } else if (message.equals(Consts.ERROR_STR.NETWORK_TIMEOUT)) {
            orderListView.setVisibility(View.GONE);

            defaultTitleTv.setText(R.string.network_error_hint);
            defaultViewLl.setVisibility(View.VISIBLE);
            defaultOperateTv.setVisibility(View.VISIBLE);
            defaultOperateTv.setText(R.string.refresh_data_str);
            defaultOperateTv.setOnClickListener(v -> {
                requestOrderData(nowPage, pageCount, request_order_status, false, selectedPlantform);
            });
            defaultIconIv.setImageResource(R.drawable.network_error_icon);
        } else if (message.equals(Consts.ERROR_STR.DATA_EMPTY)) {
            orderListView.setVisibility(View.GONE);

            defaultViewLl.setVisibility(View.VISIBLE);
            defaultTitleTv.setText(R.string.no_orders_hint);
            defaultOperateTv.setVisibility(View.VISIBLE);
            defaultOperateTv.setText(R.string.refresh_data_str);
            defaultOperateTv.setOnClickListener(v -> {
                requestOrderData(nowPage, pageCount, request_order_status, false, selectedPlantform);
            });
            defaultIconIv.setImageResource(R.drawable.no_data_icon);
        }
    }

    private void hideNodataView() {
        //   viewStub.setVisibility(View.GONE);
        orderListView.setVisibility(View.VISIBLE);
        defaultViewLl.setVisibility(View.GONE);
    }

    //下拉筛选适配器
    class RecycleItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public RecycleItemAdapter(int layoutResId, @Nullable List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

            LinearLayout ll_container_sort = helper.getView(R.id.ll_container_sort);
            TextView tv_sort_name = helper.getView(R.id.tv_sort_name);

            tv_sort_name.setText(item);


        }
    }

    //订单类型
    public enum OrderType {
        //全部
        ALL("all"),
        //交易成功
        SUCCESS("success"),
        //交易结算
        FINISH("finish"),
        //订单失效
        INVALID("invalid");
        public String type;

        OrderType(String type) {
            this.type = type;
        }
    }

    @Override
    public void showMessage(@NonNull String message) { }

    @Override
    public void launchActivity(@NonNull Intent intent) { }

    @Override
    public void killMyself() { }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
