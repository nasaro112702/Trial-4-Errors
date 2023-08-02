package com.example.testprint;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice printerDevice;
    private static BluetoothSocket socket;

    private Button Returnbtn;

    public static BluetoothSocket getBluetoothSocket() {
        return socket;
    }
    private OutputStream outputStream;

    private static final String PRINTER_PAPER_WIDTH_58MM = "PRINTER_PAPER_WIDTH_58MM";
    private static final UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String PRINTER_DEVICE_NAME = "Your Printer Device Name";

    private static final int REQUEST_BLUETOOTH_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the Bluetooth connection is already established
        if (socket == null || !socket.isConnected()) {
            // If not connected, establish the connection
            showPrinterNameDialog();
        }

        Button connectButton = findViewById(R.id.print_button);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPrinterNameDialog();
            }
        });

    }

    private void showPrinterNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Printer Name");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String printerName = input.getText().toString();
                connectToPrinter(printerName);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    private void connectToPrinter(String printerName) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not supported on this device", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "Bluetooth is disabled", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            if (device.getName().equals(printerName)) {
                printerDevice = device;
                break;
            }
        }

        if (printerDevice == null) {
            Toast.makeText(this, "Printer device not found", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            socket = printerDevice.createRfcommSocketToServiceRecord(BT_UUID);
            socket.connect();
            outputStream = socket.getOutputStream();

            // After establishing the connection, configure the printer paper width
            if (printerName.equals(PRINTER_PAPER_WIDTH_58MM)) {
                configurePrinterFor58mmWidth();
            }

            // Printer connection successful, you can now send data to the printer
        } catch (IOException e) {
            Toast.makeText(this, "Failed to connect to printer: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void configurePrinterFor58mmWidth() throws IOException {
        // ESC/POS command to set paper width for 58mm (Assuming printer supports the command)
        byte[] command = {27, 87, 2}; // ESC W 2 (Specifies the paper width in dots)

        // Send the command to the printer
        if (outputStream != null) {
            outputStream.write(command);
            outputStream.flush();
        }
    }

}