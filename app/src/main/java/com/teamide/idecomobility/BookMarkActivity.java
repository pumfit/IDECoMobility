package com.teamide.idecomobility;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookMarkActivity extends AppCompatActivity {

    public TextView textView;

    public ArrayList<InfoAddress> boolmarklist;
    public ArrayList<InfoAddress> infoArrayList;
    public RecyclerView mRecyclerView;
    public RecyclerView.LayoutManager mLayoutManager;

    private BookMarkAdapter myAdapter;
    public AddBookMarkDialog dialog;
    public SearchAddress startAddress;
    public SearchAddress endAddress;
    private InfoAddress saveAddress;//현위치,출발지,도착지 주소 정보

    public Boolean onEdited = false;

    public Button deletButton;

    public myDBHelper helper;
    public SQLiteDatabase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.bookmarktoolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color='#ffffff'>즐겨찾기 </font>"));
        actionBar.setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        textView = findViewById(R.id.textView);
        boolmarklist = new ArrayList<InfoAddress>();
        dialog = new AddBookMarkDialog(this);

        saveAddress = new InfoAddress();

        mRecyclerView = findViewById(R.id.addresslist);
        mRecyclerView.setHasFixedSize(true);//리사이클러뷰 받아오고 설정

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        deletButton = findViewById(R.id.deletebutton);
        deletButton.setVisibility(View.GONE);

        helper = new myDBHelper(this);

        try {
            db = helper.getWritableDatabase();
            Log.v("mytag", "데이터베이스를 쓸 수 있음");
        } catch (SQLiteException e) {
            db = helper.getReadableDatabase();
            Log.v("mytag", "데이터베이스를 읽을 수만 있음");
        }

        viewRecyclerView();
    }

    public class myDBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "bookmarkInfo.db";

        public myDBHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, 1);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS bookmarks( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " " + "s_main TEXT," + "s_full TEXT," + "s_la DOUBLE," + "s_lo DOUBLE," +
                    "e_main TEXT," + "e_full TEXT," + "e_la DOUBLE," + "e_lo DOUBLE);");
            Log.v("mytag", "테이블 처음 생성됨");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS bookmarks;");
            Log.v("mytag", "데이터베이스 오픈됨");
            onCreate(db);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) { //from SearchActivity
            if (resultCode == RESULT_OK) {
                SearchAddress startAllAdress = data.getParcelableExtra("startAllAddress");
                EditText s_editText = dialog.findViewById(R.id.editText);
                EditText e_editText = dialog.findViewById(R.id.editText2);

                s_editText.setText(startAllAdress.getMainAdress());
                saveAddress.setStartAddress(startAllAdress);
            }
        } else if (requestCode == 201) {
            if (resultCode == RESULT_OK) { //from SearchActivity2
                SearchAddress endAllAdress = data.getParcelableExtra("endAllAddress");
                EditText e_editText = dialog.findViewById(R.id.editText2);
                if (saveAddress.getStartAddress().getMainAdress().equals(endAllAdress.getMainAdress())) {
                    Toast.makeText(getApplicationContext(), "출발지와 도착지는 같을 수 없습니다.", Toast.LENGTH_LONG).show();
                } else {
                    e_editText.setText((CharSequence) endAllAdress.getMainAdress());
                    saveAddress.setEndAddress(endAllAdress);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
            case R.id.action_add: {
                dialog.setDialogListener(new AddBookMarkDialog.CustomDialogListener() {
                    @Override
                    public void onPositiveClicked() {
                        String s_main = saveAddress.getStartAddress().getMainAdress();
                        String s_full = saveAddress.getStartAddress().getFullAdress();
                        Double s_la = saveAddress.getStartAddress().getLatitude();
                        Double s_lo = saveAddress.getStartAddress().getLongitude();
                        String e_main = saveAddress.getEndAddress().getMainAdress();
                        String e_full = saveAddress.getEndAddress().getFullAdress();
                        Double e_la = saveAddress.getEndAddress().getLatitude();
                        Double e_lo = saveAddress.getEndAddress().getLongitude();
                        db.execSQL("INSERT INTO bookmarks VALUES (null,'" + s_main + "','" + s_full + "','" + s_la + "','" + s_lo
                                + "','" + e_main + "','" + e_full + "','" + e_la + "','" + e_lo + "');");
                        Log.v("mytag", "데이테베이스에 데이터 저장함");
                        viewRecyclerView();
                    }

                    @Override
                    public void onNegativeClicked() {
                        dialog.cancel();
                    }
                });
                dialog.show();
                break;
            }
            case R.id.action_edit: {
                if (onEdited == false) {
                    myAdapter.setEdited(true);
                    mRecyclerView.setAdapter(myAdapter);
                    Button deletButton = findViewById(R.id.deletebutton);
                    deletButton.setVisibility(View.VISIBLE);
                    onEdited = true;
                } else {
                    myAdapter.setEdited(false);
                    mRecyclerView.setAdapter(myAdapter);
                    Button deletButton = findViewById(R.id.deletebutton);
                    deletButton.setVisibility(View.GONE);
                    onEdited = false;
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickedDelete(View v) {
        infoArrayList = new ArrayList<InfoAddress>();
        Boolean[] list = myAdapter.getSelectList();
        ArrayList<InfoAddress> cList = myAdapter.getListData();
        ArrayList<InfoAddress> mList = new ArrayList<InfoAddress>();

        for (int i = 0; i < cList.size(); i++) {
            if (list[i] == false) {
                mList.add(cList.get(i));
            }
        }
        infoArrayList = mList;

        setDb();
        viewRecyclerView();

        Button deletButton = findViewById(R.id.deletebutton);
        deletButton.setVisibility(View.GONE);
        onEdited = false;
    }

    public void viewRecyclerView() {
        infoArrayList = new ArrayList<InfoAddress>();
        Cursor cursor;//커서 생성
        cursor = db.rawQuery("SELECT * FROM bookmarks", null);
        while (cursor.moveToNext()) {
            String s_main = cursor.getString(1);
            String s_full = cursor.getString(2);
            Double s_la = Double.parseDouble(cursor.getString(3));
            Double s_lo = Double.parseDouble(cursor.getString(4));
            String e_main = cursor.getString(5);
            String e_full = cursor.getString(6);
            Double e_la = Double.parseDouble(cursor.getString(7));
            Double e_lo = Double.parseDouble(cursor.getString(8));
            startAddress = new SearchAddress(s_main, s_full, s_la, s_lo);
            endAddress = new SearchAddress(e_main, e_full, e_la, e_lo);
            infoArrayList.add(new InfoAddress(startAddress, startAddress, endAddress));
        }//리스트에 데이터베이스안에 들어 있는 데이터들을 추가함
        cursor.close();
        myAdapter = new BookMarkAdapter(infoArrayList, getApplicationContext());
        mRecyclerView.setAdapter(myAdapter);//어뎁터와 연결
        Log.v("mytag", "데이터베이스를 조회 함");
    }

    public void setDb() {
        db.execSQL("delete from bookmarks");
        for (int i = 0; i < infoArrayList.size(); i++) {
            String s_main = infoArrayList.get(i).getStartAddress().getMainAdress();
            String s_full = infoArrayList.get(i).getStartAddress().getFullAdress();
            Double s_la = infoArrayList.get(i).getStartAddress().getLatitude();
            Double s_lo = infoArrayList.get(i).getStartAddress().getLongitude();
            String e_main = infoArrayList.get(i).getEndAddress().getMainAdress();
            String e_full = infoArrayList.get(i).getEndAddress().getFullAdress();
            Double e_la = infoArrayList.get(i).getEndAddress().getLatitude();
            Double e_lo = infoArrayList.get(i).getEndAddress().getLongitude();

            db.execSQL("INSERT INTO bookmarks VALUES (null,'" + s_main + "','" + s_full + "','" + s_la + "','" + s_lo
                    + "','" + e_main + "','" + e_full + "','" + e_la + "','" + e_lo + "');");
        }
        Log.v("mytag", "데이테베이스에 삭제후 데이터 다시 저장함");
    }

}
