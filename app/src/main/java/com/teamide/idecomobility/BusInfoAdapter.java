package com.teamide.idecomobility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BusInfoAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<BusStaionData> subwayData;

    public BusInfoAdapter(Context mContext, ArrayList<BusStaionData> busStaionData) {
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.subwayData = busStaionData;
    }

    @Override
    public int getCount() {
        return subwayData.size();
    }

    @Override
    public Object getItem(int position) {
        return subwayData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.businfo_item, null);

        ImageView imageView = (ImageView)view.findViewById(R.id.listNumimageView);
        TextView movieName = (TextView)view.findViewById(R.id.busStationNametextView);

        imageView.setImageResource(subwayData.get(position).getListNum());
        movieName.setText(subwayData.get(position).getBusStationName());

        return view;
    }
}
