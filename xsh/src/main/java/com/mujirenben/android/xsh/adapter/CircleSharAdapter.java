package com.mujirenben.android.xsh.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.widget.CircleImageView;

import java.util.List;

/**
 * Create by weiyq
 * <p>
 * on 2018/7/13
 */
public class CircleSharAdapter extends BaseQuickAdapter
{
	public CircleSharAdapter(int layoutResId, @Nullable List data) {
		super(layoutResId, data);
	}

	@Override
	protected void convert(BaseViewHolder helper, Object item) {

	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

	}

	//	private Context mContext;
//	private CustomShareImageAdapter	adapter;
//	private List<ShareEnitity> mList	= new ArrayList<>();
//	private int						width	= 0;
//	private onShareListener			mShareListener;
//
//	public List<ShareEnitity> getmList()
//	{
//		return mList;
//	}
//
//	public void setmList(List<ShareEnitity> mList)
//	{
//		this.mList = mList;
//		notifyDataSetChanged();
//	}
//
//	public void setShareListener(onShareListener mShareListener)
//	{
//		this.mShareListener = mShareListener;
//
//	}
//
//	public CircleSharAdapter(Context mContext)
//	{
//		this.mContext = mContext;
//		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//		width = wm.getDefaultDisplay().getWidth();
//	}
//
//	@Override
//	public void onMyBindViewHolder(CircleSharAdapter.MyViewHolder holder, int position)
//	{
//		ShareEnitity enitity = mList.get(position);
//
//		if (enitity.getImgList() != null)
//		{
//			// 视频
//			if (null != enitity.getImgList().getVideo() && !"".equals(enitity.getImgList().getVideo())
//					&& enitity.getImgList().getVideo().size() > 0)
//			{
//
//				if (enitity.getImgList().getVideo().get(0).getVideoCss().equals("1"))
//				{
//
//					holder.video
//							.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(mContext,165)));
//					RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) holder.video.getLayoutParams();
//					rl.setMargins(DeviceUtils.dip2px(mContext, 16), 0, DeviceUtils.dip2px(mContext, 16), 15);
//					holder.video.setLayoutParams(rl);
//					int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//					int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//					holder.tv_bg.measure(w, h);
//					holder.tv_bg.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
//							RelativeLayout.LayoutParams.WRAP_CONTENT));
//					RelativeLayout.LayoutParams rll = (RelativeLayout.LayoutParams) holder.tv_bg.getLayoutParams();
//					rll.setMargins(DeviceUtils.dip2px(mContext, 16),
//							(DeviceUtils.dip2px(mContext,165)- holder.tv_bg.getMeasuredHeight()), DeviceUtils.dip2px(mContext, 16),
//							0);
//					holder.tv_bg.setLayoutParams(rll);
//
//				}
//				else
//				{
//					int widthImage = width - DeviceUtils.dip2px(mContext, 16 * 2);
//					holder.video
//							.setLayoutParams(new RelativeLayout.LayoutParams(widthImage * 3 / 5, widthImage * 21 / 25));
//					RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) holder.video.getLayoutParams();
//					rl.setMargins(DeviceUtils.dip2px(mContext, 16), 0, DeviceUtils.dip2px(mContext, 3), 15);
//					holder.video.setLayoutParams(rl);
//
//					int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//					int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//					holder.tv_bg.measure(w, h);
//					holder.tv_bg.setLayoutParams(new RelativeLayout.LayoutParams(widthImage * 3 / 5,
//							RelativeLayout.LayoutParams.WRAP_CONTENT));
//					RelativeLayout.LayoutParams rll = (RelativeLayout.LayoutParams) holder.tv_bg.getLayoutParams();
//					rll.setMargins(DeviceUtils.dip2px(mContext, 16),
//							(widthImage * 21 / 25 - holder.tv_bg.getMeasuredHeight()), DeviceUtils.dip2px(mContext, 3),
//							0);
//					holder.tv_bg.setLayoutParams(rll);
//				}
//
//				holder.videoLayout.setVisibility(View.VISIBLE);
//				holder.shareImage.setVisibility(View.GONE);
//				holder.imageRecycler.setVisibility(View.GONE);
//				Glide.with(mContext).load(enitity.getImgList().getVideo().get(0).getImgUrl()).into(holder.video);
//				holder.tv_bg.setText(enitity.getImgList().getVideo().get(0).getName());
//				holder.video.setOnClickListener(new View.OnClickListener()
//				{
//					@Override
//					public void onClick(View v)
//					{
//						Intent intent = new Intent(mContext, NewVideoActivity.class);
//						intent.putExtra(Contant.IntentConstant.INTENT_ID,
//								enitity.getImgList().getVideo().get(0).getUrl());
//						intent.putExtra(Contant.IntentConstant.TITLE, enitity.getImgList().getVideo().get(0).getName());
//						intent.putExtra(Contant.IntentConstant.LINK_URL,
//								enitity.getImgList().getVideo().get(0).getUrl());
//						intent.putExtra(Contant.IntentConstant.SEARCH_TXT,
//								enitity.getImgList().getVideo().get(0).getName());
//						intent.putExtra(Contant.IntentConstant.ISWXIMG,
//								enitity.getImgList().getVideo().get(0).getImgUrl());
//						mContext.startActivity(intent);
//					}
//				});
//
//			}
//
//			// 多张图片
//			else if (null != enitity.getImgList().getImg() && !"".equals(enitity.getImgList().getImg())
//					&& enitity.getImgList().getImg().size() > 0)
//			{
//				// 单张图片
//				if (enitity.getImgList().getImg().size() == 1)
//				{
//					int widthImage = width - DeviceUtils.dip2px(mContext, 16 * 2);
//					holder.shareImage
//							.setLayoutParams(new LinearLayout.LayoutParams(widthImage * 3 / 5, widthImage * 21 / 25));
//					LinearLayout.LayoutParams rl = (LinearLayout.LayoutParams) holder.shareImage.getLayoutParams();
//					rl.setMargins(DeviceUtils.dip2px(mContext, 16), 0, DeviceUtils.dip2px(mContext, 3), 15);
//					holder.shareImage.setLayoutParams(rl);
//
//					holder.videoLayout.setVisibility(View.GONE);
//					holder.shareImage.setVisibility(View.VISIBLE);
//					holder.imageRecycler.setVisibility(View.GONE);
//					Glide.with(mContext).load(enitity.getImgList().getImg().get(0)).into(holder.shareImage);
//
//					holder.shareImage.setOnClickListener((v) -> {
//						PhotoViewActivity.startSelf(mContext, (String[]) enitity.getImgList().getImg()
//								.toArray(new String[enitity.getImgList().getImg().size()]), 0);
//
//					});
//				}
//				else if (enitity.getImgList().getImg().size() == 2 || enitity.getImgList().getImg().size() == 4)
//				{
//					holder.videoLayout.setVisibility(View.GONE);
//					holder.shareImage.setVisibility(View.GONE);
//					holder.imageRecycler.setVisibility(View.VISIBLE);
//					adapter = new CustomShareImageAdapter(mContext);
//					adapter.setType(0);
//					GridLayoutManager manager = new GridLayoutManager(mContext, 2);
//					holder.imageRecycler.setLayoutManager(manager);
//					holder.imageRecycler.setAdapter(adapter);
//					adapter.setImgsList(enitity.getImgList().getImg());
//				}
//				else
//				{
//					holder.videoLayout.setVisibility(View.GONE);
//					holder.shareImage.setVisibility(View.GONE);
//					holder.imageRecycler.setVisibility(View.VISIBLE);
//					adapter = new CustomShareImageAdapter(mContext);
//					adapter.setType(1);
//					GridLayoutManager manager = new GridLayoutManager(mContext, 3);
//					holder.imageRecycler.setLayoutManager(manager);
//					holder.imageRecycler.setAdapter(adapter);
//					adapter.setImgsList(enitity.getImgList().getImg());
//				}
//
//			}
//
//		}
//		else
//		{
//
//		}
//		holder.name.setText(enitity.getName());
//		holder.time.setText(FormatCurrentData.getTimeRange(enitity.getDateTime()));
//		holder.content.setText(enitity.getContent());
//		Glide.with(mContext).load(enitity.getImgList().getLogo()).into(holder.icon);
//
//		holder.share.setOnClickListener((b) -> {
//			mShareListener.onShare(position);
//		});
//
//		// adapter = new CustomShareImageAdapter(mContext);
//		// adapter.setType(1);
//		// GridLayoutManager manager = new GridLayoutManager(mContext, 3);
//		// holder.imageRecycler.setLayoutManager(manager);
//		// holder.imageRecycler.setAdapter(adapter);
//	}
//
//	@Override
//	public CircleSharAdapter.MyViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType)
//	{
//		return new CircleSharAdapter.MyViewHolder(
//				LayoutInflater.from(mContext).inflate(R.layout.circleshare_item, null));
//	}
//
//	@Override
//	public Object getItem(int position)
//	{
//		return mList.get(position);
//	}
//
//	@Override
//	public int getItemCount()
//	{
//		return mList.size();
//	}
//
	class MyViewHolder extends RecyclerView.ViewHolder
	{
		private CircleImageView icon;
		private AppCompatTextView time;
		private AppCompatTextView name;
		private AppCompatTextView share;
		private AppCompatTextView content;
		private AppCompatImageView shareImage;		// 一张图片

		private RelativeLayout videoLayout;
		private AppCompatImageView video;			// 视频
		private AppCompatTextView tv_bg;

		private RecyclerView imageRecycler;	// 多张图片

		public MyViewHolder(View itemView)
		{
			super(itemView);
			icon = (CircleImageView) itemView.findViewById(R.id.icon);
			time = (AppCompatTextView) itemView.findViewById(R.id.time);
			name = (AppCompatTextView) itemView.findViewById(R.id.name);
			share = (AppCompatTextView) itemView.findViewById(R.id.share);
			content = (AppCompatTextView) itemView.findViewById(R.id.content);
//			shareImage = (AppCompatImageView) itemView.findViewById(R.id.shareImage);
//			videoLayout = (RelativeLayout) itemView.findViewById(R.id.videoLayout);
//			video = (AppCompatImageView) itemView.findViewById(R.id.video);
//			tv_bg = (AppCompatTextView) itemView.findViewById(R.id.tv_bg);
			imageRecycler = (RecyclerView) itemView.findViewById(R.id.imageRecycler);

		}
	}

	public interface onShareListener
	{
		void onShare(int position);
	}
}
