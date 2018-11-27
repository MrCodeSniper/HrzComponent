package com.mujirenben.android.common.widget.dialog.dialogpopmanager.callback;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.mujirenben.android.common.util.DoubleChooseListener;
import com.mujirenben.android.common.widget.dialog.dialogpopmanager.bean.BuildBean;
import com.mujirenben.android.common.widget.dialog.dialogpopmanager.bean.TieBean;
import com.mujirenben.android.common.widget.dialog.dialogpopmanager.config.DialogConfig;

import java.util.List;

/**
 * Created by Administrator on 2018/8/9 8
 */
public class DialogAssigner implements Assignable {

    private static DialogAssigner instance;

    private DialogAssigner() {

    }

    public static DialogAssigner getInstance() {
        if (instance == null) {
            instance = new DialogAssigner();
        }
        return instance;
    }


    @Override
    public BuildBean assignDatePick(Context context, int gravity, String dateTitle, long date, int dateType, int tag, DialogUIDateTimeSaveListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.dateTitle = dateTitle;
        bean.gravity = gravity;
        bean.cancelable = true;
        bean.outsideTouchable = true;
        bean.type = DialogConfig.TYPE_DATEPICK;
        bean.dateTimeListener = listener;
        bean.dateType = dateType;
        bean.date = date;
        bean.tag = tag;
        return bean;
    }

