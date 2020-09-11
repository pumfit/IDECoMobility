
package com.teamide.idecomobility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

        TextView title = (TextView)view.findViewById(R.id.title);
        Button button = (Button) view.findViewById(R.id.button);

        title.setText(sample.get(position).gettitle());
        button.setText(sample.get(position).getbutton());

        return view;
    }
}

