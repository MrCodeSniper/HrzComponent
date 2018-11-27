package com.mujirenben.android.vip.mvp.ui.fragment;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseFragment;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.bean.Const;
import com.mujirenben.android.common.entity.SearchResult;
import com.mujirenben.android.common.event.LoginStatusEvent;
import com.mujirenben.android.common.event.VipLevelChangedEvent;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.DeviceUtils;
import com.mujirenben.android.common.util.NetWorkUtils;
import com.mujirenben.android.common.widget.DialogUtils;
import com.mujirenben.android.common.widget.HrzLoadMoreView;
import com.mujirenben.android.common.widget.JDHeadersView;
import com.mujirenben.android.common.widget.LoadingDialog;
import com.mujirenben.android.common.widget.StatusBarView;
import com.mujirenben.android.common.widget.WaterfallDividerDecoration;

import com.mujirenben.android.vip.R;
import com.mujirenben.android.vip.R2;
import com.mujirenben.android.vip.di.component.DaggerVipComponent;
import com.mujirenben.android.vip.di.module.VipModule;
import com.mujirenben.android.vip.mvp.contract.VipContract;
import com.mujirenben.android.vip.mvp.model.bean.FanHeaderInfo;
import com.mujirenben.android.vip.mvp.model.bean.NoticeBean;
import com.mujirenben.android.vip.mvp.model.bean.VipBannerBean;
import com.mujirenben.android.vip.mvp.presenter.VipPresenter;
import com.mujirenben.android.vip.mvp.ui.adapter.SelfBussinessAdapter;
import com.mujirenben.android.vip.mvp.ui.fragment.state.ShopLoginState;
import com.mujirenben.android.vip.mvp.ui.fragment.state.UserLoginState;
import com.mujirenben.android.vip.mvp.ui.fragment.state.UserStateManager;
import com.mujirenben.android.vip.mvp.ui.fragment.state.UserUnLoginState;
import com.mujirenben.android.vip.mvp.ui.widget.VipRefreshLayout;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * @author sb pan
 */
