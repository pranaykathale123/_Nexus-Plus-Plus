package com.example.final_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class machine_details extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_details);

        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.myViewPager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Machine Info");
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdopter viewPagerAdopter = new ViewPagerAdopter(getSupportFragmentManager());
        viewPagerAdopter.addFragment(new DetailsFragment(),"Details");
        viewPagerAdopter.addFragment(new MaintainanceHistoryFragment(),"Service History");

        viewPager.setAdapter(viewPagerAdopter);
    }
}