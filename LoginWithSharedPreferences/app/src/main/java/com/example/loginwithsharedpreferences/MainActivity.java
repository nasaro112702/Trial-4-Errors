package com.example.loginwithsharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    String test_username, test_password;
    EditText et_username, et_password;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        test_username = "nasaro";
        test_password = "1234";

        sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);

        if(sharedPreferences.getString("username","").equals(test_username) && sharedPreferences.getString("password", "").equals(test_password)){
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

    }

    public void login(View view){
        if(et_username.getText().toString().equals(test_username) && et_password.getText().toString().equals(test_password)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", et_username.getText().toString());
            editor.putString("password", et_password.getText().toString());
            editor.apply();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
    }
}