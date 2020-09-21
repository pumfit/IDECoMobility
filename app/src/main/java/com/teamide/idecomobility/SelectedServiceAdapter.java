package com.teamide.idecomobility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectedServiceAdapter extends RecyclerView.Adapter<SelectedServiceAdapter.ViewHolder> {

    private ArrayList<String> list;

    public SelectedServiceAdapter( ArrayList<String> itemList) {
        this.list = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.selected_service_item, parent, false);
        SelectedServiceAdapter.ViewHolder vh = new SelectedServiceAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = list.get(position);
        holder.textview.setText(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textview = itemView.findViewById(R.id.serviceTextView);
        }
    }
}
