package com.example.dateandtimedialog2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    Calendar calendar;

    EditText et_date, et_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();

        et_date = findViewById(R.id.et_date);
        et_time = findViewById(R.id.et_time);
    }

    public void setDate(View view){
        int year = 0;
        int month = 0;
        int day = 0;

        String date = et_date.getText().toString();

        if(date.isEmpty()){
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }else{
            year = Integer.parseInt(date.split("/")[2]);
            month = Integer.parseInt(date.split("/")[0])-1;
            day = Integer.parseInt(date.split("/")[1]);
        }

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                et_date.setText(month+"/"+dayOfMonth+"/"+year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void setTime(View view){
        int hour = 0;
        int minute = 0;

        String time = et_time.getText().toString();

        if(time.isEmpty()){
            hour = calendar.get(Calendar.HOUR);
            minute = calendar.get(Calendar.MINUTE);
        }else{
            hour = Integer.parseInt(time.split(":")[0]);
            minute = Integer.parseInt(time.split(":")[1]);
        }

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                et_time.setText(hourOfDay+":"+minute);
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }
}