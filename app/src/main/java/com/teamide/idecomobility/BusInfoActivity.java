package com.teamide.idecomobility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class BusInfoActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public  InfoAddress infoAddress;

    LinearLayout draglayout;
    boolean isSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businfo);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map); //프래그먼트 생성
        //SupportMapFragment mapFragment = findViewById(R.id.map);
        mapFragment.getMapAsync(this);//맵을 불러옴

        Intent intent = getIntent();
        infoAddress = intent.getParcelableExtra("infoAddress");

        draglayout = (LinearLayout) findViewById(R.id.draglistView);
        draglayout.setVisibility(View.INVISIBLE);

        final ListView listView = findViewById(R.id.listbusView);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                new String[]{"copy","past","cut","delete","convert","open", "copy","past","cut","delete","convert","open", "copy","past","cut","delete","convert","open"}));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), position+" 번째 값 : " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng CLocation = new LatLng(infoAddress.getCurruntAddress().getLatitude(),infoAddress.getCurruntAddress().getLongitude()); //위경도 좌표를 나타내는 클래스
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(CLocation);
        mMap.addMarker(markerOptions);//마커를 맵 객체에 추가함

        mMap.moveCamera(CameraUpdateFactory.newLatLng(CLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    public  void onClickedSearch(View v)
    {
        if(isSelected==false)
        {
            draglayout.setVisibility(View.VISIBLE);
            draglayout.setTop(1000);
            Log.d("VIEW","보여져야함"+draglayout.getVisibility());
            isSelected = true;
        }
        else
        {
            draglayout.setVisibility(View.INVISIBLE);
            Log.d("VIEW","안보여져야함"+draglayout.getVisibility());
            isSelected = false;
        }
    }
}
