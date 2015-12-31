package com.joe.loadmorerecyclerview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private LoadMoreRecyclerView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (LoadMoreRecyclerView) findViewById(R.id.recycler);
        view.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> list = new ArrayList<>();
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
        view.setAdapter(new MyAdapter(this, list));
    }
}
