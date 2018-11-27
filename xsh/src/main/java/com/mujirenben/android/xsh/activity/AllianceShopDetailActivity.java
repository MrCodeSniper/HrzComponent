package com.mujirenben.android.xsh.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.baichuan.trade.common.utils.JSONUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.R2;
import com.mujirenben.android.xsh.adapter.DiscountShopGridAdapter;
import com.mujirenben.android.xsh.adapter.ShopTicketAdapter;
import com.mujirenben.android.xsh.constant.Constants;
import com.mujirenben.android.xsh.entity.GetTicketInfo;
import com.mujirenben.android.xsh.entity.MediaEntity;
import com.mujirenben.android.xsh.entity.OfficeResult;
import com.mujirenben.android.xsh.entity.ShopMerchantBean;
import com.mujirenben.android.xsh.entity.ShopRecommedGoods;
import com.mujirenben.android.xsh.entity.ShopTicketEntity;
import com.mujirenben.android.xsh.entity.msg.UpdateLevelEvent;
import com.mujirenben.android.xsh.service.AllianceDetailService;
import com.mujirenben.android.xsh.service.AllianceService;
import com.mujirenben.android.xsh.service.OfficeApi;
import com.mujirenben.android.xsh.service.RetrofitSingle;
import com.mujirenben.android.xsh.utils.ActivityUtils;
import com.mujirenben.android.xsh.utils.DateUtil;
import com.mujirenben.android.xsh.utils.DeviceUtils;
import com.mujirenben.android.xsh.utils.RandomStr;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseActivity;
import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.entity.AllianceDetailBeans;
import com.mujirenben.android.common.event.LoginStatusEvent;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.EmptyUtils;
import com.mujirenben.android.common.util.MiniProgramHelper;
import com.mujirenben.android.common.util.SpUtil;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.util.UIUtil;
import com.mujirenben.android.common.util.rxtools.RxTextTool;
import com.mujirenben.android.common.util.wxHelper.WeiXinHelper;
import com.mujirenben.android.common.widget.ExpLayout;
import com.mujirenben.android.common.widget.LoadingDialog;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Route(path = ARouterPaths.ALLIANCE_SHOPDETAIL)
public class AllianceShopDetailActivity extends BaseActivity implements ShopTicketAdapter.OnItemQuanListern
{

	private RecyclerView		xrecyclerview;
	private ShopTicketAdapter	shopTicketAdapter;
	private ShopTicketEntity	shopTicketEntity;
	private RelativeLayout		rl_near;
	private TextView datastatus;

	public static void startSelf(Context context, int shopId)
	{
		Intent intent = new Intent(context, AllianceShopDetailActivity.class);
		intent.putExtra("shopId", shopId);
		context.startActivity(intent);
	}

	public static String			SDIMGFILE;
	@BindView(R2.id.iv_back)
	ImageView						ivBack;
	@BindView(R2.id.tv_titlebar)
	TextView						tvTitlebar;
	@BindView(R2.id.rl_titlebar)
	RelativeLayout					rlTitlebar;
	@BindView(R2.id.rv_shopdetail)
	RecyclerView					rvShopdetail;
	@BindView(R2.id.tv_share)
	TextView						tvShare;
	@BindView(R2.id.ll_bottom)
	LinearLayout					llBottom;
	@BindView(R2.id.tv_discount_pay)
	TextView						tv_discount_pay;

	private String					quan1Text		= "";
	private String					quan2Text		= "";
	private ArrayList<String>		recommend_goods_url;
	private ArrayList<String>		recommend_shoppics_url;
	private  ArrayList<String> shopBannerDetail;

	private OfficeResult			mPayVipResult;

	private RelativeLayout			rl_recommend_menu;
	private RelativeLayout			rl_recommend_ticket;

	private ImageView				iv_shopdetail_banner;
	private ImageView				iv_play;
	private TextView				tv_shopdetail_shopname;
	private TextView				tv_shop_type;
	private TextView				tv_shop_city;
	private TextView				tv_shop_price;
	private LinearLayout			ll_shop_pics;
	private TextView				tv_bussiness_time;
	private TextView				tv_location;
	private ImageView				iv_phone_call;
	private LinearLayout			ll_shop_tickets;
	private LinearLayout			ll_shop_menu;
	private LinearLayout			ll_merchant_tag;
	private String					user_entity_id	= null;
	private LinearLayout			ll_horion_scroll_pics;
	private RelativeLayout			ll_merchant_ticket;
	private Dialog					dialogpop;
	private RelativeLayout			rl_shopdetail_banner;
	private ExpLayout 				exp_iv;

	private AllianceDetailBeans		detailBean;

	Intent							intent;
	private int						shopId;
	private DiscountShopGridAdapter	discountShopAdapter;
	private RelativeLayout			rl_nomore;

	private ShopMerchantBean		detailBeanMic;

	private final List<String>		urls			= new ArrayList<>();
	private final List<String>		goods_urls		= new ArrayList<>();

	@Override
	public void setupActivityComponent(@NonNull AppComponent appComponent)
	{

	}

	@Override
	public int initView(@Nullable Bundle savedInstanceState)
	{
		if (SpUtil.getIsMIUI(this)){
			StatusBarUtil.getStatusBarHelper(this,R.color.white).into(this);
		}else {
			StatusBarUtil.setStatusBarWhite(this);
		}
		BaseApplication.isGoNextActivity = true;
		return R.layout.activity_shopdetail;
	}

	@Override
	public void initData(@Nullable Bundle savedInstanceState)
	{
		initPrepareStatus();
		initView();
		initDatas();
	}

	@Override
	public boolean useEventBus()
	{
		return true;
	}

