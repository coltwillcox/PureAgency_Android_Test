package com.koltinjo.pureagency_android_test;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by colt on 23.11.2016.
 */

public class BarAdapter extends BaseAdapter {

        private ArrayList<Bar> bars;

    public BarAdapter(ArrayList<Bar> bars) {
        this.bars = bars;
    }

    @Override
    public int getCount() {
        return bars.size();
    }

    @Override
    public Object getItem(int position) {
        return bars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View customView = inflater.inflate(R.layout.item, parent, false);

        ImageView imageView = (ImageView) customView.findViewById(R.id.imageView);
        TextView textViewName = (TextView) customView.findViewById(R.id.textViewBarName);
        TextView textViewAddress = (TextView) customView.findViewById(R.id.textViewBarAddress);

        Glide.with(parent.getContext()).load(bars.get(position).getImage()).fitCenter().into(imageView);
        textViewName.setText(bars.get(position).getName());
        textViewAddress.setText(bars.get(position).getAddress());

        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), MarkerActivity.class);
                intent.putExtra("name", bars.get(position).getName());
                intent.putExtra("address", bars.get(position).getAddress());
                intent.putExtra("phone", bars.get(position).getPhone());
                intent.putExtra("url", bars.get(position).getUrl());
                intent.putExtra("checkins", bars.get(position).getCheckins());
                intent.putExtra("herenow", bars.get(position).getHereNow());
                parent.getContext().startActivity(intent);
            }
        });

        return customView;
    }

}