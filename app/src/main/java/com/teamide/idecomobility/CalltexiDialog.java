package com.teamide.idecomobility;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.annotation.SuppressLint;
import org.w3c.dom.Text;
import android.os.Bundle;
import android.util.Log;

public class CalltexiDialog extends Dialog  {
    private Button callButton;
    private Button cancelButton;
    private TextView local;
    private TextView callNumber;
    private Context context;

    private androidx.appcompat.widget.Toolbar toolbar;

    private CustomDialogListener1 customDialogListener;

    public CalltexiDialog(Context context) {
        super(context);
        this.context = context;
    }

    interface CustomDialogListener1{
        void onPositiveClicked();
        void onNegativeClicked();
    }

    public void setDialogListener(CustomDialogListener1 customDialogListener){
        this.customDialogListener = customDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calltexi_dialog);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("장애인 콜택시 호출");

        local = findViewById(R.id.region);
        callNumber = findViewById(R.id.callNum);
        cancelButton = findViewById(R.id.button25);
        callButton = findViewById(R.id.button26);



    };

}
