package com.example.wungko.cityselect.view;

import android.content.Context;

/**
 * 测量工具类
 *
 * Util of measure.
 *
 * @author AigeStudio 2015-03-26
 */
public final class MeasureUtil {

	public static float dp2px(Context context, float dp) {
		float scale = context.getResources().getDisplayMetrics().density;
		return dp * scale + 0.5f;
	}

	public static float px2dp(Context context, float px) {
		float scale = context.getResources().getDisplayMetrics().density;
		return px / scale + 0.5f;
	}

	/**
	 *
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static float px2sp(Context context, float pxValue) {
		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return pxValue / fontScale + 0.5f;
	}

	/**
	 *
	 * @param context
	 * @param spValue
	 * @return
	 */
	public static float sp2px(Context context, float spValue) {
		float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return spValue * fontScale + 0.5f;
	}
}