	public void initPrepareStatus()
	{
		intent = new Intent();
		shopId = getIntent().getIntExtra("shopId", -1);
		recommend_goods_url = new ArrayList<>();
		recommend_shoppics_url = new ArrayList<>();
		shopBannerDetail=new ArrayList<>();

		Log.e(Constants.TAG, "My PASSED SHOPID VALUE: " + shopId + "");
		if (LoginDataManager.getsInstance(this).isLogin())
		{
			user_entity_id = LoginDataManager.getsInstance(this).getWeixinUnionId();
		}

		tv_discount_pay.setOnClickListener(v -> {
			if (LoginDataManager.getsInstance(AllianceShopDetailActivity.this).isLogin()){
                String miniPath = MiniProgramHelper.getParameters(this,shopId+"",null);
                WeiXinHelper.getBuilder(this)
						.setMiniUserName(Consts.MINI_PROGRAM_ID)
						.setMiniPath(miniPath)
                        .build()
                        .openMiniProgram();
			}else{
				ARouter.getInstance()
						.build(ARouterPaths.LOGIN_MAIN_MINE)
						.withString(Consts.LOGIN_SOURCE_KEY,"店铺详情")
						.navigation(this);
			}
		});

		tvShare.setOnClickListener((v) -> {
			if (!LoginDataManager.getsInstance(this).isLogin())
			{
				ARouter.getInstance()
						.build(ARouterPaths.LOGIN_MAIN_MINE)
						.withString(Consts.LOGIN_SOURCE_KEY,"店铺详情")
						.navigation(this);
				return;
			}
			Bundle mBundle = new Bundle();

			if (detailBean != null && !"".equals(detailBean))
			{
				if (!"".equals(detailBean.getData()) && null != detailBean.getData())
				{
					mBundle.putString("shopName", detailBean.getData().getStoreName());
					mBundle.putString("shopAddress", detailBean.getData().getAddress());
					mBundle.putString("shopId", detailBean.getData().getId() + "");
					DecimalFormat df = new DecimalFormat("######0.00"); // 保留两位小数点
					if (detailBeanMic.getData() != null)
					{
						for (int i = 0; i < detailBeanMic.getData().size(); i++)
						{
							ShopMerchantBean.DataBean item = detailBeanMic.getData().get(i);
							if (i == 0)
								quan1Text = "满" + df.format(item.getMinPrice()) + "减" + df.format(item.getDiscounted());
							if (i == 1)
								quan2Text = "满" + df.format(item.getMinPrice()) + "减" + df.format(item.getDiscounted());
							// if (i == 2)
							// quan3Text = df.format(item.getMinPrice()) + "减" + df.format(item.getDiscounted());

						}
					}

					mBundle.putString("ticket1", quan1Text + "");
					mBundle.putString("ticket2", quan2Text + "");
					// mBundle.putString("ticket3", quan3Text + "");
					mBundle.putString("perCapita", detailBean.getData().getPerCapita() + "");

					if (EmptyUtils.isNotEmpty(detailBean.getData().getStorefrontImgs()))
					{
						if (detailBean.getData().getStorefrontImgs().size() >= 1)
						{
							mBundle.putString("shopThumb", detailBean.getData().getStorefrontImgs().get(0) + "");
						}
					}
					else
					{
						mBundle.putString("shopThumb", "");
					}
					ActivityUtils.skipActivity(AllianceShopDetailActivity.this, ShareActivity.class, mBundle);
					AllianceShopDetailActivity.this.overridePendingTransition(R.anim.in_bottomtop, 0);

				}
			}

		});

	}


	/**
	 * 获取dialog宽度
	 */
	public int getDialogW(Context aty)
	{
		DisplayMetrics dm = new DisplayMetrics();
		dm = aty.getResources().getDisplayMetrics();
		int w = dm.widthPixels - 100;
		return w;
	}

	/**
	 * 获取当前设备的屏幕密度等基本参数
	 */
	protected void getDeviceDensity()
	{
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Constants.EXACT_SCREEN_HEIGHT = metrics.heightPixels;
		Constants.EXACT_SCREEN_WIDTH = metrics.widthPixels;
	}

	public void initView()
	{
		rl_nomore = (RelativeLayout) findViewById(R.id.rl_nomore);
		datastatus = findViewById(R.id.datastatus);
		getDeviceDensity();
		dialogpop = new Dialog(AllianceShopDetailActivity.this, R.style.AlertDialog_AppCompat_Light_);
		ivBack.setOnClickListener((View v) -> finish());

		//////////// 配置头部headerview////////
		View view = LayoutInflater.from(this).inflate(R.layout.shop_detail_header, null);
		initBanner(view);
		initShopName(view);
		initShopParam(view);
		initShopPics(view);
		initBussinessDetail(view);
		initLocation(view);
		initCommonTicket(view);
		initRecommendGoods(view);
		initShopTicket(view);
		//////////// 配置头部headerview////////

		discountShopAdapter = new DiscountShopGridAdapter(R.layout.item_shop_discount_grid);
		rvShopdetail.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
		discountShopAdapter.addHeaderView(view);
		rvShopdetail.setAdapter(discountShopAdapter);
	}

	private void initShopTicket(View view)
	{
		rl_near = (RelativeLayout) view.findViewById(R.id.rl_near);
		rl_near.setVisibility(View.GONE);
		xrecyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
		xrecyclerview.setFocusable(false);
		LinearLayoutManager manager = new LinearLayoutManager(this);
		xrecyclerview.setLayoutManager(manager);
		shopTicketAdapter = new ShopTicketAdapter(R.layout.alliance_shopticket_item);
		xrecyclerview.setAdapter(shopTicketAdapter);
	}

	private void initRecommendGoods(View view)
	{
		ll_shop_menu = (LinearLayout) view.findViewById(R.id.ll_shop_menu);
		rl_recommend_menu = (RelativeLayout) view.findViewById(R.id.rl_recommend_menu);
	}

	private void initCommonTicket(View view)
	{
		ll_shop_tickets = (LinearLayout) view.findViewById(R.id.ll_shop_tickets);
		rl_recommend_ticket = (RelativeLayout) view.findViewById(R.id.rl_recommend_ticket);
	}

	private void initLocation(View view)
	{
		tv_location = (TextView) view.findViewById(R.id.tv_location);
		iv_phone_call = (ImageView) view.findViewById(R.id.iv_phone_call);
	}

	private void initBussinessDetail(View view)
	{
		tv_bussiness_time = (TextView) view.findViewById(R.id.tv_bussiness_time);
		ll_merchant_ticket = (RelativeLayout) view.findViewById(R.id.ll_merchant_ticket);
	}

	private void initShopPics(View view)
	{
		ll_shop_pics = (LinearLayout) view.findViewById(R.id.ll_shop_pics);
		ll_horion_scroll_pics = (LinearLayout) view.findViewById(R.id.ll_horion_scroll_pics);
	}

	private void initShopParam(View view)
	{
		ll_merchant_tag = (LinearLayout) view.findViewById(R.id.ll_merchant_tag);
		tv_shop_type = (TextView) view.findViewById(R.id.tv_shop_type);
		tv_shop_city = (TextView) view.findViewById(R.id.tv_shop_city);
		tv_shop_price = (TextView) view.findViewById(R.id.tv_shop_price);
	}

