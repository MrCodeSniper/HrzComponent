package com.mujirenben.android.discovery.mvp.ui.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mujirenben.android.common.base.BaseFragment;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.util.sensorHelper.SensorHelper;
import com.mujirenben.android.common.widget.DialogUtils;
import com.mujirenben.android.common.widget.StatusBarView;
import com.mujirenben.android.discovery.R;
import com.mujirenben.android.discovery.R2;
import com.orhanobut.logger.Logger;

import java.util.HashMap;

import butterknife.BindView;

public class DiscoveryFragment extends BaseFragment {

    @BindView(R2.id.tl_category)
    TabLayout mCategoryTL;

    @BindView(R2.id.vp_content)
    ViewPager mContentVP;

    @BindView(R2.id.status_bar_view)
    StatusBarView mStatusBarView;


    private String[] mCategoryTitles;
    private Fragment[] mFragments;
    private DialogUtils loading_dialog;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater,
                         @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        loading_dialog = new DialogUtils(getActivity(),R.layout.common_loading_toast,"");
        View view = inflater.inflate(R.layout.fragment_discovery, null);
        mContentVP = view.findViewById(R.id.vp_content);
        mCategoryTL = view.findViewById(R.id.tl_category);
        mStatusBarView = view.findViewById(R.id.status_bar_view);
        return view;
    }
    private boolean canLoad = true;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            delayInit();
            mStatusBarView.setBackgroundColor(0x00000000);
            mStatusBarView.adjustStatusBarColor(getActivity());
        }
    }

    //延迟加载数据

    private void delayInit() {
        if (!canLoad) {
            return;
        }
        showLoadDialog();
        setupTabLayoutAndViewPager();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    public void scrollToTop(){
        Logger.e(mContentVP.getCurrentItem() + "");
       switch (mContentVP.getCurrentItem()){
           case 0:
               RecommendFragment fragment = (RecommendFragment) mFragments[0];
               if (fragment != null){
                   fragment.scrollToTop();
               }
               break;
           case 1:
               MaterialFragment materialFragment = (MaterialFragment) mFragments[1];
               if (materialFragment != null){
                   materialFragment.scrollToTop();
               }
               break;
       }
    }

    private void setupTabLayoutAndViewPager() {
        Resources res = getResources();

        mCategoryTitles = new String[] {
                res.getString(R.string.category_recommend),
                res.getString(R.string.category_material)
        };

        mFragments = new Fragment[] {
                new RecommendFragment(),
                new MaterialFragment()
        };

        mContentVP.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mCategoryTitles[position];
            }

            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mCategoryTitles.length;
            }
        });

        mCategoryTL.setupWithViewPager(mContentVP);
        mCategoryTL.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mContentVP.setCurrentItem(tab.getPosition());

                HashMap<String,Object> sensorMap = new HashMap<>();
                sensorMap.put("name", mCategoryTitles[tab.getPosition()]);
                SensorHelper.uploadTrack("FindClick", sensorMap);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        canLoad=false;
        hideLoadDialog();


    }


    private void showLoadDialog(){
        if(loading_dialog!=null){
            loading_dialog.show();
        }
    }


    private void hideLoadDialog(){
        if(loading_dialog!=null){
            loading_dialog.hide();
        }
    }

}
