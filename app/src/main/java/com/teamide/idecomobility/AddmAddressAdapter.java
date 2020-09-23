package com.teamide.idecomobility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddmAddressAdapter extends RecyclerView.Adapter<AddmAddressAdapter.ViewHolder> {

    private ArrayList<SearchAddress> mData = null;

    public AddmAddressAdapter(ArrayList<SearchAddress> list) {
        mData = list;
    }

    @NonNull
    @Override
    public AddmAddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.addm_address_item, parent, false);
        AddmAddressAdapter.ViewHolder vh = new AddmAddressAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final AddmAddressAdapter.ViewHolder holder, final int position) {
        String text = mData.get(position).getMainAdress();
        String subText = mData.get(position).getFullAdress();
        holder.textView.setText(text);
        holder.subtextView.setText(subText);

        holder.deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mData.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView subtextView;
        Button deleteButton;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            textView = itemView.findViewById(R.id.textView1);
            subtextView = itemView.findViewById(R.id.textView2);
            deleteButton = itemView.findViewById(R.id.deletebutton);
        }
    }

}
