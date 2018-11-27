package com.mujirenben.android.discovery.mvp.ui.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.bumptech.glide.Glide;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.widget.photos.PhotoView;
import com.mujirenben.android.discovery.R;

import java.io.File;
import java.util.ArrayList;
@Route(path = ARouterPaths.IMAGE_BROWSE_ACTIVITY)
public class ImageBrowseActivity extends BaseActivity {

    private ViewPager mImagesVP;
    private TextView mCurrentPageTV;

    private ArrayList<String> mImageUrls = new ArrayList<>();

    public static void launchImageBrowserActivity(Context context, ArrayList<String> imageUrls, int currentIndex) {
        Intent intent = new Intent(context, ImageBrowseActivity.class);
        intent.putStringArrayListExtra("image_urls", imageUrls);
        intent.putExtra("current_image_url_index", currentIndex);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(context,
                android.R.anim.fade_in,
                android.R.anim.fade_out);
        context.startActivity(intent, options.toBundle());
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        return R.layout.activity_image_browse;
    }

    @Override
    public void onBackPressed() {
        finishSelf();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getIntent().getBundleExtra("fromOther");
        int currentIndex;
        if(bundle != null){
            mImageUrls = bundle.getStringArrayList("image_urls");
            Log.e("mImageUrls",mImageUrls.toString());
            currentIndex = bundle.getInt("current_image_url_index", 0);
        }else {
            mImageUrls = getIntent().getStringArrayListExtra("image_urls");
            currentIndex = getIntent().getIntExtra("current_image_url_index", 0);
        }
        mImagesVP = findViewById(R.id.vp_images);
        mImagesVP.setAdapter(new ImagesPagerAdapter());
        mImagesVP.setCurrentItem(currentIndex);
        mImagesVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateCurrentPageInfo();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mCurrentPageTV = findViewById(R.id.tv_current_page);

        updateCurrentPageInfo();

        if(mCurrentPageTV.getText().toString().equals("1/1")){
            mCurrentPageTV.setVisibility(View.GONE);
        }else {
            mCurrentPageTV.setVisibility(View.VISIBLE);
        }
    }

    private void updateCurrentPageInfo() {
        String info = (mImagesVP.getCurrentItem() + 1) + "/" + mImageUrls.size();
        mCurrentPageTV.setText(info);
    }

    private void finishSelf() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    class ImagesPagerAdapter extends PagerAdapter {

        public ImagesPagerAdapter() {
        }

        @Override
        public int getCount() {
            return mImageUrls.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            PhotoView pv = new PhotoView(getApplicationContext());
            pv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishSelf();
                }
            });
            pv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            pv.enable();
            pv.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
            container.addView(pv);
            String url = mImageUrls.get(position);
            if(!TextUtils.isEmpty(url)){
                if(url.contains("http")){
                    //网络图片
                    Glide.with(ImageBrowseActivity.this).load(url).into(pv);
                }else {
                    //本地图片
                    File file = new File(url);
                    Glide.with(ImageBrowseActivity.this).load(file).into(pv);
                }
            }
            return pv;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (object instanceof View) {
                container.removeView((View)object);
            }
        }
    }
}
