package com.example.final_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    GridLayout mainGrid;
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);
        mainGrid = findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
        }


        private void setSingleEvent(GridLayout mainGrid){
        for (int i=0; i<mainGrid.getColumnCount();i++)
        {
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (finalI)
                    {
                        case 0:
                            Intent RegIntent = new Intent(MainActivity.this, Registration.class);
                            startActivity(RegIntent);
                            finish();
                            break;
                        case 1:
                            Intent mechIntent = new Intent(MainActivity.this, machin_list.class);
                            startActivity(mechIntent);
                            finish();
                            Toast.makeText(MainActivity.this, "Machine", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Intent userIntent = new Intent(MainActivity.this, user_list.class);
                            startActivity(userIntent);
                            finish();
                            break;
                        case 3:
//                            Intent compIntent = new Intent(MainActivity.this,.class);
//                            startActivity(compIntent);
//                            finish();
                            Toast.makeText(MainActivity.this, "Work Status", Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
//                            Intent jobIntent = new Intent(MainActivity.this, Registration.class);
//                            startActivity(jonIntent);
//                            finish();
                            Toast.makeText(MainActivity.this, "Job Allotment", Toast.LENGTH_SHORT).show();
                            break;
                        case 5:
//                            Intent workStatusIntent = new Intent(MainActivity.this, Registration.class);
//                            startActivity(workStatusIntent);
//                            finish();
                                Toast.makeText(MainActivity.this, "Complaints", Toast.LENGTH_SHORT).show();
                                break;
                    }
                }
            });
        }
    }
}