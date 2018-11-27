package com.mujirenben.android.home.mvp.ui.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.baichuan.trade.common.utils.JSONUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.arounter.HrzRouter;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.base.BaseFragment;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.datapackage.bean.SearchHotWords;
import com.mujirenben.android.common.event.LoginStatusEvent;
import com.mujirenben.android.common.event.VipLevelChangedEvent;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ClickUtil;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.sensorHelper.SensorHelper;
import com.mujirenben.android.common.widget.BackToTopView;
import com.mujirenben.android.common.widget.HrzHeadersView;
import com.mujirenben.android.common.widget.LoadingDialog;
import com.mujirenben.android.common.widget.StatusBarView;
import com.mujirenben.android.common.widget.WaterfallDividerDecoration;
import com.mujirenben.android.common.widget.campaignview.TimeLimitViewForHome;
import com.mujirenben.android.common.widget.dialog.dialogpopmanager.DialogUIUtils;
import com.mujirenben.android.common.widget.dialog.dialogpopmanager.bean.BuildBean;
import com.mujirenben.android.common.widget.dialog.dialogpopmanager.utils.DialogManager;
import com.mujirenben.android.common.widget.indicatorLayout.IndicatorLayout;
import com.mujirenben.android.home.R;
import com.mujirenben.android.home.R2;
import com.mujirenben.android.home.dagger.component.DaggerHomeComponent;
import com.mujirenben.android.home.dagger.module.HomeModule;
import com.mujirenben.android.home.mvp.contract.HomeContract;
import com.mujirenben.android.home.mvp.model.entity.HomeModuleBean;
import com.mujirenben.android.home.mvp.model.entity.WaterfallBean;
import com.mujirenben.android.home.mvp.presenter.HomePresenter;
import com.mujirenben.android.home.mvp.ui.activity.MyCapturesActivity;
import com.mujirenben.android.home.mvp.ui.adapter.HomeWaterfallAdapter;
import com.mujirenben.android.home.mvp.ui.adapter.ToufuGridAdapter;
import com.mujirenben.android.home.mvp.ui.widget.GridDivider;
import com.mujirenben.android.home.mvp.ui.widget.JDHeaderView;
import com.mujirenben.android.home.service.ResourceLoadService;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.RequestBody;

import static com.mujirenben.android.common.constants.Consts.HRZ_ROUTER_BUNDLE;
import static com.mujirenben.android.common.constants.Consts.USE_GRAY_TEST;

/**
 * Created by mac on 2018/5/5.
 */

