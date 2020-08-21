package com.teamide.idecomobility;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAddressAdapter2 extends RecyclerView.Adapter<SearchAddressAdapter2.ViewHolder> {


    public ArrayList<SearchAddress> myDataList;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mainAdress;
        TextView fullAdress;
        TextView distance;

        ViewHolder(View itemView)
        {
            super(itemView);

            mainAdress = itemView.findViewById(R.id.titleTextView);
            fullAdress = itemView.findViewById(R.id.subTitleTextView);
            distance = itemView.findViewById(R.id.kmTextView);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(v.getContext(),MainActivity.class);
                    int pos = getAdapterPosition() ;
                    intent.putExtra("endAddress",myDataList.get(pos).getFullAdress());
                    Log.d("ad",myDataList.get(pos).getFullAdress());
                    context.startActivity(intent);
                }
            });
        }
    }

    SearchAddressAdapter2(ArrayList<SearchAddress> dataList)
    {
        myDataList = dataList;
    }

    @Override
    public SearchAddressAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //전개자(Inflater)를 통해 얻은 참조 객체를 통해 뷰홀더 객체 생성
        View view = inflater.inflate(R.layout.recycler_adress_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mainAdress.setText( myDataList.get(position).getMainAdress());
        holder.fullAdress.setText(myDataList.get(position).getFullAdress());
        holder.distance.setText(myDataList.get(position).getDistance());

    }

    @Override
    public int getItemCount() {
        //Adapter가 관리하는 전체 데이터 개수 반환
        return myDataList.size();
    }
}
