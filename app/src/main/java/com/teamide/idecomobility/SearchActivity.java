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

public class SearchActivity extends Activity { //출발지 검색시 실행되는 액티비티

    private GpsTracker gpsTracker;
    public ArrayList<SearchAddressData> dataList;
    public ArrayList<SearchAddressData> savelist;
    private SharedPreferences preferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dataList = new ArrayList<SearchAddressData>(); //역 GeoCoder를 통해 알아온 주소 결과들을 저장하는 배열리스트
        savelist = new ArrayList<SearchAddressData>();//저장해둔 리스트

        preferences = getSharedPreferences("info", MODE_PRIVATE);
        savelist = getAddressList();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.adressRecyclerView1);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        SearchAddAdapter adapter = new SearchAddAdapter(savelist);
        recyclerView.setAdapter(adapter);

        View view = findViewById(R.id.nodata);//검색결과 없는 경우 띄워줄 image
        view.setVisibility(View.GONE);
    }

    public ArrayList<SearchAddressData> getAddressList() {
        ArrayList<SearchAddressData> savelist = new ArrayList<>();
        ArrayList<String> full = getStringArrayPref("Addaddress1");
        ArrayList<String> main = getStringArrayPref("Addaddress2");
        ArrayList<String> lo = getStringArrayPref("Addaddress3");
        ArrayList<String> la = getStringArrayPref("Addaddress4");
        if (full.size() > 0) {
            for (int i = 0; i < full.size(); i++) {
                savelist.add(new SearchAddressData(main.get(i), full.get(i), "0km", Double.parseDouble(lo.get(i)), Double.parseDouble(la.get(i))));
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

    public void onClickedSearch(View v)//검색을 눌렀을 경우
    {
        TextView textView = findViewById(R.id.textView4);
        textView.setVisibility(View.GONE);
        if (!(dataList == null)) {
            dataList.clear();//기존 데이터리스트 clear()
        }
        EditText startText = (EditText) findViewById(R.id.addressSearchEditText3);
        String startAddress = startText.getText().toString();//사용자가 검색한 주소를 불러옴
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocationName(startAddress, 5); //역 Geocoding으로 최대 검색 결과 개수 5개까지의 주소를 불러옴
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
                    Location location = new Location("search location");
                    location.setLatitude(ad.getLatitude());
                    location.setLongitude(ad.getLongitude());
                    double distance = location.distanceTo(location2) / 1000;
                    Log.d("ad", String.valueOf(distance));
                    dataList.add(new SearchAddressData(startAddress, address, String.format("%.1f", distance) + "Km", location.getLatitude(), location.getLongitude()));
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