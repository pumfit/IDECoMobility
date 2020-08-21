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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class SearchActivity2 extends Activity {

    private GpsTracker gpsTracker;
    public ArrayList<SearchAddress> dataList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

        dataList = new ArrayList<SearchAddress>();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.adressRecyclerView2);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager); // LayoutManager 등록
        recyclerView.setAdapter(new SearchAddressAdapter2(dataList));  // Adapter 등록
    }

    public void onClickedSearch(View v)
    {
        if(!(dataList==null)) {
            dataList.clear();
        }
        EditText startText = (EditText) findViewById(R.id.addressSearchEditText4);
        String startAdress = startText.getText().toString();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addressList = null;

        try {
            addressList = geocoder.getFromLocationName(startAdress, 5); // 최대 검색 결과 개수
            Log.d("ad", "검색 결과 개수"+String.valueOf(addressList.size()));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if(addressList.size()==0)
            {
                Log.d("ad","검색결과없음");
            }else
            {
                Location location2 = new Location("current location");
                gpsTracker = new GpsTracker(SearchActivity2.this);
                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();
                location2.setLatitude(latitude);
                location2.setLongitude(longitude);
                for (int i =0;i<addressList.size();i++)
                {
                    Address ad = addressList.get(i);
                    String address = ad.getAddressLine(0).toString()+"\n";
                    Location location = new Location("seaech location");
                    location.setLatitude(ad.getLatitude());
                    location.setLongitude(ad.getLongitude());
                    double distance = location.distanceTo(location2)/1000;
                    Log.d("ad",String.valueOf(distance));
                    dataList.add(new SearchAddress(startAdress,address,String.format("%.1f", distance)+"Km"));//현재 위치와의 거리
                }
            }
            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.adressRecyclerView1);
            LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(manager); // LayoutManager 등록
            recyclerView.setAdapter(new SearchAddressAdapter(dataList));  // Adapter 등록
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
