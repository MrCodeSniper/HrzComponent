package com.mujirenben.android.mine.mvp.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.DateTimeUtil;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.widget.DialogUtils;
import com.mujirenben.android.common.widget.HrzHeadersView;
import com.mujirenben.android.common.widget.HrzLoadMoreView;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;
import com.mujirenben.android.mine.di.component.DaggerProfitDetailsComponent;
import com.mujirenben.android.mine.di.module.ProfitDetailsModule;
import com.mujirenben.android.mine.mvp.contract.ProfitDetailsContract;
import com.mujirenben.android.mine.mvp.model.bean.ProfitListDetailsBean;
import com.mujirenben.android.mine.mvp.model.bean.ProfitMainDetailsBean;
import com.mujirenben.android.mine.mvp.model.bean.ProfitMonthDetailsBean;
import com.mujirenben.android.mine.mvp.model.bean.ProfitNoticeBean;
import com.mujirenben.android.mine.mvp.presenter.ProfitDetailsPresenter;
import com.mujirenben.android.mine.mvp.ui.adapter.ProfitDetailsListAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;


@Route(path = ARouterPaths.PROFIT_DETAILS)
public class ProfitDetailsActivity extends BaseActivity<ProfitDetailsPresenter> implements ProfitDetailsContract.View, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {
    @BindView(R2.id.tv_back)
    ImageView backBtn;
    @BindView(R2.id.tv_titlebar)
    TextView titleView;
    @BindView(R2.id.mine_profit_hrz_headerview)
    HrzHeadersView hrzHeadersView;
    @BindView(R2.id.mine_profit_recycler_view)
    RecyclerView recyclerView;
    @BindView(R2.id.mine_profit_float_tab_layout)
    View floatTabView;
    @BindView(R2.id.mine_profit_float_tablayout)
    TabLayout floatTabLayout;

    TextView doCashBtn;
    TabLayout tabLayout;
    View floatCalendarView;
    View calendarView;
    View monthLayout;

    private View headerView;
    private ProfitDetailsListAdapter mAdapter;

    private int pageNumber = 1;
    private int type = PROFIT_TYPE_SELF_BUY;
    private final static int PAGE_SIZE = 10;

    private int mYear;
    private int mMonth;
    private int mDay;