public class HomeModifyFragment extends BaseFragment<HomePresenter> implements HomeContract.View,ViewPager.OnPageChangeListener,
         BaseQuickAdapter.RequestLoadMoreListener,BaseQuickAdapter.OnItemClickListener ,PtrHandler,PtrUIHandler{

    @BindView(R2.id.home_title_bar_layout)
    LinearLayout mHomeTitleBarLayout;
    @BindView(R2.id.home_status_bar)
    StatusBarView home_status_bar;
    @BindView(R2.id.ll_home_layout)
    LinearLayout ll_home_layout;
    @BindView(R2.id.masque)
    View mMasque;

    private BackToTopView toTopView;
    private View mRootView;
    private HrzHeadersView mPTR;
    private RecyclerView mRecyclerView;
    private BGABanner mBanner;
    private LinearLayout mScanningLayout;
    private LinearLayout mHeaderIconsLayout;
    private View mHeader;
    private View mSearchBox;
    private RecyclerView mRvToufuPart;
    private TextView tv_search_text;
    private LinearLayout mToolBarLL;
    private LinearLayout llExpirationBar;

    private HomeWaterfallAdapter mAdapter;
    private ToufuGridAdapter toufuGridAdapter;

    private Drawable mToolBarDrawable;
    private int mWaterfallItemGutterPx = 4 * 3;
    private int mNextPageToLoad = 2;
    private int mRvScrollDistance = 0;
    private int mToufuCount;
    private boolean mFirstShowTip;
    private long mLastClickItemTime = SystemClock.elapsedRealtime();

    private HomeModuleBean mBannerData;
    private HomeModuleBean mIconListData;
    private HomeModuleBean mToufuData;
    private HomeModuleBean mRewardData;

    private Map<Integer, Boolean> mModulesDataLoadedStatus = new HashMap<>();
    private List<WaterfallBean> mWaterfallItems = new ArrayList<>();

    public static final int REQUEST_TIP_CODE=100;

    private boolean canLoad = true;

    //加载主页资源服务
    private ResourceLoadService.CallbackBinder mBinder;
    private ServiceConnection mConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected");
            mBinder = (ResourceLoadService.CallbackBinder)service;
            mBinder.setCallback(new Callback());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private int[] mIconIds = {R.id.ll_one, R.id.ll_two, R.id.ll_three, R.id.ll_four, R.id.ll_five};

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent
                .builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFirstShowTip = true;
        mRootView = inflater.inflate(R.layout.home_activity_layout, container, false);
        initFragmentView(mRootView);
        initRecycle(mRootView);
        return mRootView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initStatusBarPreStatus();
        checkShopKeeperExpiration();
        requestHotWord(Const.Platform.ALL);
    }

    private void initStatusBarPreStatus(){
        mToolBarDrawable.setAlpha(0);
        home_status_bar.setBackgroundColor(0x00000000);
        home_status_bar.adjustStatusBarColor(getActivity());
    }


    private void initFragmentView(View rootView){
        tv_search_text=rootView.findViewById(R.id.tv_search_text);
        mPTR = rootView.findViewById(R.id.ptr);
        llExpirationBar=rootView.findViewById(R.id.ll_expiration_bar);
        mToolBarLL = rootView.findViewById(R.id.ll_tool_bar);
        mToolBarDrawable = mToolBarLL.getBackground();
        mHeader = LayoutInflater.from(getActivity()).inflate(R.layout.include_header_view, null, false);
        mPTR.setViewPager(mHeader.findViewById(R.id.banner));
        mPTR.setPtrHandler(this);
        mPTR.addPtrUIHandler(this);
    }

    public void scrollToTop(){
        if (mRecyclerView != null){
            mRecyclerView.smoothScrollToPosition(0);
        }
    }
    /**
     * 搜索热词请求
     */
    private void requestHotWord(int platform){
        RetrofitUrlManager.getInstance().putDomain("global_search", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        Map<String,String> map=new HashMap<>();
        map.put("platform",String.valueOf(platform));
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), JSONUtils.toJson(map));
        mPresenter.requestHotWords(requestBody);
    }

    @Override
    public void getSearchHotWordsSuccess(SearchHotWords searchHotWords) {
        if(searchHotWords == null
                ||  searchHotWords.getData() == null
                ||  searchHotWords.getData().getSearchBox() == null
                ||  searchHotWords.getData().getSearchBox().getSearchContent()==null){
            return;
        }
        tv_search_text.setText(searchHotWords.getData().getSearchBox().getSearchContent());
    }

    @Override
    public void getSearchHotWordsFail() {
        tv_search_text.setText(R.string.try_use_ticket);
    }


    private void initRecycle(View rootView) {

        mWaterfallItemGutterPx = getResources().getDimensionPixelSize(R.dimen.waterfall_item_gutter);

        mScanningLayout = rootView.findViewById(R.id.scanning_layout);
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mSearchBox = rootView.findViewById(R.id.rl_search_box);
        mHeaderIconsLayout=mHeader.findViewById(R.id.ll_header_icons);
        mRvToufuPart=mHeader.findViewById(R.id.rv_tofu_part);
        toTopView = rootView.findViewById(R.id.iv_to_top);

        toTopView.attathRecyclerView(mRecyclerView);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        mRvToufuPart.setLayoutManager(gridLayoutManager);

        StaggeredGridLayoutManager recyclerViewLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(recyclerViewLayoutManager);

        mAdapter = new HomeWaterfallAdapter(mWaterfallItems, getActivity());
        toufuGridAdapter=new ToufuGridAdapter(R.layout.home_toufu_item_view);

        mAdapter.addHeaderView(mHeader);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new WaterfallDividerDecoration(mAdapter,getActivity()));
        mRvToufuPart.setAdapter(toufuGridAdapter);
        mRvToufuPart.addItemDecoration(new GridDivider(getActivity()));

        // 滑动图片停止加载
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(getActivity().getApplicationContext()).resumeRequests();
                } else {
                    Glide.with(getActivity().getApplicationContext()).pauseRequests();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mRvScrollDistance += dy;
                Log.i("jan", "mRvScrollDistance = " + mRvScrollDistance);
                adjustToolbar();
            }
        });

        mScanningLayout.setOnClickListener(v -> MyCapturesActivity.gotoActivity(getActivity()));
        mRecyclerView.smoothScrollToPosition(0);
    }


    /**
     * 根据rv移动距离调整透明度
     */
    private void adjustToolbar() {
        if (mBanner == null) return;
        float ratio = mRvScrollDistance / (float)mBanner.getHeight();
        if (ratio > 1) {
            ratio = 1;
        } else if (ratio < 0) {
            ratio = 0;
        }

        int alpha = (int)(255 * ratio);

        mToolBarDrawable.setAlpha(alpha);
        home_status_bar.setBackgroundColor(0x00000000);
        home_status_bar.adjustStatusBarColor(getActivity());
    }


    /**
     * 瀑布流点击事件
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if(!ClickUtil.isFastDoubleClick()){
            WaterfallBean data = mAdapter.getData().get(position);
            waterfallClickRoute(data);
            waterfallClickPoint(data);
        }
    }

    private void waterfallClickRoute(WaterfallBean data){
        if (data.getItemType() == WaterfallBean.TYPE_PRODUCT) {

            Bundle bundle=new Bundle();
            bundle.putString(Consts.GOODS_ID_INTENT_STR,data.getProduct().getId()+"");
            bundle.putString(Consts.PLATFORM_ID_INTENT_STR, data.getProduct().getPlatform()+"");
            bundle.putString(Consts.ROUTER_FROM,"瀑布流");

            ARouter.getInstance().build(ARouterPaths.GOODS_DETAIL)
                    .withBundle(HRZ_ROUTER_BUNDLE,bundle)
                    .navigation(getActivity());
        } else if (data.getItemType() == WaterfallBean.TYPE_ADVERTISE) {
            HrzRouter.getsInstance(getActivity()).navigation(data.getAdvertise().getUrl());
        } else if (data.getItemType() == WaterfallBean.TYPE_SHOP) {
            HrzRouter.getsInstance(getActivity()).navigation(data.getShop().getShopUrl());
        }
    }



    private class Callback implements ResourceLoadService.Callback, Serializable {

        @Override
        public void onHomeModuleDataLoaded(int module, HomeModuleBean data) {
            Log.i(TAG, "module = " + module + " data = " + data.getCode());
            switch (module) {
                case ResourceLoadService.Callback.MODULE_BANNER:
                    mModulesDataLoadedStatus.put(MODULE_BANNER, true);
                    onBannerDataLoaded(data);
                    break;
                case ResourceLoadService.Callback.MODULE_ICON_LIST:
                    mModulesDataLoadedStatus.put(MODULE_ICON_LIST, true);
                    onIconListDataLoaded(data);
                    break;
                case ResourceLoadService.Callback.MODULE_TOUFU:
                    mModulesDataLoadedStatus.put(MODULE_TOUFU, true);
                    onToufuDataLoaded(data);
                    break;
                case ResourceLoadService.Callback.MODULE_REWARD:
                    mModulesDataLoadedStatus.put(MODULE_REWARD, true);
                    onRewardDataLoaded(data);
                    break;
                case ResourceLoadService.Callback.MODULE_WATERFALL:
                    mModulesDataLoadedStatus.put(MODULE_WATERFALL, true);
                    onWaterfallDataLoaded(data);
                    break;
            }
            checkModulesDataLoadedStatus();
        }

        @Override
        public void onMoreWaterfallDataLoaded(HomeModuleBean data) {
            List<WaterfallBean> list = generatorWaterfallItems(data);
            if (list.isEmpty()) {
                mAdapter.loadMoreEnd(false);
            } else {
                mAdapter.addData(generatorWaterfallItems(data));
                mAdapter.loadMoreComplete();

                mNextPageToLoad++;
                toTopView.triggle(mNextPageToLoad-1);
            }
        }
    }

    private void resetModulesDataLoadedStatus() {
        for (int i = 0 ; i <= 4; i++) {
            if (i == 3) { // 不显示新人活动，所以默认加载完毕
                mModulesDataLoadedStatus.put(i, true);
            } else {
                mModulesDataLoadedStatus.put(i, false);
            }
        }
        mNextPageToLoad = 2;
        //因为业务需求是加载两页数据显示置顶按钮，初始化的时候mNextPageToLoad就已经为2，所以减去1等到再加载一页的时候在显示按钮
    }

    private List<WaterfallBean> generatorWaterfallItems(HomeModuleBean data) {

        if(data==null||data.getData()==null){
            return new ArrayList<>();
        }

        HomeModuleBean.DataBean dataBean = data.getData().get(0);
        List<HomeModuleBean.DataBean.Product> products =
                dataBean.getProducts() == null ? new ArrayList<>() : dataBean.getProducts();
        List<HomeModuleBean.DataBean.Shop> shops =
                dataBean.getShops() == null ? new ArrayList<>() : dataBean.getShops();
        List<HomeModuleBean.DataBean.Advertise> advertises =
                dataBean.getAdvertises() == null ? new ArrayList<>() : dataBean.getAdvertises();

        List<WaterfallBean> items = new ArrayList<>();
        for (HomeModuleBean.DataBean.Product product : products) {
            WaterfallBean b = new WaterfallBean();
            b.setProduct(product);
            items.add(b);
        }


        for (HomeModuleBean.DataBean.Shop shop : shops) {
            WaterfallBean b = new WaterfallBean();
            b.setShop(shop);
            items.add(b);
        }
        for (HomeModuleBean.DataBean.Advertise advertise : advertises) {
            WaterfallBean b = new WaterfallBean();
            b.setAdvertise(advertise);
            items.add(b);
        }

        return items;
    }

    private void checkModulesDataLoadedStatus() {
        boolean loaded = true;
        for (boolean b : mModulesDataLoadedStatus.values()) {
            loaded = loaded && b;
        }
        if (loaded) {
            mPTR.refreshComplete();
        }

        canLoad=false;
        hideLoadDialog();
    }

    private void setResourceLoadServiceCallback() {
        resetModulesDataLoadedStatus();

        Log.i(TAG, "setResourceLoadServiceCallback");
        Intent intent = new Intent(getActivity(), ResourceLoadService.class);
        intent.setAction("hrz.intent.action.HOME_FRAGMENT");

        getActivity().bindService(intent, mConn, Context.BIND_AUTO_CREATE);
    }

    private void unbindResourceLoadService() {
        if (mBinder != null) {
            mBinder.setCallback(null);
        }
        getActivity().unbindService(mConn);
    }

    public void initTofu(View view){
        if (mToufuData == null || mToufuData.getData() == null) {
            //拿不到数据，隐藏豆腐块
            mHeader.findViewById(R.id.ll_toufu_type2).setVisibility(View.GONE);
            return;
        }

        mToufuCount = mToufuData.getData().size();
        if (mToufuCount == 2) {
            //只有两个豆腐块的情况
            mRvToufuPart.setVisibility(View.GONE);
            mHeader.findViewById(R.id.ll_toufu_type2).setVisibility(View.VISIBLE);
            ImageView toufu1 = (ImageView)mHeader.findViewById(R.id.iv_toufu_type2_1);
            ImageView toufu2 = (ImageView)mHeader.findViewById(R.id.iv_toufu_type2_2);
            toufu1.setOnClickListener(v -> {
                if (mToufuData.getData() == null) return;
                HrzRouter.getsInstance(getContext()).navigation(mToufuData.getData().get(0).getUrl());

                tofuClickPoint(mToufuData,1);
            });
            toufu2.setOnClickListener(v -> {
                if (mToufuData.getData() == null) return;
                HrzRouter.getsInstance(getContext()).navigation(mToufuData.getData().get(1).getUrl());

                tofuClickPoint(mToufuData,2);
            });
            Glide.with(getContext()).load(mToufuData.getData().get(0).getImg()).into(toufu1);
            Glide.with(getContext()).load(mToufuData.getData().get(1).getImg()).into(toufu2);

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                toufu1.requestLayout();
                toufu2.requestLayout();

                mRvToufuPart.post(() -> showGuideInterfaceIfNeeded());
            }, 500);
        } else {
            //有两个豆腐块以上的情况
            mRvToufuPart.setVisibility(View.VISIBLE);
            mHeader.findViewById(R.id.ll_toufu_type2).setVisibility(View.GONE);
            toufuGridAdapter.setNewData(mToufuData.getData());
            toufuGridAdapter.setOnItemClickListener((adapter, view1, position) -> {
                if (mToufuData.getData() == null) return;
                String url = mToufuData.getData().get(position).getUrl();
                Logger.e(url);
                HrzRouter.getsInstance(getContext()).navigation(url);

                tofuClickPoint(mToufuData,position+1);
            });

            View toufu = mHeader.findViewById(R.id.ll_toufu_type2);
            toufu.post(() -> showGuideInterfaceIfNeeded());
        }


//        TimeLimitViewForHome timeLimitViewForHome = view.findViewById(R.id.miaosha_view);
//        timeLimitViewForHome.setTime(100,19,20);
//        timeLimitViewForHome.start();
    }


    private void initIconList(View view){
        if (mIconListData == null || mIconListData.getData() == null) {
            return;
        }

        List<HomeModuleBean.DataBean> data = mIconListData.getData();

        if(data==null) return;

        mHeaderIconsLayout.removeAllViews();

        for(int i=0;i<data.size();i++){
            View icon_item_view=LayoutInflater.from(getActivity()).inflate(R.layout.homerecycle_item_icon_view,null);
            String url = data.get(i).getUrl();
            final int j = i;
            icon_item_view.setOnClickListener(v -> {
                HrzRouter.getsInstance(getContext()).navigation(url);
                iconClickPoint(data.get(j).getTitle(),j + 1);
            });

            Glide.with(getActivity()).load(data.get(i).getImg()).into((ImageView)icon_item_view.findViewById(R.id.iv_menu_icon));
            ((TextView)icon_item_view.findViewById(R.id.tv_menu_icon)).setText(data.get(i).getTitle());


            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.weight=1;
            icon_item_view.setLayoutParams(layoutParams);
            mHeaderIconsLayout.addView(icon_item_view);

        }

        for (int i = data.size(); i < mIconIds.length; i++) {
            view.findViewById(mIconIds[i]).setVisibility(View.GONE);
        }

    }

    private void initReward(View view) {
        if (mRewardData == null || mRewardData.getData() == null) {
            return;
        }
        ImageView rewardIV = view.findViewById(R.id.iv_newuser_register);
        if(mRewardData.getData()==null||mRewardData.getData().get(0)==null){
            return;
        }
        Glide.with(getActivity()).load(mRewardData.getData().get(0).getImg()).into(rewardIV);
    }

    private void initBanner(View view){
        if (mBannerData == null || mBannerData.getData() == null) {
            return;
        }
        int itemSize = mBannerData.getData().size();
        IndicatorLayout indicatorLayout  = view.findViewById(R.id.indicatorLayout);
        BGABanner banner=view.findViewById(R.id.banner);
        mBanner = banner;
        banner.setAutoPlayInterval(5 * 1000);
        if(itemSize <= 1 ){
            //只有一张，不显示指示器
            banner.setAutoPlayAble(false);
            indicatorLayout.setVisibility(View.GONE);
        }else {
            //大于一张，显示指示器
            indicatorLayout.setVisibility(View.VISIBLE);
            indicatorLayout.removePointView();
            indicatorLayout.setItemSize(itemSize);
            //设置第一张图片为选中状态
            indicatorLayout.setCurrentItemPosition(0);
        }
        banner.setData(R.layout.homerecycle_top_banner_content, mBannerData.getData(), null);
        banner.setDelegate((BGABanner.Delegate<View, HomeModuleBean.DataBean>) (banner12, itemView, model, position) -> {
            HrzRouter.getsInstance(getContext()).navigation(model.getUrl());
            bannerClickPoint(model.getTitle(),model.getId(),model.getUrl(),position+1);
        });
        banner.setAdapter((BGABanner.Adapter<View, HomeModuleBean.DataBean>) (banner1, itemView, model, position) -> {
            ImageView simpleDraweeView = itemView.findViewById(R.id.sdv_item_fresco_content);
            Glide.with(getActivity()).load(model.getImg()).into(simpleDraweeView);
        });
        banner.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ((IndicatorLayout)(mHeader.findViewById(R.id.indicatorLayout))).setCurrentItemPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void onBannerDataLoaded(HomeModuleBean data) {
        mBannerData = data;
        initBanner(mHeader);
    }

    public void onWaterfallDataLoaded(HomeModuleBean data) {
        mAdapter.setNewData(generatorWaterfallItems(data));
        mRecyclerView.scrollToPosition(0);
    }

    public void onToufuDataLoaded(HomeModuleBean data) {
        mToufuData = data;
        initTofu(mHeader);
    }

    public void onRewardDataLoaded(HomeModuleBean data) {
        mRewardData = data;
        initReward(mHeader);
    }

    public void onIconListDataLoaded(HomeModuleBean data) {
        mIconListData = data;
        initIconList(mHeader);
    }



    @Override
    public void onLoadMoreRequested() {
        mRecyclerView.postDelayed(() -> {
            if (mAdapter.getData().size() >= 800) {
                mAdapter.loadMoreEnd(false);
            } else {

                HashMap<String, Object> sensorMap = new HashMap<>();
                SensorHelper.uploadTrack("feed_load", sensorMap);

                if (mBinder != null) {
                    mBinder.requestLoadMoreWaterfallData(mNextPageToLoad, countProductNum());
                }
            }
        }, 1000);
    }

    private int countProductNum() {
        int count = 0;
        for (WaterfallBean bean : mAdapter.getData()) {
            if (bean.getItemType() == WaterfallBean.TYPE_PRODUCT) {
                count++;
            }
        }
        return count;
    }

    @OnClick(R2.id.home_title_bar_layout)
    public void clickEvent(View view) {
        Bundle bundle=new Bundle();
        bundle.putString("platform",Const.PlatformName.ALL);
        bundle.putString("passKeyWord", TextUtils.equals(tv_search_text.getText().toString(),getResources().getString(R.string.try_use_ticket))?"":tv_search_text.getText().toString());
        ARouter.getInstance().build(ARouterPaths.SEARCH_ENTERANCE)
                .withBundle(HRZ_ROUTER_BUNDLE,bundle)
                .navigation(getActivity());
    }

    private void showGuideInterfaceIfNeeded() {

        if (USE_GRAY_TEST)return;

        if (!mFirstShowTip) return;

        if (!SpUtil.getBoolenValue(null, "showed_guide_interface", getActivity())) {
            int[] tofuLoc = new int[2];
            int toufuH, toufuW;
            if (mToufuCount == 2) {
                View toufu = mHeader.findViewById(R.id.ll_toufu_type2);
                toufu.getLocationOnScreen(tofuLoc);
                toufuH = toufu.getHeight();
                toufuW = toufu.getWidth();

                Log.i("jan", "toufuH = " + toufuH);

            } else {
                mRvToufuPart.getLocationOnScreen(tofuLoc);
                toufuH = mRvToufuPart.getHeight();
                toufuW = mRvToufuPart.getWidth();
            }

            int[] searchLoc = new int[2];
            mSearchBox.getLocationOnScreen(searchLoc);

            ARouter.getInstance()
                    .build(ARouterPaths.TIP_ACTIVITY)
                    .withParcelable("search_box_region", new Rect(searchLoc[0] - 10, searchLoc[1] - 10,
                            searchLoc[0] + mSearchBox.getWidth() + 10, searchLoc[1] + mSearchBox.getHeight() + 10))
                    .withParcelable("tofu_region", new Rect(tofuLoc[0], tofuLoc[1],
                            tofuLoc[0] + toufuW, tofuLoc[1] + toufuH))
                    .withTransition(0, 0)
                    .navigation(getActivity());
            ((BaseActivity)getActivity()).invokeActivityMethod(Consts.DISMISS_UPGRADE_DIALOG);

            mFirstShowTip = false;
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            delayInit();
            home_status_bar.adjustStatusBarColor(getActivity());
        }
    }


    //延迟加载数据
    private void delayInit() {
        if (!canLoad) {
            return;
        }
        showLoadDialog();
        setResourceLoadServiceCallback();
    }



    private void showLoadDialog(){
        LoadingDialog.getInstance(getActivity()).show();
    }

    private void hideLoadDialog(){
        LoadingDialog.getInstance(getActivity()).hide();
    }

    //---------------------------------店主过期提示相关----------------------------------------

    private void dismissExpirationBarIfNeeded() {
        LoginDataManager loginDataManager = LoginDataManager.getsInstance(getContext());
        if (loginDataManager.getLevel() != Const.UserLevel.LEVEL_SHOP) {
            return;
        }

        LinearLayout expirationBarLL = mRootView.findViewById(R.id.ll_expiration_bar);
        if (expirationBarLL.getVisibility() != View.VISIBLE) {
            return;
        }

        long diffDays = (loginDataManager.getShopKeeperExpirationTimeMillis()
                - System.currentTimeMillis()) / (1000 * 60 * 60 * 24) + 1;
        if (diffDays > 8) {
            expirationBarLL.setVisibility(View.GONE);
        }
    }

    /**
     *  检查店主资格是否到期
     */
    private void checkShopKeeperExpiration() {
        if (LoginDataManager.getsInstance(getContext()).getLevel() == Const.UserLevel.LEVEL_FANS || LoginDataManager.getsInstance(getContext()).getShopKeeperExpirationTimeMillis() == 0) {
            return;
        }

        Calendar expirationDate = Calendar.getInstance();
        expirationDate.setTime(new Date(LoginDataManager.getsInstance(getContext()).getShopKeeperExpirationTimeMillis()));

        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(new Date());

        long diffDays = (LoginDataManager.getsInstance(getContext()).getShopKeeperExpirationTimeMillis() - System.currentTimeMillis()) / (1000 * 60 * 60 * 24) + 1;

        if (diffDays <= 8) {
            if (diffDays <= 0) { // 到期了
                shopKeeperHasExpired((int)diffDays);
            } else { // 即将到期
                shopKeeperExpiringAtOnce((int)diffDays);
            }
        } else {
            llExpirationBar.setVisibility(View.GONE);
        }
    }

    private void shopKeeperHasExpired(int days) {
        int showCount = SpUtil.getIntValue("", "show_expired_count", getContext());
        if (showCount >= 4) return;

        if (showCount == 0) {
            showExpirationDialog(days);
        } else {
            setupExpirationBar();
            TextView expirationTipTV = mRootView.findViewById(R.id.tv_expiration_tip);
            expirationTipTV.setText("您的店主身份已到期，点击立即续费。");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("show_expired_count", showCount + 1);
        SpUtil.save("", data, getContext());
    }

    private void shopKeeperExpiringAtOnce(int days) {
        int showCount = SpUtil.getIntValue("", "show_at_once_expired_count", getContext());
        Log.i("jan3", "showCount = " + showCount);
        if (showCount >= 4) return;

        if (showCount == 0) {
            showExpirationDialog(days);
        } else {
            setupExpirationBar();
            TextView expirationTipTV = mRootView.findViewById(R.id.tv_expiration_tip);
            expirationTipTV.setText(String.format("您的店主身份还有%d天到期，点击立即续费。", days));
        }

        Map<String, Object> data = new HashMap<>();
        data.put("show_at_once_expired_count", showCount + 1);
        SpUtil.save("", data, getContext());
    }

    private void setupExpirationBar() {
        LinearLayout expirationBarLL = mRootView.findViewById(R.id.ll_expiration_bar);
        expirationBarLL.setVisibility(View.VISIBLE);
        expirationBarLL.setOnClickListener(v -> ARouter.getInstance()
                .build(ARouterPaths.SHOP_KEEPER_RENEW_ACTIVITY)
                .navigation(getActivity()));

        FrameLayout deleteFL = mRootView.findViewById(R.id.fl_delete);
        deleteFL.setOnClickListener(v -> expirationBarLL.setVisibility(View.GONE));
    }


    /**
     * 店主到期dialog
     * @param days
     */
    private void showExpirationDialog(int days) {
        BuildBean buildBean= DialogUIUtils.showExpirationDialog(getActivity(),null,days);
        buildBean.setmLevel(4);
        DialogManager.getInstance().pushToQueue(buildBean);
    }

    //---------------------------------刷新状态----------------------------------------


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginStateEvent(LoginStatusEvent event){
        Log.e("onLoginStateEvent",event.isLogin()+"");
        //登录成功之后，刷新会员页面数据
        resetModulesDataLoadedStatus();
        mRvScrollDistance = 0;
        mBinder.refresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVipLevelChanged(VipLevelChangedEvent event) {
        Log.e("onVipLevelChanged",event.toString());
        resetModulesDataLoadedStatus();
        mRvScrollDistance = 0;
        mBinder.refresh();
    }


    //---------------------------------打点函数----------------------------------------



    private void waterfallClickPoint(WaterfallBean data){
        HashMap<String,Object> sensorMap = new HashMap<>();
        if (data.getItemType() == WaterfallBean.TYPE_PRODUCT) {
            sensorMap.put("type", "商品");
            sensorMap.put("commodity_name",data.getProduct().getProductName());
            sensorMap.put("commodity_id", data.getProduct().getId());
        } else if (data.getItemType() == WaterfallBean.TYPE_ADVERTISE) {
            sensorMap.put("type", "广告");
        } else if (data.getItemType() == WaterfallBean.TYPE_SHOP) {
            sensorMap.put("type", "店铺");
        }
        SensorHelper.uploadTrack("feed", sensorMap);
    }

    private void tofuClickPoint(HomeModuleBean data,int position){
        if(position==0) return;
        HashMap<String,Object> sensorMap = new HashMap<>();
        sensorMap.put("position", position);
        sensorMap.put("name",data.getData().get(position-1).getTitle());
        SensorHelper.uploadTrack("ResourceState", sensorMap);
    }

    private void iconClickPoint(String title,int rank){
       HashMap<String,Object> sensorMap = new HashMap<>();
       sensorMap.put("name", title);
       sensorMap.put("rank", rank);
       SensorHelper.uploadTrack("navigation", sensorMap);
    }

     private void bannerClickPoint(String bannerName,long bannerId,String url,int rank){
         HashMap<String,Object> sensorMap = new HashMap<>();
         sensorMap.put("page_type", "首页");
         sensorMap.put("banner_name", bannerName);
         sensorMap.put("banner_id", bannerId);
         sensorMap.put("url", url);
         sensorMap.put("banner_rank", rank);
         SensorHelper.uploadTrack("BannerClick", sensorMap);
     }

    //-------------------------------生命周期-------------------------------



    @Override
    public void onResume() {
        super.onResume();
        dismissExpirationBarIfNeeded();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setResourceLoadServiceCallback();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbindResourceLoadService();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    //-----------------------------PTR回调---------------------

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        mHomeTitleBarLayout.setVisibility(View.VISIBLE);
        home_status_bar.setVisibility(View.VISIBLE);
        mMasque.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mHomeTitleBarLayout.setVisibility(View.INVISIBLE);
        home_status_bar.setVisibility(View.INVISIBLE);
        mMasque.setVisibility(View.INVISIBLE);
    }



    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        mRvScrollDistance = 0;
        resetModulesDataLoadedStatus();
        if(mBinder!=null){
            mBinder.refresh();
        }
        frame.postDelayed(() -> mPTR.refreshComplete(), 3000);
    }



    //----------------------------空实现-------------------------------------------------------

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
}
