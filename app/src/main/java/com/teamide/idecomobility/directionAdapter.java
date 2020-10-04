package com.teamide.idecomobility;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class directionAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<direction_data> sample;

    public directionAdapter(Context context, ArrayList<direction_data> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public direction_data getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.list_direction, null);

        ImageView img = (ImageView) view.findViewById(R.id.img);
        TextView shape = (TextView) view.findViewById(R.id.circle);
        TextView title = (TextView)view.findViewById(R.id.title);
        TextView vehicle = (TextView)view.findViewById(R.id.busOrSub);
        TextView num = (TextView)view.findViewById(R.id.num);
        TextView minLeft = (TextView)view.findViewById(R.id.minLeft);

        img.setImageResource(sample.get(position).getImg());
        shape.setText(sample.get(position).getcircle());
        title.setText(sample.get(position).gettitle());
        vehicle.setText(sample.get(position).getbusOrSub());
        num.setText(sample.get(position).getnum());
        minLeft.setText(sample.get(position).getminLeft());

        return view;
    }
}