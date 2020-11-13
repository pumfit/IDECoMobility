package com.teamide.idecomobility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.zip.Inflater;

public class BusHelpDialog extends Dialog implements View.OnClickListener{
    private Button cancelButton;
    private Button callButton;
    private TextView busNm;
    private TextView telNum;
    private Context context;
    String tel = "1588-4388";
    String bus = "1156";

    private androidx.appcompat.widget.Toolbar toolbar;


    private CalltexiDialog.GroupDialogListener groupDialogListener;
    private Inflater inflater;

    public BusHelpDialog(Context context) {
        super(context);
        this.context = context;
    }

    interface GroupDialogListener{
        void onCancelClicked();
        void onCallClicked();
    }
    public void dialogListener(CalltexiDialog.GroupDialogListener groupDialogListener){
        this.groupDialogListener = groupDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bus_dialog);


        toolbar = findViewById(R.id.bustoolbar);
        toolbar.setTitle("저상버스 호출");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        cancelButton = findViewById(R.id.button25);
        callButton = findViewById(R.id.button26);

        busNm = findViewById(R.id.num);
        busNm.setText(bus);

        telNum = findViewById(R.id.callNum);
        telNum.setText(tel);

        cancelButton.setOnClickListener(this);
        callButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button26:
                ((Activity)context).startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+tel)));
                break;
            case R.id.button25:
                cancel();
                break;
        }
    }

}
