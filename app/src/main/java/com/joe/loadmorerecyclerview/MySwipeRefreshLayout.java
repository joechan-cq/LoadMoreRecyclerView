package com.joe.loadmorerecyclerview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Description  仅为了查看与LoadMoreRecyclerView的交互，没有其他作用，使用原生SwipeRefreshLayout即可
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
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        boolean tf = super.onStartNestedScroll(child, target, nestedScrollAxes);
        Log.d("MySwipeRefreshLayout", "onStartNestedScroll: " + tf);
        return tf;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        Log.d("MySwipeRefreshLayout", "onNestedScrollAccepted: ");
        super.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.d("MySwipeRefreshLayout", "onNestedPreScroll: " + dy);
        super.onNestedPreScroll(target, dx, dy, consumed);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.d("MySwipeRefreshLayout", "onNestedScroll: " + dyConsumed + " " + dyUnconsumed);
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }
}
