package com.joe.loadmorerecyclerview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Description
 * Created by chenqiao on 2016/1/3.
 */
public class MySwipeRefreshLayout extends SwipeRefreshLayout {
    public MySwipeRefreshLayout(Context context) {
        super(context);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean tf = super.onInterceptTouchEvent(ev);
        Log.d("refresh", "onInterceptTouchEvent:" + tf + "  ev:" + ev.getAction());
        return tf;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean tf = super.dispatchTouchEvent(ev);
        Log.d("refresh", "dispatchTouchevent：" + tf + "  ev:" + ev.getAction());
        return tf;
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        Log.d("refresh", "onStartNestedScroll");
        return super.onStartNestedScroll(child, target, nestedScrollAxes);
    }
}
