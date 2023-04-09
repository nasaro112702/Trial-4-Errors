package com.example.loginsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    static String DB_NAME = "simple_system";
    static String TBL_1 = "users";

    //TBL_1
    static String COL_1 = "firstname";
    static String COL_2 = "lastname";
    static String COL_3 = "username";
    static String COL_4 = "password";

    SQLiteDatabase db = getWritableDatabase();

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TBL_1+" (id integer primary key autoincrement, " +
                COL_1+" text,"+
                COL_2+" text, "+
                COL_3+" text,"+
                COL_4+" text"+
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TBL_1);
    }

    boolean addUser(String firstname, String lastname, String username, String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, firstname);
        contentValues.put(COL_2, lastname);
        contentValues.put(COL_3, username);
        contentValues.put(COL_4, password);
        long result = db.insert(TBL_1,null, contentValues);

        return result != 0;
    }

    Cursor getValue(String query){
        return db.rawQuery(query, null);
    }
}
