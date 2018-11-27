package com.mujirenben.android.mine.mvp.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseRecyclerAdapter<VH extends ViewHolder> extends RecyclerView.Adapter<ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(RecyclerView.Adapter<?> parent, View view, int position);
    }

    private OnItemClickListener itemClickListener;

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (null != itemClickListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(BaseRecyclerAdapter.this, v, position);
                }
            });
        }
        onMyBindViewHolder((VH) holder, position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onMyCreateViewHolder(parent, viewType);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public abstract void onMyBindViewHolder(VH holder, final int position);

    public abstract VH onMyCreateViewHolder(ViewGroup parent, int viewType);

    public abstract Object getItem(int position);

}