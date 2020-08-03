package com.example.airport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;
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
        String users = "create table "+table1+" (emp_id INTEGER PRIMARY KEY, fname TEXT NOT NULL, lname TEXT NOT NULL,email TEXT NOT NULL UNIQUE,password TEXT NOT NULL UNIQUE, phone TEXT NOT NULL UNIQUE ,DOB TEXT,address TEXT,gender TEXT,age  TEXT  , qualification TEXT  , department TEXT  , designation TEXT  , DOJ TEXT  ,IdProof BLOB,profile BLOB  , qrID  BLOB, userType TEXT  )";

        String equipments = "create table "+table2+" ( machine_id TEXT PRIMARY KEY NOT NULL, mname TEXT NOT NULL, categories TEXT NOT NULL,type TEXT NOT NULL ,location TEXT NOT NULL, " +
                "lifespan TEXT NOT NULL, mfg_date TEXT NOT Null , date_of_installation TEXT,price REAL, qr_img BLOB )";

        String job_details = "create table "+table3+" (job_id INTEGER PRIMARY KEY NOT NULL,machine_no TEXT NOT NULL, job_desc TEXT NOT NULL, start_date TEXT NOT NULL,end_date TEXT NOT NULL," +
                " priority INTEGER NOT NULL, emp_id INTEGER NOT NULL, image BLOB, FOREIGN KEY(machine_no) REFERENCES equipments (machine_no)  ON DELETE CASCADE ON UPDATE NO ACTION)";

        String service_history = "create table "+table4+" (job_id INTEGER PRIMARY KEY NOT NULL, machine_no TEXT NOT NULL,job_desc TEXT NOT NULL, start_date TEXT NOT NULL,end_date TEXT NOT NULL, priority INTEGER NOT NULL, emp_id INTEGER NOT NULL, image BLOB, suggestions TEXT  NOT NULL, status INTEGER  NOT NULL," +
                "FOREIGN KEY(job_id) REFERENCES job_details (job_id)  ON DELETE CASCADE ON UPDATE NO ACTION," +
                "FOREIGN KEY(machine_no) REFERENCES equipments (machine_no)  ON DELETE CASCADE ON UPDATE NO ACTION)";

        DB.execSQL(users);
        DB.execSQL(equipments);
        DB.execSQL(job_details);
        DB.execSQL(service_history);


        DB.execSQL("insert into users(emp_id, fname, lname,email,password, phone,DOB ,address,gender,age, qualification, department , designation, DOJ ,IdProof,profile, qrID , userType) " +
                "values(1,'Nick','Morningstar', 'admin@gmail.com', 'admin', '9522801231', '04/09/1999','BIT Wardha','Male','21','M.Tech','admin','Manager','23/05/2020',null,null,null,'Admin')");

        DB.execSQL("insert into users(emp_id, fname, lname,email,password, phone,DOB ,address,gender,age, qualification, department , designation, DOJ ,IdProof,profile, qrID , userType) " +
                        "values(2,'Ben','stokes', 'benst@gmail.com', 'password', '9727801231', '09/01/1999','NIT kurukshetra','Male','21','B.Tech','supervisor','assitant','2/05/2020',null,null,null,'Maintainance Supervisor')");
        DB.execSQL("insert into users(emp_id, fname, lname,email,password, phone,DOB ,address,gender,age, qualification, department , designation, DOJ ,IdProof,profile, qrID , userType) " +
                        "values(3,'Khal','Drogo', 'mechanic@gmail.com', 'mechanic', '9522801233', '24/09/1992', 'Borgaon Bridge','Male','28','Diploma','aircraft','senior engineer','23/03/2020',null,null,null,'Mechanic')");


        DB.execSQL("insert into equipments(machine_id, mname, categories, type,location,   lifespan, mfg_date ,   date_of_installation, price, qr_img ) " +
                "values('M202001','Table no. 31','Furniture & Facilitation Equipments', 'Room no. 7', 'furniture', '5 years','21/08/2019', '04/02/2019',2500,null)");


