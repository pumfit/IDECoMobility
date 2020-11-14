package com.teamide.idecomobility;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

public class BusArrivalParsingData1 extends AsyncTask<String, Void, String> {

    public String busStId, localStId;
    public ODsayService odsayService;
    private Context context;

    public BusArrivalParsingData1(Context context, String busStId){
        odsayService = ODsayService.init(context, "nFVGyVxSTk6opjbmKKPCTDaEfNWyidhvs1HbmTtAf6U");
        this.busStId = busStId;
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d("ad","ss1"+localStId);

        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData odsayData, API api) {
                try{
                    localStId = odsayData.getJson().getJSONObject("result").getString("localStationID");
                    Log.d("ad","s"+localStId);

                }catch (Exception e){
                    e.printStackTrace();
                }
                Log.d("ad","ss2"+localStId);
            }
            @Override
            public void onError(int i, String s, API api) {
                Log.d("ad","에러코드"+s);
            }
        };
        odsayService.requestBusStationInfo(busStId, onResultCallbackListener);

        return localStId;
    }
    @Override
    protected void onPreExecute(){

    }
}
