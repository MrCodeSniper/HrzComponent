package com.mujirenben.android.vip.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gcssloop.widget.RCImageView;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.util.rxtools.RxTextTool;
import com.mujirenben.android.common.widget.DialogUtils;
import com.mujirenben.android.common.widget.HrzHeadersView;
import com.mujirenben.android.common.widget.HrzLoadMoreView;
import com.mujirenben.android.vip.R;
import com.mujirenben.android.vip.R2;
import com.mujirenben.android.vip.di.component.DaggerMyFanComponent;
import com.mujirenben.android.vip.di.module.MyFanModule;
import com.mujirenben.android.vip.mvp.contract.MyFanContract;
import com.mujirenben.android.vip.mvp.model.bean.FanHeaderInfo;
import com.mujirenben.android.vip.mvp.model.bean.FanListBean;
import com.mujirenben.android.vip.mvp.presenter.MyFanPresenter;
import com.mujirenben.android.vip.mvp.ui.adapter.FanAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

import static com.mujirenben.android.common.util.Preconditions.checkNotNull;

@Route(path = ARouterPaths.ACTIVITY_FAN)
public class MyFanActivity extends BaseActivity<MyFanPresenter> implements MyFanContract.View, HrzHeadersView.RefreshDistanceListener, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R2.id.tv_back)
    ImageView tvBack;
    @BindView(R2.id.tv_titlebar)
    TextView tvTitlebar;
    @BindView(R2.id.common_titlebar_right_iv)
    ImageView commonTitlebarRightIv;
    @BindView(R2.id.common_titlebar_right_tv)
    TextView commonTitlebarRightTv;
    @BindView(R2.id.rl_titlebar)
    RelativeLayout rlTitlebar;
    @BindView(R2.id.recyclerView)
    RecyclerView fanRecyclerView;
    @BindView(R2.id.hrz_header_view)
    HrzHeadersView hrzHeaderView;
    /**
     * 粉丝总览信息页面（headerView)
     */
    private FrameLayout fanOverviewLayout;
    private List<FanListBean.DataBean> fanDataList = new ArrayList<>();
    private FanAdapter mAdapter;
    /**
     * 请求类型：第一次请求
     */
    private Type reqType = Type.FIRST_LOAD;
    private LinearLayoutManager layoutManager;
    private DialogUtils dialog;
    private LinearLayout defaultLayout;
    /**推荐人的联系电话*/
    private String phoneNumber;

    private enum Type {
        FIRST_LOAD, REFRESH, LOAD_MORE
    }

    private int page = 1;
    private int pageSize = 10;
    private int DATA_ERROR_TYPE = 0;
    private int NET_WORK_ERROR_TYPE = 1;
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;
    RxPermissions rxPermissions;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyFanComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myFanModule(new MyFanModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
//        StatusBarUtil.setStatusBarWhite(this);
        if (SpUtil.getIsMIUI(this)){
            StatusBarUtil.getStatusBarHelper(this,R.color.white).into(this);
        }else {
            StatusBarUtil.setStatusBarWhite(this);
        }
        return R.layout.activity_my_fan;
    }

    private void initViewUI() {
        rxPermissions = new RxPermissions(this);
        tvTitlebar.setText(getResources().getString(R.string.fan_my_fans_text));
        tvTitlebar.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tvTitlebar.setTextColor(getResources().getColor(R.color.text_color));
        fanOverviewLayout = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.fan_header_layout, null);
        defaultLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.common_default_layout,null);
        tvBack.setOnClickListener(view -> finish());
        initRecyclerView();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        fanRecyclerView.setLayoutManager(layoutManager);
        fanRecyclerView.smoothScrollToPosition(0);
        fanRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    Logger.e("停止了开始加载");
                    Glide.with(MyFanActivity.this).resumeRequests();
                } else {
                    Glide.with(MyFanActivity.this).pauseRequests();
                }
            }
        });
        hrzHeaderView.setPullToRefresh(false);
        hrzHeaderView.setOnRefreshDistanceListener(this);
        hrzHeaderView.setPtrHandler(this);
        //设置适配器
        mAdapter = new FanAdapter(fanDataList);
        mAdapter.setLoadMoreView(new HrzLoadMoreView());
        mAdapter.setOnLoadMoreListener(this, fanRecyclerView);
        mAdapter.addHeaderView(fanOverviewLayout);
        fanRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
       dialog = new DialogUtils(this, R.layout.common_loading_toast, "");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyFanData(Type.FIRST_LOAD,1);
    }

    /**
     * @param type
     * @param pageNumber
     */
    private void getMyFanData(Type type,int pageNumber) {
        page = pageNumber;
        reqType = type;
        if(reqType == Type.LOAD_MORE){
           getFanListData(page);
        }else {
            //获取粉丝报表
            RetrofitUrlManager.getInstance().putDomain("mock_app_fan_info", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
            Map<String, String> infoMap = HttpParamUtil.getCommonSignParamMap(this, null);
            mPresenter.getMyFanInfo(infoMap);
            //获取粉丝列表
            getFanListData(page);
        }
    }

    /**
     * 获取粉丝列表
     * @param page
     */
    private void getFanListData(int page) {
        RetrofitUrlManager.getInstance().putDomain("mock_app_fans_list", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("page", page + "");
        map.put("pageSize", pageSize + "");
        Map<String, String> listMap = HttpParamUtil.getCommonSignParamMap(this, map);
        mPresenter.getMyFanList(listMap);
    }

    @Override
    public void showLoading() {
        if(reqType == Type.FIRST_LOAD){
            dialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (dialog != null) {
            dialog.hide();
        }
    }

    @Override
    public void showFanInfo(FanHeaderInfo headerInfo) {
       initFanInfo(headerInfo);
    }

    @Override
    public void showFanList(List<FanListBean.DataBean> dataList) {
        fanDataList = dataList;
        //int oldIndex = mAdapter.getItemCount() - 1;
        switch (reqType) {
            case FIRST_LOAD:
                // TODO: 2018/6/30 第一次加载没数据时候，显示没数据页面
                mAdapter.setNewData(dataList);
                mAdapter.notifyDataSetChanged();
                //如果列表没数据，则展示缺省页
                if (dataList.size() == 0) {
                    String msg = "您还没有粉丝呢，快去邀请吧";
                    fanListDataErrorLayout(msg);
                }
               hrzHeaderView.refreshComplete();
               //mAdapter.loadMoreComplete();
            case REFRESH:
                // TODO: 2018/6/30 下拉刷新没有数据，则不更新
                if (dataList.size() != 0) {
                    //隐藏缺省页面
                    fanDataList = dataList;
                    mAdapter.removeHeaderView(defaultLayout);
                    mAdapter.setNewData(dataList);
                   // mAdapter.notifyItemChanged(oldIndex < 0 ? 0 : oldIndex);
                    mAdapter.notifyDataSetChanged();
                }
                hrzHeaderView.refreshComplete();
//                mAdapter.loadMoreComplete();
//                mAdapter.loadMoreEnd();
                break;
            case LOAD_MORE:
                // TODO: 2018/6/30 加载更多
                mAdapter.addData(dataList);
               // mAdapter.notifyItemChanged(oldIndex < 0 ? 0 : oldIndex);
                mAdapter.notifyDataSetChanged();
                mAdapter.loadMoreComplete();
                if (dataList.size() == 0){
                    mAdapter.loadMoreEnd();
                }
                break;
        }
    }

    @Override
    public void fanInfoException(String msg) {
        initFanInfo(null);
        hrzHeaderView.refreshComplete();
        mAdapter.loadMoreComplete();
    }

    @Override
    public void fanListNetworkException(String msg) {
        showDefaultLayout(NET_WORK_ERROR_TYPE,msg,R.drawable.network_error_icon);
    }

    @Override
    public void fanListDataErrorLayout(String msg) {
        showDefaultLayout(DATA_ERROR_TYPE,msg,R.drawable.no_data_icon);
    }

    @Override
    public void showMessage(@NonNull String msg) {

    }
    /**
     * 展示粉丝报表的数据
     * @param headerInfo
     */
    private void initFanInfo(FanHeaderInfo headerInfo) {
        FanHeaderInfo.DataBean fhd = null;
        //是否有推荐人
        boolean hasRel = false;
        if(headerInfo != null){
            fhd = headerInfo.getData();
            hasRel = headerInfo.getData().isHasRel();
             phoneNumber =fhd.getPhone();
        }
        TextView  fanHeaderViewNormalFanTv = (TextView) findViewById(R.id.fan_header_view_normal_fan_tv);
        TextView fanHeaderViewCrownFanTv = (TextView) findViewById(R.id.fan_header_view_crown_fan_tv);
        TextView fanHeaderViewShopFanTv = (TextView) findViewById(R.id.fan_header_view_shop_fan_tv);
        TextView fanHeaderViewTotalTv = (TextView) findViewById(R.id.fan_header_view_total_tv);
        RCImageView fanHeaderReferrerAvatarIv = (RCImageView) findViewById(R.id.fan_header_referrer_avatar_iv);
        TextView fanHeaderReferrerNameTv = (TextView) findViewById(R.id.fan_header_referrer_name_tv);
        ImageView fanHeaderReferrerRecommendIv = (ImageView) findViewById(R.id.fan_header_referrer_recommend_iv);
        ImageView fanHeaderReferrerPhone = (ImageView) findViewById(R.id.fan_header_referrer_phone);
        TextView fanHeaderGetReferrerTv = (TextView) findViewById(R.id.fan_header_get_referrer_tv);

        //普通粉丝
        fanHeaderViewNormalFanTv.setText(fhd == null?"--":fhd.getLv0Count()+"");
        //皇冠粉丝
        fanHeaderViewCrownFanTv.setText(fhd == null?"--":fhd.getLv1Count() + "");
        //店长粉丝
        fanHeaderViewShopFanTv.setText(fhd == null?"--":fhd.getLv2Count() + "");
        String totalFansStr = getResources().getString(R.string.fan_total_text);
        //店铺总粉丝
        RxTextTool.getBuilder(totalFansStr, this)
                .append(" ")
                .append(fhd == null?"--":fhd.getTotalFans() + "")
                .setProportion(1.4f)
                .append(" ")
                .append("人")
                .into(fanHeaderViewTotalTv);
        //  mAdapter.addHeaderView(fanOverviewLayout);
        // fanRecyclerView.smoothScrollToPosition(0);
        if (!hasRel) {
            //如果没有推荐人，则引导用户去填写推荐人
            fanHeaderReferrerAvatarIv.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fanHeaderReferrerNameTv.getLayoutParams();
            layoutParams.leftMargin = ArmsUtils.dip2px(this,20f);
            fanHeaderReferrerNameTv.setText("我的推荐人");
            fanHeaderGetReferrerTv.setVisibility(View.VISIBLE);
            fanHeaderReferrerRecommendIv.setVisibility(View.GONE);
            fanHeaderReferrerPhone.setVisibility(View.GONE);
            String getRelText = getResources().getString(R.string.fan_get_referrer_text);
            RxTextTool.getBuilder(getRelText, this)
                    .setForegroundColor(Color.parseColor("#FFC9B07A"))
                    .setUnderline()
                    .into(fanHeaderGetReferrerTv);
            fanHeaderGetReferrerTv.setOnClickListener(v ->
                    ARouter.getInstance()
                            .build(ARouterPaths.INVITE_CODE_WRITING_ACTIVITY)
                            .navigation(MyFanActivity.this));
        } else {
            //有推荐人则显示推荐人的头像，名字
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) fanHeaderReferrerNameTv.getLayoutParams();
            layoutParams.leftMargin = ArmsUtils.dip2px(this,7f);
            fanHeaderGetReferrerTv.setVisibility(View.GONE);
            String referrerURL = fhd.getAvatarUrl();
            fanHeaderReferrerRecommendIv.setVisibility(View.VISIBLE);
            fanHeaderReferrerPhone.setVisibility(View.VISIBLE);
            fanHeaderReferrerAvatarIv.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(referrerURL)) {
                Glide.with(this).load(referrerURL).into(fanHeaderReferrerAvatarIv);
            }
            fanHeaderReferrerNameTv.setText(fhd.getNikeName() + "");
            fanHeaderReferrerNameTv.setTextColor(Color.parseColor("#FF333333"));

            fanHeaderReferrerPhone.setOnClickListener(v -> {
                rxPermissions.request(Manifest.permission.CALL_PHONE)
                        .subscribe(result -> {
                            if(result){
                                if(TextUtils.isEmpty(phoneNumber)){
                                    ArmsUtils.makeText(MyFanActivity.this,"暂无该用户的手机信息");
                                    return;
                                }
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNumber));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }else {
                                ArmsUtils.makeText(MyFanActivity.this,"授权之后，才能拨打电话哦~");
                            }
                        });
            });
        }
    }

    /**
     * 显示缺省页，网络错误，数据错误
     * @param errorType
     * @param msg
     * @param resId
     */
    public void showDefaultLayout(int errorType, String msg, int resId){
        if(reqType == Type.FIRST_LOAD){
            int statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
            int screenHeight = ArmsUtils.getScreenHeidth(this);
            int titleBarHeight = ArmsUtils.dip2px(this,49);
            //先调用这个方法，在获取宽高，否则获取不到宽高
            fanOverviewLayout.measure(0,0);
            int firstHeaderHeight = fanOverviewLayout.getMeasuredHeight();
            //当前视图的高 = 屏幕的高-状态栏的高-标题栏的高-第一个Headerview的高度
            int secondHeaderHeight = screenHeight - statusBarHeight-titleBarHeight-firstHeaderHeight;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,secondHeaderHeight);
            params.gravity= Gravity.CENTER;
            defaultLayout.setLayoutParams(params);
            mAdapter.removeHeaderView(defaultLayout);
            mAdapter.addHeaderView(defaultLayout);
            defaultLayout.setVisibility(View.VISIBLE);
            View defaultViewLl = defaultLayout.findViewById(R.id.default_view_ll);
            ImageView defaultIconIv = defaultLayout.findViewById(R.id.default_icon_iv);
            TextView defaultTitleTv = defaultLayout.findViewById(R.id.default_title_tv);
            TextView defaultTitleDesTv = defaultLayout.findViewById(R.id.default_title_des_tv);
            TextView defaultOperateTv = defaultLayout.findViewById(R.id.default_operate_tv);
            LinearLayout.LayoutParams titleParams = (LinearLayout.LayoutParams) defaultTitleTv.getLayoutParams();
            titleParams.topMargin = ArmsUtils.dip2px(this,25);
            LinearLayout.LayoutParams titleDesParams = (LinearLayout.LayoutParams) defaultTitleTv.getLayoutParams();
            titleDesParams.topMargin = ArmsUtils.dip2px(this,15);
            defaultOperateTv.setVisibility(View.GONE);

            defaultViewLl.setVisibility(View.VISIBLE);
            defaultIconIv.setImageResource(resId);
            defaultTitleTv.setText(msg);
            defaultTitleDesTv.setVisibility(View.GONE);
            if(errorType == DATA_ERROR_TYPE ){
                defaultTitleTv.setText("您还没有粉丝呢，快去邀请吧");
                defaultTitleDesTv.setVisibility(View.VISIBLE);
                defaultTitleDesTv.setText("去邀请");
                defaultTitleDesTv.setTextColor(getResources().getColor(R.color.golden_deep_color));
                defaultTitleDesTv.setOnClickListener(v ->
                    ARouter.getInstance()
                            .build(ARouterPaths.VIP_QR_CODE_ACTIVITY)
                            .navigation(MyFanActivity.this));
            }else if(errorType == NET_WORK_ERROR_TYPE){
                defaultTitleTv.setText(msg);
                defaultTitleDesTv.setVisibility(View.VISIBLE);
                defaultTitleDesTv.setTextColor(getResources().getColor(R.color.golden_deep_color));
                defaultTitleDesTv.setText("点击刷新");
                defaultTitleDesTv.setOnClickListener(v -> getMyFanData(Type.REFRESH,1) );
            }
        }else {
            mAdapter.loadMoreComplete();
            hrzHeaderView.refreshComplete();
            ArmsUtils.makeText(this,msg);
        }
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initViewUI();
    }

    @Override
    public void onPositionChange(int currentPosY) {

    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    /**
     * 刷新数据
     * @param frame
     */
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        if(mAdapter.isLoading()){
            hrzHeaderView.refreshComplete();
            return;
        }
        if (mAdapter != null) {
            getMyFanData(Type.REFRESH,1);
        }
    }

    /**
     * 加载更多回调
     */
    @Override
    public void onLoadMoreRequested() {
        fanRecyclerView.post(()->{
            page++;
            getMyFanData(Type.LOAD_MORE,page);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(mAdapter != null){
                    mAdapter.gotoCallPhone();
                }
            } else {
                ArmsUtils.makeText(MyFanActivity.this,"请同意系统权限后继续");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
