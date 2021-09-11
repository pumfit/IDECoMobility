package com.teamide.idecomobility;

import android.content.Context;
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
    ArrayList<DirectionData> sample;

    public directionAdapter(Context context, ArrayList<DirectionData> data) {
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
    public DirectionData getItem(int position) {
        return sample.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.list_direction, null);

        ImageView img = (ImageView) view.findViewById(R.id.img);
        TextView shape = (TextView) view.findViewById(R.id.circle);
        TextView start = (TextView)view.findViewById(R.id.startTraffic);
        TextView end = (TextView)view.findViewById(R.id.endTraffic);
        TextView movetime = (TextView)view.findViewById(R.id.moveTime);
        TextView stationNm = (TextView)view.findViewById(R.id.stationCount);


        img.setImageResource(sample.get(position).getImg());
        shape.setText(sample.get(position).getcircle());
        start.setText(sample.get(position).getstartTraffic());
        end.setText(sample.get(position).getendTraffic());
        movetime.setText(sample.get(position).getmoveTime());
        stationNm.setText(sample.get(position).getstationCount());

        return view;
    }
}