package com.example.pmsqrcqueue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);

        LocalDBHelper dbHelper = new LocalDBHelper("http://"+new Host().getAddress()+"/pmsqrc_host/get_queue_num.php",this);
        dbHelper.getQueueNumber();
    }

    public void gotoDentalProcedure(View view){
        new ServiceCategory("Dental Procedure");
        startActivity(new Intent(this, DentalProcedureActivity.class));
    }

    public void gotoDentalAssessment(View view){
        new ServiceCategory("Dental Assessment");
        startActivity(new Intent(this, DentalAssessmentActivity.class));
    }

    public void gotoPersonalInformation(View view){
        new ServiceCategory("Consultation");
        startActivity(new Intent(this, PersonalInformationActivity.class));
    }
}