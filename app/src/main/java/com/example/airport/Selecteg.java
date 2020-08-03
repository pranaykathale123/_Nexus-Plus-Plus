package com.example.airport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.circularreveal.cardview.CircularRevealCardView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Selecteg extends AppCompatActivity {
    CircleImageView userReg, machineReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecteg);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registrations");

        userReg = findViewById(R.id.user_form);
        machineReg = findViewById(R.id.machine_form);


        userReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegIntent = new Intent(Selecteg.this,UserRegistration.class);
                startActivity(RegIntent);
            }
        });
        machineReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MRegIntent = new Intent(Selecteg.this,MachineRegitration.class);
                startActivity(MRegIntent);
            }
        });
    }
}