package com.mujirenben.android.xsh.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.entity.OfficeResult;
import com.mujirenben.android.xsh.entity.OfflineEntity;
import com.mujirenben.android.xsh.entity.Share;
import com.mujirenben.android.xsh.service.OfficeApi;
import com.mujirenben.android.xsh.service.RetrofitSingle;
import com.mujirenben.android.xsh.utils.Base64;
import com.mujirenben.android.xsh.utils.CodeUtils;
import com.mujirenben.android.xsh.utils.ShareUtil;
import com.mujirenben.android.common.arounter.ARouterPaths;
import com.mujirenben.android.common.base.BaseApplication;
import com.mujirenben.android.common.constants.Consts;
import com.mujirenben.android.common.http.HttpParamUtil;
import com.mujirenben.android.common.user.LoginDataManager;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.common.util.StatusBarUtil;
import com.mujirenben.android.common.util.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Create by weiyq on 2018/8/14
 */
public class ShareActivity extends Activity implements View.OnClickListener
{
	private AppCompatTextView	rule;
	private PopupWindow			sharepop;
	private LinearLayout		rel;
	private TextView			tv_wxchat;
	private TextView			tv_wxfriend;
	private IWXAPI				api;
	private CardView			shareLayout;
	private String				imgFile;
	private ArrayList<Uri>		imageUris			= new ArrayList<Uri>();
	private String				shopNameStr;
	private String				shopAddressStr;
	private String				shopThumbStr;
	private String				shopId;
	private String				quan1Str;
	private String				quan2Str;
	private String				quan3Str;
	private String				perCapitaStr;

	private AppCompatImageView	shopThumb;
	private AppCompatTextView	shopName;
	private AppCompatTextView	shopAddress;

	private TextView			tv_cancel;
	private String				thumbData			= "";
	private static final int	THUMB_SIZE_WIDTH	= 150;
	private static final int	THUMB_SIZE_HEIGHT	= 120;
	private AppCompatTextView	quan1;
	private AppCompatTextView	quan2;
	private AppCompatTextView	quan3;

	private WXMediaMessage		msg;
	private AppCompatTextView	perCapita;
	private AppCompatImageView	erCode;
	private String				userId				= "";

