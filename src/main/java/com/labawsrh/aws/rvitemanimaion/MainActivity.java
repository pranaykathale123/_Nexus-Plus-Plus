package com.labawsrh.aws.rvitemanimaion;

import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView NewsRecyclerview;
    NewsAdapter newsAdapter;
    List<NewsItem> mData;
    FloatingActionButton fabSwitcher;
    boolean isDark = false;
    ConstraintLayout rootLayout;
    EditText searchInput ;
    CharSequence search="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // let's make this activity on full screen

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // hide the action bar

        getSupportActionBar().hide();



        // ini view

        fabSwitcher = findViewById(R.id.fab_switcher);
        rootLayout = findViewById(R.id.root_layout);
        searchInput = findViewById(R.id.search_input);
        NewsRecyclerview = findViewById(R.id.news_rv);
        mData = new ArrayList<>();

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
        mData.add(new NewsItem("Supervisor 1","Supervisor ID:","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 2","Supervisor ID:","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 3 ","Supervisor ID:","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 4","Supervisor ID:,","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 5","Supervisor ID:","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 6","Supervisor ID: ","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 7:","Supervisor ID:","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 8","Supervisor ID:","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 9 ","Supervisor ID:","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 10","Supervisor ID:","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 11","Supervisor ID:","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 12","Supervisor ID:","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 13","Supervisor ID:","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 14","Supervisor ID:","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 15 ","Supervisor ID:","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 16","Supervisor ID:","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 17","Supervisor ID:","6 july 2020",R.drawable.user_list));
        mData.add(new NewsItem("Supervisor 18","Supervisor ID: ","6 july 2020",R.drawable.user_list));


        // adapter ini and setup

        newsAdapter = new NewsAdapter(this,mData,isDark);
        NewsRecyclerview.setAdapter(newsAdapter);
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

                newsAdapter = new NewsAdapter(getApplicationContext(),mData,isDark);
                if (!search.toString().isEmpty()){

                    newsAdapter.getFilter().filter(search);

                }
                NewsRecyclerview.setAdapter(newsAdapter);
                saveThemeStatePref(isDark);




            }
        });



        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                newsAdapter.getFilter().filter(s);
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

    private boolean getThemeStatePref () {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        boolean isDark = pref.getBoolean("isDark",false) ;
        return isDark;

    }



}
