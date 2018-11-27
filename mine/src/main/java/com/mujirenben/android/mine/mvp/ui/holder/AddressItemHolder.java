package com.mujirenben.android.mine.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.mujirenben.android.common.base.holder.BaseHolder;
import com.mujirenben.android.common.dagger.component.AppComponent;
import com.mujirenben.android.common.datapackage.bean.DbTag;
import com.mujirenben.android.common.datapackage.http.imageLoader.ImageLoader;
import com.mujirenben.android.common.util.ArmsUtils;
import com.mujirenben.android.mine.R;
import com.mujirenben.android.mine.R2;

import butterknife.BindView;

/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法

 * ================================================
 */
public class AddressItemHolder extends BaseHolder<DbTag> {


    @BindView(R2.id.tv_item)
    TextView tv_item;
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架

    public AddressItemHolder(View itemView) {
        super(itemView);
        //可以在任何可以拿到 Context 的地方,拿到 AppComponent,从而得到用 Dagger 管理的单例对象
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(DbTag data, int position) {
        tv_item.setText(data.getTag_name());
        if(!data.isSelected()){
            tv_item.setSelected(false);
        }else {
            tv_item.setSelected(true);
        }
    }


    @Override
    public void onRelease() {

    }
}
