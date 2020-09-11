package com.teamide.idecomobility;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends Activity { //출발지 검색시 실행되는 액티비티

    private GpsTracker gpsTracker;
    public ArrayList<SearchAddress> dataList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dataList = new ArrayList<SearchAddress>(); //역 GeoCoder를 통해 알아온 주소 결과들을 저장하는 배열리스트

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.adressRecyclerView1);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new SearchAddressAdapter(dataList));

        View view = findViewById(R.id.nodata);//검색결과 없는 경우 띄워줄 image
        view.setVisibility(View.GONE);
    }

    public void onClickedSearch(View v)//검색을 눌렀을 경우
    {
        if (!(dataList == null)) {
            dataList.clear();//기존 데이터리스트 clear()
        }
        EditText startText = (EditText) findViewById(R.id.addressSearchEditText3);
        String startAdress = startText.getText().toString();//사용자가 검색한 주소를 불러옴
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocationName(startAdress, 5); //역 Geocoding으로 최대 검색 결과 개수 5개까지의 주소를 불러옴
            Log.d("ad", "검색 결과 개수" + String.valueOf(addressList.size()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if (addressList.size() == 0)//검색결과가 없는 경우 알람 image를 띄움
            {
                View view = findViewById(R.id.nodata);
                view.setVisibility(View.VISIBLE);
                Log.d("ad", "검색결과없음");
            } else//받아온 데이터를 dataList에  SearchAddress형식으로 저장 -> 검색주소,전체주소,현위치에서의 거리,위도,경도
            {
                View view = findViewById(R.id.nodata);
                view.setVisibility(View.GONE);
                Location location2 = new Location("current location");
                gpsTracker = new GpsTracker(SearchActivity.this);
                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();
                location2.setLatitude(latitude);
                location2.setLongitude(longitude);
                for (int i = 0; i < addressList.size(); i++) {
                    Address ad = addressList.get(i);
                    String address = ad.getAddressLine(0).toString() + "\n";
                    Location location = new Location("seaech location");
                    location.setLatitude(ad.getLatitude());
                    location.setLongitude(ad.getLongitude());
                    double distance = location.distanceTo(location2) / 1000;
                    Log.d("ad", String.valueOf(distance));
                    dataList.add(new SearchAddress(startAdress, address, String.format("%.1f", distance) + "Km", location.getLatitude(), location.getLongitude()));
                }
            }
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.adressRecyclerView1);//RecyclerView 아이템 재등록
            LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager); // LayoutManager 등록
            recyclerView.setAdapter(new SearchAddressAdapter(dataList));  // Adapter 등록
        } catch (IndexOutOfBoundsException e) {
            e.getStackTrace();
        } catch (NullPointerException e) {
            e.getStackTrace();
        }
    }
}