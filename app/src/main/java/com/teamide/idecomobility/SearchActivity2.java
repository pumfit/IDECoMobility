package com.teamide.idecomobility;

import android.app.Activity;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity2 extends Activity {

    private GpsTracker gpsTracker;
    public ArrayList<SearchAddress> dataList;
    public ArrayList<SearchAddress> savelist;
    private SharedPreferences preferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

        dataList = new ArrayList<SearchAddress>();
        savelist = new ArrayList<SearchAddress>();//저장해둔 리스트

        preferences = getSharedPreferences("info", MODE_PRIVATE);
        savelist = getAddressList();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.adressRecyclerView2);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager); // LayoutManager 등록

        SearchAddAdapter adapter = new SearchAddAdapter(savelist);
        recyclerView.setAdapter(adapter);

        View view = findViewById(R.id.nodata);
        view.setVisibility(View.GONE);
    }

    public ArrayList<SearchAddress> getAddressList() {
        ArrayList<SearchAddress> savelist = new ArrayList<>();
        ArrayList<String> full = getStringArrayPref("Addaddress1");
        ArrayList<String> main = getStringArrayPref("Addaddress2");
        ArrayList<String> lo = getStringArrayPref("Addaddress3");
        ArrayList<String> la = getStringArrayPref("Addaddress4");
        if (full.size() > 0) {
            for (int i = 0; i < full.size(); i++) {
                savelist.add(new SearchAddress(main.get(i), full.get(i), "0km", Double.parseDouble(lo.get(i)), Double.parseDouble(la.get(i))));
            }
        }
        return savelist;
    }

    private ArrayList<String> getStringArrayPref(String key) {
        preferences = getSharedPreferences("info", MODE_PRIVATE);
        String json = preferences.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    public void onClickedSearch(View v)
    {
        TextView textView = findViewById(R.id.textView4);
        textView.setVisibility(View.GONE);
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
                View view = findViewById(R.id.nodata);
                view.setVisibility(View.VISIBLE);
                Log.d("ad","검색결과없음");
            }else
            {
                View view = findViewById(R.id.nodata);
                view.setVisibility(View.GONE);
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
                    dataList.add(new SearchAddress(startAdress,address,String.format("%.1f", distance)+"Km",location.getLatitude(),location.getLongitude()));//현재 위치와의 거리
                }
            }
            RecyclerView recyclerView = (RecyclerView)findViewById(R.id.adressRecyclerView2);
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