public class VipFragment extends BaseFragment<VipPresenter> implements VipContract.View,
        BaseQuickAdapter.RequestLoadMoreListener, JDHeadersView.RefreshDistanceListener, PtrHandler {

    @BindView(R2.id.tv_back)
    ImageView tvBack;
    @BindView(R2.id.tv_titlebar)
    TextView tvTitlebar;
    @BindView(R2.id.rl_titlebar)
    RelativeLayout rlTitlebar;
    @BindView(R2.id.vip_recycler_view)
    RecyclerView vipRecyclerView;
    @BindView(R2.id.vip_refresh_layout)
    VipRefreshLayout vipRefreshLayout;
    @BindView(R2.id.vip_header_float_specific_tab_layout)
    View floatSpecTabLayout;
    @BindView(R2.id.nearby)
    TextView floatNearByTv;
    @BindView(R2.id.food)
    TextView floatFoodTv;
    @BindView(R2.id.dis)
    TextView floatDisTv;
    @BindView(R2.id.id_status_bar_view)
    StatusBarView mStatusBarView;
    @BindView(R2.id.id_status_bar_view_unlogin)
    StatusBarView mUnLoginStatusBarView;
    @Inject
    Application application;

    /**头部布局*/
    private View vipHeaderViewLayout;
    /**专卖店铺*/
    private View exclusiveLayout;
    /**headerView头部信息*/
    private FanHeaderInfo tempHeaderInfo;
    /**会员未登录ViewStub*/
    private ViewStub vipUnLoginViewStub;
    /** 会员登录ViewStub*/
    private ViewStub vipLoginViewStub;
    /**店主登录ViewStub*/
    private ViewStub vipShopLoginViewStub;
    /**会员未登录页面*/
    private View vipUnLoginLayout;
    /**会员已登录页面*/
    private View vipLoginLayout;
    /**店主登录页面*/
    private View vipShopLoginLayout;

    private static boolean isLoadFirst=false;

    private DialogUtils dialog;
    private Unbinder unbinder;
    private SelfBussinessAdapter mAdapter;

    /** 请求类型：第一次请求*/
    public static Type reqType = Type.FIRST_LOAD;
    private TextView vipLoginTitleBar;
    private View emptyView;
    private LinearLayout ll_discount;
    private ImageView ivSelect;
    public enum Type {
        /**第一次加载*/
        FIRST_LOAD,
        /**刷新数据*/
        REFRESH,
        /**加载数据*/
        LOAD_MORE
    }
    /**
     * 用户状态管理
     */
    private UserStateManager userStateManager;
    private UserUnLoginState userUnLoginState;
    private UserLoginState userLoginState;
    private ShopLoginState shopLoginState;
    int mDistance = 0;
    /**当距离在[0,255]变化时，透明度在[0,255之间变化]*/
    int maxDistance = 0;
    //请求接口参数
    private static int page=1;
    private int pageSize=10;

    public static VipFragment newInstance() {
        return new VipFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerVipComponent
                .builder()
                .appComponent(appComponent)
                .vipModule(new VipModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        maxDistance = (int)(180*getResources().getDisplayMetrics().density);
        emptyView=LayoutInflater.from(getActivity()).inflate(R.layout.common_default_layout, null);
        emptyView.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height= (int) DeviceUtils.dpToPixel(getActivity(),250);
        emptyView.setLayoutParams(params);
        return inflater.inflate(R.layout.vip_fragment, container, false);
    }

    private void setStatusBarColor(int color) {
        mStatusBarView.setBackgroundColor(color);
        mStatusBarView.adjustStatusBarColor(getActivity());
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if(!NetWorkUtils.isAvailable(getActivity())){
            mAdapter.setNewData(null);
            setStandByView(Consts.STAND_BY_VIEW.STATE_NETERROR,emptyView);
            return;
        }
    }

    private void requestSelfGoods(){
        RetrofitUrlManager.getInstance().putDomain("self_goods", Consts.PROTOCROL+Consts.OFFICIAL_SERVER);
        mPresenter.requestSearchGoodsData(getRequestParamMap());
    }

    //搜索结果列表的map
    private Map<String, String> getRequestParamMap() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("platform","1");
        params.put("page", page + "");//page
        params.put("pageSize", 10 + "");

        return HttpParamUtil.getResultMap(application,params);
    }

    /**
     * 获取
     * @param type
     * @param
     */
    private void getVipPageData(Type type, int page) {
        reqType = type;
        this.page=page;
       if(reqType == Type.LOAD_MORE){
           requestSelfGoods();
       }else {
           getVipUserInfo();
           requestSelfGoods();
       }
    }

    /**
     * 初始化ViewStub
     */
    private void initViewStub() {
        vipUnLoginViewStub = vipHeaderViewLayout.findViewById(R.id.vip_header_unLogin_view_stub);
        vipLoginViewStub = vipHeaderViewLayout.findViewById(R.id.vip_header_login_view_stub);
        vipShopLoginViewStub = vipHeaderViewLayout.findViewById(R.id.vip_header_shop_login_view_stub);
    }
    /**
     * 初始化视图
     */
    private void initViewUI() {
        dialog = new DialogUtils(getActivity(),R.layout.common_loading_toast,"");
        tvBack.setVisibility(View.GONE);
        tvTitlebar.setText(getActivity().getResources().getString(R.string.vip_text));
        tvTitlebar.setTextSize(18f);
        tvTitlebar.setTextColor(Color.BLACK);
//        会员字体加粗
        TextPaint paint = tvTitlebar.getPaint();
        paint.setFakeBoldText(true);

        ll_discount = exclusiveLayout.findViewById(R.id.ll_discount);
        ivSelect = exclusiveLayout.findViewById(R.id.iv_select);
        ll_discount.setOnClickListener((View v) -> {
            if (ivSelect.isSelected()) {
                ivSelect.setSelected(false);
            } else {
                ivSelect.setSelected(true);
            }
            LoadingDialog.getInstance(getActivity()).show();
            page = 1;
            getVipPageData(Type.FIRST_LOAD,1);
        });
        userIsLogin();
        intRecyclerView();
        adjustToolbar();
    }

    /**
     * 判断用户是否登录
     */
    private void userIsLogin() {
        //初始化用户状态管理者
        userStateManager = new UserStateManager();
        boolean isLogin = LoginDataManager.getsInstance(getActivity()).isLogin();
        //登录时候返回的用户级别
        int level = LoginDataManager.getsInstance(getActivity()).getLevel();
        //获取数据时候，返回的用户级别
        if(tempHeaderInfo != null && tempHeaderInfo.getData() != null){
            level = tempHeaderInfo.getData().getLv();
            long expiredDay = tempHeaderInfo.getData().getExpiredDay();
            //更新店主过期时间
            LoginDataManager.getsInstance(getActivity()).setShopKeeperExpirationTimeMillis(expiredDay);
            //设置会员级别
            LoginDataManager.getsInstance(getActivity()).setLevel(level);
        }
        Log.e("level",level+"");
        if (isLogin) {
            //用户已登录，初始化已登录状态，设置已登录页面,还需要区分登录的会员是店主还是普通会员
            //店主
            if (level >= Const.UserLevel.LEVEL_SHOP){
                if(vipShopLoginLayout == null){
                    vipShopLoginLayout = vipShopLoginViewStub.inflate();
                }
                //显示店主页面
                if(shopLoginState == null){
                    shopLoginState = new ShopLoginState(getActivity(),vipShopLoginLayout);
                }
                //隐藏普通粉丝、皇冠页面
                if(vipLoginLayout != null){
                    vipLoginLayout.setVisibility(View.GONE);
                }
                //隐藏未登录页面
                if(vipUnLoginLayout != null){
                    vipUnLoginLayout.setVisibility(View.GONE);
                }
                vipShopLoginLayout.setVisibility(View.VISIBLE);
                userStateManager.setState(shopLoginState);
            }else {
                //普通会员
                //隐藏未登录状态页面
                if (vipUnLoginLayout != null) {
                    vipUnLoginLayout.setVisibility(View.GONE);
                }

                //显示会员页面，粉丝，皇冠
                if(vipLoginLayout == null){
                    vipLoginLayout = vipLoginViewStub.inflate();
                }
                //隐藏店主页面
                if(vipShopLoginLayout != null){
                    vipShopLoginLayout.setVisibility(View.GONE);
                }
                //普通粉丝，皇冠
                if (userLoginState == null) {
                    userLoginState = new UserLoginState(getActivity(), vipLoginLayout);
                }
                vipLoginLayout.setVisibility(View.VISIBLE);
                userStateManager.setState(userLoginState);
            }
            vipLoginTitleBar.setVisibility(View.VISIBLE);
            mStatusBarView.setVisibility(View.VISIBLE);
            userStateManager.showUniqueInfoLayout(tempHeaderInfo);
            rlTitlebar.setVisibility(View.GONE);
            mUnLoginStatusBarView.setVisibility(View.GONE);
            return;
        }

        //用户未登录，初始化未登录状态，设置未登录页面
        if(vipUnLoginLayout == null){
            vipUnLoginLayout = vipUnLoginViewStub.inflate();
        }
        if (vipLoginLayout != null) {
            vipLoginLayout.setVisibility(View.GONE);
        }
        if(vipShopLoginLayout != null){
            vipShopLoginLayout.setVisibility(View.GONE);
        }
        if (userUnLoginState == null) {
            userUnLoginState = new UserUnLoginState(getActivity(), vipUnLoginLayout);
        }
        vipLoginTitleBar.setVisibility(View.GONE);
        mStatusBarView.setVisibility(View.GONE);
        vipUnLoginLayout.setVisibility(View.VISIBLE);
        rlTitlebar.setVisibility(View.VISIBLE);
        mUnLoginStatusBarView.setVisibility(View.VISIBLE);
        userStateManager.setState(userUnLoginState);
        userStateManager.showUniqueInfoLayout(tempHeaderInfo);
    }

    private void adjustToolbar() {
        if (LoginDataManager.getsInstance(getContext()).isLogin()) {
            float ratio = mDistance / (float)maxDistance;
            if (ratio > 1) {
                ratio = 1;
            } else if (ratio < 0) {
                ratio = 0;
            }

            int color = 0xffffff;
            int alpha = (int)(255 * ratio);

            int resultColor = alpha << 24 | color;
            setStatusBarColor(resultColor);
            vipLoginTitleBar.setAlpha(ratio);
        } else {
            mUnLoginStatusBarView.setBackgroundColor(Color.WHITE);
            mUnLoginStatusBarView.adjustStatusBarColor(getActivity());
        }
    }

    /**
     * 初始化列表
     */
    private void intRecyclerView() {
       // LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        vipRecyclerView.setLayoutManager(layoutManager);
        vipRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(getActivity()).resumeRequests();
                } else {
                    Glide.with(getActivity()).pauseRequests();
                }
            }

            /**
             * @param recyclerView
             * @param dx > 0 时为手指向左滚动,列表滚动显示右面的内容
             * @param dx < 0 时为手指向右滚动,列表滚动显示左面的内容
             * @param dy > 0 时为手指向上滚动,列表滚动显示下面的内容
             * @param dy < 0 时为手指向下滚动,列表滚动显示上面的内容
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                maxDistance = (int)(180*getResources().getDisplayMetrics().density);
                mDistance += dy;
//                Log.e("dyDistance: ",dy+"");
//                Log.e("maxDistance",maxDistance+"");
//                Log.e("mDistance",mDistance+"");
                adjustToolbar();
                // 展示隐藏tab, tab悬停
//                int[] location = new int[2];
//                innerSpecTabLayout.getLocationOnScreen(location);
//                int y = location[1];
//                int[] location2 = new int[2];
//                floatSpecTabLayout.getLocationOnScreen(location2);
//                int y2 = location2[1];
//                if (y <= y2) {
//                    floatSpecTabLayout.setVisibility(View.VISIBLE);
//                } else {
//                    floatSpecTabLayout.setVisibility(View.INVISIBLE);
//                }
            }
        });
        vipRefreshLayout.setPullToRefresh(false);
        vipRefreshLayout.setOnRefreshDistanceListener(this);
        vipRefreshLayout.setPtrHandler(this);
        mAdapter = new SelfBussinessAdapter(R.layout.product_item);

      //mAdapter = new VipRecycleAdapter(R.layout.vip_shop_item_layout, shopDataList);
        mAdapter.setOnLoadMoreListener(this, vipRecyclerView);
        mAdapter.setLoadMoreView(new HrzLoadMoreView());
        vipRecyclerView.setAdapter(mAdapter);
        vipRecyclerView.addItemDecoration(new WaterfallDividerDecoration(mAdapter,getActivity()));
        mAdapter.removeAllHeaderView();
        mAdapter.removeAllFooterView();
        mAdapter.addHeaderView(vipHeaderViewLayout);
        mAdapter.addHeaderView(exclusiveLayout);

       // ((LinearLayout.LayoutParams)mAdapter.getHeaderLayout().getChildAt(1).getLayoutParams()).setMargins(0,0,0,R.dimen.recycleview_marginbottom);
        userStateManager.showUniqueInfoLayout(tempHeaderInfo);
        mAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position) -> {

            Bundle bundle=new Bundle();
            bundle.putString(Consts.GOODS_ID_INTENT_STR,mAdapter.getData().get(position).getId()+"");
            bundle.putString(Consts.PLATFORM_ID_INTENT_STR,mAdapter.getData().get(position).getPlatform()+"");
            bundle.putString(Consts.ROUTER_FROM,"会员");
            ARouter.getInstance().build(ARouterPaths.GOODS_DETAIL)
                    .withBundle(Consts.HRZ_ROUTER_BUNDLE,bundle)
                    .navigation(getActivity());

        });
    }

    /**
     * 获取公告数据
     */
    private void getNoticeList() {
        RetrofitUrlManager.getInstance().putDomain("notice_list", Consts.PROTOCROL+Consts.OFFICIAL_SERVER);
        HashMap<String,String> map = HttpParamUtil.getCommonSignParamMap(getActivity(),null);
        mPresenter.getNoticeList(map);
    }

    /**
     * 获取轮播图
     * 只有在未登录的状态下获取数据
     */
    private void getVipBanner() {
        if (!LoginDataManager.getsInstance(getActivity()).isLogin()){
            RetrofitUrlManager.getInstance().putDomain("banner_list", Consts.PROTOCROL+Consts.OFFICIAL_SERVER);
            String versionCode =  HttpParamUtil.getClientVersionName(getActivity());
            HashMap<String,String> bannerMap = new HashMap<>();
            bannerMap.put("type","vip");
            bannerMap.put("platform",1+"");
            bannerMap.put("version",versionCode);
            mPresenter.getVipBanner(bannerMap);
        }
    }

    /**
     * 获取我的粉丝信息
     * 1、只有在登录的状态才获取粉丝报表
     * 2、若没有登录，则获取公告栏，和banner
     */
    private void getVipUserInfo(){
        if (LoginDataManager.getsInstance(getActivity()).isLogin()){
            //获取我的粉丝
            RetrofitUrlManager.getInstance().putDomain("mock_app_fan_info", Consts.PROTOCROL+Consts.OFFICIAL_SERVER);
            HashMap<String,String> map = HttpParamUtil.getCommonSignParamMap(getActivity(),null);
            mPresenter.getMyFanInfo(map);
        }else {
            getVipBanner();
        }
        //登录，未登录状态都有公告轮播，所以登录未登录都需要请求数据
        getNoticeList();
    }

    /**
     * 轮播图获取成功
     * @param bannerBean
     */
    @Override
    public void vipBannerOnSuccess(VipBannerBean bannerBean) {
        userStateManager.showBannerLayout(bannerBean);
    }

    /**
     * 轮播图获取失败
     * @param msg
     */
    @Override
    public void vipBannerOnFail(String msg) {
        ArmsUtils.makeText(getActivity(),msg);
    }

    /**
     * 公告栏获取成功
     * @param noticeBean
     */
    @Override
    public void noticeListOnSuccess(List<NoticeBean.DataBean> noticeBean) {
        userStateManager.showAnnounceLayout(noticeBean);
    }

    /**
     * 公告栏获取失败
     * @param msg
     */
    @Override
    public void noticeListOnFail(String msg) {
        ArmsUtils.makeText(getActivity(),msg);
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        vipRecyclerView.post(() -> {
            Logger.e("触发了加载更多，请求页数为:"+page);
            page++;
            reqType=Type.LOAD_MORE;
            getVipPageData(reqType,page);
        });
    }

    /**
     * 会员信息
     * @param headerInfo 头部信息
     */
    @Override
    public void showVipInfo(FanHeaderInfo headerInfo) {
        tempHeaderInfo = headerInfo;
        userIsLogin();
    }

    /**
     * 获取会员信息异常
     * @param msg
     */
    @Override
    public void vipInfoException(String msg) {
        hideLoading();
        ArmsUtils.makeText(getActivity(),msg);
        vipRefreshLayout.refreshComplete();
    }

    @Override
    public void showGoods(SearchResult data) {
        mAdapter.removeHeaderView(emptyView);
        mAdapter.setNewData(data.getData().getList());
        if(mAdapter.getData().size()<pageSize){
            mAdapter.loadMoreEnd(true);
        }else {
            mAdapter.loadMoreEnd(false);
            mAdapter.setNewData(data.getData().getList());
            mAdapter.loadMoreComplete();
        }
    }

    @Override
    public void searchResultNodata() {
        mAdapter.setNewData(null);
        setStandByView(Consts.STAND_BY_VIEW.STATE_NODATA,emptyView);
    }

    private void setStandByView(String type,View emptyView){
       if(type.equals(Consts.STAND_BY_VIEW.STATE_NETERROR)){
           ImageView default_icon_iv=emptyView.findViewById(R.id.default_icon_iv);
           default_icon_iv.setImageResource(R.drawable.network_error_icon);
           TextView default_title_tv=emptyView.findViewById(R.id.default_title_tv);
           default_title_tv.setText("当前网络异常");
           TextView default_operate_tv=emptyView.findViewById(R.id.default_operate_tv);
           default_operate_tv.setVisibility(View.VISIBLE);
           default_operate_tv.setText("点击刷新");
           default_operate_tv.setOnClickListener(v -> getVipPageData(Type.FIRST_LOAD,1));
           mAdapter.removeHeaderView(emptyView);
           mAdapter.addHeaderView(emptyView);
       }else if(type.equals(Consts.STAND_BY_VIEW.STATE_NETOUTTIME)){


       }else if(type.equals(Consts.STAND_BY_VIEW.STATE_NODATA)){
           ImageView default_icon_iv=emptyView.findViewById(R.id.default_icon_iv);
           default_icon_iv.setImageResource(R.drawable.no_data_icon);
           TextView default_operate_tv=emptyView.findViewById(R.id.default_operate_tv);
           default_operate_tv.setVisibility(View.INVISIBLE);
           TextView default_title_tv=emptyView.findViewById(R.id.default_title_tv);
           default_title_tv.setText("暂时没有自营商品哦");
           mAdapter.removeHeaderView(emptyView);
           mAdapter.addHeaderView(emptyView);
       }
    }

    @Override
    public void onPositionChange(int currentPosY) { }

    /**
     * 检查是否可以刷新
     * @param frame
     * @param content
     * @param header
     * @return
     */

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    /**
     * 开始刷新
     * @param frame
     */
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        page=1;
        if (mPresenter != null) {
            getVipPageData(Type.REFRESH,1);
        }
    }

    @Override
    public void setData(@Nullable Object data) { }

    @Override
    public void showLoading() {
        if(reqType == Type.FIRST_LOAD){
            dialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if(dialog != null){
            dialog.hide();
        }
        vipRefreshLayout.refreshComplete();
    }

    @Override
    public void showMessage(@NonNull String message) {
        if(dialog != null){
            dialog.hide();
        }
        vipRefreshLayout.refreshComplete();
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        vipHeaderViewLayout = LayoutInflater.from(getActivity()).inflate(R.layout.vip_header_view_layout, null);
        exclusiveLayout = LayoutInflater.from(getActivity()).inflate(R.layout.vip_header_exclusive_layout, null);
        vipLoginTitleBar = rootView.findViewById(R.id.vip_login_title_bar);
        unbinder = ButterKnife.bind(this, rootView);
        initViewStub();
        initViewUI();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Glide.with(this).pauseRequests();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginStateEvent(LoginStatusEvent event){
        Log.e("onLoginStateEvent",event.isLogin()+"");
        //登录成功之后，刷新会员页面数据
        userIsLogin();
        mDistance=0;
        adjustToolbar();
        getVipPageData(Type.REFRESH,1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVipLevelChanged(VipLevelChangedEvent event) {
        Log.e("onVipLevelChanged",event.toString());
        mDistance=0;
        adjustToolbar();
        getVipPageData(Type.REFRESH,1);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            if(!isLoadFirst){
                delayInit();
                isLoadFirst=true;
            }
            adjustToolbar();
        }
    }

    //延迟加载数据
    private void delayInit() {
        getVipPageData(Type.FIRST_LOAD,1);
    }
}
