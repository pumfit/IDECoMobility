package com.teamide.idecomobility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.ViewHolder> {

    private ArrayList<InfoAddress> mData = null;
    SQLiteDatabase db;
    Context context;

    public BookMarkAdapter(ArrayList<InfoAddress> list, Context context,SQLiteDatabase db) {

        mData = list;
        this.db = db;
        this.context = context;
    }


    @NonNull
    @Override
    public BookMarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.bookmark_address_item, parent, false);
        BookMarkAdapter.ViewHolder vh = new BookMarkAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final BookMarkAdapter.ViewHolder holder, final int position) {
        String text = mData.get(position).getStartAddress().getMainAdress();
        String subText = mData.get(position).getEndAddress().getMainAdress();
        holder.startTextView.setText(text);
        holder.endTextView.setText(subText);
        holder.deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mData.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());
                Log.v("mytag", (position)+"번 삭제");
                setDb();
            }
        });
        holder.searchButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Context context = v.getContext();
                Intent in = new Intent(context, RouteResultActivity.class);
                in.putExtra("infoAddress", (Parcelable) mData.get(position));
                ((Activity)context).startActivity(in);
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

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            startTextView = itemView.findViewById(R.id.textView1);
            endTextView = itemView.findViewById(R.id.textView2);
            deleteButton = itemView.findViewById(R.id.deletebutton);
            searchButton = itemView.findViewById(R.id.searchbutton);
        }
    }

    public void setDb()
    {
        db.execSQL("delete from bookmarks");
        for(int i=0;i<mData.size();i++)
        {
            String s_main = mData.get(i).getStartAddress().getMainAdress();
            String s_full = mData.get(i).getStartAddress().getFullAdress();
            Double s_la = mData.get(i).getStartAddress().getLatitude();
            Double s_lo = mData.get(i).getStartAddress().getLongitude();
            String e_main = mData.get(i).getEndAddress().getMainAdress();
            String e_full = mData.get(i).getEndAddress().getFullAdress();
            Double e_la = mData.get(i).getEndAddress().getLatitude();
            Double e_lo = mData.get(i).getEndAddress().getLongitude();

            db.execSQL("INSERT INTO bookmarks VALUES (null,'" + s_main + "','" +s_full + "','" +s_la + "','" +s_lo
                    + "','" +e_main + "','" +e_full + "','" +e_la + "','" + e_lo + "');");
        }
        Log.v("mytag","데이테베이스에 삭제후 데이터 다시 저장함");
    }

}
