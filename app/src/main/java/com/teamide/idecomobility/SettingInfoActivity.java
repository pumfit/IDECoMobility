package com.teamide.idecomobility;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class SettingInfoActivity extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    public boolean isbTrue[] = new boolean[8];
    EditText addressEdit;
    Button wheel, wheelLift, elevator, foot;
    public RadioGroup preferRadioButton;
    public RecyclerView recyclerView;
    public ArrayList<SearchAddress> savelist;
    public ArrayList<String> fullNamelist, mainNamelist, lalist, lolist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        Toolbar toolbar = findViewById(R.id.settingtoolbar);
        setActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("설정");
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        preferences = getSharedPreferences("info", MODE_PRIVATE);
        editor = preferences.edit();

        initView();

        int savedRadioIndex = preferences.getInt("trans",0);
        RadioButton savedRadioButton = (RadioButton)preferRadioButton.getChildAt(savedRadioIndex);
        savedRadioButton.setChecked(true);

        if(preferences.getBoolean("inputdata1",false)) {
            wheel.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            wheel.setTextColor(ContextCompat.getColor(this, R.color.white));
        }

        if(preferences.getBoolean("inputdata2",false))
        {
            wheelLift.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            wheelLift.setTextColor(ContextCompat.getColor(this, R.color.white));
        }
        if(preferences.getBoolean("inputdata3",false))
        {
            elevator.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            elevator.setTextColor(ContextCompat.getColor(this, R.color.white));
        }
        if(preferences.getBoolean("inputdata4",false))
        {
            foot.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            foot.setTextColor(ContextCompat.getColor(this, R.color.white));
        }

        savelist = getAddressList();
        Log.d("ad", "함수로 넣음");
        preferRadioButton = findViewById(R.id.PreferRadioGroup);
        fullNamelist = new ArrayList<>();
        mainNamelist = new ArrayList<>();
        lalist = new ArrayList<>();
        lolist = new ArrayList<>();

        recyclerView = findViewById(R.id.addresslist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        AddmAddressAdapter adapter = new AddmAddressAdapter(savelist);
        recyclerView.setAdapter(adapter);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addbutton:
                if (savelist.size() >= 3) {
                    Toast.makeText(getApplicationContext(), "최대 3개까지만 저장할 수 있습니다.", Toast.LENGTH_LONG).show();
                } else {
                    final AddAddressDialog dialog = new AddAddressDialog(this);
                    dialog.setDialogListener(new AddAddressDialog.CustomDialogListener() {
                        @Override
                        public void onPositiveClicked(SearchAddress saveAddress) {
                            savelist.add(saveAddress);
                        }

                        @Override
                        public void onNegativeClicked() {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
                break;
        }
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

    public ArrayList<SearchAddress> getAddressList() {
        ArrayList<SearchAddress> savelist = new ArrayList<>();
        ArrayList<String> full = getStringArrayPref("Addaddress1");
        ArrayList<String> main = getStringArrayPref("Addaddress2");
        ArrayList<String> lo = getStringArrayPref("Addaddress3");
        ArrayList<String> la = getStringArrayPref("Addaddress4");
        if (full.size() > 0) {
            for (int i = 0; i < full.size(); i++) {
                savelist.add(new SearchAddress(main.get(i), full.get(i), "0km", Double.parseDouble(lo.get(i)), Double.parseDouble(la.get(i))));
            }
        }
        return savelist;
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
                    editor.putInt("trans", 0);
                    editor.putBoolean("inputcalltax", false);
                    editor.putBoolean("inputsubway", false);
                    editor.putBoolean("inputonfoot", false);

                }
                break;
            case R.id.radioButton4:
                if (checked) {
                    editor.putInt("trans", 1);
                    editor.putBoolean("inputbus", false);
                    editor.putBoolean("inputsubway", false);
                    editor.putBoolean("inputonfoot", false);
                }
                break;
            case R.id.radioButton5:
                if (checked) {
                    editor.putInt("trans", 2);
                }
                break;
            case R.id.radioButton6:
                if (checked) {
                    editor.putInt("trans", 3);
                }
                break;
        }
        editor.commit();
    }

    public void OnCliked5(View v) {
        Button button = (Button) v;

        if (preferences.getBoolean("inputdata1",false)==false) {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputdata1", true); // key,value 형식으로 저장
        } else {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputdata1", false); // key,value 형식으로 저장
        }
        editor.commit();
    }

    public void OnCliked6(View v) {
        Button button = (Button) v;
        if(preferences.getBoolean("inputdata2",false)==false) {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputdata2", true); // key,value 형식으로 저장
        }else{
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputdata2", false); // key,value 형식으로 저장
        }
//        if (isbTrue[5] == false) {
//            isbTrue[5] = true;
        editor.commit();
    }

    public void OnCliked7(View v) {
        Button button = (Button) v;

        if (preferences.getBoolean("inputdata3",false)==false) {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputdata3", true); // key,value 형식으로 저장
        } else {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputdata3", false); // key,value 형식으로 저장
        }
        editor.commit();
    }

    public void OnCliked8(View v) {
        Button button = (Button) v;

        if (preferences.getBoolean("inputdata4",false)==false) {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius_filled));
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            editor.putBoolean("inputdata4", true); // key,value 형식으로 저장
        } else {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.radius));
            button.setTextColor(ContextCompat.getColor(this, R.color.black));
            editor.putBoolean("inputdata4", false); // key,value 형식으로 저장
        }
        editor.commit();
    }

    public void OnSaved(View v) {
        Log.d("ad", "사이즈" + savelist.size());
        if (savelist.size() > 0) {
            for (int i = 0; i < savelist.size(); i++)
            {
                fullNamelist.add(savelist.get(i).getFullAdress());
                mainNamelist.add(savelist.get(i).getMainAdress());
                lolist.add(Double.toString(savelist.get(i).getLongitude()));
                lalist.add(Double.toString(savelist.get(i).getLatitude()));
            }
        }
        else
        {
            fullNamelist.clear();
            mainNamelist.clear();
            lolist.clear();
            lalist.clear();
        }
        setStringArrayPref("Addaddress1", fullNamelist);
        setStringArrayPref("Addaddress2", mainNamelist);
        setStringArrayPref("Addaddress3", lolist);
        setStringArrayPref("Addaddress4", lalist);
        editor.commit();

        Intent newIntent = new Intent(this, MainActivity.class);
        startActivity(newIntent);
        finish();
    }

    private void setStringArrayPref(String key, ArrayList<String> values) {
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();
    }

    private ArrayList<String> getStringArrayPref(String key) {
        preferences = getSharedPreferences("info", MODE_PRIVATE);
        String json = preferences.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }

    private void initView(){
        preferRadioButton = (RadioGroup)findViewById(R.id.PreferRadioGroup);
        wheel = (Button)findViewById(R.id.wheel);
        wheelLift = (Button)findViewById(R.id.wheelLift);
        elevator = (Button)findViewById(R.id.elevator);
        foot = (Button)findViewById(R.id.foot);
    }
}
