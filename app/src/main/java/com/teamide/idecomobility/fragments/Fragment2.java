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

public class Fragment2 extends Fragment {

    private RecyclerView recyclerView;
    private SubWayInfoAdapter adapter;
    private ArrayList<SubWayInfoData> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_escalator,container,false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        list.clear();
        list.add(new SubWayInfoData(R.drawable.ic_escalator,"http://indoormap.seoul.go.kr/app/openapi/seoulcity/handicapPOIInfo/78/stream","1호선 환승통로 에스컬레이터"));
        list.add(new SubWayInfoData(R.drawable.ic_escalator,"http://indoormap.seoul.go.kr/app/openapi/seoulcity/handicapPOIInfo/81/stream","1호선 환승통로 계단,에스컬레이터"));
        list.add(new SubWayInfoData(R.drawable.ic_escalator,"http://indoormap.seoul.go.kr/app/openapi/seoulcity/handicapPOIInfo/187/stream","1호선 B1 대합실 에스컬레이터"));
        recyclerView.setHasFixedSize(true);
        adapter = new SubWayInfoAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
