package com.example.sharedpreference2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText et_input;
    TextView tv_result;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_input = findViewById(R.id.et_input);
        tv_result = findViewById(R.id.tv_result);

        sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);

        if(sharedPreferences.getString("name","").equals("ryan")){
            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
        }
    }

    public void saveSession(View view){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", et_input.getText().toString());
        editor.apply();
        et_input.setText("");
        if(sharedPreferences.getString("name","").equals("ryan")){
            startActivity(new Intent(getApplicationContext(), MainActivity2.class));
        }
    }

    public void displaySession(View view){
        tv_result.setText(sharedPreferences.getString("name", ""));
    }
}