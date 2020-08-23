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
    public String busStationNm = "";
    public String busTm = "";
    public String subwayId = "";

    public String localId = "";
    public String startId ="";
    public String sLatitude = "37.6281126";
    public String sLongitude = "127.09045680000001";
    public String eLatitude = "37.654527";
    public String eLongitude = "127.060551";

    ArrayList<direction_data> movieDataList;
    //AIzaSyDX8dROz3L3e1jMQ7tUE1CC094guImPlo8"

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
        Log.d("ad",sLatitude+"    "+sLongitude+"   "+eLatitude+"  "+eLongitude);

        searchPath(infoAddress.getStartAddress(),infoAddress.getEndAddress());//길찾기 실행
        searchBusID(startId);

        BusTime bustm = new BusTime(localId);
        bustm.getData();

        busStationNm = (bustm.getData()).get(0);
        busTm = (bustm.getData()).get(1);

        Log.d("ad",busStationNm);
        Log.d("ad",busTm);

        TextView startTextView = findViewById(R.id.start);
        TextView endTextView = findViewById(R.id.end);

        startTextView.setText(infoAddress.getStartAddress().getMainAdress());
        endTextView.setText(infoAddress.getEndAddress().getMainAdress());

        this.InitializeMovieData(busStationNm,busTm,subwayId);//->72

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

    public void InitializeMovieData(String a, String b,String c) //data받아오기
    {
        movieDataList = new ArrayList<direction_data>();
        //이부분을 case문으로 처리
        movieDataList.add(new direction_data("출발", "출발지",null, null, null));
        movieDataList.add(new direction_data("버스", a,"362",b,"분 뒤 도착"));
        movieDataList.add(new direction_data("도보", "도보로 420m 이동","태릉입구역 하차후 6번 출구 엘리베이터",null,null));
        movieDataList.add(new direction_data("지하철", c+"역","4-1, 6-1, 8-1","3","분 뒤 도착"));
        movieDataList.add(new direction_data("도착", "도착지",null,null,null));
    }
    public void searchPath(SearchAddress startAddress,SearchAddress endAddress)//오딧세이 경로 찾기 함수
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
                    if (api == API.SEARCH_PUB_TRANS_PATH) {//여기가 오디세이 데이터 처리하는 함수
                        String searchType = odsayData.getJson().getJSONObject("result").getString("searchType");
                        JSONArray path = odsayData.getJson().getJSONObject("result").getJSONArray("path");
                        String totalTime = path.getJSONObject(0).getJSONObject("info").getString("totalTime");
                        String payment = path.getJSONObject(0).getJSONObject("info").getString("payment");
                        Integer busstId = path.getJSONObject(0).getJSONObject("subPath").getInt("startID");
                        startId = Integer.toString(busstId);
                        subwayId = path.getJSONObject(0).getJSONObject("subPath").getJSONObject("passStopList").getJSONObject("stations").getString("stationID");

                        Log.d("ad", "오딧세이 호출 searchType :" + searchType + "총 걸리는 시간:" + totalTime + "총 요금:" + payment);
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // 호출 실패 시 실행
            @Override
            public void onError(int i, String s, API api) {
                if (api == API.SEARCH_PUB_TRANS_PATH) {
                    Log.d("ad","오딧세이1 Can't read data");
                    Log.d("ad","에러코드"+s);
                }
            }
        };
        odsayService.requestSearchPubTransPath(sLongitude,sLatitude,eLongitude,eLatitude,"0","0","0",onResultCallbackListener);
    }
    public void searchBusID(String busid)//오딧세이 경로 찾기 함수
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
                    if (api == API.BUS_STATION_INFO) {//여기가 오디세이 데이터 처리하는 함수
                        Integer lcid = odsayData.getJson().getJSONObject("result").getInt("localStationID");
                        localId = Integer.toString(lcid);
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // 호출 실패 시 실행
            @Override
            public void onError(int i, String s, API api) {
                if (api == API.BUS_STATION_INFO) {
                    Log.d("ad","오딧세이2 Can't read data");
                    Log.d("ad","에러코드"+s);
                }
            }
        };
        odsayService.requestSearchPubTransPath(sLongitude,sLatitude,eLongitude,eLatitude,"0","0","0",onResultCallbackListener);
    }
}
