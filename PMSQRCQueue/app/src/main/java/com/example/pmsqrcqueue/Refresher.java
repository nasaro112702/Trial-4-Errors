package com.example.pmsqrcqueue;


import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

public class Refresher {
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    Context context;

    public Refresher(Context context){
        this.context = context;
    }

    public void run(){
        Toast.makeText(context, "Normal", Toast.LENGTH_SHORT).show();
    }

    public void start() {
        if (!isTimerRunning) {
            countDownTimer = new CountDownTimer(Long.MAX_VALUE, 3000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    run();
                }

                @Override
                public void onFinish() {
                    isTimerRunning = false;
                }
            }.start();

            isTimerRunning = true;
        }
    }

    public void stop() {
        if (isTimerRunning) {
            countDownTimer.cancel();
            isTimerRunning = false;
        }
    }
}
