package com.example.mave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoLocation(View view){
        startActivity(new Intent(MainActivity.this, LocationActivity.class));
    }

    public void gotoObserve(View view){
        startActivity(new Intent(MainActivity.this, ObserveActivity.class));
    }
}