package com.mujirenben.android.home.mvp.ui.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.ch.android.resource.HrzResourceManagerImpl;
import com.ch.android.resource.IHrzResourceManager;
import com.ch.android.resource.controller.data_structure.HomeData;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.LogUtil;
import com.mujirenben.android.common.util.Logger;
import com.mujirenben.android.common.widget.campaignview.TimeLimitViewForHome;
import com.mujirenben.android.home.R;
import com.mujirenben.android.home.mvp.model.entity.Constant;
import com.mujirenben.android.home.mvp.ui.widget.HrzRecycleView;
import com.mujirenben.android.home.mvp.ui.widget.JDHeaderView;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;


/**
 * @author admin 数据绑定未进行详细的数据验证，再实际使用中不可取
 */
@Deprecated
public class HomeMultipleRecycleAdapter extends BaseMultiItemQuickAdapter<HomeData.ItemInfoListBean, BaseViewHolder> implements BaseQuickAdapter.SpanSizeLookup, BaseQuickAdapter.OnItemChildClickListener {

    private CountDownTimer timer;
    private int maxHasLoadPosition;
    private Context context;
    private TimeLimitViewForHome timeLimitViewForHome;
    private JDHeaderView ptrFrameLayout;
    List<HomeData.ItemInfoListBean> datas;
    RecyclerView.RecycledViewPool pool;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架
    private HrzRecycleView recyclerView;
    private HomeWaterfallAdapter mAdapter;
    private RequestLoadMoreListener requestLoadMoreListener;


    public HomeMultipleRecycleAdapter(List<HomeData.ItemInfoListBean> data, Context context, JDHeaderView ptrFrameLayout,RecyclerView.RecycledViewPool pool,RequestLoadMoreListener loadMoreListener) {
        super(data);
        this.datas=data;
     //   this.pool=pool;
        this.ptrFrameLayout=ptrFrameLayout;
        this.context=context;
        this.mContext=context;
        this.requestLoadMoreListener=loadMoreListener;
        setSpanSizeLookup(this);
//        mAppComponent = ArmsUtils.obtainAppComponentFromContext(mContext);
//        mImageLoader = mAppComponent.imageLoader();
        addItemType(Constant.TYPE_TOP_BANNER, R.layout.homerecycle_item_top_banner);
        addItemType(Constant.TYPE_ICON_LIST, R.layout.homerecycle_item_icon_list);
        addItemType(Constant.TYPE_NEW_USER, R.layout.homerecycle_item_toufu);
        addItemType(Constant.TYPE_SHOW_EVENT_3, R.layout.homerecycle_item_show_event_3);
        addItemType(Constant.TYPE_FIND_GOOD_SHOP, R.layout.homerecycle_item_type_find_good_shop);
    }
//
//    private boolean isScrolling=false;
//    public void setScrolling(boolean scrolling){
//        isScrolling=scrolling;
//    }
//
//    public boolean isScrolling() {
//        return isScrolling;
//    }
//
//    public void stopCountDown(){
//        timeLimitViewForHome.stop();
//    }
//
//    public void resetMaxHasLoadPosition() {
//        maxHasLoadPosition = 0;
//    }



    @Override
    protected void convert(BaseViewHolder helper, HomeData.ItemInfoListBean item) {
        if ("topBanner".equals(item.itemType)) {
            bindTopBannerData(helper, item);
        } else if ("iconList".equals(item.itemType)) {
            bindIconListData(helper, item);
        } else if ("newUser".equals(item.itemType)) {
            bindNewUserData(helper, item);
        } else if ("showEvent".equals(item.itemType)) {
            bindShowEventData(helper, item);
        } else if ("findGoodShop".equals(item.itemType)) {
            bindFindGoodShopData(helper, item);
        }
    }

