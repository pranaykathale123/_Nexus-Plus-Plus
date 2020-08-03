package com.example.airport.Mechanic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.airport.DatabaseHelper;
import com.example.airport.ListAdapter;
import com.example.airport.ListItem;
import com.example.airport.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ServiceList extends AppCompatActivity {

    DatabaseHelper db;

    RecyclerView NewsRecyclerview;
    ListAdapter listAdapter;
    List<ListItem> mData;
    FloatingActionButton fabSwitcher;
    boolean isDark = false;
    ConstraintLayout rootLayout;
    EditText searchInput ;
    CharSequence search="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Service List");
        db = new DatabaseHelper(this);

        // hide the action bar

//        getSupportActionBar().hide();



        // ini view

        fabSwitcher = findViewById(R.id.fab_switcher);
        rootLayout = findViewById(R.id.mech_layout);
        searchInput = findViewById(R.id.search_input);
        NewsRecyclerview = findViewById(R.id.news_rv);
        mData = new ArrayList<>();
        viewMachine();
        // load theme state

        isDark = getThemeStatePref();
        if(isDark) {
            // dark theme is on

            searchInput.setBackgroundResource(R.drawable.search_input_dark_style);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.black));

        }
        else
        {
            // light theme is on
            searchInput.setBackgroundResource(R.drawable.search_input_style);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.white));

        }



        // fill list news with data
        // just for testing purpose i will fill the news list with random data
        // you may get your data from an api / firebase or sqlite database ...

//        mData.add(new NewsItem("Service 1","Supervisor ID:","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Service  2","Supervisor ID:","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Service  3 ","Supervisor ID:","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Service  4","Supervisor ID:,","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Service  5","Supervisor ID:","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Service  6","Supervisor ID: ","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Supervisor 7:","Supervisor ID:","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Service  8","Supervisor ID:","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Service  9 ","Supervisor ID:","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Service  10","Supervisor ID:","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Supervisor 11","Supervisor ID:","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Supervisor 12","Supervisor ID:","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Supervisor 13","Supervisor ID:","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Supervisor 14","Supervisor ID:","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Supervisor 15 ","Supervisor ID:","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Supervisor 16","Supervisor ID:","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Supervisor 17","Supervisor ID:","6 july 2020",R.drawable.user_list));
//        mData.add(new NewsItem("Supervisor 18","Supervisor ID: ","6 july 2020",R.drawable.user_list));

        // adapter ini and setup

        listAdapter = new com.example.airport.ListAdapter(this,mData,isDark);
        NewsRecyclerview.setAdapter(listAdapter);
        NewsRecyclerview.setLayoutManager(new LinearLayoutManager(this));


        fabSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDark = !isDark ;
                if (isDark) {

                    rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
                    searchInput.setBackgroundResource(R.drawable.search_input_dark_style);

                }
                else {
                    rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    searchInput.setBackgroundResource(R.drawable.search_input_style);
                }

                listAdapter = new ListAdapter(getApplicationContext(),mData,isDark);
                if (!search.toString().isEmpty()){

                    listAdapter.getFilter().filter(search);

                }
                NewsRecyclerview.setAdapter(listAdapter);
                saveThemeStatePref(isDark);




            }
        });



        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                listAdapter.getFilter().filter(s);
                search = s;


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }

    private void saveThemeStatePref(boolean isDark) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isDark",isDark);
        editor.commit();
    }

    private void viewMachine() {
        Cursor cursor = db.ViewMachines();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No Data to show", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                mData.add(new ListItem(cursor.getString(1),cursor.getString(0),cursor.getString(8).concat("MRP"),R.drawable.hiclipart_com));
            }
        }
    }

    private boolean getThemeStatePref () {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        boolean isDark = pref.getBoolean("isDark",false) ;
        return isDark;

    }




}