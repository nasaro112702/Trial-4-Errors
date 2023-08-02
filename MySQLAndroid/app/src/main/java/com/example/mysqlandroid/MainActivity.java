package com.example.mysqlandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.sql.*;

public class MainActivity extends AppCompatActivity {

    EditText et_name, et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
    }

    public void Add(View view){
        if(et_name.getText().toString().equals("") && et_email.getText().toString().equals("")){

        }else{
            try {
                String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
                String DB_URL = "jdbc:mysql://192.168.8.118:3306/test";
                String USER = "root";
                String PASS = "";
                Connection conn;
                Statement stmt;

                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                stmt = conn.createStatement();

                String query = "INSERT INTO users (name, email) VALUES ('" + et_name.getText().toString() + "', '" + et_email.getText().toString() + "')";
                stmt.executeUpdate(query);


                stmt.close();
                conn.close();
            } catch (Exception e) {
                // Handle exceptions
            }
            et_name.setText("");
            et_email.setText("");
            Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
        }



    }
}