	private void initShopName(View view)
	{
		tv_shopdetail_shopname = (TextView) view.findViewById(R.id.tv_shopdetail_shopname);
	}

	private void initBanner(View view)
	{
		iv_play = (ImageView) view.findViewById(R.id.iv_play);
		rl_shopdetail_banner = (RelativeLayout) view.findViewById(R.id.rl_shopdetail_banner);
		iv_shopdetail_banner = (ImageView) view.findViewById(R.id.iv_shopdetail_banner);
		exp_iv=view.findViewById(R.id.exp_iv);

	}

	private AllianceDetailService allianceService;

	public void initDatas()
	{

		Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_ALLIANCE_HOST)
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())// 设置 Json 转换器
				.build();

		allianceService = retrofit.create(AllianceDetailService.class);
		allianceMediaService = retrofit.create(AllianceService.class);
		// 商铺详情相关
		getMecharntDetail();

		getMecharntTicket();
		// 优惠券相关
		getShopTicket();

	}

	private void getSharePer(String perCapita)
	{
		HashMap<String, String> mMap = new HashMap<String, String>();
		mMap.put("merchantId", shopId + "");
		mMap.put("money", perCapita);

		DecimalFormat df = new DecimalFormat("######0.0"); // 保留两位小数点
		Retrofit retrofit = RetrofitSingle.getInstance(this).retrofit;
		OfficeApi mPayVipApi = retrofit.create(OfficeApi.class);
		Call<OfficeResult> call = mPayVipApi.office(HttpParamUtil.getCommonSignParamMap(this, mMap));
		call.enqueue(new Callback<OfficeResult>()
		{
			@Override
			public void onResponse(Call<OfficeResult> call, Response<OfficeResult> response)
			{
				if (response.body() != null)
				{
					mPayVipResult = response.body();

					if (mPayVipResult.getCode().equals("00000"))
					{
						if(tvShare == null )return;
						// setData(mPayVipResult);
						if ("0.0".equals(perCapita + ""))
						{
							tvShare.setText("分享"
									+ df.format(
											(Float.parseFloat(mPayVipResult.getData().getProfitMoney()) / 50.0) * 100)
									+ "%");

						}
						else
						{
							tvShare.setText("分享" + df.format((Float.parseFloat(mPayVipResult.getData().getProfitMoney())
									/ Float.parseFloat(perCapita + "")) * 100) + "%");
						}
					}
				}
			}

			@Override
			public void onFailure(Call<OfficeResult> call, Throwable t)
			{

				ArmsUtils.makeText(AllianceShopDetailActivity.this,"请求失败");
			}
		});

	}

	/**
	 * 附近商家优惠券
	 */
	private void getShopTicket()
	{
		LoadingDialog.getInstance(this).show();
		String sessionId = LoginDataManager.getsInstance(AllianceShopDetailActivity.this).getMediaId();
		getMediaRegister();
//		if (TextUtils.isEmpty(sessionId))
//		{
//			getMediaRegister();
//		}
//		else
//		{
//			getNearByMerchantTicket(sessionId);
//		}

		shopTicketAdapter.setOnItemQuanListern(this);

	}

	/**
	 * 附近商家优惠券
	 * 
	 * @param sessionId
	 */
	private void getNearByMerchantTicket(String sessionId)
	{
		Map<String, Object> map = new HashMap<>();
		map.put("sessionId", sessionId);
		map.put("merchantId", shopId);

		Logger.e(JSONUtils.toJson(map));

		if (!LoginDataManager.getsInstance(AllianceShopDetailActivity.this).getUserLatitude().equals(""))
		{
			map.put("latitude",
					Double.valueOf(LoginDataManager.getsInstance(AllianceShopDetailActivity.this).getUserLatitude()));
		}
		else
		{
			map.put("latitude", 0.0);
		}

		if (!LoginDataManager.getsInstance(AllianceShopDetailActivity.this).getUserLongtitude().equals(""))
		{
			map.put("longitude",
					Double.valueOf(LoginDataManager.getsInstance(AllianceShopDetailActivity.this).getUserLongtitude()));
		}
		else
		{
			map.put("longitude", 0.0);
		}

		map.put("coordinatesType", 1);
		Gson gson = new Gson();
		String strEntity = gson.toJson(map);
		Log.i(Consts.CHENHOGN_TAG, "map：" + map.toString());
		RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),
				strEntity);
		Call<ShopTicketEntity> call = allianceService.getShopTicket(requestBody);
		call.enqueue(new Callback<ShopTicketEntity>()
		{
			@Override
			public void onResponse(Call<ShopTicketEntity> call, Response<ShopTicketEntity> response)
			{
				if (EmptyUtils.isNotEmpty(response.body()))
				{
					shopTicketEntity = response.body();
					Log.i("Test", shopTicketEntity.getCode());
					if (shopTicketEntity.getCode().equals("10000"))
					{
						Log.i("Test", shopTicketEntity.toString());
						shopTicketAdapter.setNewData(shopTicketEntity.getData());
						if (shopTicketEntity.getData().size() > 0)
						{
							rl_near.setVisibility(View.VISIBLE);
						}
						else
						{
							rl_near.setVisibility(View.GONE);
						}
					}

					LoadingDialog.getInstance(AllianceShopDetailActivity.this).hide();

				}
			}

			@Override
			public void onFailure(Call<ShopTicketEntity> call, Throwable t)
			{
				ArmsUtils.makeText(AllianceShopDetailActivity.this,getResources().getString(R.string.network_error));
				LoadingDialog.getInstance(AllianceShopDetailActivity.this).hide();
			}
		});
	}

	private AllianceService allianceMediaService;

	private void getMediaRegister()
	{
		Map<String, Object> map = new HashMap<>();
		if (TextUtils.isEmpty(LoginDataManager.getsInstance(this).getAvatarUrl()))
		{
			map.put("avatarUrl",
					"http://thirdwx.qlogo.cn/mmopen/vi_32/mPicr5SKacJFljJOHfVTwp1Q6p9tD7R2A7kjmfPPO6%20ibibMRrAuNLYN4YJ8oJvQ1iao36icww7icCVbOorMibG2u9QJXQ/132");
		}
		else
		{
			map.put("avatarUrl", LoginDataManager.getsInstance(this).getAvatarUrl());
		}
		map.put("wxNickName", LoginDataManager.getsInstance(this).getNickName());
		map.put("unionId", LoginDataManager.getsInstance(this).getWeixinUnionId());
		map.put("mediaId", Constants.MEDIA_ID);
		map.put("mediaUserId", LoginDataManager.getsInstance(this).getWeixinUnionId());
		Gson gson = new Gson();
		String strEntity = gson.toJson(map);
		RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),
				strEntity);
		Call<MediaEntity> call = allianceMediaService.getMediaEntity(requestBody);
		call.enqueue(new Callback<MediaEntity>()
		{
			@Override
			public void onResponse(Call<MediaEntity> call, Response<MediaEntity> response)
			{
				if (!TextUtils.isEmpty(response.body().getData()))
				{
					LoginDataManager.getsInstance(AllianceShopDetailActivity.this)
							.setMediaIdForAlliance(response.body().getData());
					Logger.e(response.toString());
					getNearByMerchantTicket(response.body().getData());
				}
			}

			@Override
			public void onFailure(Call<MediaEntity> call, Throwable t)
			{
				Logger.e(t.getMessage() + "注册媒体Id失败");
			}
		});
	}

	private void getMecharntDetail()
	{

		Call<AllianceDetailBeans> call = allianceService.getShopDetailBeanData(shopId);
		call.enqueue(new Callback<AllianceDetailBeans>()
		{
			@Override
			public void onResponse(Call<AllianceDetailBeans> call, Response<AllianceDetailBeans> response)
			{

				if (response.body() != null)
				{
					Logger.e(response.body().toString());
					detailBean = response.body();
					if (detailBean.getCode().equals(Constants.ALLIANCE_REQUEST_SUCCESS))
					{

						urls.addAll(detailBean.getData().getEnvironmentImgs());

						if (EmptyUtils.isEmpty(detailBean.getData().getVideoImg()))
						{
							rl_shopdetail_banner.setVisibility(View.GONE);
						}

						if (EmptyUtils.isNotEmpty(detailBean.getData().getVideoImg())
								&& EmptyUtils.isEmpty(detailBean.getData().getVideoUrl()))
						{
							iv_play.setVisibility(View.GONE);
						}

						if (detailBean.getData().getStorefrontImgs().size() > 0)
						{
							rl_shopdetail_banner.setVisibility(View.VISIBLE);
							Glide.with(getApplicationContext()).load(detailBean.getData().getStorefrontImgs().get(0))
									.apply(new RequestOptions().placeholder(R.color.place_holder_error_color)
											.error(R.color.place_holder_error_color))
									.into(iv_shopdetail_banner);

							RelativeLayout.LayoutParams exParams2 = (RelativeLayout.LayoutParams) exp_iv.getLayoutParams();
							exParams2.height = UIUtil.getWidth(AllianceShopDetailActivity.this) * 9 / 16;
							exParams2.width = UIUtil.getWidth(AllianceShopDetailActivity.this);
							exp_iv.setImgBg(580, 773, detailBean.getData().getStorefrontImgs().get(0), 0, 0, 0, 10);

							exp_iv.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									Bundle bundle = new Bundle();
									bundle.putStringArrayList("image_urls", shopBannerDetail);
									bundle.putInt("current_image_url_index", 0);
									ARouter.getInstance().build(ARouterPaths.IMAGE_BROWSE_ACTIVITY)
											.withBundle("fromOther", bundle)
											.navigation(AllianceShopDetailActivity.this);
								}
							});


							shopBannerDetail.clear();
							shopBannerDetail.add(detailBean.getData().getStorefrontImgs().get(0));
							iv_shopdetail_banner.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									Bundle bundle = new Bundle();
									bundle.putStringArrayList("image_urls", shopBannerDetail);
									bundle.putInt("current_image_url_index", 0);
									ARouter.getInstance().build(ARouterPaths.IMAGE_BROWSE_ACTIVITY)
											.withBundle("fromOther", bundle)
											.navigation(AllianceShopDetailActivity.this);
								}
							});
						}

						if (EmptyUtils.isNotEmpty(detailBean.getData().getVideoUrl()))
						{
							iv_play.setVisibility(View.VISIBLE);
							Glide.with(AllianceShopDetailActivity.this).load(detailBean.getData().getVideoImg())
									.apply(new RequestOptions().placeholder(R.color.place_holder_error_color)
											.error(R.color.place_holder_error_color))
									.into(iv_shopdetail_banner);

							RelativeLayout.LayoutParams exParams2 = (RelativeLayout.LayoutParams) exp_iv.getLayoutParams();
							exParams2.height = UIUtil.getWidth(AllianceShopDetailActivity.this) * 9 / 16;
							exParams2.width = UIUtil.getWidth(AllianceShopDetailActivity.this);
							exp_iv.setImgBg(580, 773, detailBean.getData().getVideoImg(), 0, 0, 0, 10);


							exp_iv.setOnClickListener(null);
							iv_shopdetail_banner.setOnClickListener(null);
						}

						iv_play.setOnClickListener((View v) -> {

							if (EmptyUtils.isEmpty(detailBean.getData().getVideoUrl()))
							{
								ArmsUtils.makeText(AllianceShopDetailActivity.this,"商家没有配置视频");
							}
							else
							{

								Bundle bundle=new Bundle();
								bundle.putString(Constants.IntentConstant.TITLE,detailBean.getData().getStoreName());
								bundle.putString(Constants.IntentConstant.LINK_URL,detailBean.getData().getVideoUrl());
								bundle.putString(Constants.IntentConstant.SEARCH_TXT,"商家介绍");
								bundle.putString(Constants.IntentConstant.ISWXIMG,detailBean.getData().getVideoImg());

								if( detailBean.getData().getVideoUrl().contains("https")){
									bundle.putString(Constants.IntentConstant.INTENT_ID, detailBean.getData().getVideoUrl().replace("https","http"));
								}else {
									bundle.putString(Constants.IntentConstant.INTENT_ID,detailBean.getData().getVideoUrl());
								}

								ARouter.getInstance().build(ARouterPaths.ALLIANCE_VIDEO).withBundle(Consts.HRZ_ROUTER_BUNDLE,bundle).navigation(AllianceShopDetailActivity.this);
							}
						});

						if (!EmptyUtils.isEmpty(detailBean.getData().getStoreName()))
							tv_shopdetail_shopname.setText(detailBean.getData().getStoreName());

						if (!EmptyUtils.isEmpty(detailBean.getData().getIndustry()))
							tv_shop_type.setText(detailBean.getData().getIndustry());

						if (EmptyUtils.isEmpty(detailBean.getData().getArea()))
						{
							tv_shop_city.setVisibility(View.GONE);
						}
						else
						{
							tv_shop_city.setText(detailBean.getData().getArea());
						}

						for (String service_tab : detailBean.getData().getServiceTabs())
						{
							View view = View.inflate(AllianceShopDetailActivity.this, R.layout.item_service_tab, null);
							TextView tv_service = (TextView) view.findViewById(R.id.tv_service_tab);
							tv_service.setText(service_tab);
							if (ll_merchant_tag.getChildCount() < 3)
							{
								ll_merchant_tag.addView(view);
							}
						}

						if (!EmptyUtils.isEmpty(detailBean.getData().getPerCapita()))
						{
							tv_shop_price.setText("¥ " + detailBean.getData().getPerCapita() + "/人");
						}

						if (EmptyUtils.isEmpty(detailBean.getData().getCmsMerchantTimes()))
						{
							ll_merchant_ticket.setVisibility(View.GONE);
						}
						else
						{

							String str_time = "";
							String str_morning = "";
							String str_afternon = "";

							for (AllianceDetailBeans.DataBean.CmsMerchantTimesBean cmsMerchantTimesBean : detailBean
									.getData().getCmsMerchantTimes())
							{
								str_time += DateUtil.strFormat(cmsMerchantTimesBean.getStartTime()) + "-"
										+ DateUtil.strFormat(cmsMerchantTimesBean.getEndTime()) + " ";
							}

							tv_bussiness_time.setText("营业时间 " + str_time);
						}

						tv_location.setText(detailBean.getData().getAddress());
						tv_location.setOnClickListener(view -> {
							String city = null;
							Object cityObj = detailBean.getData().getCity();
							if (cityObj != null)
							{
								city = cityObj.toString();
							}
							Intent intent = new Intent(AllianceShopDetailActivity.this, Amap2DActivity.class);
							intent.putExtra(Amap2DActivity.EXTRA_STORE_NAME, detailBean.getData().getStoreName());
							intent.putExtra(Amap2DActivity.EXTRA_STORE_ADDRESS, detailBean.getData().getAddress());
							intent.putExtra(Amap2DActivity.EXTRA_STORE_CITY, city);
							startActivity(intent);
						});
						iv_phone_call.setOnClickListener((View v) -> {
							Intent intent = new Intent(Intent.ACTION_DIAL);
							intent.setData(Uri.parse("tel:" + detailBean.getData().getTelephone()));
							if (intent.resolveActivity(getPackageManager()) != null)
							{
								startActivity(intent);
							}
						});

						if (EmptyUtils.isEmpty(detailBean.getData().getEnvironmentImgs()))
						{
							ll_horion_scroll_pics.setVisibility(View.GONE);
						}
						else
						{
							ll_horion_scroll_pics.setVisibility(View.VISIBLE);
							ll_shop_pics.removeAllViews();

							recommend_shoppics_url.clear();
							recommend_shoppics_url.addAll(detailBean.getData().getEnvironmentImgs());

							for (int i = 0; i < detailBean.getData().getEnvironmentImgs().size(); i++)
							{
								String url = detailBean.getData().getEnvironmentImgs().get(i);
								FrameLayout view = (FrameLayout) View.inflate(AllianceShopDetailActivity.this,
										R.layout.alliance_item_iv, null);


								try {
									Glide.with(AllianceShopDetailActivity.this).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            Bitmap bitmap = imageZoom(resource);
                                            ((ImageView)view.findViewById(R.id.iv_shop_pic_item)).setImageBitmap(bitmap);
                                        }

                                    }); //方
								} catch (Exception e) {
								}
