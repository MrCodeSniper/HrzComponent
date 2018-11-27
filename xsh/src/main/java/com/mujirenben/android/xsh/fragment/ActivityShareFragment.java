package com.mujirenben.android.xsh.fragment;


import android.app.ActionBar;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.adapter.CircleSharAdapter;
import com.mujirenben.android.xsh.adapter.ShareFragmentAdapter;
import com.mujirenben.android.xsh.constant.Constants;
import com.mujirenben.android.xsh.entity.PersonIndexTop;
import com.mujirenben.android.xsh.entity.ShareEntity;
import com.mujirenben.android.xsh.entity.ShareQrResult;
import com.mujirenben.android.xsh.service.AllianceDetailService;
import com.mujirenben.android.xsh.widget.xrecyclerview.ProgressStyle;
import com.mujirenben.android.xsh.widget.xrecyclerview.XRecyclerView;
import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.base.BaseFragment;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.util.EmptyUtils;
import com.mujirenben.android.common.util.wxHelper.WeiXinHelper;
import com.mujirenben.android.common.util.wxHelper.WxBitmapUtil;
import com.mujirenben.android.common.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.mujirenben.android.common.util.wxHelper.WxBitmapUtil.SHARE_IMG_TEMP_PATH;


/**
 * Created by Cyj on 2018/8/2.
 */
public class ActivityShareFragment extends BaseFragment implements XRecyclerView.LoadingListener, CircleSharAdapter.onShareListener, ShareFragmentAdapter.onShareListener {


    private View view;
    private static ActivityShareFragment activityShareFragment;
    private PersonIndexTop personIndexTop;
    private ImageView iv_imgicon;
    private XRecyclerView xrecyclerview;
    private XRecyclerView mXrecylerView;
    private ShareFragmentAdapter adapter;
    private int position = 0;
    private int page = 1;
    private int pageSize = 10;
    private String shareUrlImage = "";
    private ShareQrResult mShareQrResult;
    private PopupWindow sharepop;
    private ClipboardManager mClipboard;
    private AllianceDetailService allianceService;
    private List<ShareEntity.DataBeanX.DataBean> dataBeanList = new ArrayList<>();
    private RelativeLayout rl_nomore;



