package com.hrz.poplayer.view.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * @Author
 * Created by mac on 2018/7/11.
 */
public class MyWebView extends WebView {

    private int processor=0;//0为web 1为原生

    public void setProcessor(int processor) {
        this.processor = processor;
    }

    public static final String TAG="MyWebView";

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG,"WEBVIEW收到分发事件");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(processor==0){
            return super.onTouchEvent(event);//交给网页处理
        }else {
            return false;
        }
        //return false自己没处理给原生处理
        // return true 自己没处理 原生没处理
        //super.onTouchEvent(event) return true 自己处理 原生不处理
        //super.onTouchEvent(event) return false 自己没处理给原生处理  + ontouch接口 false  自己没处理给原生处理
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG,"WEBVIEW的拦截事件");
        return super.onInterceptTouchEvent(ev);
    }

}
