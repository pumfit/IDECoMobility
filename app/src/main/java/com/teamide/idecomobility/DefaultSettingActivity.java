package com.teamide.idecomobility;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DefaultSettingActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    public boolean isbTrue[] = new boolean[8];
    public Button addButton;
    public RadioGroup preferRadioButton;
    RecyclerView recyclerView;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;//for GPS Tracking
    InfoAddress infoAddress = new InfoAddress();//현위치,출발지,도착지 주소 정보
    private GpsTracker gpsTracker;

    public ArrayList<SearchAddress> savelist = new ArrayList<>();
    public ArrayList<String> fullNamelist,mainNamelist,lalist,lolist = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaultsetting);

        preferences = getSharedPreferences("info", MODE_PRIVATE);
        editor = preferences.edit();
        editor.putBoolean("inputbus", true);

        addButton = findViewById(R.id.addbutton);

        preferRadioButton = findViewById(R.id.PreferRadioGroup);

        if (!checkLocationServicesStatus()) {//GPS 확인
            Intent callGPSSettingIntent
                    = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
        } else {
            checkRunTimePermission();
        }

        gpsTracker = new GpsTracker(DefaultSettingActivity.this);//현위치 GPS로 받아오기

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        String address = getCurrentAddress(latitude, longitude);
        infoAddress.setCurruntAddress(new SearchAddress("현위치", address, "0", latitude, longitude));
        addButton.setOnClickListener(this);
        recyclerView = findViewById(R.id.recycleradressList);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addbutton:
                if (savelist.size() >= 3) {
                    Toast.makeText(getApplicationContext(), "최대 3개까지만 저장할 수 있습니다.", Toast.LENGTH_LONG).show();
                } else {
                    final AddAddressDialog dialog = new AddAddressDialog(this);
                    dialog.setDialogListener(new AddAddressDialog.CustomDialogListener() {
                        @Override
                        public void onPositiveClicked(SearchAddress saveAddress) {
                            savelist.add(saveAddress);

                            recyclerView = findViewById(R.id.recycleradressList);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                            AddAddressAdapter adapter = new AddAddressAdapter(savelist);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onNegativeClicked() {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
                break;
        }
    }


    public String getCurrentAddress(double latitude, double longitude) {

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
    }

    public boolean checkLocationServicesStatus() { //GPS기능 사용가능한지 판단
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    void checkRunTimePermission() {//RunTimePermissio 처리

        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(DefaultSettingActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(DefaultSettingActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
        } else {

            if (ActivityCompat.shouldShowRequestPermissionRationale(DefaultSettingActivity.this, REQUIRED_PERMISSIONS[0])) {
                Toast.makeText(DefaultSettingActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(DefaultSettingActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);//위치 정보 퍼미션 재요청

            } else {
                ActivityCompat.requestPermissions(DefaultSettingActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        editor.putBoolean("inputbus", false);
        editor.putBoolean("inputcalltax", false);
        editor.putBoolean("inputsubway", false);
        editor.putBoolean("inputonfoot", false);

        switch (view.getId()) {
            case R.id.radioButton3:
                if (checked) {
                    editor.putBoolean("inputbus", true);
                }
                break;
            case R.id.radioButton4:
                if (checked) {
                    editor.putBoolean("inputcalltax", true);
                }
                break;
            case R.id.radioButton5:
                if (checked) {
                    editor.putBoolean("inputsubway", true);
                }
                break;
            case R.id.radioButton6:
                if (checked) {
                    editor.putBoolean("inputonfoot", true);
                }
                break;
        }
        editor.commit();
    }

    public void OnCliked5(View v) {
        Button button = (Button) v;

        if (isbTrue[4] == false) {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputdata1", true); // key,value 형식으로 저장
            isbTrue[4] = true;
        } else {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputdata1", false); // key,value 형식으로 저장
            isbTrue[4] = false;
        }
        editor.commit();
    }

    public void OnCliked6(View v) {
        Button button = (Button) v;

        if (isbTrue[5] == false) {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputdata2", true); // key,value 형식으로 저장
            isbTrue[5] = true;
        } else {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputdata2", false); // key,value 형식으로 저장
            isbTrue[5] = false;
        }
        editor.commit();
    }

    public void OnCliked7(View v) {
        Button button = (Button) v;

        if (isbTrue[6] == false) {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputdata3", true); // key,value 형식으로 저장
            isbTrue[6] = true;
        } else {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputdata3", false); // key,value 형식으로 저장
            isbTrue[6] = false;
        }
        editor.commit();
    }

    public void OnCliked8(View v) {
        Button button = (Button) v;

        if (isbTrue[7] == false) {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputdata4", true); // key,value 형식으로 저장
            isbTrue[7] = true;
        } else {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputdata4", false); // key,value 형식으로 저장
            isbTrue[7] = false;
        }
        editor.commit();
    }

    public void OnSaved(View v) {
        for(int i=0;i<savelist.size();i++)
        {
            fullNamelist.add(savelist.get(i).getFullAdress());
            mainNamelist.add(savelist.get(i).getMainAdress());
            lolist.add(Double.toString(savelist.get(i).getLongitude()));
            lalist.add(Double.toString(savelist.get(i).getLatitude()));
        }
        setStringArrayPref(getApplicationContext(),"Addaddress1",fullNamelist);
        setStringArrayPref(getApplicationContext(),"Addaddress2",mainNamelist);
        setStringArrayPref(getApplicationContext(),"Addaddress3",lolist);
        setStringArrayPref(getApplicationContext(),"Addaddress4",lalist);

        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);
        finish();
    }

    private void setStringArrayPref(Context context, String key, ArrayList<String> values) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();
    }

    private ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
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
}
