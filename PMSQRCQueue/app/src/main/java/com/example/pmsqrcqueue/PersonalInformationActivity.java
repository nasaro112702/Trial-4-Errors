package com.example.pmsqrcqueue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PersonalInformationActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText et_name, et_contact_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_personal_information);

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

            startActivity(new Intent(this, FinishQueueActivity.class));

            new ServiceCategory().clearCategory();
            new DentalService().clearService();
            finish();
        }else{
            Toast.makeText(this, "Some fields are Empty!", Toast.LENGTH_SHORT).show();
        }

    }
}