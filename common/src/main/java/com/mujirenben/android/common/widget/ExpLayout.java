package com.mujirenben.android.common.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ch.android.common.R;
import com.mujirenben.android.common.util.UIUtil;


/**
 * Created by Administrator on 2018/5/10.
 */

public class ExpLayout extends FrameLayout {
    Context mContext;
    ImageView imgBg;
    FrameLayout frame;

    int pic_with;
    int pic_height;

    public ExpLayout(Context context) {
        this(context, null);
    }

    public ExpLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {

        mContext = context;
        View imgPointLayout = inflate(context, R.layout.layout_explayout, this);
        imgBg = imgPointLayout.findViewById(R.id.imgBg);
        frame = imgPointLayout.findViewById(R.id.frame);
    }

    public void setImgBg(int pic_with, int pic_height, String imgUrl, int Up_left_x, int Up_left_y, int Down_right_x, int Down_right_y) {
        this.pic_with = pic_with;
        this.pic_height = pic_height;
        int expla_width = UIUtil.getWidth(mContext);

        int sourceScale_height = expla_width * pic_height / pic_with;//按图片比例放大的实际高度
        Log.e("实际高度是多少咯", sourceScale_height + "");
        int view_height = expla_width * 9 / 16;//控件的高度

        int face_up_left_y = expla_width / pic_with * Up_left_y;
        int face_down_right_y = expla_width / pic_with * Down_right_y;

        //可移动的y轴区域
        int can_move_y = sourceScale_height - view_height;

        //脸部中心点在
        int point_y = face_up_left_y + (face_down_right_y - face_up_left_y) / 2;


        //将图片宽度放大到控件宽度，如果放大后的图片高度大于控件高度即可
        //否则将图片高度放大到控件高度。就是用最大的缩放比
        if (sourceScale_height > view_height) {
            imgBg.setScaleType(ImageView.ScaleType.FIT_XY);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.width = expla_width;
            layoutParams.height = expla_width * pic_height / pic_with;
            layoutParams.topMargin = 0;

            if (point_y >= 0 && point_y <= view_height / 2) {
                //说明脸部中心位置在控件上半部分，那么不做处理


            } else if (point_y > view_height / 2) {
                //只要图片区域在下半部分那么走
                //即将要移动的图片高度
                int will_move_y = point_y - (view_height / 2);
                if (will_move_y <= can_move_y) {//如果要移动的区域 小于可移动的区域
                    layoutParams.topMargin = -will_move_y;
                } else {
                    layoutParams.topMargin = -can_move_y;
                }

            }

            imgBg.setLayoutParams(layoutParams);
            Glide.with(mContext)
                    .asBitmap()
                    .apply(new RequestOptions().placeholder(R.color.place_holder_error_color)
                            .error(R.color.place_holder_error_color))
                    .load(imgUrl)
                    .into(imgBg);

        } else {
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.width = view_height * pic_with / pic_height;
            layoutParams.height = view_height;
            layoutParams.topMargin = 0;
            imgBg.setLayoutParams(layoutParams);
            imgBg.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(mContext)
                    .asBitmap()
                    .apply(new RequestOptions().placeholder(R.color.place_holder_error_color)
                            .error(R.color.place_holder_error_color))
                    .load(imgUrl)
                    .into(imgBg);
        }


    }


    //图片上没高树我们要显示的区域 那么走CENTER_CROP
    public void setImgBg(int pic_with, int pic_height, String imgUrl) {
        this.pic_with = pic_with;
        this.pic_height = pic_height;
        int expla_width = UIUtil.getWidth(mContext);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width = expla_width;
        layoutParams.height = expla_width * 9 / 16;
        layoutParams.topMargin = 0;
        imgBg.setLayoutParams(layoutParams);
        imgBg.setScaleType(ImageView.ScaleType.CENTER_CROP);

        if (TextUtils.isEmpty(imgUrl)) {
            imgBg.setImageResource(R.color.bg_grey);
        } else {
            Glide.with(mContext)
                    .asBitmap()
                    .apply(new RequestOptions().placeholder(R.color.place_holder_error_color)
                            .error(R.color.place_holder_error_color))
                    .load(imgUrl)
                    .into(imgBg);
        }

    }


    public void move() {
        int expla_width = UIUtil.getWidth(mContext);
        LayoutParams layoutParams = (LayoutParams) imgBg.getLayoutParams();
        layoutParams.width = expla_width;
        layoutParams.height = expla_width * pic_height / pic_with;
        layoutParams.topMargin = layoutParams.topMargin-expla_width * 9 / 16/15;
        imgBg.setLayoutParams(layoutParams);
    }

}
