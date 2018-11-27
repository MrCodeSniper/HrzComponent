package com.mujirenben.android.xsh.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.entity.ShareEntity;
import com.mujirenben.android.xsh.utils.DeviceUtils;
import com.mujirenben.android.common.arounter.ARouterPaths;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * on 2018/7/13
 */
public class ShareImageAdapter extends RecyclerView.Adapter<ShareImageAdapter.MyViewHolder> {
    private Context mContext;
    private int type = 0;
    private MyViewHolder myViewHolder;


    private List<ShareEntity.DataBeanX.DataBean.ImgsBean> imgsBeanList;
    private List<String> imgList = new ArrayList<>();

    public int getType() {
        return type;
    }

    public void setType(int type, List<ShareEntity.DataBeanX.DataBean.ImgsBean> imgsBeanList) {
        this.type = type;
        this.imgsBeanList = imgsBeanList;
        for (int i = 0; i < imgsBeanList.size(); i++) {
            this.imgList.add(imgsBeanList.get(i).getImg());
        }
        notifyDataSetChanged();
    }


    private int width;

    public ShareImageAdapter(Context mContext) {
        this.mContext = mContext;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        myViewHolder= new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.customshareimage_item, null));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

//		if (type == 0) // 每行展示两个
//		{
//			holder.mImage.setLayoutParams(
//					new LinearLayout.LayoutParams((width - DeviceUtils.dip2px(mContext, 26 + 3 * 4)) / 2,
//							(width - DeviceUtils.dip2px(mContext, 26 + 3 * 4)) / 2));
//			LinearLayout.LayoutParams rl = (LinearLayout.LayoutParams) holder.mImage.getLayoutParams();
//			rl.setMargins(DeviceUtils.dip2px(mContext, 3), 5, DeviceUtils.dip2px(mContext, 3), 5);
//			holder.mImage.setLayoutParams(rl);
//		}
//		else if (type == 1)
//		{ // 每行展示三个




        ShareEntity.DataBeanX.DataBean.ImgsBean imgsBean = imgsBeanList.get(position);
        holder.mImage.setLayoutParams(
                new LinearLayout.LayoutParams((width - DeviceUtils.dip2px(mContext, 26 + 3 * 6)) / 3,
                        (width - DeviceUtils.dip2px(mContext, 26 + 3 * 6)) / 3));
        LinearLayout.LayoutParams rl = (LinearLayout.LayoutParams) holder.mImage.getLayoutParams();
        rl.setMargins(DeviceUtils.dip2px(mContext, 3), 5, DeviceUtils.dip2px(mContext, 3), 5);
        holder.mImage.setLayoutParams(rl);
        Glide.with(mContext.getApplicationContext()).load(imgsBean.getImg()).apply(new RequestOptions().placeholder(R.color.place_holder_error_color).error(R.color.place_holder_error_color)).into(holder.mImage);
        holder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("image_urls", (ArrayList<String>) imgList);
                bundle.putInt("current_image_url_index",position);
                ARouter.getInstance()
                        .build(ARouterPaths.IMAGE_BROWSE_ACTIVITY)
                        .withBundle("fromOther",bundle)
                        .navigation(mContext);

            }
        });
    }

    @Override
    public int getItemCount() {
        return imgsBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.itemImage);
        }
    }

}
