package com.example.mvoting.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.mvoting.model.UserModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "tbl_user";
    public SQLiteDatabase db;

    public DataBaseHelper(@Nullable Context context) {
        super(context,"mvoting.db",null,1);
    }

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler)
    {
        super(context, name, factory, version, errorHandler);
    }

    //Function for the first time a database is accessed.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //FOR USER PART
        String createTableStatement="CREATE TABLE IF NOT EXISTS "+ USER_TABLE+"(userId VARCHAR PRIMARY KEY, fname VARCHAR, surname VARCHAR, address VARCHAR, email VARCHAR, dob VARCHAR, nic VARCHAR, phone NUMERIC, pin VARCHAR, vote VARCHAR, voted BOOLEAN, uType VARCHAR)";
        db.execSQL(createTableStatement);
        Log.i("Create Table User","Table User Created");
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public DataBaseHelper(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    //function when version number changes.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addUser(UserModel userModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("userId",userModel.getId());
        cv.put("fname",userModel.getFname());
        cv.put("surname",userModel.getSurname());
        cv.put("address",userModel.getAddress());
        cv.put("email",userModel.getEmail());
        cv.put("dob",userModel.getDob());
        cv.put("nic",userModel.getNic());
        cv.put("phone",userModel.getPhone());
        cv.put("pin",userModel.getPin());
        cv.put("vote",userModel.getVote());
        cv.put("voted",userModel.isVoted());
        cv.put("uType",userModel.getuType());
        long insert = db.insert(USER_TABLE, null, cv);

        if(insert == -1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
    public List<UserModel> getAllElements() {

        ArrayList<UserModel> list = new ArrayList<UserModel>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + USER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            try {
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        UserModel obj = new UserModel();
                        list.add(obj);
                    } while (cursor.moveToNext());
                }

            } finally {
                try { cursor.close(); } catch (Exception ignore) {}
            }

        } finally {
            try { db.close(); } catch (Exception ignore) {}
        }
        //Log.e("",list.toString());
        return list;
    }

    public Cursor getData()
    {
        db = this.getWritableDatabase();
        String selectTableStatement="SELECT * FROM  " + USER_TABLE;

        Cursor res = db.rawQuery(selectTableStatement, null);
        return res;

    }

    public String checkUser(String pin, String nic)
    {
        String response = "";
        db = this.getWritableDatabase();
        String selectTableStatement="SELECT * FROM  " + USER_TABLE + " WHERE nic = '"+ nic +"' AND pin = '" + pin +"'";

        Cursor res = db.rawQuery(selectTableStatement, null);

        if(res.getCount() > 0) {
            while (res.moveToNext()) {
                response = res.getString(11).toString();
            }
        }else {
            response = "User not found";
        }

        return response;
    }




}