package com.teamide.idecomobility;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ArrayList<Address> dataList = this.InitializeData();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.adressRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager); // LayoutManager 등록
        recyclerView.setAdapter(new SearchAdressAdapter(dataList));  // Adapter 등록

    }

    public ArrayList<Address> InitializeData()
    {
        ArrayList<Address> dataList = new ArrayList<>();
        dataList.add(new Address("서울여대","서울여대 중앙","10km"));

        return dataList;
    }
}