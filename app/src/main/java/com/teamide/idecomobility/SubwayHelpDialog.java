package com.teamide.idecomobility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class SubwayHelpDialog extends Dialog implements View.OnClickListener{
    private Button cancelButton;
    private Button callButton;
    private Context context;
    String tel = "1577-1234";

    private androidx.appcompat.widget.Toolbar toolbar;

    private CalltexiDialog.GroupDialogListener groupDialogListener;

    public SubwayHelpDialog(Context context) {
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

        setContentView(R.layout.subway_help_dialog);

        toolbar = findViewById(R.id.calltexitoolbar);
        toolbar.setTitle("원스톱 케어 서비스");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));

        cancelButton = findViewById(R.id.button25);
        callButton = findViewById(R.id.button26);

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
