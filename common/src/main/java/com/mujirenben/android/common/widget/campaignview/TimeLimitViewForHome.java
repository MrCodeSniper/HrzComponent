package com.mujirenben.android.common.widget.campaignview;

/**
 * Created by mac on 2018/5/11.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ch.android.common.R;
import com.mujirenben.android.common.util.ArmsUtils;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("HandlerLeak")
public class TimeLimitViewForHome extends LinearLayout {

    private TextView tv_hour_decade;
    private TextView tv_min_decade;
    private TextView tv_sec_decade;


    private Context context;

    private int hour_decade;
    private int min_decade;
    private int sec_decade;

    private Timer timer;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            countDown();
        }
    };

    public TimeLimitViewForHome(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_miaosha, this);

        tv_hour_decade = (TextView) view.findViewById(R.id.tv_hour);
       // tv_hour_unit = (TextView) view.findViewById(R.id.tv_hour_unit);
        tv_min_decade = (TextView) view.findViewById(R.id.tv_minute);
      //  tv_min_unit = (TextView) view.findViewById(R.id.tv_min_unit);
        tv_sec_decade = (TextView) view.findViewById(R.id.tv_second);
       // tv_sec_unit = (TextView) view.findViewById(R.id.tv_sec_unit);

//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SnapUpCountDownTimerView);
//        int size = array.getInteger(R.styleable.SnapUpCountDownTimerView_viewSize, 12);
//
//
//        tv_hour_decade.setTextSize(size);
//        tv_hour_unit.setTextSize(size);
//        tv_min_decade.setTextSize(size);
//        tv_min_unit.setTextSize(size);
//        tv_sec_decade.setTextSize(size);
//        tv_sec_unit.setTextSize(size);
//        ((TextView)view.findViewById(R.id.colon_minute)).setTextSize(size);
//        ((TextView)view.findViewById(R.id.colon_hour)).setTextSize(size);
    }


    public void start() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            }, 0, 1000);
        }
    }


    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    public void setTime(int hour, int min, int sec) {
        if (hour >= 1000 || min >= 60 || sec >= 60 || hour < 0 || min < 0
                || sec < 0) {
            throw new RuntimeException("时间格式错误,请检查你的代码");
        }
        hour_decade = hour ;
        min_decade = min ;
        sec_decade = sec ;

        tv_hour_decade.setText(hour_decade + "");
        tv_min_decade.setText(min_decade + "");
        tv_sec_decade.setText(sec_decade + "");
    }


    private void countDown() {
            if (isCarry4Decade(tv_sec_decade)) {
                    if (isCarry4Decade(tv_min_decade)) {
                            if (isCarry4Decade(tv_hour_decade)) {
                                ArmsUtils.makeText(context, "计数完成");
                                stop();
                                setTime(0, 0, 0);//重置为0
                            }
                        }
                    }
        }



    private boolean isCarry4Decade(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 59;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }
    }


    private boolean isCarry4Unit(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 9;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }
    }
}
