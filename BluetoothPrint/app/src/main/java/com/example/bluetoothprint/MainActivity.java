package com.example.bluetoothprint;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    EditText myTextbox;
    BluetoothPrinter printer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        printer = new BluetoothPrinter("PT-210_61D9");
        myTextbox = findViewById(R.id.entry);
    }

    public void open(View view){
        printer.findDevice();
        try {
            printer.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(View view){
        try {
            printer.boldText(true);
            printer.setTextAlignment("center");
            printer.printText("Hello World");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void close(View view){
        try {
            printer.closeConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}