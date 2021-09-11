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
    public String eLongitude = "127.060551";//기본 디폴트 값으로 이후 없애야함

    public String busName, busNm, busMin;
    public String subwayName, subwayMin;
    public String img;
    public String localID;
    public String test1, test2, test3, test4;
    String[] result;

    private BusTime busParser;
    //ArrayList<direction_data> resultDataList;
    ArrayList<DirectionData> trafficDataList = new ArrayList<>();
    //public ArrayList<String> addTrafficList = new ArrayList<>();
    //ArrayList<String> searchPathData = new ArrayList<String>();
    //ArrayList<String> busData = new ArrayList<String>();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeresult);

        Intent intent = getIntent();
        InfoAddressData infoAddressData = intent.getParcelableExtra("infoAddress");
        boolean[] services = intent.getBooleanArrayExtra("service");

        sLatitude = Double.toString(infoAddressData.getStartAddress().getLatitude());
        sLongitude = Double.toString(infoAddressData.getStartAddress().getLongitude());
        eLatitude = Double.toString(infoAddressData.getEndAddress().getLatitude());
        eLongitude = Double.toString(infoAddressData.getEndAddress().getLongitude());

        ODsayService odsayService = ODsayService.init(RouteResultActivity.this, "");

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
                        Integer trafficCount = path.getJSONObject(0).getJSONArray("subPath").length();
                        trafficDataList.add(0,new DirectionData(R.drawable.ic_flag,null,"출발",null,null,null));
                        for (int i=0; i<trafficCount; i++){
                            Integer trafficType =path.getJSONObject(0).getJSONArray("subPath").getJSONObject(i).getInt("trafficType");
                            if (trafficType==3){ //도보
                                Integer distance =path.getJSONObject(0).getJSONArray("subPath").getJSONObject(i).getInt("distance");
                                Integer secTime =path.getJSONObject(0).getJSONArray("subPath").getJSONObject(i).getInt("sectionTime");
                                //String array[]={trafficType.toString(),null,null,distance.toString()+"m 이동",secTime.toString()+"분 소요",null,"도보"};
                                trafficDataList.add(i+1,new DirectionData(R.drawable.ic_walk,"도보","도보로 "+distance+"m 이동",null,secTime.toString()+"분 소요  ",null));
                            }else if (trafficType==2){ //버스
                                String busstart = path.getJSONObject(0).getJSONArray("subPath").getJSONObject(i).getString("startName");
                                String busend = path.getJSONObject(0).getJSONArray("subPath").getJSONObject(i).getString("endName");
                                Integer secTime = path.getJSONObject(0).getJSONArray("subPath").getJSONObject(i).getInt("sectionTime");
                                String busNo = path.getJSONObject(0).getJSONArray("subPath").getJSONObject(i).getJSONArray("lane").getJSONObject(0).getString("busNo");
//                                String array[]={trafficType.toString(),busstart,busend,null,secTime.toString()+"분 소요",null,busNo};
//                                trafficDataList.add(array);
                                trafficDataList.add(i+1,new DirectionData(R.drawable.ic_bus_blue,busNo,busstart,busend+"  ",secTime.toString()+"분 소요  ",null));
                            }else if (trafficType==1){ //지하철
                                String subwaystart = path.getJSONObject(0).getJSONArray("subPath").getJSONObject(i).getString("startName");
                                String subwayend = path.getJSONObject(0).getJSONArray("subPath").getJSONObject(i).getString("endName");
                                Integer secTime = path.getJSONObject(0).getJSONArray("subPath").getJSONObject(i).getInt("sectionTime");
                                Integer subwayCount = path.getJSONObject(0).getJSONArray("subPath").getJSONObject(i).getInt("stationCount");
                                Integer subwayNo = path.getJSONObject(0).getJSONArray("subPath").getJSONObject(i).getJSONArray("lane").getJSONObject(0).getInt("subwayCode");

                                trafficDataList.add(i+1,new DirectionData(R.drawable.ic_train_blue,subwayNo.toString()+"호선",subwaystart+"역",subwayend+"역  ",secTime.toString()+"분 소요  ",subwayCount.toString()+"개역 이동"));
                            }
                        }
                        trafficDataList.add(trafficCount+1,new DirectionData(R.drawable.ic_flag,null,"도착",null,null,null));


                        final ListView listView = findViewById(R.id.listView);
                        final directionAdapter mAdapter = new directionAdapter(getApplicationContext(),trafficDataList);
                        listView.setAdapter(mAdapter);
