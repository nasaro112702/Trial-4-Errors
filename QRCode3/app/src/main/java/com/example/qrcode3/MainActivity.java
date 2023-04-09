package com.example.qrcode3;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {

    ImageView img_qr;
    EditText et_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_value = findViewById(R.id.et_value);
        img_qr = findViewById(R.id.img_qr);
    }

    public void generateQR(View view){
        String value = et_value.getText().toString();

        if(!value.isEmpty()){
            MultiFormatWriter writer = new MultiFormatWriter();

            try {
                BitMatrix matrix = writer.encode(value, BarcodeFormat.QR_CODE, 350, 350);

                BarcodeEncoder encoder = new BarcodeEncoder();

                Bitmap bitmap = encoder.createBitmap(matrix);

                img_qr.setImageBitmap(bitmap);
            } catch (WriterException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void scanQR(View view){
        ScanOptions options = new ScanOptions();
        options.setCaptureActivity(CaptureActivity.class);
        options.setOrientationLocked(true);
        options.setBeepEnabled(true);
        qr_launcher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> qr_launcher = registerForActivityResult(new ScanContract(), result ->{
        if(result.getContents() != null){
            new AlertDialog.Builder(this)
                    .setTitle("Result")
                    .setMessage(result.getContents())
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }
    });
}