package com.teamide.idecomobility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBookMarkDialog extends Dialog implements View.OnClickListener {
    private Button mPositiveButton;
    private Button mNegativeButton;
    private Button startButton;
    private Button endButton;
    private EditText editStartText;
    private EditText editEndText;
    private Context context;

    private androidx.appcompat.widget.Toolbar toolbar;

    private AddBookMarkDialog.CustomDialogListener customDialogListener;

    //public ArrayList<SearchAddress> dataList;

    public AddBookMarkDialog(Context context) {
        super(context);
        this.context = context;
    }
    interface CustomDialogListener{
        void onPositiveClicked();
        void onNegativeClicked();
    }

    public void setDialogListener(AddBookMarkDialog.CustomDialogListener customDialogListener){
        this.customDialogListener = customDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_bookmark_dialog);

        toolbar = findViewById(R.id.bookmarktoolbar);
        toolbar.setTitle("자주가는 경로 등록");

        editStartText = findViewById(R.id.editText);
        editEndText = findViewById(R.id.editText2);
        editStartText.setEnabled(false);
        editEndText.setEnabled(false);

        mPositiveButton = findViewById(R.id.sbutton);
        mNegativeButton = findViewById(R.id.nbutton);
        startButton = findViewById(R.id.startbutton);
        endButton = findViewById(R.id.endbutton);

        mPositiveButton.setOnClickListener(this);
        mNegativeButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        endButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startbutton:
                Log.d("intent","인텐트 실행");
                Intent intent = new Intent(getContext(), SearchActivity.class);
                ((Activity)context).startActivityForResult(intent, 200);
                break;
            case R.id.endbutton:
                intent = new Intent(getContext(), SearchActivity2.class);
                ((Activity)context).startActivityForResult(intent, 201);
                break;
            case R.id.sbutton: //저장 버튼을 눌렀을 때
                if (editStartText.length() == 0) {
                    Toast.makeText(v.getContext(), "출발지 주소를 선택해주세요.", Toast.LENGTH_LONG).show();
                } else if (editEndText.length() == 0) {
                    Toast.makeText(v.getContext(), "도착지 주소를 선택해주세요.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    customDialogListener.onPositiveClicked();
                    dismiss();
                }
                break;
            case R.id.nbutton: //취소 버튼을 눌렀을 때
                cancel();
                break;
        }
    }
}
