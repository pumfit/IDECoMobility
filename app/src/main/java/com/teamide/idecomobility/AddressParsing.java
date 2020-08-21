package com.teamide.idecomobility;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddressParsing extends AsyncTask<Object, Object, ArrayList<SearchAddress>> {

    private Geocoder geocoder = null;
    private String searchaddress;

    public AddressParsing(String searchaddress) {
        this.searchaddress = searchaddress;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // 값을 가져오기 전에 하고 싶은 일 (프로그레스 바를 띄운다던가 등등)
    }

    @Override
    protected ArrayList<SearchAddress> doInBackground(Object... params) {
        List<Address> addressList = null;
        ArrayList<SearchAddress> dataList = new ArrayList<SearchAddress>();

        try
        {
            addressList = geocoder.getFromLocationName(searchaddress, 10); // 최대 검색 결과 개수1
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if(!(addressList==null))
            {
                // 콤마를 기준으로 split
                for (int i = 0; i < addressList.size(); i++)
                {
                    String[] splitStr = addressList.get(i).toString().split(",");
                    String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2); // 주소
                    Log.d("ad", address);
                    String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                    String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도

                    Log.d("ad", latitude);
                    Log.d("ad", longitude);

                    dataList.add(new SearchAddress(searchaddress, address, "10km"));
                }
            }else
            {
                dataList.add(new SearchAddress("검색결과없음", "알수없음", "10km"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    @Override
    protected void onPostExecute(ArrayList<SearchAddress> SearchAddresses )
    {
        super.onPostExecute(SearchAddresses);
        // 값을 가져오고 나서 하는 일 (프로그레스바를 종료한다던가 등)
    }

}
