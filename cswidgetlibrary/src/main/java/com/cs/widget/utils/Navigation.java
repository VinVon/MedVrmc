package com.cs.widget.utils;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs.widgetlibrary.R;

/**
 * Created by Administrator on 2016/3/23.
 * 设置标题栏
 */
public class Navigation {
	private FragmentActivity mActivity;

	private Navigation(FragmentActivity activity) {
		this.mActivity = activity;

	}

	public static Navigation getInstance(FragmentActivity activity) {
		return new Navigation(activity);
	}

	public Navigation setBack() {
		View view = mActivity.findViewById(R.id.base_header_back_iv);
		view.setVisibility(View.VISIBLE);
		view.setOnClickListener(v -> mActivity.finish());
		return this;
	}

	public Navigation setTitle(String title) {
		((TextView) mActivity.findViewById(R.id.base_header_title_tv)).setText(title);
		return this;
	}

	public Navigation setRight(String text, View.OnClickListener clickListener) {
		Button bt = ((Button) mActivity.findViewById(R.id.base_header_right_bt));
		bt.setText(text);
		bt.setOnClickListener(clickListener);
		return this;
	}

	public Navigation setRightDrawable(int resourceId, View.OnClickListener clickListener) {
		ImageView iv = ((ImageView) mActivity.findViewById(R.id.base_header_right_iv));
		iv.setImageResource(resourceId);
		iv.setOnClickListener(clickListener);
		return this;
	}

	public Navigation setRightDrawableListener(View.OnClickListener clickListener) {
		ImageView iv = ((ImageView) mActivity.findViewById(R.id.base_header_right_iv));
		iv.setOnClickListener(clickListener);
		return this;
	}

	public Navigation setRightDrawable(int resourceId) {
		ImageView iv = ((ImageView) mActivity.findViewById(R.id.base_header_right_iv));
		iv.setImageResource(resourceId);
		return this;
	}
}
