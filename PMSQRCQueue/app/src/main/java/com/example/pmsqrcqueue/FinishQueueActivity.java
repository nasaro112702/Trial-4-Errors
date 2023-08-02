package com.example.pmsqrcqueue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class FinishQueueActivity extends AppCompatActivity {

    TextView tv_queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_finish_queue);

        tv_queue = findViewById(R.id.tv_queue);

        tv_queue.setText("#"+new QueueNum().getQueue());
    }

    public void finishActivity(View view){
        finish();
    }
}