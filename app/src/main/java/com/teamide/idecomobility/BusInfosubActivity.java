package com.teamide.idecomobility;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BusInfosubActivity extends AppCompatActivity {

    TextView titleText;
    ArrayList<BusInfoSubData> busInfoDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businfosub);

        titleText = findViewById(R.id.busTitleTextView);

        Intent in = getIntent();
        String busTitle = in.getExtras().getString("bustitle");
        titleText.setText(busTitle);

        Toolbar toolbar = findViewById(R.id.businfosubtoolbar);
        setActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("정류소 세부정보");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

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

        busInfoDataList.add(new BusInfoSubData("362","3분 뒤 도착"));
        busInfoDataList.add(new BusInfoSubData("4318","8분 뒤 도착"));
        busInfoDataList.add(new BusInfoSubData("1156","11분 뒤 도착"));
    }
}
