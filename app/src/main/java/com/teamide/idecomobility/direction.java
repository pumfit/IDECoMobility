package com.teamide.idecomobility;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.teamide.idecomobility.R;
import com.teamide.idecomobility.directionAdapter;
import com.teamide.idecomobility.direction_data;

import java.util.ArrayList;

public class direction extends AppCompatActivity {

    ArrayList<direction_data> movieDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_direction);

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
//toolbar의 back키 눌렀을 때 동작
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작 > id:home화면으로 넘어
//                finish();
//                return true;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public void InitializeMovieData() //data받아오
    {
        movieDataList = new ArrayList<direction_data>();

        movieDataList.add(new direction_data("출발", "출발지",null, null, null));
        movieDataList.add(new direction_data("버스", "서울여대 후문 정류소","362","3","분 뒤 도착"));
        movieDataList.add(new direction_data("도보", "도보로 420m 이동","태릉입구역 하차후 6번 출구 엘리베이터",null,null));
        movieDataList.add(new direction_data("지하철", "태릉입구역","4-1, 6-1, 8-1","2","분 뒤 도착"));
        movieDataList.add(new direction_data("도착", "도착지",null,null,null));
    }
}



