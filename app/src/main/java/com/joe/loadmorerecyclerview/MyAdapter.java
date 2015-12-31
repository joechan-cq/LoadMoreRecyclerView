package com.joe.loadmorerecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Description
 * Created by chenqiao on 2015/12/31.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<String> datass;

    public MyAdapter(Context context, ArrayList<String> list) {
        mContext = context;
        datass = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (holder == null) {
            System.out.print("holder null");
        }
        if (holder.item == null) {
            System.out.print("holder item null");
        }
        if (datass == null) {
            System.out.print("datass null");
        }
        holder.item.setText(datass.get(position));
    }

    @Override
    public int getItemCount() {
        return datass.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item;

        public MyViewHolder(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }
}