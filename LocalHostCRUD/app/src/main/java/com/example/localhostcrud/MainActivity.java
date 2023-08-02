package com.example.localhostcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText et_name;
    TextView tv_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = findViewById(R.id.et_name);
        tv_result = findViewById(R.id.tv_result);
    }

    public void saveData(View view){
        String name = et_name.getText().toString();

        LocalDBHelper exeHelper = new LocalDBHelper("http://192.168.8.198/testhost/execute.php", this);
        exeHelper.execute("INSERT INTO tbl_info (name) VALUES ('"+name+"')");

        LocalDBHelper readHelper = new LocalDBHelper("http://192.168.8.198/testhost/read.php", this);
        readHelper.read("SELECT * FROM tbl_info", new LocalDBHelper.VolleyCallback() {
            @Override
            public void onSuccess(String response) {
                String data = "";
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String fname = jsonObject.getString("name");
                        data += "\nid: "+id+"\nname: "+fname;
                        tv_result.setText(data);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError() {
                // Handle the error
                Toast.makeText(MainActivity.this, "Invalid Host Address", Toast.LENGTH_SHORT).show();
            }
        });
        et_name.setText("");
    }
}