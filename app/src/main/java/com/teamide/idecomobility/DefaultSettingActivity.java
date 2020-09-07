package com.teamide.idecomobility;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class DefaultSettingActivity extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    public boolean isbTrue[] = new boolean[8];

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaultsetting);

        preferences = getSharedPreferences("info", MODE_PRIVATE);
        editor = preferences.edit();

    }

    public void OnClikedSearch(View v)
    {

    }


    public void OnCliked1(View v)
    {
        Button button = (Button) v;

        if(isbTrue[0]==false)
        {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputbus",true); // key,value 형식으로 저장
            isbTrue[0] = true;
        }
        else
        {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputbus",false); // key,value 형식으로 저장
            isbTrue[0] =false;
        }
        editor.commit();
    }

    public void OnCliked2(View v)
    {
        Button button = (Button) v;

        if(isbTrue[1]==false)
        {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputcalltax",true); // key,value 형식으로 저장
            isbTrue[1] = true;
        }
        else
        {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputcalltax",false); // key,value 형식으로 저장
            isbTrue[1] =false;
        }
        editor.commit();
    }

    public void OnCliked3(View v)
    {
        Button button = (Button) v;

        if(isbTrue[2]==false)
        {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputsubway",true); // key,value 형식으로 저장
            isbTrue[2] = true;
        }
        else
        {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputsubway",false); // key,value 형식으로 저장
            isbTrue[2] =false;
        }
        editor.commit();
    }

    public void OnCliked4(View v)
    {
        Button button = (Button) v;

        if(isbTrue[3]==false)
        {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputonfoot",true); // key,value 형식으로 저장
            isbTrue[3] = true;
        }
        else
        {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputonfoot",false); // key,value 형식으로 저장
            isbTrue[3] =false;
        }
        editor.commit();
    }
    public void OnCliked5(View v)
    {
        Button button = (Button) v;

        if(isbTrue[4]==false)
        {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputdata1",true); // key,value 형식으로 저장
            isbTrue[4] = true;
        }
        else
        {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputdata1",false); // key,value 형식으로 저장
            isbTrue[4] =false;
        }
        editor.commit();
    }
    public void OnCliked6(View v)
    {
        Button button = (Button) v;

        if(isbTrue[5]==false)
        {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputdata2",true); // key,value 형식으로 저장
            isbTrue[5] = true;
        }
        else
        {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputdata2",false); // key,value 형식으로 저장
            isbTrue[5] =false;
        }
        editor.commit();
    }
    public void OnCliked7(View v)
    {
        Button button = (Button) v;

        if(isbTrue[6]==false)
        {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputdata3",true); // key,value 형식으로 저장
            isbTrue[6] = true;
        }
        else
        {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputdata3",false); // key,value 형식으로 저장
            isbTrue[6] =false;
        }
        editor.commit();
    }
    public void OnCliked8(View v)
    {
        Button button = (Button) v;

        if(isbTrue[7]==false)
        {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputdata4",true); // key,value 형식으로 저장
            isbTrue[7] = true;
        }
        else
        {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputdata4",false); // key,value 형식으로 저장
            isbTrue[7] =false;
        }
        editor.commit();
    }
    public void OnSaved(View v)
    {
        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);
        //인텐트 종료까지 해줘야함 뒤로가기시 불가능하도록
    }
}