	private OfficeResult		mPayVipResult		= new OfficeResult();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);


		setContentView(R.layout.hrz_alliance_share);
		Window window = this.getWindow();
		userId = LoginDataManager.getsInstance(this).getUserId() + "";

		// 取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		// 需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		StatusBarUtil.setStatusBarColor(this, R.color.gray_61);
		api = WXAPIFactory.createWXAPI(this, "wxb82c21dcc13d3fa7", true);
		api.registerApp("wxb82c21dcc13d3fa7");
		tv_wxchat = (TextView) findViewById(R.id.tv_wxchat);
		tv_wxfriend = (TextView) findViewById(R.id.tv_wxfriend);

		tv_cancel = (TextView) findViewById(R.id.tv_cancel);
		tv_cancel.setOnClickListener((v) -> {
			this.finish();
			this.overridePendingTransition(0, R.anim.out_bottomtop);
		});

		quan1 = (AppCompatTextView) findViewById(R.id.quan1);
		quan2 = (AppCompatTextView) findViewById(R.id.quan2);
		// quan3 = (AppCompatTextView) findViewById(R.id.quan3);

		perCapita = (AppCompatTextView) findViewById(R.id.perCapita);

		shopNameStr = getIntent().getExtras().getString("shopName");
		shopAddressStr = getIntent().getExtras().getString("shopAddress");
		shopThumbStr = getIntent().getExtras().getString("shopThumb");
		shopId = getIntent().getExtras().getString("shopId");

		perCapitaStr = getIntent().getExtras().getString("perCapita");

		quan1Str = getIntent().getExtras().getString("ticket1");
		quan2Str = getIntent().getExtras().getString("ticket2");
		// quan3Str = getIntent().getExtras().getString("ticket3");

		if (!"".equals(quan1Str) && null != quan1Str)
		{
			quan1.setVisibility(View.VISIBLE);
			quan1.setText(quan1Str);
		}
		if (!"".equals(quan2Str) && null != quan2Str)
		{
			quan2.setVisibility(View.VISIBLE);
			quan2.setText(quan2Str);
		}
		// if (!"".equals(quan3Str) && null != quan3Str)
		// {
		// quan3.setVisibility(View.VISIBLE);
		// quan3.setText(quan3Str);
		// }

		shopName = (AppCompatTextView) findViewById(R.id.shopName);
		shopAddress = (AppCompatTextView) findViewById(R.id.shopAddress);
		shopThumb = (AppCompatImageView) findViewById(R.id.shopPic);

		shopName.setText(shopNameStr);
		shopAddress.setText(shopAddressStr);
		tv_wxfriend.setClickable(false);
		tv_wxfriend.setFocusable(false);

		Glide.with(this).load(shopThumbStr).listener(new RequestListener<Drawable>()
		{
			@Override
			public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target,
					boolean isFirstResource)
			{
				return false;
			}

			@Override
			public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
					DataSource dataSource, boolean isFirstResource)
			{
				tv_wxfriend.setClickable(true);
				tv_wxfriend.setFocusable(true);
				return false;
			}
		}).into(shopThumb);

		shareLayout = (CardView) findViewById(R.id.shareLayout);

		rel = (LinearLayout) findViewById(R.id.rel);
		rule = (AppCompatTextView) findViewById(R.id.rule);
		rule.setOnClickListener(this);
		tv_wxchat.setOnClickListener(this);
		tv_wxfriend.setOnClickListener(this);
		erCode = (AppCompatImageView) findViewById(R.id.erCode);
		getInfo();

	}

	private void getInfo()
	{

		HashMap<String, String> mMap = new HashMap<String, String>();
		mMap.put("merchantId", shopId + "");
		mMap.put("money", perCapitaStr + "");

		DecimalFormat df = new DecimalFormat("######0.00"); // 保留两位小数点
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

					if(mPayVipResult.getCode().equals("90009")){
						ARouter.getInstance()
								.build(ARouterPaths.LOGIN_MAIN_MINE)
								.withString(Consts.LOGIN_SOURCE_KEY,"店铺分享")
								.navigation(ShareActivity.this);

					}else{
						DecimalFormat df = new DecimalFormat("######0.00"); // 保留两位小数点
						if(!"".equals(mPayVipResult.getData()) && mPayVipResult.getData()!=null ){
							if (df.format(Float.parseFloat(perCapitaStr)).equals("0.00"))
							{
								perCapita.setText("人均 " + "50.00" + " 元，预估赚 "
										+ df.format(Float.parseFloat(mPayVipResult.getData().getProfitMoney() + "")) + " 元");
							}
							else
							{
								perCapita.setText("人均 " + df.format(Float.parseFloat(perCapitaStr)) + " 元，预估赚 "
										+ df.format(Float.parseFloat(mPayVipResult.getData().getProfitMoney() + "")) + " 元");
							}

							if (!"".equals(mPayVipResult.getData().getMiniProgramThumb())
									&& null != mPayVipResult.getData().getMiniProgramThumb())
							{

								erCode.setImageBitmap(CodeUtils.createImageBlack(
										mPayVipResult.getData().getMiniProgramThumb() + "id=" + shopId + "&shareUserId=" + userId
												+ "",
										300, 300,
										BitmapFactory.decodeResource(getResources(), R.drawable.hrz_index_menu_lianmeng))); // 创建二维码
							}

						}
					}



				}

			}

			@Override
			public void onFailure(Call<OfficeResult> call, Throwable t)
			{

				ArmsUtils.makeText(ShareActivity.this,"请求失败");
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			this.finish();
			this.overridePendingTransition(0, R.anim.out_bottomtop);
		}
		return super.onKeyDown(keyCode, event);

	}

	@Override
	public void onClick(View v)
	{
		if (v.getId() == R.id.rule)
		{
			initPopUpWindow();
		}
		if (v.getId() == R.id.tv_wxchat)
		{

			if(mPayVipResult == null || mPayVipResult.getData() == null) return;
			// http://videocdn.tlgn365.com/thumb/2018-03-06/15203456304022.png
			WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();

			if(mPayVipResult.getData()==null|| "".equals(mPayVipResult.getData()) ){
				return;

			}
			miniProgramObj.webpageUrl = mPayVipResult.getData().getUrl(); // 兼容低版本的网页链接
			miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
			miniProgramObj.userName = mPayVipResult.getData().getMicid(); // 小程序原始id
			miniProgramObj.path = mPayVipResult.getData().getMicpath() + "id=" + shopId + "&shareUserId="+ userId + "";
			msg = new WXMediaMessage(miniProgramObj);
			msg.title = "发现一个好店：" + shopNameStr; // 小程序消息title
			msg.description = mPayVipResult.getData().getText(); // 小程序消息desc

			// msg.thumbData = getThumb(); // 小程序消息封面图片，小于128k

			if ("".equals(thumbData))
			{
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							if (!"".equals(shopThumbStr) && null != shopThumbStr)
							{

								new Thread(new Runnable()
								{
									@Override
									public void run()
									{
										byte[] data = new byte[0];
										try
										{
											data = getBytes(new URL(shopThumbStr).openStream());
											Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
											// Bitmap bmp =
											// Glide.with(getApplicationContext()).load(shopThumbStr).asBitmap() // 必须
											// .into(500, 500).get();
											Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE_WIDTH,
													THUMB_SIZE_HEIGHT, true);
											bmp.recycle();
											Message message = new Message();
											message.what = 4;
											message.obj = thumbBmp;
											myHandler.sendMessageDelayed(message, 1000);
										}
										catch (IOException e)
										{
											e.printStackTrace();
										}
									}
								}).start();
								// Bitmap bmp = Glide.with(getApplicationContext()).load(shopThumbStr).asBitmap() // 必须
								// .into(500, 500).get();
								// Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE_WIDTH, THUMB_SIZE_HEIGHT,
								// true);
								// bmp.recycle();
								// Message message = new Message();
								// message.what = 4;
								// message.obj = thumbBmp;
								// myHandler.sendMessageDelayed(message, 1000);
							}
							else
							{

								new Thread(new Runnable()
								{
									@Override
									public void run()
									{
										byte[] data = new byte[0];
										try
										{
											data = getBytes(new URL(mPayVipResult.getData().getThumb()).openStream());
											Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
											// Bitmap bmp =
											// Glide.with(getApplicationContext()).load(shopThumbStr).asBitmap() // 必须
											// .into(500, 500).get();
											Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE_WIDTH,
													THUMB_SIZE_HEIGHT, true);
											bmp.recycle();
											Message message = new Message();
											message.what = 4;
											message.obj = thumbBmp;
											myHandler.sendMessageDelayed(message, 1000);
										}
										catch (IOException e)
										{
											e.printStackTrace();
										}
									}
								}).start();

							}

						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}).start();
			}
			else
			{
				if (thumbData.contains("base64,"))
				{
					if (!"".equals(thumbData.split("base64,")[1]))
					{
						msg.thumbData = Base64.decode(thumbData.split("base64,")[1]);

					}
				}
				else
				{
					msg.thumbData = Base64.decode(thumbData);
				}
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = buildTransaction("webpage");
				req.message = msg;
				req.scene = SendMessageToWX.Req.WXSceneSession; // 目前支持会话
				api.sendReq(req);

			}

		}
		if (v.getId() == R.id.tv_wxfriend)
		{

			shareCircle();

		}

	}

	public void shareCircle()
	{
		shareLayout.setDrawingCacheEnabled(true);
		shareLayout.buildDrawingCache();
		myHandler.sendEmptyMessage(1);
	}

	private String buildTransaction(final String type)
	{
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}

	/**
	 * 初始化分享弹窗
	 */
	@SuppressWarnings("deprecation")
	private void initPopUpWindow()
	{
		LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View menuView = (View) mLayoutInflater.inflate(R.layout.dialog_rule, null, true);// 弹出窗口包含的视图

		sharepop = new PopupWindow(menuView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT,
				true);
		sharepop.setTouchable(true);
		sharepop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		sharepop.setOutsideTouchable(true);
		ColorDrawable colorDrawable = new ColorDrawable(00000000);
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		sharepop.setBackgroundDrawable(colorDrawable);
		sharepop.setBackgroundDrawable(new BitmapDrawable());
		sharepop.setTouchInterceptor(new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1)
			{
				// TODO Auto-generated method stub
				return false;
			}
		});
		if (this != null && !this.isFinishing())
		{
			TextView commit = (TextView) menuView.findViewById(R.id.commit);
			commit.setOnClickListener((b) -> {
				sharepop.dismiss();
			});
			sharepop.showAtLocation(rel, Gravity.BOTTOM, 0, 0);
		}
	}

	Handler myHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msgs)
		{
			super.handleMessage(msgs);
			if (msgs.what == 1)
			{
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						Bitmap bmp = shareLayout.getDrawingCache(); // 获取图片
						saveBitmap(bmp); // 保存图片
						shareLayout.destroyDrawingCache(); // 保存过后释放资源
						myHandler.sendEmptyMessage(2);
					}
				}).start();

			}
			if (msgs.what == 2)
			{
				doWxFriendShare();
			}

			if (msgs.what == 4)
			{
				Bitmap thumbBmp = (Bitmap) msgs.obj;
				msg.thumbData = ShareUtil.bmpToByteArray(thumbBmp, true);
				// 小程序消息封面图片，小于128k

				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = buildTransaction("webpage");
				req.message = msg;
				req.scene = SendMessageToWX.Req.WXSceneSession; // 目前支持会话
				api.sendReq(req);
			}

		}
	};

	private void saveBitmap(Bitmap bitmap)
	{
		imageUris.clear();
		if (!new File(BaseApplication.SDIMGFILE).exists())
		{
			new File(BaseApplication.SDIMGFILE).mkdir();
		}
		imgFile = BaseApplication.SDIMGFILE + "/" + System.currentTimeMillis() + "shareCircle" + "hrz.jpg";
		Uri imageUri = Uri.fromFile(new File(imgFile));
		imageUris.add(imageUri);

		FileOutputStream out;
		try
		{
			out = new FileOutputStream(imgFile);
			if (out != null)
			{
				if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out))
				{
					out.flush();
					out.close();
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 分享到朋友圈
	 */
	private void doWxFriendShare()
	{
		// showToast("图片已经保存",0);
		Intent shareIntent = new Intent();
		// 1 Finals 2016-11-2 调用系统分享
		shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
		shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		try
		{
			shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
			shareIntent.setType("image/*");
			// 3 Finals 2016-11-2 指定选择微信朋友圈。
			ComponentName mComponentName = new ComponentName("com.tencent.mm",
					"com.tencent.mm.ui.tools.ShareToTimeLineUI");
			shareIntent.setComponent(mComponentName);
			// 4 Finals 2016-11-2 开始分享。
			startActivity(Intent.createChooser(shareIntent, "分享到朋友圈"));

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (new File(BaseApplication.SDIMGFILE).exists())
		{
			File file = new File(BaseApplication.SDIMGFILE);
			List<File> fileList = getFile(file);
			for (int i = 0; i < fileList.size(); i++)
			{
				if (fileList.get(i) != null)
				{
					File fileFild = fileList.get(i);
					if (fileFild.getPath().contains("shareCircle"))
					{
						fileFild.delete();
					}
				}
			}
		}
	}

	private List<File> mFileList = new ArrayList<>();

	public List<File> getFile(File file)
	{
		File[] fileArray = file.listFiles();
		if (fileArray != null)
		{
			for (File f : fileArray)
			{
				if (f.isFile())
				{
					mFileList.add(f);
				}
				else
				{
					getFile(f);
				}
			}
		}
		return mFileList;
	}

	public static byte[] getBytes(InputStream is) throws IOException
	{
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024]; // 用数据装
		int len = -1;
		while ((len = is.read(buffer)) != -1)
		{
			outstream.write(buffer, 0, len);
		}
		outstream.close();
		// 关闭流一定要记得。
		return outstream.toByteArray();
	}

}
