package com.teamide.idecomobility;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.content.Context.MODE_PRIVATE;

public class ModifyDialog extends Dialog implements View.OnClickListener{
    private MainActivity main;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;
    private Button cancelButton, callButton, button29, button30, button31, button32;
    private Context context;
    private androidx.appcompat.widget.Toolbar toolbar;
    private ModifyDialog.GroupDialogListener groupDialogListener;

    public ModifyDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void setDialogListener(GroupDialogListener groupDialogListener) {
        this.groupDialogListener = groupDialogListener;
    }

    interface GroupDialogListener{
        void onCancelClicked();
        void onSaveClicked();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_modify_dialog);

        preferences = context.getSharedPreferences("info", MODE_PRIVATE);
        editor = preferences.edit();

        toolbar = findViewById(R.id.servicemodifytoolbar);
        toolbar.setTitle("필요서비스 수정");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        cancelButton = findViewById(R.id.button27);
        callButton = findViewById(R.id.button28);
        button29 = findViewById(R.id.button29);
        button30 = findViewById(R.id.button30);
        button31 = findViewById(R.id.button31);
        button32 = findViewById(R.id.button32);

        if(preferences.getBoolean("inputdata1",false))
        {
            button29.setBackgroundResource(R.drawable.radius_filled);
            button29.setTextColor(Color.parseColor("white"));
        }
        if(preferences.getBoolean("inputdata2",false))
        {
            button30.setBackgroundResource(R.drawable.radius_filled);
            button30.setTextColor(Color.parseColor("white"));
        }
        if(preferences.getBoolean("inputdata3",false))
        {
            button31.setBackgroundResource(R.drawable.radius_filled);
            button31.setTextColor(Color.parseColor("white"));
        }
        if(preferences.getBoolean("inputdata4",false))
        {
            button32.setBackgroundResource(R.drawable.radius_filled);
            button32.setTextColor(Color.parseColor("white"));
        }

        cancelButton.setOnClickListener(this);
        callButton.setOnClickListener(this);
        button29.setOnClickListener(this);
        button30.setOnClickListener(this);
        button31.setOnClickListener(this);
        button32.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        switch (view.getId()){
            case R.id.button29:
                if (preferences.getBoolean("inputdata1",false)==false) {
                    button.setBackgroundResource(R.drawable.radius_filled);
                    button.setTextColor(Color.parseColor("white"));
                    editor.putBoolean("inputdata1", true); // key,value 형식으로 저장
                } else {
                    button.setBackgroundResource(R.drawable.radius);
                    button.setTextColor(Color.parseColor("black"));
                    editor.putBoolean("inputdata1", false); // key,value 형식으로 저장
                }
                break;
            case R.id.button30:
                if (preferences.getBoolean("inputdata2",false)==false) {
                    button.setBackgroundResource(R.drawable.radius_filled);
                    button.setTextColor(Color.parseColor("white"));
                    editor.putBoolean("inputdata2", true); // key,value 형식으로 저장
                } else {
                    button.setBackgroundResource(R.drawable.radius);
                    button.setTextColor(Color.parseColor("black"));
                    editor.putBoolean("inputdata2", false); // key,value 형식으로 저장
                }
                break;
            case R.id.button31:
                if (preferences.getBoolean("inputdata3",false)==false) {
                    button.setBackgroundResource(R.drawable.radius_filled);
                    button.setTextColor(Color.parseColor("white"));
                    editor.putBoolean("inputdata3", true); // key,value 형식으로 저장
                } else {
                    button.setBackgroundResource(R.drawable.radius);
                    button.setTextColor(Color.parseColor("black"));
                    editor.putBoolean("inputdata3", false); // key,value 형식으로 저장
                }
                break;
            case R.id.button32:
                if (preferences.getBoolean("inputdata4",false)==false) {
                    button.setBackgroundResource(R.drawable.radius_filled);
                    button.setTextColor(Color.parseColor("white"));
                    editor.putBoolean("inputdata4", true); // key,value 형식으로 저장
                } else {
                    button.setBackgroundResource(R.drawable.radius);
                    button.setTextColor(Color.parseColor("black"));
                    editor.putBoolean("inputdata4", false); // key,value 형식으로 저장
                }
                break;
            case R.id.button28:
                editor.commit();
                groupDialogListener.onSaveClicked();
                // main에 데이터는 넘겨줬는데 main 다시 로드 하는 코드
                dismiss();
                break;
            case R.id.button27:
                groupDialogListener.onCancelClicked();
                cancel();
                break;
        }
    }

}
