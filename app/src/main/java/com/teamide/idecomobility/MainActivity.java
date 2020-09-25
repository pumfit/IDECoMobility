package com.teamide.idecomobility;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private GpsTracker gpsTracker;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;//for GPS Tracking

    InfoAddress infoAddress = new InfoAddress();//현위치,출발지,도착지 주소 정보
    boolean[] isServiceNeed = new boolean[4];//사용자 설정 boolean 휠체어 리프트,엘리베이터,저상버스,무장애 정류소

    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    RecyclerView selectedRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedRecyclerView = findViewById(R.id.selectedlist);
        selectedRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        EditText startText = (EditText) findViewById(R.id.addressSearchEditText1);
        EditText endText = (EditText) findViewById(R.id.addressSearchEditText2);
        startText.setInputType(0);
        endText.setInputType(0);

        preferences = getSharedPreferences("info", MODE_PRIVATE);    // test 이름의 기본모드 설정
        editor= preferences.edit(); //sharedPreferences를 제어할 editor를 선언

        ArrayList<String> savelist = new ArrayList<String>();
        if(preferences.getBoolean("inputdata1",false))
        {
            savelist.add("휠체어칸");
        }
        if(preferences.getBoolean("inputdata2",false))
        {
            savelist.add("휠체어 리프트");
        }
        if(preferences.getBoolean("inputdata3",false))
        {
            savelist.add("엘레베이터");
        }
        if(preferences.getBoolean("inputdata4",false))
        {
            savelist.add("저상버스");
        }
        SelectedServiceAdapter adapter = new SelectedServiceAdapter(savelist);
        selectedRecyclerView.setAdapter(adapter);

        checkFirstRun();

        if (!checkLocationServicesStatus()) {//GPS 확인
            Intent callGPSSettingIntent
                    = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
        } else {

            checkRunTimePermission();
        }

        gpsTracker = new GpsTracker(MainActivity.this);//현위치 GPS로 받아오기

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        String address = getCurrentAddress(latitude, longitude);
        infoAddress.setStartAddress(new SearchAddress("현위치", address, "0", latitude, longitude));
        infoAddress.setCurruntAddress(new SearchAddress("현위치", address, "0", latitude, longitude));
        startText.setText(infoAddress.getCurruntAddress().getFullAdress());//현위치를 출발지로 지정

        startText.setOnClickListener( //출발지 EditText버튼 클릭시
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivityForResult(intent, 100);

                    }
                }
        );
        endText.setOnClickListener( //도착지 EditText버튼 클릭시
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SearchActivity2.class);
                        startActivityForResult(intent, 101);
                    }
                }
        );
    }


    public boolean checkLocationServicesStatus() { //GPS기능 사용가능한지 판단
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    void checkRunTimePermission() {//RunTimePermissio 처리

        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
        } else {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);//위치 정보 퍼미션 재요청

            } else {
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    @Override//받아온 intent 정보 처리
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) { //from SearchActivity
            if (resultCode == RESULT_OK) {
                SearchAddress startAllAdress = data.getParcelableExtra("startAllAddress");
                infoAddress.setStartAddress(startAllAdress);
                EditText startText = (EditText) findViewById(R.id.addressSearchEditText1);
                startText.setText(infoAddress.getStartAddress().getFullAdress());
            }
        } else if (requestCode == 101) {
            if (resultCode == RESULT_OK) { //from SearchActivity2
                SearchAddress endAllAdress = data.getParcelableExtra("endAllAddress");
                infoAddress.setEndAddress(endAllAdress);
                EditText endText = (EditText) findViewById(R.id.addressSearchEditText2);
                endText.setText(infoAddress.getEndAddress().getFullAdress());
            }
        }
    }

    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    5);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "GPS 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "GPS 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";
        //참고 https://webnautes.tistory.com/1315
    }

    public void onClickedSearch(View v) { //길찾기 버튼 클릭시 받아온 주소 정보들 보냄
        Intent in = new Intent(getApplicationContext(), RouteResultActivity.class);
        in.putExtra("infoAddress", (Parcelable) infoAddress);
        in.putExtra("service", isServiceNeed);
        startActivity(in);
    }

    public void onClickedBusInfo(View v){ //버스버튼
        Intent in = new Intent(getApplicationContext(), BusInfoActivity.class);
        in.putExtra("infoAddress", (Parcelable) infoAddress);
        startActivity(in);
    }

    public void onClickedSettingInfo(View v){ //설정버튼
        Intent in = new Intent(getApplicationContext(), SettingInfoActivity.class); //현재 위치가져가면된다.
   //     in.putExtra("infoAddress", (Parcelable) infoAddress);
        startActivity(in);
    }
    public void onClickedBookMark(View v){
        final AddBookMarkDialog dialog = new AddBookMarkDialog(this);
        dialog.show();
    }
    public void onClickedCallTexi(View v){
        final CalltexiDialog dialog = new CalltexiDialog(this);
        dialog.show();
    }

    public void checkFirstRun() {
        boolean isFirstRun = preferences.getBoolean("firstrun",true);
        if (isFirstRun) {
            Intent newIntent = new Intent(this, DefaultSettingActivity.class);
            startActivity(newIntent);
            editor.putBoolean("firstrun",false);
            editor.commit();
        }

    }

}
