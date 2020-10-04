package com.teamide.idecomobility;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddAddressDialog extends Dialog implements View.OnClickListener {
    private Button mPositiveButton;
    private Button mNegativeButton;
    private EditText editText;
    private ListView listView;
    private SearchView searchView;
    private TextView textView;
    private Context context;
    public SearchAddress saveAddress;

    private androidx.appcompat.widget.Toolbar toolbar;
    private CustomDialogListener customDialogListener;

    public ArrayList<SearchAddress> dataList;

    public AddAddressDialog(Context context) {
        super(context);
        this.context = context;
    }
    interface CustomDialogListener{
        void onPositiveClicked(SearchAddress saveAddress);
        void onNegativeClicked();
    }

    public void setDialogListener(CustomDialogListener customDialogListener){
        this.customDialogListener = customDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_address_dialog);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("장소 등록");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.addresslistView);
        textView = findViewById(R.id.textView10);
        textView.setVisibility(View.GONE);
        searchView = findViewById(R.id.addressSearchView);
        mPositiveButton = findViewById(R.id.sbutton);
        mNegativeButton = findViewById(R.id.nbutton);

        mPositiveButton.setOnClickListener(this);
        mNegativeButton.setOnClickListener(this);

        dataList = new ArrayList<SearchAddress>();

        GpsTracker gpsTracker = new GpsTracker(getContext());//현위치 GPS로 받아오기

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();
        ArrayList<String> list = new ArrayList<>();
        String address = getCurrentAddress(latitude, longitude);
        list.add(address);

        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_single_choice, list);
        listView.setChoiceMode(1);
        dataList.add(new SearchAddress("",address,"0km",latitude,longitude));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                saveAddress = dataList.get(position);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                SearchAddress(s);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sbutton: //저장 버튼을 눌렀을 때
                String name = editText.getText().toString();
                if(saveAddress==null)
                {
                    Toast.makeText(v.getContext(), "주소를 선택해주세요.", Toast.LENGTH_LONG).show();
                }
                else if(name.length()==0)
                {
                    Toast.makeText(v.getContext(), "장소 이름을 입력해주세요.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    saveAddress.setMainAdress(name);
                    customDialogListener.onPositiveClicked(saveAddress);
                    dismiss();
                }
                break;
            case R.id.nbutton: //취소 버튼을 눌렀을 때
                cancel();
                break;
        }
    }

    public void SearchAddress(String s)
    {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        ArrayList<String> list = new ArrayList<>();
        List<Address> addressList = null;
        dataList.clear();
        try {
            addressList = geocoder.getFromLocationName(s, 5); //역 Geocoding으로 최대 검색 결과 개수 5개까지의 주소를 불러옴
            Log.d("ad", "검색 결과 개수" + String.valueOf(addressList.size()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            if (addressList.size() == 0)//검색결과가 없는 경우 알람 image를 띄움
            {
                Log.d("ad", "검색결과없음");
                list.add("결과없음");
            } else {
                for (int i = 0; i < addressList.size(); i++) {
                    Address ad = addressList.get(i);
                    String address = ad.getAddressLine(0).toString() + "\n";
                    Location location = new Location("seaech location");
                    location.setLatitude(ad.getLatitude());
                    location.setLongitude(ad.getLongitude());
                    dataList.add(new SearchAddress(s, address, 0 + "Km", location.getLatitude(), location.getLongitude()));
                    list.add(address);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayAdapter adapter;
        if(list.get(0).equals("결과없음"))
        {
            adapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1, list);
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
        else
        {
            adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_single_choice, list);
        }
        listView.setAdapter(adapter);
    }

    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    5);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(getContext(), "GPS 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "GPS 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(getContext(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(getContext(), "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";

    }

}
