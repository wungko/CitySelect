package com.example.wungko.cityselect.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.wungko.cityselect.R;


public class SideBarWithText extends View {

	// 触摸事件
	private OnTouchingLetterChangedListener	onTouchingLetterChangedListener;

	// 26个字母
	// public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
	// "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
	// "X", "Y", "Z", "#" };

	public String[]							b				= {};

	private int								maxNumber		= 27;

	private int								choose			= -1;			// 选中

	private Paint paint			= new Paint();

	private Paint paintText		= new Paint();

	private TextView mTextDialog;

	private int								nomalBg, touchBg;

	private int								originalColor, selectedColor;

	private float							textSize		= 14;

	private float							textSizeText	= 16;

	private boolean							isHaveABC		= false;

	/**
	 * 为SideBar设置显示字母的TextView
	 *
	 * @param mTextDialog
	 */
	public void setTextView(TextView mTextDialog) {
		this.mTextDialog = mTextDialog;
	}

	public SideBarWithText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SideBarWithText(Context context, AttributeSet attrs) {
		super(context, attrs);
		/**
		 * 获得我们所定义的自定义样式属性
		 */
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.sideBarAttrs, 0, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
				case R.styleable.sideBarAttrs_normalBg:
					nomalBg = a.getResourceId(attr, 0);
					break;
				case R.styleable.sideBarAttrs_touchedBg:
					touchBg = a.getResourceId(attr, 0);
					break;
				case R.styleable.sideBarAttrs_originalTextColor:
					originalColor = a.getColor(attr, 0);
					break;
				case R.styleable.sideBarAttrs_selectedTextColor:
					selectedColor = a.getColor(attr, 0);
					break;
				case R.styleable.sideBarAttrs_textSize:
					textSize = a.getDimension(attr, 14);

			}

		}
		a.recycle();
	}

	public SideBarWithText(Context context) {
		super(context);
	}

	/**
	 * 设置字母数组
	 *
	 * @param abcArr
	 */
	public void setABC(String[] abcArr) {
		if (abcArr.length > 0){
			isHaveABC = true;
		}
		b = abcArr;
		invalidate();
	}

	/**
	 * 重写这个方法
	 */
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取焦点改变背景颜色.
		int height = getHeight();// 获取对应高度
		int width = getWidth(); // 获取对应宽度
		int singleHeight = height / maxNumber;// 获取每一个字母的高度
		if (textSize > singleHeight) {
			textSize = singleHeight;
		}
		/**
		 * 开始的位置为剩下的字母数的一半乘以每一个字母的高度 那么就能居中 不
		 */
		// int startH = (maxNumber - b.length) / 2 * singleHeight;
		int startH = (int) (height / 2 - b.length / 2 * textSize);
		// 绘制文字
		paintText.setColor(originalColor);
		paintText.setAntiAlias(true);
		textSizeText = MeasureUtil.dp2px(getContext(), 8);
//		if (isHaveABC){
//			paintText.setTextSize(textSizeText);
//			canvas.drawText("热门", width / 2 - paintText.measureText("热"), startH - textSizeText, paintText);
//			canvas.drawText("城市", width / 2 - paintText.measureText("城"), startH, paintText);
//		}
		for (int i = 0; i < b.length; i++) {
			paint.setColor(originalColor);
			// paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setAntiAlias(true);
			paint.setTextSize(textSize);
			// 选中的状态
			if (i == choose) {
				paint.setColor(selectedColor);
				paint.setFakeBoldText(true);
			}
			// x坐标等于中间-字符串宽度的一半.
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = startH + textSize * i + textSize;
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();// 重置画笔
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();// 点击y坐标
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		int startH = (int) (getHeight() / 2 - b.length / 2 * textSize);
		int startTextH = (int) (getHeight() / 2 - b.length / 2 * textSize - textSizeText);
		int endH = (int) (getHeight() / 2 + b.length / 2 * textSize + textSize);
		if (y >= startTextH && y <= endH) {
			// final int c = (int) (y / getHeight() * maxNumber) - startH /
			// singleHeight;// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.
			int c = -1;
			if (y < startH){
//				if (listener != null) {
//					listener.onTouchingLetterChanged("热门城市");
//					return true;
//				}
			}else{
				c = (int) ((y - startH) / textSize);
			}
			switch (action) {
				case MotionEvent.ACTION_UP:
					if (nomalBg != 0) {
						setBackgroundResource(nomalBg);
					}
					choose = -2;//
					invalidate();
					if (mTextDialog != null) {
						mTextDialog.setVisibility(View.GONE);
					}
					break;

				default:
					if (touchBg != 0) {
						setBackgroundResource(touchBg);
					}
					if (oldChoose != c) {
						if ((c >= 0 && c < b.length) || c == -1) {
							if (listener != null) {
								if (c == -1){
									listener.onTouchingLetterChanged("热门城市");
								}else{
									listener.onTouchingLetterChanged(b[c]);
								}

							}
							if (mTextDialog != null) {
								if (c == -1){
									mTextDialog.setText("热门\n城市");
								}else{
									mTextDialog.setText(b[c]);
								}
								mTextDialog.setVisibility(View.VISIBLE);
							}
							choose = c;
							invalidate();
						}

					}

					break;
			}
		} else {
			if (nomalBg != 0) {
				setBackgroundResource(nomalBg);
			}
			choose = -1;//
			invalidate();
			if (mTextDialog != null) {
				mTextDialog.setVisibility(View.GONE);
			}
		}
		return true;
	}

	/**
	 * 向外公开的方法
	 *
	 * @param onTouchingLetterChangedListener
	 */
	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	/**
	 * 接口
	 *
	 * @author coder
	 */
	public interface OnTouchingLetterChangedListener {

		public void onTouchingLetterChanged(String s);
	}

}