package com.example.airport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

public class AdminDashboard extends AppCompatActivity implements View.OnClickListener{

    CardView register,machine,usr,complaint,job,ws;
    String userEmail, userPWD,userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        register = findViewById(R.id.card_reg);
        machine = findViewById(R.id.card_machine);
        usr = findViewById(R.id.card_users);
        complaint = findViewById(R.id.card_cmln);
        job = findViewById(R.id.card_job);
        ws = findViewById(R.id.card_ws);


        register.setOnClickListener(this);
        machine.setOnClickListener(this);
        usr.setOnClickListener(this);
        complaint.setOnClickListener(this);
        ws.setOnClickListener(this);

//        Intent LoginIntent = getIntent();
//        userID = LoginIntent.getExtras().getString("key_Id");
//        userEmail = LoginIntent.getExtras().getString("key_email");
//        userPWD = LoginIntent.getExtras().getString("key_pwd");



//        SharedPreferences pref = PreferenceManager.getSharedPreferences(this);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString("email",userID);
//        editor.putString("password",userPWD);
//        editor.commit();

    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.card_reg:
                i = new Intent(this,Selecteg.class);
                startActivity(i);
                break;
            case R.id.card_machine:
                i = new Intent(this,machine_list.class);
                startActivity(i);
                break;
            case R.id.card_users:
                i = new Intent(this,UserList.class);
                startActivity(i);
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.card_cmln:
                i = new Intent(this,ComplaintList.class);
                startActivity(i);
                break;
            case R.id.card_job:
//                i = new Intent(this,);
//                startActivity(i);
                break;
            case R.id.card_ws:
//                i = new Intent(this,);
//                startActivity(i);
                break;

        }
    }
}