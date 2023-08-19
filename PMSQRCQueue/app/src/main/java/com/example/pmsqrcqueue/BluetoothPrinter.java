package com.example.pmsqrcqueue;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BluetoothPrinter {
    // android built in classes for bluetooth operations
    static BluetoothAdapter mBluetoothAdapter;
    static BluetoothSocket mmSocket;
    static BluetoothDevice mmDevice;

    // needed for communication to bluetooth device / network
    static OutputStream mmOutputStream;
    static InputStream mmInputStream;
    static Thread workerThread;

    static byte[] readBuffer;
    static int readBufferPosition;
    static volatile boolean stopWorker;

    static String deviceName;

    static Context context;

    public BluetoothPrinter(String deviceName){
        this.deviceName = deviceName;
    }

    void setTextSize(String size){
        switch(size){
            case "normal":
                byte[] resetTextSizeCommand = new byte[]{0x1B, 0x21, 0x00};
                try {
                    mmOutputStream.write(resetTextSizeCommand);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "large":
                byte[] largeSizeCommand = new byte[]{0x1B, 0x21, 0x10};
                try {
                    mmOutputStream.write(largeSizeCommand);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "x-large":
                byte[] xlargeSizeCommand = new byte[]{0x1B, 0x21, 0x30};
                try {
                    mmOutputStream.write(xlargeSizeCommand);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }
    void line(){
        try {
            String msg = "--------------------------------"+"\n";
            mmOutputStream.write(msg.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void space(String value){
        try {
            String msg = "";
            switch(value){
                case "start":
                    msg = "\n";
                    break;
                case "end":
                    msg = "\n\n\n";
                    break;
            }
            mmOutputStream.write(msg.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setTextAlignment(String alignment){
        switch(alignment){
            case "left":
                byte[] leftAlignCommand = new byte[]{0x1B, 0x61, 0x00};
                try {
                    mmOutputStream.write(leftAlignCommand);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "center":
                byte[] centerAlignCommand = new byte[]{0x1B, 0x61, 0x01};
                try {
                    mmOutputStream.write(centerAlignCommand);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "right":
                byte[] rightAlignCommand = new byte[]{0x1B, 0x61, 0x02};
                try {
                    mmOutputStream.write(rightAlignCommand);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    void boldText(boolean isBold){
        if(isBold){
            byte[] boldTextCommand = new byte[]{0x1B, 0x45, 0x01};
            try {
                mmOutputStream.write(boldTextCommand);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            byte[] disableBoldTextCommand = new byte[]{0x1B, 0x45, 0x00};
            try {
                mmOutputStream.write(disableBoldTextCommand);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void defaultText(){
        boldText(false);
        setTextAlignment("left");
        setTextSize("normal");
    }

    void printText(String text) throws IOException{
        try {
            String msg = text+"\n";
            mmOutputStream.write(msg.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void openConnection() throws IOException {
        try {
            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();
            beginListenForData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void findDevice() {
        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

//            if(!mBluetoothAdapter.isEnabled()) {
//                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBluetooth, 0);
//            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

            if(pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    // PT-210_61D9 is the name of the bluetooth printer device
                    // we got this name from the list of paired devices
                    if (device.getName().equals(deviceName)) {
                        mmDevice = device;
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // close the connection to bluetooth printer.
    void closeConnection() throws IOException {
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {
                                                System.out.print(data);
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
