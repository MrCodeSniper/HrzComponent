package com.mujirenben.android.mine.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mujirenben.android.common.base.BaseFragment;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.widget.JDHeadersView;
import com.mujirenben.android.common.widget.DialogUtils;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;
import com.mujirenben.android.mine.di.component.DaggerMateriaComponent;
import com.mujirenben.android.mine.di.module.MateriaModule;
import com.mujirenben.android.mine.mvp.contract.MateriaContract;
import com.mujirenben.android.mine.mvp.model.bean.MateriaCircleBean;
import com.mujirenben.android.mine.mvp.model.bean.MateriaFriendsBean;
import com.mujirenben.android.mine.mvp.presenter.MateriaPresenter;
import com.mujirenben.android.mine.mvp.ui.adapter.LazyFragmentPagerAdapter;
import com.mujirenben.android.mine.mvp.ui.adapter.MateriaFriendsAdapter;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

public class MateriaWxFriendsFragment extends BaseFragment<MateriaPresenter> implements MateriaContract.View,LazyFragmentPagerAdapter.Laziable {


    @BindView(R2.id.rv_materia)
    RecyclerView rvMateria;
    @BindView(R2.id.pull_view)
    JDHeadersView pullView;
    private DialogUtils toastUtil;

    private MateriaFriendsAdapter materiaAdapter;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMateriaComponent
                .builder()
                .appComponent(appComponent)
                .materiaModule(new MateriaModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment_materia_wx_friends, container, false);
        return view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        rvMateria.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        materiaAdapter = new MateriaFriendsAdapter(R.layout.mine_materia_item);
        rvMateria.setAdapter(materiaAdapter);
        RetrofitUrlManager.getInstance().putDomain("mock_category_search", "http://www.wanandroid.com");
        mPresenter.getMateriaDataFromFriends();

        pullView.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(()->pullView.refreshComplete(), 1000);
            }
        });

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showFriendsMateriaSuccess(MateriaFriendsBean data) {
        materiaAdapter.setNewData(data.itemInfoList.get(0).itemContentList);
    }

    @Override
    public void showCirclesMateriaSuccess(MateriaCircleBean data) {

    }

    @Override
    public void showFriendsMateriaFail() {

    }

    @Override
    public void showCirclesMateriaFail() {

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
