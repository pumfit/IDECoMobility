package com.teamide.idecomobility;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
    public RadioGroup preferRadioButton;
    public RecyclerView recyclerView;
    public ArrayList<SearchAddress> savelist;
    public ArrayList<String> fullNamelist, mainNamelist, lalist, lolist;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        preferences = getSharedPreferences("info", MODE_PRIVATE);
        editor = preferences.edit();
        editor.putBoolean("inputbus", true);
        savelist = getAddressList();
        //savelist = new ArrayList<SearchAddress>();
        Log.d("ad", "함수로 넣음");
        //savelist.add(new SearchAddress("d","e","0km",0,0));
        preferRadioButton = findViewById(R.id.PreferRadioGroup);
        fullNamelist = new ArrayList<>();
        mainNamelist = new ArrayList<>();
        lalist = new ArrayList<>();
        lolist = new ArrayList<>();
        editor.putBoolean("inputdata1", false);
        editor.putBoolean("inputdata2", false);
        editor.putBoolean("inputdata3", false);
        editor.putBoolean("inputdata4", false);

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
}
