package com.mujirenben.android.xsh.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gcssloop.widget.RCImageView;
import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.entity.ShareEntity;
import com.mujirenben.android.xsh.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by weiyq
 * <p>
 * on 2018
 */
public class ShareFragmentAdapter extends RecyclerView.Adapter<ShareFragmentAdapter.MyViewHolder> {


    private Context mContext;
    private List<ShareEntity.DataBeanX.DataBean> dataBeanList = new ArrayList<>();
    private onShareListener onShareListener;

    public ShareFragmentAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public void setOnShareListener(onShareListener onShareListener) {
        this.onShareListener = onShareListener;
    }

    public void refreshAdapter(List<ShareEntity.DataBeanX.DataBean> dataBeanList) {
        if (dataBeanList != null) {
            this.dataBeanList = dataBeanList;
        } else {
            this.dataBeanList = new ArrayList<>();
        }
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.activityshare_item, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ShareEntity.DataBeanX.DataBean dataBean = dataBeanList.get(position);
        Glide.with(mContext.getApplicationContext()).load(dataBean.getAvatarUrl()).apply(new RequestOptions().placeholder(R.drawable.hrz_logo).error(R.drawable.hrz_logo)).into(holder.icon);
        holder.name.setText(dataBean.getAuthor());
        holder.time.setText(DateUtil.getStandardTime(dataBean.getCreateTime()) + "");
        holder.content.setText(dataBean.getContent());
        List<ShareEntity.DataBeanX.DataBean.ImgsBean> imgsBeanList = dataBean.getImgs();
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        holder.imageRecycler.setLayoutManager(manager);
        ShareImageAdapter adapter = new ShareImageAdapter(mContext);
        holder.imageRecycler.setAdapter(adapter);
        adapter.setType(1, imgsBeanList);

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShareListener.onShare(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView imageRecycler;    // 多张图片
        private RCImageView icon;
        private final AppCompatTextView name;
        private final AppCompatTextView time;
        private final AppCompatTextView share;
        private final AppCompatTextView content;

        public MyViewHolder(View itemView) {
            super(itemView);

            icon = (RCImageView) itemView.findViewById(R.id.icon);
            imageRecycler = (RecyclerView) itemView.findViewById(R.id.imageRecycler);
            name = (AppCompatTextView) itemView.findViewById(R.id.name);
            time = (AppCompatTextView) itemView.findViewById(R.id.createTime);
            share = (AppCompatTextView) itemView.findViewById(R.id.share);
            content = (AppCompatTextView) itemView.findViewById(R.id.content);

        }
    }

    public interface onShareListener {
        void onShare(int position);
    }
}