//								Glide.with(AllianceShopDetailActivity.this).load(url)
//										.apply(new RequestOptions().placeholder(R.color.place_holder_error_color)
//												.error(R.color.place_holder_error_color))
//										.into((ImageView) view.findViewById(R.id.iv_shop_pic_item));

								if (i == 0)
								{
									FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
											(int) DeviceUtils.dpToPixel(AllianceShopDetailActivity.this, 90),
											(int) DeviceUtils.dpToPixel(AllianceShopDetailActivity.this, 90));
									params.setMargins(DeviceUtils.dip2px(AllianceShopDetailActivity.this, 12), 0,
											DeviceUtils.dip2px(AllianceShopDetailActivity.this, 5), 0);
									((ImageView) view.findViewById(R.id.iv_shop_pic_item)).setLayoutParams(params);
								}

								// ((ImageView) view.findViewById(R.id.iv_shop_pic_item)).setTag(i);

								int finalI = i;
								((ImageView) view.findViewById(R.id.iv_shop_pic_item))
										.setOnClickListener(new View.OnClickListener()
										{
											@Override
											public void onClick(View v)
											{
												Bundle bundle = new Bundle();
												bundle.putStringArrayList("image_urls", recommend_shoppics_url);
												bundle.putInt("current_image_url_index", finalI);
												ARouter.getInstance().build(ARouterPaths.IMAGE_BROWSE_ACTIVITY)
														.withBundle("fromOther", bundle)
														.navigation(AllianceShopDetailActivity.this);
											}
										});

								ll_shop_pics.addView(view);
							}
						}

						// if (dialog != null) {
						// dialog.dismiss();
						// }
					}
					if(detailBean.getData()!=null && !"".equals(detailBean.getData())){
						getSharePer(detailBean.getData().getPerCapita() + "");
					}


				}
				else
				{
					rl_nomore.setVisibility(View.VISIBLE);
					rvShopdetail.setVisibility(View.GONE);
				}
			}

			@Override
			public void onFailure(Call<AllianceDetailBeans> call, Throwable t)
			{
				ArmsUtils.makeText(AllianceShopDetailActivity.this,getResources().getString(R.string.network_error));
				rl_nomore.setVisibility(View.VISIBLE);
				datastatus.setText("需要打开网络后才能使用红人装哦");
				if (rvShopdetail != null)
				{
					rvShopdetail.setVisibility(View.GONE);
				}
			}
		});

		Call<ShopRecommedGoods> call_recommend_goods = allianceService.getShopRecommendGoods(shopId);
		call_recommend_goods.enqueue(new Callback<ShopRecommedGoods>()
		{
			@Override
			public void onResponse(Call<ShopRecommedGoods> call, Response<ShopRecommedGoods> response)
			{
				if (response.body() != null)
				{
					ShopRecommedGoods detailBean = response.body();
					if (detailBean.getCode().equals(Constants.ALLIANCE_REQUEST_SUCCESS))
					{

						if (EmptyUtils.isEmpty(detailBean.getData()))
						{
							rl_recommend_menu.setVisibility(View.GONE);
						}
						else
						{
							rl_recommend_menu.setVisibility(View.VISIBLE);
							ll_shop_menu.removeAllViews();

							recommend_goods_url.clear();
							for (int i = 0; i < detailBean.getData().size(); i++)
							{
								recommend_goods_url.add(detailBean.getData().get(i).getImg());
							}

							for (int i = 0; i < detailBean.getData().size(); i++)
							{

								ShopRecommedGoods.DataBean items = detailBean.getData().get(i);
								LinearLayout view = (LinearLayout) View.inflate(AllianceShopDetailActivity.this,
										R.layout.alliance_item_menu, null);
								((TextView) view.findViewById(R.id.tv_menu_name)).setText(items.getName());
								((TextView) view.findViewById(R.id.iv_menu_type)).setText(items.getDescribe());
								((TextView) view.findViewById(R.id.tv_menu_price))
										.setText("¥ " + items.getPrice() + "");

								Glide.with(AllianceShopDetailActivity.this).load(items.getImg())
										.into((ImageView) view.findViewById(R.id.iv_shop_menu_item));

								int finalI = i;
								((ImageView) view.findViewById(R.id.iv_shop_menu_item))
										.setOnClickListener(new View.OnClickListener()
										{
											@Override
											public void onClick(View v)
											{
												Bundle bundle = new Bundle();
												bundle.putStringArrayList("image_urls", recommend_goods_url);
												bundle.putInt("current_image_url_index", finalI);
												ARouter.getInstance().build(ARouterPaths.IMAGE_BROWSE_ACTIVITY)
														.withBundle("fromOther", bundle)
														.navigation(AllianceShopDetailActivity.this);
											}
										});

								if (i == 0)
								{
									LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
											ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
									params.setMargins(DeviceUtils.dip2px(AllianceShopDetailActivity.this, 12), 0,
											DeviceUtils.dip2px(AllianceShopDetailActivity.this, 0), 0);
									((LinearLayout) view.findViewById(R.id.ll_shop_menu_item)).setLayoutParams(params);
								}

								ll_shop_menu.addView(view);
							}
						}
					}
					else
					{
						rl_recommend_menu.setVisibility(View.GONE);
					}
				}
				else
				{
					rl_recommend_menu.setVisibility(View.GONE);
				}
			}

			@Override
			public void onFailure(Call<ShopRecommedGoods> call, Throwable t)
			{
				ArmsUtils.makeText(AllianceShopDetailActivity.this,getResources().getString(R.string.network_error));
				rl_recommend_menu.setVisibility(View.GONE);
			}
		});

	}

	@Override
	public void onItemQuan(TextView textView, TextView tv_distance, TextView tv_time, TextView tv_title, TextView tv_fu,
			TextView tv_ticket_money, RelativeLayout rl_parent, ShopTicketEntity.DataBean item)
	{
		if (!LoginDataManager.getsInstance(AllianceShopDetailActivity.this).isLogin())
		{
			ArmsUtils.makeText(AllianceShopDetailActivity.this, getResources().getString(R.string.not_login));
			ARouter.getInstance()
					.build(ARouterPaths.LOGIN_MAIN_MINE)
					.withString(Consts.LOGIN_SOURCE_KEY,"店铺详情")
					.navigation(AllianceShopDetailActivity.this);
			textView.setText("立即\n领取");
			textView.setEnabled(true);
			textView.setTextColor(getResources().getColor(R.color.white));
		}
		else
		{
			// TODO 领取优惠券
			String user_id = LoginDataManager.getsInstance(AllianceShopDetailActivity.this).getWeixinUnionId();
			Log.e(Constants.TAG, "union_id" + user_id);
			Long ticket_id = Long.valueOf(item.getId());
			requestShopTicket(allianceService, user_id + "", ticket_id, textView, tv_distance, tv_time, tv_title, tv_fu,
					tv_ticket_money, rl_parent);
		}
	}

	private void requestShopTicket(AllianceDetailService allianceService, String user_id, Long ticket_id,
			TextView textView, TextView tv_distance, TextView tv_time, TextView tv_fu, TextView tv_title,
			TextView tv_ticket_money, RelativeLayout rl_parent)
	{

		///////////////// POST参数//////////////////
		Map<String, String> params = new HashMap<>();
		params.put("id", ticket_id + "");
		params.put("mediaId", Constants.MEDIA_ID);
		params.put("mediaUserId", user_id);
		params.put("clientId", "android");
		params.put("clientVersion", DeviceUtils.getAppVersionName(this));
		params.put("timeStamp", System.currentTimeMillis() + "");
		params.put("nonceStr", RandomStr.getRandomStr(12));
		String strEntity = JSONUtils.toJson(params);
		Logger.e(strEntity);
		RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),
				strEntity);

		///////////////// 获取商家和用户对应优惠券列表//////////////////
		Call<GetTicketInfo> call_recommend_goods = allianceService.getCommonTicket(requestBody);
		call_recommend_goods.enqueue(new Callback<GetTicketInfo>()
		{
			@Override
			public void onResponse(Call<GetTicketInfo> call, Response<GetTicketInfo> response)
			{
				if (response.body() != null)
				{
					GetTicketInfo detailBean = response.body();
					Log.e("Test", "Test\t" + detailBean.getCode());
					Log.e(Constants.TAG, detailBean.toString());
					if (detailBean.getCode().equals(Constants.ALLIANCE_REQUEST_SUCCESS))
					{
						// Log.e(Constants.TAG,detailBean.getMessage());
						textView.setText("已经\n领取");
						textView.setEnabled(false);
						textView.setTextColor(getResources().getColor(R.color.text_color_ticket_received));

						tv_distance.setTextColor(AllianceShopDetailActivity.this.getResources()
								.getColor(R.color.text_color_blur));
						tv_time.setTextColor(AllianceShopDetailActivity.this.getResources()
								.getColor(R.color.text_color_blur));
						tv_title.setTextColor(AllianceShopDetailActivity.this.getResources()
								.getColor(R.color.text_color_blur));
						tv_fu.setTextColor(AllianceShopDetailActivity.this.getResources()
								.getColor(R.color.text_color_blur));
						tv_ticket_money.setTextColor(AllianceShopDetailActivity.this.getResources()
								.getColor(R.color.text_color_blur));
						rl_parent.setBackgroundResource(R.drawable.hrz_alliance_shoptiticket_nomal);

						ArmsUtils.makeText(AllianceShopDetailActivity.this,"领取成功，微信扫码消费自动优惠");
						//

					}
					else if (detailBean.getCode().equals(Constants.ALLIANCE_REQUEST_FAIL))
					{
						Logger.e(detailBean.getMsg());
						// ToastUtils.getInstanc(AllianceShopDetailActivity.this).showToast(detailBean.getMsg());
					}
				}
			}

			@Override
			public void onFailure(Call<GetTicketInfo> call, Throwable t)
			{
				ArmsUtils.makeText(AllianceShopDetailActivity.this,getResources().getString(R.string.network_error));
			}
		});

	}

	/**
	 * 获取优惠券列表
	 */
	private void getMecharntTicket()
	{

		Call<ShopMerchantBean> call_shop_merchant = allianceService.getShopMerchantBean(user_entity_id,
				Constants.MEDIA_ID, (long) shopId, System.currentTimeMillis() + "", RandomStr.getRandomStr(12),
				"android", DeviceUtils.getAppVersionName(this));

		call_shop_merchant.enqueue(new Callback<ShopMerchantBean>()
		{
			@Override
			public void onResponse(Call<ShopMerchantBean> call, Response<ShopMerchantBean> response)
			{
				if (response.body() != null)
				{
					ShopMerchantBean detailBean = response.body();
					detailBeanMic = response.body();

					if (detailBean.getCode().equals(Constants.ALLIANCE_REQUEST_SUCCESS))
					{

						Log.e(Constants.TAG, "TICKET:" + detailBean.getData().toString() + "");

						if (EmptyUtils.isEmpty(detailBean.getData()))
						{
							rl_recommend_ticket.setVisibility(View.GONE);
						}

						ll_shop_tickets.removeAllViews();
						for (int i = 0; i < detailBean.getData().size(); i++)
						{

							ShopMerchantBean.DataBean items = detailBean.getData().get(i);
							/////////////// VIEW状态更新/////////////////////////////
							LinearLayout view = (LinearLayout) View.inflate(AllianceShopDetailActivity.this,
									R.layout.alliance_ticket_item, null);
							TextView tv_use_ticket = (TextView) view.findViewById(R.id.tv_use_ticket);
							TextView tv_use_scope = (TextView) view.findViewById(R.id.tv_use_scope);
							TextView tv_time_scope = (TextView) view.findViewById(R.id.tv_time_scope);
							RelativeLayout rl_parent = (RelativeLayout) view.findViewById(R.id.rl_parent);
							TextView tv_ticket_amount = (TextView) view.findViewById(R.id.tv_ticket_amount);
							TextView tv_ticket_limit = (TextView) view.findViewById(R.id.tv_ticket_limit);
							tv_use_ticket.setOnClickListener((View v) -> {
								if (!LoginDataManager.getsInstance(AllianceShopDetailActivity.this).isLogin())
								{
									ArmsUtils.makeText(AllianceShopDetailActivity.this, getResources().getString(R.string.not_login));
									ARouter.getInstance()
											.build(ARouterPaths.LOGIN_MAIN_MINE)
											.withString(Consts.LOGIN_SOURCE_KEY,"店铺详情")
											.navigation(AllianceShopDetailActivity.this);
									tv_use_ticket.setText("立即\n领取");
									tv_use_ticket.setEnabled(true);
									tv_use_ticket.setTextColor(getResources().getColor(R.color.white));
								}
								else
								{
									// TODO 领取优惠券
									String user_id = LoginDataManager.getsInstance(AllianceShopDetailActivity.this)
											.getWeixinUnionId();
									Log.e(Constants.TAG, "union_id" + user_id);
									Long ticket_id = Long.valueOf(items.getId());
									requestTicket(allianceService, user_id + "", ticket_id, tv_use_ticket, view);
								}
							});

							float amounts = items.getDiscounted();
							String amount_str = amounts + "";

							String middle = amount_str.substring(amount_str.indexOf("."));
							int amount = items.getDiscounted().intValue();
							if (items.getReceType() == 1)
							{
								tv_use_ticket.setText("已经\n领取");
								tv_use_ticket.setEnabled(false);
								tv_use_ticket.setTextColor(getResources().getColor(R.color.text_color_ticket_received));
								rl_parent.setBackgroundResource(R.drawable.bg_ticket_second_nomal);
								tv_ticket_amount
										.setTextColor(getResources().getColor(R.color.text_color_ticket_received));
								tv_ticket_limit
										.setTextColor(getResources().getColor(R.color.text_color_ticket_received));
								tv_use_scope.setTextColor(getResources().getColor(R.color.text_color_ticket_received));
								tv_time_scope.setTextColor(getResources().getColor(R.color.text_color_ticket_received));
								RxTextTool.getBuilder("¥ ", AllianceShopDetailActivity.this).append(amount + "")
										.setForegroundColor(AllianceShopDetailActivity.this.getResources()
												.getColor(R.color.text_color_ticket_received))
										.setProportion(2.5f).append(middle).setProportion(1f)
										.append("  " + "满" + items.getMinPrice() + "" + "使用").setProportion(0.7f)
										.into(tv_ticket_amount);
							}
							else
							{
								tv_use_ticket.setText("立即\n领取");
								tv_use_ticket.setEnabled(true);
								tv_use_ticket.setTextColor(getResources().getColor(R.color.white));
								rl_parent.setBackgroundResource(R.drawable.bg_ticket_second);
								tv_ticket_amount.setTextColor(getResources().getColor(R.color.white));
								tv_ticket_limit.setTextColor(getResources().getColor(R.color.white));
								tv_use_scope.setTextColor(getResources().getColor(R.color.white));
								tv_time_scope.setTextColor(getResources().getColor(R.color.white));
								RxTextTool.getBuilder("¥ ", AllianceShopDetailActivity.this).append(amount + "")
										.setForegroundColor(
												AllianceShopDetailActivity.this.getResources().getColor(R.color.white))
										.setProportion(2.5f).append(middle).setProportion(1f)
										.append("  " + "满" + items.getMinPrice() + "" + "使用").setProportion(0.7f)
										.into(tv_ticket_amount);
							}

							if (items.getUseType() == 1)
							{
								tv_use_scope.setText("全平台可使用");
							}
							else if (items.getUseType() == 2)
							{
								tv_use_scope.setText("仅限本店使用");
							}

							tv_time_scope.setText(DateUtil.getStandardTime(items.getBeginTime()) + "~"
									+ DateUtil.getStandardTime(items.getEndTime()));

							/////////////// VIEW状态更新/////////////////////////////

							if (i == 0)
							{
								LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
										ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
								params.setMargins((int) DeviceUtils.dip2px(AllianceShopDetailActivity.this, 12), 0,
										(int) DeviceUtils.dip2px(AllianceShopDetailActivity.this, 0), 0);
								view.setLayoutParams(params);
							}

							ll_shop_tickets.addView(view);
						}
					}
					else
					{
						rl_recommend_ticket.setVisibility(View.GONE);
					}
				}
				else
				{
					rl_recommend_ticket.setVisibility(View.GONE);
				}

			}

			@Override
			public void onFailure(Call<ShopMerchantBean> call, Throwable t)
			{
				rl_recommend_ticket.setVisibility(View.GONE);
			}
		});

	}

	private void requestTicket(AllianceDetailService allianceService, String user_id, Long ticket_id,
			TextView tv_use_ticket, LinearLayout view)
	{

		///////////////// POST参数//////////////////
		Map<String, Object> params = new HashMap<>();
		params.put("id", ticket_id);
		params.put("mediaId", Constants.MEDIA_ID);
		params.put("mediaUserId", user_id);
		params.put("clientId", "android");
		params.put("clientVersion", DeviceUtils.getAppVersionName(this));
		params.put("timeStamp", System.currentTimeMillis() + "");
		params.put("nonceStr", RandomStr.getRandomStr(12));
		Gson gson = new Gson();
		String strEntity = gson.toJson(params);
		Logger.e(strEntity);
		RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),
				strEntity);

		///////////////// 获取商家和用户对应优惠券列表//////////////////
		Call<GetTicketInfo> call_recommend_goods = allianceService.getCommonTicket(requestBody);
		call_recommend_goods.enqueue(new Callback<GetTicketInfo>()
		{
			@Override
			public void onResponse(Call<GetTicketInfo> call, Response<GetTicketInfo> response)
			{
				if (response.body() != null)
				{
					GetTicketInfo detailBean = response.body();
					Log.e(Constants.TAG, detailBean.toString());
					if (detailBean.getCode().equals(Constants.ALLIANCE_REQUEST_SUCCESS))
					{
						// Log.e(Constants.TAG,detailBean.getMessage());
						tv_use_ticket.setText("已经\n领取");
						tv_use_ticket.setEnabled(false);
						tv_use_ticket.setTextColor(getResources().getColor(R.color.text_color_ticket_received));
						ArmsUtils.makeText(AllianceShopDetailActivity.this,"领取成功，微信扫码消费自动优惠");

						TextView tv_use_scope = (TextView) view.findViewById(R.id.tv_use_scope);
						TextView tv_time_scope = (TextView) view.findViewById(R.id.tv_time_scope);
						RelativeLayout rl_parent = (RelativeLayout) view.findViewById(R.id.rl_parent);
						TextView tv_ticket_amount = (TextView) view.findViewById(R.id.tv_ticket_amount);
						TextView tv_ticket_limit = (TextView) view.findViewById(R.id.tv_ticket_limit);
						rl_parent.setBackgroundResource(R.drawable.bg_ticket_second_nomal);
						tv_ticket_amount.setTextColor(getResources().getColor(R.color.text_color_ticket_received));
						tv_ticket_limit.setTextColor(getResources().getColor(R.color.text_color_ticket_received));
						tv_use_scope.setTextColor(getResources().getColor(R.color.text_color_ticket_received));
						tv_time_scope.setTextColor(getResources().getColor(R.color.text_color_ticket_received));
						// Intent intent = new Intent(Intent.ACTION_MAIN);
						// ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
						// intent.addCategory(Intent.CATEGORY_LAUNCHER);
						// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						// intent.setComponent(cmp);
						// startActivity(intent);

					}
					else if (detailBean.getCode().equals(Constants.ALLIANCE_REQUEST_FAIL))
					{
						Logger.e(detailBean.getMsg());

						if(!TextUtils.isEmpty(detailBean.getMsg())){
							ArmsUtils.makeText(AllianceShopDetailActivity.this,detailBean.getMsg());
						}


						// ToastUtils.getInstanc(AllianceShopDetailActivity.this).showToast(detailBean.getMsg());
					}
				}
			}

			@Override
			public void onFailure(Call<GetTicketInfo> call, Throwable t)
			{
				ArmsUtils.makeText(AllianceShopDetailActivity.this,getResources().getString(R.string.network_error));
			}
		});
	}

	@Subscribe // 订阅事件FirstEvent
	public void onUpdateUer(UpdateLevelEvent updateLevelEvent)
	{
		Log.e(Constants.TAG, "登录成功回调");
		user_entity_id = LoginDataManager.getsInstance(AllianceShopDetailActivity.this).getWeixinUnionId();
		getMecharntTicket();
	}

	@Subscribe // 订阅事件FirstEvent
	public void onGetShare(LoginStatusEvent loginStatusEvent)
	{
		if(detailBean.getData()!=null && !"".equals(detailBean.getData())){
			getSharePer(detailBean.getData().getPerCapita() + "");
		}

	}


	/**
	 * 分享图片大小不能大于32kb
	 */
	private Bitmap imageZoom(Bitmap bitMap) {
		//图片允许最大空间   单位：KB
		double maxSize =32;
		//将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		//将字节换成KB
		double mid = b.length/1024;
		//判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
		if (mid > maxSize) {
			//获取bitmap大小 是允许最大大小的多少倍
			double i = mid / maxSize;
			//开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			bitMap = zoomImage(bitMap, bitMap.getWidth() / Math.sqrt(i),
					bitMap.getHeight() / Math.sqrt(i));
		}

		return  bitMap;
	}


	/***
	 * 图片的缩放方法
	 *
	 * @param bgimage
	 *            ：源图片资源
	 * @param newWidth
	 *            ：缩放后宽度
	 * @param newHeight
	 *            ：缩放后高度
	 * @return
	 */
	public  Bitmap zoomImage(Bitmap bgimage, double newWidth,
							 double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}

}
