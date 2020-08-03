package com.example.airport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {


    DatabaseHelper db;

    RecyclerView userlist;
    UserListAdapter userlistadapter;
    List<UserListItem> mData;
    FloatingActionButton fabSwitcher;
    boolean isDark = false;
    ConstraintLayout rootLayout;
    EditText searchInput;
    CharSequence search = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_list);

        db = new DatabaseHelper(this);
        fabSwitcher = findViewById(R.id.fab_switcher);
        rootLayout = findViewById(R.id.user_layout);
        searchInput = findViewById(R.id.search_input);
        userlist = findViewById(R.id.news_rv);
        mData = new ArrayList<>();


        viewUser();
        // load theme state
        isDark = getThemeStatePref();
        if (isDark) {
            // dark theme is on

            searchInput.setBackgroundResource(R.drawable.search_input_dark_style);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.black));

        } else {
            // light theme is on
            searchInput.setBackgroundResource(R.drawable.search_input_style);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.white));

        }


        // fill list news with data
        // just for testing purpose i will fill the news list with random data
        // you may get your data from an api / firebase or sqlite database ...


        // adapter ini and setup

        userlistadapter = new UserListAdapter(this, mData, isDark);
        userlist.setAdapter(userlistadapter);
        userlist.setLayoutManager(new LinearLayoutManager(this));


        fabSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDark = !isDark;
                if (isDark) {

                    rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
                    searchInput.setBackgroundResource(R.drawable.search_input_dark_style);

                } else {
                    rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
                    searchInput.setBackgroundResource(R.drawable.search_input_style);
                }

                userlistadapter = new UserListAdapter(getApplicationContext(), mData, isDark);
                if (!search.toString().isEmpty()) {

                    userlistadapter.getFilter().filter(search);

                }
                userlist.setAdapter(userlistadapter);
                saveThemeStatePref(isDark);


            }
        });


        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                userlistadapter.getFilter().filter(s);
                search = s;


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void saveThemeStatePref(boolean isDark) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isDark", isDark);
        editor.commit();
    }

    private void viewUser() {
        Cursor cursor = db.ViewUsers();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                mData.add(new UserListItem(cursor.getString(1), cursor.getString(0), cursor.getString(8).concat("MRP"), R.drawable.hiclipart_com));
            }
        }
    }

    private boolean getThemeStatePref() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        boolean isDark = pref.getBoolean("isDark", false);
        return isDark;

    }


}