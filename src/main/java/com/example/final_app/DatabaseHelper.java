package com.example.final_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DbName = "AirAuth.db";
    private static final String table1 = "users";
    private static final String table2 = "equipments";
    private static final String table3 = "job_details";
    private static final String table4 = "service_history";


    public DatabaseHelper(Context context) {
        super(context, DbName, null, 1);
        SQLiteDatabase DB =this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        String users = "create table "+table1+" (emp_id INTEGER PRIMARY KEY, fname TEXT NOT NULL, lname TEXT NOT NULL,email TEXT NOT NULL UNIQUE,password TEXT NOT NULL UNIQUE, phone INTEGER NOT NULL UNIQUE ,DOB TEXT NOT NULL ,address TEXT NOT NULL , qualification TEXT NOT NULL, proficiency TEXT NOT NULL, designation TEXT NOT NULL, DOJ TEXT NOT NULL,IdProof BLOB NOT NULL, profile BLOB NOT NULL, qrID  BLOB NOT NULL, user_type INTEGER NOT NULL)";
        String equipments = "create table "+table2+" ( machine_id INTEGER PRIMARY KEY NOT NULL, mname TEXT NOT NULL, categories TEXT NOT NULL,type TEXT NOT NULL ,location TEXT NOT NULL, lifespan TEXT NOT NULL, mfg_date TEXT NOT Null , installation TEXT,price REAL, qr_img BLOB )";
        String job_details = "create table "+table3+" (job_id INTEGER PRIMARY KEY NOT NULL,machine_no TEXT NOT NULL, job_desc TEXT NOT NULL, start_date TEXT NOT NULL,end_date TEXT NOT NULL, priority INTEGER NOT NULL, emp_id INTEGER NOT NULL, image BLOB, FOREIGN KEY(machine_no) REFERENCES equipments (machine_no)  ON DELETE CASCADE ON UPDATE NO ACTION)";
        String service_history = "create table "+table4+" (job_id INTEGER PRIMARY KEY NOT NULL, machine_no TEXT NOT NULL,job_desc TEXT NOT NULL, start_date TEXT NOT NULL,end_date TEXT NOT NULL, priority INTEGER NOT NULL, emp_id INTEGER NOT NULL, image BLOB, suggestions TEXT  NOT NULL, status INTEGER  NOT NULL," +
                "FOREIGN KEY(job_id) REFERENCES job_details (job_id)  ON DELETE CASCADE ON UPDATE NO ACTION," +
                "FOREIGN KEY(machine_no) REFERENCES equipments (machine_no)  ON DELETE CASCADE ON UPDATE NO ACTION)";

        DB.execSQL(users);
        DB.execSQL(equipments);
        DB.execSQL(job_details);
        DB.execSQL(service_history);

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("DROP TABLE IF EXISTS " +table1);
        DB.execSQL("DROP TABLE IF EXISTS " +table2);
        DB.execSQL("DROP TABLE IF EXISTS " +table3);
        DB.execSQL("DROP TABLE IF EXISTS " +table4);
        onCreate(DB);
    }
    void addmachine(int m_id, String name, String ctg, String type, String loc, String lyf, String mfg, float price, byte[] img ){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("machine_id",m_id);
        cv.put("mname",name);
        cv.put("categories",ctg);
        cv.put("type",type);
        cv.put("location",loc);
        cv.put("lifespan",lyf);
        cv.put("mfg_date",mfg);
        cv.put("installation","22/10/2019");
        cv.put("price",price);
        cv.put("qr_img", img);

        long result = DB.insert(table2,null, cv);
//        if(result == -1){
//            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
//        }

    }
}
