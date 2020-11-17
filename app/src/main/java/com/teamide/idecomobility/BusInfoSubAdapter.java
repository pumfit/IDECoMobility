package com.teamide.idecomobility;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import java.util.ArrayList;

public class BusInfoSubAdapter extends BaseAdapter implements View.OnClickListener{

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<BusInfoSubData> busData;
    public ODsayService oDsayService;

    public interface ListBtnClickListener{
        void onListBtnClick(int position);
    }
    int resourceId;
    private ListBtnClickListener listBtnClickListener;

    public BusInfoSubAdapter(Context context, int resource, ArrayList<BusInfoSubData> data,ListBtnClickListener clickListener) {
        mContext = context;
        busData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.resourceId = resource;
        this.listBtnClickListener = clickListener;
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
        final int pos = position;
        View view = mLayoutInflater.inflate(R.layout.businfosub_item, null);

        TextView busNum = (TextView)view.findViewById(R.id.busNumTextView);
        TextView arrivalTime = (TextView)view.findViewById(R.id.arrivalTimeTextView);

        Button button = (Button)view.findViewById(R.id.button8);
        button.setTag(position);
        button.setOnClickListener(this);

        busNum.setText(busData.get(position).getBusNum());
        arrivalTime.setText(busData.get(position).getArrivalTime());

        //busType 어떻게 표시할지 코드 추가 - busData.get(position).getBusType() 이용

        return view;
    }

    @Override
    public void onClick(View view) {
        final BusHelpDialog dialog = new BusHelpDialog(mContext);
        dialog.show();
    }
}
