package com.mujirenben.android.mine.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.widget.DialogUtils;
import com.mujirenben.android.common.widget.HrzHeadersView;
import com.mujirenben.android.common.widget.HrzLoadMoreView;

import com.mujirenben.android.mine.di.component.DaggerUserRequstComponent;
import com.mujirenben.android.mine.di.module.UserRequestModule;
import com.mujirenben.android.mine.mvp.contract.WithdrawRecordContract;
import com.mujirenben.android.mine.mvp.model.bean.WithdrawListBean;
import com.mujirenben.android.mine.mvp.presenter.WithdrawRecordPresenter;
import com.mujirenben.android.mine.mvp.ui.adapter.WithdrawRecordAdapter;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

@Route(path = ARouterPaths.WITHDRAW_RECORD)
public class WithdrawRecordActivity extends BaseActivity<WithdrawRecordPresenter> implements
        WithdrawRecordContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R2.id.tv_back)
    ImageView tvBack;
    @BindView(R2.id.tv_titlebar)
    TextView tvTitlebar;
    @BindView(R2.id.rl_titlebar)
    RelativeLayout rlTitlebar;
    @BindView(R2.id.rv_withdraw_record)
    RecyclerView rvWithdrawRecord;
    @BindView(R2.id.pull_view)
    HrzHeadersView pullView;
    @BindView(R2.id.default_icon_iv)
    ImageView defaultIconIv;
    @BindView(R2.id.default_title_tv)
    TextView defaultTitleTv;
    @BindView(R2.id.default_title_des_tv)
    TextView defaultTitleDesTv;
    @BindView(R2.id.default_operate_tv)
    TextView defaultOperateTv;
    @BindView(R2.id.default_view_ll)
    LinearLayout defaultViewLl;
    private DialogUtils toastUtil;
    private WithdrawRecordAdapter mAdapter;
    private int page = 1;
    private int pageSize = 20;
    /**
     * 请求类型：第一次请求
     */
    private Type reqType = Type.FIRST_LOAD;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private enum Type {
        FIRST_LOAD, REFRESH, LOAD_MORE
    }
    private int DATA_ERROR_TYPE = 0;
    private int NET_WORK_ERROR_TYPE = 1;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserRequstComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .userRequestModule(new UserRequestModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        return R.layout.mine_activity_withdraw_record;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTitlebar.setText("提现记录");
        defaultViewLl.setVisibility(View.GONE);
        tvBack.setOnClickListener((View v) -> finish());
        pullView.setPtrHandler(this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvWithdrawRecord.setLayoutManager(layoutManager);
        mAdapter = new WithdrawRecordAdapter(R.layout.mine_item_withdraw_record);
        mAdapter.setLoadMoreView(new HrzLoadMoreView());
        mAdapter.setOnLoadMoreListener(this, rvWithdrawRecord);
        rvWithdrawRecord.setAdapter(mAdapter);
        getWithdrawRecord(Type.FIRST_LOAD,1);
    }

    /**
     * 获取数据
     * @param type
     * @param pageNumber
     */
    private void getWithdrawRecord(Type type, int pageNumber) {
        reqType = type;
        page = pageNumber;
        RetrofitUrlManager.getInstance().putDomain("mock_app_withdraw_Record", Consts.PROTOCROL + Consts.OFFICIAL_SERVER);
        HashMap<String, String> pageMap = new HashMap<>();
        pageMap.put("page", page + "");
        pageMap.put("pageSize", pageSize + "");
        HashMap<String, String> recordMap = HttpParamUtil.getCommonSignParamMap(this, pageMap);
        mPresenter.getWithdrawRecordData(recordMap);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showWithdrawRecordSuccess(List<WithdrawListBean.DataBean> dataList) {
        int oldIndex = mAdapter.getItemCount() - 1;
        switch (reqType) {
            case FIRST_LOAD:
                // TODO: 2018/6/30 第一次加载没数据时候，显示没数据页面
                if (dataList.size() != 0) {
                    defaultViewLl.setVisibility(View.GONE);
                    mAdapter.setNewData(dataList);
                    //mAdapter.notifyItemChanged(oldIndex < 0 ? 0 : oldIndex);
                    mAdapter.notifyDataSetChanged();
                    rvWithdrawRecord.smoothScrollToPosition(0);
                    return;
                }
                String msg = getResources().getString(R.string.withdraw_no_record_text);
                showDataErrorLayout(msg);
                pullView.refreshComplete();
            case REFRESH:
                // TODO: 2018/6/30 下拉刷新没有数据，则不更新
                if (dataList.size() != 0) {
                    defaultViewLl.setVisibility(View.GONE);
                    mAdapter.setNewData(dataList);
                    mAdapter.notifyDataSetChanged();
                   // mAdapter.notifyItemChanged(oldIndex < 0 ? 0 : oldIndex);
                }
                pullView.refreshComplete();
                break;
            case LOAD_MORE:
                // TODO: 2018/6/30 加载更多
                mAdapter.addData(dataList);
                mAdapter.notifyDataSetChanged();
               // mAdapter.notifyItemChanged(oldIndex < 0 ? 0 : oldIndex);
                mAdapter.loadMoreComplete();
                if (dataList == null || dataList.size() == 0){
                    mAdapter.loadMoreEnd();
                }
                break;
        }
        hideLoading();
    }

    @Override
    public void showWithdrawRecordFail() {
        mAdapter.loadMoreComplete();
        pullView.refreshComplete();
    }

    @Override
    public void showLoading() {
        if (toastUtil == null) {
            toastUtil = new DialogUtils(getActivity(), R.layout.common_loading_toast, "");
        }
        if (reqType == Type.FIRST_LOAD) {
            toastUtil.show();
        }
    }

    @Override
    public void hideLoading() {
        if (toastUtil != null) {
            toastUtil.hide();
        }
    }

    @Override
    public void showDataErrorLayout(String msg) {
        showDefaultLayout(DATA_ERROR_TYPE,msg,R.drawable.no_data_icon);
    }

    @Override
    public void showMessage(@NonNull String message) {
        showDefaultLayout(NET_WORK_ERROR_TYPE,message,R.drawable.network_error_icon);
    }

    /**
     * 显示缺省页，网络错误，数据错误
     * @param errorType
     * @param msg
     * @param resId
     */
    public void showDefaultLayout(int errorType, String msg, int resId){
        if(reqType == Type.FIRST_LOAD){
            defaultViewLl.setVisibility(View.VISIBLE);
            defaultIconIv.setImageResource(resId);
            defaultTitleTv.setText(msg);
            defaultTitleDesTv.setVisibility(View.GONE);
           if(errorType == NET_WORK_ERROR_TYPE){
                defaultOperateTv.setText("刷新试试");
                defaultOperateTv.setOnClickListener(v -> getWithdrawRecord(Type.REFRESH,1));
            }else {
               defaultOperateTv.setVisibility(View.GONE);
           }
        }else {
            ArmsUtils.makeText(this,msg);
        }
        pullView.refreshComplete();
        mAdapter.loadMoreComplete();
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        if(mAdapter.isLoading()){
            pullView.refreshComplete();
            return;
        }
        if(mAdapter != null){
            getWithdrawRecord(Type.REFRESH,1);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        pullView.post(()->{
            page++;
            getWithdrawRecord(Type.LOAD_MORE,page);
        });
    }
}
