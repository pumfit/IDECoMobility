package com.teamide.idecomobility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAddressAdapter extends RecyclerView.Adapter<SearchAddressAdapter.ViewHolder>{

    public ArrayList<SearchAddressData> myDataList = new ArrayList<SearchAddressData>();

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
                public void onClick(View v) { //해당 검색결과 클릭시 intent로 데이터를 넘김
                    int pos = getAdapterPosition() ;
                    Context context = v.getContext();
                    if(((Activity)context).getLocalClassName().equals("SearchActivity"))
                    {
                        Intent intent = new Intent(v.getContext(),MainActivity.class);
                        intent.putExtra("startAllAddress", myDataList.get(pos));
                        ((Activity)context).setResult(Activity.RESULT_OK,intent);
                        ((Activity)context).finish();
                    }
                    else if(((Activity)context).getLocalClassName().equals("SearchActivity2"))
                    {
                        Intent intent = new Intent(v.getContext(),MainActivity.class);
                        intent.putExtra("endAllAddress", myDataList.get(pos));
                        ((Activity)context).setResult(Activity.RESULT_OK,intent);
                        ((Activity)context).finish();
                    }
                }
            });
        }
    }

    SearchAddressAdapter(ArrayList<SearchAddressData> dataList)
    {
        myDataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recycler_address_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mainAdress.setText( myDataList.get(position).getMainAdress());
        holder.fullAdress.setText(myDataList.get(position).getFullAdress());
        holder.distance.setText(myDataList.get(position).getDistance());
    }

    @Override
    public int getItemCount() { //Adapter가 관리하는 전체 데이터 개수 반환
        if(! (myDataList==null))
        {
            return myDataList.size();
        }else
        {
            return 0;
        }
    }

}