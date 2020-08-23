package com.teamide.idecomobility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class RouteResultActivity extends Activity {

    public String sLatitude = "37.6281126";
    public String sLongitude = "127.09045680000001";
    public String eLatitude = "37.654527";
    public String eLongitude = "127.060551";


    ArrayList<direction_data> movieDataList;
    ArrayList<String> searchPathData = new ArrayList<String>();
    ArrayList<String> busData = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeresult);

        Intent intent = getIntent();
        InfoAddress infoAddress = intent.getParcelableExtra("infoAddress");
        boolean[] services = intent.getBooleanArrayExtra("service");

        sLatitude = Double.toString(infoAddress.getStartAddress().getLatitude());
        sLongitude = Double.toString(infoAddress.getStartAddress().getLongitude());
        eLatitude = Double.toString(infoAddress.getEndAddress().getLatitude());
        eLongitude = Double.toString(infoAddress.getEndAddress().getLongitude());


        ODsayService odsayService = ODsayService.init(RouteResultActivity.this, "nFVGyVxSTk6opjbmKKPCTDaEfNWyidhvs1HbmTtAf6U");

        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
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

                        new Thread()
                        {
                            public void run()
                            {
                                if(Integer.parseInt(searchPathData.get(3))>10000)
                                {
                                    BusTime busTime = new BusTime(searchPathData.get(3));
                                    busData = busTime.getData();
                                }
                                else
                                {
                                    Log.d("ad","버스 정류장이 아닙니다.");
                                }
                            }

                        }.start();

                        Log.d("ad",searchPathData.toString());
                        Log.d("ad",busData.toString());

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


        TextView startTextView = findViewById(R.id.start);
        TextView endTextView = findViewById(R.id.end);

        startTextView.setText(infoAddress.getStartAddress().getMainAdress());
        endTextView.setText(infoAddress.getEndAddress().getMainAdress());

        this.InitializeMovieData();

        ListView listView = (ListView)findViewById(R.id.listView);
        final directionAdapter myAdapter = new directionAdapter(this,movieDataList);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(),
                        myAdapter.getItem(position).gettitle(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    public void InitializeMovieData() //data받아오기
    {
        movieDataList = new ArrayList<direction_data>();
        //이부분을 case문으로 처리
        movieDataList.add(new direction_data("출발", "출발지",null, null, null));
        movieDataList.add(new direction_data("버스", "서울여대 후문 정류소","362","3","분 뒤 도착"));
        movieDataList.add(new direction_data("도보", "도보로 420m 이동","태릉입구역 하차후 6번 출구 엘리베이터",null,null));
        movieDataList.add(new direction_data("지하철", "태릉입구역","4-1, 6-1, 8-1","2","분 뒤 도착"));
        movieDataList.add(new direction_data("도착", "도착지",null,null,null));
    }
}
