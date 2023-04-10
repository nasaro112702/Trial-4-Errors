package com.example.attendanceqrcode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db = getWritableDatabase();
    public DatabaseHelper(@Nullable Context context) {
        super(context, "attendance", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users (id int primary key autoincrement, firstname text, lastname text, username text, password text, contact_no text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists users");
    }

    void addUser(String firstname, String lastname, String username, String password, String contact_no){
        ContentValues cv = new ContentValues();
        cv.put("firstname", firstname);
        cv.put("lastname", lastname);
        cv.put("username", username);
        cv.put("password", password);
        cv.put("contact_no", contact_no);

        db.insert("users", null, cv);
    }

    Cursor getValue(String query){
        return db.rawQuery(query, null);
    }


}
