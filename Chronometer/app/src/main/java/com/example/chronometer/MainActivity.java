package com.example.chronometer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
    }

    public void startTime(View view){
        chronometer.start();
    }

    public void stopTime(View view){
        chronometer.stop();
    }

    public void restartTime(View view){
        chronometer.setBase(SystemClock.elapsedRealtime());
    }
}