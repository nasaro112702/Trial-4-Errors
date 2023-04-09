package com.example.sharedpreference2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);

    }

    public void logout(View view){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", "");
        editor.apply();
        finish();
    }
}