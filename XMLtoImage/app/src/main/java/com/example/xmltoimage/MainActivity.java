
package com.example.xmltoimage;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    RelativeLayout test_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test_layout = findViewById(R.id.test_layout);
    }

    public void saveImage(View view){
        test_layout.setDrawingCacheEnabled(true);
        test_layout.buildDrawingCache();
        test_layout.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = test_layout.getDrawingCache();
        save(bitmap);
    }

    void save(Bitmap bitmap){
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(root + "/Download");
        String filename = "hahahha.jpg";
        File myfile = new File(file, filename);

        if(myfile.exists()){
            myfile.delete();
        }

        try{
            FileOutputStream fileOutputStream = new FileOutputStream(myfile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch (Exception e){
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}