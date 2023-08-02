package com.example.wifitest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView all_wifi;
    EditText et_ssid, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        all_wifi = findViewById(R.id.all_wifi);
        et_ssid = findViewById(R.id.et_ssid);
        et_password = findViewById(R.id.et_password);


        requestLocationPermission();
    }

    public void refresh(View view){
        requestLocationPermission();
    }

    public void requestLocationPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            // Instantiate a WifiManager object to get the list of available Wi-Fi networks
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiManager.startScan();

            List<ScanResult> scanResults = wifiManager.getScanResults();

            String list_of_wifi = "";
            // Iterate over the scan results and show the details of each network
            for (ScanResult scanResult : scanResults) {
                String ssid = scanResult.SSID;
                String bssid = scanResult.BSSID;
                int signalStrength = scanResult.level;
                list_of_wifi += "SSID: "+ssid+"\n\n";
            }

            all_wifi.setText(list_of_wifi);

            // Define the SSID and password of the Wi-Fi network you want to connect to
            String ssid = et_ssid.getText().toString();
            String password = et_password.getText().toString();

            if(ssid.isEmpty() || password.isEmpty()){

            }else{
                // The network with the specified SSID has been found, check the password
                WifiConfiguration wifiConfig = new WifiConfiguration();
                wifiConfig.SSID = "\"" + ssid + "\"";
                wifiConfig.preSharedKey = "\"" + password + "\"";
                WifiManager wifiManager2 = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                int networkId = wifiManager2.addNetwork(wifiConfig);
                wifiManager2.disconnect();
                wifiManager2.enableNetwork(networkId, true);
                wifiManager2.reconnect();

                int retries = 0;
                while (wifiManager2.getConnectionInfo().getSupplicantState() != SupplicantState.COMPLETED && retries < 10) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Toast.makeText(this, "Retries: "+retries, Toast.LENGTH_SHORT).show();
                    retries++;
                }



            }



        }else{
            writePermission();
        }
    }

    int FINE_LOCATION_CODE = 1;

    private void writePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Write is really needed")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == FINE_LOCATION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}