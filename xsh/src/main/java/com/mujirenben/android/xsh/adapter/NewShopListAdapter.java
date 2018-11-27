package com.mujirenben.android.xsh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.entity.NewShopEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thinkpad on 2018/4/2.
 */

public class NewShopListAdapter extends RecyclerView.Adapter<NewShopListAdapter.MyViewHolder>{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<NewShopEntity> shopEntityList;

    public NewShopListAdapter(Context mContext, List<NewShopEntity> shopEntityList) {
        this.mContext = mContext;
        this.shopEntityList = shopEntityList;
        this.mLayoutInflater= LayoutInflater.from(mContext);

    }
    public void refreshAdapter(List<NewShopEntity> shopEntityList)
    {
        if (shopEntityList!=null)
        {
            this.shopEntityList=shopEntityList;
        }
        else
        {
            this.shopEntityList=new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.inflate(R.layout.hrz_activity_shoplist_item,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        NewShopEntity listBean = shopEntityList.get(position);
        holder.area.setText(listBean.getAddress());
        holder.tv_dian1.setText(listBean.getStoreName());
    }

    @Override
    public int getItemCount() {
        return shopEntityList.size();
    }
    public class  MyViewHolder extends RecyclerView.ViewHolder
    {

        private TextView tv_name;
        private TextView tv_dian1;
        private TextView area;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_dian1 = (TextView) itemView.findViewById(R.id.tv_dian1);
            area = (TextView) itemView.findViewById(R.id.area);

        }
    }
}
