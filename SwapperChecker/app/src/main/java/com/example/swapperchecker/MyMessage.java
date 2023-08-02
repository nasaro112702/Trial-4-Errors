package com.example.swapperchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MyMessage extends AppCompatActivity {

    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);

        tv_result = findViewById(R.id.tv_result);

        String remarks = getIntent().getExtras().getString("remarks");

        tv_result.setText(remarks);
    }
}