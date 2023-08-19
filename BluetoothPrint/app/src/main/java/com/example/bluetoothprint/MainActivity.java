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
import android.widget.Toast;

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

    public void send(View view){
        try {
            printer.defaultText();
            printer.space("start");
            printer.setTextAlignment("center");
            printer.setTextSize("x-large");
            printer.printText("Paqueo");
            printer.printText("Dental Clinic");
            printer.setTextSize("normal");
            printer.printText("Navarro Street, Surigao City");
            printer.printText("8400 Surigao del Norte");
            printer.printText("paqueodentalclinic@gmail.com");
            printer.printText("09196196889");
            printer.setTextAlignment("left");
            printer.printText("\nName: Ryan C. Elico");
            printer.printText("Contact Number: 09096490065");
            printer.printText("Service: Dental Crown");
            printer.line();
            printer.setTextAlignment("center");
            printer.boldText(true);
            printer.setTextSize("x-large");
            printer.printText("457");
            printer.defaultText();
            printer.line();
            printer.setTextAlignment("center");
            printer.printText("*This serves as your ticket*");
            printer.space("end");
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