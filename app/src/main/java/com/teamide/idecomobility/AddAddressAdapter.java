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

public class AddAddressAdapter extends RecyclerView.Adapter<AddAddressAdapter.ViewHolder> {

    private ArrayList<SearchAddress> mData = null;

    public AddAddressAdapter(ArrayList<SearchAddress> list) {
        mData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.add_address_item, parent, false);
        AddAddressAdapter.ViewHolder vh = new AddAddressAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        String text = mData.get(position).getMainAdress();
        holder.textView1.setText(text);

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
        TextView textView1;
        Button deleteButton;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.addressTextView);
            deleteButton = itemView.findViewById(R.id.deletebutton);
        }
    }

}