//        this.addUser(1,"Nick","Morningstar", "admin@gmail.com", "admin", "9522801231", "04/09/1999", "BIT Wardha","Male","21","M.Tech",null,"","23/05/2020",null,null,null,"Admin");
//        this.addUser(2,"John","Snow", "supervisor@gmail.com", "supervisor", "9522801232", "24/09/196", "Ram Nagar","Male","24","B.Tech",null,"","23/03/2020",null,null,null,"Maintainance Supervisor");
//        this.addUser(3,'Khal','Drogo', 'mechanic@gmail.com', 'mechanic', '9522801233', '24/09/1992', 'Borgaon Bridge','Male','28','Diploma','aircraft','senior engineer','23/03/2020',null,null,null,'Mechanic');
//

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("DROP TABLE IF EXISTS " +table1);
        DB.execSQL("DROP TABLE IF EXISTS " +table2);
        DB.execSQL("DROP TABLE IF EXISTS " +table3);
        DB.execSQL("DROP TABLE IF EXISTS " +table4);
        onCreate(DB);
    }

    //INSERT USER TO USERS TABLE
    public void addUser(int emp_id, String fname, String lname, String email, String pwd, String phone, String dob, String address, String gender, String age, String qualification,
                 String department, String designation, String doj, byte[] IdProof, byte[] profile, byte[] qrID, String usertype){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("emp_id", emp_id);
        cv.put("fname",fname);               cv.put("lname",lname);
        cv.put("email",email);              cv.put("password",pwd);
        cv.put("phone",phone);               cv.put("DOB",dob);
        cv.put("address",address);           cv.put("gender",gender);
        cv.put("age",age);                   cv.put("qualification",qualification);
        cv.put("department",department);   cv.put("designation",designation);
        cv.put("DOJ",doj);                   cv.put("IdProof",IdProof);
        cv.put("profile",profile);           cv.put("qrID",qrID);
        cv.put("userType",usertype);
        long result = DB.insert(table1,null, cv);
//        if(result == -1){
//            Toast.makeText(.this, "Failed", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(this, "Added Successfully!", Toast.LENGTH_SHORT).show();
//        }
    }



    //INSERT MACHINE TO MACHINE TABLE
    void addmachine(String m_id, String name, String ctg, String type, String loc, String lyf, String mfg, float price, byte[] img ){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("machine_id",m_id);
        cv.put("mname",name);
        cv.put("categories",ctg);
        cv.put("type",type);
        cv.put("location",loc);
        cv.put("lifespan",lyf);
        cv.put("mfg_date",mfg);
        cv.put("date_of_installation","22/10/2019");
        cv.put("price",price);
        cv.put("qr_img", img);

        long result = DB.insert(table2,null, cv);
//        if(result == -1){
//            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
//        }
    }

    //INSERT JOB DETAILS
    void addJobDetails(int job_id,String machine_no,String job_desc,String start_date,String end_date,int priority,int emp_id, byte[] image ){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("job_id",job_id);    cv.put("machine_no",machine_no);
        cv.put("job_desc",job_desc);    cv.put("start_date",start_date);
        cv.put("end_date",end_date);    cv.put("machine_no",priority);
        cv.put("emp_id",emp_id);    cv.put("image",image);

        long result = DB.insert(table3,null, cv);
    }

    //INSERT SERVICE HISTORY
    void addJobDetails(int job_id,String machine_no,String job_desc,String start_date,String end_date,int priority,int emp_id, byte[] image,String suggestions,int status){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("job_id",job_id);    cv.put("machine_no",machine_no);
        cv.put("job_desc",job_desc);    cv.put("start_date",start_date);
        cv.put("end_date",end_date);    cv.put("machine_no",priority);
        cv.put("emp_id",emp_id);    cv.put("image",image);
        cv.put("suggestions",suggestions);    cv.put("status",status);

        long result = DB.insert(table4,null, cv);
    }

    public Cursor ViewUsers(){
        SQLiteDatabase DB = this.getReadableDatabase();
        String q1 = "select * from "+table1;
        Cursor cursor = DB.rawQuery(q1,null);
        return  cursor;
    }
    public Cursor ViewMachines() {
        SQLiteDatabase DB = this.getReadableDatabase();
        String q2 = "select * from " + table2;
        Cursor cursor = DB.rawQuery(q2, null);
        return cursor;
    }
    }
