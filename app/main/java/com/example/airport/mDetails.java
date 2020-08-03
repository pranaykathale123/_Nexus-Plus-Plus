package com.example.airport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class mDetails extends AppCompatActivity {

    DatabaseHelper mdb;
    SQLiteDatabase SQLdb;

    TextView mID,mName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_details);

        mdb = new DatabaseHelper(this);
        SQLdb = mdb.getReadableDatabase();

        mID = findViewById(R.id.m_id);
        mName = findViewById(R.id.mname);

        Intent i = getIntent();
        String Id = i.getExtras().getString("key_id");

        Cursor cv = SQLdb.rawQuery("select * from equipments where emp_id =?",new String[]{Id});
        if (cv.moveToFirst()){

            mID.setText(cv.getString(0));
            mName.setText(cv.getString(1));
        }
        else {
            mID.setText("");
            mName.setText("");
        }
    }
}