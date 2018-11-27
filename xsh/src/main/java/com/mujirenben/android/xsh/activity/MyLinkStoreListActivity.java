package com.mujirenben.android.xsh.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.adapter.LinkListAdapter;
import com.mujirenben.android.xsh.constant.Constants;
import com.mujirenben.android.xsh.entity.LinkDataBean;
import com.mujirenben.android.xsh.service.LinkListService;
import com.mujirenben.android.xsh.widget.xrecyclerview.ProgressStyle;
import com.mujirenben.android.xsh.widget.xrecyclerview.XRecyclerView;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class MyLinkStoreListActivity extends BaseActivity implements View.OnClickListener, LinkListAdapter.LinkItemActionListener ,XRecyclerView.LoadingListener{
	private XRecyclerView mRecyclerView;
	private LinkListAdapter mAdapter;
	private String userId;

	private final static int RATE_ACTION_CONFIRM = 1;
	private final static int RATE_ACTION_REFUSE = 2;

	private int page=1;
	private int pageSize=10;
	private Retrofit retrofit;
	private LinkListService linkListService;

    private List<LinkDataBean.LinkItem> linkItemList=new ArrayList<>();

    @Override
	public void setupActivityComponent(@NonNull AppComponent appComponent) {

	}

	@Override
	public int initView(@Nullable Bundle savedInstanceState) {
		StatusBarUtil.setStatusBarColor(this,R.color.yellow);
		return R.layout.alliance_linkstore_list_layout;
	}

	@Override
	public void initData(@Nullable Bundle savedInstanceState) {
		userId = LoginDataManager.getsInstance(this).getUserId()+"";
//		userId = "5541890";

        retrofit = new Retrofit.Builder()
//				.baseUrl(BASE_PROTOCOL + Constants.BASE_HOST_CHUWEN)//基础URL 建议以 / 结尾
                .baseUrl(Constants.BASE_ALLIANCE_HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                .build();
        linkListService = retrofit.create(LinkListService.class);
        initView();


	}



	private void initView() {
		mRecyclerView = (XRecyclerView) findViewById(R.id.alliance_linkstore_recycler_view);
		mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SysProgress);
		mAdapter = new LinkListAdapter(null, getApplicationContext());
		mAdapter.setLinkItemActionListener(MyLinkStoreListActivity.this);
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadingListener(this);

		initData();
	}



	public static void startSelf(Context context) {
		Intent intent = new Intent(context, MyLinkStoreListActivity.class);
		if (!(context instanceof Activity)) {
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}

		context.startActivity(intent);
	}


	private void initData() {


		Call<LinkDataBean> call = linkListService.getLinkList(userId ,page,pageSize);
		call.enqueue(new Callback<LinkDataBean>() {
			@Override
			public void onResponse(Call<LinkDataBean> call, Response<LinkDataBean> response) {
				if (response.body()!=null) {
//					Log.e("zzz",response.body().toString());
					LinkDataBean linkDataBean = response.body();
					if (linkDataBean.getCode() == 10000) {
						List<LinkDataBean.LinkItem> dataList = linkDataBean.getData();
						if (dataList != null && dataList.size() > 0) {
							dataList.get(0).setFirstItem(true);
							mAdapter.setShowNothing(false);
						}
						LinkDataBean.LinkItem bannerItem = new LinkDataBean.LinkItem();
						bannerItem.setHeaderFlag(true);
						dataList.add(0, bannerItem);
						linkItemList=dataList;
						mAdapter.setNewData(linkItemList);

					} else {
						Log.e("zzz",response.body().toString());
					}
				}
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();

			}

			@Override
			public void onFailure(Call<LinkDataBean> call, Throwable t) {
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
				ArmsUtils.makeText(MyLinkStoreListActivity.this,getResources().getString(R.string.network_error));
			}
		});
	}

	@Override
	public void onClick(View view) {
	}

	@Override
	public void confirmRate(int storeId) {
//		Toast.makeText(getApplicationContext(), "confirmRate: " + storeId, Toast.LENGTH_SHORT).show();
		refreshStatus(storeId, RATE_ACTION_CONFIRM);

	}

	@Override
	public void refuseRate(int storeId) {
//		Toast.makeText(getApplicationContext(), "refuseRate: " + storeId, Toast.LENGTH_SHORT).show();
		refreshStatus(storeId, RATE_ACTION_REFUSE);
	}

	/**
	 * 确认分佣比例 + 拒绝分佣比例
	 *
	 * @param storeId
	 * @param status
	 */
	private void refreshStatus(int storeId, int status) {


		String apiVersion = "1.0";
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("v", apiVersion);
		jsonObject.addProperty("userId", userId);
		jsonObject.addProperty("id", storeId);
		jsonObject.addProperty("referrerStatus", status);
		Log.e("refreshStatus", jsonObject.toString());

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(Constants.BASE_ALLIANCE_HOST)//基础URL 建议以 / 结尾
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
				.build();

		LinkListService linkListService = retrofit.create(LinkListService.class);
		RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
		Call<JsonObject> call = linkListService.refreshStatus(apiVersion, body);
		call.enqueue(new Callback<JsonObject>() {
			@Override
			public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
				try {
					Log.e("refreshStatus", response.body().toString());
					if (response.body().get("code").getAsInt() == 10000) {
						initData();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
                    mRecyclerView.refreshComplete();
                    mRecyclerView.loadMoreComplete();
				}
			}

			@Override
			public void onFailure(Call<JsonObject> call, Throwable t) {
				Log.e("refreshStatus", t.getMessage());
				ArmsUtils.makeText(MyLinkStoreListActivity.this,getResources().getString(R.string.network_error));
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
			}
		});
	}


    @Override
    public void onRefresh() {
        page=1;
        refreshing();
    }

    @Override
    public void onLoadMore() {
        page=page+1;
        loadMore();
    }


    private void refreshing() {

        Call<LinkDataBean> call = linkListService.getLinkList(userId,page,pageSize);
        call.enqueue(new Callback<LinkDataBean>() {
            @Override
            public void onResponse(Call<LinkDataBean> call, Response<LinkDataBean> response) {
                if (response.body()!=null) {
//					Log.e("zzz",response.body().toString());
                    LinkDataBean linkDataBean = response.body();
                    if (linkDataBean.getCode() == 10000) {
                        linkItemList.clear();
                        mAdapter.notifyDataSetChanged();
                        List<LinkDataBean.LinkItem> dataList = linkDataBean.getData();
                        if (dataList != null && dataList.size() > 0) {
                            dataList.get(0).setFirstItem(true);
                        }
                        LinkDataBean.LinkItem bannerItem = new LinkDataBean.LinkItem();
                        bannerItem.setHeaderFlag(true);
                        dataList.add(0, bannerItem);
                        linkItemList=dataList;
                        mAdapter.setNewData(linkItemList);
//						mAdapter.setNewData(dataList);
                    } else {
						ArmsUtils.makeText(MyLinkStoreListActivity.this,linkDataBean.getMsg()+"");
                    }
                }

                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
            }

            @Override
            public void onFailure(Call<LinkDataBean> call, Throwable t) {
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
                ArmsUtils.makeText(MyLinkStoreListActivity.this,getResources().getString(R.string.network_error));
            }
        });
    }
    private void loadMore() {
        Call<LinkDataBean> call = linkListService.getLinkList(userId,page,pageSize);
        call.enqueue(new Callback<LinkDataBean>() {
            @Override
            public void onResponse(Call<LinkDataBean> call, Response<LinkDataBean> response) {
                if (response.body()!=null) {
//					Log.e("zzz",response.body().toString());
                    LinkDataBean linkDataBean = response.body();
                    if (linkDataBean.getCode() == 10000) {
//						List<LinkDataBean.LinkItem> dataList = linkDataBean.getData();
//						if (dataList != null && dataList.size() > 0) {
//							dataList.get(0).setFirstItem(true);
//						}
                        linkItemList.addAll(linkDataBean.getData());
                        mAdapter.setNewData(linkItemList);
                    } else {
						ArmsUtils.makeText(MyLinkStoreListActivity.this,linkDataBean.getMsg()+"");

                    }
                }

                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
            }

            @Override
            public void onFailure(Call<LinkDataBean> call, Throwable t) {
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
                ArmsUtils.makeText(MyLinkStoreListActivity.this,getResources().getString(R.string.network_error));
            }
        });
    }
}
