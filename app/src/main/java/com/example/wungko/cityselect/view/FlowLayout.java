package com.example.wungko.cityselect.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FlowLayout extends ViewGroup {

	private int						horizontalSpacing	= 12;	// 子view之间的水平间距

	private int						verticalSpacing		= 12;	// 行与行的间距

	private OnClickCityItemListener	listener;

	public interface OnClickCityItemListener {

		void clickCityItem(View view);
	}

	public void setOnClickCityItemListener(OnClickCityItemListener listener) {
		this.listener = listener;

	}

	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FlowLayout(Context context) {
		super(context);
	}

	/**
	 * 设置水平间距
	 *
	 * @param horizontalSpacing
	 */
	public void setHorizontalSpacing(int horizontalSpacing) {
		this.horizontalSpacing = horizontalSpacing;
	}

	/**
	 * 设置竖直间距
	 *
	 * @param verticalSpacing
	 */
	public void setVerticalSpacing(int verticalSpacing) {
		this.verticalSpacing = verticalSpacing;
	}

	public void setTags(List<View> tags) {
		for (View view : tags) {
			addView(view);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (listener != null) {
						listener.clickCityItem(v);
					}
				}
			});
		}
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		measureChildren(widthMeasureSpec, heightMeasureSpec);

		int width = 0;
		int height = 0;

		int row = 0; // The row counter.
		int rowWidth = 0; // Calc the current row width.
		int rowMaxHeight = 0; // Calc the max tag height, in current row.

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			final int childWidth = child.getMeasuredWidth();
			final int childHeight = child.getMeasuredHeight();

			if (child.getVisibility() != GONE) {
				rowWidth += childWidth;
				// 判断剩下的空间是否小于当前输入框

				if (rowWidth > widthSize) { // Next line.
					rowWidth = childWidth; // The next row width.
					height += rowMaxHeight + verticalSpacing;
					rowMaxHeight = childHeight; // The next row max height.
					row++;
				} else { // This line.
					rowMaxHeight = Math.max(rowMaxHeight, childHeight);
				}
				rowWidth += horizontalSpacing;
			}
		}
		// Account for the last row height.
		height += rowMaxHeight;

		// Account for the padding too.
		height += getPaddingTop() + getPaddingBottom();

		// If the tags grouped in one row, set the width to wrap the tags.
		if (row == 0) {
			width = rowWidth;
			width += getPaddingLeft() + getPaddingRight();
		} else {// If the tags grouped exceed one line, set the width to match
			// the parent.
			width = widthSize;
		}
		setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode == MeasureSpec.EXACTLY ? heightSize : height);
	}

	@Override
	public void onLayout(boolean changed, int l, int t, int r, int b) {

		final int parentLeft = getPaddingLeft();
		final int parentRight = r - l - getPaddingRight();
		final int parentTop = getPaddingTop();
		final int parentBottom = b - t - getPaddingBottom();

		int childLeft = parentLeft;
		int childTop = parentTop;

		int rowMaxHeight = 0;

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			final int width = child.getMeasuredWidth();
			final int height = child.getMeasuredHeight();

			if (child.getVisibility() != GONE) {
				if (childLeft + width > parentRight) { // Next line
					childLeft = parentLeft;
					childTop += rowMaxHeight + verticalSpacing;
					rowMaxHeight = height;
				} else {
					rowMaxHeight = Math.max(rowMaxHeight, height);
				}
				child.layout(childLeft, childTop, childLeft + width, childTop + height);

				childLeft += width + horizontalSpacing;
			}
		}
		requestLayout();
	}
}
