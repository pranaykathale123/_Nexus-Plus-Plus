package com.example.airport.SuperVisor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.airport.ComplaintList;
import com.example.airport.MainActivity;
import com.example.airport.R;
import com.example.airport.Selecteg;
import com.example.airport.machine_list;

public class SuperVisorDashboard extends AppCompatActivity implements View.OnClickListener{

    CardView Lodge,profile;
    LinearLayout logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_visor_dashboard);

        Lodge = findViewById(R.id.lodge_complaint);
        profile = findViewById(R.id.sup_profile);
        logout = findViewById(R.id.logout);

        logout.setOnClickListener(this);

        Lodge.setOnClickListener(this);
        profile.setOnClickListener(this);
    }
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.sup_profile:
                i = new Intent(this, SuperProfile.class);
                startActivity(i);
                break;
            case R.id.card_machine:
//                i = new Intent(this, machine_list.class);
//                startActivity(i);
                break;
            case R.id.lodge_complaint:
                    i = new Intent(this,LodgeComplaint.class);
                    startActivity(i);
                break;
            case R.id.card_cmln:
//                i = new Intent(this, ComplaintList.class);
//                startActivity(i);
                break;
            case R.id.logout:
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;


        }
    }
}