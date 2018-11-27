package com.mujirenben.android.mine.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import java.util.List;


public class OrderPlatformSelectAdapter extends BaseAdapter {

    private Context context;
    private List<String> platformList;
    private int checkItemPosition = 0;

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public OrderPlatformSelectAdapter(Context context, List<String> list) {
        this.context = context;
        this.platformList = list;
    }

    @Override
    public int getCount() {
        return platformList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.mine_order_rv_itemview, null);
            viewHolder = new ViewHolder();
            viewHolder.mItemLayout = convertView.findViewById(R.id.ll_container_sort);
            viewHolder.mIconTv = convertView.findViewById(R.id.tv_sort_name);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {

        viewHolder.mIconTv.setText(platformList.get(position));

        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder.mItemLayout.setSelected(true);
            } else {
                viewHolder.mItemLayout.setSelected(false);
            }
        }
    }

    /**
     * 根据平台名称选中对应平台
     *
     * @param lastPlatformName
     */
    public void setCheckItem(String lastPlatformName) {
        if (platformList == null) {
            return;
        }

        for (int i = 0; i < platformList.size(); i++) {
            if (platformList.get(i).equalsIgnoreCase(lastPlatformName)) {
                setCheckItem(i);
                break;
            }
        }
    }

    class ViewHolder {
        View mItemLayout;
        TextView mIconTv;
    }
}
