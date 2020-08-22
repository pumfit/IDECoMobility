package com.teamide.idecomobility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;

public class RouteResultActivity extends Activity {

    public String sLatitude = "37.6281126";
    public String sLongitude = "127.09045680000001";
    public String eLatitude = "37.654527";
    public String eLongitude = "127.060551";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeresult);

        TextView textView = findViewById(R.id.textView6);
        Intent intent = getIntent();
        InfoAddress infoAddress = intent.getParcelableExtra("infoAddress");
        boolean[] services = intent.getBooleanArrayExtra("service");

        sLatitude = Double.toString(infoAddress.getStartAddress().getLatitude());
        sLongitude = Double.toString(infoAddress.getStartAddress().getLongitude());
        eLatitude = Double.toString(infoAddress.getEndAddress().getLatitude());
        eLongitude = Double.toString(infoAddress.getEndAddress().getLongitude());
        Log.d("ad",sLatitude+"    "+sLongitude+"   "+eLatitude+"  "+eLongitude);

        searchPath(infoAddress.getStartAddress(),infoAddress.getEndAddress());

        StringBuffer sb = new StringBuffer();
        sb.append(infoAddress.getCurruntAddress().getMainAdress());
        sb.append(services[0]);
        sb.append(services[1]);

       textView.setText(sb);

    }

    public void searchPath(SearchAddress startAddress,SearchAddress endAddress)
    {
        ODsayService odsayService = ODsayService.init(this, "nFVGyVxSTk6opjbmKKPCTDaEfNWyidhvs1HbmTtAf6U");
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);

        // 콜백 함수 구현
        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
            // 호출 성공 시 실행
            @Override
            public void onSuccess(ODsayData odsayData, API api) {
                try {
                    // API Value 는 API 호출 메소드 명을 따라갑니다.
                    if (api == API.SEARCH_PUB_TRANS_PATH) {
                        String searchType = odsayData.getJson().getJSONObject("result").getString("searchType");
                        JSONArray path =  odsayData.getJson().getJSONObject("result").getJSONArray("path");
                        String totalTime = path.getJSONObject(0).getJSONObject("info").getString("totalTime");
                        String payment = path.getJSONObject(0).getJSONObject("info").getString("payment");
                        Log.d("ad","오딧세이 호출 searchType :"+ searchType+"총 걸리는 시간:"+totalTime+"총 요금:"+payment);
                        TextView textView = findViewById(R.id.textView6);
                        textView.append("총 걸리는 시간:"+totalTime+"총 요금:"+payment);
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

    }

}
