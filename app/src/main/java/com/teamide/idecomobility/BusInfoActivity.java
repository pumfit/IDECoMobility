package com.teamide.idecomobility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businfo);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map); //프래그먼트 생성
        //SupportMapFragment mapFragment = findViewById(R.id.map);
        mapFragment.getMapAsync(this);//맵을 불러옴

        Intent intent = getIntent();
        infoAddress = intent.getParcelableExtra("infoAddress");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

       // LatLng SEOUL = new LatLng(37.56, 126.97); //위경도 좌표를 나타내는 클래스

        LatLng CLocation = new LatLng(infoAddress.getCurruntAddress().getLatitude(),infoAddress.getCurruntAddress().getLongitude()); //위경도 좌표를 나타내는 클래스
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(CLocation);
        //markerOptions.title("서울");
        //markerOptions.snippet("한국의 수도");
        mMap.addMarker(markerOptions);//마커를 맵 객체에 추가함

        mMap.moveCamera(CameraUpdateFactory.newLatLng(CLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }
}
