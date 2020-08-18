package com.teamide.idecomobility;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

public class SearchActivity extends Activity {

    private Geocoder geocoder = null;
    public String sLatitude = "37.6281126";
    public String sLongitude = "127.09045680000001";
    public ArrayList<SearchAddress> dataList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        geocoder = new Geocoder(this);
        dataList = new ArrayList<SearchAddress>();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.adressRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager); // LayoutManager 등록
        recyclerView.setAdapter(new SearchAdressAdapter(dataList));  // Adapter 등록

    }

    public void onClickedSearch(View v)
    {
        if(!(dataList==null)) {
            dataList.clear();
        }
        EditText startText = (EditText) findViewById(R.id.addressSearchEditText);
        String startAdress = startText.getText().toString();

        List<Address> addressList = null;

        try {
            addressList = geocoder.getFromLocationName(startAdress, 10); // 최대 검색 결과 개수
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            // 콤마를 기준으로 split
            for (int i =0;i<addressList.size();i++)
            {
                String []splitStr = addressList.get(i).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // 주소
                Log.d("ad",address);
                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도

                sLatitude = latitude;
                sLongitude = longitude;
                Log.d("ad",latitude);
                Log.d("ad",longitude);

                dataList.add(new SearchAddress(startAdress,address,"10km"));
            }

            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.adressRecyclerView);
            LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(new SearchAdressAdapter(dataList));
        }
        catch (IndexOutOfBoundsException e) {
            e.getStackTrace();
        }
        catch(NullPointerException e)
        {
            e.getStackTrace();
        }
    }
}