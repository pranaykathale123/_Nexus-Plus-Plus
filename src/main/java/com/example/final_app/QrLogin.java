package com.example.final_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class QrLogin extends AppCompatActivity {
    DatabaseHelper mdb;
    SQLiteDatabase SQLdb;
    private CodeScanner mCodeScanner;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_login);

        //DataBaseHelper Instance
        mdb=new DatabaseHelper(this);


        loadingBar = new ProgressDialog(this);
        CodeScannerView scannerView = findViewById(R.id.ScannerView);
        mCodeScanner = new CodeScanner(this, scannerView);

//        Decoding QR Code
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        loadingBar.setMessage("Please Wait...");
                        loadingBar.show();
                        loadingBar.setCanceledOnTouchOutside(true);
                        CheckUserValidation(result);  //Method to Check User

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeScanner.startPreview();
            }
        });
    }

    private void CheckUserValidation(Result result) {
        SQLiteDatabase db = mdb.getReadableDatabase();
        if(result.getText().trim().isEmpty()){

            showMessage("Oops","Make Sure You entered both Username and Password");

        }else {

            //To check wether the feed username and password match or not
            Cursor c = db.rawQuery("SELECT * FROM users WHERE email='"
                            + result.getText().trim() + "", null);
            if (c.moveToFirst()){
                String UserEmail = c.getString(4);
                String UseryType = c.getString(16);

                Intent QrIntent = new Intent(QrLogin.this,Password.class);
                QrIntent.putExtra("key_email",UserEmail);
                QrIntent.putExtra("key_utype",UseryType);
                startActivity(QrIntent);

            }
    }


}


    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();

    }
    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
    private void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                mCodeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(QrLogin.this, "Camera  Permission is Required", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    private void showMessage(String title,String message) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}