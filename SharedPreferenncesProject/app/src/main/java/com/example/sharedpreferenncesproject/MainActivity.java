package com.example.sharedpreferenncesproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText et_text;
    TextView tv_session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_text = findViewById(R.id.et_text);
        tv_session = findViewById(R.id.tv_session);

    }

    public void save(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("msg", et_text.getText().toString());
        editor.apply();
        et_text.setText("");
    }

    public void display(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        tv_session.setText(sharedPreferences.getString("msg", ""));
    }
}