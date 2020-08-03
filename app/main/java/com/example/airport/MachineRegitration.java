package com.example.airport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MachineRegitration extends AppCompatActivity {
    DatabaseHelper mdb ;
    SQLiteDatabase sqLiteDatabase;
    AppCompatEditText machineId1;
    AppCompatEditText machineName1;
    AppCompatEditText machine_type1;
    AppCompatEditText purchase_date1;
    AppCompatEditText cat;
    AppCompatEditText loc;
    AppCompatEditText p;
    AppCompatEditText life;
    ImageView imageView;
    Button capture, insert;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_regitration);

        mdb=new DatabaseHelper(this);

        machineId1 = findViewById(R.id.edit_text_machine_id);
        machineName1 = findViewById(R.id.edit_text_machine_name);
        machine_type1 = findViewById(R.id.edit_text_machine_type);
        loc = findViewById(R.id.m_location);
        p= findViewById(R.id.m_price);
        life = findViewById(R.id.m_lifespan);
        cat = findViewById(R.id.m_ctg);
        purchase_date1 = findViewById(R.id.m_purchase_date);



        imageView = findViewById(R.id.image_machine);
        capture = findViewById(R.id.button_capture_image);
        insert = findViewById(R.id.button_insert);


        capture.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper mdb =new DatabaseHelper(MachineRegitration.this);
                try {

                    mdb.addmachine(machineId1.getText().toString().trim(),
                            machineName1.getText().toString().trim(),
                            cat.getText().toString().trim(),
                            machine_type1.getText().toString().trim(),
                            machine_type1.getText().toString().trim(),
                            loc.getText().toString().trim(),
                            purchase_date1.getText().toString(),
                            Float.valueOf(p.getText().toString().trim()),
                            imageViewToByte(imageView)
                    );
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }

//
}