        /**
         * 绑定banner数据
         *
         * @param helper
         * @param item
         *
         */
    private void bindTopBannerData(BaseViewHolder helper, final HomeData.ItemInfoListBean item) {
        BGABanner banner = helper.getView(R.id.banner);
        banner.setData(R.layout.homerecycle_top_banner_content, item.itemContentList, null);
        banner.setDelegate(new BGABanner.Delegate<View, HomeData.ItemInfoListBean.ItemContentListBean>() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, HomeData.ItemInfoListBean.ItemContentListBean model, int position) {
                ArmsUtils.makeText(itemView.getContext(), "" + item.itemContentList.get(position).clickUrl);
                if(position==0){
                    //显示dialog并旋转
//                    ToastUtil toastUtil=new ToastUtil(mContext,R.layout.common_loading_toast,"测试文本");
//                    toastUtil.show(15000);
//                    ARouter.getInstance().build(ARouterPaths.TEST_USER_LIST).navigation();
                }else if(position==1){
                    ARouter.getInstance().build(ARouterPaths.ORDER_INFO).navigation();
                }else if(position==2){//加载apk中的资源

//                    String apkPath =     Environment.getExternalStorageDirectory()+"/resources.apk";
//                    String assetsPath="resources.apk";
//                    Resources resources = BundlerResourceLoader.getBundleResource(context,apkPath);
//                    Drawable drawable = resources.getDrawable(resources.getIdentifier("apk_resource", "drawable",
//                            "com.hrz.resource"));
//                    Logger.e(drawable.hashCode()+"&&&!!!");
                }
            }
        });
        banner.setAdapter(new BGABanner.Adapter<View, HomeData.ItemInfoListBean.ItemContentListBean>() {
            @Override
            public void fillBannerItem(BGABanner banner, View itemView, HomeData.ItemInfoListBean.ItemContentListBean model, int position) {
                ImageView simpleDraweeView = (ImageView) itemView.findViewById(R.id.sdv_item_fresco_content);

                Glide.with(simpleDraweeView).load(model.imageUrl).into(simpleDraweeView);

//              mImageLoader.loadImage(itemView.getContext(),
//                        ImageConfigImpl
//                                .builder()
//                                .placeholder(R.color.bg_placeholder)
//                                .isCrossFade(true)
//                                .errorPic(R.drawable.load_error)
//                                .url(model.imageUrl)
//                                .imageView(simpleDraweeView)
//                                .build());
            }
        });
        ptrFrameLayout.setViewPager(banner);
    }


    public HomeWaterfallAdapter getmAdapter() {
        return mAdapter;
    }

    private void bindFindGoodShopData(BaseViewHolder helper, HomeData.ItemInfoListBean item) {
        recyclerView=helper.getView(R.id.recyclerView_waterfall);
        recyclerView.setRecycledViewPool(pool);
        StaggeredGridLayoutManager recyclerViewLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
//        mAdapter=new HomeWaterfallAdapter(item.itemContentList,mContext);
//        mAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position)->{
//                ARouter.getInstance().build(ARouterPaths.GOODS_DETAIL).navigation(mContext);
//        });

//        mAdapter.setEnableLoadMore(true);
//        mAdapter.setOnLoadMoreListener(requestLoadMoreListener,recyclerView);
        recyclerView.setAdapter(mAdapter);
//        recyclerView.setNestedScrollingEnabled(false);
    }


    private void bindShowEventData(BaseViewHolder helper, HomeData.ItemInfoListBean item) {
           // Glide.with(context).load(item.itemContentList.get(0).imageUrl).into(((ImageView)helper.getView(R.id.iv_newuser_register)));
        IHrzResourceManager mgr = HrzResourceManagerImpl.getDefault(mContext);
        mgr.loadBitmapForImageView(item.itemContentList.get(0).imageUrl, (ImageView)helper.getView(R.id.iv_newuser_register));
    }



    private void bindNewUserData(BaseViewHolder helper, HomeData.ItemInfoListBean item) {


        IHrzResourceManager mgr = HrzResourceManagerImpl.getDefault(mContext);
        mgr.loadBitmapForImageView(item.itemContentList.get(0).imageUrl, (ImageView)helper.getView(R.id.iv_toufu_one));
        mgr.loadBitmapForImageView(item.itemContentList.get(1).imageUrl, (ImageView)helper.getView(R.id.iv_toufu_two));
        mgr.loadBitmapForImageView(item.itemContentList.get(2).imageUrl, (ImageView)helper.getView(R.id.iv_toufu_three));
        mgr.loadBitmapForImageView(item.itemContentList.get(3).imageUrl, (ImageView)helper.getView(R.id.iv_toufu_four));


            ((TextView)helper.getView(R.id.tv_tofu_one)).setText(item.itemContentList.get(0).itemTitle);
            ((TextView)helper.getView(R.id.tv_hotsale)).setText(item.itemContentList.get(1).itemTitle);
            ((TextView)helper.getView(R.id.tv_buyticket)).setText(item.itemContentList.get(2).itemTitle);
            ((TextView)helper.getView(R.id.tv_super_quality)).setText(item.itemContentList.get(3).itemTitle);

            timeLimitViewForHome =(TimeLimitViewForHome)helper.getView(R.id.miaosha_view);
            timeLimitViewForHome.setTime(100,19,20);
            timeLimitViewForHome.start();

    }

    private void bindIconListData(BaseViewHolder helper, HomeData.ItemInfoListBean item) {

            IHrzResourceManager mgr = HrzResourceManagerImpl.getDefault(mContext);
        LogUtil.i("jan", "bindIconListData");
//        ((ImageView)helper.getView(R.id.iv_menu_icon_one)).setImageBitmap(mgr.getBitmapForUri(item.itemContentList.get(0).imageUrl));
            mgr.loadBitmapForImageView(item.itemContentList.get(0).imageUrl, (ImageView)helper.getView(R.id.iv_menu_icon_one));
        mgr.loadBitmapForImageView(item.itemContentList.get(1).imageUrl, (ImageView)helper.getView(R.id.iv_menu_icon_two));
        mgr.loadBitmapForImageView(item.itemContentList.get(2).imageUrl, (ImageView)helper.getView(R.id.iv_menu_icon_three));
        mgr.loadBitmapForImageView(item.itemContentList.get(3).imageUrl, (ImageView)helper.getView(R.id.iv_menu_icon_four));
        mgr.loadBitmapForImageView(item.itemContentList.get(4).imageUrl, (ImageView)helper.getView(R.id.iv_menu_icon_five));
//            Glide.with(context).load(item.itemContentList.get(0).imageUrl).into(((ImageView)helper.getView(R.id.iv_menu_icon_one)));
//            Glide.with(context).load(item.itemContentList.get(1).imageUrl).into(((ImageView)helper.getView(R.id.iv_menu_icon_two)));
//            Glide.with(context).load(item.itemContentList.get(2).imageUrl).into(((ImageView)helper.getView(R.id.iv_menu_icon_three)));
//            Glide.with(context).load(item.itemContentList.get(3).imageUrl).into(((ImageView)helper.getView(R.id.iv_menu_icon_four)));
//            Glide.with(context).load(item.itemContentList.get(4).imageUrl).into(((ImageView)helper.getView(R.id.iv_menu_icon_five)));

            ((TextView)helper.getView(R.id.tv_menu_icon_one)).setText(item.itemContentList.get(0).itemTitle);
            ((TextView)helper.getView(R.id.tv_menu_icon_two)).setText(item.itemContentList.get(1).itemTitle);
            ((TextView)helper.getView(R.id.tv_menu_icon_three)).setText(item.itemContentList.get(2).itemTitle);
            ((TextView)helper.getView(R.id.tv_menu_icon_four)).setText(item.itemContentList.get(3).itemTitle);
            ((TextView)helper.getView(R.id.tv_menu_icon_five)).setText(item.itemContentList.get(4).itemTitle);


            //lambda优化匿名表达式 ok
            helper.getView(R.id.ll_one).setOnClickListener((View v)->{
                    ARouter.getInstance().build(ARouterPaths.CATEGORY_LEVEL_ONE).navigation(mContext);
            });

            //测试跳转我的活动
            helper.getView(R.id.ll_two).setOnClickListener(view->{
                        Logger.dump(">>>>>>>>","测试跳转我的活动");
                ARouter.getInstance().build(ARouterPaths.ACTIVITY_PAGE).navigation(mContext);
                    }
            );


    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return mData.get(position).getSpanSize();
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
//            case R.id.icon_list_one:
//                ARouter.getInstance().build("/test1/activity").navigation(view.getContext());
//                break;
        }
    }





}


