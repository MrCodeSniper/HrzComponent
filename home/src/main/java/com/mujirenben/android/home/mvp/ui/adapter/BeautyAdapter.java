package com.mujirenben.android.home.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mujirenben.android.common.util.rxtools.RxTextTool;
import com.mujirenben.android.home.R;
import com.mujirenben.android.home.mvp.model.entity.HomeIndex;

import java.util.List;

/**
 * Created by mac on 2018/5/10.
 */

public class BeautyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private HomeMultipleRecycleAdapter adapter;

    public static enum ITEM_TYPE {
        ITEM_TYPE_GOODS,
        ITEM_TYPE_MORE,
        ITEM_TYPE_ACTIVITY
    }

    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 数据集合
     */
    private List<HomeIndex.ItemInfoListBean.ItemContentListBean> data;

    public BeautyAdapter(List<HomeIndex.ItemInfoListBean.ItemContentListBean> data, Context context,HomeMultipleRecycleAdapter adapter) {
        this.data = data;
        this.mContext = context;
        this.adapter=adapter;
    }

    public List<HomeIndex.ItemInfoListBean.ItemContentListBean> getData() {
        return data;
    }

    public void addNewData(List<HomeIndex.ItemInfoListBean.ItemContentListBean> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == ITEM_TYPE.ITEM_TYPE_GOODS.ordinal()){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false);
            return new BeautyViewHolder(view);
        }else if(viewType == ITEM_TYPE.ITEM_TYPE_ACTIVITY.ordinal()){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,parent,false);
            return new ActivityViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //将数据设置到item上
        HomeIndex.ItemInfoListBean.ItemContentListBean beauty = data.get(position);
        if(holder instanceof BeautyViewHolder){

            Glide.with(mContext).load(beauty.imageUrl).into(((BeautyViewHolder)holder).beautyImage);
            ((BeautyViewHolder)holder).ll_cutoff.setVisibility(View.VISIBLE);

//            if(!adapter.isScrolling()){
//                Glide.with(mContext).load(beauty.imageUrl).into(((BeautyViewHolder)holder).beautyImage);
//                ((BeautyViewHolder)holder).ll_cutoff.setVisibility(View.VISIBLE);
//            }else {
//                ((BeautyViewHolder)holder).ll_cutoff.setVisibility(View.GONE);
//            }




            ((BeautyViewHolder)holder).show_toast.setText(beauty.itemSubTitle);

            if(((BeautyViewHolder)holder).show_toast.getText().length()==2){
                ((BeautyViewHolder)holder).nameTv.setText("          "+beauty.itemTitle);
            }else if(((BeautyViewHolder)holder).show_toast.getText().length()==3){
                ((BeautyViewHolder)holder).nameTv.setText("             "+beauty.itemTitle);
            }

            RxTextTool.getBuilder("",mContext)
                    .append("券后")
                    .append("¥").setForegroundColor(mContext.getResources().getColor(R.color.main_color))
                    .append("88").setForegroundColor(mContext.getResources().getColor(R.color.main_color)).setProportion(1.8f)
                    .append(".04").setForegroundColor(mContext.getResources().getColor(R.color.main_color))
                    .into(((BeautyViewHolder) holder).tv_rxtool);

            RxTextTool.getBuilder("原价: ¥120",mContext).setStrikethrough().into(((BeautyViewHolder) holder).tv_preprice);

        }else if(holder instanceof ActivityViewHolder){
//            if(!adapter.isScrolling()){
                Glide.with(mContext).load(beauty.imageUrl).into(((ActivityViewHolder) holder).backImage);
//            }

        }
    }


    @Override
    public int getItemViewType(int position) {
        if(TextUtils.equals(data.get(position).itemSubTitle,"活动项目")){
            return ITEM_TYPE.ITEM_TYPE_ACTIVITY.ordinal();
        }
      return ITEM_TYPE.ITEM_TYPE_GOODS.ordinal();

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

     static class BeautyViewHolder extends RecyclerView.ViewHolder {
        ImageView beautyImage;
        TextView nameTv;
        TextView tv_preprice;
        TextView tv_rxtool;
        TextView show_toast;
        LinearLayout ll_cutoff;

        public BeautyViewHolder(View itemView) {
            super(itemView);
            ll_cutoff=itemView.findViewById(R.id.ll_cutoff);
            beautyImage = itemView.findViewById(R.id.image_item);
            nameTv = itemView.findViewById(R.id.name_item);
            show_toast=itemView.findViewById(R.id.home_tv_goods_tag);
            tv_preprice = itemView.findViewById(R.id.tv_preprice);
            tv_rxtool = itemView.findViewById(R.id.tv_rxtool);
        }
    }


     static class ActivityViewHolder extends RecyclerView.ViewHolder {
        ImageView backImage;

        public ActivityViewHolder(View itemView) {
            super(itemView);
            backImage=itemView.findViewById(R.id.iv_activity);
        }
    }
}

