package com.example.loginsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText et_username, et_password;

    DatabaseHelper databaseHelper;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        databaseHelper = new DatabaseHelper(this);

        et_username = findViewById(R.id.log_username);
        et_password = findViewById(R.id.log_password);

        if(!sharedPreferences.getString("user_id","").equals("")){
            startActivity(new Intent(this, HomeActivity.class));
        }
    }

    public void loginUser(View view){
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();

        if(username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Some fields are empty!", Toast.LENGTH_SHORT).show();
        }else{
            Cursor cursor = databaseHelper.getValue("select * from users where username='"+username+"' and password='"+password+"'");
            if(cursor.getCount() > 0){

                cursor.moveToNext();
                editor.putString("user_id", cursor.getString(0));
                editor.apply();
                startActivity(new Intent(this, HomeActivity.class));
            }else{
                Toast.makeText(this, "No user found!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void gotoRegister(View view){
        startActivity(new Intent(this, RegisterActivity.class));
    }
}