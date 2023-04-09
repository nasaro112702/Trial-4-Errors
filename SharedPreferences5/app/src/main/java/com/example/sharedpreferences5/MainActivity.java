package com.example.sharedpreferences5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    EditText et_value;
    TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        et_value = findViewById(R.id.et_value);
        tv_result = findViewById(R.id.tv_result);
    }

    public void setSession(View view){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("text", et_value.getText().toString());
        editor.apply();
        et_value.setText("");
    }

    public void getSession(View view){
        tv_result.setText(sharedPreferences.getString("text", ""));
    }
}