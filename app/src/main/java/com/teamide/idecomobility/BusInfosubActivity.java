package com.teamide.idecomobility;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import java.util.ArrayList;

public class BusInfosubActivity extends AppCompatActivity {

    TextView titleText;
    String localStId, busNm, busMin;
    ArrayList<BusInfoSubData> busInfoDataList;
    public ODsayService oDsayService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businfosub);

        oDsayService = ODsayService.init(BusInfosubActivity.this, "nFVGyVxSTk6opjbmKKPCTDaEfNWyidhvs1HbmTtAf6U");

        titleText = findViewById(R.id.busTitleTextView);

        Intent in = getIntent();
        String busTitle = in.getExtras().getString("bustitle");
        String busStID = in.getExtras().getString("busStId");
        Log.d("ad","받은 버정 변환전 id: "+busStID);
        titleText.setText(busTitle);

        busChangeId(busStID);
        Log.d("ad","local변환ID : "+localStId);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.businfosubtoolbar);
        setSupportActionBar(toolbar);
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>정류소 세부정보 </font>"));
        actionBar.setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

//        String url = "http://ws.bus.go.kr/api/rest/arrive/getLowArrInfoByStId?ServiceKey=idAKQNTIDrnSK5vmheOsFszfGqNfoydTlN08JVMaLchmHaKDSY0lWkjMtjiSfDGSa%2FVm7mVWhVX7WXEfF7OGgA%3D%3D&stId="+busStID;
//        BusTime bustime = new BusTime(url);
//        bustime.execute();  //BusTime api 불러오기 // for문 돌려서 데이터 넣어서 리스트 띄우기
//        JSONParser jsonParser = new JSONParser();
//
//
//        JSONObject busjsonObject = null;
//        try {
//            busjsonObject = (JSONObject) jsonParser.parse(jsonData.jsonbus);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        JSONObject busInfoArray = (JSONObject) busjsonObject.get("inf");
//        busNm= (String) busInfoArray.get("rtNm");
//        String busSec = (String) busInfoArray.get("exps1_sec");
//        busMin = String.valueOf(Integer.parseInt(busSec)/60);

//        Toolbar toolbar = findViewById(R.id.businfosubtoolbar);
//        setActionBar(toolbar);
//        ActionBar actionBar = getActionBar();
//        actionBar.setTitle("정류소 세부정보");
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        this.InitializeMovieData();

        ListView listView = (ListView)findViewById(R.id.busInfoSublistView);
        final BusInfoSubAdapter mAdapter = new BusInfoSubAdapter(this,busInfoDataList);

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(),
                        mAdapter.getItem(position).toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    public void InitializeMovieData()
    {
        busInfoDataList = new ArrayList<BusInfoSubData>();

        busInfoDataList.add(new BusInfoSubData(busNm,busMin+"분 뒤 도착"));
        busInfoDataList.add(new BusInfoSubData("4318","분 뒤 도착"));
        busInfoDataList.add(new BusInfoSubData("1156","분 뒤 도착"));
    }

    public void busChangeId(String busStId){
        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData odsayData, API api) {
                try{
                    localStId = odsayData.getJson().getJSONObject("result").getString("localStationID");
                    Log.d("ad","local ID 함수내부: "+localStId);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(int i, String s, API api) {
                Log.d("ad","에러코드"+s);
            }
        };
        oDsayService.requestBusStationInfo(busStId, onResultCallbackListener);
    }
}
