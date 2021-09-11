package com.teamide.idecomobility;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class BusInfoParsingData extends AsyncTask<Object, Object, ArrayList<String>> {

    ArrayList<String> searchBusData = new ArrayList<String>();
    private String serviceKey = "";
    ODsayService odsayService;
    String latitude;
    String longitude;

    BusInfoParsingData(Context context, String latitude, String longitude)
    {
        odsayService = ODsayService.init(context, serviceKey);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected ArrayList<String> doInBackground(Object... params) {

        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {//콜백리스너
            // 호출 성공 시 실행
            @Override
            public void onSuccess(ODsayData odsayData, API api) {
                try {
                    Integer count = odsayData.getJson().getJSONObject("result").getInt("count");
                    JSONArray station = odsayData.getJson().getJSONObject("result").getJSONArray("station");
                    String sname = station.getJSONObject(0).getString("stationName");
                    String id = station.getJSONObject(0).getString("arsID");
                    Double x = station.getJSONObject(0).getDouble("x");
                    Double y = station.getJSONObject(0).getDouble("y");
                    Log.d("ad","주변 정류장 갯수"+count.toString());
                    Log.d("ad","주변 정류장 전체 데이터"+station.toString());
                    Log.d("ad","첫번째 데이터"+sname+id+x+y);
                    Log.d("ad","두번째 데이터"+station.getJSONObject(1).getString("arsID"));
                    Log.d("ad","세번째 데이터"+station.getJSONObject(2).getString("arsID"));

                    for (int i = 0;i<count;i++)
                    {
                        searchBusData.add(station.getJSONObject(i).getString("stationName"));
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // 호출 실패 시 실행
            @Override
            public void onError(int i, String s, API api) {
                    Log.d("ad","오딧세이 Can't read data");
                    Log.d("ad","에러코드"+s);
            }
        };
        Log.d("ad","검색에 들어가는 좌표"+longitude+","+latitude);
        odsayService.requestPointSearch(longitude,latitude,"500","1",onResultCallbackListener);

        return searchBusData;
    }
}
