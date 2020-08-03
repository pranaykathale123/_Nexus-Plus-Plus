package com.example.airport.Mechanic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.airport.ComplaintList;
import com.example.airport.MainActivity;
import com.example.airport.QrScanner;
import com.example.airport.R;
import com.example.airport.Selecteg;
import com.example.airport.UserList;
import com.example.airport.machine_list;
import com.example.airport.mechaanic_details;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MechanicDashboard extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton scanner;
    CardView logout, mprofile, mjob, sHistory, Onwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_dashboard);

        scanner = findViewById(R.id.dash_scan);
        logout = findViewById(R.id.logout);
        mprofile = findViewById(R.id.mech_profile);
        mjob = findViewById(R.id.mech_job);
        sHistory = findViewById(R.id.mech_service);
        Onwork = findViewById(R.id.mech_ongoing_work);

        logout.setOnClickListener(this);
        mprofile.setOnClickListener(this);
        mjob.setOnClickListener(this);
        sHistory.setOnClickListener(this);
        Onwork.setOnClickListener(this);

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scannerIntent = new Intent(MechanicDashboard.this, QrScanner.class);
                startActivity(scannerIntent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sIntent = new Intent(MechanicDashboard.this, MainActivity.class);
                startActivity(sIntent);
            }
        });


    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.dash_scan:
                i = new Intent(this, QrScanner.class);
                startActivity(i);
                break;
            case R.id.mech_profile:
                i = new Intent(this, MechanicProfile.class);
                startActivity(i);
                break;
            case R.id.mech_job:
                i = new Intent(this, jobList.class);
                startActivity(i);
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mech_ongoing_work:
//                i = new Intent(this, );
//                startActivity(i);
                Toast.makeText(this, "Ongoing Work", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mech_service:
                i = new Intent(this, ServiceList.class);
                startActivity(i);
                break;
            case R.id.logout:
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;

        }
    }
}