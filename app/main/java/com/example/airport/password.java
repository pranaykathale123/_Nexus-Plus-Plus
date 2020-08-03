package com.example.airport;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.airport.Mechanic.MechanicDashboard;
import com.example.airport.SuperVisor.SuperVisorDashboard;

public class password extends AppCompatActivity {

    private TextView showEmail,UserPassword, ForgotUserPassword,UserWelcome;
    private Button login;
    private ProgressDialog loadingBar;
    private String email;
    DatabaseHelper mdb;
    SQLiteDatabase SQLdb;
    Integer userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        UserPassword = findViewById(R.id.User_Login_Password);
        login = findViewById(R.id.user_login_btn);
        UserWelcome = findViewById(R.id.UserWelcome);

        loadingBar = new ProgressDialog(this);

        Intent Passintent = getIntent();
        String userType = Passintent.getExtras().getString("key_utype");
        String email = Passintent.getExtras().getString("key_email");

        showEmail = findViewById(R.id.show_email);
        showEmail.setText(email);

        if (userType.equals("Admin") ){
            UserWelcome.setText("Welcome Admin");
        }
        else if (userType.equals("Maintainance Supervisor")){
            UserWelcome.setText("Welcome Supervisor");
        }else {
            UserWelcome.setText("Welcome Mechanic");
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowingUserToLogin();
            }
        });


    }

    private void AllowingUserToLogin() {
        DatabaseHelper mdb = new DatabaseHelper(this);
        SQLdb = mdb.getReadableDatabase();
        Intent Passintent = getIntent();
        String email = Passintent.getExtras().getString("key_email");
        String userType = Passintent.getExtras().getString("key_utype");
        // To read or show singel data
        if(UserPassword.getText().toString().trim().isEmpty()){

            showMessage("Oops","Make Sure You entered Password");


        }else {

            loadingBar.setTitle("Login");
            loadingBar.setMessage("Please Wait...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            String pwd = UserPassword.getText().toString().trim();

            //To check wether the feed username and password match or not
            Cursor c = SQLdb.rawQuery("SELECT * FROM users WHERE email='"
                            + email + "'" +
                            "AND password='" + UserPassword.getText().toString().trim() + "'"
                    , null);
            if (c.moveToFirst()) {
                String UserId = c.getString(1);
                // showMessage(loginUsername, loginPassword);
                //Send User to Dashboard According to UserType
                if (userType.equals("Admin")) {
                    Intent intent = new Intent(password.this, AdminDashboard.class);
                    intent.putExtra("key_Id", UserId);
                    intent.putExtra("key_email",email);
                    intent.putExtra("key_pwd",pwd);
                    startActivity(intent);
                    loadingBar.dismiss();
                    clearText();
                } else if (userType.equals("Maintainance Supervisor")) {
                    Intent intent = new Intent(password.this, SuperVisorDashboard.class);
                    intent.putExtra("key_Id", UserId);
                    intent.putExtra("key_email",email);
                    intent.putExtra("key_pwd",pwd);
                    startActivity(intent);
                    loadingBar.dismiss();
                    clearText();
                } else {
                    Intent intent = new Intent(password.this, MechanicDashboard.class);
                    intent.putExtra("key_Id", UserId);
                    intent.putExtra("key_email",email);
                    intent.putExtra("key_pwd",pwd);
                    startActivity(intent);
                    loadingBar.dismiss();
                    clearText();
                }
            } else {

                showMessage("Oops", "Username and Password does not match");
                loadingBar.dismiss();
                clearText();
            }
            c.close();
        }
    }

    public void showMessage(String title,String message)
    {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText()
    {
        UserPassword.setText("");
    }
}