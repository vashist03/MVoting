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

import com.example.mvoting.model.CandidateModel;
import com.example.mvoting.model.UserModel;
import com.example.mvoting.model.VotingModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "tbl_user";
    public static final String CANDIDATE_TABLE = "tbl_candidate";
    public static final String VOTING_TABLE = "tbl_voting";
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
        String createTableStatementCandidate ="CREATE TABLE IF NOT EXISTS "+ CANDIDATE_TABLE+"(userId INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, party VARCHAR, vote INTEGER, unvote INTEGER)";
        db.execSQL(createTableStatementCandidate);
        Log.i("Create Table User","Table Candidate Created");
        String createTableStatementVoting ="CREATE TABLE IF NOT EXISTS "+ VOTING_TABLE+"(userId INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, party VARCHAR, vote INTEGER)";
        db.execSQL(createTableStatementVoting);
        Log.i("Create Table User","Table voting Created");
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

    public boolean addCandidate(CandidateModel candidateModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", candidateModel.getName());
        cv.put("party", candidateModel.getParty());
        cv.put("vote", candidateModel.getVote());
        cv.put("unvote", candidateModel.getUnvote());

        long insert = db.insert(CANDIDATE_TABLE, null, cv);

        if(insert == -1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public boolean addVoting(VotingModel voteModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", voteModel.getName());
        cv.put("party", voteModel.getParty());
        cv.put("vote", voteModel.getVote());

        long insert = db.insert(VOTING_TABLE, null, cv);

        if(insert == -1)
        {
            Log.e("S", insert+"");
            return false;

        }
        else
        {
            Log.e("f", insert+"");
            return true;
        }

    }

    public boolean updateUserVote(String userNIC)
    {
        Log.e("IDDDDDDDDDD", userNIC+"");
        ContentValues cv = new ContentValues();
        cv.put("voted", true);
        long update = db.update(USER_TABLE, cv, "nic = ?", new String[]{userNIC});

        if(update == - 1) {
            Log.e("user not updated", false+"");
            return false;
        }else {
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

    public Cursor checkUser(String pin, String nic)
    {
        String response = "";
        db = this.getWritableDatabase();
        String selectTableStatement="SELECT * FROM  " + USER_TABLE + " WHERE nic = '"+ nic +"' AND pin = '" + pin +"'";

        Cursor res = db.rawQuery(selectTableStatement, null);

        return res;
    }

    public Cursor getCandidate()
    {
        db = this.getWritableDatabase();
        String selectTableStatement="SELECT * FROM tbl_candidate";

        Cursor res = db.rawQuery(selectTableStatement, null);
        return res;

    }



}
