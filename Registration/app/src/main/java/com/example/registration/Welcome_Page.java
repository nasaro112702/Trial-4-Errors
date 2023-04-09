package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Welcome_Page extends AppCompatActivity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        result = findViewById(R.id.result);

        String full_name = getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE);

        result.setText("Welcome to Google, "+full_name+"!");


    }
}