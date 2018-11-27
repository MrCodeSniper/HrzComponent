package com.mujirenben.android.mine.mvp.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.widget.tablayout.SlidingTabLayout;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;
import com.mujirenben.android.mine.mvp.ui.adapter.LazyFragmentPagerAdapter;
import com.mujirenben.android.mine.mvp.ui.fragment.MateriaWxCirclesFragment;
import com.mujirenben.android.mine.mvp.ui.fragment.MateriaWxFriendsFragment;
import com.mujirenben.android.mine.mvp.ui.view.LazyViewPager;

import java.util.ArrayList;

import butterknife.BindView;


@Route(path = ARouterPaths.MATERIA_LIST)
public class MaterialActivity extends BaseActivity {


    @BindView(R2.id.tv_back)
    ImageView tvBack;
    @BindView(R2.id.tv_titlebar)
    TextView tvTitlebar;
    @BindView(R2.id.rl_titlebar)
    RelativeLayout rlTitlebar;
    @BindView(R2.id.stl_select_materia)
    SlidingTabLayout stlSelectMateria;
    @BindView(R2.id.vp_materia)
    LazyViewPager vpMateria;


    private ArrayList<Fragment> fragments = new ArrayList<>();
    String[] mTitles = {"微信好友", "朋友圈"};
    private CustomLazyFragmentPagerAdapter pagerAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
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

        fragments.add(new MateriaWxFriendsFragment());
        fragments.add(new MateriaWxCirclesFragment());
        return R.layout.mine_activity_materia;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTitlebar.setText("必发素材");
        pagerAdapter = new CustomLazyFragmentPagerAdapter(getSupportFragmentManager());
        vpMateria.setOffscreenPageLimit(1);
        vpMateria.setAdapter(pagerAdapter);
        tvBack.setOnClickListener((View v) -> finish());
        stlSelectMateria.setViewPager(vpMateria, mTitles, this, fragments);
    }






    private  class CustomLazyFragmentPagerAdapter extends LazyFragmentPagerAdapter {

        private CustomLazyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(ViewGroup container, int position) {
            return fragments.get(position) ;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }




}
