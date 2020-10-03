package com.teamide.idecomobility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CalltexiDialog extends Dialog implements View.OnClickListener{
    private Button cancelButton;
    private Button callButton;
    private TextView telNum;
    private Context context;
    String tel = "1588-4388";

    private androidx.appcompat.widget.Toolbar toolbar;

    private CalltexiDialog.GroupDialogListener groupDialogListener;

    public CalltexiDialog(Context context) {
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

        setContentView(R.layout.calltexi_dialog);

        toolbar = findViewById(R.id.calltexitoolbar);
        toolbar.setTitle("장애인 콜 택시 호출");

        cancelButton = findViewById(R.id.button25);
        callButton = findViewById(R.id.button26);
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
