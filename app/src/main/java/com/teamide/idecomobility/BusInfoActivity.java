package com.teamide.idecomobility;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class BusInfoActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public InfoAddressData infoAddressData;
    public LinearLayout draglayout;
    public EditText editText;

    ArrayList<BusStaionData> busDataList = new ArrayList<>();
    public ArrayList<String> addresslist = new ArrayList<>();
    public ArrayList<String> stlist = new ArrayList<>();
    public ODsayService odsayService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businfo);

        odsayService = ODsayService.init(BusInfoActivity.this, "nFVGyVxSTk6opjbmKKPCTDaEfNWyidhvs1HbmTtAf6U");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map); //프래그먼트 생성
        mapFragment.getMapAsync(this);//맵을 불러옴

        addresslist.add("결과 없음");
        //localStIdlist.add("결과 없음");
        busDataList.add(new BusStaionData(0,"결과없음","결과없음"));

        Intent intent = getIntent();
        infoAddressData = intent.getParcelableExtra("infoAddress");

        editText = findViewById(R.id.searcheditText);
        Toolbar toolbar = findViewById(R.id.businfotoolbar);
        setActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("버스");
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setAddressList(infoAddressData.getCurruntAddress().getMainAdress());

        draglayout = (LinearLayout) findViewById(R.id.draglistView);

        final ListView listView = findViewById(R.id.listsubwayView);
        final BusInfoAdapter mAdapter = new BusInfoAdapter(this,busDataList);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), position + " 번째 값 : " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                String busTitle = mAdapter.subwayData.get(position).getBusStationName();
                String busStId = mAdapter.subwayData.get(position).getBusStationID();
                Intent in = new Intent(getApplicationContext(), BusInfosubActivity.class);
                in.putExtra("bustitle", busTitle);
                in.putExtra("busStId",busStId);
                startActivity(in);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng CLocation = new LatLng(infoAddressData.getCurruntAddress().getLatitude(), infoAddressData.getCurruntAddress().getLongitude()); //위경도 좌표를 나타내는 클래스
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(CLocation);
        mMap.addMarker(markerOptions);//마커를 맵 객체에 추가함

        mMap.moveCamera(CameraUpdateFactory.newLatLng(CLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickedAddressSearch(View v) {
        draglayout.setVisibility(View.VISIBLE);
        draglayout.setActivated(true);

        String address = editText.getText().toString();//사용자가 검색한 주소를 불러옴
        setAddressList(address);
    }

    public void setAddressList(String searchAddress) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addressList = null;

        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {//콜백리스너
            // 호출 성공 시 실행
            @Override
            public void onSuccess(ODsayData odsayData, API api) {
                try {
                    Integer count = (odsayData.getJson().getJSONObject("result").getInt("count")>10)?
                            10:odsayData.getJson().getJSONObject("result").getInt("count");
                    JSONArray station = odsayData.getJson().getJSONObject("result").getJSONArray("station");
                    Log.d("ad", "주변 정류장 갯수" + count.toString());
                    //Log.d("ad", "주변 정류장 전체 데이터" + station.toString());
                    MarkerOptions[] busmarkerOptions = new MarkerOptions[count];
                    LatLng[] CLocation = new LatLng[count];
                    busDataList.clear();
                    for (int i = 0; i < count; i++) {
                        String location = station.getJSONObject(i).getString("stationName");
                        String stID = station.getJSONObject(i).getString("stationID"); // odsay 추가!!!
                        Double busx = station.getJSONObject(i).getDouble("x");
                        Double busy = station.getJSONObject(i).getDouble("y");
                        addresslist.add(i, location);
                        stlist.add(i,stID);
                        CLocation[i] = new LatLng(busy, busx);
                        busmarkerOptions[i] = new MarkerOptions();
                        busmarkerOptions[i].position(CLocation[i]);
                        mMap.addMarker(busmarkerOptions[i]);
                        if (i == 0) {
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(CLocation[0]));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                        }
                        busDataList.add(i, new BusStaionData(i,addresslist.get(i),stlist.get(i)));
                    }
                    final ListView listView = findViewById(R.id.listsubwayView);
                    final BusInfoAdapter mAdapter = new BusInfoAdapter(getApplicationContext(),busDataList);
                    listView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedOperationException e) {
                    e.printStackTrace();
                }
            }

            // 호출 실패 시 실행
            @Override
            public void onError(int i, String s, API api) {
                Log.d("ad", "오딧세이 Can't read data");
                Log.d("ad", "에러코드" + s);
            }
        };

        try {
            if(searchAddress.equals("현위치"))
            {
                addressList = geocoder.getFromLocation(infoAddressData.getCurruntAddress().getLatitude(), infoAddressData.getCurruntAddress().getLongitude(),5);
            }
            else
            {
                addressList = geocoder.getFromLocationName(searchAddress, 5); //역 Geocoding으로 최대 검색 결과 개수 5개까지의 주소를 불러옴
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            if (addressList.size() == 0)//검색결과가 없는 경우 알람을 띄움
            {
                Toast.makeText(getApplicationContext(), "검색 결과 없음", Toast.LENGTH_SHORT).show();
            } else {
                Address ad = addressList.get(0);
                addresslist.clear();
                odsayService.requestPointSearch(String.valueOf(ad.getLongitude()), String.valueOf(ad.getLatitude()), "500", "1", onResultCallbackListener);
            }
        } catch (IndexOutOfBoundsException e) {
            e.getStackTrace();
        } catch (NullPointerException e) {
            e.getStackTrace();
        }
        final ListView listView = findViewById(R.id.listsubwayView);
        final BusInfoAdapter mAdapter = new BusInfoAdapter(getApplicationContext(),busDataList);
        listView.setAdapter(mAdapter);
    }

}