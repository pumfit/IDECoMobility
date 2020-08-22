package com.teamide.idecomobility;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.graphics.drawable.Drawable.*;


public class MainActivity extends AppCompatActivity {

    private GpsTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    //GPS
    InfoAddress infoAddress = new InfoAddress();//받아올 정보 class객체
    boolean[] isServiceNeed = new  boolean[4];//순서대로 휠체어 리프트,엘리베이터,저상버스,무장애 정류소

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText startText = (EditText) findViewById(R.id.addressSearchEditText1);
        EditText endText = (EditText) findViewById(R.id.addressSearchEditText2);
        startText.setInputType(0);
        endText.setInputType(0);

        if (!checkLocationServicesStatus()) {
            Intent callGPSSettingIntent
                    = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
        }else {

            checkRunTimePermission();
        }

        gpsTracker = new GpsTracker(MainActivity.this);

        double latitude = gpsTracker.getLatitude();//현위치 위도 경도 좌표 여기서
        double longitude = gpsTracker.getLongitude();

        String address = getCurrentAddress(latitude, longitude);
        infoAddress.setStartAddress(new SearchAddress("currentAddress",address,"0",latitude,longitude));
        infoAddress.setCurruntAddress( new SearchAddress("currentAddress",address,"0",latitude,longitude));
        startText.setText(infoAddress.getCurruntAddress().getFullAdress());//현위치를 출발지로 지정

        startText.setOnClickListener(//에디트텍스트버튼클릭시
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                        startActivityForResult(intent,100);

                    }
                }
        );

        endText.setOnClickListener(//에디트텍스트버튼클릭시
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(),SearchActivity2.class);
                        startActivityForResult(intent,101);
                    }
                }
        );

//        Intent intent = getIntent();
//        if(!(intent.getStringExtra("startAddress")==null))
//        {
//            infoAddress.setStartAddress(intent.getStringExtra("startAddress"));
//            //startText.setText(startAddress);
//        }
//        else if(!(intent.getStringExtra("endAddress")==null))
//        {
//            infoAddress.setEndAddress(intent.getStringExtra("endAddress"));
//            //endText.setText(endAddress);
//        }
//
//        if(infoAddress.getStartAddress()==null)
//        {
//            startText.setText(infoAddress.getCurruntAddress());
//            endText.setText(infoAddress.getEndAddress());
//        }
//        else
//        {
//            startText.setText(infoAddress.getStartAddress());
//            endText.setText(infoAddress.getEndAddress());
//        }

    }


    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            // 3.  위치 값을 가져올 수 있음

        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {
                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==100)
            {
                if(resultCode==RESULT_OK)
                {
                    SearchAddress startAllAdress = data.getParcelableExtra("startAllAddress");
                    infoAddress.setStartAddress(startAllAdress);
                    EditText startText = (EditText) findViewById(R.id.addressSearchEditText1);
                    startText.setText(infoAddress.getStartAddress().getFullAdress());
                }
            }
            else if(requestCode==101)
            {
                if(resultCode==RESULT_OK)
                {
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
        return address.getAddressLine(0).toString()+"\n";
//https://webnautes.tistory.com/1315
    }

    public void onClickedSearch(View v) { //정보 보냄

        Intent in = new Intent(getApplicationContext(),RouteResultActivity.class);
        in.putExtra("infoAddress", (Parcelable) infoAddress);
        in.putExtra("service",isServiceNeed);
        startActivity(in);
    }
    //사용자 설정 버튼
    public void onClickedwheelchairlift(View v)
    {
        if(isServiceNeed[0]==false)
        {
            isServiceNeed[0] = true;
            Button setWheelchairlift = findViewById(R.id.button5);
            setWheelchairlift.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            setWheelchairlift.setTextColor(ContextCompat.getColor(this,R.color.white));
        }
        else
        {
            isServiceNeed[0] = false;
            Button setWheelchairlift = findViewById(R.id.button5);
            setWheelchairlift.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            setWheelchairlift.setTextColor(ContextCompat.getColor(this,R.color.black));
        }

    }

    public void onClickedElevator(View v)
    {
        if(isServiceNeed[1]==false)
        {
            isServiceNeed[1] = true;
            Button setWheelchairlift = findViewById(R.id.button6);
            setWheelchairlift.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            setWheelchairlift.setTextColor(ContextCompat.getColor(this,R.color.white));
        }
        else
        {
            isServiceNeed[1] = false;
            Button setWheelchairlift = findViewById(R.id.button6);
            setWheelchairlift.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            setWheelchairlift.setTextColor(ContextCompat.getColor(this,R.color.black));
        }

    }

    public void onClickedBus(View v)
    {
        if(isServiceNeed[2]==false)
        {
            isServiceNeed[2] = true;
            Button setWheelchairlift = findViewById(R.id.button2);
            setWheelchairlift.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            setWheelchairlift.setTextColor(ContextCompat.getColor(this,R.color.white));
        }
        else
        {
            isServiceNeed[2] = false;
            Button setWheelchairlift = findViewById(R.id.button2);
            setWheelchairlift.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            setWheelchairlift.setTextColor(ContextCompat.getColor(this,R.color.black));
        }

    }

    public void onClickedBusStation(View v)
    {
        if(isServiceNeed[3]==false)
        {
            isServiceNeed[3] = true;
            Button setWheelchairlift = findViewById(R.id.button3);
            setWheelchairlift.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            setWheelchairlift.setTextColor(ContextCompat.getColor(this,R.color.white));
        }
        else
        {
            isServiceNeed[3] = false;
            Button setWheelchairlift = findViewById(R.id.button3);
            setWheelchairlift.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            setWheelchairlift.setTextColor(ContextCompat.getColor(this,R.color.black));
        }

    }

}
