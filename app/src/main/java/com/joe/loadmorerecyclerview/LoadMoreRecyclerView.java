package com.joe.loadmorerecyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Description
 * Created by chenqiao on 2015/12/31.
 */
public class LoadMoreRecyclerView extends LinearLayout implements NestedScrollingParent, NestedScrollingChild {
    private RecyclerView recyclerView;
    private FrameLayout footView;
    private Scroller mScroller;
    private NestedScrollingParentHelper helper;
    private NestedScrollingChildHelper childHelper;
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
        childHelper = new NestedScrollingChildHelper(this);
        childHelper.setNestedScrollingEnabled(true);
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
            //已经到了底部，并且还在往上拉，直接返回，不处理事件
            if (mScroller.getFinalY() + dy >= footView.getMeasuredHeight()) {
                return;
            }
            //回弹
            if (mScroller.getFinalY() + dy <= 0) {
                dy = -mScroller.getFinalY();
            }
            changeBottom = false;
            mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), 0, dy);
            invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
        } else {
            //往上拉时，距离大于了footview的高度，只让它拉到那么大
            if (mScroller.getFinalY() + dy >= footView.getMeasuredHeight()) {
                dy = footView.getMeasuredHeight() - mScroller.getFinalY();
                changeBottom = true;
                //TODO 拉到footview最大高度时候可以做的事情
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

    /*==========以下为Child的方法==========*/

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        childHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return childHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return childHelper.startNestedScroll(axes);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public void stopNestedScroll() {
        childHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return childHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return childHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return childHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    /*==========以下为Parent的方法==========*/
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        helper.onNestedScrollAccepted(child, target, axes);
    }


    @Override
    public int getNestedScrollAxes() {
        return helper.getNestedScrollAxes();
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (isBottom && dy < 0) {
            smoothScrollBy(dx, dy);
            consumed[1] = dy;
        }
    }

    private final int[] mScrollOffset = new int[2];

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dyConsumed == 0 && dyUnconsumed > 0) {
            smoothScrollBy(dxUnconsumed, dyUnconsumed);
        } else {
            //其余的未消费的事件，传给父View消费
            dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, mScrollOffset);
        }
    }

    @Override
    public void onStopNestedScroll(View child) {
        helper.onStopNestedScroll(child);
        isBottom = changeBottom;
        if (isBottom) {
            //TODO LoadMore
        } else {
            if (mScroller.getFinalY() > 0) {
                mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), mScroller.getFinalX(), -mScroller.getFinalY());
            }
        }
        helper.onStopNestedScroll(child);
        //最后一定要调用这个，告诉父view滑动结束，不然父view的滑动会卡住。
        childHelper.stopNestedScroll();
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean tf = super.dispatchTouchEvent(ev);//这个必须放在前面调用，不然下拉事件会被父view拦截
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //告诉父View（如果存在），要开始滑动，和SwipeRefreshLayout嵌套时，不调用的话，下拉事件会直接被Swipe拦截。
                startNestedScroll(SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return tf;
    }

    /*==============以下为开放部分的recyclerView方法 ================*/
    //TODO  如果还需要其他recyclerview的方法，可在下方进行开放。

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