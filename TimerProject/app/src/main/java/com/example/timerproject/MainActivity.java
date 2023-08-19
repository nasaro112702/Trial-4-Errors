package com.example.timerproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity{

    private TextView timerTextView;
    private Button startButton;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);

        Runner runner = new Runner(this);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runner.start();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runner.stop();
            }
        });

        checkSMSPermission();
    }

    class Runner extends Refresher{
        public Runner(Context context) {
            super(context);
        }

        @Override
        public void run(){
            LocalDBHelper readHelper = new LocalDBHelper("http://192.168.42.216/sms_host/read_sms.php", context);
            readHelper.checkConnection(new LocalDBHelper.VolleyCallback() {
                @Override
                public void onSuccess(String response) {
                    if(!response.isEmpty()){
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String number = jsonObject.getString("number");
                                String message = jsonObject.getString("message");
                                
                                sendSMS(number, message);

                                LocalDBHelper exeHelper = new LocalDBHelper("http://192.168.42.216/sms_host/execute.php", context);
                                exeHelper.execute("DELETE FROM sms_tbl WHERE id = '"+id+"'");
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    Toast.makeText(context, "Running", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError() {
                    // Handle the error
                    Toast.makeText(MainActivity.this, "Invalid Host Address", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void sendSMS(String number, String message){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null,message, null, null);
        Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
    }


    public void checkSMSPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){

        }else{
            requestSMS();
        }
    }
    int SMS_CODE = 1;
    private void requestSMS() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.SEND_SMS)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("SMS Permission is really needed!")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.SEND_SMS}, SMS_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == SMS_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}