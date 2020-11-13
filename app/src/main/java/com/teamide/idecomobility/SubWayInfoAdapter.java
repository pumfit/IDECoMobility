package com.teamide.idecomobility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SubWayInfoAdapter extends RecyclerView.Adapter<SubWayInfoAdapter.Holder> {

    private Context context;
    private List<SubWayInfoData> list = new ArrayList<>();

    public SubWayInfoAdapter(Context context, List<SubWayInfoData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_subwayinfo_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        holder.icon.setImageResource(list.get(position).getIcon());
        ImageLoadTask task = new ImageLoadTask(list.get(position).getInfoImage(),holder.infoImage);
        task.execute();
        holder.title.setText(list.get(position).getTitle());
        holder.dropButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if(holder.infoImage.getVisibility()==View.GONE)
                {
                    holder.infoImage.setVisibility(View.VISIBLE);
                }else
                {
                    holder.infoImage.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        public ImageView icon;
        public ImageView infoImage;
        public TextView title;
        public Button dropButton;

        public Holder(View view){
            super(view);
            icon = (ImageView) view.findViewById(R.id.iconImageView);
            infoImage = (ImageView) view.findViewById(R.id.infoImageView);
            title = (TextView) view.findViewById(R.id.titleTextView);
            dropButton = (Button) view.findViewById(R.id.dropButton);
        }
    }

}
