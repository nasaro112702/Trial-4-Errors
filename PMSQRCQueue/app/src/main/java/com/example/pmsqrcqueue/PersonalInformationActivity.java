package com.example.pmsqrcqueue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PersonalInformationActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText et_name, et_contact_num;

    BluetoothPrinter printer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_personal_information);

        printer = new BluetoothPrinter("PT-210_61D9");

        toolbar = findViewById(R.id.toolbar4);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LocalDBHelper dbHelper = new LocalDBHelper("http://"+new Host().getAddress()+"/pmsqrc_host/get_queue_num.php",this);
        dbHelper.getQueueNumber();

        et_name = findViewById(R.id.et_name);
        et_contact_num = findViewById(R.id.et_contact_num);
    }

    public void gotoFinishQueue(View view){

        String name = et_name.getText().toString();
        String contact_num = et_contact_num.getText().toString();
        String service = new DentalService().getService();

        if(!(name.isEmpty() || contact_num.isEmpty())){
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String currentTime = sdf.format(new Date());


            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String currentDate = dateFormat.format(calendar.getTime());


            LocalDBHelper dbHelper = new LocalDBHelper("http://"+new Host().getAddress()+"/pmsqrc_host/execute.php",this);
            dbHelper.execute("INSERT INTO queuing_tbl (queue_num, name, contact_num, service, status, date_added, time_added) VALUES ('"+new QueueNum().getQueue()+"','"+name+"','"+contact_num+"','"+service+"','Waiting','"+currentDate+"','"+currentTime+"')");

            sendToPrinter(name, contact_num, service, new QueueNum().getQueue());

            String message = "Reminder from Paqueo Dental Clinic\n\nYour Queue Number is "+new QueueNum().getQueue()+".\n\nPlease be guided.";
            sendSMS(contact_num, message);

            startActivity(new Intent(this, FinishQueueActivity.class));

            new ServiceCategory().clearCategory();
            new DentalService().clearService();
            finish();
        }else{
            Toast.makeText(this, "Some fields are Empty!", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendToPrinter(String name, String contact_num, String service, String queue_num){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            String currentTime = sdf.format(new Date());


            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
            String currentDate = dateFormat.format(calendar.getTime());

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
            printer.printText("\nName: "+name);
            printer.printText("Contact Number: "+contact_num);
            printer.printText("Service: "+service);
            printer.line();
            printer.setTextAlignment("center");
            printer.boldText(true);
            printer.setTextSize("x-large");
            printer.printText(queue_num);
            printer.defaultText();
            printer.line();
            printer.setTextAlignment("center");
            printer.printText(currentDate+" @ "+currentTime);
            printer.printText("*This serves as your ticket*");
            printer.space("end");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendSMS(String number, String message){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null,message, null, null);
    }
}