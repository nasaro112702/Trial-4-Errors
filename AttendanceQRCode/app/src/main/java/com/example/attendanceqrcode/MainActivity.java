package com.example.attendanceqrcode;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

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

    public void generateQR(View view){
        MultiFormatWriter writer = new MultiFormatWriter();

        try {
            BitMatrix matrix = writer.encode("hello world", BarcodeFormat.QR_CODE, 350, 350);

            BarcodeEncoder encoder = new BarcodeEncoder();

            Bitmap bitmap = encoder.createBitmap(matrix);

        } catch (WriterException e) {
            throw new RuntimeException(e);
        }

    }

    public void scanQR(View view){
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setCaptureActivity(CaptureAct.class);
        options.setOrientationLocked(true);
        scan_launcher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> scan_launcher = registerForActivityResult(new ScanContract(), result->{
        if(result.getContents() != null){
            new AlertDialog.Builder(this)
                    .setTitle("Result")
                    .setMessage(result.getContents())
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();


        }
    });
}