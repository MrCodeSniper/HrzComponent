package com.mujirenben.android.discovery.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mujirenben.android.common.base.BaseFragment;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.widget.BackToTopView;
import com.mujirenben.android.common.widget.HrzHeadersView;
import com.mujirenben.android.discovery.R;

import com.mujirenben.android.discovery.dagger.component.DaggerMaterialComponent;
import com.mujirenben.android.discovery.dagger.module.MaterialModule;
import com.mujirenben.android.discovery.mvp.contract.DiscoveryContract;
import com.mujirenben.android.discovery.mvp.model.entity.DiscoveryBean;
import com.mujirenben.android.discovery.mvp.presenter.DiscoveryPresenter;
import com.mujirenben.android.discovery.mvp.ui.adapter.MaterialAdapter;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class MaterialFragment extends BaseFragment<DiscoveryPresenter>
        implements DiscoveryContract.View, BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerView mMaterialContentRV;
    private View mDefaultPage;
    private HrzHeadersView mPTR;
    private MaterialAdapter mAdapter;
    private BackToTopView topView;

    private int mNextPageToLoad = 1;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMaterialComponent.builder()
                .appComponent(appComponent)
                .materialModule(new MaterialModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater,
                         @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_material, null);
        mMaterialContentRV = view.findViewById(R.id.rv_material_content);
        mPTR = view.findViewById(R.id.ptr);
        topView = view.findViewById(R.id.iv_to_top);
        topView.attathRecyclerView(mMaterialContentRV);
        mDefaultPage = view.findViewById(R.id.default_view_ll);
        ((TextView)mDefaultPage.findViewById(R.id.default_title_tv)).setText("暂无数据");
        ((TextView)mDefaultPage.findViewById(R.id.default_operate_tv)).setText("刷新");
        ((TextView)mDefaultPage.findViewById(R.id.default_operate_tv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNextPageToLoad = 1;
                mPresenter.loadMaterialData(false, mNextPageToLoad++, 10);
            }
        });
        return view;
    }

    public void scrollToTop(){
        if (mMaterialContentRV != null){
            mMaterialContentRV.smoothScrollToPosition(0);
        }

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mPTR.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mNextPageToLoad = 1;
                mPresenter.loadMaterialData(false, mNextPageToLoad++, 10);
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPTR.refreshComplete();
                    }
                }, 1000);
            }
        });

        mPresenter.loadMaterialData(false, mNextPageToLoad++, 10);

        List<DiscoveryBean.Data> data = new ArrayList<>();
        mMaterialContentRV.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        mAdapter = new MaterialAdapter(getActivity(), R.layout.recycle_view_item_discovery, data, mPresenter);
        mAdapter.setOnLoadMoreListener(this, mMaterialContentRV);
        mMaterialContentRV.setAdapter(mAdapter);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void onRecommendDataLoaded(DiscoveryBean data) {

    }

    @Override
    public void onMaterialDataLoaded(DiscoveryBean data) {
        Log.i(TAG, "onRecommendDataLoaded");
        if (data != null && data.getData() != null && data.getData().size() > 0) {
            mPTR.setVisibility(View.VISIBLE);
            mDefaultPage.setVisibility(View.GONE);

            mPTR.refreshComplete();
            mAdapter.setNewData(data.getData());
        } else {
            mPTR.setVisibility(View.GONE);
            mDefaultPage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMoreRecommendDataLoaded(DiscoveryBean data) {

    }

    @Override
    public void onMoreMaterialDataLoaded(DiscoveryBean data) {
        mAdapter.loadMoreComplete();
        if (data.getData() != null && data.getData().size() > 0) {
            mAdapter.addData(data.getData());
        } else {
            mAdapter.loadMoreEnd(false);
        }
        topView.triggle(mNextPageToLoad);
    }

    @Override
    public void onLoadDataError(String errorMsg) {
        ((TextView)mDefaultPage.findViewById(R.id.default_title_tv)).setText(errorMsg);
        mPTR.setVisibility(View.GONE);
        mDefaultPage.setVisibility(View.VISIBLE);
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
    public void onLoadMoreRequested() {
        mMaterialContentRV.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAdapter.getData().size() >= 800) {
                    mAdapter.loadMoreEnd(false);
                } else {
                    mPresenter.loadMaterialData(true, mNextPageToLoad++, 10);
                }
            }
        }, 1000);
    }
}
