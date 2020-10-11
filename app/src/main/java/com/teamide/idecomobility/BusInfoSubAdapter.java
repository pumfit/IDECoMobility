package com.teamide.idecomobility;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import java.util.ArrayList;

public class BusInfoSubAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<BusInfoSubData> busData;
    public ODsayService oDsayService;

    public BusInfoSubAdapter(Context context, ArrayList<BusInfoSubData> data) {
        mContext = context;
        busData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return busData.size();
    }

    @Override
    public Object getItem(int position) {
        return busData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.businfosub_item, null);

        TextView busNum = (TextView)view.findViewById(R.id.busNumTextView);
        TextView arrivalTime = (TextView)view.findViewById(R.id.arrivalTimeTextView);

        busNum.setText(busData.get(position).getBusNum());
        arrivalTime.setText(busData.get(position).getArrivalTime());

        return view;
    }
}
