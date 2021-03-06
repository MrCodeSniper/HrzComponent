package com.mujirenben.android.xsh.utils.jcvideolib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.ui.LoginActivity;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.constant.Constants;
import com.mujirenben.android.xsh.entity.ShangXinVideoBean;
import com.mujirenben.android.common.util.ArmsUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public abstract class JCVideoPlayer extends FrameLayout implements View.OnClickListener, View.OnTouchListener,
		SeekBar.OnSeekBarChangeListener, JCMediaManager.JCMediaPlayerListener, TextureView.SurfaceTextureListener
{

	public static final String TAG										= "JieCaoVideoPlayer";
	public int						mCurrentState							= -1;					// -1相当于null
	public static final int			CURRENT_STATE_NORMAL					= 0;
	public static final int			CURRENT_STATE_PREPAREING				= 1;
	public static final int			CURRENT_STATE_PLAYING					= 2;
	protected static final int		CURRENT_STATE_PLAYING_BUFFERING_START	= 3;
	public static final int			CURRENT_STATE_PAUSE						= 5;
	protected static final int		CURRENT_STATE_AUTO_COMPLETE				= 6;
	protected static final int		CURRENT_STATE_ERROR						= 7;
	protected static int			BACKUP_PLAYING_BUFFERING_STATE			= -1;

	protected boolean				mTouchingProgressBar					= false;
	protected boolean				mIfCurrentIsFullscreen					= false;
	protected boolean				mIfFullscreenIsDirectly					= false;				// mIfCurrentIsFullscreen
																									// should be true
																									// first
	protected static boolean		IF_FULLSCREEN_FROM_NORMAL				= false;				// to prevent
																									// infinite looping
	public static boolean			IF_RELEASE_WHEN_ON_PAUSE				= true;
	protected static long			CLICK_QUIT_FULLSCREEN_TIME				= 0;
	public static final int			FULL_SCREEN_NORMAL_DELAY				= 2000;

	public ImageView startButton;
	public SeekBar progressBar;
	public ImageView fullscreenButton;
	public TextView currentTimeTextView, totalTimeTextView;
	public ViewGroup textureViewContainer;
	public ViewGroup topContainer, bottomContainer;
	public JCResizeTextureView		textureView;
	public Surface mSurface;
	public LinearLayout ll_pro;
	public LinearLayout ll_caozuo;
	public ImageView iv_pro;
	public TextView tv_pro;
	public TextView tv_price;
	public TextView tv_fanli;
	public TextView tv_fan_bg;
	public TextView tv_gaofan_bg;
	public TextView tv_buy;
	public ImageView iv_addCart;
	public ImageView iv_full;
	public int						type;															// 0大片1上新
	public int						position;
	private ShangXinVideoBean.Goods	submitGoods;
	protected String mUrl;
	protected Object[]				mObjects;
	protected Map<String, String> mMapHeadData							= new HashMap<>();
	protected boolean				mLooping								= false;
	public int						seekToInAdvance							= -1;
	public OnProClickListener		onProClickListener;

	protected static Timer UPDATE_PROGRESS_TIMER;
	protected ProgressTimerTask		mProgressTimerTask;

	protected static JCBuriedPoint	JC_BURIED_POINT;
	protected int					mScreenWidth;
	protected int					mScreenHeight;
	protected AudioManager mAudioManager;
	public OnProgressListener		onProgressListener;

	public void setOnProgress(OnProgressListener onProgressListener)
	{
		this.onProgressListener = onProgressListener;
	}

	public interface OnProgressListener
	{
		void onProgress(int progress);
	}

	protected int			mThreshold				= 80;
	protected float			mDownX;
	protected float			mDownY;
	protected boolean		mChangeVolume			= false;
	protected boolean		mChangePosition			= false;
	protected int			mDownPosition;
	protected int			mGestureDownVolume;
	protected int			mSeekTimePosition;						// change postion when finger up

	public static boolean	WIFI_TIP_DIALOG_SHOWED	= false;

	protected Handler mHandler				= new Handler();

	public JCVideoPlayer(Context context)
	{
		super(context);
		init(context);
	}

	public JCVideoPlayer(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	protected void init(Context context)
	{
		View.inflate(context, getLayoutId(), this);
		startButton = (ImageView) findViewById(R.id.start);
		fullscreenButton = (ImageView) findViewById(R.id.fullscreen);
		progressBar = (SeekBar) findViewById(R.id.progress);
		currentTimeTextView = (TextView) findViewById(R.id.current);
		totalTimeTextView = (TextView) findViewById(R.id.total);
		bottomContainer = (ViewGroup) findViewById(R.id.layout_bottom);
		textureViewContainer = (RelativeLayout) findViewById(R.id.surface_container);
		topContainer = (ViewGroup) findViewById(R.id.layout_top);
		ll_pro = (LinearLayout) findViewById(R.id.ll_pro);
		tv_pro = (TextView) findViewById(R.id.tv_pro);
		iv_pro = (ImageView) findViewById(R.id.iv_pro);
		tv_buy = (TextView) findViewById(R.id.tv_buy);
		tv_buy.setOnClickListener(this);
		iv_addCart = (ImageView) findViewById(R.id.iv_cart);
		iv_addCart.setOnClickListener(this);
		tv_price = (TextView) findViewById(R.id.tv_price);
		tv_fanli = (TextView) findViewById(R.id.tv_fanli);
		tv_fan_bg = (TextView) findViewById(R.id.tv_fan_bg);
		tv_gaofan_bg = (TextView) findViewById(R.id.tv_gaofan_bg);
		startButton.setOnClickListener(this);
		ll_caozuo = (LinearLayout) findViewById(R.id.ll_caozuo);
		iv_full = (ImageView) findViewById(R.id.iv_full);
		iv_full.setOnClickListener(this);
		fullscreenButton.setOnClickListener(this);
		progressBar.setOnSeekBarChangeListener(this);
		bottomContainer.setOnClickListener(this);
		textureViewContainer.setOnClickListener(this);

		textureViewContainer.setOnTouchListener(this);
		mScreenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
		mScreenHeight = getContext().getResources().getDisplayMetrics().heightPixels;

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mScreenHeight, mScreenHeight / 2);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		ll_pro.setLayoutParams(params);
		mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
	}

	public abstract int getLayoutId();

	protected static void setJcBuriedPoint(JCBuriedPoint jcBuriedPoint)
	{
		JC_BURIED_POINT = jcBuriedPoint;
	}

	public boolean setUp(String url, Object... objects)
	{
		if (isCurrentMediaListener()
				&& (System.currentTimeMillis() - CLICK_QUIT_FULLSCREEN_TIME) < FULL_SCREEN_NORMAL_DELAY)
			return false;
		mCurrentState = CURRENT_STATE_NORMAL;
		this.mUrl = url;
		this.mObjects = objects;
		setStateAndUi(CURRENT_STATE_NORMAL);
		return true;
	}

	public boolean setUp(String url, Map<String, String> mapHeadData, Object... objects)
	{
		if (setUp(url, objects))
		{
			this.mMapHeadData.clear();
			this.mMapHeadData.putAll(mapHeadData);
			return true;
		}
		return false;
	}

	public void setLoop(boolean looping)
	{
		this.mLooping = looping;
	}

	public void stop()
	{
		Log.d(TAG, "pauseVideo [" + this.hashCode() + "] ");
		JCMediaManager.instance().mediaPlayer.pause();
		setStateAndUi(CURRENT_STATE_PAUSE);
		if (JC_BURIED_POINT != null && isCurrentMediaListener())
		{
			if (mIfCurrentIsFullscreen)
			{
				JC_BURIED_POINT.onClickStopFullscreen(mUrl, mObjects);
			}
			else
			{
				JC_BURIED_POINT.onClickStop(mUrl, mObjects);
			}
		}
	}

	public void start()
	{
		if (JC_BURIED_POINT != null && isCurrentMediaListener())
		{
			if (mIfCurrentIsFullscreen)
			{
				JC_BURIED_POINT.onClickResumeFullscreen(mUrl, mObjects);
			}
			else
			{
				JC_BURIED_POINT.onClickResume(mUrl, mObjects);
			}
		}
		if (JCMediaManager.instance().mediaPlayer != null)
		{
			JCMediaManager.instance().mediaPlayer.start();
		}

		setStateAndUi(CURRENT_STATE_PLAYING);
	}

	// set ui
	protected void setStateAndUi(int state)
	{
		mCurrentState = state;
		switch (mCurrentState)
		{
		case CURRENT_STATE_NORMAL:
			if (isCurrentMediaListener())
			{
				cancelProgressTimer();
				JCMediaManager.instance().releaseMediaPlayer();
			}
			break;
		case CURRENT_STATE_PREPAREING:
			resetProgressAndTime();
			break;
		case CURRENT_STATE_PLAYING:
			startProgressTimer();
			break;
		case CURRENT_STATE_PAUSE:
			startProgressTimer();
			break;
		case CURRENT_STATE_ERROR:
			if (isCurrentMediaListener())
			{
				JCMediaManager.instance().releaseMediaPlayer();
			}
			break;
		case CURRENT_STATE_AUTO_COMPLETE:
			cancelProgressTimer();
			progressBar.setProgress(100);
			currentTimeTextView.setText(totalTimeTextView.getText());
			Intent intent = new Intent();
			intent.setAction(Constants.BroadCast.VIDEO_COMPLETE);
			getContext().sendBroadcast(intent);
			break;
		}
	}



	public void setType(int type)
	{
		this.type = type;
	}

	@Override
	public void onClick(View v)
	{
		int i = v.getId();
		if (i == R.id.start)
		{
			Log.i(TAG, "onClick start [" + this.hashCode() + "] ");
			if (TextUtils.isEmpty(mUrl))
			{
				ArmsUtils.makeText(getContext(), getResources().getString(R.string.no_url));
				return;
			}
			if (mCurrentState == CURRENT_STATE_NORMAL || mCurrentState == CURRENT_STATE_ERROR)
			{
				if (!mUrl.startsWith("file") && !JCUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED)
				{
					showWifiDialog();
					return;
				}

				startButtonLogic();
			}
			else if (mCurrentState == CURRENT_STATE_PLAYING)
			{
				Log.d(TAG, "pauseVideo [" + this.hashCode() + "] ");
				JCMediaManager.instance().mediaPlayer.pause();
				setStateAndUi(CURRENT_STATE_PAUSE);
				if (mIfCurrentIsFullscreen)
				{
					//setPro();

				}
				if (JC_BURIED_POINT != null && isCurrentMediaListener())
				{

					if (mIfCurrentIsFullscreen)
					{

						JC_BURIED_POINT.onClickStopFullscreen(mUrl, mObjects);
					}
					else
					{
						JC_BURIED_POINT.onClickStop(mUrl, mObjects);
					}
				}

			}
			else if (mCurrentState == CURRENT_STATE_PAUSE)
			{
				if (mIfCurrentIsFullscreen)
				{
					ll_pro.setVisibility(View.GONE);
					ll_caozuo.setVisibility(View.GONE);
				}
				if (JC_BURIED_POINT != null && isCurrentMediaListener())
				{
					if (mIfCurrentIsFullscreen)
					{
						JC_BURIED_POINT.onClickResumeFullscreen(mUrl, mObjects);
					}
					else
					{
						JC_BURIED_POINT.onClickResume(mUrl, mObjects);
					}
				}
				JCMediaManager.instance().mediaPlayer.start();
				setStateAndUi(CURRENT_STATE_PLAYING);
			}
			else if (mCurrentState == CURRENT_STATE_AUTO_COMPLETE)
			{
				startButtonLogic();
			}
		}
		else if (i == R.id.fullscreen)
		{
			Log.i(TAG, "onClick fullscreen [" + this.hashCode() + "] ");
			if (mCurrentState == CURRENT_STATE_AUTO_COMPLETE)
				return;
			if (mIfCurrentIsFullscreen)
			{
				// quit fullscreen
				ll_pro.setVisibility(View.GONE);
				ll_caozuo.setVisibility(View.GONE);
				backFullscreen();
			}
			else
			{
				Log.d(TAG, "toFullscreenActivity [" + this.hashCode() + "] ");
				if (JC_BURIED_POINT != null && isCurrentMediaListener())
				{
					JC_BURIED_POINT.onEnterFullscreen(mUrl, mObjects);
				}
				// to fullscreen
				JCMediaManager.instance().setDisplay(null);
				JCMediaManager.instance().setLastListener(this);
				JCMediaManager.instance().setListener(null);
				IF_FULLSCREEN_FROM_NORMAL = true;
				IF_RELEASE_WHEN_ON_PAUSE = false;
				JCFullScreenActivity.startActivityFromNormal(getContext(), mCurrentState, mUrl,
						JCVideoPlayer.this.getClass(), type, this.mObjects);
			}
		}
		else if (i == R.id.surface_container && mCurrentState == CURRENT_STATE_ERROR)
		{
			Log.i(TAG, "onClick surfaceContainer State=Error [" + this.hashCode() + "] ");
			if (JC_BURIED_POINT != null)
			{
				JC_BURIED_POINT.onClickStartError(mUrl, mObjects);
			}
			prepareVideo();
		}
		else if (i == R.id.iv_cart)
		{
			if (onProClickListener != null)
			{
				onProClickListener.proCart(submitGoods);
			}

		}
		else if (i == R.id.tv_buy)
		{
			if (onProClickListener != null)
			{
				onProClickListener.proBuy(submitGoods);
			}

		}
		else if (i == R.id.iv_full)
		{
			iv_full.setVisibility(View.GONE);
		}
	}

	public void setOnProClickListener(OnProClickListener onProClickListener)
	{
		this.onProClickListener = onProClickListener;
	}

	public interface OnProClickListener
	{
		void proBuy(ShangXinVideoBean.Goods goods);

		void proCart(ShangXinVideoBean.Goods goods);
	}

	public void showWifiDialog()
	{

	}

	private void startButtonLogic()
	{
		if (JC_BURIED_POINT != null && mCurrentState == CURRENT_STATE_NORMAL)
		{
			JC_BURIED_POINT.onClickStartIcon(mUrl, mObjects);
		}
		else if (JC_BURIED_POINT != null)
		{
			JC_BURIED_POINT.onClickStartError(mUrl, mObjects);
		}
		prepareVideo();
	}

	public void prepareVideo()
	{
		Log.d(TAG, "prepareVideo [" + this.hashCode() + "] ");
		if (JCMediaManager.instance().listener() != null)
		{
			JCMediaManager.instance().listener().onCompletion();
		}
		JCMediaManager.instance().setListener(this);
		addTextureView();
		AudioManager mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
		mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
				AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

		((Activity) getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		JCMediaManager.instance().prepare(mUrl, mMapHeadData, mLooping);
		setStateAndUi(CURRENT_STATE_PREPAREING);
	}

	public void prepareVideo(boolean isGone)
	{
		if (isGone)
		{
			topContainer.setVisibility(View.GONE);
		}

		Log.d(TAG, "prepareVideo [" + this.hashCode() + "] ");
		if (JCMediaManager.instance().listener() != null)
		{
			JCMediaManager.instance().listener().onCompletion();
		}
		JCMediaManager.instance().setListener(this);
		addTextureView();
		AudioManager mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
		mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
				AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

		((Activity) getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		JCMediaManager.instance().prepare(mUrl, mMapHeadData, mLooping);
		setStateAndUi(CURRENT_STATE_PREPAREING);
	}

	private static AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener()
	{
		@Override
		public void onAudioFocusChange(int focusChange)
		{
			switch (focusChange)
			{
			case AudioManager.AUDIOFOCUS_GAIN:
				break;
			case AudioManager.AUDIOFOCUS_LOSS:
				releaseAllVideos();
				break;
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
				if (JCMediaManager.instance().mediaPlayer.isPlaying())
				{
					JCMediaManager.instance().mediaPlayer.pause();
				}
				break;
			case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
				break;
			}
		}
	};

	protected void addTextureView()
	{
		Log.d(TAG, "addTextureView [" + this.hashCode() + "] ");
		if (textureViewContainer.getChildCount() > 0)
		{
			textureViewContainer.removeAllViews();
		}
		textureView = null;
		textureView = new JCResizeTextureView(getContext());
		textureView.setSurfaceTextureListener(this);

		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		textureViewContainer.addView(textureView, layoutParams);
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height)
	{
		Log.i(TAG, "onSurfaceTextureAvailable [" + this.hashCode() + "] ");
		mSurface = new Surface(surface);
		JCMediaManager.instance().setDisplay(mSurface);
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height)
	{

	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface)
	{
		surface.release();
		return true;
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface)
	{

	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		float x = event.getX();
		float y = event.getY();
		int id = v.getId();
		if (id == R.id.surface_container)
		{
			switch (event.getAction())
			{
			case MotionEvent.ACTION_DOWN:
				Log.i(TAG, "onTouch surfaceContainer actionDown [" + this.hashCode() + "] ");
				mTouchingProgressBar = true;

				mDownX = x;
				mDownY = y;
				mChangeVolume = false;
				mChangePosition = false;
				/////////////////////
				break;
			case MotionEvent.ACTION_MOVE:
				Log.i(TAG, "onTouch surfaceContainer actionMove [" + this.hashCode() + "] ");
				float deltaX = x - mDownX;
				float deltaY = y - mDownY;
				float absDeltaX = Math.abs(deltaX);
				float absDeltaY = Math.abs(deltaY);
				if (mIfCurrentIsFullscreen)
				{
					if (!mChangePosition && !mChangeVolume)
					{
						if (absDeltaX > mThreshold || absDeltaY > mThreshold)
						{
							cancelProgressTimer();
							if (absDeltaX >= mThreshold)
							{
								mChangePosition = true;
								mDownPosition = getCurrentPositionWhenPlaying();
								if (JC_BURIED_POINT != null && isCurrentMediaListener())
								{
									JC_BURIED_POINT.onTouchScreenSeekPosition(mUrl, mObjects);
								}
							}
							else
							{
								mChangeVolume = true;
								mGestureDownVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
								if (JC_BURIED_POINT != null && isCurrentMediaListener())
								{
									JC_BURIED_POINT.onTouchScreenSeekVolume(mUrl, mObjects);
								}
							}
						}
					}
				}
				if (mChangePosition)
				{
					int totalTimeDuration = getDuration();
					mSeekTimePosition = (int) (mDownPosition + deltaX * totalTimeDuration / mScreenWidth);
					if (mSeekTimePosition > totalTimeDuration)
						mSeekTimePosition = totalTimeDuration;
					String seekTime = JCUtils.stringForTime(mSeekTimePosition);
					String totalTime = JCUtils.stringForTime(totalTimeDuration);

					showProgressDialog(deltaX, seekTime, mSeekTimePosition, totalTime, totalTimeDuration);
				}
				if (mChangeVolume)
				{
					deltaY = -deltaY;
					int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
					int deltaV = (int) (max * deltaY * 3 / mScreenHeight);
					mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mGestureDownVolume + deltaV, 0);
					int volumePercent = (int) (mGestureDownVolume * 100 / max + deltaY * 3 * 100 / mScreenHeight);

					showVolumDialog(-deltaY, volumePercent);
				}

				break;
			case MotionEvent.ACTION_UP:
				Log.i(TAG, "onTouch surfaceContainer actionUp [" + this.hashCode() + "] ");
				mTouchingProgressBar = false;
				dismissProgressDialog();
				dismissVolumDialog();
				if (mChangePosition)
				{
					JCMediaManager.instance().mediaPlayer.seekTo(mSeekTimePosition);
					int duration = getDuration();
					int progress = mSeekTimePosition * 100 / (duration == 0 ? 1 : duration);
					progressBar.setProgress(progress);
				}
				/////////////////////
				startProgressTimer();
				if (JC_BURIED_POINT != null && isCurrentMediaListener())
				{
					if (mIfCurrentIsFullscreen)
					{
						JC_BURIED_POINT.onClickSeekbarFullscreen(mUrl, mObjects);
					}
					else
					{
						JC_BURIED_POINT.onClickSeekbar(mUrl, mObjects);
					}
				}
				break;
			}
		}
		return false;
	}

	protected void showProgressDialog(float deltaX, String seekTime, int seekTimePosition, String totalTime,
                                      int totalTimeDuration)
	{

	}

	protected void dismissProgressDialog()
	{

	}

	protected void showVolumDialog(float deltaY, int volumePercent)
	{

	}

	protected void dismissVolumDialog()
	{

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
	{
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar)
	{
		Log.i(TAG, "bottomProgress onStartTrackingTouch [" + this.hashCode() + "] ");
		cancelProgressTimer();
		ViewParent vpdown = getParent();
		while (vpdown != null)
		{
			vpdown.requestDisallowInterceptTouchEvent(true);
			vpdown = vpdown.getParent();
		}
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar)
	{
		Log.i(TAG, "bottomProgress onStopTrackingTouch [" + this.hashCode() + "] ");
		startProgressTimer();
		ViewParent vpup = getParent();
		while (vpup != null)
		{
			vpup.requestDisallowInterceptTouchEvent(false);
			vpup = vpup.getParent();
		}
		if (mCurrentState != CURRENT_STATE_PLAYING && mCurrentState != CURRENT_STATE_PAUSE)
			return;
		int time = seekBar.getProgress() * getDuration() / 100;
		JCMediaManager.instance().mediaPlayer.seekTo(time);
		Log.i(TAG, "seekTo " + time + " [" + this.hashCode() + "] ");
	}

	@Override
	public void onPrepared()
	{
		if (mCurrentState != CURRENT_STATE_PREPAREING)
			return;
		JCMediaManager.instance().mediaPlayer.start();
		if (seekToInAdvance != -1)
		{
			JCMediaManager.instance().mediaPlayer.seekTo(seekToInAdvance);
			seekToInAdvance = -1;
		}
		startProgressTimer();
		setStateAndUi(CURRENT_STATE_PLAYING);
	}

	@Override
	public void onAutoCompletion()
	{
		// make me normal first
		if (JC_BURIED_POINT != null && isCurrentMediaListener())
		{
			if (mIfCurrentIsFullscreen)
			{
				JC_BURIED_POINT.onAutoCompleteFullscreen(mUrl, mObjects);
			}
			else
			{
				JC_BURIED_POINT.onAutoComplete(mUrl, mObjects);
			}
		}
		setStateAndUi(CURRENT_STATE_AUTO_COMPLETE);
		if (textureViewContainer.getChildCount() > 0)
		{
			textureViewContainer.removeAllViews();
		}
		finishFullscreenActivity();
		if (IF_FULLSCREEN_FROM_NORMAL)
		{// 如果在进入全屏后播放完就初始化自己非全屏的控件
			IF_FULLSCREEN_FROM_NORMAL = false;
			JCMediaManager.instance().lastListener().onAutoCompletion();
		}
		JCMediaManager.instance().setLastListener(null);
		AudioManager mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
		mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
		((Activity) getContext()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public void onCompletion()
	{
		// make me normal first

		setStateAndUi(CURRENT_STATE_NORMAL);
		if (textureViewContainer.getChildCount() > 0)
		{
			textureViewContainer.removeAllViews();
		}
		// if fullscreen finish activity what ever the activity is directly or click fullscreen
		finishFullscreenActivity();

		if (IF_FULLSCREEN_FROM_NORMAL)
		{// 如果在进入全屏后播放完就初始化自己非全屏的控件
			IF_FULLSCREEN_FROM_NORMAL = false;
			JCMediaManager.instance().lastListener().onCompletion();
		}
		JCMediaManager.instance().setListener(null);
		JCMediaManager.instance().setLastListener(null);
		JCMediaManager.instance().currentVideoWidth = 0;
		JCMediaManager.instance().currentVideoHeight = 0;

		AudioManager mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
		mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
		((Activity) getContext()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public void onBufferingUpdate(int percent)
	{
		if (mCurrentState != CURRENT_STATE_NORMAL && mCurrentState != CURRENT_STATE_PREPAREING
				&& mCurrentState != CURRENT_STATE_PAUSE)
		{
			Log.v(TAG, "onBufferingUpdate " + percent + " [" + this.hashCode() + "] ");
			setTextAndProgress(percent);
		}
	}

	@Override
	public void onSeekComplete()
	{

	}

	@Override
	public void onError(int what, int extra)
	{
		Log.e(TAG, "onError " + what + " - " + extra + " [" + this.hashCode() + "] ");
		if (what != 38 && what != -38)
		{
			setStateAndUi(CURRENT_STATE_ERROR);
		}
	}

	@Override
	public void onInfo(int what, int extra)
	{
		Log.d(TAG, "onInfo what - " + what + " extra - " + extra);
		if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START)
		{
			BACKUP_PLAYING_BUFFERING_STATE = mCurrentState;
			setStateAndUi(CURRENT_STATE_PLAYING_BUFFERING_START);
			Log.d(TAG, "MEDIA_INFO_BUFFERING_START");
		}
		else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END)
		{
			if (BACKUP_PLAYING_BUFFERING_STATE != -1)
			{

				setStateAndUi(BACKUP_PLAYING_BUFFERING_STATE);
				BACKUP_PLAYING_BUFFERING_STATE = -1;
			}
			Log.d(TAG, "MEDIA_INFO_BUFFERING_END");
		}
	}

	@Override
	public void onVideoSizeChanged()
	{
		int mVideoWidth = JCMediaManager.instance().currentVideoWidth;
		int mVideoHeight = JCMediaManager.instance().currentVideoHeight;
		if (mVideoWidth != 0 && mVideoHeight != 0)
		{
			textureView.requestLayout();
		}
	}

	@Override
	public void onBackFullscreen()
	{
		mCurrentState = JCMediaManager.instance().lastState;
		setStateAndUi(mCurrentState);
		addTextureView();
	}

	protected void startProgressTimer()
	{
		cancelProgressTimer();
		UPDATE_PROGRESS_TIMER = new Timer();
		mProgressTimerTask = new ProgressTimerTask();
		UPDATE_PROGRESS_TIMER.schedule(mProgressTimerTask, 0, 300);
	}

	protected void cancelProgressTimer()
	{
		if (UPDATE_PROGRESS_TIMER != null)
		{
			UPDATE_PROGRESS_TIMER.cancel();
		}
		if (mProgressTimerTask != null)
		{
			mProgressTimerTask.cancel();
		}

	}

	protected class ProgressTimerTask extends TimerTask
	{
		@Override
		public void run()
		{
			if (mCurrentState == CURRENT_STATE_PLAYING || mCurrentState == CURRENT_STATE_PAUSE)
			{
				int position = getCurrentPositionWhenPlaying();
				int duration = getDuration();
				Log.v(TAG, "onProgressUpdate " + position + "/" + duration + " [" + this.hashCode() + "] ");
				mHandler.post(new Runnable()
				{
					@Override
					public void run()
					{
						setTextAndProgress(0);
					}
				});
			}
		}
	}

	protected int getCurrentPositionWhenPlaying()
	{
		int position = 0;
		if (mCurrentState == CURRENT_STATE_PLAYING || mCurrentState == CURRENT_STATE_PAUSE)
		{
			try
			{
				position = (int) JCMediaManager.instance().mediaPlayer.getCurrentPosition();
			}
			catch (IllegalStateException e)
			{
				e.printStackTrace();
				return position;
			}
		}
		return position;
	}

	protected int getDuration()
	{
		int duration = 0;
		try
		{
			duration = (int) JCMediaManager.instance().mediaPlayer.getDuration();
		}
		catch (IllegalStateException e)
		{
			e.printStackTrace();
			return duration;
		}
		return duration;
	}

	protected void setTextAndProgress(int secProgress)
	{
		position = getCurrentPositionWhenPlaying();
		int duration = getDuration();
		// if duration == 0 (e.g. in HLS streams) avoids ArithmeticException
		int progress = position * 100 / (duration == 0 ? 1 : duration);
		// if(getContext() instanceof JCFullScreenActivity){
		// Log.i(Contant.TAG,"是全屏");
		// }else{
		// Log.i(Contant.TAG,"不是全屏");
		// }
		if (onProgressListener != null)
		{
			onProgressListener.onProgress(position / 1000);
		}
		setProgressAndTime(progress, secProgress, position, duration);
	}

	protected void setProgressAndTime(int progress, int secProgress, int currentTime, int totalTime)
	{
		if (!mTouchingProgressBar)
		{
			if (progress != 0)
				progressBar.setProgress(progress);
		}
		if (secProgress > 95)
			secProgress = 100;
		if (secProgress != 0)
			progressBar.setSecondaryProgress(secProgress);
		currentTimeTextView.setText(JCUtils.stringForTime(currentTime));
		totalTimeTextView.setText(JCUtils.stringForTime(totalTime));
	}

	protected void resetProgressAndTime()
	{
		progressBar.setProgress(0);
		progressBar.setSecondaryProgress(0);
		currentTimeTextView.setText(JCUtils.stringForTime(0));
		totalTimeTextView.setText(JCUtils.stringForTime(0));
	}

	protected void quitFullScreenGoToNormal()
	{
		Log.d(TAG, "quitFullScreenGoToNormal [" + this.hashCode() + "] ");
		if (JC_BURIED_POINT != null && isCurrentMediaListener())
		{
			JC_BURIED_POINT.onQuitFullscreen(mUrl, mObjects);
		}
		JCMediaManager.instance().setDisplay(null);
		JCMediaManager.instance().setListener(JCMediaManager.instance().lastListener());
		JCMediaManager.instance().setLastListener(null);
		JCMediaManager.instance().lastState = mCurrentState;// save state
		if (JCMediaManager.instance().listener() != null)
		{
			JCMediaManager.instance().listener().onBackFullscreen();
		}

		if (mCurrentState == CURRENT_STATE_PAUSE)
		{
			JCMediaManager.instance().mediaPlayer.seekTo(JCMediaManager.instance().mediaPlayer.getCurrentPosition());
		}
		finishFullscreenActivity();
	}

	public void finishFullscreenActivity()
	{
		if (getContext() instanceof JCFullScreenActivity)
		{
			Log.d(TAG, "finishFullscreenActivity [" + this.hashCode() + "] ");
			((JCFullScreenActivity) getContext()).finish();
		}
	}

	public void backFullscreen()
	{
		Log.d(TAG, "quitFullscreen [" + this.hashCode() + "] ");
		IF_FULLSCREEN_FROM_NORMAL = false;
		if (mIfFullscreenIsDirectly)
		{
			JCMediaManager.instance().mediaPlayer.release();
			finishFullscreenActivity();
		}
		else
		{
			CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
			IF_RELEASE_WHEN_ON_PAUSE = false;
			quitFullScreenGoToNormal();
		}
	}

	public void noLogin()
	{
		IF_FULLSCREEN_FROM_NORMAL = false;
		if (mIfFullscreenIsDirectly)
		{
			JCMediaManager.instance().mediaPlayer.release();
			finishFullscreenActivity();
		}
		else
		{
			CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
			IF_RELEASE_WHEN_ON_PAUSE = false;
			quitFullScreenGoToNormal();
		}
		Intent intent = new Intent(getContext(), LoginActivity.class);
		getContext().startActivity(intent);

	}

	public static void releaseAllVideos()
	{
		if (IF_RELEASE_WHEN_ON_PAUSE)
		{
			Log.d(TAG, "releaseAllVideos");
			if (JCMediaManager.instance().listener() != null)
			{
				JCMediaManager.instance().listener().onCompletion();
			}
			JCMediaManager.instance().releaseMediaPlayer();
		}
		else
		{
			IF_RELEASE_WHEN_ON_PAUSE = true;
		}
	}

	/**
	 * if I am playing release me
	 */
	public void release()
	{
		if (isCurrentMediaListener()
				&& (System.currentTimeMillis() - CLICK_QUIT_FULLSCREEN_TIME) > FULL_SCREEN_NORMAL_DELAY)
		{
			Log.d(TAG, "release [" + this.hashCode() + "]");
			releaseAllVideos();
		}
	}

	public boolean isCurrentMediaListener()
	{
		return JCMediaManager.instance().listener() != null && JCMediaManager.instance().listener() == this;
	}
}
