package com.teamide.idecomobility.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teamide.idecomobility.R;
import com.teamide.idecomobility.SubWayInfoAdapter;
import com.teamide.idecomobility.SubWayInfoData;

import java.util.ArrayList;

public class Fragment1 extends Fragment {

    private RecyclerView recyclerView;
    private SubWayInfoAdapter adapter;
    private ArrayList<SubWayInfoData> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_elevator,container,false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        list.clear();
        list.add(new SubWayInfoData(R.drawable.ic_elevator,"http://indoormap.seoul.go.kr/app/openapi/seoulcity/handicapPOIInfo/149/stream","엘리베이터 지상 9번출구"));
        list.add(new SubWayInfoData(R.drawable.ic_elevator,"http://indoormap.seoul.go.kr/app/openapi/seoulcity/handicapPOIInfo/148/stream","엘리베이터 지하1층 9번출구"));
        list.add(new SubWayInfoData(R.drawable.ic_elevator,"http://indoormap.seoul.go.kr/app/openapi/seoulcity/handicapPOIInfo/75/stream","엘리베이터 지하1층 2호선"));
        //list.add(new SubWayInfoData(R.drawable.ic_elevator,"http://indoormap.seoul.go.kr/app/openapi/seoulcity/handicapPOIInfo/71/stream","엘리베이터 지하2층 대합실"));
        recyclerView.setHasFixedSize(true);
        adapter = new SubWayInfoAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        
        return rootView;
    }
}
