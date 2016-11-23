package com.koltinjo.pureagency_android_test;

import android.content.Context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View customView = inflater.inflate(R.layout.item, parent, false);

        ImageView imageView = (ImageView) customView.findViewById(R.id.imageView);
        TextView textViewName = (TextView) customView.findViewById(R.id.textViewBarName);
        TextView textViewAddress = (TextView) customView.findViewById(R.id.textViewBarAddress);

        Glide.with(parent.getContext()).load(bars.get(position).getImage()).fitCenter().into(imageView);
        textViewName.setText(bars.get(position).getName());
        textViewAddress.setText(bars.get(position).getAddress());

        return customView;
    }

//    private ArrayList<Bar> bars;
//
//    public BarAdapter(Context context, @LayoutRes int resource, ArrayList<Bar> bars) {
//        super(context, resource);
//        this.bars = bars;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Log.d("oiram", "list" + position);
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        View customView = inflater.inflate(R.layout.item, parent, false);
//
//        ImageView imageView = (ImageView) customView.findViewById(R.id.imageView);
//        TextView textViewName = (TextView) customView.findViewById(R.id.textViewBarName);
//        TextView textViewAddress = (TextView) customView.findViewById(R.id.textViewBarAddress);
//
//        Glide.with(parent.getContext()).load(bars.get(position).getImage()).fitCenter().into(imageView);
//        textViewName.setText(bars.get(position).getName());
//        textViewAddress.setText(bars.get(position).getAddress());
//
//        return customView;
//    }
}