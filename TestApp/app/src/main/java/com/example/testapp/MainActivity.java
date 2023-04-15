package com.example.testapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.telephony.SmsManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendSMS(String number, String message){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            infoDialog("Need load balance", "This function need load balance");
            SmsManager manager = SmsManager.getDefault();
            manager.sendTextMessage(number, null, message, null, null);
        }else{
            requestSMS();
        }

    }

    private void requestSMS() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)){
            new AlertDialog.Builder(this)
                    .setTitle("Need permission")
                    .setMessage("This should need permission!")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
                        }
                    }).create().show();
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
    }

    public void infoDialog(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }

    public void generateQR(String input){
        MultiFormatWriter writer = new MultiFormatWriter();

        try {
            BitMatrix matrix = writer.encode(input, BarcodeFormat.QR_CODE, 350, 350);

            BarcodeEncoder encoder = new BarcodeEncoder();

            Bitmap bitmap = encoder.createBitmap(matrix);

        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }

    public void scanQR(){
        ScanOptions options = new ScanOptions();
        options.setCaptureActivity(CaptureAct.class);
        options.setOrientationLocked(true);
        options.setBeepEnabled(true);
        scan_launcher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> scan_launcher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() != null){
            infoDialog("Result", result.getContents());
        }
    });

    public void saveLayout(){
        
    }
}