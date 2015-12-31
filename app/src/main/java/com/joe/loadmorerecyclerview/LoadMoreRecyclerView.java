package com.joe.loadmorerecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Description
 * Created by chenqiao on 2015/12/31.
 */
public class LoadMoreRecyclerView extends LinearLayout implements NestedScrollingParent {
    private RecyclerView recyclerView;
    private FrameLayout footView;
    private Scroller mScroller;
    private NestedScrollingParentHelper helper;
    private boolean isBottom = false;
    private boolean changeBottom = false;

    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        helper = new NestedScrollingParentHelper(this);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.layout_morerecycler, this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_content);
        footView = (FrameLayout) findViewById(R.id.layout_footView);
    }

    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    private void smoothScrollBy(int dx, int dy) {
        //设置mScroller的滚动偏移量
        if (isBottom) {
            if (mScroller.getFinalY() + dy >= footView.getMeasuredHeight()) {
                return;
            }
            if (mScroller.getFinalY() + dy <= 0) {
                dy = -mScroller.getFinalY();
            }
            changeBottom = false;
            mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), 0, dy);
            invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
        } else {
            if (mScroller.getFinalY() + dy >= footView.getMeasuredHeight()) {
                dy = footView.getMeasuredHeight() - mScroller.getFinalY();
                changeBottom = true;
            }
            mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), 0, dy);
            invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
        }
    }

    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
            getChildAt(0).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        helper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (isBottom && dy < 0) {
            smoothScrollBy(dx, dy);
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyConsumed == 0 && dyUnconsumed > 0) {
            smoothScrollBy(dxUnconsumed, dyUnconsumed);
        }
    }

    @Override
    public void onStopNestedScroll(View child) {
        helper.onStopNestedScroll(child);
        isBottom = changeBottom;
        if (isBottom) {
            //TODO LoadMore
        } else {
            if (mScroller.getFinalY() > 0 && mScroller.getFinalY() < footView.getMeasuredHeight()) {
                mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), mScroller.getFinalX(), -mScroller.getFinalY());
            }
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onNestedPrePerformAccessibilityAction(View target, int action, Bundle args) {
        return false;
    }

    /*==============以下为开放部分的recyclerView方法 ================*/
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decoration) {
        recyclerView.addItemDecoration(decoration);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decoration, int index) {
        recyclerView.addItemDecoration(decoration, index);
    }

    public RecyclerView.Adapter getAdapter() {
        return recyclerView.getAdapter();
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return recyclerView.getLayoutManager();
    }
}