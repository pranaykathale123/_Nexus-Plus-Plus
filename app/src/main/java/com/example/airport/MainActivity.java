package com.example.airport;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private ProgressDialog loadingBar;
    DatabaseHelper mdb;
    SQLiteDatabase Sqldb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingBar = new ProgressDialog(this);



        CodeScannerView scannerView = findViewById(R.id.ScannerView);
        mCodeScanner = new CodeScanner(this, scannerView);

          //      Decoding QR Code
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


    private void CheckUserValidation(final Result result) {
        DatabaseHelper mdb = new DatabaseHelper(MainActivity.this);
        SQLiteDatabase db = mdb.getReadableDatabase();
        String email = result.getText().trim();
        if(result.getText().trim().isEmpty()){

            Toast.makeText(MainActivity.this,"ID Not Found", Toast.LENGTH_LONG).show();
            loadingBar.dismiss();

        }else {
            //To check wether the feed QR_ID(EMAIL) match or not
         Cursor c = db.rawQuery("SELECT * FROM users WHERE email=?",new String[]{email});
            if(c.moveToFirst()){
                String UserEmail = c.getString(3);
                String UserType = c.getString(17);

                Intent QrIntent = new Intent(MainActivity.this,password.class);
                QrIntent.putExtra("key_email",UserEmail);
                QrIntent.putExtra("key_utype",UserType);
                startActivity(QrIntent);
                loadingBar.dismiss();
            }
            else {
                Toast.makeText(MainActivity.this,"Unknown ID", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
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
                Toast.makeText(MainActivity.this, "Camera  Permission is Required", Toast.LENGTH_SHORT).show();
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
