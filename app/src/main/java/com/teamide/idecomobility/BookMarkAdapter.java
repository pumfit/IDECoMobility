package com.teamide.idecomobility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.ViewHolder> {

    private ArrayList<InfoAddress> mData = null;
    private Boolean selectlist[];
    Context context;
    public Boolean isEdited = false;

    public BookMarkAdapter(ArrayList<InfoAddress> list, Context context) {

        mData = list;
        selectlist = new Boolean[mData.size()];
        Arrays.fill(selectlist,false);
        this.context = context;
    }

    public Boolean getEdited() {
        return isEdited;
    }

    public void setEdited(Boolean edited) {
        isEdited = edited;
    }

    public ArrayList<InfoAddress> getListData() {
        return mData;
    }

    @NonNull
    @Override
    public BookMarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.bookmark_address_item, parent, false);
        BookMarkAdapter.ViewHolder vh = new BookMarkAdapter.ViewHolder(view);
        if (!isEdited) {
            vh.checkBox.setVisibility(View.GONE);
        } else {
            vh.checkBox.setVisibility(View.VISIBLE);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final BookMarkAdapter.ViewHolder holder, final int position) {
        String text = mData.get(position).getStartAddress().getMainAdress();
        String subText = mData.get(position).getEndAddress().getMainAdress();
        holder.startTextView.setText(text);
        holder.endTextView.setText(subText);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                if (cb.isChecked()) {
                    selectlist[position] = true;
                } else {
                    selectlist[position] = false;
                }
            }
        });
        holder.searchButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Context context = v.getContext();
                Intent in = new Intent(context, RouteResultActivity.class);
                in.putExtra("infoAddress", (Parcelable) mData.get(position));
                ((Activity) context).startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView startTextView;
        TextView endTextView;
        Button deleteButton;
        Button searchButton;
        CheckBox checkBox;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            startTextView = itemView.findViewById(R.id.textView1);
            endTextView = itemView.findViewById(R.id.textView2);
            deleteButton = itemView.findViewById(R.id.deletebutton);
            searchButton = itemView.findViewById(R.id.searchbutton);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    public Boolean[] getSelectList()
    {
        return selectlist;
    }

}
