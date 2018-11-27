package com.mujirenben.android.xsh.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mujirenben.android.xsh.R;
import com.mujirenben.android.xsh.callback.DialogCallBack;
import com.mujirenben.android.common.base.BaseApplication;

public class ODialog {


	/***
	 * 关闭对话框
	 *
	 * @param dialog
	 */
	public static void dismissDelDialog(Dialog dialog) {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
			dialog = null;
		}
	}
	/****
	 * 此项目通用自定义对话框(4.0方式对话框)
	 *
	 * @param context
	 * @param width
	 * @param canceledOnTouchOutside
	 * @param cancelable
	 * @param title
	 * @param content
	 * @param sureStr
	 * @param cancelStr
	 * @param sureTextColor
	 * @param cacelTextColor
	 * @param callback
	 * @param cancelCallBack
	 * @return
	 */
	public static Dialog showLCDialog(Context context, int width,
									  boolean canceledOnTouchOutside, boolean cancelable, String title,
									  String content, String sureStr, String cancelStr,
									  int sureTextColor, int cacelTextColor,
									  final DialogCallBack callback, final DialogCallBack cancelCallBack) {
		if (width <= 0)
			width = BaseApplication.mScreenW;
		if (TextUtils.isEmpty(sureStr))
			sureStr = context.getString(R.string.dialog_ensure);
		if (TextUtils.isEmpty(cancelStr))
			cancelStr = context.getString(R.string.dialog_cancel);

		View mViewDel = LayoutInflater.from(context).inflate(
				R.layout.public_dialog_comfirm_layout1, null);
		final Dialog customDialog = new Dialog(context, R.style.dialog);
		customDialog.setContentView(mViewDel);

		Window dialogWindow = customDialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.CENTER);
		// lp.width = width/5 * 4; // 左右都有间隔的宽度
		lp.width = width;// 全屏宽度
		dialogWindow.setAttributes(lp);
		//dialogWindow.setWindowAnimations(R.style.dialogStyleAnimation);
		customDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
		customDialog.setCancelable(cancelable);

		TextView tvTitle = (TextView) mViewDel.findViewById(R.id.dialog_title);
		TextView tvMsg = (TextView) mViewDel.findViewById(R.id.dialog_message);
		tvTitle.setText(title);
		tvMsg.setText(content);
		TextView mBtnDelSure = (TextView) mViewDel.findViewById(R.id.yes);

		TextView mBtnDelCancel = (TextView) mViewDel.findViewById(R.id.no);
		TextView mSubmit = (TextView) mViewDel.findViewById(R.id.submit);

		LinearLayout two_btn_layout = (LinearLayout) mViewDel
				.findViewById(R.id.two_btn_layout);
		LinearLayout one_btn_layout = (LinearLayout) mViewDel
				.findViewById(R.id.one_btn_layout);

		if (cancelable) {
			two_btn_layout.setVisibility(View.VISIBLE);
			one_btn_layout.setVisibility(View.GONE);
			mBtnDelCancel.setVisibility(View.VISIBLE);
			mBtnDelCancel.setText(cancelStr);
			if (cacelTextColor > -1) {
				mBtnDelCancel.setTextColor(cacelTextColor);
			}
			mBtnDelSure.setText(sureStr);
			if (sureTextColor > -1) {
				mBtnDelSure.setTextColor(sureTextColor);
			}
			mBtnDelCancel.setOnClickListener(v -> {
                if (cancelCallBack != null) {
                    cancelCallBack.onCallBack();
                } else {
                    dismissDelDialog(customDialog);
                }
            });
			mBtnDelSure.setOnClickListener(v -> {
                if (callback != null) {
                    callback.onCallBack();
                }
                dismissDelDialog(customDialog);
            });
		} else {
			two_btn_layout.setVisibility(View.GONE);
			one_btn_layout.setVisibility(View.VISIBLE);
			mSubmit.setText(sureStr);
			if (sureTextColor > -1) {
				mSubmit.setTextColor(sureTextColor);
			}
			mSubmit.setOnClickListener(v -> {
                if (callback != null) {
                    callback.onCallBack();
                }
                dismissDelDialog(customDialog);
            });
		}
		if(context!=null&&getActivity(context)!=null&&!getActivity(context).isFinishing()){
			customDialog.show();
		}


		return customDialog;
	}



	private static Activity getActivity(Context mContext) {
		try {
			while (!(mContext instanceof Activity) && mContext instanceof ContextWrapper) {
				mContext = ((ContextWrapper) mContext).getBaseContext();
			}
			if (mContext instanceof Activity) {
				return (Activity) mContext;
			}
		} catch (Exception e) {


		}
		return  null;

	}

}
