package com.example.loginsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    TextView tv_userid;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        tv_userid = findViewById(R.id.tv_userid);

        databaseHelper = new DatabaseHelper(this);

        String id = sharedPreferences.getString("user_id","");
        String firstname = "";
        String lastname = "";

        Cursor cursor = databaseHelper.getValue("select * from users where id = '"+id+"'");
        while(cursor.moveToNext()){
            firstname = cursor.getString(1);
            lastname = cursor.getString(2);
        }

        tv_userid.setText("Firstname:  "+firstname+"\nLastname: "+lastname);
    }

    public void logout(View view){
        editor.putString("user_id", "");
        editor.apply();
        finish();
    }
}