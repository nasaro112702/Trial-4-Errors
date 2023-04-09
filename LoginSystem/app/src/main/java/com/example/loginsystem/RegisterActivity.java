package com.example.loginsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText et_username, et_firstname, et_lastname, et_password, et_conf_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);

        et_username = findViewById(R.id.reg_username);
        et_firstname = findViewById(R.id.reg_firstname);
        et_lastname = findViewById(R.id.reg_lastname);
        et_password = findViewById(R.id.reg_password);
        et_conf_password = findViewById(R.id.reg_conf_password);
    }

    public void registerUser(View view){
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        String conf_password = et_conf_password.getText().toString();
        String firstname = et_firstname.getText().toString();
        String lastname = et_lastname.getText().toString();

        if(username.isEmpty() || password.isEmpty() || conf_password.isEmpty() || firstname.isEmpty() || lastname.isEmpty()){
            Toast.makeText(this, "Some fields are empty!", Toast.LENGTH_SHORT).show();
        }else{
            if(password.equals(conf_password)){
                databaseHelper.addUser(firstname, lastname, username, password);
                Toast.makeText(this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(this, "Password don't match!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}