    private List<Uri> uriList;


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        uriList=new ArrayList<>();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_wechatshare, container, false);
        mXrecylerView = (XRecyclerView) view.findViewById(R.id.xRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        adapter = new ShareFragmentAdapter(getActivity());
        adapter.setOnShareListener(this);
        mXrecylerView.setLayoutManager(manager);
        mXrecylerView.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
        mXrecylerView.setAdapter(adapter);
        mXrecylerView.setLoadingListener(this);
        rl_nomore = (RelativeLayout) view.findViewById(R.id.rl_nomore);
            return view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mClipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_ALLIANCE_HOST)//基础URL 建议以 / 结尾
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .build();

        allianceService = retrofit.create(AllianceDetailService.class);
        initData();
    }

    @Override
    public void setData(@Nullable Object data) {

    }



    public static ActivityShareFragment getInstance() {
        if (activityShareFragment == null) {
            activityShareFragment = new ActivityShareFragment();
        }
        return activityShareFragment;
    }



    private void initData() {
        Call<ShareEntity> call = allianceService.getShareData(page, pageSize, BaseApplication.VER_CODE, "Android", "xxx", System.currentTimeMillis() + "");
        call.enqueue(new Callback<ShareEntity>() {
            @Override
            public void onResponse(Call<ShareEntity> call, Response<ShareEntity> response) {
                if (EmptyUtils.isNotEmpty(response.body())) {
                    ShareEntity shareEntity = response.body();
                    if (shareEntity.getCode().equals("10000")) {
                        Log.i(Constants.TAG, "sharefragment\t" + shareEntity.getData().getData().toString());
                        dataBeanList = shareEntity.getData().getData();
                        adapter.refreshAdapter(dataBeanList);
                        if (dataBeanList.size()== 0) {
                            rl_nomore.setVisibility(View.VISIBLE);
                            mXrecylerView.setVisibility(View.GONE);
                        } else {
                            rl_nomore.setVisibility(View.GONE);
                            mXrecylerView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ShareEntity> call, Throwable t) {

            }
        });
    }


    @Override
    public void onRefresh() {
        page = 1;
        refreshData();
    }

    private void refreshData() {

        Call<ShareEntity> call = allianceService.getShareData(page, pageSize, BaseApplication.VER_CODE, "Android", "xxx", System.currentTimeMillis() + "");
        call.enqueue(new Callback<ShareEntity>() {
            @Override
            public void onResponse(Call<ShareEntity> call, Response<ShareEntity> response) {
                if (EmptyUtils.isNotEmpty(response.body())) {
                    ShareEntity shareEntity = response.body();
                    if (shareEntity.getCode().equals("10000")) {
                        dataBeanList = shareEntity.getData().getData();
                        adapter.refreshAdapter(dataBeanList);
                        if (dataBeanList.size()== 0) {
                            rl_nomore.setVisibility(View.VISIBLE);
                            mXrecylerView.setVisibility(View.GONE);
                        } else {
                            rl_nomore.setVisibility(View.GONE);
                            mXrecylerView.setVisibility(View.VISIBLE);
                        }

                    }
                    mXrecylerView.refreshComplete();
                    LoadingDialog.getInstance(getActivity()).hide();
                }
            }

            @Override
            public void onFailure(Call<ShareEntity> call, Throwable t) {

            }
        });

    }

    private void MoreData() {

        Call<ShareEntity> call = allianceService.getShareData(page, pageSize, BaseApplication.VER_CODE, "Android", "xxx", System.currentTimeMillis() + "");
        call.enqueue(new Callback<ShareEntity>() {
            @Override
            public void onResponse(Call<ShareEntity> call, Response<ShareEntity> response) {
                if (EmptyUtils.isNotEmpty(response.body())) {
                    ShareEntity shareEntity = response.body();
                    if (shareEntity.getCode().equals("10000")) {
                        dataBeanList.addAll(shareEntity.getData().getData());
                        adapter.refreshAdapter(dataBeanList);

                    }

                    mXrecylerView.loadMoreComplete();
                    LoadingDialog.getInstance(getActivity()).hide();
                }
            }

            @Override
            public void onFailure(Call<ShareEntity> call, Throwable t) {

            }
        });

    }

    @Override
    public void onLoadMore() {
        page = page + 1;
        MoreData();
    }

    @Override
    public void onShare(int position) {
        this.position = position;
        initPopUpWindow();
    }




    /**
     * 初始化分享弹窗
     */
    @SuppressWarnings("deprecation")
    private void initPopUpWindow() {
        LayoutInflater mLayoutInflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View menuView = (View) mLayoutInflater.inflate(R.layout.hrz_activity_share_pop, null, true);// 弹出窗口包含的视图

        sharepop = new PopupWindow(menuView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT,
                true);
        sharepop.setTouchable(true);
        sharepop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        sharepop.setOutsideTouchable(true);
        ColorDrawable colorDrawable = new ColorDrawable(00000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        sharepop.setBackgroundDrawable(colorDrawable);
        sharepop.setBackgroundDrawable(new BitmapDrawable());
        sharepop.setTouchInterceptor((arg0, arg1) -> {
            return false;
        });
        if (getActivity() != null && !getActivity().isFinishing()) {
            // ininView(menuView);
            TextView tv_cancel = (TextView) menuView.findViewById(R.id.tv_cancel);
            TextView tv_wxchat = (TextView) menuView.findViewById(R.id.tv_wxchat);
            TextView tv_wxfriend = (TextView) menuView.findViewById(R.id.tv_wxfriend);
            tv_cancel.setOnClickListener((v) -> {
                sharepop.dismiss();
            });
            tv_wxchat.setOnClickListener((v) -> {
                shareGFriend();
                sharepop.dismiss();
            });
            tv_wxfriend.setOnClickListener((v) -> {
                shareCircle();
                sharepop.dismiss();
            });

            sharepop.showAtLocation(mXrecylerView, Gravity.BOTTOM, 0, 0);
        }
    }

    private void shareGFriend() {
        if (dataBeanList.size() != 0) {
            ShareEntity.DataBeanX.DataBean dataBean = dataBeanList.get(position);
            mClipboard.setPrimaryClip(ClipData.newPlainText(null, dataBeanList.get(position).getContent()));
            if (dataBean.getImgs().size() == 0) {
                WeiXinHelper.getBuilder(getActivity()).setTitle(dataBean.getTitle()).setDescription(dataBean.getContent()).build().shareTextTo(WeiXinHelper.ShareToType.SESSION);
            } else {
                List<String> sharePicUris = new ArrayList<>();
                for (int i = 0; i < dataBean.getImgs().size(); i++) {
                    sharePicUris.add(dataBean.getImgs().get(i).getImg());
                }

                WeiXinHelper.getBuilder(getActivity())
                        .setPictureThumbPaths(sharePicUris.toArray(new String[sharePicUris.size()]))
                        .build()
                        .sharePictureTo(WeiXinHelper.ShareToType.SESSION);
            }

        }
    }


    private void shareCircle() {
        if (dataBeanList.size() != 0) {
            ShareEntity.DataBeanX.DataBean dataBean = dataBeanList.get(position);
            mClipboard.setPrimaryClip(ClipData.newPlainText(null, dataBeanList.get(position).getContent()));
            if (dataBean.getImgs().size() == 0) {
                WeiXinHelper.getBuilder(getActivity()).setTitle(dataBean.getTitle()).setDescription(dataBean.getContent()).build().shareTextTo(WeiXinHelper.ShareToType.TIMELINE);
            }else {
                List<String> sharePicUris = new ArrayList<>();
                for (int i = 0; i < dataBean.getImgs().size(); i++) {
                    sharePicUris.add(dataBean.getImgs().get(i).getImg());
                }

                WeiXinHelper.getBuilder(getActivity())
                        .setPictureThumbPaths(sharePicUris.toArray(new String[sharePicUris.size()]))
                        .build()
                        .sharePictureTo(WeiXinHelper.ShareToType.TIMELINE);
            }

        }
    }



    private String buildTransction(String str) {
        return (str == null) ? String.valueOf(System.currentTimeMillis()) : str + System.currentTimeMillis();
    }


    /**
     * 分享到朋友圈
     */
    private void doWxFriendShare() {
        // showToast("图片已经保存",0);
        mClipboard.setPrimaryClip(ClipData.newPlainText(null, dataBeanList.get(position).getContent()));
        Intent shareIntent = new Intent();
        // 1 Finals 2016-11-2 调用系统分享
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            ComponentName mComponentName = new ComponentName("com.tencent.mm",
                    "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, WxBitmapUtil.getFileUriArray(getActivity(),SHARE_IMG_TEMP_PATH));
            shareIntent.setType("image/*");
            shareIntent.putExtra("Kdescription", dataBeanList.get(position).getContent());
            // 3 Finals 2016-11-2 指定选择微信朋友圈。
            shareIntent.setComponent(mComponentName);
            // 4 Finals 2016-11-2 开始分享。
            startActivity(Intent.createChooser(shareIntent, "分享到朋友圈"));

            LoadingDialog.getInstance(getActivity()).hide();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
