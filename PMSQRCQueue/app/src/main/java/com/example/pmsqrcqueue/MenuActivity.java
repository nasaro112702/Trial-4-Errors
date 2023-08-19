package com.example.pmsqrcqueue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MenuActivity extends AppCompatActivity {

    BluetoothPrinter printer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        LocalDBHelper dbHelper = new LocalDBHelper("http://"+new Host().getAddress()+"/pmsqrc_host/get_queue_num.php",this);
        dbHelper.getQueueNumber();
        printer = new BluetoothPrinter("PT-210_61D9");
        requestPermissions();

        Runner runner = new Runner(this);
        runner.start();
    }

    private static final int REQUEST_CODE_PERMISSIONS = 100; // Use any unique integer value
    private void requestPermissions() {
        String[] permissions = {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.BLUETOOTH,
                android.Manifest.permission.SEND_SMS,
                Manifest.permission.BLUETOOTH_CONNECT
        };
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSIONS);
    }

    public void gotoDentalProcedure(View view){
        new ServiceCategory("Dental Procedure");
        startActivity(new Intent(this, DentalProcedureActivity.class));
    }

    public void gotoDentalAssessment(View view){
        new ServiceCategory("Dental Assessment");
        startActivity(new Intent(this, DentalAssessmentActivity.class));
    }

    public void gotoPersonalInformation(View view){
        new ServiceCategory("Consultation");
        startActivity(new Intent(this, PersonalInformationActivity.class));
    }

    public void open(View view){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            // Bluetooth is currently disabled, prompt the user to enable it
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 2);
        }else{
            printer.findDevice();
            try {
                printer.openConnection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Runner extends Refresher{
        public Runner(Context context) {
            super(context);
        }

        @Override
        public void run(){
            LocalDBHelper readHelper = new LocalDBHelper("http://"+new Host().getAddress()+"/pmsqrc_host/read_sms.php", context);
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

                                LocalDBHelper exeHelper = new LocalDBHelper("http://"+new Host().getAddress()+"/pmsqrc_host/execute.php", context);
                                exeHelper.execute("DELETE FROM sms_tbl WHERE id = '"+id+"'");
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onError() {
                    // Handle the error
                    Toast.makeText(context, "Invalid Host Address", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void sendSMS(String number, String message){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null,message, null, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == WRITE_PERMISSION_CODE){
//            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//        if (requestCode == REQUEST_CODE_PERMISSIONS) {
//            // Check if all the requested permissions are granted
//            boolean allPermissionsGranted = true;
//            for (int grantResult : grantResults) {
//                if (grantResult != PackageManager.PERMISSION_GRANTED) {
//                    allPermissionsGranted = false;
//                    break;
//                }
//            }
//
//            if (allPermissionsGranted) {
//                // All permissions are granted, proceed with your operation
//                Toast.makeText(this, "All Permissions Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                // Permissions are not granted, handle the situation here
//                // For example, show a message or ask for permissions again
//            }
//        }
    }
}