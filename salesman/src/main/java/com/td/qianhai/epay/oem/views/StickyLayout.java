/**
The MIT License (MIT)

Copyright (c) 2014 singwhatiwanna
https://github.com/singwhatiwanna
http://blog.csdn.net/singwhatiwanna

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.td.qianhai.epay.oem.views;

import java.util.NoSuchElementException;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

public class StickyLayout extends LinearLayout {
	private static final String TAG = "StickyLayout";

	public interface OnGiveUpTouchEventListener {
		public boolean giveUpTouchEvent(MotionEvent event);
	}

	private View mHeader;
	private View mContent;
	private OnGiveUpTouchEventListener mGiveUpTouchEventListener;

	// header的高�?单位：px
	private int mOriginalHeaderHeight;
	private int mHeaderHeight;

	private int mStatus = STATUS_EXPANDED;
	public static final int STATUS_EXPANDED = 1;
	public static final int STATUS_COLLAPSED = 2;

	private int mTouchSlop;

	// 分别记录上次滑动的坐标
	private int mLastX = 0;
	private int mLastY = 0;

	// 分别记录上次滑动的坐标onInterceptTouchEvent)
	private int mLastXIntercept = 0;
	private int mLastYIntercept = 0;

	// 用来控制滑动角度，仅当角度a满足如下条件才进行滑动：tan a = deltaX / deltaY > 2
	private static final int TAN = 2;

	public StickyLayout(Context context) {
		super(context);
	}

	public StickyLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public StickyLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

//	PullDownTemplateView mPullDownTemplateView;
	private boolean isTop = false;// 表示listview上面的组件是否在顶部，即是否已经header

//	public void setPullDownTemplateView(
//			PullDownTemplateView mPullDownTemplateView) {
//		this.mPullDownTemplateView = mPullDownTemplateView;
//	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		if (hasWindowFocus && (mHeader == null || mContent == null)) {
			initData();
		}
	}

	private void initData() {
		int headerId = getResources().getIdentifier("header", "id",
				getContext().getPackageName());
		int contentId = getResources().getIdentifier("content", "id",
				getContext().getPackageName());
		if (headerId != 0 && contentId != 0) {
			mHeader = findViewById(headerId);
			mContent = findViewById(contentId);
			mOriginalHeaderHeight = mHeader.getMeasuredHeight();
			mHeaderHeight = mOriginalHeaderHeight;
			mTouchSlop = ViewConfiguration.get(getContext())
					.getScaledTouchSlop();
			Log.d(TAG, "mTouchSlop = " + mTouchSlop);
		} else {
			throw new NoSuchElementException(
					"Did your view with \"header\" or \"content\" exist?");
		}
	}

	public void setOnGiveUpTouchEventListener(OnGiveUpTouchEventListener l) {
		mGiveUpTouchEventListener = l;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		int intercepted = 0;
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			mLastXIntercept = x;
			mLastYIntercept = y;
			mLastX = x;
			mLastY = y;
			intercepted = 0;
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			int deltaX = x - mLastXIntercept;
			int deltaY = y - mLastYIntercept;
			if (isTop) {
				// 向下滑动
				if (deltaY > 0) {
					intercepted = 1;
					// 如果listview已经滑动了很多条码，则优先把listview滑动到最上面
//					if (mPullDownTemplateView.pullDownView.getListView()
//							.getFirstVisiblePosition() != 0) {
//						intercepted = 0;
//					} else {
//						intercepted = 1;
//					}
				} else {
					// 向上滑动
					intercepted = 0;
				}
			} else {
				// 向下滑动
				if (deltaY >= 0) {
					intercepted = 0;
				} else {
					// 向上滑动
					intercepted = 1;
				}
			}
			// if (mStatus == STATUS_EXPANDED && deltaY <= -mTouchSlop) {
			// intercepted = 1;
			// } else if (mGiveUpTouchEventListener != null) {
			// if (mGiveUpTouchEventListener.giveUpTouchEvent(event)
			// && deltaY >= mTouchSlop) {
			// intercepted = 1;
			// }
			// }
			break;
		}
		case MotionEvent.ACTION_UP: {
			intercepted = 0;
			mLastXIntercept = mLastYIntercept = 0;
			break;
		}
		default:
			break;
		}
		// 返回true表示焦点不向下（子）传递，返回false表示传给子布局
		Log.d(TAG, "intercepted=" + intercepted);
		return intercepted != 0;
	}

	int templateHeaderHeight;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		Log.d(TAG, "x=" + x + "  y=" + y + "  mlastY=" + mLastY);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			int deltaX = x - mLastX;
			int deltaY = y - mLastY;
			Log.d(TAG, "mHeaderHeight=" + mHeaderHeight + "  deltaY=" + deltaY
					+ "  mlastY=" + mLastY);
			mHeaderHeight += deltaY;
			templateHeaderHeight = mHeaderHeight / 2;
			setHeaderHeight(mHeaderHeight);
			break;
		}
		case MotionEvent.ACTION_UP: {
			// 这里做了下判断，当松�?��的时候，会自动向两边滑动，具体向哪边滑，要看当前mHeaderHeight的位置
			int destHeight = 0;
			if (mHeaderHeight <= mOriginalHeaderHeight * 0.5) {
				destHeight = 0;
				mStatus = STATUS_COLLAPSED;
				isTop = true;
			} else {
				destHeight = mOriginalHeaderHeight;
				mStatus = STATUS_EXPANDED;
				isTop = false;
			}
			// 慢慢滑向终点
			templateHeaderHeight = mHeaderHeight / 2;
			this.smoothSetHeaderHeight(mHeaderHeight, destHeight, 500);
			break;
		}
		default:
			break;
		}
		mLastX = x;
		mLastY = y;
		return true;
	}

	public void smoothSetHeaderHeight(final int from, final int to,
			long duration) {
		final int frameCount = (int) (duration / 1000f * 30) + 1;
		final float partation = (to - from) / (float) frameCount;
		new Thread("Thread#smoothSetHeaderHeight") {

			@Override
			public void run() {
				for (int i = 0; i < frameCount; i++) {
					final int height;
					if (i == frameCount - 1) {
						height = to;
					} else {
						height = (int) (from + partation * i);
					}
					post(new Runnable() {
						public void run() {
							setHeaderHeight(height);
						}
					});
					try {
						sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};

		}.start();
	}

	private void setHeaderHeight(int height) {
		Log.d(TAG, "setHeaderHeight height=" + height);
		if (height < 0) {
			height = 0;
		} else if (height > mOriginalHeaderHeight) {
			height = mOriginalHeaderHeight;
		}
		if (templateHeaderHeight != height || true) {
			templateHeaderHeight = height;
			mHeader.getLayoutParams().height = templateHeaderHeight;
			mHeader.requestLayout();
		}
	}

}