    private final static int PROFIT_TYPE_SELF_BUY  = 1; // 个人购物
    private final static int PROFIT_TYPE_ACTIVITY_REWARD  = 2; // 活动奖励
    private final static int PROFIT_TYPE_VIP_REWARD  = 3; // 皇冠奖励
    private final static int PROFIT_TYPE_STORE_REWARD  = 4; // 店铺收益
    /**缺省页面*/
    private View defaultLayout;
    private int DATA_ERROR_TYPE = 0;
    private int NET_WORK_ERROR_TYPE = 1;
    private boolean floatTabVisible = false;
    private DialogUtils dialog;
    private int tabIndex;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerProfitDetailsComponent.builder()
                .appComponent(appComponent)
                .profitDetailsModule(new ProfitDetailsModule(this))
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
        return R.layout.mine_profit_details_layout;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initViews();
    }

    private void initViews() {
        titleView.setText(R.string.mine_profit_details_title);
        backBtn.setOnClickListener(v -> finish());
        defaultLayout =LayoutInflater.from(this).inflate(R.layout.common_default_layout,null);
        headerView = LayoutInflater.from(this).inflate(R.layout.mine_profit_headview_layout, null);
        doCashBtn = headerView.findViewById(R.id.mine_profit_header_cash_btn);
        tabLayout = headerView.findViewById(R.id.mine_profit_tab_tablayout);
        monthLayout = headerView.findViewById(R.id.mine_profit_header_month_layout);
        doCashBtn.setOnClickListener(v -> ARouter.getInstance().build(ARouterPaths.WITHDRAW_DEPOSIT).navigation(this));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // 展示隐藏tab tab悬停
                int[] location = new int[2];
                tabLayout.getLocationOnScreen(location);
                int y = location[1];
                int[] location2 = new int[2];
                floatTabLayout.getLocationOnScreen(location2);
                int y2 = location2[1];

                Log.e("onScrolled","y:"+y +"y2:"+y2);
                if (y <= y2) {
                    floatTabVisible = true;
                    floatTabView.setVisibility(View.VISIBLE);
                } else {
                    floatTabVisible = false;
                    floatTabView.setVisibility(View.INVISIBLE);
                }
            }
        });

        mAdapter = new ProfitDetailsListAdapter(null, getApplicationContext());
        mAdapter.setLoadMoreView(new HrzLoadMoreView());
        mAdapter.setOnLoadMoreListener(this, recyclerView);
        mAdapter.setHeaderView(headerView);
        recyclerView.setAdapter(mAdapter);
        hrzHeadersView.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initRemoteData();
            }
        });

        initHeaderView();
        initTabLayout();
        initRemoteData();
    }

    private void initHeaderView() {
        calendarView = headerView.findViewById(R.id.mine_profit_tab_calendar);
        floatCalendarView = findViewById(R.id.mine_profit_float_calendar_iv);
        calendarView.setOnClickListener(ProfitDetailsActivity.this);
        floatCalendarView.setOnClickListener(ProfitDetailsActivity.this);
    }

    private void initRemoteData() {
        if(mAdapter.isLoading()) return;
        RetrofitUrlManager.getInstance().putDomain("profit_details", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        mPresenter.getProfitMainDetails(getApplicationContext());
        mPresenter.getProfitNotice();
        mPresenter.getProfitMonthDetails(getApplicationContext());
        mPresenter.getProfitListByType(getApplicationContext(), type, pageNumber, PAGE_SIZE);
    }

    private void initTabLayout() {
        ArrayList<String> titleListFans = new ArrayList<>();
        titleListFans.add("个人购物奖励");
        titleListFans.add("活动奖励");
        titleListFans.add("皇冠奖励");
        //判断当前的用户等级，如果是店主则tab显示店铺收益，如果是非店主则显示粉丝收益
        int lv =  LoginDataManager.getsInstance(this).getLevel();
        if(lv == 2){
            titleListFans.add("店铺收益");
        }else {
            titleListFans.add("粉丝收益");
        }

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        floatTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        floatTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        for (int i = 0; i < titleListFans.size(); i++) {
            String title = titleListFans.get(i);
            TabLayout.Tab tab = tabLayout.newTab();
            TabLayout.Tab tabFloat = floatTabLayout.newTab();
            tab.setText(title);
            tabFloat.setText(title);

            tabLayout.addTab(tab);
            floatTabLayout.addTab(tabFloat);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (!floatTabVisible){
                    //浮框不可见时候才发送请求
                    tabIndex = tab.getPosition();
                    resetProfitListByType(tabIndex);
                }
                floatTabLayout.getTabAt(tab.getPosition()).select();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        floatTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (floatTabVisible){
                    //浮框可见时候才发送请求
                    tabIndex = tab.getPosition();
                    resetProfitListByType(tab.getPosition());
                }
                tabLayout.getTabAt(tab.getPosition()).select();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 刷新收益列表数据
     *
     * @param position 点击的tab位置
     */
    public void resetProfitListByType(int position) {
        if(dialog == null){
            dialog = new DialogUtils(this,R.layout.common_loading_toast,"");
        }
        showLoading();
        pageNumber = 1;
        int index = position + 1;
        if (index == PROFIT_TYPE_SELF_BUY) {
            type = PROFIT_TYPE_SELF_BUY;
        } else if (index == PROFIT_TYPE_ACTIVITY_REWARD) {
            type = PROFIT_TYPE_ACTIVITY_REWARD;
        } else if (index == PROFIT_TYPE_VIP_REWARD) {
            type = PROFIT_TYPE_VIP_REWARD;
        } else if (index == PROFIT_TYPE_STORE_REWARD) {
            type = PROFIT_TYPE_STORE_REWARD;
        }

        mPresenter.getProfitListByType(getApplicationContext(), type, pageNumber, PAGE_SIZE);
    }

    @Override
    public void onClick(View v) {
        if (v == calendarView || v == floatCalendarView) {
            showDatePicker();
        }
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        if (mYear == 0) {
            mYear = calendar.get(Calendar.YEAR);
        }
        if (mMonth == 0) {
            mMonth = calendar.get(Calendar.MONTH);
        }
        if (mDay == 0) {
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(ProfitDetailsActivity.this, (view, year, month, dayOfMonth) -> {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
        }, mYear, mMonth, mDay);

        datePickerDialog.show();
    }

    @Override
    public void bindMainProfitData(ProfitMainDetailsBean profitMainDetailsBean) {
        hrzHeadersView.refreshComplete();
        if (profitMainDetailsBean == null || profitMainDetailsBean.getData() == null) {
            return;
        }

        TextView totalView = headerView.findViewById(R.id.mine_header_profit_total_tv);
        TextView precView = headerView.findViewById(R.id.mine_header_profit_evulate_tv);
        TextView withdrawingView = headerView.findViewById(R.id.mine_header_profit_withdrawing_tv);
        TextView withdrawView = headerView.findViewById(R.id.mine_header_profit_withdraw_tv);
        TextView cashView = headerView.findViewById(R.id.mine_header_profit_cash_tv);

        ProfitMainDetailsBean.MainProfit mainProfit = profitMainDetailsBean.getData();
        totalView.setText(mainProfit.getTotalIncome());
        precView.setText(mainProfit.getPredictIncome());
        withdrawingView.setText(mainProfit.getWithDrawing());
        withdrawView.setText(mainProfit.getWithDraw());
        cashView.setText(mainProfit.getCash());
    }

    @Override
    public void bindProfitNoticeData(ProfitNoticeBean profitNoticeBean) {
        if (profitNoticeBean == null || profitNoticeBean.getData() == null) {
            return;
        }

        TextView noticeTitleView = headerView.findViewById(R.id.mine_header_notice_title);
        TextView noticeDescView = headerView.findViewById(R.id.mine_header_notice_desc);
        TextView noticeSubTitleView = headerView.findViewById(R.id.mine_header_notice_sub_title);
        TextView noticeSubDescView = headerView.findViewById(R.id.mine_header_notice_sub_desc);

        ProfitNoticeBean.DescBean descBean= profitNoticeBean.getData();
        noticeTitleView.setText(Html.fromHtml(descBean.getMainTitle()));
        noticeDescView.setText(Html.fromHtml(descBean.getMainDesc()));
        noticeSubTitleView.setText(Html.fromHtml(descBean.getSubTitle()));
        noticeSubDescView.setText(Html.fromHtml(descBean.getSubDesc()));
    }

    @Override
    public void bindProfitMonthData(ProfitMonthDetailsBean profitMonthDetailsBean) {
        if (profitMonthDetailsBean == null || profitMonthDetailsBean.getData() == null) {
            return;
        }

        ProfitMonthDetailsBean.MonthDetails monthDetails = profitMonthDetailsBean.getData();
        TextView titleView = monthLayout.findViewById(R.id.mine_profit_month_details_title);
        TextView personalTv = monthLayout.findViewById(R.id.mine_profit_personal_number_tv);
        TextView activityTv = monthLayout.findViewById(R.id.mine_profit_activity_number_tv);
        TextView levelTv = monthLayout.findViewById(R.id.mine_profit_level_number_tv);
        TextView shopTv = monthLayout.findViewById(R.id.mine_profit_shop_number_tv);
        TextView serviceIncomeTv =  monthLayout.findViewById(R.id.mine_profit_reward_number_tv);
        int month = DateTimeUtil.getMonth();
        titleView.setText(getString(R.string.profit_main_month_title, month));
        personalTv.setText(monthDetails.getShopIncome()+"");
        activityTv.setText(monthDetails.getActivityIncome()+"");
        levelTv.setText(monthDetails.getCrownIncome()+"");
        shopTv.setText(monthDetails.getStoreIncome()+"");
        serviceIncomeTv.setText(monthDetails.getServiceIncome()+"");
    }

    @Override
    public void bindProfitListData(ProfitListDetailsBean profitListDetailsBean) {
        List<ProfitListDetailsBean.ProfitItem> profitItemList = profitListDetailsBean.getData();
        if ((profitItemList == null || profitItemList.size() == 0) && pageNumber == 1) { // 防止切换tab后没数据页面不刷新问题
            mAdapter.replaceData(new ArrayList<>());
            String msg = "你当前暂未产生收益";
            showDefaultLayout(DATA_ERROR_TYPE,msg,R.drawable.no_data_icon);
        } else {
            mAdapter.removeHeaderView(defaultLayout);
            int currentType = tabIndex + 1;
            int dataType = profitListDetailsBean.getDataType();
            //当前的数据类型跟当前的tab类型一致才加载数据
            if(currentType == dataType){
                if (pageNumber == 1) {
                    hrzHeadersView.refreshComplete();
                    mAdapter.loadMoreComplete();
                    mAdapter.replaceData(setItemTypeData(profitItemList));
                } else {
                    if(profitItemList == null || profitItemList.size() == 0){
                        mAdapter.loadMoreComplete();
                        mAdapter.loadMoreEnd();
                    }else {
                        mAdapter.getData().addAll(setItemTypeData(profitItemList));
                        mAdapter.notifyDataSetChanged();
                        mAdapter.loadMoreComplete();
                    }
                }
            }
        }
    }

    /**
     * 修改itemType
     * @param profitItemList
     * @return
     */
    private List<ProfitListDetailsBean.ProfitItem> setItemTypeData(List<ProfitListDetailsBean.ProfitItem> profitItemList) {
        for (int i = 0; i < profitItemList.size(); i++) {
            if (PROFIT_TYPE_VIP_REWARD == type && LoginDataManager.getsInstance(getApplicationContext()).getLevel() == 0) {
                profitItemList.get(i).setItemType(5); // 皇冠分类数据，且当前用户为粉丝
            } else {
                profitItemList.get(i).setItemType(type);
            }
        }
        return profitItemList;
    }

    /**
     * 显示缺省页，网络错误，数据错误
     * @param errorType
     * @param msg
     * @param resId
     */
    public void showDefaultLayout(int errorType, String msg, int resId){
        //只有在刷新，第一次加载时候显示缺省页
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,500);
        params.gravity= Gravity.CENTER;
        defaultLayout.setLayoutParams(params);
        mAdapter.replaceData(new ArrayList<>());
        mAdapter.removeHeaderView(defaultLayout);
        mAdapter.addHeaderView(defaultLayout);
        View defaultViewLl = defaultLayout.findViewById(R.id.default_view_ll);
        ImageView defaultIconIv = defaultLayout.findViewById(R.id.default_icon_iv);
        TextView defaultTitleTv = defaultLayout.findViewById(R.id.default_title_tv);
        TextView defaultTitleDesTv = defaultLayout.findViewById(R.id.default_title_des_tv);
        TextView defaultOperateTv = defaultLayout.findViewById(R.id.default_operate_tv);
        LinearLayout.LayoutParams titleParams = (LinearLayout.LayoutParams) defaultTitleTv.getLayoutParams();
        titleParams.topMargin = ArmsUtils.dip2px(this,25);
        LinearLayout.LayoutParams titleDesParams = (LinearLayout.LayoutParams) defaultTitleTv.getLayoutParams();
        titleDesParams.topMargin = ArmsUtils.dip2px(this,25);
        defaultTitleDesTv.setVisibility(View.GONE);
        defaultOperateTv.setVisibility(View.GONE);

        defaultViewLl.setVisibility(View.VISIBLE);
        defaultIconIv.setImageResource(resId);
        defaultTitleDesTv.setVisibility(View.GONE);
        if(errorType == DATA_ERROR_TYPE ){
            defaultTitleTv.setText(msg);
            defaultTitleTv.setText("你当前暂未产生收益");
        }else if(errorType == NET_WORK_ERROR_TYPE){
            defaultTitleTv.setText(msg);
            defaultTitleDesTv.setVisibility(View.VISIBLE);
            defaultTitleDesTv.setText("点击刷新");
            defaultTitleDesTv.setOnClickListener(v ->
                    mPresenter.getProfitListByType(getApplicationContext(),type, pageNumber, PAGE_SIZE)
            );
        }
        hrzHeadersView.refreshComplete();
        mAdapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreSuccess() {
        mAdapter.loadMoreComplete();
    }

    @Override
    public void onLoadMoreFailure() {
        mAdapter.loadMoreComplete();
        mAdapter.loadMoreEnd();
        ArmsUtils.makeText(this,"加载更多失败");
    }

    @Override
    public void onLoadDataError(String msg) {
        hrzHeadersView.refreshComplete();
        mAdapter.loadMoreComplete();
    }

    /**
     * 网络错误
     * @param msg
     */
    @Override
    public void onLoadNetworkError(String msg) {
        hrzHeadersView.refreshComplete();
        mAdapter.loadMoreComplete();
    }

    /**
     * 列表的数据异常
     * @param msg
     */
    @Override
    public void onLoadListDataError(String msg) {
        if(pageNumber == 1){
            showDefaultLayout(DATA_ERROR_TYPE,msg,R.drawable.no_data_icon);
        }else {
            ArmsUtils.makeText(this,msg);
        }
        hrzHeadersView.refreshComplete();
        mAdapter.loadMoreComplete();
    }

    /**
     * 列表网络异常
     * @param msg
     */
    @Override
    public void onLoadListNetworkError(String msg) {
        if(pageNumber == 1){
            showDefaultLayout(NET_WORK_ERROR_TYPE,msg,R.drawable.network_error_icon);
        }
        hrzHeadersView.refreshComplete();
        mAdapter.loadMoreComplete();
    }

    @Override
    public void showLoading() {
        if(dialog != null){
            dialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if(dialog != null){
            dialog.hide();
        }
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
    public void onLoadMoreRequested() {
        //如果正在刷新，则不能使用加载更多
       if(hrzHeadersView.isRefreshing()) return;
        recyclerView.post(() -> {
            pageNumber++;
            Log.e("onLoadMoreRequested",">>>>>>>>>>>fuck "+pageNumber);
            mPresenter.getProfitListByType(getApplicationContext(),type, pageNumber, PAGE_SIZE);
        });
    }
}
