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

public class OdsayParsingData extends AsyncTask<Object, Object, ArrayList<String>> {

    ArrayList<String> searchPathData = new ArrayList<String>();
    ODsayService odsayService;
    String sLatitude;
    String sLongitude;
    String eLatitude;
    String eLongitude;

    OdsayParsingData(Context context,String sLatitude,String sLongitude,String eLatitude,String eLongitude)
    {
        odsayService = ODsayService.init(context, "nFVGyVxSTk6opjbmKKPCTDaEfNWyidhvs1HbmTtAf6U");
        this.sLatitude = sLatitude;
        this.sLongitude = sLongitude;
        this.eLatitude = eLatitude;
        this.eLongitude = eLongitude;
    }

    @Override
    protected ArrayList<String> doInBackground(Object... params) {

        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {//콜백리스너
            // 호출 성공 시 실행
            @Override
            public void onSuccess(ODsayData odsayData, API api) {
                try {
                    // API Value 는 API 호출 메소드 명을 따라갑니다.
                    if (api == API.SEARCH_PUB_TRANS_PATH) {//여기가 오디세이 데이터 처리하는 함수
                        String searchType = odsayData.getJson().getJSONObject("result").getString("searchType");
                        JSONArray path =  odsayData.getJson().getJSONObject("result").getJSONArray("path");
                        String totalTime = path.getJSONObject(0).getJSONObject("info").getString("totalTime");
                        String payment = path.getJSONObject(0).getJSONObject("info").getString("payment");
                        String stationId = path.getJSONObject(0).getJSONArray("subPath").getJSONObject(1).getJSONObject("passStopList").getJSONArray("stations").getJSONObject(0).toString();
                        Log.d("ad","오딧세이 호출 searchType :"+ searchType+"총 걸리는 시간:"+totalTime+"총 요금:"+payment+"정류장ID"+stationId);
                        searchPathData.add(searchType);
                        searchPathData.add(totalTime);
                        searchPathData.add(payment);
                        searchPathData.add(stationId);

                        Log.d("ad",searchPathData.toString()+"11111111");

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // 호출 실패 시 실행
            @Override
            public void onError(int i, String s, API api) {
                if (api == API.SEARCH_PUB_TRANS_PATH) {
                    Log.d("ad","오딧세이 Can't read data");
                    Log.d("ad","에러코드"+s);
                }
            }
        };
        odsayService.requestSearchPubTransPath(sLongitude,sLatitude,eLongitude,eLatitude,"0","0","0",onResultCallbackListener);

        return searchPathData;
    }
}