//
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            // 호출 실패 시 실행
            @Override
            public void onError(int i, String s, API api) {
                if (api == API.SEARCH_PUB_TRANS_PATH) {
                    Log.d("ad", "오딧세이 Can't read data");
                    Log.d("ad", "에러코드" + s);
                }
            }
        };

//        String url = "http://ws.bus.go.kr/api/rest/arrive/getLowArrInfoByStId?ServiceKey=idAKQNTIDrnSK5vmheOsFszfGqNfoydTlN08JVMaLchmHaKDSY0lWkjMtjiSfDGSa%2FVm7mVWhVX7WXEfF7OGgA%3D%3D&stId=112000001";
//        Log.d("ad","버스 데이터 호출 전 ");
//        BusTime bustime = new BusTime();
//        bustime.execute(url);
        //busName=result[3];
        //busMin=result[2];
        //busNm=result[1];


//        JSONParser jsonParser = new JSONParser();
//
//        JSONObject busjsonObject = null;
//        try {
//            busjsonObject = (JSONObject) jsonParser.parse(jsonData.jsonbus);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        JSONObject busInfoArray = (JSONObject) busjsonObject.get("inf");
//        busName = (String) busInfoArray.get("stNm");
//        busNm = (String) busInfoArray.get("rtNm");
//        String busSec = (String) busInfoArray.get("exps1_sec");
//        busMin = String.valueOf(Integer.parseInt(busSec) / 60);

//        JSONObject subwayjsonObject = null;
//        try {
//            subwayjsonObject = (JSONObject) jsonParser.parse(jsonData.jsonsubway);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        JSONObject subwayInfoArray = (JSONObject) subwayjsonObject.get("inf");
//        subwayName = (String) subwayInfoArray.get("statnNm");
//        String subwaySec = (String) subwayInfoArray.get("barvlDt_sec");
//        subwayMin = String.valueOf(Integer.parseInt(subwaySec) / 60);
//
//        JSONObject imgjsonObject = null;
//        try {
//            imgjsonObject = (JSONObject) jsonParser.parse(jsonData.jsonimg);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        JSONObject ImgInfoArray = (JSONObject) imgjsonObject.get("inf");
//        img = (String) ImgInfoArray.get("storedName");

        //API 파라미터 설정
        odsayService.requestSearchPubTransPath(sLongitude, sLatitude, eLongitude, eLatitude, "0", "0", "0", onResultCallbackListener);
        //odsayService.requestBusStationInfo(busName,onResultCallbackListener);

        TextView startTextView = findViewById(R.id.start);
        TextView endTextView = findViewById(R.id.end);

        startTextView.setText(infoAddressData.getStartAddress().getMainAdress());
        endTextView.setText(infoAddressData.getEndAddress().getMainAdress());

        ListView listView = (ListView) findViewById(R.id.listView);
        final directionAdapter myAdapter = new directionAdapter(this, trafficDataList);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        myAdapter.getItem(position).getstartTraffic(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callBackData(String[] result) {
        test1 = result[0];
        test2 = result[1];
        test3 = result[2];
        test4 = result[3];
        Log.d("ad", "결과1 : " + test1);
        Log.d("ad", "결과2 : " + test2);
        Log.d("ad", "결과3 : " + test3);
        Log.d("ad", "결과4 : " + test4);
    }
}
