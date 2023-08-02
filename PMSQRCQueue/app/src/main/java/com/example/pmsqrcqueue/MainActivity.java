package com.example.pmsqrcqueue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText et_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        et_address = findViewById(R.id.et_address);
    }

    public void connect(View view){
        if(!et_address.getText().toString().isEmpty()){
            LocalDBHelper readHelper = new LocalDBHelper("http://"+et_address.getText().toString()+"/pmsqrc_host/connection.php", this);
            readHelper.checkConnection(new LocalDBHelper.VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    new Host(et_address.getText().toString());
                    gotoMenu();
                }

                @Override
                public void onError() {
                    // Handle the error
                    Toast.makeText(MainActivity.this, "Invalid Host Address", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void gotoMenu(){
        startActivity(new Intent(this, MenuActivity.class));
    }
}