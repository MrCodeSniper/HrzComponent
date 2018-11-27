package com.mujirenben.android.vip.mvp.ui.fragment.state;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.arounter.HrzRouter;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.util.sensorHelper.SensorHelper;
import com.mujirenben.android.common.widget.indicatorLayout.IndicatorLayout;
import com.mujirenben.android.common.widget.switcher.AdvTextSwitcher;
import com.mujirenben.android.common.widget.switcher.Switcher;
import com.mujirenben.android.vip.R;
import com.mujirenben.android.vip.mvp.model.bean.FanHeaderInfo;
import com.mujirenben.android.vip.mvp.model.bean.NoticeBean;
import com.mujirenben.android.vip.mvp.model.bean.VipBannerBean;
import com.mujirenben.android.vip.mvp.model.bean.VipHeaderInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * @author: panrongfu
 * @date: 2018/7/17 9:49
 * @describe: 用户未登录状态
 */
public class UserUnLoginState implements UserState {

    /**上下文*/
    public Context mContext;

    /**会员未登录页面*/
    private View vipUnLoginLayout;

    /**会员未登录ViewStub*/
    /**公告轮播控制器*/
    private Switcher switcher;
    /**公告轮播栏*/
    private AdvTextSwitcher textSwitcher;
    /**banner轮播*/
    public BGABanner bgaBanner;
    /**指示器*/
    public IndicatorLayout indicatorLayout;

    //拉新活动
    private View vipHeaderPullNewActivityIv;

    private ImageView shareLayoutIv;
    public UserUnLoginState(Context context, View vipUnLoginLayout) {
        this.mContext = context;
        this.vipUnLoginLayout = vipUnLoginLayout;
        initViewUI();
    }

    /**
     * 根据当前的对象是UserLoginState或UserUnLoginState
     * 做不同的控件初始化
     */
    private void initViewUI() {
        switcher = new Switcher();
        vipUnLoginLayout.setVisibility(View.VISIBLE);
        bgaBanner = vipUnLoginLayout.findViewById(R.id.vip_header_unLogin_banner);
        textSwitcher = vipUnLoginLayout.findViewById(R.id.text_switcher);
        indicatorLayout = vipUnLoginLayout.findViewById(R.id.vip_header_unLogin_indicatorLayout);
        shareLayoutIv = vipUnLoginLayout.findViewById(R.id.vip_header_unLogin_share_iv);
        shareLayoutIv.setOnClickListener(v -> {
            ARouter.getInstance()
                    .build(ARouterPaths.LOGIN_MAIN_MINE)
                    .withString(Consts.LOGIN_SOURCE_KEY,"会员")
                    .navigation(mContext);
        });
        vipHeaderPullNewActivityIv = vipUnLoginLayout.findViewById(R.id.vip_header_pull_new_activity_iv);
        vipHeaderPullNewActivityIv.setOnClickListener(v -> {
            ARouter.getInstance()
                    .build(ARouterPaths.LOGIN_MAIN_MINE)
                    .withString(Consts.LOGIN_SOURCE_KEY,"活动运营")
                    .navigation(mContext);
        });
    }
    /**
     * 展示未登录会员独有的信息
     * 1)员未登录个人信息
     * @param fanHeaderInfo vip信息对象
     */
    @Override
    public void showUniqueInfoLayout(FanHeaderInfo fanHeaderInfo) {
        // TODO: 2018/7/17 未登录会员页面

    }

    @Override
    public void intAnnounceLayout(List<NoticeBean.DataBean> noticeList) {
        if(noticeList !=null && noticeList.size() > 0){
            String[] tips = new String[noticeList.size()];
            for(int i = 0; i<noticeList.size(); i++){
                tips[i] = noticeList.get(i).getNotice();
            }
            textSwitcher.clearTips();
            textSwitcher.setTpis(tips);
            switcher.pause();
            switcher.setAdvTsView(textSwitcher);
            //注意这里的单位是秒！秒！秒！秒！秒！
            switcher.setDuration(5);
            switcher.start();
            textSwitcher.setOnTipClickListener(position -> {});
        }
    }

    /**
     * 轮播图
     * @param bannerBean
     */
    @Override
    public void showBannerLayout(VipBannerBean bannerBean) {
        if(bannerBean == null || bannerBean.getData() == null )return;
        int itemSize = bannerBean.getData().size();
        //每次调用前，需把之前的view移除
        indicatorLayout.removePointView();
        indicatorLayout.setItemSize(itemSize);

        bgaBanner.setPageChangeDuration(5000);
        List<String> bannerListStr = new ArrayList<>();
        List<VipBannerBean.DataBean> bannerList = bannerBean.getData();
        for (VipBannerBean.DataBean bean: bannerList) {
            bannerListStr.add(bean.getImg());
        }
        if(itemSize <= 1 ){
            bgaBanner.setAutoPlayAble(false);
            indicatorLayout.setVisibility(View.GONE);
        }else {
            //设置第一张图片为选中状态
            indicatorLayout.setCurrentItemPosition(0);
            bgaBanner.setAutoPlayAble(true);
            indicatorLayout.setVisibility(View.VISIBLE);
        }
        bgaBanner.setData(R.layout.vip_header_banner_content, bannerListStr, null);
        bgaBanner.setDelegate((banner, itemView, url, position) ->{
            String clickURL = bannerList.get(position).getUrl();
            String title = bannerList.get(position).getTitle();
            HrzRouter.getsInstance(mContext).navigation(clickURL);
            HashMap<String,Object> trackMap = new HashMap<>();
            trackMap.put("page_type","会员未登录");
            trackMap.put("banner_name",title);
            trackMap.put("url",clickURL);
            trackMap.put("banner_rank",position);
            SensorHelper.uploadTrack("BannerClick",trackMap);
        });
        bgaBanner.setAdapter((banner, itemView, url, position) -> {
            ImageView targetView = itemView.findViewById(R.id.vip_header_banner_iv);
            if(url == null) return;
            Glide.with(mContext).load(url).into(targetView);

        });
        bgaBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
              //  Log.e("indicatorLayout",position+"");
                if(itemSize == 1) return;
                indicatorLayout.setCurrentItemPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean loginViewHidden, boolean unLoginViewHidden) {
        if(unLoginViewHidden){
            if(switcher != null){
                switcher.pause();
            }
        }
    }
}
