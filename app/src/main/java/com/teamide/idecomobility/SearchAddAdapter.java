package com.teamide.idecomobility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAddAdapter extends RecyclerView.Adapter<SearchAddAdapter.ViewHolder> {

    private ArrayList<SearchAddress> mData = null;

    public SearchAddAdapter(ArrayList<SearchAddress> list) {
        mData = list;
    }

    @NonNull
    @Override
    public SearchAddAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.addl_address_item, parent, false);
        SearchAddAdapter.ViewHolder vh = new SearchAddAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchAddAdapter.ViewHolder holder, final int position) {
        String text = mData.get(position).getMainAdress();
        String subText = mData.get(position).getFullAdress();
        holder.textView.setText(text);
        holder.subtextView.setText(subText);

//        holder.deleteButton.setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View v) {
//                mData.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, getItemCount());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView subtextView;

        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            textView = itemView.findViewById(R.id.textView1);
            subtextView = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) { //해당 검색결과 클릭시 intent로 데이터를 넘김
                    int pos = getAdapterPosition() ;
                    Context context = v.getContext();

                    if(((Activity)context).getLocalClassName().equals("SearchActivity"))
                    {
                        Intent intent = new Intent(v.getContext(),MainActivity.class);
                        intent.putExtra("startAllAddress", mData.get(pos));
                        ((Activity)context).setResult(Activity.RESULT_OK,intent);
                        ((Activity)context).finish();
                    }
                    else if(((Activity)context).getLocalClassName().equals("SearchActivity2"))
                    {

                        Intent intent = new Intent(v.getContext(),MainActivity.class);
                        intent.putExtra("endAllAddress", mData.get(pos));
                        ((Activity)context).setResult(Activity.RESULT_OK,intent);
                        ((Activity)context).finish();
                    }
                }
            });
        }
    }
}
