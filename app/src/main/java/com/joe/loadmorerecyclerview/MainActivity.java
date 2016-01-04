package com.joe.loadmorerecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private LoadMoreRecyclerView view;
    private ArrayList<String> list;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (LoadMoreRecyclerView) findViewById(R.id.recycler);
        view.setLayoutManager(new GridLayoutManager(this, 3));
        list = new ArrayList<>();
        list.add("456");
        list.add("123");
        list.add("456");
        list.add("123");
        list.add("456");
        list.add("123");
        list.add("456");
        list.add("123");
        list.add("456");
        list.add("123");
        list.add("456");
        list.add("123");
        list.add("456");
        list.add("123");
        list.add("456");
        list.add("123");
        list.add("456");
        list.add("123");
        list.add("456");
        list.add("123");
        list.add("456");
        list.add("123");
        list.add("456");
        list.add("456");
        list.add("123");
        list.add("456");
        list.add("123");
        list.add("456");
        list.add("123");
        list.add("456");
        list.add("123");
        list.add("456");
        list.add("123");
        list.add("456");
        list.add("123");
        list.add("456");
        adapter = new MyAdapter(this, list);
        view.setAdapter(adapter);
        final Handler handler = new Handler();
        view.setOnLoadingListener(new LoadMoreRecyclerView.onLoadingMoreListener() {
            @Override
            public void onLoading() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add("new 1");
                        list.add("new 2");
                        list.add("new 3");
                        list.add("new 4");
                        adapter.notifyItemRangeChanged(0, list.size());
                        view.loadFinished();
                    }
                }, 2000);
            }
        });
    }
}
