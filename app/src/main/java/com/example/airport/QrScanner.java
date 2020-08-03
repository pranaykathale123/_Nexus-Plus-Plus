package com.example.airport;

import androidx.annotation.NonNull;
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

public class QrScanner extends AppCompatActivity {
    private CodeScanner mScanner;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);

        loadingBar = new ProgressDialog(this);
        CodeScannerView scannerView = findViewById(R.id.scan);
        mScanner = new CodeScanner(this, scannerView);

        //      Decoding QR Code
        mScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result id) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadingBar.setMessage("Please Wait...");
                        loadingBar.show();
                        loadingBar.setCanceledOnTouchOutside(true);
                        CheckValidation(id);  //Method to Check User

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScanner.startPreview();
            }
        });
    }

    private void CheckValidation(Result id) {
        DatabaseHelper mdb = new DatabaseHelper(QrScanner.this);
        SQLiteDatabase db = mdb.getReadableDatabase();
        String uid = id.getText();
        if(id.getText().isEmpty()){
            Toast.makeText(QrScanner.this,"Nothing to show", Toast.LENGTH_LONG).show();

        }else {
            Cursor c = db.rawQuery("SELECT * FROM equipments WHERE emp_id=?",new String[]{uid});
            if(c.moveToFirst()){
                Intent QrIntent = new Intent(QrScanner.this,mDetails.class);
                QrIntent.putExtra("key_id",""+uid);
                startActivity(QrIntent);
            }
            else {
                Toast.makeText(QrScanner.this,"Invalid Equipment", Toast.LENGTH_SHORT).show();
            }
        }
        loadingBar.dismiss();
    }
    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();

    }
    @Override
    protected void onPause() {
        mScanner.releaseResources();
        super.onPause();
    }
    private void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                mScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(QrScanner.this, "Camera  Permission is Required", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }
}