    @Override
    public BuildBean assignLoading(Context context, CharSequence msg,boolean isVertical, boolean cancleable, boolean outsideTouchable, boolean isWhiteBg) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.msg = msg;
        bean.isVertical = isVertical;
        bean.isWhiteBg = isWhiteBg;
        bean.msg = "加载";
        bean.gravity = Gravity.CENTER;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.type = DialogConfig.TYPE_LOADING;
        return bean;
    }

    @Override
    public BuildBean assignTokenDialog(Context context, DoubleChooseListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.msg = "Token过期";
        bean.gravity = Gravity.CENTER;
        bean.cancelable = false;
        bean.outsideTouchable = false;
        bean.type = DialogConfig.TYPE_TOKEN;
        bean.setDoubleChooseListener(new DoubleChooseListener() {
            @Override
            public void onConfirm() {
                bean.dissmiss();
                listener.onConfirm();
            }

            @Override
            public void onCancel() {
                bean.dissmiss();
                listener.onCancel();
            }
        });
        return bean;
    }

    @Override
    public BuildBean assignTaokoulingDialog(Context context, View contentView) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.msg = "淘口令";
        bean.customView=contentView;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = false;
        bean.outsideTouchable = false;
        bean.type = DialogConfig.TYPE_KOULING;
        return bean;
    }

    @Override
    public BuildBean assignExpirationDialog(Context context, View contentView, int days) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.msg = "過期彈窗";
        bean.days=days;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = false;
        bean.outsideTouchable = false;
        bean.type = DialogConfig.TYPE_EXPIRATION;
        return bean;
    }

    @Override
    public BuildBean assignKouLingErrorDialog(Context context, View contentView) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.msg = "口令无法识别";
        bean.gravity = Gravity.CENTER;
        bean.cancelable = false;
        bean.outsideTouchable = false;
        bean.type = DialogConfig.TYPE_KOULING_ERROR;
        return bean;
    }

    @Override
    public BuildBean assignMdLoading(Context context, CharSequence msg,boolean isVertical, boolean cancleable, boolean outsideTouchable, boolean isWhiteBg) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.msg = msg;
        bean.isVertical = isVertical;
        bean.isWhiteBg = isWhiteBg;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.type = DialogConfig.TYPE_MD_LOADING;
        return bean;
    }

    @Override
    public BuildBean assignMdAlert(Activity activity, CharSequence title, CharSequence msg, boolean cancleable, boolean outsideTouchable, DialogUIListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = activity;
        bean.msg = msg;
        bean.title = title;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.listener = listener;
        bean.type = DialogConfig.TYPE_MD_ALERT;
        bean.btn1Color = DialogConfig.mdBtnColor;
        bean.btn2Color = DialogConfig.mdBtnColor;
        bean.btn3Color = DialogConfig.mdBtnColor;
        return bean;
    }

    @Override
    public BuildBean assignSingleChoose(Activity context, CharSequence title, int defaultChosen, CharSequence[] words, boolean cancleable, boolean outsideTouchable, DialogUIItemListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.title = title;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.itemListener = listener;
        bean.wordsMd = words;
        bean.type = DialogConfig.TYPE_SINGLE_CHOOSE;
        bean.defaultChosen = defaultChosen;
        bean.btn1Color = DialogConfig.mdBtnColor;
        bean.btn2Color = DialogConfig.mdBtnColor;
        bean.btn3Color = DialogConfig.mdBtnColor;
        return bean;
    }

    @Override
    public BuildBean assignMdMultiChoose(Activity context, CharSequence title, CharSequence[] words, boolean[] checkedItems, boolean cancleable, boolean outsideTouchable, DialogUIListener btnListener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.msg = title;
        bean.title = title;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.listener = btnListener;
        bean.wordsMd = words;
        bean.checkedItems = checkedItems;
        bean.type = DialogConfig.TYPE_MD_MULTI_CHOOSE;
        bean.btn1Color = DialogConfig.mdBtnColor;
        bean.btn2Color = DialogConfig.mdBtnColor;
        bean.btn3Color = DialogConfig.mdBtnColor;
        return bean;
    }

    @Override
    public BuildBean assignAlert(Context activity, CharSequence title, CharSequence msg, CharSequence hint1, CharSequence hint2,
                                 CharSequence firstTxt, CharSequence secondTxt, boolean isVertical, boolean cancleable, boolean outsideTouchable, DialogUIListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = activity;
        bean.msg = msg;
        bean.title = title;
        bean.hint1 = hint1;
        bean.hint2 = hint2;
        bean.text1 = firstTxt;
        bean.text2 = secondTxt;
        bean.isVertical = isVertical;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.listener = listener;
        bean.type = DialogConfig.TYPE_ALERT;
        return bean;
    }


    @Override
    public BuildBean assignCustomAlert(Context context, View contentView, int gravity, boolean cancleable, boolean outsideTouchable, int showTime,String dialogName,OutSideDismissListener dialogDisMissListener,long showDate,int dialogId) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.customView = contentView;
        bean.msg = dialogName;
        bean.dateType=showTime;
        bean.gravity = gravity;
        bean.cancelable = cancleable;
        bean.date=showDate;
        bean.tag=dialogId;
        bean.setOutSideDismissListener(dialogDisMissListener);
        bean.outsideTouchable = outsideTouchable;
        bean.type = DialogConfig.TYPE_CUSTOM_ALERT;
        return bean;
    }

    @Override
    public BuildBean assignCustomBottomAlert(Context context, View contentView, boolean cancleable, boolean outsideTouchable) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.customView = contentView;
        bean.msg = "自定义插入";
        bean.gravity = Gravity.BOTTOM;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.type = DialogConfig.TYPE_CUSTOM_BOTTOM_ALERT;
        return bean;
    }

    @Override
    public BuildBean assignDownloadDialog(Context context,  boolean cancleable, boolean outsideTouchable) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.gravity = Gravity.CENTER;
        bean.msg = "进度条";
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.type = DialogConfig.TYPE_DOWNLOAD;
        return bean;
    }



    @Override
    public BuildBean assignSheet(Context context, List<TieBean> datas, CharSequence bottomTxt, int gravity, boolean cancleable, boolean outsideTouchable, DialogUIItemListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.itemListener = listener;
        bean.mLists = datas;
        bean.bottomTxt = bottomTxt;
        bean.gravity = gravity;
        bean.isVertical = true;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.type = DialogConfig.TYPE_SHEET;

        return bean;
    }

    @Override
    public BuildBean assignMdBottomSheet(Context context,boolean isVertical, CharSequence title, List<TieBean> datas, int columnsNum, boolean cancleable, boolean outsideTouchable, DialogUIItemListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.title = title;
        bean.isVertical = isVertical;
        bean.mLists = datas;
        bean.bottomTxt = "";
        bean.gravity = Gravity.BOTTOM;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.itemListener = listener;
        bean.gridColumns = columnsNum;
        bean.type = DialogConfig.TYPE_BOTTOM_SHEET;
        return bean;
    }


}
