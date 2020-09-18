package com.teamide.idecomobility;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class DefaultSettingActivity extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    public boolean isbTrue[] = new boolean[8];
    EditText addressEdit;
    public RadioGroup preferRadioButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaultsetting);

        preferences = getSharedPreferences("info", MODE_PRIVATE);
        editor = preferences.edit();
        editor.putBoolean("inputbus", true);

        preferRadioButton = findViewById(R.id.PreferRadioGroup);
    }

    public void OnClikedSearch(View v) {

    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        editor.putBoolean("inputbus", false);
        editor.putBoolean("inputcalltax", false);
        editor.putBoolean("inputsubway", false);
        editor.putBoolean("inputonfoot", false);

        switch (view.getId()) {
            case R.id.radioButton3:
                if (checked) {
                    editor.putBoolean("inputbus", true);
                }
                break;
            case R.id.radioButton4:
                if (checked) {
                    editor.putBoolean("inputcalltax", true);
                }
                break;
            case R.id.radioButton5:
                if (checked) {
                    editor.putBoolean("inputsubway", true);
                }
                break;
            case R.id.radioButton6:
                if (checked) {
                    editor.putBoolean("inputonfoot", true);
                }
                break;
        }
        editor.commit();
    }

    public void OnCliked5(View v) {
        Button button = (Button) v;

        if (isbTrue[4] == false) {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputdata1", true); // key,value 형식으로 저장
            isbTrue[4] = true;
        } else {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputdata1", false); // key,value 형식으로 저장
            isbTrue[4] = false;
        }
        editor.commit();
    }

    public void OnCliked6(View v) {
        Button button = (Button) v;

        if (isbTrue[5] == false) {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputdata2", true); // key,value 형식으로 저장
            isbTrue[5] = true;
        } else {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputdata2", false); // key,value 형식으로 저장
            isbTrue[5] = false;
        }
        editor.commit();
    }

    public void OnCliked7(View v) {
        Button button = (Button) v;

        if (isbTrue[6] == false) {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputdata3", true); // key,value 형식으로 저장
            isbTrue[6] = true;
        } else {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputdata3", false); // key,value 형식으로 저장
            isbTrue[6] = false;
        }
        editor.commit();
    }

    public void OnCliked8(View v) {
        Button button = (Button) v;

        if (isbTrue[7] == false) {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputdata4", true); // key,value 형식으로 저장
            isbTrue[7] = true;
        } else {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputdata4", false); // key,value 형식으로 저장
            isbTrue[7] = false;
        }
        editor.commit();
    }

    public void OnSaved(View v) {
        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);
        finish();
        //인텐트 종료까지 해줘야함 뒤로가기시 불가능하도록
    }
}
