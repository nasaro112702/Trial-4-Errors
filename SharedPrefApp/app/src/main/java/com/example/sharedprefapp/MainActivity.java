package com.example.sharedprefapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String myPreferences = "MyPrefs";
        String name = "nameKey";
        String password = "passKey";
        String email = "emailKey";

        EditText et1 = findViewById(R.id.et1);
        EditText et2 = findViewById(R.id.et2);
        EditText et3 = findViewById(R.id.et3);
        Button bt = findViewById(R.id.bt);

        SharedPreferences sharedPref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = et1.getText().toString();
                String pw = et2.getText().toString();
                String e = et3.getText().toString();

                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString(name, n);
                editor.putString(password, pw);
                editor.putString(email, e);
                editor.commit();
                Toast.makeText(MainActivity.this, "Thanks", Toast.LENGTH_SHORT).show();
            }
        });
    }
}