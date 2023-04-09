package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText fname, lname, username, password, conf_password;

    public static final String EXTRA_MESSAGE = "com.example.registration.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        conf_password = findViewById(R.id.conf_password);
    }

    public void register(View view){
        if(fname.getText().toString().isEmpty() || lname.getText().toString().isEmpty() || username.getText().toString().isEmpty() ||
                password.getText().toString().isEmpty() || conf_password.getText().toString().isEmpty()){
            Toast.makeText(this, "Some Fields are Empty!", Toast.LENGTH_SHORT).show();
        }else{
            if(password.getText().toString().equals(conf_password.getText().toString())){
                Intent intent = new Intent(this, Welcome_Page.class);
                intent.putExtra(EXTRA_MESSAGE, fname.getText()+" "+lname.getText());
                startActivity(intent);

                Toast.makeText(this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Password don't Match!", Toast.LENGTH_SHORT).show();
            }

        }
        
        
    }
}