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
public class TimeLimitViewForGoodsDetail extends LinearLayout {

    private TextView tv_hour_decade;
    private TextView tv_min_decade;
    private TextView tv_sec_decade;
    private TextView tv_minsec_decade;


    private Context context;

    private int hour_decade;
    private int min_decade;
    private int sec_decade;
    private int minisec_decade;

    private Timer timer;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            countDown();
        }
    };

    public TimeLimitViewForGoodsDetail(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.goods_detail_timelimit_view, this);

        tv_hour_decade = (TextView) view.findViewById(R.id.tv_hour);
        tv_min_decade = (TextView) view.findViewById(R.id.tv_minute);
        tv_sec_decade = (TextView) view.findViewById(R.id.tv_seconds);
        tv_minsec_decade = (TextView) view.findViewById(R.id.tv_minisecond);
    }


    public void start() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            }, 0, 10);
        }
    }


    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    public void setTime(int hour, int min, int sec,int minisec) {
        if (hour >= 1000 || min >= 60 || sec >= 60 || hour < 0 || min < 0
                || sec < 0 || minisec<0 || minisec>100) {
            throw new RuntimeException("时间格式错误,请检查你的代码");
        }
        hour_decade = hour ;
        min_decade = min ;
        sec_decade = sec ;
        minisec_decade=minisec;

        tv_hour_decade.setText(hour_decade + "");
        tv_min_decade.setText(min_decade + "");
        tv_sec_decade.setText(sec_decade + "");
        tv_minsec_decade.setText(minisec_decade+"");
    }


    private void countDown() {
        if(isCarryMDecade(tv_minsec_decade)) {
            if (isCarry4Decade(tv_sec_decade)) {
                if (isCarry4Decade(tv_min_decade)) {
                    if (isCarry4Decade(tv_hour_decade)) {
                        ArmsUtils.makeText(context, "计数完成");
                        stop();
                        setTime(0, 0, 0, 0);//重置为0

                    }
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


    private boolean isCarryMDecade(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 99;
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
